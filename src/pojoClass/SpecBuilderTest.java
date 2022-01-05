package pojoClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
			RestAssured.baseURI="https://rahulshettyacademy.com";
			
			GMPojoClass p=new GMPojoClass();
			GMlocation l = new GMlocation();
			
			
			p.setAccuracy(50);
			p.setAddress("29, side layout, cohen 09");
			p.setLanguage("French-IN");
			p.setPhone_number("(+91) 983 893 3937");
			p.setWebsite("https://rahulshettyacademy.com");
			p.setName("Frontline house");
			List<String> myList =new ArrayList<String>();
			myList.add("shoe park");
			myList.add("shop");
			
			p.setTypes(myList);
			
			l.setLat(-38.383494);
			l.setLng(33.427362);
			p.setLocation(l);
			
			//Declare one time baseuri & addquery parameter
			 RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
			.setContentType(ContentType.JSON).build();
			 
			//declare one time 200 status code  
			ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
			RequestSpecification res=given().spec(req)
			.body(p);
			
			
			Response response =res.when().post("/maps/api/place/add/json").
			then().spec(resspec).extract().response();
			
			String responseString=response.asString();
			System.out.println(responseString);
			
			

	}

}
