// Christopher Lenk for COS 210
// For the game board, m = width, n = height

import java.util.*;

public class Chomp {
	private int[][] board;
	private int firstBlankCol;
	private int firstBlankRow;
	private int usrLastX;
	private int usrLastY;

	// Constructor
	public Chomp(int m, int n) {
		// Create game board, initializing each spot to '1'
		board = new int[n][m];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = 1;
			}
		}
		firstBlankCol = board[0].length+1;
		firstBlankRow = board.length+1;
	}
	
	// Getters
	public int getMaxY() {
		return board.length;
	}
	public int getMaxX() {
		return board[0].length;
	}
	public int getUsrLastX() {
		return usrLastX;
	}
	public int getUsrLastY() {
		return usrLastY;
	}
	public int getFirstBlankCell() {
		return firstBlankCol;
	}
	public int getFirstBlankRow() {
		return firstBlankRow;
	}

	// Setters
	public void setUsrLast(int x, int y) {
		usrLastX = x;
		usrLastY = y;
	}
	
	// Prints out what the board currently looks like
	public void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					System.out.print(" X ");
				} else {
					System.out.print("   ");
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	// Removes the 'cookie' at the given coordinates and all those to the right and down from it
	// Pass in x and y as values starting at 1 - it will automatically convert them to array indices
	public void chomp(int x, int y) {
		x--;
		y--;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i >= y && j >= x) {
					board[i][j] = 0;
				}
			}
		}
		updateFirsts();
	}
	
	// Updates first blank cell and row 
	public void updateFirsts() {
		firstBlankCol = board[0].length+1;
		firstBlankRow = board.length+1;
		int blankInRow;
		for (int i = 0; i < board.length; i++) {
			blankInRow = -1;
			for (int j = 0; j < board[i].length; j++) {
				if (i == 0 ) { // First row
					if (board[i][j] == 0 && firstBlankCol == board[0].length+1) { // First empty cell
						firstBlankCol = j+1;	
					} else if (j == board[i].length-1 && firstBlankCol == board[0].length+1) { // First row is full
						firstBlankCol = board[0].length+1; // This index is out of the array because the row is full; the first empty cell in this row isn't in the array
					}
				} else { // Subsequent rows
					if (board[i][j] == 0 && blankInRow == -1) { // First empty cell in this row
						blankInRow = j;	
					} else if (j == board[i].length-1 && blankInRow == -1) { // This row is full
						blankInRow = board[0].length; // This index is out of the array because the row is full; the first empty cell in this row isn't in the array
					}
					
					if (blankInRow == 0 && firstBlankRow == board.length+1) { // Find the first blank row
						firstBlankRow = i+1;
					}
				}
			}
		}
	}
	
	// Tells the computer to take a turn
	public void ai() {
		int x, y;
		
		 if (firstBlankCol == 2) { // Single column case - reduce it to only the last cookie for them to choose 
			x = 1;
			y = 2;
		} else if (firstBlankRow == 2) { // Single row case - reduce it to only the last cookie for them to choose 
			x = 2;
			y = 1;
		} else if (isSquare() && board[1][1] != 0) { // Square board case
			x = 2;
			y = 2;
		} else if (isThin()) { // 2-by-n or n-by-2 case
			if (board[2][0] == 0) { // Horz case
				int first = getFirstBlankinRow(1);
				int second = getFirstBlankinRow(2);
				
				if (first == second) { // Board is a rectangle
					x = firstBlankCol - 1;
					y = firstBlankRow - 1;
				} else { // Board is uneven - take away from the longer half 
					if (first > second) {
						x = second + 2;
						y = 1;
					} else {
						x = first + 2;
						y = 2;
					}
				}
			} else { // Vert case
				int first = getFirstBlankinCol(1);
				int second = getFirstBlankinCol(2);
				
				if (first == second) { // Board is a rectangle
					x = firstBlankCol - 1;
					y = firstBlankRow - 1;
				} else { // Board is uneven - take 2 from the longer half 
					if (first > second) {
						x = 1;
						y = second + 2;
					} else {
						x = 2;
						y = first + 2;
					}
				}
			}
		} else if (board[1][1] == 0) { // Square case endgame
			if (firstBlankCol == firstBlankRow) { // Both ends of the L are same length 
				x = firstBlankCol-1;
				y = 1;
			} else { // Ends of the L are unequal length
				if (firstBlankCol > firstBlankRow) {
					x = firstBlankRow;
					y = 1;
				} else {
					x = 1;
					y = firstBlankCol;
				}
			}
		} else { // Default strategy - remove as little as possible (conservative)
			if (firstBlankRow > firstBlankCol) {
				x = getLastFullinRow(firstBlankRow-1);
				y = firstBlankRow-1;
				
				if (x == -1) {
					x = firstBlankCol-1;
				}
			} else {
				x = firstBlankCol-1;
				y = getLastFullinCol(firstBlankCol-1);
				
				if (y == -1) {
					y = firstBlankRow-1;
				}
			}
		}

		chomp(x, y);
		System.out.println("Computer chomped ("+x+", "+y+").");
	}
	
	// Returns the first blank in given row (starting at 1, not 0)
	public int getFirstBlankinRow(int row) {
		int blankInRow = -1;
		
		for (int i = 0; i < board[row-1].length; i++) {
			if (board[row-1][i] == 0 && blankInRow == -1) { // First empty cell in this row
				blankInRow = i;	
			} else if (i == board[row-1].length-1 && blankInRow == -1) { // This row is full
				blankInRow = board[row-1].length; // This index is out of the array because the row is full; the first empty cell in this row isn't in the array
			}
		}
		return blankInRow;
	}
	
	// Returns the first blank in given column (starting at 1, not 0)
	public int getFirstBlankinCol(int col) {
		int blankInCol = -1;
		
		for (int i = 0; i < board.length; i++) {
			if (board[i][col-1] == 0 && blankInCol == -1) { // First empty cell in this column
				blankInCol = i;	
			} else if (i == board[i].length-1 && blankInCol == -1) { // This column is full
				blankInCol = board[i].length; // This index is out of the array because the column is full; the first empty cell in this column isn't in the array
			}
		}
		return blankInCol;
	}
	
	// Returns the last filled cell in given row (starting at 1, not 0); returns a column index
	public int getLastFullinRow(int row) {
		int filledInRow = -1;
		
		for (int i = 0; i < board[row-1].length; i++) {
			if (board[row-1][i] == 1) { // Filled cell
				filledInRow = i;	
			}
		}
		return filledInRow + 1;
	}
	
	// Returns the last filled cell in given col (starting at 1, not 0); returns a row index
	public int getLastFullinCol(int col) {
		int filledInCol = -1;
		
		for (int i = 0; i < board.length; i++) {
			if (board[i][col-1] == 1) { // Filled cell
				filledInCol = i;	
			}
		}
		return filledInCol + 1;
	}
	
	// Returns true if the board is currently a square or an L with sides of equal length
	public boolean isSquare() {
		updateFirsts();
		
		if (firstBlankCol == firstBlankRow) {
			return true;
		}
		else return false;
	}	
	
	// Returns true if the board is 2-by-n or n-by 2
	public boolean isThin() {
		updateFirsts();
		
		if (firstBlankCol == 3) { // Check if the board's 2-by-n
			return true;
		} else if (firstBlankRow == 3) { // Check if the board is n-by-2
			return true;
		} else {
			return false;
		}
	}
	
	// Returns true if the final "cookie" is the only one left
	public boolean isOver() {
		if (board[0][1] == 0 && board[1][0] == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// Get board size
		int m = 0;
		int n = 0;
		while (true) {
			try {
				System.out.print("Enter m for m x n board: ");
				m = sc.nextInt();
				System.out.print("Enter n for m x n board: ");
				n = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				sc.next(); // Throw away newline character from input
				System.out.println("Error! Must provide m and n as integer values!");
				continue;
			}
		}
		
		// Decide order of players
		boolean isUsrTurn;
		int mode;
		while (true) {
			try {
				System.out.println("Possible modes: ");
				System.out.println("1: User goes first, Computer goes second");
				System.out.println("2: Computer goes first, User goes second");
				System.out.print("Enter mode: ");
				mode = sc.nextInt();
				
				if (mode == 1) {
					isUsrTurn = true;
				} else if (mode == 2) {
					isUsrTurn = false;
				} else {
					throw new Exception("Invalid mode");
				}
				break;
			} catch (InputMismatchException e) {
				sc.next(); // Throw away newline character from input
				System.out.println("Error! Must provide an integer value for the mode!");
				continue;
			} catch (Exception e) {
				System.out.println("Error! Input must be a valid mode on the list!");
				continue;
			} 
		}
		System.out.println();

		Chomp game = new Chomp(m, n); // Create board
		game.print(); // Print the current board
		
		// Game loop
		while (true) {
			if (isUsrTurn) { // User's turn
				// Get coordinates to be chomped
				int x = 0;
				int y = 0;
				while (true) {
					try {
						System.out.print("Enter X-coordinate of cookie to chomp: ");
						x = sc.nextInt();
						System.out.print("Enter Y-coordinate of cookie to chomp: ");
						y = sc.nextInt();
						
						if (x >= game.getFirstBlankCell() || y >= game.getFirstBlankRow() || x < 1 || y < 1) 
							throw new Exception("x and y are out of bounds");
						break;
					} catch (InputMismatchException e) {
						sc.next(); // Throw away newline character from input
						System.out.println("Error! Input must be integers!");
						continue;
					} catch (Exception e) {
						System.out.println("Error! Must provide values that are within the game board!");
						continue;
					}
				}

				// Eat the specified cookie and chomp out the ones to the right and below it
				game.chomp(x, y); // -1 to handle off-by-one errors
				game.setUsrLast(x, y);
				game.print(); // Show results
				
				if (game.isOver()) { // Check for end-of-game condition
					System.out.println("You win!");
					break;
				}
				
				isUsrTurn = false;
			} else { // Computer's turn
				game.ai(); // Computer player's turn
				game.print(); // Show results
				if (game.isOver()) { // Check for end-of-game condition
					System.out.println("Computer wins!");
					break;
				}

				isUsrTurn = true;
			}
		}
	}
	
}
