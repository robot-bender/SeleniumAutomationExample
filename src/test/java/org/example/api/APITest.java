package org.example.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class APITest {

    private static final String BASE_URL = "http://localhost:8080";

    @Test
    void getTasks() {

        given()
                .when()
                .get(BASE_URL + "/tasks")
                .then()
                .statusCode(200);
    }

    @Test
    void createTask() {

        String taskId =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                    "text": "My test task"
                                }
                                """)
                        .when()
                        .post(BASE_URL + "/tasks")
                        .then()
                        .statusCode(200)
                        .body("id", notNullValue())
                        .body("text", equalTo("My test task"))
                        .extract()
                        .path("id");

        System.out.println("Created task ID: " + taskId);
    }

    @Test
    void getCompletedTasks() {

        given()
                .when()
                .get(BASE_URL + "/tasks/completed")
                .then()
                .statusCode(200);
    }

    @Test
    void updateTask() {

        // First create a task
        String taskId =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                    "text": "Task before update"
                                }
                                """)
                        .when()
                        .post(BASE_URL + "/tasks")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        // Then update the task using its generated ID
        given()
                .contentType("application/json")
                .body("""
                        {
                            "text": "Task after update"
                        }
                        """)
                .when()
                .post(BASE_URL + "/tasks/" + taskId)
                .then()
                .statusCode(200)
                .body("id", equalTo(taskId))
                .body("text", equalTo("Task after update"));
    }

    @Test
    void deleteTask() {

        // First create a task
        String taskId =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                    "text": "Task to delete"
                                }
                                """)
                        .when()
                        .post(BASE_URL + "/tasks")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        // Then delete it
        given()
                .when()
                .delete(BASE_URL + "/tasks/" + taskId)
                .then()
                .statusCode(200);
    }

    @Test
    void completeTask() {

        // First create a task
        String taskId =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                    "text": "Task to complete"
                                }
                                """)
                        .when()
                        .post(BASE_URL + "/tasks")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        // Complete the task
        given()
                .when()
                .post(BASE_URL + "/tasks/" + taskId + "/complete")
                .then()
                .statusCode(200)
                .body("id", equalTo(taskId))
                .body("completed", equalTo(true));
    }

    @Test
    void incompleteTask() {

        // First create a task
        String taskId =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                    "text": "Task to make incomplete"
                                }
                                """)
                        .when()
                        .post(BASE_URL + "/tasks")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        // Complete it first
        given()
                .when()
                .post(BASE_URL + "/tasks/" + taskId + "/complete")
                .then()
                .statusCode(200);

        // Then make it incomplete
        given()
                .when()
                .post(BASE_URL + "/tasks/" + taskId + "/incomplete")
                .then()
                .statusCode(200)
                .body("id", equalTo(taskId))
                .body("completed", equalTo(false));

        // API should return an error when by using an ID that doesn't exist
        given()
                .when()
                .delete(BASE_URL + "/tasks/non-existing-id")
                .then()
                .statusCode(404);
    }
    @Test
    void updateNonExistingTask() {

        String nonExistingId = "this-task-does-not-exist";

        given()
                .contentType("application/json")
                .body("""
                    {
                        "text": "Updated task"
                    }
                    """)
                .when()
                .post(BASE_URL + "/tasks/" + nonExistingId)
                .then()
                .statusCode(400);
    }

    @Test
    void deleteNonExistingTask() {

        String nonExistingId = "this-task-does-not-exist";

        given()
                .when()
                .delete(BASE_URL + "/tasks/" + nonExistingId)
                .then()
                .statusCode(400);
    }

    @Test
    void completeNonExistingTask() {

        String nonExistingId = "this-task-does-not-exist";

        given()
                .when()
                .post(BASE_URL + "/tasks/" + nonExistingId + "/complete")
                .then()
                .statusCode(400);
    }

    @Test
    void incompleteNonExistingTask() {

        String nonExistingId = "this-task-does-not-exist";

        given()
                .when()
                .post(BASE_URL + "/tasks/" + nonExistingId + "/incomplete")
                .then()
                .statusCode(400);
    }

    @Test
    void createTaskWithoutText() {

        given()
                .contentType("application/json")
                .body("""
                    {
                    }
                    """)
                .when()
                .post(BASE_URL + "/tasks")
                .then()
                .statusCode(422);
    }

    @Test
    void createTaskWithEmptyText() {

        given()
                .contentType("application/json")
                .body("""
                    {
                        "text": ""
                    }
                    """)
                .when()
                .post(BASE_URL + "/tasks")
                .then()
                .statusCode(422);
    }

    @Test
    void updateTaskWithoutText() {

        // Create a valid task first
        String taskId =
                given()
                        .contentType("application/json")
                        .body("""
                            {
                                "text": "Original task"
                            }
                            """)
                        .when()
                        .post(BASE_URL + "/tasks")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("id");

        // Try to update without providing text
        given()
                .contentType("application/json")
                .body("""
                    {
                    }
                    """)
                .when()
                .post(BASE_URL + "/tasks/" + taskId)
                .then()
                .statusCode(422);
    }

}
