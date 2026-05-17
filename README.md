# SMS Messaging Microservice

A Java microservice built with Quarkus that simulates an SMS messaging platform. Supports sending messages, listing, and searching — with synchronous validation and simulated delivery responses.

---

## Tech Stack

- **Java 17**
- **Quarkus 3.35.2**
- **PostgreSQL 15** — message persistence
- **Hibernate ORM** — entity management
- **SmallRye OpenAPI / Swagger UI** — API documentation
- **Docker & Docker Compose** — containerization

---

## System Design

```
Client
  │
  ▼
MessageResource        (REST layer — validation, routing)
  │
  ▼
MessageService         (Business logic — simulate delivery)
  │
  ▼
MessageRepository      (Data layer — EntityManager / JPQL)
  │
  ▼
PostgreSQL
```

### Message Flow

1. Client sends a `POST /api/messages` request with sender, receiver, and content.
2. `MessageResource` validates the request using Bean Validation (`@Valid`).
3. `MessageService` creates the message, simulates delivery (random DELIVERED / FAILED), persists it, and returns the result synchronously.
4. The response includes the message ID, status, error description (if failed), and timestamps.

### Message Statuses

| Status    | Description                         |
|-----------|-------------------------------------|
| SENDING   | Message accepted, processing        |
| DELIVERED | Successfully delivered              |
| FAILED    | Delivery failed (with error reason) |

---

## Prerequisites

- **Docker Desktop** installed and running
- **Java 17+** and **Maven** (only needed if running without Docker)

---

## Running with Docker Compose (Recommended)

```bash
# Clone the repository
git clone https://github.com/GiannisHadtzizorzis/sms_messaging.git
cd sms_messaging

# Start PostgreSQL
docker-compose up postgres -d

# Run the application in dev mode
./mvnw quarkus:dev
```

The API will be available at `http://localhost:8080`.

To stop PostgreSQL:
```bash
docker-compose down
```

To stop and remove all data:
```bash
docker-compose down -v
```

### Running the full stack with Docker (app + database)

```bash
docker-compose up --build
```

---

## Testing with Swagger UI

Once the application is running, open your browser and navigate to:

```
http://localhost:8080/q/swagger-ui
```

### Step 1 — Send a message

1. Expand the `POST /api/messages` endpoint
2. Click **Try it out**
3. Paste the following request body:
```json
{
  "senderNumber": "99123456",
  "receiverNumber": "25123456",
  "content": "Hello, I am just checking up on you, are you ok?"
}
```
4. Click **Execute**
5. You will receive a `201` response with a `messageId`, `status` (DELIVERED or FAILED), and `sentAt` timestamp
6. Copy the `messageId` from the response for use in the next step

### Step 2 — Get a message by ID

1. Expand `GET /api/messages/{id}`
2. Click **Try it out**
3. Paste the `messageId` from Step 1
4. Click **Execute**

### Step 3 — List all messages

1. Expand `GET /api/messages`
2. Click **Try it out**
3. Click **Execute** — returns all messages in the database

### Validation testing

Try sending an invalid request to see validation in action:
```json
{
  "senderNumber": "12345",
  "receiverNumber": "",
  "content": ""
}
```
You should receive a `400 Bad Request` with descriptive validation errors.

---

## API Endpoints

| Method | Endpoint               | Description              |
|--------|------------------------|--------------------------|
| POST   | `/api/messages`        | Send a new SMS message   |
| GET    | `/api/messages`        | List all messages        |
| GET    | `/api/messages/{id}`   | Get a message by ID      |

### Send Message — Request Body

```json
{
  "senderNumber": "99123456",
  "receiverNumber": "25123456",
  "content": "Hello from the SMS platform!"
}
```

### Validation Rules

| Field            | Rule                                              |
|------------------|---------------------------------------------------|
| `senderNumber`   | Required, valid Cyprus number (`[29]XXXXXXXX`)    |
| `receiverNumber` | Required, valid Cyprus number (`[29]XXXXXXXX`)    |
| `content`        | Required, max 160 characters                      |

Valid number formats: `99123456`, `25123456`, `+35799123456`, `35725123456`

### Send Message — Response (DELIVERED)

```json
{
  "messageId": "2d8e53f5-78f0-420f-a2fc-e551e3904c96",
  "status": "DELIVERED",
  "errorDescription": null,
  "sentAt": "2026-05-17T18:32:59.538542468"
}
```

### Send Message — Response (FAILED)

```json
{
  "messageId": "f3480c59-cc5e-40e2-8a12-f09bfc61535c",
  "status": "FAILED",
  "errorDescription": "Simulated delivery failure",
  "sentAt": "2026-05-17T18:36:01.216071891"
}
```

---

## Project Structure

```
src/
├── main/
│   ├── java/dev/giannishadjizorzis/
│   │   ├── Message.java                  # JPA entity
│   │   ├── MessageStatus.java            # Enum: SENDING, DELIVERED, FAILED
│   │   ├── MessageRepository.java        # JPQL queries via EntityManager
│   │   ├── MessageService.java           # Business logic + delivery simulation
│   │   ├── MessageResource.java          # REST endpoints
│   │   ├── SendMessageRequest.java       # Request DTO with validation
│   │   └── SendMessageResponse.java      # Response DTO
│   └── resources/
│       └── application.properties
├── test/
│   └── java/dev/giannishadjizorzis/
Dockerfile
docker-compose.yml
pom.xml
README.md
```