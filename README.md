# Backend Coding Task - Accreditation Service

## Overview

This project is a Java-based Accreditation Service built for a financial compliance platform. The service is designed to manage the lifecycle of investor accreditations, ensuring that users can only have one active request and enforcing a strict state machine for document verification and status transitions.

To run the project run the command: ./start-service.sh

---

## 🧩 Features

### ✅ Accreditation Management
- Register new accreditation requests with document metadata (Base64).
- Prevent duplicate active requests (only one PENDING status allowed per user).
- Enforce business rules to block updates on final states like FAILED or EXPIRED.
- Automatically update timestamps for every state change.

### ⚖️ State Machine & Validation
- Validate transitions: only PENDING requests can be moved to CONFIRMED or FAILED.
- Allow CONFIRMED accreditations to be marked as EXPIRED.
- Throw custom business exceptions for illegal transitions or duplicate requests.
---

## 🛠️ Technical Requirements

- **Language:** Java 17+
- **Framework:** Spring Boot 3.4x
- **Persistence:** In-memory (no database)-Spring Data JPA with Hibernate
- **Mapping: MapStruct for clean DTO to Entity conversion
- **Exception Handling:** Centralized using `@ControllerAdvice` and custom exception classes
- **Tests:** Unit tests covering service and controller layers

---

---

### 1. Create Accreditation

- **Endpoint:** `POST /user/accreditation`
- **Headers:**
  - Content-Type: application/json

- **Request JSON:**
```json
{
  "user_id": "user_01",
  "accreditation_type": "BY_NET_WORTH",
  "document": {
    "name": "tax_return_2023.pdf",
    "mime_type": "application/pdf",
    "content": "S0NPTiB0ZXN0ZSBkZSBiYXNlNjQ="
  }
}
```
Status: `201 CREATED`
### 2. Update User Accreditations

- **Endpoint:** `PUT /user/accreditation/{id}`
- **Headers:**
  - Content-Type: application/json

- **Request JSON:**
```json
{
  "status": "CONFIRMED"
}
```
Status: `200 OK`

### 3. Get User Accreditations

- **Endpoint:** `GET /user/{userId}/accreditation`
- **Headers:**
  - Content-Type: application/json

- **Response JSON:**
```json
{
    "user_id": "user_01",
    "accreditation_statuses": {
        "1": {
            "accreditation_type": "BY_NET_WORTH",
            "status": "CONFIRMED"
        }
    }
}
```
## 🧪 Testing

Unit tests are implemented using JUnit and Mockito and cover:

- ✅ **AccreditationService:**:
  - Validating "Single Pending" rule (409 Conflict scenario).
  - Enforcing state machine transitions (blocking FAILED -> CONFIRMED).
  - Logic for retrieving and mapping accreditation history.
- ✅ **AccreditationController:**:
  - Validating REST contract and Path Variables.
  - Integration with the Global Exception Handler for 400, 404, and 409 errors.
  - Verifying JSON response structure for history listing and creation.
---

