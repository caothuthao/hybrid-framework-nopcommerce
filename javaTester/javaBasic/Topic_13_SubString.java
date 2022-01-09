package javaBasic;

public class Topic_13_SubString {

	public static void main(String[] args) {
		String text2 = "  Xpath   = //a[text()='Logout']";
		
		String[] lstText = text2.split("=");
		String finalString = "";
		
		for (int i = 1; i<lstText.length-1; i++) {
			finalString = finalString.concat(lstText[i].trim()).concat("=");			
		}
		
		finalString = finalString + lstText[lstText.length-1];
		
		System.out.println("Final String: " + finalString);
		System.out.println("Prefix: " + lstText[0].trim().toLowerCase());
	}
}
