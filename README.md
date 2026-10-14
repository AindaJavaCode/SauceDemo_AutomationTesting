Functional automation testing of https://www.saucedemo.com/

Designed and built an end-to-end UI test automation framework for an e-commerce web application, with a CI/CD pipeline that runs the suite automatically and publishes a live test report.

- Built in Java 17 with Selenium WebDriver and JUnit 6, structured with the Page Object Model to keep tests readable and easy to maintain
- Designed a thread-safe driver layer (driver factory + ThreadLocal manager) with cross-browser support for Chrome and Firefox, switchable at runtime through configuration
- Externalised configuration and credentials (properties files, environment variables, GitHub Secrets) so no sensitive data lives in the repository
- Covered login, negative and parameterised scenarios, and cart workflows, using explicit waits and fully independent tests to keep results reliable
- Added structured logging (SLF4J/Logback), automatic screenshots on failure, and Allure reporting with steps and attachments
- Set up a GitHub Actions pipeline that runs the suite headless on every push and pull request, uploads artifacts, and publishes the Allure report to GitHub Pages

Tech: Java, Selenium WebDriver, JUnit 6, Maven, Allure, GitHub Actions, GitHub Pages, Logback

