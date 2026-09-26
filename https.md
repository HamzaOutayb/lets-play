## HTTPS / SSL Certificate

This project uses HTTPS with a self-signed PKCS#12 certificate for local development.

### 1. Generate the certificate

From the project root, run:

```bash
keytool -genkeypair \
  -alias localhost \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore src/main/resources/localhost.p12 \
  -validity 3650 \
  -storepass changeit \
  -keypass changeit \
  -dname "CN=localhost, OU=Development, O=Lets Play, L=Oujda, ST=Oriental, C=MA"
```

This creates:

```text
src/main/resources/localhost.p12
```

### 2. Configure the environment

Create a `.env` file in the project root:

```env
KEYSTORE_PATH=classpath:localhost.p12
KEYSTORE_PASSWORD=changeit
KEYSTORE_TYPE=PKCS12
KEYSTORE_ALIAS=localhost
SERVER_PORT=8443
```

The `.env` file should not be committed to Git.

Add it to `.gitignore`:

```gitignore
.env
```

### 3. Import the `.env` file

In `application.properties`:

```properties
spring.config.import=optional:file:.env[.properties]
```

Then configure Spring Boot SSL:

```properties
server.port=${SERVER_PORT}

server.ssl.enabled=true
server.ssl.key-store=${KEYSTORE_PATH}
server.ssl.key-store-password=${KEYSTORE_PASSWORD}
server.ssl.key-store-type=${KEYSTORE_TYPE}
server.ssl.key-alias=${KEYSTORE_ALIAS}
```

### 4. Run the application

```bash
./gradlew bootRun
```

The application will be available at:

```text
https://localhost:8443
```

### Note

The certificate is self-signed and intended for local development. Browsers and API clients may display a certificate warning because the certificate is not signed by a trusted Certificate Authority.

For production, use a certificate issued by a trusted Certificate Authority such as Let's Encrypt.
