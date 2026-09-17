#!/bin/bash

set -e

CERT_DIR="src/main/resources"
CERT_FILE="$CERT_DIR/localhost.p12"

mkdir -p "$CERT_DIR"

# Remove old certificate
rm -f "$CERT_FILE"

# Generate new self-signed certificate
keytool -genkeypair \
  -alias localhost \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore "$CERT_FILE" \
  -validity 3650 \
  -storepass changeit \
  -dname "CN=localhost"

echo "HTTPS certificate generated :"
echo "   $CERT_FILE"
echo ""
echo "Run your application with:"
echo "   ./gradlew bootRun"
echo ""
echo "Then open:"
echo "   https://localhost:8443"