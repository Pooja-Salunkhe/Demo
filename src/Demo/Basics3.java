package Demo;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payloads;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics3 
{
  //add place & update a place with new address --> get place to validate if new address is present in response
	public static void main(String[] args)
	{
		/* given- all input details
		when- submit the API
		then-validate the response */
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(Payloads.AddPlace()).
		when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response is: "+Response);
		
		//parsing the json
		JsonPath js = ReusableMethods.rawToJason(Response);
		String PlaceID = js.getString("place_id");
		String status = js.getString("status");
		String scope = js.getString("scope");
		String reference = js.getString("reference");
		String id = js.getString("id");
		
		System.out.println("PlaceID: "+ PlaceID);
		System.out.println("status: "+status );
		System.out.println("scope: "+ scope);
		System.out.println("reference: "+reference );
		System.out.println("id: "+ id);
		
		//update place
		String NewAddress = "70 winter walk, USA";
		given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json").
		       body("{\r\n"
				+ "\"place_id\":\""+PlaceID+"\",\r\n"
				+ "\"address\":\""+NewAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").
		when().put("maps/api/place/update/json").
		then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//Get Place
		String GetResponse =given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceID).
		when().get("/maps/api/place/get/json").andReturn().
		then().assertThat().statusCode(200).extract().response().asString();
		
		
		//parsing the json
		JsonPath js1 = ReusableMethods.rawToJason(GetResponse);
		String ActualAdress = js1.getString("address");
		System.out.println("ActualAdress: "+ActualAdress);
		if(NewAddress.contains(ActualAdress))
		{
			System.out.println("Yes pass############");
		}
		else
		{
			System.out.println("No fail#########");
			
			
		}
		
		Assert.assertEquals(ActualAdress, NewAddress);

	}
}
