# Member 1 Module - API Testing Script
# Uses PowerShell to test all endpoints

# Configuration
$BASE_URL = "http://localhost:8080/api/member1/resources"
$ADMIN_TOKEN = "YOUR_JWT_TOKEN_HERE"  # Replace with actual token from auth module

# Color output helper
function Write-Success { Write-Host $args -ForegroundColor Green }
function Write-Error { Write-Host $args -ForegroundColor Red }
function Write-Info { Write-Host $args -ForegroundColor Cyan }
function Write-Header { Write-Host "`n=== $args ===" -ForegroundColor Yellow }

# Test counter
$testsPassed = 0
$testsFailed = 0

# Test helper
function Test-Endpoint {
    param(
        [string]$Method,
        [string]$Endpoint,
        [hashtable]$Body,
        [string]$Description
    )
    
    Write-Info "Testing: $Description"
    
    try {
        $headers = @{
            "Content-Type" = "application/json"
            "Authorization" = "Bearer $ADMIN_TOKEN"
        }
        
        $params = @{
            Uri     = "$BASE_URL$Endpoint"
            Method  = $Method
            Headers = $headers
        }
        
        if ($Body) {
            $params["Body"] = $Body | ConvertTo-Json -Depth 10
        }
        
        $response = Invoke-RestMethod @params -ErrorAction Stop
        Write-Success "✓ PASSED - Status: OK"
        Write-Host ($response | ConvertTo-Json | Out-String)
        $script:testsPassed++
        return $response
    }
    catch {
        Write-Error "✗ FAILED - $($_.Exception.Message)"
        $script:testsFailed++
        return $null
    }
}

# ============================================
# START TESTING
# ============================================

Write-Host "`n╔════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║  Member 1 Module - API Test Suite    ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════╝" -ForegroundColor Cyan

Write-Header "SETUP"
Write-Info "Base URL: $BASE_URL"
Write-Info "Testing with ADMIN token"
Write-Host ""

# Test 1: Create Resource
Write-Header "TEST 1: CREATE RESOURCE (POST)"
$createBody = @{
    name = "Lecture Hall A101"
    type = "LECTURE_HALL"
    capacity = 100
    location = "Building A, Floor 1"
    description = "Main lecture hall with projector"
    status = "ACTIVE"
    availableFrom = "09:00:00"
    availableTo = "17:00:00"
}
$createdResource = Test-Endpoint -Method POST -Endpoint "" -Body $createBody -Description "Create Lecture Hall"
$resourceId = $createdResource.data.id

# Test 2: Create Another Resource
Write-Header "TEST 2: CREATE LAB RESOURCE"
$createLabBody = @{
    name = "Computer Lab B201"
    type = "LAB"
    capacity = 30
    location = "Building B, Floor 2"
    description = "Linux/Windows computer lab"
    status = "ACTIVE"
    availableFrom = "08:00:00"
    availableTo = "18:00:00"
}
Test-Endpoint -Method POST -Endpoint "" -Body $createLabBody -Description "Create Lab Resource"

# Test 3: Get All Resources
Write-Header "TEST 3: GET ALL RESOURCES (PAGINATED)"
Test-Endpoint -Method GET -Endpoint "?page=0&size=10&sort=id,desc" -Description "List all resources (page 0, size 10)"

# Test 4: Get by ID
Write-Header "TEST 4: GET RESOURCE BY ID"
if ($resourceId) {
    Test-Endpoint -Method GET -Endpoint "/$resourceId" -Description "Get resource by ID: $resourceId"
}

# Test 5: Search
Write-Header "TEST 5: SEARCH RESOURCES"
Test-Endpoint -Method GET -Endpoint "/search?term=lecture" -Description "Search for 'lecture'"

# Test 6: Filter by Type
Write-Header "TEST 6: FILTER BY TYPE"
Test-Endpoint -Method GET -Endpoint "/filter/by-type?type=LECTURE_HALL" -Description "Filter by type: LECTURE_HALL"

# Test 7: Filter by Location
Write-Header "TEST 7: FILTER BY LOCATION"
Test-Endpoint -Method GET -Endpoint "/filter/by-location?location=Building" -Description "Filter by location containing 'Building'"

# Test 8: Filter by Capacity
Write-Header "TEST 8: FILTER BY CAPACITY"
Test-Endpoint -Method GET -Endpoint "/filter/by-capacity?capacity=50" -Description "Filter by capacity >= 50"

# Test 9: Filter by Status
Write-Header "TEST 9: FILTER BY STATUS"
Test-Endpoint -Method GET -Endpoint "/filter/by-status?status=ACTIVE" -Description "Filter by status: ACTIVE"

# Test 10: Advanced Search
Write-Header "TEST 10: ADVANCED SEARCH"
Test-Endpoint -Method GET -Endpoint "/advanced-search?type=LAB&minCapacity=20&maxCapacity=50" -Description "Advanced search: LAB type, capacity 20-50"

# Test 11: Get Active Resources
Write-Header "TEST 11: GET ACTIVE RESOURCES"
Test-Endpoint -Method GET -Endpoint "/active" -Description "Get only active resources"

# Test 12: Location Suggestions
Write-Header "TEST 12: LOCATION SUGGESTIONS"
Test-Endpoint -Method GET -Endpoint "/suggestions/locations?prefix=Build" -Description "Get location suggestions starting with 'Build'"

# Test 13: Update Resource
Write-Header "TEST 13: UPDATE RESOURCE (PUT)"
if ($resourceId) {
    $updateBody = @{
        name = "Lecture Hall A101 - Updated"
        type = "LECTURE_HALL"
        capacity = 120
        location = "Building A, Floor 1"
        description = "Updated lecture hall with new projector"
        status = "ACTIVE"
        availableFrom = "08:00:00"
        availableTo = "19:00:00"
    }
    Test-Endpoint -Method PUT -Endpoint "/$resourceId" -Body $updateBody -Description "Update resource ID: $resourceId"
}

# Test 14: Update Status
Write-Header "TEST 14: UPDATE RESOURCE STATUS (PATCH)"
if ($resourceId) {
    Test-Endpoint -Method PATCH -Endpoint "/$resourceId/status?status=OUT_OF_SERVICE" -Description "Change status to OUT_OF_SERVICE"
}

# Test 15: Restore Status
Write-Header "TEST 15: RESTORE RESOURCE STATUS"
if ($resourceId) {
    Test-Endpoint -Method PATCH -Endpoint "/$resourceId/status?status=ACTIVE" -Description "Change status back to ACTIVE"
}

# Test 16: Delete Resource
Write-Header "TEST 16: DELETE RESOURCE"
if ($resourceId) {
    Test-Endpoint -Method DELETE -Endpoint "/$resourceId" -Description "Delete resource ID: $resourceId"
}

# Test 17: Verify Deletion
Write-Header "TEST 17: VERIFY DELETION"
if ($resourceId) {
    try {
        $headers = @{
            "Authorization" = "Bearer $ADMIN_TOKEN"
        }
        Invoke-RestMethod -Uri "$BASE_URL/$resourceId" -Method GET -Headers $headers -ErrorAction Stop
        Write-Error "✗ FAILED - Resource still exists"
        $script:testsFailed++
    }
    catch {
        Write-Success "✓ PASSED - Resource successfully deleted (404)"
        $script:testsPassed++
    }
}

# ============================================
# SUMMARY
# ============================================

Write-Host "`n╔════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║  Test Summary                        ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""
Write-Success "Tests Passed: $testsPassed"
Write-Error "Tests Failed: $testsFailed"
$totalTests = $testsPassed + $testsFailed
Write-Info "Total: $totalTests"
Write-Host ""

if ($testsFailed -eq 0) {
    Write-Success "🎉 All tests passed!"
} else {
    Write-Error "❌ Some tests failed. Check errors above."
}

Write-Host ""
Write-Info "Next Steps:"
Write-Host "1. Start Backend: mvn spring-boot:run"
Write-Host "2. Test with this script: .\TEST_API.ps1"
Write-Host "3. Use frontend: npm run dev"
Write-Host ""
Write-Info "Replace ADMIN_TOKEN with real JWT token from auth module"
Write-Host ""
