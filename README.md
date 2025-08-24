# Invoices Android App

A modern Android application built with Clean Architecture, Jetpack Compose, and MVVM pattern. This app demonstrates best practices for Android development including proper separation of concerns and comprehensive testing.

## Screenshots
**Display correct invoices**

<img width="270" height="555" alt="Screenshot_20250823_232348" src="https://github.com/user-attachments/assets/1a9f1cc3-02e6-4edd-b8f9-9fbab1041d95" />

**Errors**
| Empty invoice list | Malformed Json | General error (Network issue) |
| --- | --- | --- |
| <img width="270" height="555" alt="Screenshot_20250823_232359" src="https://github.com/user-attachments/assets/9c1b9925-43d8-4026-8438-2c3a56e4a545" /> | <img width="270" height="555" alt="Screenshot_20250823_232411" src="https://github.com/user-attachments/assets/e17131ea-02f7-435a-b2bf-b18fc22e1770" /> | <img width="270" height="555" alt="Screenshot_20250823_232519" src="https://github.com/user-attachments/assets/35c350e2-8301-4ab9-a5f6-dd137369d594" /> |

**Note**: Use the `Retry` button to reload the correct invoice list after viewing error states.

https://github.com/user-attachments/assets/de6724d8-38e1-43ac-93aa-9d6532bf11ef

## Project Structure

```
app/src/main/java/com/example/invoices_android_app/
├── data/
│   ├── InvoiceRepositoryImpl.kt      # Repository implementation
│   ├── InvoiceListResponse.kt        # API response models
│   ├── remote/
│   │   └── InvoiceApiService.kt      # Network API interface
│   └── mapper/
│       └── InvoiceMappers.kt         # Data mapping utilities
├── domain/
│   ├── Invoice.kt                    # Business entities
│   ├── InvoiceRepository.kt          # Business interface
│   └── InvoiceResult.kt              # Operation results
├── presentation/
│   ├── InvoiceViewModel.kt           # ViewModel with UI logic & states
│   └── ui/
│       ├── screens/
│       │   └── InvoiceListScreen.kt  # Main UI screen
│       ├── components/
│       │   └── CommonComponents.kt   # Reusable UI components
│       └── theme/
│           ├── Color.kt              # Color definitions
│           ├── Theme.kt              # App theme configuration
│           └── Type.kt               # Typography definitions
├── di/
│   ├── NetworkModule.kt              # Network dependency injection
│   └── RepositoryModule.kt           # Repository dependency injection
├── utils/
│   └── Extensions.kt                 # Utility extensions
├── InvoicesApplication.kt            # Application class
└── MainActivity.kt                   # Main activity
```

### Key Architectural Benefits

-  **Clean Separation**: Domain, Data, and Presentation layers are clearly separated
-  **Testability**: Each layer can be tested independently
-  **Maintainability**: Clear responsibilities and easy to modify
-  **Scalability**: Easy to add new features without breaking existing code
-  **Dependency Inversion**: Depends on abstractions, not concretions

## Features

### Core Features
- **Invoice List Display**: View and manage invoices with a clean, modern UI
- **Multiple Data Sources**: Demo mode with different API endpoints for testing
- **Error Handling**: Comprehensive error handling with user-friendly messages
- **Responsive Design**: Modern Material Design 3 with Jetpack Compose

### User Experience
- **Loading States**: Clear loading indicators
- **Error States**: User-friendly error messages with retry options
- **Empty States**: Graceful handling of empty data

### Technical Features
- **Clean Architecture**: Clear separation of concerns with domain, data, and presentation layers
- **MVVM Architecture**: Model-View-ViewModel pattern with reactive state management using StateFlow
- **Jetpack Compose**: Modern declarative UI framework
- **Coroutines & Flow**: Asynchronous programming with reactive state management
- **Dependency Injection**: Hilt for dependency management

## Dependencies

### Core Libraries
- **Jetpack Compose**: UI framework
- **ViewModel**: State management
- **Coroutines**: Asynchronous programming
- **Retrofit**: Network requests
- **Coil**: Image loading
- **Gson**: JSON parsing
- **Hilt**: Dependency injection

### Testing Libraries
- **JUnit**: Unit testing
- **MockK**: Mocking framework
- **Compose Testing**: Compose UI testing

## Testing

The project includes comprehensive testing across all layers:

#### Unit Tests
- **ExtensionsTest**: Tests utility functions for date and price formatting
- **InvoiceRepositoryImplTest**: Tests repository implementation and error handling
- **InvoiceViewModelTest**: Tests ViewModel logic and state management

#### Test Features
- ✅ **Comprehensive Testing**: Edge cases, error scenarios, and boundary conditions
- ✅ **Mock Testing**: Uses MockK for dependency mocking
- ✅ **Coroutine Testing**: Proper async testing with `runTest`
- ✅ **Error Testing**: Tests exception handling and error states
- ✅ **Data Mapping**: Tests data transformation between layers

### Running Tests
```bash
# Run all tests
./gradlew test
```

## Getting Started

### Prerequisites
- Android Studio (latest stable)
- JDK 21 (Gradle toolchain supported)
- Android SDK Platform **36** (Android 16)
- Minimum supported: Android 7.0 (API 24)

### Installation
1. Clone the repository
```bash
git clone https://github.com/WenchuanWang/invoices-android-app.git
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Run the app on an emulator or device
