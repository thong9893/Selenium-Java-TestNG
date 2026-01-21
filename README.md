# Selenium Java — OrangeHRM Demo

Demo Selenium + TestNG project (Page Object Model) for OrangeHRM UI tests. Includes Allure reporting configuration.

## Quick start

Prerequisites:
- Java 17+ (project uses modern Java features)
- Maven 3.6+
- Browser driver (Chrome/Firefox) available on `PATH` or provided via system property
Prerequisites:
- Java 17 (project `pom.xml` targets Java 17)
- Maven 3.6+
- Browser driver (Chrome/Firefox) available on `PATH` or provide its path via `-Dwebdriver.chrome.driver`

Quick start (minimal):

1. Clone the repo and change directory:

```bash
git clone <repo-url>
cd Selenium-Java-TestNG
```

2. Run the TestNG suite defined in `orangeHRM.xml` (single command):

```bash
mvn test "-DsuiteXmlFiles=orangeHRM.xml"
```

Notes:
- The suite file sets the `browser` parameter to `chrome`.

## Allure reporting

This project writes Allure results to `allure-results` at the project root.

- After a test run, generate/serve the report with the Allure CLI:

```bash
mvn test
allure serve allure-results
```

Or open an already generated report:

```bash
allure open allure-results
```

## Project layout (selected)

- `pom.xml` — Maven build, dependencies (Selenium, TestNG, Allure) and Surefire configuration
- `src/test/java/pages` — Page Objects
- `src/test/java/pageUIs` — locators/constants for pages
- `src/test/java/specs` — Test classes (TestNG)
- `src/test/java/support` — base classes and test utilities (`BasePage`, `BaseTest`, `DriverFactory`)
- `src/test/resources` — test configuration and test data

## About OrangeHRM

OrangeHRM is a popular web-based Human Resource Management (HRM) application that provides core modules such as employee management, recruitment, time and attendance, leave management, and performance appraisal. Its user-friendly web interface, customization options, and REST API support help small and medium-sized organizations streamline HR processes, manage employee data, and automate administrative tasks.
























