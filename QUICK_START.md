# Quick Start Reference

## 🚀 Fast Track (5 Minutes)

### Step 1: Build Backend

```bash
cd "d:\PAF PROJECT\PAF-Assignment\backend"
mvn clean install -DskipTests -q
```

### Step 2: Install Frontend

```bash
cd "d:\PAF PROJECT\PAF-Assignment\frontend"
npm install -q
```

### Step 3: Start Backend (Terminal 1)

```bash
cd "d:\PAF PROJECT\PAF-Assignment\backend"
mvn spring-boot:run
# Wait for: "Started SmartCampusHubApplication in X.XXXs"
```

### Step 4: Start Frontend (Terminal 2)

```bash
cd "d:\PAF PROJECT\PAF-Assignment\frontend"
npm run dev
# Shows: "Local: http://localhost:5173"
```

### Step 5: Open Browser

Go to: **http://localhost:5173**

---

## 📋 Command Reference

| Task                 | Command                         |
| -------------------- | ------------------------------- |
| **Build Backend**    | `mvn clean install -DskipTests` |
| **Run Backend**      | `mvn spring-boot:run`           |
| **Build Frontend**   | `npm install`                   |
| **Dev Server**       | `npm run dev`                   |
| **Build Production** | `npm run build`                 |
| **Test Backend**     | `mvn test`                      |
| **Test API**         | `$PSScriptRoot/TEST_API.ps1`    |
| **Check Java**       | `java -version`                 |
| **Check Maven**      | `mvn -version`                  |
| **Check Node**       | `node -v && npm -v`             |

---

## 🌐 API Endpoints (Localhost)

| Method     | Endpoint                                               | Purpose               |
| ---------- | ------------------------------------------------------ | --------------------- |
| **POST**   | `/api/member1/resources`                               | Create resource       |
| **GET**    | `/api/member1/resources`                               | List all (paginated)  |
| **GET**    | `/api/member1/resources/{id}`                          | Get by ID             |
| **GET**    | `/api/member1/resources/active`                        | List active only      |
| **GET**    | `/api/member1/resources/search?term=`                  | Full-text search      |
| **GET**    | `/api/member1/resources/advanced-search?...`           | Multi-filter search   |
| **GET**    | `/api/member1/resources/filter/by-type?type=`          | Filter by type        |
| **GET**    | `/api/member1/resources/filter/by-location?location=`  | Filter by location    |
| **GET**    | `/api/member1/resources/filter/by-capacity?capacity=`  | Filter by capacity    |
| **GET**    | `/api/member1/resources/filter/by-status?status=`      | Filter by status      |
| **GET**    | `/api/member1/resources/suggestions/locations?prefix=` | Location autocomplete |
| **PUT**    | `/api/member1/resources/{id}`                          | Update resource       |
| **PATCH**  | `/api/member1/resources/{id}/status?status=`           | Change status         |
| **DELETE** | `/api/member1/resources/{id}`                          | Delete resource       |

---

## 🧪 Quick Test Examples

### Test with PowerShell

**Create Resource:**

```powershell
$headers = @{ "Authorization" = "Bearer YOUR_TOKEN" }
$body = @{
    name = "Test Room"
    type = "LECTURE_HALL"
    capacity = 100
    location = "Building A"
    status = "ACTIVE"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/member1/resources" `
    -Method POST -Headers $headers -Body $body
```

**List Resources:**

```powershell
$headers = @{ "Authorization" = "Bearer YOUR_TOKEN" }
Invoke-RestMethod -Uri "http://localhost:8080/api/member1/resources?page=0&size=10" `
    -Method GET -Headers $headers | ConvertTo-Json
```

**Search:**

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/member1/resources/search?term=lecture" `
    -Method GET -Headers $headers | ConvertTo-Json
```

---

## 🔑 Key Files & Locations

```
PAF-Assignment/
├── backend/
│   ├── src/main/java/com/smartcampus/hub/
│   │   ├── controller/
│   │   │   └── MemberOneResourceController.java         (14 endpoints)
│   │   ├── service/
│   │   │   ├── MemberOneResourceService.java            (Interface)
│   │   │   └── impl/MemberOneResourceServiceImpl.java    (Implementation)
│   │   ├── repository/
│   │   │   └── MemberOneResourceRepository.java         (8+ queries)
│   │   ├── model/
│   │   │   └── MemberOneResource.java                   (JPA Entity)
│   │   ├── dto/
│   │   │   ├── MemberOneResourceRequestDTO.java         (Validation)
│   │   │   └── MemberOneResourceResponseDTO.java
│   │   └── enums/
│   │       ├── MemberOneResourceType.java
│   │       └── MemberOneResourceStatus.java
│   └── pom.xml                                          (Dependencies)
│
├── frontend/
│   ├── src/
│   │   ├── components/MemberOne/
│   │   │   ├── ResourceCard.jsx
│   │   │   ├── ResourceForm.jsx
│   │   │   └── FilterBar.jsx
│   │   ├── pages/MemberOne/
│   │   │   └── ResourceListPage.jsx
│   │   ├── services/
│   │   │   └── memberOneResourceService.js              (HTTP Client)
│   │   └── App.jsx
│   └── package.json
│
├── TESTING_GUIDE.md                                     (Comprehensive guide)
├── MEMBER1_DOCUMENTATION.md                             (API specs)
├── TEST_API.ps1                                         (PowerShell tests)
├── SETUP.cmd                                            (Auto setup)
└── README.md                                            (Project info)
```

---

## 🛠️ Configuration

### Backend Database

**File:** `backend/src/main/resources/application.properties`

Default:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartcampus_db
spring.datasource.username=root
spring.datasource.password=
server.port=8080
```

### Frontend API Base

**File:** `frontend/src/services/memberOneResourceService.js`

```javascript
const BASE_URL = "http://localhost:8080/api/member1/resources";
```

---

## ✅ Verification Checklist

After startup, verify:

- [ ] Backend compiles without errors
- [ ] Backend starts on port 8080
- [ ] Frontend npm install succeeds
- [ ] Frontend serves on port 5173
- [ ] Can access http://localhost:5173
- [ ] Resource list page loads
- [ ] Can create resource (admin)
- [ ] Can search resources
- [ ] Can filter by type/location
- [ ] Pagination works
- [ ] No console errors

---

## 🐛 Troubleshooting

| Error               | Solution                                         |
| ------------------- | ------------------------------------------------ |
| Port 8080 in use    | Change `server.port` in `application.properties` |
| Port 5173 in use    | Run `npm run dev -- --port 3000`                 |
| DB connection error | Check MySQL running, verify credentials          |
| Java not found      | Install Java 21+ and add to PATH                 |
| Maven not found     | Install Maven 3.9.8+ and add to PATH             |
| npm not working     | Install Node.js 18+ and npm 9+                   |
| API 404 errors      | Ensure backend on 8080, check URL                |
| CORS errors         | Verify CORS config in `SecurityConfig.java`      |

---

## 📊 Resource Types

```
LECTURE_HALL     - Classroom for lectures
LAB              - Laboratory/practical room
MEETING_ROOM     - Conference/meeting space
EQUIPMENT        - Equipment/machinery
```

## 📊 Resource Status

```
ACTIVE           - Available for use
OUT_OF_SERVICE   - Maintenance/unavailable
```

---

## 🎯 Next Steps

1. ✅ Build and run backend
2. ✅ Build and run frontend
3. ✅ Test with PowerShell script
4. ✅ Create sample resources
5. ✅ Test search and filters
6. ⏭️ Integrate with Auth Module
7. ⏭️ Setup database backups
8. ⏭️ Deploy to production

---

## 📞 Support

- Backend API logs: Check terminal running `mvn spring-boot:run`
- Frontend logs: Check browser DevTools Console (F12)
- Database: MySQL localhost:3306
- Full docs: See `TESTING_GUIDE.md`
