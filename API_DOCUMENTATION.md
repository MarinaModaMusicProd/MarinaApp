# Marina.Moda® API Documentation

This document provides comprehensive documentation for all APIs and services available in Marina.Moda®.

## 📋 Table of Contents

1. [Overview](#overview)
2. [Authentication](#authentication)
3. [Music Streaming API](#music-streaming-api)
4. [User Management API](#user-management-api)
5. [Blockchain API](#blockchain-api)
6. [AI Services API](#ai-services-api)
7. [Storage API](#storage-api)
8. [Community API](#community-api)
9. [Error Handling](#error-handling)
10. [Rate Limiting](#rate-limiting)
11. [SDKs and Libraries](#sdks-and-libraries)

## 🌟 Overview

Marina.Moda® provides a comprehensive set of APIs for building decentralized music streaming applications. All APIs are RESTful and return JSON responses. The base URL for all API endpoints is:

```
https://api.marina.moda/v1
```

### API Versioning

- **Current Version**: v1
- **Version Header**: `X-API-Version: 1`
- **Deprecation Policy**: APIs are supported for at least 12 months after deprecation notice

### Response Format

All API responses follow this standard format:

```json
{
  "success": true,
  "data": {},
  "message": "Operation completed successfully",
  "timestamp": "2024-12-01T10:00:00Z",
  "requestId": "req_123456789"
}
```

## 🔐 Authentication

Marina.Moda® uses JWT (JSON Web Tokens) for authentication. All authenticated endpoints require a valid JWT token in the Authorization header.

### Authentication Flow

1. **Login**: POST `/auth/login`
2. **Receive JWT**: Token valid for 24 hours
3. **Include Token**: Add to `Authorization: Bearer <token>` header
4. **Refresh**: POST `/auth/refresh` before expiration

### Headers

```http
Authorization: Bearer <jwt_token>
Content-Type: application/json
X-API-Key: <your_api_key>
```

### Example Authentication

```bash
curl -X POST https://api.marina.moda/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "securepassword"
  }'
```

## 🎵 Music Streaming API

### Get Music Stream

**Endpoint**: `GET /music/stream/{track_id}`

**Description**: Retrieve a music stream URL for playback

**Parameters**:
- `track_id` (string, required): Unique identifier for the track
- `quality` (string, optional): Audio quality (low, medium, high, lossless)
- `format` (string, optional): Audio format (mp3, aac, flac)

**Response**:
```json
{
  "success": true,
  "data": {
    "track_id": "track_123",
    "stream_url": "https://stream.marina.moda/track_123.m3u8",
    "quality": "high",
    "format": "aac",
    "bitrate": 320,
    "duration": 180,
    "expires_at": "2024-12-01T11:00:00Z"
  }
}
```

### Search Music

**Endpoint**: `GET /music/search`

**Description**: Search for music tracks, albums, or artists

**Query Parameters**:
- `q` (string, required): Search query
- `type` (string, optional): Search type (track, album, artist, playlist)
- `limit` (integer, optional): Maximum results (default: 20, max: 100)
- `offset` (integer, optional): Pagination offset (default: 0)

**Response**:
```json
{
  "success": true,
  "data": {
    "results": [
      {
        "id": "track_123",
        "title": "Song Title",
        "artist": "Artist Name",
        "album": "Album Name",
        "duration": 180,
        "thumbnail": "https://cdn.marina.moda/thumbnails/track_123.jpg"
      }
    ],
    "total": 150,
    "limit": 20,
    "offset": 0
  }
}
```

### Get Track Details

**Endpoint**: `GET /music/tracks/{track_id}`

**Description**: Retrieve detailed information about a specific track

**Response**:
```json
{
  "success": true,
  "data": {
    "id": "track_123",
    "title": "Song Title",
    "artist": {
      "id": "artist_456",
      "name": "Artist Name",
      "verified": true
    },
    "album": {
      "id": "album_789",
      "name": "Album Name",
      "release_date": "2024-01-01"
    },
    "duration": 180,
    "genre": "Pop",
    "tags": ["upbeat", "summer", "dance"],
    "play_count": 15000,
    "like_count": 2500,
    "thumbnail": "https://cdn.marina.moda/thumbnails/track_123.jpg",
    "audio_url": "https://stream.marina.moda/track_123.m3u8"
  }
}
```

## 👥 User Management API

### User Registration

**Endpoint**: `POST /users/register`

**Description**: Create a new user account

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "securepassword",
  "username": "username",
  "display_name": "Display Name",
  "date_of_birth": "1990-01-01"
}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "user_id": "user_123",
    "email": "user@example.com",
    "username": "username",
    "display_name": "Display Name",
    "created_at": "2024-12-01T10:00:00Z"
  }
}
```

### User Profile

**Endpoint**: `GET /users/profile`

**Description**: Retrieve current user's profile information

**Headers**: Requires authentication

**Response**:
```json
{
  "success": true,
  "data": {
    "id": "user_123",
    "email": "user@example.com",
    "username": "username",
    "display_name": "Display Name",
    "avatar": "https://cdn.marina.moda/avatars/user_123.jpg",
    "bio": "Music enthusiast",
    "followers_count": 150,
    "following_count": 75,
    "playlists_count": 12,
    "created_at": "2024-12-01T10:00:00Z",
    "last_active": "2024-12-01T09:30:00Z"
  }
}
```

### Update Profile

**Endpoint**: `PUT /users/profile`

**Description**: Update user profile information

**Headers**: Requires authentication

**Request Body**:
```json
{
  "display_name": "New Display Name",
  "bio": "Updated bio",
  "avatar": "data:image/jpeg;base64,..."
}
```

## ⛓️ Blockchain API

### Smart Contract Interaction

**Endpoint**: `POST /blockchain/contract/interact`

**Description**: Interact with smart contracts for music licensing and compensation

**Headers**: Requires authentication

**Request Body**:
```json
{
  "contract_address": "0x1234567890abcdef...",
  "function_name": "purchaseLicense",
  "parameters": {
    "track_id": "track_123",
    "license_type": "commercial",
    "duration": 365
  },
  "gas_limit": 300000
}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "transaction_hash": "0xabcdef1234567890...",
    "block_number": 12345678,
    "gas_used": 250000,
    "status": "pending"
  }
}
```

### Get Transaction Status

**Endpoint**: `GET /blockchain/transaction/{tx_hash}`

**Description**: Check the status of a blockchain transaction

**Response**:
```json
{
  "success": true,
  "data": {
    "transaction_hash": "0xabcdef1234567890...",
    "block_number": 12345678,
    "status": "confirmed",
    "confirmations": 12,
    "gas_used": 250000,
    "timestamp": "2024-12-01T10:00:00Z"
  }
}
```

## 🤖 AI Services API

### Music Recommendations

**Endpoint**: `POST /ai/recommendations`

**Description**: Get AI-powered music recommendations based on user preferences

**Headers**: Requires authentication

**Request Body**:
```json
{
  "user_id": "user_123",
  "context": "workout",
  "mood": "energetic",
  "genres": ["pop", "electronic"],
  "limit": 20
}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "recommendations": [
      {
        "track_id": "track_123",
        "title": "Recommended Song",
        "artist": "Artist Name",
        "confidence_score": 0.95,
        "reason": "Based on your workout playlist preferences"
      }
    ],
    "context": "workout",
    "mood": "energetic"
  }
}
```

### Content Moderation

**Endpoint**: `POST /ai/moderate`

**Description**: AI-powered content moderation for user-generated content

**Request Body**:
```json
{
  "content": "User comment text",
  "content_type": "comment",
  "user_id": "user_123"
}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "is_appropriate": true,
    "confidence_score": 0.98,
    "flagged_keywords": [],
    "moderation_notes": "Content appears appropriate"
  }
}
```

## 💾 Storage API

### Upload File

**Endpoint**: `POST /storage/upload`

**Description**: Upload files to decentralized storage (IPFS)

**Headers**: Requires authentication

**Request Body**: Multipart form data

**Response**:
```json
{
  "success": true,
  "data": {
    "file_id": "file_123",
    "ipfs_hash": "QmHash123...",
    "file_size": 1024000,
    "mime_type": "audio/mpeg",
    "uploaded_at": "2024-12-01T10:00:00Z",
    "gateway_url": "https://ipfs.io/ipfs/QmHash123..."
  }
}
```

### Get File

**Endpoint**: `GET /storage/files/{file_id}`

**Description**: Retrieve file information and access URLs

**Response**:
```json
{
  "success": true,
  "data": {
    "file_id": "file_123",
    "ipfs_hash": "QmHash123...",
    "file_size": 1024000,
    "mime_type": "audio/mpeg",
    "gateway_urls": [
      "https://ipfs.io/ipfs/QmHash123...",
      "https://gateway.pinata.cloud/ipfs/QmHash123..."
    ],
    "created_at": "2024-12-01T10:00:00Z"
  }
}
```

## 🌐 Community API

### Create Post

**Endpoint**: `POST /community/posts`

**Description**: Create a new community post

**Headers**: Requires authentication

**Request Body**:
```json
{
  "content": "Check out this amazing new track!",
  "type": "music_share",
  "track_id": "track_123",
  "tags": ["new_release", "electronic"],
  "visibility": "public"
}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "post_id": "post_123",
    "content": "Check out this amazing new track!",
    "user": {
      "id": "user_123",
      "username": "username",
      "display_name": "Display Name"
    },
    "track": {
      "id": "track_123",
      "title": "Track Title",
      "artist": "Artist Name"
    },
    "created_at": "2024-12-01T10:00:00Z",
    "like_count": 0,
    "comment_count": 0
  }
}
```

### Get Community Feed

**Endpoint**: `GET /community/feed`

**Description**: Retrieve community posts and updates

**Query Parameters**:
- `page` (integer, optional): Page number (default: 1)
- `limit` (integer, optional): Posts per page (default: 20, max: 50)
- `type` (string, optional): Filter by post type
- `user_id` (string, optional): Filter by specific user

## ❌ Error Handling

### Error Response Format

```json
{
  "success": false,
  "error": {
    "code": "AUTHENTICATION_FAILED",
    "message": "Invalid or expired authentication token",
    "details": "Token expired at 2024-12-01T09:00:00Z"
  },
  "timestamp": "2024-12-01T10:00:00Z",
  "requestId": "req_123456789"
}
```

### Common Error Codes

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `AUTHENTICATION_FAILED` | 401 | Invalid or expired token |
| `PERMISSION_DENIED` | 403 | Insufficient permissions |
| `RESOURCE_NOT_FOUND` | 404 | Requested resource not found |
| `VALIDATION_ERROR` | 400 | Invalid request parameters |
| `RATE_LIMIT_EXCEEDED` | 429 | Too many requests |
| `INTERNAL_SERVER_ERROR` | 500 | Server-side error |

## 🚦 Rate Limiting

### Rate Limits

- **Free Tier**: 100 requests per hour
- **Premium Tier**: 1000 requests per hour
- **Enterprise Tier**: 10000 requests per hour

### Rate Limit Headers

```http
X-RateLimit-Limit: 1000
X-RateLimit-Remaining: 950
X-RateLimit-Reset: 1640995200
```

### Rate Limit Response

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMIT_EXCEEDED",
    "message": "Rate limit exceeded. Try again in 3600 seconds.",
    "retry_after": 3600
  }
}
```

## 📚 SDKs and Libraries

### Official SDKs

- **Flutter SDK**: [GitHub Repository](https://github.com/marinamoda/flutter-sdk)
- **JavaScript SDK**: [GitHub Repository](https://github.com/marinamoda/js-sdk)
- **Python SDK**: [GitHub Repository](https://github.com/marinamoda/python-sdk)

### Community Libraries

- **React Native**: [npm package](https://www.npmjs.com/package/marinamoda-react-native)
- **Vue.js**: [npm package](https://www.npmjs.com/package/marinamoda-vue)
- **Angular**: [npm package](https://www.npmjs.com/package/marinamoda-angular)

### SDK Installation

#### Flutter
```yaml
dependencies:
  marinamoda_flutter: ^1.0.0
```

#### JavaScript
```bash
npm install @marinamoda/sdk
```

#### Python
```bash
pip install marinamoda-sdk
```

## 🔗 Additional Resources

- **Interactive API Explorer**: [API Playground](https://api.marina.moda/playground)
- **Postman Collection**: [Download Collection](https://api.marina.moda/postman)
- **OpenAPI Specification**: [OpenAPI 3.0 Spec](https://api.marina.moda/openapi.json)
- **SDK Documentation**: [SDK Docs](https://docs.marina.moda/sdk)

## 📞 Support

For API support and questions:

- **Documentation**: [docs.marina.moda](https://docs.marina.moda)
- **GitHub Issues**: [API Issues](https://github.com/marinamoda/api/issues)
- **Email**: api-support@marina.moda
- **Discord**: [Join Community](https://discord.gg/marinamoda)

---

**Note**: This API documentation is maintained by the Marina.Moda® development team. For the most up-to-date information, please refer to our [official documentation](https://docs.marina.moda).
