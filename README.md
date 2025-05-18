# 🎬 Show Time

**Show Time** is a full-stack video streaming platform built with **Spring Boot (backend)** and **React (frontend)**. It includes functionality for user authentication (with JWT), secure video uploads by admins, HTTP Live Streaming (HLS) (.m3u8) methods, and chunk-based video streaming. The app is protected with role-based access and supports CORS for frontend integration.

---

## 🚀 Features

- ✅ JWT-based Authentication
- 🔐 Role-based Authorization (ADMIN / USER)
- 📼 Video Upload by Admins
- 🔁 HLS Video Streaming (`.m3u8` and `.ts`)
- ⏩ Range-based video streaming (chunked)
- 🧭 Protected routes in frontend using React Router
- ⚙️ Spring Security Integration
- 🧩 CORS Configuration

---

## 🛠️ Tech Stack

### 🔙 Backend
- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- HLS Streaming
- Maven

### 🔜 Frontend
- React (with React Router)
- Axios
- HTML5 Video Player (custom)
- HLS.js for `.m3u8` playback

---
## 🧩 Backend API Endpoints

### 🔐 Authentication

| Method | Endpoint         | Description                | Auth Required | Role       |
|--------|------------------|----------------------------|---------------|------------|
| POST   | `/auth/login`    | Authenticates user and returns JWT | ❌ No       | Any        |

---

### 📁 Video Upload (Admin Only)

| Method | Endpoint           | Description              | Auth Required | Role       |
|--------|--------------------|--------------------------|---------------|------------|
| POST   | `/admin/upload`    | Upload a new video       | ✅ Yes        | `ROLE_ADMIN` |

> `multipart/form-data` with field: `file`

---

### 📼 Video Streaming (Non-HLS)

| Method | Endpoint                       | Description                           | Auth Required | Role |
|--------|--------------------------------|---------------------------------------|---------------|------|
| GET    | `/stream/video/{id}`           | Stream entire video file              | ✅ Yes        | Any  |
| GET    | `/stream/video/range/{videoId}`| Stream video using HTTP range requests| ✅ Yes        | Any  |

---

### 🛰️ HLS Streaming (for .m3u8)

| Method | Endpoint                                     | Description                         | Auth Required | Role |
|--------|----------------------------------------------|-------------------------------------|---------------|------|
| GET    | `/hls/video/{videoId}/master.m3u8`           | Serves HLS master playlist          | ✅ Yes        | Any  |
| GET    | `/hls/video/{videoId}/{segment}.ts`          | Serves individual `.ts` segments    | ✅ Yes        | Any  |

---

