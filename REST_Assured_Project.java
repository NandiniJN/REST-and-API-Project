package Activities_TestNG;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class REST_Assured_Project {

	// Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;
    // Global properties
    String sshKey;
    int sshKeyId;
	  		
	    
    @BeforeClass
	public void setUp() {
		// Create request specification
		requestSpec = new RequestSpecBuilder()
				// Set content type
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token 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")
				// Set base URL
				.setBaseUri("https://api.github.com")
				// Build request specification
				.build();
		sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCZm4GunSodUKXn0ubVDkY+EtIR6MZ3nFi7FKuwLXeBx1WOkfJqpcXI4V9UQqMPnbrgu39T2ajm5PlmCgx2xAJH+n5xutedDvbEAxbzDU3LDtzCDI0qY5PjIONLc7sGDG/4Nt4aSAjeVq+CGEYeR+KJzqmBwlvLqceVI9bH58qTei6uh+fYMuRQAnzMZ1l2hXJC3piWmLaWxWoTejDxRRLNdf2HUmhMwvaFIjcF/msJSvxCJcBPHw7iJsEeMqPH4S52IuymtijXUVaCQwcWZhWbQQeM+zE1Xuov5/5cDwpbHla/rjKVJxCNAJrRa/lSdk3bRq8dB+8IsIyBRW17vN8G53UOUSLho8/bUOyQpR1PxLW78gyUkkUsrFbAABdOqrBXEQcadAUSsbyuu1WjHHaviCibzbQN9IYhggLLyFUAftR7mXL/XISfYpKTkYRFSQG6td2OELOumT0usmlXUiFoE3iJP8F2sOYr79dMwVttGxFewrF4/z7aEUPIy7qPgHM=";
	}
    
	@Test(priority = 1)
	// Test case using a DataProvider
	public void addKeys() {
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
		Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) // Send request body
				.when().post("/user/keys"); // Send POST request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		sshKeyId = response.then().extract().path("id");
		// Assertions
		response.then().statusCode(201);
	}
	@Test(priority = 2)
	// Test case using a DataProvider
	public void getKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.when().get("/user/keys"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(200);
	}
	@Test(priority = 3)
	// Test case using a DataProvider
	public void deleteKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(204);
	}

}
