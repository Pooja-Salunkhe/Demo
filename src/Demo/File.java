package Demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static  io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import files.ReusableMethods;

public class File {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//F:\POSTMAN\API\A2.json
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String Response = given().queryParam("key", "qaclick123").header("Content-Type","application/json").body(new String ( Files.readAllBytes(Paths.get("F:\\POSTMAN\\API\\A2.json"))))
		.when().post("/maps/api/place/add/json").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJason(Response);
		String placeID = js.getString("place_id");
		System.out.println("placeID: "+placeID);

	}

}
