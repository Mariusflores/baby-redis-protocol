# baby-redis-protocol

![Build](https://github.com/mariusflores/baby-redis-protocol/actions/workflows/build.yml/badge.svg)

A shared protocol library implementing a simplified RESP (Redis Serialization
Protocol) for the [baby-redis](https://github.com/mariusflores/baby-redis) ecosystem.

Provides encoding and decoding of typed messages between the baby-redis
server and client.

## Status

🚧 **In active development.**

## RESP Type Prefixes

| Prefix | Type          | Example                    |
|--------|---------------|----------------------------|
| `+`    | Simple String | `+OK\r\n`                  |
| `-`    | Error         | `-ERR unknown command\r\n` |
| `:`    | Integer       | `:42\r\n`                  |
| `$`    | Bulk String   | `$5\r\nhello\r\n`          |
| `*`    | Array         | `*3\r\n$5\r\napple\r\n...` |

## Usage

### Encoding (server side)

```java
import io.babyredis.protocol.RespEncoder;

String encoded = RespEncoder.encodeString("OK");           // → +OK\r\n
String encoded = RespEncoder.encodeInteger(42);            // → :42\r\n
String encoded = RespEncoder.encodeError("Unknown cmd");   // → -Unknown cmd\r\n
String encoded = RespEncoder.encodeBulkString("hello");    // → $5\r\nhello\r\n
String encoded = RespEncoder.encodeArray("SET", "k", "v"); // → *3\r\n$3\r\nSET\r\n...
```

### Decoding (client side)

```java
import io.babyredis.protocol.RespDecoder;

String value = RespDecoder.decodeString(reader);       // +OK → "OK"
int number = RespDecoder.decodeInteger(reader);         // :42 → 42
String bulk = RespDecoder.decodeBulkString(reader);     // $5\r\nhello → "hello"
String[] items = RespDecoder.decodeArray(reader);       // *3\r\n... → ["a","b","c"]
```

Error responses (`-ERR ...`) automatically throw `BabyRedisException`.

## Prerequisites

- Java 21+
- Maven

## Building

```bash
git clone https://github.com/mariusflores/baby-redis-protocol.git
cd baby-redis-protocol
mvn clean install
```

## Related

- [baby-redis](https://github.com/mariusflores/baby-redis) — the server
- [baby-redis-client](https://github.com/mariusflores/baby-redis-client) — the client library
- [baby-redis-cli](https://github.com/mariusflores/baby-redis-cli) — the CLI tool