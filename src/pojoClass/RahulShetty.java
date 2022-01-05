package pojoClass;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class RahulShetty 
{
	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub
			
		/*  https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/
			oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&
			response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php    */
	     
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWgA5ehNGdEzqITWKg02pbJ_yE18h4HcOzV2B2DPxIya6b-tPWgZLxhQvd9xpXjrsQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none#";


String[] ExpectedString = {"Selenium Webdriver Java","Cypress","Protractor"};
		String partialcode=url.split("code=")[1];

		String code=partialcode.split("&scope")[0];

		System.out.println(code);

		String response =

		                given() 

		                .urlEncodingEnabled(false)

		                       .queryParams("code",code)

		               

		                   .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

		                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

		                        .queryParams("grant_type", "authorization_code")

		                        .queryParams("state", "verifyfjdss")

		                        .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

		                     // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

		                       

		                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")

		                        .when().log().all()

		                        .post("https://www.googleapis.com/oauth2/v4/token").asString();

		// System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);

		    String accessToken = jsonPath.getString("access_token");

		    System.out.println(accessToken);

		String r2=    given().contentType("application/json").

		queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)

		.when()

		           .get("https://rahulshettyacademy.com/getCourse.php")

		.asString();

		System.out.println(r2);  
		System.out.println("########################");
		
		GetCourses gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
  String LinkedIn=  gc.getLinkedIn();
  String Instructor=  gc.getInstructor();
  String Expertise=  gc.getExpertise();
      System.out.println("####getLinkedIn: "+LinkedIn);
  System.out.println("####get Instructor: "+Instructor);
  System.out.println("####get Expertise: "+Expertise);
  
  List<Api> listPrice = gc.getCourses().getApi();

  
    for(int i = 0; i<listPrice.size(); i++)
    {
    	 String CourseTitle = gc.getCourses().getApi().get(i).getCourseTitle();
    	 
    	 if(CourseTitle.equalsIgnoreCase("SoapUI Webservices testing"))
    	 {
    		 String price =  gc.getCourses().getApi().get(i).getPrice();
    		 System.out.println("#### Price is: "+price);
    		 break;
    	 }
    	
    }
  
  //Get all course title which present in webautomation
    ArrayList<String> a = new ArrayList<String> ();
    
    List<WebAutomation>   ListWebAutomation = gc.getCourses().getWebAutomation();
    
    for(int j=0; j<ListWebAutomation.size(); j++)
    {
    	String CouseTitle = gc.getCourses().getWebAutomation().get(j).getCourseTitle();
    	System.out.println(j+" Course Title is: "+CouseTitle);
    	
    	a.add(ListWebAutomation.get(j).getCourseTitle());
    }
  
  List<String> ExpectedList = Arrays.asList(ExpectedString);
  Assert.assertTrue(a.equals(ExpectedList));
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

		}

}
