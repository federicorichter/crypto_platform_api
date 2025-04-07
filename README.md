# Crypto Trading Platform - Backend API

This is a Java Spring Boot backend for a cryptocurrency trading platform. The API supports user authentication with optional two-factor authentication (2FA), and provides access to coin data including market charts, search, rankings, and trading information.

---

## 📦 Features

- ✅ User registration and login with JWT-based authentication
- 🔐 Two-Factor Authentication (2FA) via email OTP
- 📊 Crypto coin listing, search, market charts, and top rankings
- 🔎 Coin detail and trending coin endpoints
- ⚙️ Built with Spring Boot, Spring Security, and JPA

---

## 🧱 Tech Stack

- Java 17+
- Spring Boot
- Spring Security (with JWT)
- JPA / Hibernate
- Mail API (for OTP email)
- External API integration (CoinGecko)

---

## 🔑 Authentication

- **Signup**: `POST /auth/signup`
- **Signin**: `POST /auth/signin`
- **2FA OTP Verification**: `POST /auth/two-factor/otp/{otp}?id=SESSION_ID`

---

## 💰 Coin Endpoints

- `GET /coins/trading`  
  Get trending coins.

- `GET /coins/top50`  
  Get top 50 coins by market cap.

- `GET /coins/search?q=bitcoin`  
  Search for coins by name.

- `GET /coins/{coinId}/chart?days=30`  
  Get market chart data for a coin.

- `GET /coins/details/{coinId}`  
  Get full coin details.

---

## 🧾 Order Endpoints

- `POST /api/orders/pay`  
  Create and pay a new order for a specific cryptocurrency.  
  **Headers:** `Authorization: Bearer <jwt>`  
  **Body Example:**  
  ```json
  {
    "coinId": 1,
    "quantity": 0.5,
    "orderType": "BUY"
  }


- `GET /api/orders/{orderId}` 
Retrieve a specific order by its ID, if it belongs to the user.
**Headers**: Authorization: Bearer <jwt>

- `GET /api/orders`
Retrieve all orders made by the authenticated user
**Headers**: Authorization: Bearer <jwt>

--- 
## 👛 Wallet Endpoints

- `GET /api/wallet`
Retrieve the wallet and balance information for the authenticated user.

- `PUT /api/wallet/{walletId}/transfer`
Transfer funds from the authenticated user's wallet to another wallet.

- `PUT /api/wallet/order/{orderId}/pay`
Pay for an existing order using the authenticated user's wallet balance.

--- 
## 🛠️ Setup & Run

### Requirements

- Java 17+
- Maven
- PostgreSQL / MySQL (or another DB if configured)
- Mail service credentials (for sending OTPs)

### Steps

```bash
# Clone the repository
git clone https://github.com/yourusername/crypto-trading-backend.git
cd crypto-trading-backend

# Build and run
./mvnw spring-boot:run

