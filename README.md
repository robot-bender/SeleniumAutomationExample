Automated Test Suite
This project contains automated tests covering the application's API and web interface.
Prerequisites
Before running the tests, make sure the following are installed:
Java 21 or later
Maven
Google Chrome
The application/backend must also be running locally before starting the tests.
The API tests communicate with:

http://localhost:8080

Running the Tests
1. Start the application
   Start the application/backend locally and make sure it is available on:
   http://localhost:8080

2. Open the project
   Open a terminal in the project's root directory — the directory containing the pom.xml file.
3. Run the complete test suite
   Execute:
   mvn clean test

This command automatically runs the complete test suite, including:
API tests
UI/Selenium tests
Responsive UI tests
Visual regression test
No additional test commands are required.
Test Results
After execution, Maven displays the test results directly in the terminal.
Detailed test reports are generated in:

target/surefire-reports/

The reports include:
Test classes executed
Number of tests run
Passed tests
Failed tests
Skipped tests
Failure details
Screenshots
If a UI test fails, a screenshot is automatically captured.
Screenshots are stored in:

target/screenshots/

This is particularly useful when investigating failures in the visual regression test.
Successful Execution
If all tests pass, Maven finishes with:
BUILD SUCCESS

If one or more tests fail, Maven finishes with:
BUILD FAILURE

The terminal output and files in target/surefire-reports/ can then be used to identify the failed test.
Quick Start
The complete process is:
# Start the application/backend first

# From the project root:
mvn clean test

The command above is sufficient to execute the entire automated test suite.