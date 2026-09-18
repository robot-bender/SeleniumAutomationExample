Automated Test Suite
This project contains automated tests covering the application's API and web interface.
The test suite includes:

API tests using REST Assured
UI tests using Selenium WebDriver
Responsive UI tests
Visual regression tests
OpenAPI client generation
Tests can be executed locally from IntelliJ IDEA or the command line, as well as automatically through the GitHub Actions CI pipeline.
Prerequisites
Local execution
Before running the tests locally, make sure the following are installed:
Java 21 or later
Maven
Google Chrome
Node.js and npm
The Todo backend application
The backend must be running locally before starting the Maven tests.
The API tests communicate with:

http://localhost:8080

CI execution
No local installation or backend startup is required when running the tests through GitHub Actions.
The CI pipeline automatically:

Checks out the test automation project.
Checks out the Todo backend.
Installs Java 21.
Installs Node.js 22.
Installs backend dependencies.
Starts the TypeScript/Node.js backend.
Waits until the API is available on port 8080.
Runs the Maven test suite.
Uploads screenshots if a UI test fails.
Running the Tests Locally
1. Start the backend
   Start the Todo backend application locally.
   Make sure the API is available at:

http://localhost:8080

You can verify that the API is running by opening:
http://localhost:8080/tasks

or by using:
curl http://localhost:8080/tasks

2. Open the project
   Open the project in IntelliJ IDEA.
   Alternatively, open a terminal in the project's root directory — the directory containing the pom.xml file.

3. Run the complete test suite
   Execute:
   mvn clean test

This command automatically runs the complete test suite, including:
API tests
UI/Selenium tests
Responsive UI tests
Visual regression tests
No additional Maven test commands are required.
Running from IntelliJ IDEA
The tests can also be executed directly from IntelliJ IDEA.
Open the project and navigate to:

src
└── test
└── java
└── org.example

Individual test classes can be run using the green Run button in IntelliJ IDEA.
Alternatively, the complete Maven test suite can be executed from IntelliJ's Maven tool window:

Lifecycle
└── test

For a full clean execution, use:
clean
test

The backend must already be running on:
http://localhost:8080

Running Tests Through GitHub Actions
The project contains a GitHub Actions workflow that automatically runs the API and UI test suite.
The workflow is responsible for preparing the complete test environment, including the backend application.

Workflow file
The GitHub Actions workflow is located in:
.github/workflows/

The workflow runs on:
Pushes to main
Pushes to master
Pull requests
Manual workflow execution using workflow_dispatch
What the pipeline does
The pipeline performs the following steps:
Checkout test automation
↓
Checkout Todo backend
↓
Install Java 21
↓
Install Node.js
↓
Install backend dependencies
↓
Start backend
↓
Wait for http://localhost:8080/tasks
↓
Run mvn test
↓
Upload screenshots if tests fail

The backend does not need to be started manually when using GitHub Actions.
Starting the pipeline manually
To run the tests manually:
Open the GitHub repository.
Go to the Actions tab.
Select the UI and API Tests workflow.
Select Run workflow.
Select the required branch.
Click Run workflow.
GitHub Actions will then create a fresh Ubuntu runner and execute the complete test environment.
Automatic pipeline execution
The pipeline also runs automatically when:
Code is pushed to main
Code is pushed to master
A pull request is created or updated
This makes the test suite part of the project's CI process.
Backend Used by the Pipeline
The GitHub Actions workflow checks out the Todo backend into:
backend/

The backend is installed with:
npm install

and started with:
npm start

The pipeline then waits for:
http://localhost:8080/tasks

to become available before executing the Maven tests.
This prevents the API and UI test suite from starting before the backend is ready.

Test Results
After local execution, Maven displays the test results directly in the terminal.
Detailed test reports are generated in:

target/surefire-reports/

The reports include:
Test classes executed
Number of tests run
Passed tests
Failed tests
Skipped tests
Failure details
In GitHub Actions, the Maven output is available directly in the workflow run under the Run API and UI tests step.
Screenshots
If a UI test fails, a screenshot is automatically captured.
Screenshots are stored locally in:

target/screenshots/

In GitHub Actions, screenshots are uploaded as a workflow artifact named:
test-screenshots

The artifact can be downloaded from the failed GitHub Actions workflow run and used to investigate UI or visual regression failures.
OpenAPI Client Generation
The project uses OpenAPI Generator to generate the Java API client from:
src/test/resources/openapi.yaml

The generated client is created automatically during the Maven build in:
target/generated-sources/openapi/

No manual generation step is required.
Running:

mvn clean test

automatically performs the OpenAPI generation before compiling and running the tests.
Successful Execution
If all tests pass, Maven finishes with:
BUILD SUCCESS

If one or more tests fail, Maven finishes with:
BUILD FAILURE

The terminal output, Surefire reports, and screenshots can then be used to identify and investigate the failed tests.
For GitHub Actions, a failed test causes the workflow to fail and the available test reports and screenshots can be inspected from the workflow run.

Quick Start — Local
The complete local process is:
# Start the Todo backend first

# From the project root:
mvn clean test

The command above is sufficient to execute the entire automated test suite.
Quick Start — GitHub Actions
No manual backend startup is required.
Push code / create pull request
↓
GitHub Actions starts
↓
Backend is checked out and started
↓
API availability is verified
↓
mvn test
↓
API + UI + Responsive + Visual tests
↓
Results and screenshots available in GitHub Actions

Project Structure
A simplified project structure is:
.
├── .github/
│   └── workflows/
│       └── <workflow>.yml
│
├── src/
│   ├── main/
│   └── test/
│       ├── java/
│       │   └── org.example/
│       │       ├── api/
│       │       ├── ui/
│       │       └── responsive/
│       │
│       └── resources/
│           └── openapi.yaml
│
├── pom.xml
└── README.md

