# Member 1 - Facilities & Assets Catalogue (Resource Management)

## 📋 Overview

Member 1 module implements a complete **Facilities & Assets Catalogue** system for the Smart Campus Operations Hub. This module allows administrators to create, manage, and organize university resources including lecture halls, labs, meeting rooms, and equipment.

---

## 🏗️ Architecture

### Backend (Spring Boot)

```
com.smartcampus.hub
├── controller/
│   └── MemberOneResourceController.java
├── service/
│   ├── MemberOneResourceService.java
│   └── impl/
│       └── MemberOneResourceServiceImpl.java
├── repository/
│   └── MemberOneResourceRepository.java
├── dto/
│   ├── MemberOneResourceRequestDTO.java
│   └── MemberOneResourceResponseDTO.java
├── model/
│   └── MemberOneResource.java
└── enums/
    ├── MemberOneResourceType.java
    └── MemberOneResourceStatus.java
```

### Frontend (React + Tailwind CSS)

```
src/
├── components/
│   └── MemberOne/
│       ├── ResourceCard.jsx
│       ├── ResourceForm.jsx
│       └── FilterBar.jsx
├── pages/
│   └── MemberOne/
│       └── ResourceListPage.jsx
├── services/
│   └── memberOneResourceService.js
└── App.jsx
```

---

## ✅ REST API Endpoints

All endpoints are prefixed with `/api/member1/resources`

### Create Resource (ADMIN ONLY)

```
POST /api/member1/resources
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Room 101",
  "type": "LECTURE_HALL",
  "capacity": 100,
  "location": "Building A, 1st Floor",
  "description": "Large lecture hall with projection equipment",
  "status": "ACTIVE",
  "availableFrom": "08:00",
  "availableTo": "18:00"
}

Response (201 Created):
{
  "id": 1,
  "name": "Room 101",
  "type": "LECTURE_HALL",
  "capacity": 100,
  "location": "Building A, 1st Floor",
  "description": "Large lecture hall with projection equipment",
  "status": "ACTIVE",
  "availableFrom": "08:00",
  "availableTo": "18:00",
  "createdAt": "2026-04-02T10:30:00",
  "updatedAt": "2026-04-02T10:30:00"
}
```

### Get All Resources (PAGINATED)

```
GET /api/member1/resources?page=0&size=10&sortBy=id&sortDirection=desc

Response (200 OK):
{
  "content": [...],
  "currentPage": 0,
  "totalItems": 25,
  "totalPages": 3,
  "hasNext": true,
  "hasPrevious": false
}
```

### Get Resource by ID

```
GET /api/member1/resources/{id}

Response (200 OK):
{
  "id": 1,
  "name": "Room 101",
  ...
}
```

### Search by Keyword

```
GET /api/member1/resources/search?term=lecture&page=0&size=10

Response (200 OK):
{
  "searchTerm": "lecture",
  "content": [...],
  "totalItems": 8,
  "totalPages": 1,
  "currentPage": 0
}
```

### Advanced Search with Filters

```
GET /api/member1/resources/advanced-search
  ?type=LECTURE_HALL
  &status=ACTIVE
  &location=Building A
  &minCapacity=50
  &maxCapacity=200
  &term=room
  &page=0
  &size=10

Response (200 OK):
{
  "filters": {
    "type": "LECTURE_HALL",
    "status": "ACTIVE",
    "location": "Building A",
    "minCapacity": 50,
    "maxCapacity": 200,
    "term": "room"
  },
  "content": [...],
  "totalItems": 3,
  "totalPages": 1,
  "currentPage": 0
}
```

### Filter by Type

```
GET /api/member1/resources/filter/by-type?type=LECTURE_HALL&page=0&size=10

Types: LECTURE_HALL, LAB, MEETING_ROOM, EQUIPMENT
```

### Filter by Location

```
GET /api/member1/resources/filter/by-location?location=Building A&page=0&size=10
```

### Filter by Capacity (Minimum)

```
GET /api/member1/resources/filter/by-capacity?capacity=50&page=0&size=10
```

### Filter by Status

```
GET /api/member1/resources/filter/by-status?status=ACTIVE&page=0&size=10

Statuses: ACTIVE, OUT_OF_SERVICE
```

### Get Active Resources Only

```
GET /api/member1/resources/active

Response (200 OK):
{
  "count": 15,
  "resources": [...]
}
```

### Get Location Suggestions

```
GET /api/member1/resources/suggestions/locations?prefix=Building

Response (200 OK):
{
  "suggestions": ["Building A", "Building B", "Building C"]
}
```

### Update Resource (ADMIN ONLY)

```
PUT /api/member1/resources/{id}
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Room 101 - Updated",
  "type": "LECTURE_HALL",
  "capacity": 120,
  ...
}

Response (200 OK): Updated resource
```

### Update Resource Status (ADMIN ONLY)

```
PATCH /api/member1/resources/{id}/status?status=OUT_OF_SERVICE
Authorization: Bearer <token>

Response (200 OK): Updated resource
```

### Delete Resource (ADMIN ONLY)

```
DELETE /api/member1/resources/{id}
Authorization: Bearer <token>

Response (200 OK):
{
  "message": "Resource deleted successfully",
  "id": 1
}
```

---

## 🗂️ Database Schema

### Table: member_one_resources

```sql
CREATE TABLE member_one_resources (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  type ENUM('LECTURE_HALL', 'LAB', 'MEETING_ROOM', 'EQUIPMENT') NOT NULL,
  capacity INT NOT NULL,
  location VARCHAR(100) NOT NULL,
  description TEXT,
  status ENUM('ACTIVE', 'OUT_OF_SERVICE') NOT NULL,
  available_from TIME,
  available_to TIME,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## 📊 Entity Details

### MemberOneResource Entity

| Field         | Type          | Constraints        | Description              |
| ------------- | ------------- | ------------------ | ------------------------ |
| id            | Long          | PK, Auto-increment | Resource ID              |
| name          | String        | NOT NULL, UNIQUE   | Resource name            |
| type          | Enum          | NOT NULL           | Type of resource         |
| capacity      | Integer       | NOT NULL           | Maximum capacity         |
| location      | String        | NOT NULL           | Physical location        |
| description   | String        | Optional           | Resource description     |
| status        | Enum          | NOT NULL           | ACTIVE or OUT_OF_SERVICE |
| availableFrom | LocalTime     | Optional           | Availability start time  |
| availableTo   | LocalTime     | Optional           | Availability end time    |
| createdAt     | LocalDateTime | Auto-set           | Creation timestamp       |
| updatedAt     | LocalDateTime | Auto-set           | Last update timestamp    |

---

## 🎨 Frontend Features

### 1. Resource List Page

- Display all resources in a responsive card grid
- Pagination (5, 10, 20, 50 items per page)
- Sorting by different fields
- Real-time loading states
- Empty state handling

### 2. Search Functionality

- Search resources by name
- Real-time search results
- Clear search button

### 3. Advanced Filtering

- Filter by resource type
- Filter by status (Active/Out of Service)
- Filter by location
- Filter by capacity range (min/max)
- Combine multiple filters
- Clear all filters button
- Active filters badge display

### 4. Resource Management (Admin Only)

- Create new resource via modal form
- Edit existing resource
- Delete resource with confirmation
- Form validation on client side
- Success/error notifications
- Loading states

### 5. Resource Card Display

- Resource name and ID
- Type icon and display name
- Status badge with color coding
- Location information
- Capacity details
- Availability hours
- Created/Updated timestamps
- Edit and Delete buttons (admin only)

### 6. User Experience

- Tailwind CSS styling
- Responsive grid layout
- Loading spinners
- Toast notifications
- Form validation with error messages
- Modal overlays for forms
- Confirmation dialogs for delete actions

---

## 🔐 Security Features

### Authentication & Authorization

- CORS enabled for frontend (http://localhost:3000)
- Role-based access control (ADMIN role)
- Endpoint-level security with @PreAuthorize
- Only ADMIN can:
  - Create resources (POST)
  - Update resources (PUT)
  - Update resource status (PATCH)
  - Delete resources (DELETE)

### Validation

- Server-side validation using @Valid
- Custom validation rules:
  - Name: 3-100 characters, unique
  - Capacity: 1-500
  - Location: required, 3-100 characters
  - Availability times: from must be before to
- Error responses with detailed messages

---

## 🚀 Running the Application

### Prerequisites

- Java 21+
- Spring Boot 3.x
- MySQL 8.0+
- Node.js 16+
- React 18+

### Backend Setup

1. **Database Configuration** (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_campus_hub
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

2. **Build and Run**

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Server runs on `http://localhost:8080`

### Frontend Setup

1. **Install Dependencies**

```bash
cd frontend
npm install
```

2. **Configure API Base URL**
   Edit `src/services/memberOneResourceService.js`:

```javascript
const API_BASE_URL = "http://localhost:8080/api/member1/resources";
```

3. **Run Development Server**

```bash
npm run dev
```

Application runs on `http://localhost:3000`

---

## 📝 Data Models

### MemberOneResourceType Enum

- `LECTURE_HALL` - 🎓 Lecture Hall
- `LAB` - 🔬 Laboratory
- `MEETING_ROOM` - 👥 Meeting Room
- `EQUIPMENT` - ⚙️ Equipment

### MemberOneResourceStatus Enum

- `ACTIVE` - ✓ Active
- `OUT_OF_SERVICE` - ✗ Out of Service

---

## 🧪 Testing

### Sample REST Requests

_Create Resource:_

```bash
curl -X POST http://localhost:8080/api/member1/resources \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "name": "Lab 01",
    "type": "LAB",
    "capacity": 40,
    "location": "Building B, Ground Floor",
    "status": "ACTIVE"
  }'
```

_Search Resources:_

```bash
curl http://localhost:8080/api/member1/resources/search?term=lecture&page=0&size=10
```

_Advanced Filter:_

```bash
curl "http://localhost:8080/api/member1/resources/advanced-search?type=LECTURE_HALL&status=ACTIVE&minCapacity=50"
```

---

## 📋 Validation Rules

### Create/Update Resource

- **Name**: Required, 3-100 chars, unique
- **Type**: Required (LECTURE_HALL|LAB|MEETING_ROOM|EQUIPMENT)
- **Capacity**: Required, 1-500
- **Location**: Required, 3-100 chars
- **Description**: Optional, max 500 chars
- **Status**: Required (ACTIVE|OUT_OF_SERVICE)
- **availableFrom**: Optional, time format HH:mm
- **availableTo**: Optional, must be after availableFrom

---

## 🎯 Endpoints Summary

| HTTP Method | Endpoint                                       | Purpose              | Auth Required |
| ----------- | ---------------------------------------------- | -------------------- | ------------- |
| POST        | `/api/member1/resources`                       | Create resource      | ADMIN         |
| GET         | `/api/member1/resources`                       | Get all (paginated)  | No            |
| GET         | `/api/member1/resources/{id}`                  | Get by ID            | No            |
| GET         | `/api/member1/resources/active`                | Get active only      | No            |
| GET         | `/api/member1/resources/search`                | Search by term       | No            |
| GET         | `/api/member1/resources/advanced-search`       | Advanced filter      | No            |
| GET         | `/api/member1/resources/filter/by-type`        | Filter by type       | No            |
| GET         | `/api/member1/resources/filter/by-location`    | Filter by location   | No            |
| GET         | `/api/member1/resources/filter/by-capacity`    | Filter by capacity   | No            |
| GET         | `/api/member1/resources/filter/by-status`      | Filter by status     | No            |
| GET         | `/api/member1/resources/suggestions/locations` | Location suggestions | No            |
| PUT         | `/api/member1/resources/{id}`                  | Update resource      | ADMIN         |
| PATCH       | `/api/member1/resources/{id}/status`           | Update status        | ADMIN         |
| DELETE      | `/api/member1/resources/{id}`                  | Delete resource      | ADMIN         |

---

## 📦 Dependencies

### Backend

- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- MySQL Connector
- Lombok
- Jakarta Validation
- Hibernate

### Frontend

- React 18+
- Axios (HTTP client)
- React Router DOM
- Tailwind CSS
- Lucide React (Icons)

---

## 🔄 Git Commit History

Member 1 implementation commits:

```
commit: [Feature] Create Member 1 resource entities and enums
commit: [Feature] Create resource DTOs and repository layer
commit: [Feature] Implement resource service layer
commit: [Feature] Create REST controller with 14 endpoints
commit: [Feature] Create React frontend components
commit: [Feature] Implement resource list page and forms
commit: [Feature] Add search and filter functionality
commit: [Feature] Complete Member 1 module implementation
```

---

## 👤 Member 1 Contribution

**Implemented by:** Member 1

**Responsibility:** Facilities & Assets Catalogue Module

**Endpoints Implemented:** 14+ REST endpoints

**Components Created:**

- ResourceCard (display)
- ResourceForm (create/edit)
- FilterBar (search & filter)
- ResourceListPage (main page)

**Features Delivered:**

- Full CRUD operations
- Advanced search and filtering
- Pagination and sorting
- Role-based access control
- Form validation
- Error handling
- Responsive UI design

---

## 📞 Support & Questions

For issues or questions about the Member 1 module:

1. Check error messages in browser console
2. Review database logs
3. Verify API endpoints in Postman collection
4. Check Spring Boot logs on server

---

**Last Updated:** April 2, 2026  
**Status:** ✅ Complete - Ready for Viva Demonstration
