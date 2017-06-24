package util;
import java.util.Scanner;

public class Console {
	static Scanner sc = new Scanner(System.in);
	public static boolean askYorN(String prompt){
		//while(true){
			String answer;
			System.out.print(prompt +" Y/N:");						
			answer = sc.nextLine();
			if(answer.equalsIgnoreCase("Y"))
				return true;
			else if(answer.equalsIgnoreCase("N"))
				return false;
			else
				return false;				
		//}		
	}
}
