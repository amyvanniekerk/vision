# Vision Android App

A modern Android application built with clean architecture, featuring user authentication, profile management, and easy navigation.

## 🏗️ Architecture

This app follows MVVM pattern with clean architecture principles:

- **ViewModels**: Handle UI state and user interactions
- **Controllers**: Manage business logic and data operations
- **State Classes**: Define UI states, events, and effects
- **Data Classes**: Model application data
- **Repository Pattern**: Abstract data sources
- **Dependency Injection**: Using Hilt for clean dependency management

## 🚀 Features

- ✅ **User Authentication**: Login and registration system
- ✅ **Profile Management**: View and edit user profiles
- ✅ **Clean Navigation**: Easy navigation between screens
- ✅ **State Management**: Reactive state handling with Flow
- ✅ **Modern UI**: Material 3 Design with Jetpack Compose
- ✅ **Error Handling**: Comprehensive error states and user feedback

## 📱 Demo Account

For testing purposes, use:
- **Email**: `test@example.com`
- **Password**: `password`

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Navigation**: Navigation Compose
- **State**: StateFlow & SharedFlow
- **Async**: Coroutines
- **Build**: Gradle with Version Catalogs

## 📂 Project Structure

```
app/src/main/java/com/example/vision/
├── core/
│   ├── base/
│   │   └── BaseViewModel.kt
│   └── navigation/
│       └── VisionNavigation.kt
├── data/
│   ├── model/
│   │   └── User.kt
│   └── repository/
│       └── AuthRepository.kt
├── domain/
│   └── controller/
│       ├── BaseController.kt
│       └── AuthController.kt
├── presentation/
│   ├── screens/
│   │   ├── LoginScreen.kt
│   │   ├── RegisterScreen.kt
│   │   ├── HomeScreen.kt
│   │   └── ProfileScreen.kt
│   ├── state/
│   │   ├── AuthState.kt
│   │   ├── ProfileState.kt
│   │   └── NavigationState.kt
│   └── viewmodel/
│       ├── AuthViewModel.kt
│       └── ProfileViewModel.kt
├── di/
│   └── RepositoryModule.kt
├── VisionApplication.kt
└── MainActivity.kt
```

## 🎯 Key Components

### BaseViewModel
- Generic ViewModel with state, event, and effect management
- Coroutine error handling
- Clean state updates

### BaseController
- Business logic separation from ViewModels
- Coroutine scope management
- State management utilities

### Authentication Flow
- Login/Register screens with validation
- JWT-ready architecture (currently using mock data)
- Persistent user sessions
- Profile management

### Navigation
- Type-safe navigation with Navigation Compose
- Proper back stack management
- Authentication-aware routing

## 🔧 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Vision
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory

3. **Sync Project**
   - Android Studio will automatically sync Gradle
   - If not, click "Sync Project with Gradle Files"

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click "Run" or press Ctrl+R (Cmd+R on Mac)

## 🎨 Customization

### Adding New Screens
1. Create screen composable in `presentation/screens/`
2. Add route to `NavigationState.kt`
3. Update `VisionNavigation.kt` with new route
4. Create ViewModel if needed

### Adding New Features
1. Define data models in `data/model/`
2. Create repository in `data/repository/`
3. Implement controller in `domain/controller/`
4. Create state classes in `presentation/state/`
5. Build ViewModel in `presentation/viewmodel/`
6. Design UI in `presentation/screens/`

## 🧪 Testing

Run tests with:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📋 Development Checklist

- [x] Project setup with dependencies
- [x] Base architecture (ViewModel, Controller)
- [x] Data classes and models
- [x] State management system
- [x] Authentication system
- [x] Login/Register UI
- [x] Profile management
- [x] Navigation setup
- [x] Main activity integration
- [x] Build verification

## 🔄 Next Steps

This foundation is ready for:
- Additional feature pages
- API integration (replace mock repository)
- Local database (Room)
- Push notifications
- Settings screen
- Advanced profile features

## 📄 License

[Add your license here]

---

Built with ❤️ using modern Android development practices