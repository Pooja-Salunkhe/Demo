package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson 
{
     @Test(dataProvider="BookData")
     public void DynamicJsonMethod(String isbn, String aisle)
     {
    	 
    	 RestAssured.baseURI ="http://216.10.245.166";
    	 
    	 String PostResponce = given().header("Content-Type","application/json").body(Payloads.DynamicJson(isbn,aisle)).
    	 when().post("/Library/Addbook.php").
    	 then().assertThat().statusCode(200).extract().response().asString();
    	 
    	JsonPath js =  ReusableMethods.rawToJason(PostResponce);
    	String ID = js.getString("ID");
    	 
    	 System.out.println("ID is= "+ID);
    	 
    	 
     }
     
     
     
     @DataProvider(name="BookData")
     public Object[][] getData()
     {
    	 return new Object[][] {{"asdc","12e45"},{"qwsd","23e4"},{"56rt","23e45"}};
     }
}
