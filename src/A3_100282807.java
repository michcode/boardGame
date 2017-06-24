/*
 * Dantao Li, 100282807, Assignment 3 Ex7.20
 */
import java.util.Scanner;
import util.*;
public class A3_100282807 {
	//These are class variables -- see text file for more details
	static char[][] board = new char[6][15];
	static String winner = "";
	static int column;		
	static int row;
	static boolean gameOver = false;
	static String nextPlayer = "Red";
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		while(!gameOver){
			playGame();
			while(Console.askYorN("Do you want to play the game again?")){
				System.out.println("Alright! The game has been reset...");
				gameOver = false;
				playGame();
			}
			System.out.println("Bye! Hope you enjoyed the game!");
			input.close();
		}		
	}//end of main
	
	public static void playGame(){
		creatNewboard();
		displayBoard();
		while (!gameOver) {
			if (nextPlayer.equals("Red")) {
				System.out.print("Drop a red disk at colum (0-6): ");
				column = findColumlocation(input.nextInt());
				row = findRowLocation(column);
				dropDisk(row, column, 'R');
				nextPlayer = "Yellow";
			} else {
				System.out.print("Drop a yellow disk at colum (0-6): ");
				column = findColumlocation(input.nextInt());
				row = findRowLocation(column);
				dropDisk(row, column, 'Y');
				nextPlayer = "Red";
			}
			displayBoard();
			gameOver = isConsecutiveFour(board);
		}
		System.out.println("The " + winner + " player won");
	}
	public static void creatNewboard() {
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 15;) {
				board[row][col] = '|';
				col=col+2;
			}
		}
		for (int row = 0; row < 6; row++) {
			for (int col = 1; col < 15;) {
				board[row][col] = ' ';
				col=col+2;
			}
		}
	}

	public static void displayBoard() {
		int count = 0;
		for (char[] u : board) {
			System.out.println(u);
			count++;
			if (count % 14 == 0)
				System.out.println("");
		}
		displayHorizontalLine();
	}

	public static void displayHorizontalLine() {
		for (int i = 0; i < 15; i++) {
			System.out.print("-");
		}
		System.out.println("");
	}

	public static int findRowLocation(int columnTemp) {
		int location = -1;
		Scanner retypeColumn = new Scanner(System.in);
		for (int row = 5; row >= 0; row--) {
			if (board[row][columnTemp] == ' ') {
				location = row;
				break;
			}
		}
		while (location == -1) {
			System.out.println("Column " + columnTemp
					+ " is full already, please choose another column: ");
			column = findColumlocation(retypeColumn.nextInt());
			location = findRowLocation(column);
		}
		return location;
	}

	public static int findColumlocation(int column) {
		int columnLocation = column;
		switch (column) {
		case 0:
			columnLocation = 1;
			break;
		case 1:
			columnLocation = 3;
			break;
		case 2:
			columnLocation = 5;
			break;
		case 3:
			columnLocation = 7;
			break;
		case 4:
			columnLocation = 9;
			break;
		case 5:
			columnLocation = 11;
			break;
		case 6:
			columnLocation = 13;
			break;
		default: {
			Scanner columnNum = new Scanner(System.in);
			while (columnLocation > 6 || columnLocation < 0) {
				System.out
						.print("Wrong column number! Please input a corret one (0-6): ");
				columnLocation = findColumlocation(columnNum.nextInt());
			}
		}
		}
		return columnLocation;
	}

	public static void dropDisk(int row, int column, char color) {
		// System.out.println("final column is : "+ column);
		board[row][column] = color;
	}

	public static boolean isConsecutiveFour(char[][] values) {
		return (isConsecutiveFourInHorizontal(values)
				|| isConsecutiveFourInVertical(values)
				|| isConsecutiveFourInDiagonalLeft(values) || isConsecutiveFourInDiagonalRight(values));
	}

	public static boolean isConsecutiveFourInHorizontal(char[][] values) {
		boolean result = false;
		for (int row = 0; row < values.length; row++) {
			for (int col = 1; col < values[0].length - 7; col += 2) {
				if (values[row][col] == values[row][col + 2]
						&& values[row][col] == values[row][col + 4]
						&& values[row][col] == values[row][col + 6]) {
					if (values[row][col] == 'R' || values[row][col] == 'Y') {
						setWinner(values[row][col]);
						result = true;
					}
				}
			}
		}
		return result;
	}

	public static boolean isConsecutiveFourInVertical(char[][] values) {
		boolean result = false;
		for (int row = 0; row < values.length - 3; row++) {
			for (int col = 1; col < values[0].length - 1; col += 2) {
				if (values[row][col] == values[row + 1][col]
						&& values[row][col] == values[row + 2][col]
						&& values[row][col] == values[row + 3][col]) {
					if (values[row][col] == 'R' || values[row][col] == 'Y') {
						setWinner(values[row][col]);
						result = true;
					}
				}
			}
		}

		return result;
	}

	public static boolean isConsecutiveFourInDiagonalLeft(char[][] values) {
		boolean result = false;
		for (int row = 0; row < values.length - 3; row++) {
			for (int col = 7; col < values[0].length; col += 2) {
				if (values[row][col] == values[row + 1][col - 2]
						&& values[row][col] == values[row + 2][col - 4]
						&& values[row][col] == values[row + 3][col - 6]) {
					if (values[row][col] == 'R' || values[row][col] == 'Y') {
						setWinner(values[row][col]);
						result = true;
					}
				}
			}
		}
		return result;
	}

	public static boolean isConsecutiveFourInDiagonalRight(char[][] values) {
		boolean result = false;
		for (int row = 0; row < values.length - 3; row++) {
			for (int col = 1; col < values[0].length - 7; col += 2) {
				if (values[row][col] == values[row + 1][col + 2]
						&& values[row][col] == values[row + 2][col + 4]
						&& values[row][col] == values[row + 3][col + 6]) {
					if (values[row][col] == 'R' || values[row][col] == 'Y') {
						setWinner(values[row][col]);
						result = true;
					}
				}
			}
		}
		return result;
	}

	public static void setWinner(char color){
		if (color == 'R')
			winner = "Red";
		else
			winner = "Yellow";
	}
}	