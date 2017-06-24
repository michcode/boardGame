import util.*;
public class PackageTest {

	public static void main(String[] args) {
		while(Console.askYorN("Do you want to play the game again?")){
			System.out.println("Waiting! Resetting the game soon...");
		}
		System.out.println("Bye...");
	}

}
