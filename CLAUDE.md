# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.5.6 application written in Kotlin 1.9.25, using Java 17. The project implements OAuth2 authentication with multiple providers (Google, Github) and uses a dual-database architecture with MySQL (JPA) and MongoDB.

## Build & Development Commands

### Build
```bash
./gradlew build
```

### Run Application
```bash
./gradlew bootRun
```

### Run Tests
```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "com.frost.lecture.LectureApplicationTests"

# Run tests with specific pattern
./gradlew test --tests "*AuthService*"
```

### Clean Build
```bash
./gradlew clean build
```

## Architecture & Code Organization

### Package Structure
- `com.frost.lecture.common/` - Shared components (exceptions, HTTP clients)
- `com.frost.lecture.config/` - Spring configuration beans (OAuth2, OkHttp)
- `com.frost.lecture.domains/` - Domain-driven design modules (e.g., `auth/`)
- `com.frost.lecture.interfaces/` - Kotlin interfaces defining contracts
- `com.frost.lecture.types/` - Data structures (entities, DTOs, messages)

### OAuth2 Architecture

The project uses a **provider-agnostic OAuth2 architecture**:

1. **Interface-Based Design**: All OAuth providers implement `OAuthServiceInterface` in `interfaces/OAuth.kt`:
   - `getToken(code: String): OAuth2TokenResponse` - Exchange auth code for access token
   - `getUserInfo(accessToken: String): OAuth2UserResponse` - Fetch user profile
   - `providerName: String` - Provider identifier

2. **Provider Registration**: OAuth services are registered as Spring beans using the provider name as the bean qualifier:
   ```kotlin
   @Service("google")
   class GoogleAuthService(private val config: OAuth2Config) : OAuthServiceInterface

   @Service("github")
   class GithubAuthService(private val config: OAuth2Config) : OAuthServiceInterface
   ```

3. **Configuration**: OAuth2 credentials are loaded from `application.yml` via `OAuth2Config`:
   ```yaml
   oauth2:
     providers:
       google:
         clientId: "..."
         clientSecret: "..."
         redirectUri: "..."
   ```

4. **Adding New Providers**: To add a new OAuth provider:
   - Create a service class in `domains/auth/service/` implementing `OAuthServiceInterface`
   - Annotate with `@Service("provider-name")`
   - Inject `OAuth2Config` and retrieve provider config using the same key
   - Implement token exchange and user info retrieval
   - Add provider config to `application.yml`

### HTTP Client Architecture

The project uses **OkHttp3** for external HTTP calls:

- **Configuration**: `OkHttpClientConfig.kt` configures a singleton `OkHttpClient` bean with 10-second timeouts
- **Wrapper**: `CallClient.kt` provides simplified GET/POST methods with automatic error handling
- **Error Logic**: HTTP responses are validated in `resultHandler()` - **NOTE**: There's a bug at line 31-33 where `isSuccessful` throws an exception instead of handling errors

### Exception Handling

Custom exception system using enum-based error codes:

- `CustomException` - RuntimeException wrapper taking `CodeInterface` and optional additional message
- `ErrorCode` enum - Defines error codes and messages (e.g., `AUTH_CONFIG_NOT_FOUND`, `FAILED_TO_CALL_CLIENT`)
- Pattern: `throw CustomException(ErrorCode.AUTH_CONFIG_NOT_FOUND, "provider-name")`

### Data Model

**User-Account Relationship** (JPA entities):
- `User` entity: Represents OAuth user with platform, username, access_token
  - Primary key: `ulId` (12-char ULID)
  - One-to-many relationship with `Account`
- `Account` entity: Represents user's financial account with balance
  - Primary key: `ulId` (12-char ULID)
  - Many-to-one lazy-loaded relationship to `User`
  - Uses `BigDecimal` for monetary precision

## Key Dependencies

- **Spring Boot Starter**: web, validation, data-jpa, data-mongodb
- **Kotlin**: kotlin-reflect, kotlin-stdlib-jdk8
- **Database**: MySQL Connector 8.0.33, Spring Data JPA, Spring Data MongoDB
- **HTTP Client**: OkHttp 4.12.0
- **Testing**: JUnit 5 (JUnit Platform Launcher)

## Development Notes

### Current Implementation Status
- OAuth2 services (GoogleAuthService, GithubAuthService) have method stubs with `TODO("Not yet implemented")`
- AuthService.kt file exists but is empty
- CallClient has a logic bug in resultHandler (line 31-33) where successful responses throw exceptions

### Kotlin Conventions in This Project
- Use `data class` for entities and DTOs
- Use `interface` for contracts (not abstract classes)
- Use `const val` for provider keys (e.g., `private const val key = "google"`)
- Use constructor injection for dependencies
- Use nullable types (`?`) only when truly optional (e.g., `email: String?`)

### Database Configuration
This project uses two databases:
- **MySQL** via JPA for relational data (User, Account entities)
- **MongoDB** via Spring Data MongoDB (usage not yet implemented)

Ensure both database connections are configured in `application.yml` before running.