# Malabe Tuk-Tuk & Three-Wheeler Spares Depot

A **JavaFX desktop application** for managing a small spare-parts business, developed for **CM1601 Programming Fundamentals** coursework.

The application supports inventory management, dealer records, point-of-sale checkout, and low-stock monitoring, with data stored in plain text files.

## Features

- Spare-parts inventory management.
- Dealer management.
- Shopping cart and point-of-sale checkout.
- Low-stock monitoring.
- Text-file persistence and audit logging.
- JUnit tests for application components.

## Technology Stack

| Component | Technology |
| --- | --- |
| Language | Java |
| Desktop UI | JavaFX 21.0.6 and FXML |
| Build | Maven, with Maven Wrapper included |
| Testing | JUnit Jupiter 5.12.1 |
| Storage | Plain text files in `data/` |

See [pom.xml](pom.xml) for the build configuration. The existing project documentation records JDK 25 as the development environment; use a compatible installed JDK and configure it in your IDE. The Maven compiler source/target settings are currently `9`, so they should not be read as the JavaFX runtime requirement.

## Getting Started

```bash
git clone https://github.com/Tharul-Fernando/Malabe_TukTuk.git
cd Malabe_TukTuk
```

### Run in an IDE

1. Open the repository as a Maven project in IntelliJ IDEA or another Maven-capable IDE.
2. Configure the project JDK and let Maven resolve the dependencies.
3. Run [Launcher.java](src/main/java/com/example/malabe_tuktuk/controller/Launcher.java).
4. Keep the working directory set to the repository root so relative data paths resolve correctly.

### Run with Maven

With Maven and a compatible JDK installed:

```bash
mvn clean javafx:run
```

The JavaFX Maven plugin and application main class are configured in `pom.xml`. The repository also includes `mvnw` and `mvnw.cmd` for Maven Wrapper use.

## Tests

The tests are in [src/test/java](src/test/java). In IntelliJ IDEA, right-click that folder or an individual test class and choose **Run Tests**.

You can also invoke Maven's test lifecycle:

```bash
mvn test
```

JUnit Jupiter dependencies are declared in the POM. Check that your Maven test runner discovers the JUnit 5 tests; the POM does not currently pin a Surefire plugin version. These commands are documented from the repository configuration, not a fresh execution in this documentation update.

## Code Organisation

- [controller](src/main/java/com/example/malabe_tuktuk/controller) — application entry points and UI controller.
- [model](src/main/java/com/example/malabe_tuktuk/model) — parts, dealers, and cart models.
- [parser](src/main/java/com/example/malabe_tuktuk/parser) — text-file parsing.
- [service](src/main/java/com/example/malabe_tuktuk/service) — inventory, dealer, checkout, and audit services.
- [data](data) — text-file data used by the application. Working with the app can change these files; use a separate checkout for experimentation.

## Development History

This is the active application repository. Development began in [javaCoursework](https://github.com/Tharul-Fernando/javaCoursework), then restarted here to integrate JavaFX with a clean project structure. The earlier repository retains the initial commits and is currently private, so its history is available only to authorised collaborators.

## Author

**Tharul Fernando** — BSc (Hons) Artificial Intelligence and Data Science undergraduate at IIT.

[GitHub](https://github.com/Tharul-Fernando) · [LinkedIn](https://www.linkedin.com/in/tharul-fernando/) · [Email](mailto:tharulnithwara@gmail.com)
