package Jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class LoginJira {

	public static void main(String[] args)
	
	{
		
		//https://developer.atlassian.com/server/jira/platform/rest-apis/
				//	https://docs.atlassian.com/software/jira/docs/api/REST/8.13.15/#component-updateComponent

		// Login JIRA
		RestAssured.baseURI = "http://localhost:8080";
		SessionFilter session = new SessionFilter();
		
		//Learn about sessionFilter
		String Responce1 = given().log().all().header("Content-Type","application/json").body("{ \"username\": \"Admin\", \"password\": \"Admin\" }").filter(session).
		when().post("/rest/auth/1/session").
		then().log().all().extract().response().asString();
		
		//add comment
		String expectedMessage ="Hi how are you??";
		String addCommentResponse = given().pathParam("key", "10000").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).
		when().post("/rest/api/2/issue/{key}/comment").
		then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js=new JsonPath(addCommentResponse);

		String commentId= js.getString("id");
		
		//Add attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10000").header("Content-Type","multipart/form-data").
		multiPart("file", new File("Jira.txt"))
		.when().post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println("####################Add Attachment############################");

	/*	//get issue
		String GetIssueResponce = given().pathParam("Key", "10000").filter(session).when().get("/rest/api/2/issue/{key}").then().extract().response().asString();
		System.out.println("#################################################");
		System.out.println("GetIssueResponce:"+GetIssueResponce);
		
		JsonPath js1 = new JsonPath(GetIssueResponce);
		
		int commentCount = js1.getInt("fields.comment.comments.size()");
		
		for (int i=0; i<commentCount; i++)
		{
			String CommentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
			
			if(CommentIdIssue.equalsIgnoreCase(commentId))
			{
				String Message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println("Message: "+Message);
				Assert.assertEquals(Message, ExpectedMessage);
			}
		}
		   */
		
		//Get Issue

		String issueDetails=given().filter(session).pathParam("key", "10000")

		.queryParam("fields", "comment")

		.log().all().when().get("/rest/api/2/issue/{key}").then()

		.log().all().extract().response().asString();

		System.out.println(issueDetails);
		System.out.println("####################Get issue############################");

		JsonPath js1 =new JsonPath(issueDetails);

		int commentsCount=js1.getInt("fields.comment.comments.size()");

		for(int i=0;i<commentsCount;i++)

		{

		String commentIdIssue =js1.get("fields.comment.comments["+i+"].id").toString();

		if (commentIdIssue.equalsIgnoreCase(commentId))

		{

		String message= js1.get("fields.comment.comments["+i+"].body").toString();

		System.out.println(message);

		Assert.assertEquals(message, expectedMessage);

		}

		}

		



		


		///////////
		
		



		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
