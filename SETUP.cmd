@echo off
REM Start Test Script for Member 1 Module

echo.
echo ========================================
echo Member 1 Module - Quick Setup
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 21+ and add to PATH
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven 3.9.8+ and add to PATH
    pause
    exit /b 1
)

echo ✓ Java found
echo ✓ Maven found
echo.

REM Navigate to project
cd "d:\PAF PROJECT\PAF-Assignment"

REM Build backend
echo.
echo [STEP 1] Building Backend...
echo ========================================
cd backend
call mvn clean install -DskipTests -q
if errorlevel 1 (
    echo ERROR: Backend build failed
    pause
    exit /b 1
)
echo ✓ Backend build successful
echo.

REM Install frontend dependencies
echo [STEP 2] Installing Frontend Dependencies...
echo ========================================
cd ..\frontend
call npm install -q
if errorlevel 1 (
    echo ERROR: npm install failed
    pause
    exit /b 1
)
echo ✓ Frontend dependencies installed
echo.

REM Show next steps
echo.
echo ========================================
echo Setup Complete! Ready to Run
echo ========================================
echo.
echo NEXT STEPS - Open 3 Terminal Windows:
echo.
echo Terminal 1 - Backend Server:
echo   cd "d:\PAF PROJECT\PAF-Assignment\backend"
echo   mvn spring-boot:run
echo.
echo Terminal 2 - Frontend Dev Server:
echo   cd "d:\PAF PROJECT\PAF-Assignment\frontend"
echo   npm run dev
echo.
echo Terminal 3 - Testing (optional):
echo   cd "d:\PAF PROJECT\PAF-Assignment"
echo   [Use curl or PowerShell to test API]
echo.
echo Then open: http://localhost:5173 in your browser
echo.
echo Backend API available at: http://localhost:8080/api/member1/resources
echo.
pause
