
# Installation Guide for Marina.Moda®

This guide will help you set up Marina.Moda® on your local development environment for all supported platforms.

## 📋 Prerequisites

Before you begin, ensure you have the following installed on your system:

### Required Software
- **Git** (2.20+) - [Download Git](https://git-scm.com/downloads)
- **Flutter SDK** (3.0+) - [Download Flutter](https://flutter.dev/docs/get-started/install)
- **Dart SDK** (3.0+) - [Download Dart](https://dart.dev/get-dart)

### Platform-Specific Requirements

#### For Android Development
- **Android Studio** (latest version) - [Download Android Studio](https://developer.android.com/studio)
- **Android SDK** (API level 21+)
- **Java Development Kit (JDK)** 11 or 17

#### For iOS Development (macOS only)
- **Xcode** (latest version) - [Download Xcode](https://developer.apple.com/xcode/)
- **CocoaPods** - `sudo gem install cocoapods`
- **macOS** 10.15 or later

#### For Web Development
- **Chrome** or **Edge** browser (for testing)
- **Node.js** (16.0+) - [Download Node.js](https://nodejs.org/)

## 🚀 Installation Steps

### 1. Clone the Repository

```bash
# Clone the main repository
git clone https://github.com/sorydima/Marina.Moda-.git

# Navigate to the project directory
cd Marina.Moda-
```

### 2. Install Flutter Dependencies

```bash
# Get Flutter packages
flutter pub get

# Verify Flutter installation
flutter doctor
```

**Note**: If `flutter doctor` shows any issues, resolve them before proceeding. Common issues and solutions are listed in the [Troubleshooting](#troubleshooting) section.

### 3. Platform-Specific Setup

#### Android Setup

1. **Open Android Studio** and install the Flutter plugin:
   - Go to `File` → `Settings` → `Plugins`
   - Search for "Flutter" and install it
   - Restart Android Studio

2. **Configure Android SDK**:
   ```bash
   # Open Android Studio
   # Go to Tools → SDK Manager
   # Install Android SDK API level 21 or higher
   ```

3. **Set up Android Virtual Device (AVD)**:
   ```bash
   # In Android Studio, go to Tools → AVD Manager
   # Create a new virtual device
   # Recommended: Pixel 4 with API level 30+
   ```

#### iOS Setup (macOS only)

1. **Install Xcode** from the Mac App Store

2. **Install CocoaPods**:
   ```bash
   sudo gem install cocoapods
   ```

3. **Set up iOS Simulator**:
   ```bash
   # Open Xcode
   # Go to Xcode → Preferences → Components
   # Download and install iOS Simulator
   ```

4. **Install iOS dependencies**:
   ```bash
   cd ios
   pod install
   cd ..
   ```

#### Web Setup

1. **Enable web support** (if not already enabled):
   ```bash
   flutter config --enable-web
   ```

2. **Install web dependencies**:
   ```bash
   flutter pub get
   ```

### 4. Run the Application

#### For Mobile Development

```bash
# Check available devices
flutter devices

# Run on Android
flutter run -d android

# Run on iOS (macOS only)
flutter run -d ios

# Run on connected device
flutter run -d <device-id>
```

#### For Web Development

```bash
# Run in Chrome
flutter run -d chrome

# Run in Edge
flutter run -d edge

# Run in any available browser
flutter run -d web-server
```

### 5. Build for Production

```bash
# Build Android APK
flutter build apk --release

# Build Android App Bundle
flutter build appbundle --release

# Build iOS (macOS only)
flutter build ios --release

# Build Web
flutter build web --release
```

## 🔧 Configuration

### Environment Variables

Create a `.env` file in the root directory:

```bash
# Copy the example environment file
cp .env.example .env

# Edit the file with your configuration
nano .env
```

### API Keys and Services

Configure the following services in your `.env` file:

```env
# Blockchain Configuration
BLOCKCHAIN_NETWORK=mainnet
SMART_CONTRACT_ADDRESS=your_contract_address

# AI Services
AI_API_KEY=your_ai_api_key
AI_SERVICE_URL=https://api.example.com

# Storage Configuration
IPFS_GATEWAY=https://ipfs.io
DECENTRALIZED_STORAGE_KEY=your_storage_key
```

## 🧪 Testing

### Run Tests

```bash
# Run all tests
flutter test

# Run tests with coverage
flutter test --coverage

# Run specific test file
flutter test test/widget_test.dart
```

### Integration Tests

```bash
# Run integration tests
flutter test integration_test/
```

## 📱 Debugging

### Flutter Inspector

```bash
# Enable Flutter Inspector
flutter run --debug
```

### Logging

```bash
# Enable verbose logging
flutter run --verbose
```

## 🚨 Troubleshooting

### Common Issues and Solutions

#### Flutter Doctor Issues

1. **Android SDK not found**:
   ```bash
   # Set ANDROID_HOME environment variable
   export ANDROID_HOME=$HOME/Library/Android/sdk
   export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
   ```

2. **Xcode not found** (macOS):
   ```bash
   # Install Xcode from Mac App Store
   # Accept license agreement
   sudo xcodebuild -license accept
   ```

3. **Flutter version mismatch**:
   ```bash
   # Update Flutter to latest stable
   flutter upgrade
   flutter doctor
   ```

#### Build Issues

1. **Android build fails**:
   ```bash
   # Clean build cache
   flutter clean
   flutter pub get
   
   # Check Android SDK version
   flutter doctor --android-licenses
   ```

2. **iOS build fails**:
   ```bash
   # Clean and reinstall pods
   cd ios
   pod deintegrate
   pod install
   cd ..
   
   # Clean Flutter cache
   flutter clean
   flutter pub get
   ```

3. **Web build issues**:
   ```bash
   # Clear browser cache
   # Check Flutter web support
   flutter config --enable-web
   flutter doctor
   ```

### Getting Help

If you encounter issues not covered in this guide:

1. **Check Flutter documentation**: [flutter.dev](https://flutter.dev/docs)
2. **Search existing issues**: [GitHub Issues](https://github.com/sorydima/Marina.Moda-/issues)
3. **Create a new issue**: Provide detailed error messages and system information
4. **Contact support**: support@rechain.network

## 🔄 Updates

### Update Flutter

```bash
# Update to latest stable version
flutter upgrade

# Switch to specific version
flutter version 3.16.0
```

### Update Dependencies

```bash
# Update all dependencies
flutter pub upgrade

# Update specific package
flutter pub upgrade package_name
```

## 📚 Additional Resources

- [Flutter Documentation](https://flutter.dev/docs)
- [Dart Documentation](https://dart.dev/guides)
- [Marina.Moda® Wiki](MarinaModaWiki.md)
- [Contributing Guidelines](CONTRIBUTING.md)

---

**Need help?** Contact us at support@rechain.network or create an issue on GitHub.
