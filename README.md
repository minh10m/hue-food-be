🚀 Online Food Ordering – Clean Architecture (Spring Boot + Docker + Redis + PostgreSQL)

Online Food Ordering là hệ thống đặt đồ ăn tương tự GrabFood/Foodpanda, xây dựng theo Clean Architecture, tách biệt rõ Domain – Use Cases – Adapters – Infrastructure.
Hệ thống hỗ trợ:

👤 Quản lý người dùng (Auth, JWT, Forgot Password)

🍽 Nhà hàng (Admin)

🍛 Món ăn (Food)

📦 Đơn hàng (Order)

🛒 Giỏ hàng (Cart)

❤️ Nhà hàng yêu thích

📬 Email OTP Reset Password

⚡ Redis cache

🐘 PostgreSQL (Neon / Local)

🐳 Docker Compose

📐 Kiến trúc tổng quan

Clean Architecture của dự án tuân thủ nguyên tắc:

domain/
 ├── model/
 ├── ports/
 │     ├── in/
 │     └── out/
 └── services/ (use-case)

adapters/
 ├── web/ (REST Controllers + DTO + WebMapper)
 ├── persistence/ (JPA Entities + SpringData Repository)
 ├── security/ (JWT Filter, AuthUtils, Custom UserDetails)
 └── email/ (EmailAdapter)

infrastructure/
 ├── config/ (SecurityConfig, JwtConfig, RedisConfig, OpenAPI)
 └── third-party/

application/
 └── OnlineFoodOrderingApplication.java


✔️ Domain thuần Java, không phụ thuộc Spring
✔️ UseCases xử lý logic, không biết Controller hay DB
✔️ Adapters đóng vai trò cầu nối → mapping domain ↔ web ↔ DB
✔️ Configuration tách riêng

🧱 Công nghệ sử dụng
Thành phần	Công nghệ
Backend	Spring Boot 3
Dữ liệu	PostgreSQL / NeonDB
Cache	Redis
Token	JWT (Access + Refresh)
Migration	Flyway
Container	Docker / Docker Compose
Mapping	MapStruct hoặc tự viết WebMapper
Build	Maven / Gradle
Auth	Spring Security + JWT + AuthPrincipal
🐳 Chạy bằng Docker
1. Tạo file .env
DATASOURCE_URL=jdbc:postgresql://your-db-url:5432/neondb
DATASOURCE_USER=postgres
DATASOURCE_PASSWORD=your_password
JWT_SECRET_KEY=your_jwt_key_64_chars
JWT_ACCESS_TOKEN_EXPIRATION=86400000
JWT_REFRESH_TOKEN_EXPIRATION=604800000
MAIL_USERNAME=your_gmail@gmail.com
MAIL_PASSWORD=your_gmail_app_password
MAIL_HOST=smtp.gmail.com
STRIPE_API_KEY=sk_test_xxx

2. Chạy Docker Compose
docker compose up -d


Docker sẽ chạy:

Redis

RedisInsight

PostgreSQL (optional)

Backend Spring Boot

🗂 Migration Database với Flyway
1. Tạo file migration

src/main/resources/db/migration/V1__init_schema.sql

Ví dụ:

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255),
  role VARCHAR(50),
  enabled BOOLEAN DEFAULT TRUE
);

2. Seed data:

V2__seed_data.sql

INSERT INTO users (email, password, full_name, role)
VALUES ('admin@gmail.com', '$2a$10$abcdef...', 'Admin', 'ADMIN');


Flyway chạy tự động khi khởi động.

🔐 Authentication & Authorization

Access Token (15m – 1d tuỳ config)

Refresh Token (2–14 days)

AuthPrincipal trả về userId, không cần query DB

JwtAuthenticationFilter đặt email, role vào SecurityContext

📬 Forgot Password Flow (Clean Architecture)

/api/auth/forgot-password → gửi OTP email

/api/auth/forgot-password/verify-otp

/api/auth/forgot-password/reset-password

OTP được lưu qua ForgotPasswordRepository, xử lý bởi:

RequestForgotPasswordUseCase

VerifyOtpUseCase

ResetPasswordUseCase

📦 API chính
Authentication
POST /api/auth/signup
POST /api/auth/login
POST /api/auth/refresh
POST /api/auth/logout/{userId}

User
GET /api/users/{id}
PUT /api/users/{id}

Restaurant (Admin)
POST /api/admin/restaurants
PUT  /api/admin/restaurants/{id}
PATCH /api/admin/restaurants/{id}/status

Food (Admin)
POST /api/admin/restaurants/{restaurantId}/foods
PUT  /api/admin/restaurants/{restaurantId}/foods/{foodId}
PATCH /api/admin/restaurants/{restaurantId}/foods/{foodId}/availability

Food (Public)
GET /api/foods/{id}
GET /api/foods?restaurantId=1&categoryId=2

Cart
GET    /api/cart
POST   /api/cart/items
PUT    /api/cart/items/{itemId}
DELETE /api/cart/items/{itemId}

Orders
POST   /api/orders
GET    /api/orders
PATCH  /api/orders/{id}/cancel

❤️ Favorite Restaurants
POST /api/user/favorites/restaurants
DELETE /api/user/favorites/restaurants/{id}
GET /api/user/favorites/restaurants

🌐 OpenAPI / Swagger

Khi bật springdoc-openapi:

/swagger-ui/index.html

🧪 Postman Collection

File Postman đã được generate:

📦 DOWNLOAD:
→ (Bạn gửi mình yêu cầu, mình generate ra file JSON để import Postman ngay)

🛠 Build & Run Local
Chạy bằng Maven
mvn spring-boot:run

Chạy bằng Gradle
./gradlew bootRun

🧰 Cấu trúc thư mục (rút gọn)
src/
 ├─ domain/
 │   ├─ model/
 │   ├─ ports/
 │   └─ services/
 ├─ adapters/
 │   ├─ web/
 │   ├─ persistence/
 │   ├─ security/
 │   └─ email/
 ├─ infrastructure/
 │   └─ config/
 └─ OnlineFoodOrderingApplication.java

🎯 Mục tiêu Clean Architecture đạt được

✔ Domain thuần Java – dễ test
✔ UseCase độc lập – không phụ thuộc framework
✔ Adapter dễ thay thế (JPA → MongoDB / Redis / API ngoài)
✔ Low-coupling – High-cohesion
✔ Dễ mở rộng module (payment, promotion, delivery tracking)

📄 License

MIT License — sử dụng thoải mái cho mục đích học tập & thương mại.
