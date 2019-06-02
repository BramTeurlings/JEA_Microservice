package UnitTests.Endpoints;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class Endpoints {
    private static String token;

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = 8080;
        }
        else{
            RestAssured.port = Integer.valueOf(port);
        }


        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://localhost:8080/";
        }
        RestAssured.baseURI = baseHost;

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.parameter("username", "user");
        httpRequest.parameter("password", "user");

        Response response = httpRequest.post("AuthenticationSystem-1.0-SNAPSHOT/api/authentication/login");

        System.out.println("Response Body is =>  " + response.asString());

        token = response.asString();
    }


    //GET Endpoints:
    @Test
    public void getAllTest() {
        given().header("Authorization", "Bearer " + token).when().get("ChatSystem-1.0-SNAPSHOT/api/chat/all").then().statusCode(200);
    }

    @Test
    public void getUsersTest() {
        given().header("Authorization", "Bearer " + token).when().get("UserSystem-1.0-SNAPSHOT/api/user/all").then().statusCode(200);
    }

    //POST Endpoints:
    @Test
    public void registerTest() {
        given().header("Authorization", "Bearer " + token).when().post("UserSystem-1.0-SNAPSHOT/api/user/register").then().statusCode(500);
    }

    @Test
    public void addKweetTest() {
        given().header("Authorization", "Bearer " + token).header("Content-Type", "application/json").when().post("ChatSystem-1.0-SNAPSHOT/api/chat/addKweet").then().statusCode(500);
    }

    @Test
    public void loginTest() {
        given().header("Content-Type", "application/x-www-form-urlencoded").when().post("AuthenticationSystem-1.0-SNAPSHOT/api/authentication/login").then().statusCode(401);
    }

//    Works but removes all users which is inconvenient.
//    @Test
//    public void removeUserTest() {
//        given().header("Authorization", "Bearer " + token).when().delete("/removeUser").then().statusCode(500);
//    }
}
