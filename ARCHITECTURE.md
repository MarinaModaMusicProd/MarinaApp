# Marina.Moda® Architecture Documentation

This document provides a comprehensive overview of the technical architecture, system design, and implementation details for Marina.Moda®.

## 📋 Table of Contents

1. [System Overview](#system-overview)
2. [Architecture Principles](#architecture-principles)
3. [High-Level Architecture](#high-level-architecture)
4. [Component Architecture](#component-architecture)
5. [Data Architecture](#data-architecture)
6. [Security Architecture](#security-architecture)
7. [Scalability & Performance](#scalability--performance)
8. [Deployment Architecture](#deployment-architecture)
9. [Technology Stack](#technology-stack)
10. [API Design](#api-design)
11. [Blockchain Integration](#blockchain-integration)
12. [AI/ML Architecture](#aiml-architecture)
13. [Monitoring & Observability](#monitoring--observability)

## 🌟 System Overview

Marina.Moda® is a decentralized music streaming platform built with a microservices architecture that leverages blockchain technology, AI/ML services, and distributed systems to provide a secure, scalable, and user-centric music experience.

### Core Objectives

- **Decentralization**: Eliminate single points of failure and central control
- **Privacy**: User data ownership and control
- **Fairness**: Transparent artist compensation through smart contracts
- **Scalability**: Handle millions of concurrent users and streams
- **Performance**: Low-latency music streaming and real-time interactions

### System Characteristics

- **Distributed**: Multiple nodes across different geographical locations
- **Resilient**: Fault-tolerant with automatic recovery mechanisms
- **Secure**: End-to-end encryption and blockchain-based security
- **Scalable**: Horizontal scaling capabilities for all components
- **Observable**: Comprehensive monitoring and logging

## 🏗️ Architecture Principles

### 1. Decentralization First
- **No Single Point of Control**: Distributed decision-making and governance
- **User Sovereignty**: Users own and control their data
- **Community-Driven**: Platform evolution driven by community consensus

### 2. Privacy by Design
- **Data Minimization**: Collect only necessary data
- **Local Processing**: Process data close to the user when possible
- **Encryption Everywhere**: Encrypt data in transit and at rest

### 3. Performance at Scale
- **Horizontal Scaling**: Scale out rather than up
- **Caching Strategy**: Multi-layer caching for optimal performance
- **Async Processing**: Non-blocking operations for better responsiveness

### 4. Security by Default
- **Zero Trust**: Verify everything, trust nothing
- **Defense in Depth**: Multiple security layers
- **Continuous Security**: Regular security audits and updates

## 🏛️ High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    Marina.Moda® Ecosystem                      │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │
│  │   Mobile    │  │     Web     │  │   Desktop   │           │
│  │   Clients   │  │   Clients   │  │   Clients   │           │
│  └─────────────┘  └─────────────┘  └─────────────┘           │
│           │                │                │                │
│           └────────────────┼────────────────┘                │
│                            │                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              API Gateway & Load Balancer               │   │
│  └─────────────────────────────────────────────────────────┘   │
│                            │                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                    Microservices                       │   │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐     │   │
│  │  │  Music  │ │  User   │ │Community│ │  AI/ML  │     │   │
│  │  │Service  │ │Service  │ │Service  │ │Service  │     │   │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────┘     │   │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐     │   │
│  │  │Blockchain│ │ Storage │ │ Analytics│ │ Streaming│     │   │
│  │  │Service  │ │Service  │ │Service  │ │Service  │     │   │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────┘     │   │
│  └─────────────────────────────────────────────────────────┘   │
│                            │                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Infrastructure Layer                       │   │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐     │   │
│  │  │Database │ │ Message │ │  Cache  │ │  CDN    │     │   │
│  │  │Cluster  │ │  Queue  │ │  Layer  │ │ Network │     │   │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────┘     │   │
│  └─────────────────────────────────────────────────────────┘   │
│                            │                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Blockchain Network                         │   │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐     │   │
│  │  │ Smart   │ │  Token  │ │  NFT    │ │  DAO    │     │   │
│  │  │Contracts│ │Contract │ │Contract │ │Governance│     │   │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────┘     │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## 🔧 Component Architecture

### 1. Client Applications

#### Mobile Applications
- **Platform**: Flutter (iOS & Android)
- **Architecture**: MVVM with Clean Architecture
- **State Management**: Provider/Riverpod
- **Local Storage**: SQLite + Hive
- **Offline Support**: Local caching and sync

#### Web Application
- **Framework**: Flutter Web
- **Architecture**: Component-based with state management
- **PWA Support**: Offline capabilities and app-like experience
- **Responsive Design**: Mobile-first approach

#### Desktop Application
- **Platform**: Flutter Desktop (Windows, macOS, Linux)
- **Native Integration**: Platform-specific features
- **Performance**: Optimized for desktop hardware

### 2. API Gateway

#### Functions
- **Routing**: Route requests to appropriate microservices
- **Authentication**: JWT token validation
- **Rate Limiting**: Prevent API abuse
- **Load Balancing**: Distribute traffic across services
- **Caching**: Response caching for improved performance
- **Monitoring**: Request/response logging and metrics

#### Implementation
- **Technology**: Kong Gateway or AWS API Gateway
- **Configuration**: Declarative configuration management
- **Scaling**: Auto-scaling based on traffic patterns

### 3. Microservices

#### Music Service
```dart
class MusicService {
  // Core music streaming functionality
  Future<StreamResponse> getMusicStream(String trackId, AudioQuality quality);
  Future<List<Track>> searchMusic(String query, SearchFilters filters);
  Future<TrackDetails> getTrackDetails(String trackId);
  Future<void> updatePlayCount(String trackId, String userId);
}
```

#### User Service
```dart
class UserService {
  // User management and authentication
  Future<User> createUser(UserRegistration registration);
  Future<User> authenticateUser(LoginCredentials credentials);
  Future<UserProfile> getUserProfile(String userId);
  Future<void> updateUserProfile(String userId, UserProfileUpdate update);
}
```

#### Community Service
```dart
class CommunityService {
  // Social features and community management
  Future<Post> createPost(PostCreation post);
  Future<List<Post>> getCommunityFeed(FeedFilters filters);
  Future<void> likePost(String postId, String userId);
  Future<List<Comment>> getPostComments(String postId);
}
```

#### Blockchain Service
```dart
class BlockchainService {
  // Blockchain interactions and smart contracts
  Future<Transaction> executeSmartContract(SmartContractCall call);
  Future<TransactionStatus> getTransactionStatus(String txHash);
  Future<List<Transaction>> getUserTransactions(String userId);
  Future<ContractState> getContractState(String contractAddress);
}
```

## 🗄️ Data Architecture

### 1. Data Storage Strategy

#### Primary Database
- **Technology**: PostgreSQL with TimescaleDB extension
- **Purpose**: User data, music metadata, analytics
- **Scaling**: Read replicas and sharding
- **Backup**: Automated daily backups with point-in-time recovery

#### NoSQL Databases
- **MongoDB**: User-generated content, comments, posts
- **Redis**: Caching, session management, real-time data
- **Elasticsearch**: Full-text search and analytics

#### Blockchain Storage
- **IPFS**: Decentralized file storage
- **Ethereum**: Smart contracts and transactions
- **Polygon**: Layer 2 scaling solution

### 2. Data Models

#### User Entity
```dart
class User {
  final String id;
  final String email;
  final String username;
  final String displayName;
  final String? avatar;
  final String? bio;
  final List<String> genres;
  final UserPreferences preferences;
  final DateTime createdAt;
  final DateTime lastActive;
  final UserStatus status;
}
```

#### Track Entity
```dart
class Track {
  final String id;
  final String title;
  final String artistId;
  final String albumId;
  final Duration duration;
  final String genre;
  final List<String> tags;
  final AudioMetadata audioMetadata;
  final TrackStats stats;
  final List<String> ipfsHashes;
  final SmartContractLicense license;
}
```

#### Playlist Entity
```dart
class Playlist {
  final String id;
  final String name;
  final String description;
  final String ownerId;
  final List<String> trackIds;
  final PlaylistPrivacy privacy;
  final List<String> collaboratorIds;
  final PlaylistStats stats;
  final DateTime createdAt;
  final DateTime lastModified;
}
```

### 3. Data Flow

```
User Action → API Gateway → Microservice → Database
     ↓
Cache Layer (Redis) ← Database Query Results
     ↓
Response → API Gateway → Client
```

## 🔐 Security Architecture

### 1. Authentication & Authorization

#### JWT Implementation
```dart
class JWTService {
  static const String _secret = 'your-secret-key';
  static const Duration _expiration = Duration(hours: 24);
  
  static String generateToken(User user) {
    final payload = {
      'userId': user.id,
      'email': user.email,
      'permissions': user.permissions,
      'exp': DateTime.now().add(_expiration).millisecondsSinceEpoch,
    };
    
    return jwt.encode(payload, _secret);
  }
  
  static Map<String, dynamic> verifyToken(String token) {
    try {
      return jwt.decode(token, verify: true, secret: _secret);
    } catch (e) {
      throw AuthenticationException('Invalid token');
    }
  }
}
```

#### Permission System
```dart
enum Permission {
  readMusic,
  writeMusic,
  createPlaylist,
  deletePlaylist,
  moderateContent,
  adminAccess,
}

class PermissionService {
  static bool hasPermission(User user, Permission permission) {
    return user.permissions.contains(permission);
  }
  
  static bool canAccessResource(User user, String resourceId, Permission permission) {
    // Check if user owns the resource or has admin access
    return hasPermission(user, permission) && 
           (user.id == resourceId || hasPermission(user, Permission.adminAccess));
  }
}
```

### 2. Data Encryption

#### Encryption at Rest
- **Database**: AES-256 encryption for sensitive fields
- **File Storage**: Client-side encryption before IPFS upload
- **Backups**: Encrypted backup files

#### Encryption in Transit
- **HTTPS**: TLS 1.3 for all API communications
- **WebRTC**: End-to-end encryption for real-time features
- **Blockchain**: Secure communication with smart contracts

### 3. Privacy Protection

#### Data Minimization
- Collect only necessary user data
- Anonymize analytics data
- Implement data retention policies

#### User Control
- Data export functionality
- Account deletion with data cleanup
- Privacy settings management

## 📈 Scalability & Performance

### 1. Horizontal Scaling

#### Microservice Scaling
```yaml
# Docker Compose with scaling
services:
  music-service:
    image: marinamoda/music-service:latest
    deploy:
      replicas: 3
      resources:
        limits:
          cpus: '1.0'
          memory: 1G
    environment:
      - DATABASE_URL=${DATABASE_URL}
      - REDIS_URL=${REDIS_URL}
```

#### Database Scaling
- **Read Replicas**: Distribute read load
- **Sharding**: Partition data by user ID or region
- **Connection Pooling**: Efficient database connections

### 2. Caching Strategy

#### Multi-Layer Caching
```dart
class CacheService {
  static const Duration _shortTerm = Duration(minutes: 5);
  static const Duration _mediumTerm = Duration(hours: 1);
  static const Duration _longTerm = Duration(hours: 24);
  
  // L1: In-memory cache (Redis)
  Future<T?> getFromCache<T>(String key);
  
  // L2: Distributed cache (Redis Cluster)
  Future<T?> getFromDistributedCache<T>(String key);
  
  // L3: CDN cache
  Future<T?> getFromCDN<T>(String key);
}
```

#### Cache Invalidation
- **Time-based**: Automatic expiration
- **Event-based**: Invalidate on data changes
- **Pattern-based**: Invalidate related cache entries

### 3. Performance Optimization

#### Database Optimization
- **Indexing**: Strategic database indexes
- **Query Optimization**: Efficient SQL queries
- **Connection Pooling**: Reuse database connections

#### API Optimization
- **Pagination**: Limit result sets
- **Field Selection**: Return only requested fields
- **Compression**: Gzip response compression

## 🚀 Deployment Architecture

### 1. Infrastructure as Code

#### Terraform Configuration
```hcl
# AWS ECS Cluster
resource "aws_ecs_cluster" "marinamoda" {
  name = "marinamoda-cluster"
  
  setting {
    name  = "containerInsights"
    value = "enabled"
  }
}

# Application Load Balancer
resource "aws_lb" "marinamoda" {
  name               = "marinamoda-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.alb.id]
  subnets            = aws_subnet.public[*].id
}
```

#### Docker Configuration
```dockerfile
# Multi-stage build for Flutter app
FROM dart:stable AS build

WORKDIR /app
COPY pubspec.* ./
RUN dart pub get

COPY . .
RUN dart compile exe bin/server.dart -o bin/server

FROM ubuntu:20.04
WORKDIR /app
COPY --from=build /app/bin/server /app/bin/server

EXPOSE 8080
CMD ["/app/bin/server"]
```

### 2. CI/CD Pipeline

#### GitHub Actions Workflow
```yaml
name: Deploy to Production

on:
  push:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: subosito/flutter-action@v2
      - run: flutter test
      - run: flutter analyze

  build:
    needs: test
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Build Docker image
        run: docker build -t marinamoda:latest .
      - name: Push to registry
        run: docker push marinamoda:latest

  deploy:
    needs: build
    runs-on: ubuntu-latest
    steps:
      - name: Deploy to ECS
        run: aws ecs update-service --cluster marinamoda --service app --force-new-deployment
```

### 3. Environment Management

#### Environment Configuration
```dart
class EnvironmentConfig {
  static const String _env = String.fromEnvironment('ENVIRONMENT', defaultValue: 'development');
  
  static bool get isProduction => _env == 'production';
  static bool get isDevelopment => _env == 'development';
  static bool get isStaging => _env == 'staging';
  
  static String get apiUrl {
    switch (_env) {
      case 'production':
        return 'https://api.marina.moda';
      case 'staging':
        return 'https://api-staging.marina.moda';
      default:
        return 'http://localhost:8080';
    }
  }
}
```

## 🛠️ Technology Stack

### 1. Frontend Technologies

#### Flutter Framework
- **Version**: 3.16+
- **Architecture**: MVVM with Clean Architecture
- **State Management**: Provider/Riverpod
- **UI Components**: Custom design system
- **Testing**: Unit tests, widget tests, integration tests

#### Web Technologies
- **Framework**: Flutter Web
- **PWA**: Service workers and offline support
- **Responsive**: Mobile-first responsive design
- **Performance**: Lazy loading and code splitting

### 2. Backend Technologies

#### Runtime Environment
- **Language**: Dart
- **Framework**: Custom microservices framework
- **Runtime**: Dart VM with JIT compilation
- **Performance**: Optimized for high-throughput applications

#### Database Technologies
- **Primary**: PostgreSQL with TimescaleDB
- **Cache**: Redis Cluster
- **Search**: Elasticsearch
- **NoSQL**: MongoDB for document storage

### 3. Infrastructure Technologies

#### Cloud Platform
- **Primary**: AWS (Amazon Web Services)
- **Container**: ECS with Fargate
- **Database**: RDS PostgreSQL
- **Storage**: S3 for static assets
- **CDN**: CloudFront for global distribution

#### Monitoring & Observability
- **Logging**: CloudWatch Logs
- **Metrics**: CloudWatch Metrics
- **Tracing**: X-Ray distributed tracing
- **Alerting**: CloudWatch Alarms

## 🔌 API Design

### 1. RESTful API Principles

#### Resource-Oriented Design
```dart
// Music tracks as resources
class TrackController {
  // GET /api/v1/tracks - List tracks
  Future<List<Track>> getTracks(TrackFilters filters);
  
  // GET /api/v1/tracks/{id} - Get specific track
  Future<Track> getTrack(String id);
  
  // POST /api/v1/tracks - Create track
  Future<Track> createTrack(TrackCreation track);
  
  // PUT /api/v1/tracks/{id} - Update track
  Future<Track> updateTrack(String id, TrackUpdate update);
  
  // DELETE /api/v1/tracks/{id} - Delete track
  Future<void> deleteTrack(String id);
}
```

#### Standard Response Format
```dart
class ApiResponse<T> {
  final bool success;
  final T? data;
  final String? message;
  final DateTime timestamp;
  final String requestId;
  final ApiError? error;
}

class ApiError {
  final String code;
  final String message;
  final String? details;
  final Map<String, dynamic>? metadata;
}
```

### 2. GraphQL Support

#### Schema Definition
```graphql
type Track {
  id: ID!
  title: String!
  artist: Artist!
  album: Album
  duration: Int!
  genre: String!
  tags: [String!]!
  audioUrl: String!
  playCount: Int!
  likeCount: Int!
}

type Query {
  tracks(filters: TrackFilters): [Track!]!
  track(id: ID!): Track
  search(query: String!): SearchResults!
}

type Mutation {
  createTrack(input: TrackInput!): Track!
  updateTrack(id: ID!, input: TrackUpdateInput!): Track!
  deleteTrack(id: ID!): Boolean!
}
```

## ⛓️ Blockchain Integration

### 1. Smart Contract Architecture

#### Music Licensing Contract
```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract MusicLicensing {
    struct License {
        string trackId;
        address licensee;
        uint256 price;
        uint256 duration;
        bool isActive;
        uint256 createdAt;
    }
    
    mapping(bytes32 => License) public licenses;
    mapping(string => uint256) public trackPrices;
    
    event LicenseCreated(bytes32 indexed licenseId, string trackId, address licensee);
    event LicenseActivated(bytes32 indexed licenseId);
    
    function createLicense(
        string memory _trackId,
        uint256 _duration
    ) external payable returns (bytes32) {
        require(msg.value >= trackPrices[_trackId], "Insufficient payment");
        
        bytes32 licenseId = keccak256(abi.encodePacked(_trackId, msg.sender, block.timestamp));
        
        licenses[licenseId] = License({
            trackId: _trackId,
            licensee: msg.sender,
            price: msg.value,
            duration: _duration,
            isActive: true,
            createdAt: block.timestamp
        });
        
        emit LicenseCreated(licenseId, _trackId, msg.sender);
        return licenseId;
    }
}
```

#### Token Contract
```solidity
contract MarinaToken is ERC20 {
    constructor() ERC20("Marina.Moda Token", "MMT") {
        _mint(msg.sender, 1000000 * 10**decimals());
    }
    
    function mint(address to, uint256 amount) external onlyOwner {
        _mint(to, amount);
    }
    
    function burn(uint256 amount) external {
        _burn(msg.sender, amount);
    }
}
```

### 2. Blockchain Service Integration

#### Service Implementation
```dart
class BlockchainService {
  final Web3Client _client;
  final Credentials _credentials;
  
  BlockchainService(this._client, this._credentials);
  
  Future<String> deployContract(String contractSource, List<dynamic> constructorParams) async {
    final contract = DeployedContract(
      ContractAbi.fromJson(contractSource, 'MusicLicensing'),
      EthereumAddress.fromHex('0x...'),
    );
    
    final transaction = await _client.sendTransaction(
      _credentials,
      Transaction.callContract(
        contract: contract,
        function: contract.function('constructor'),
        params: constructorParams,
      ),
    );
    
    return transaction;
  }
  
  Future<dynamic> callContractFunction(
    String contractAddress,
    String functionName,
    List<dynamic> params,
  ) async {
    // Contract interaction logic
  }
}
```

## 🤖 AI/ML Architecture

### 1. Machine Learning Pipeline

#### Data Collection
```dart
class MLDataCollector {
  Future<void> collectUserBehavior(UserAction action) async {
    final data = {
      'userId': action.userId,
      'actionType': action.type,
      'trackId': action.trackId,
      'timestamp': DateTime.now().toIso8601String(),
      'context': action.context,
    };
    
    await _analyticsService.track('user_behavior', data);
  }
  
  Future<void> collectMusicFeatures(AudioFeatures features) async {
    // Extract audio features for ML training
    final extractedFeatures = await _audioProcessor.extractFeatures(features);
    await _mlService.storeFeatures(extractedFeatures);
  }
}
```

#### Model Training
```python
# Python ML training pipeline
import tensorflow as tf
from sklearn.model_selection import train_test_split

class MusicRecommendationModel:
    def __init__(self):
        self.model = self._build_model()
    
    def _build_model(self):
        model = tf.keras.Sequential([
            tf.keras.layers.Dense(128, activation='relu'),
            tf.keras.layers.Dropout(0.3),
            tf.keras.layers.Dense(64, activation='relu'),
            tf.keras.layers.Dense(32, activation='relu'),
            tf.keras.layers.Dense(1, activation='sigmoid')
        ])
        
        model.compile(
            optimizer='adam',
            loss='binary_crossentropy',
            metrics=['accuracy']
        )
        
        return model
    
    def train(self, X_train, y_train, epochs=100):
        return self.model.fit(X_train, y_train, epochs=epochs, validation_split=0.2)
```

### 2. AI Service Integration

#### Recommendation Engine
```dart
class AIRecommendationService {
  Future<List<TrackRecommendation>> getRecommendations(
    String userId,
    RecommendationContext context,
  ) async {
    // Get user preferences and behavior
    final userProfile = await _userService.getUserProfile(userId);
    final userBehavior = await _analyticsService.getUserBehavior(userId);
    
    // Generate recommendations using ML model
    final recommendations = await _mlService.generateRecommendations(
      userProfile: userProfile,
      userBehavior: userBehavior,
      context: context,
    );
    
    // Filter and rank recommendations
    final filteredRecommendations = await _filterRecommendations(recommendations);
    final rankedRecommendations = await _rankRecommendations(filteredRecommendations);
    
    return rankedRecommendations;
  }
}
```

## 📊 Monitoring & Observability

### 1. Logging Strategy

#### Structured Logging
```dart
class LoggerService {
  static void info(String message, {Map<String, dynamic>? context}) {
    _log('INFO', message, context);
  }
  
  static void error(String message, {Object? error, StackTrace? stackTrace, Map<String, dynamic>? context}) {
    _log('ERROR', message, {
      ...?context,
      'error': error?.toString(),
      'stackTrace': stackTrace?.toString(),
    });
  }
  
  static void _log(String level, String message, Map<String, dynamic>? context) {
    final logEntry = {
      'timestamp': DateTime.now().toIso8601String(),
      'level': level,
      'message': message,
      'service': 'marina-moda',
      'version': '1.0.0',
      ...?context,
    };
    
    // Send to centralized logging service
    _sendToLogService(logEntry);
  }
}
```

#### Metrics Collection
```dart
class MetricsService {
  static void incrementCounter(String metricName, {Map<String, String>? labels}) {
    _sendMetric('counter', metricName, 1, labels);
  }
  
  static void recordGauge(String metricName, double value, {Map<String, String>? labels}) {
    _sendMetric('gauge', metricName, value, labels);
  }
  
  static void recordHistogram(String metricName, double value, {Map<String, String>? labels}) {
    _sendMetric('histogram', metricName, value, labels);
  }
  
  static void _sendMetric(String type, String name, dynamic value, Map<String, String>? labels) {
    final metric = {
      'type': type,
      'name': name,
      'value': value,
      'timestamp': DateTime.now().millisecondsSinceEpoch,
      'labels': labels ?? {},
    };
    
    _sendToMetricsService(metric);
  }
}
```

### 2. Health Checks

#### Service Health Monitoring
```dart
class HealthCheckService {
  Future<HealthStatus> checkServiceHealth() async {
    final checks = <HealthCheck>[];
    
    // Database health check
    checks.add(await _checkDatabaseHealth());
    
    // Cache health check
    checks.add(await _checkCacheHealth());
    
    // External service health checks
    checks.add(await _checkBlockchainHealth());
    checks.add(await _checkMLServiceHealth());
    
    final overallStatus = checks.every((check) => check.status == HealthStatus.healthy)
        ? HealthStatus.healthy
        : HealthStatus.unhealthy;
    
    return HealthStatus(
      status: overallStatus,
      checks: checks,
      timestamp: DateTime.now(),
    );
  }
}
```

---

**Note**: This architecture documentation is maintained by the Marina.Moda® development team. For the most up-to-date technical information, please refer to our [developer documentation](https://docs.marina.moda) or contact our technical team.
