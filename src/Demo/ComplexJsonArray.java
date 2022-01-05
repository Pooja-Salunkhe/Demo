package Demo;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonArray
{

	public static void main(String[] args)
{
		 
    JsonPath js = new JsonPath(Payloads.ComplexJson());
    
  //  1. Print No of courses returned by API
    int count = js.getInt("courses.size()");
    System.out.println("count is: "+count);
    
    //2.Print Purchase Amount
   int PurchaseAmount =  js.getInt("dashboard.purchaseAmount");
   System.out.println("Purchase Amount is: "+PurchaseAmount);
   
   //3. Print Title of the first course
   String FirstTitle = js.get("courses[0].title");
   System.out.println("FirstTitle is: "+FirstTitle);
  
   String SecondTitle = js.get("courses[1].title");
   System.out.println("SecondTitle is: "+SecondTitle);
   
   String ThirdTitle = js.get("courses[2].title");
   System.out.println("ThirdTitle is: "+ThirdTitle);
   
   int FirstPrice = js.getInt("courses[0].price");
   int SecondPrice = js.getInt("courses[1].price");
   int ThiredPrice = js.getInt("courses[2].price");
   
   System.out.println("price is 1: "+FirstPrice+" ,2: "+SecondPrice+" ,3: "+ThiredPrice);
   
  //4. Print All course titles and their respective Prices 
   for(int i=0; i<count; i++)
   {
	 String Title =  js.get("courses["+i+"].title");
	 System.out.println("Title is : "+Title);
	 
	 int price = js.getInt("courses["+i+"].price");
	 System.out.println("Price is: "+price);
	 
	 int copies = js.getInt("courses["+i+"].copies");
	 System.out.println("copies: "+copies);
	 
	 //Print no of copies sold by RPA Course
	 if(Title.equalsIgnoreCase("RPA"))
	 {
		 int copie = js.getInt("courses["+i+"].copies");
		 System.out.println("copies of RPA: "+copie);
		 break;
	 }
	 
	 
   }
   
   //6. Verify if Sum of all Course prices matches with Purchase Amount
   int SumOfCP = 0;
   for (int j=0; j<count; j++)
   {
	   int copies = js.getInt("courses["+j+"].copies");
	   int price = js.getInt("courses["+j+"].price");
	    SumOfCP = SumOfCP+(copies*price);
	   System.out.println("SumOfCP: "+SumOfCP);

   }
   
   if(SumOfCP == PurchaseAmount)
   {
	   System.out.println("Purchase amount is matched");
   }
   else
   {
	   System.out.println("Purchase amount is not matched");
   }
   
   
}
	

}