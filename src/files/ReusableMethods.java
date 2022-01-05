package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods 
{
   public static JsonPath rawToJason(String Response)
   {
	   JsonPath js =new  JsonPath(Response);
	   return js;
   }
}
