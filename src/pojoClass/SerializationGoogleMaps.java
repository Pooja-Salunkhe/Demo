package pojoClass;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;

public class SerializationGoogleMaps {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		GMPojoClass GM=new GMPojoClass();
		GMlocation L = new GMlocation();
		
		//Set Location
		L.setLat(-38.383494);
		L.setLng(33.427362);
		GM.setLocation(L);
		
		//Set types
		ArrayList<String>  array = new ArrayList<String>();
		array.add("shoe park");
		array.add("shop");
		GM.setTypes(array);
		
		//SET remaining methods
		
		GM.setAccuracy(50);
		GM.setAddress("29, side layout, cohen 09");
		GM.setLanguage("French-IN");
		GM.setName("Frontline house");
		GM.setPhone_number("(+91) 983 893 3937");
		GM.setWebsite("http://google.com");
	
		
		String Responce = given().queryParam("key", "qaclick123").body(GM).
				when().post("/maps/api/place/add/json").
				then().assertThat().statusCode(200).extract().response().asString();
		System.out.println("Responce: "+Responce);

	}

}
