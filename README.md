# Malabe Tuk-Tuk & Three-Wheeler Spares Depot

A JavaFX desktop application built for CM1601 Programming Fundamentals coursework.
The application manages inventory, dealers, point-of-sale checkout, and low-stock
monitoring for a small local spare-parts business, reading and writing all data
to plain .txt files.

## Repository and Development History

The current, active repository is:
https://github.com/Tharul-Fernando/Malabe_TukTuk

The development for the coursework started in the repository called javaCoursework
(https://github.com/Tharul-Fernando/javaCoursework). That repository contains all
the early stage commits. Partway through the project I realized I cannot integrate
JavaFX into that project and decided to restart cleanly in this repository. The
"javaCoursework" repository has been kept unmodified and is included as a
collaborator-visible reference to show the full development timeline.

## Requirements

- **JDK version:** JDK 25 (Temurin 25.0.3 LTS)
- **JavaFX version:** 21.0.6
- **Build tool:** Maven (JavaFX and JUnit dependencies are managed via `pom.xml`)

## How to Run the Application

1. Open the project in IntelliJ IDEA (or another IDE that supports Maven projects).
2. Let the IDE resolve the Maven dependencies from `pom.xml` (this pulls in JavaFX
   and JUnit automatically).
3. Run the application by running `Launcher.java`
   (`src/main/java/com/example/malabe_tuktuk/controller/Launcher.java`).
4. The application launches maximized, showing the Inventory tab by default.

## How to Run the Tests

The test suite uses JUnit 5. To run the tests:

- In IntelliJ, right-click the `src/test/java` folder (or an individual test class)
  and select **Run Tests**.
- Alternatively, from the project root, run: