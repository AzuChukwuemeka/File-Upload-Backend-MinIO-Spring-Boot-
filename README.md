📁 File Upload Backend (MinIO + Spring Boot)
This project provides a simple, S3-style backend for securely uploading, listing, and downloading files. It's designed to help developers learn and prototype file upload logic with a local MinIO container before moving to production blob storage solutions like AWS S3.
🚀 Features
Upload files via octet-stream (not multipart/form-data)

Download files using UUID-based access

Retrieve file metadata

View all files uploaded by a user

Uses MinIO for local object storage (S3-compatible)

Authenticated routes via HTTP Basic Auth

Uses Spring Boot and a custom blob handler

🏗️ Technologies Used
Java + Spring Boot

MinIO (Docker container)

Dotenv for environment variable management

HTTP Basic Authentication

Stream-based upload/download (no multipart)

PostgreSQL or your DB of choice for metadata

📦 API Endpoints
All endpoints except user creation require authentication.

🔐 Auth
Uses HTTP Basic Auth (Authorization: Basic base64(username:password)).

📤 Upload File
POST /api/v1/file/uploadFile
Headers:

filename: Desired name of the file

file-extension: File extension (e.g., .jpg, .pdf)
Body: Raw binary data (application/octet-stream)

Example:

bash
Copy
Edit
curl -X POST http://localhost:8080/api/v1/file/uploadFile \
  -H "filename: document" \
  -H "file-extension: .pdf" \
  -H "Content-Type: application/octet-stream" \
  --data-binary "@path/to/document.pdf" \
  -u user@example.com:password123
📥 Download File
GET /api/v1/file/files/{id}
Downloads a file by its UUID.
Returns the file as a binary stream.

🧾 Get File Metadata
GET /api/v1/file/files/{id}
Returns metadata about the file (name, type, size, etc.)

📚 List User Files
GET /api/v1/file/files/{email}
Lists all files uploaded by the authenticated user.

👤 User Creation
There is a separate controller to register users. Once created, all file endpoints require authentication. Only the authenticated user can see or manage their own files.

⚙️ Running Locally
Start MinIO via Docker:

bash
Copy
Edit
docker run -p 9000:9000 -p 9001:9001 \
  -e "MINIO_ROOT_USER=minioadmin" \
  -e "MINIO_ROOT_PASSWORD=minioadmin" \
  quay.io/minio/minio server /data --console-address ":9001"
Set up .env file:

ini
Copy
Edit
MINIO_ENDPOINT=http://localhost:9000
MINIO_ACCESS_KEY=minioadmin
MINIO_SECRET_KEY=minioadmin
BUCKET_NAME=your-bucket-name
Run the Spring Boot app:

bash
Copy
Edit
./gradlew bootRun
or

bash
Copy
Edit
mvn spring-boot:run
📁 Project Structure
bash
Copy
Edit
└── com.fileuploader.upload
    ├── controllers         # FileUploadController & UserController
    ├── services            # Business logic for storing and retrieving files
    ├── dataclasses         # File metadata class
    ├── utils               # BlobStoreHandler for MinIO/S3 interaction
🧠 Learning Goals
This project is meant as a learning tool and prototype. It helps you:

Understand blob storage interactions using a local setup

Implement stream-based file handling in Java

Get ready for AWS S3 migration by working with MinIO

Explore authentication and metadata separation

