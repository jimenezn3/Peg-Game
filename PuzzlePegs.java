// Nick Jimenez
// jimenezn3@mailbox.winthrop.edu

import java.util.*;

public class PuzzlePegs {

	public static boolean isValidJump(char[] board_, int[][] move, Stack<char[]> stackBoard_, Stack<String> stackMoves_, int lastPeg_) {

		for (int i = 0; i < 36; i++) {

			if ((board_[move[i][0]] == 'x') && (board_[move[i][1]] == 'x') && (board_[move[i][2]] == 'o')) {
				
				board_[move[i][0]] = 'o';
				board_[move[i][1]] = 'o';
				board_[move[i][2]] = 'x';
				
				char[] clone = board_.clone();
				stackBoard_.push(clone);
				
				if (isValidJump(board_, move, stackBoard_, stackMoves_, lastPeg_)) {
					stackMoves_.push(" Jumped from " + move[i][0] + " to " + move[i][2] + ", eliminated " + move[i][1]);
					return true;
				}
				
				else {
					stackBoard_.remove(clone);
					board_[move[i][0]] = 'x';
					board_[move[i][1]] = 'x';
					board_[move[i][2]] = 'o';
				}
			}
		}

		int pegCount = 15;
		int pegPosition = 16;   

		for (int i = 1; i <= 15; i++) {

			if (board_[i] == 'o'){
				pegCount--;
			}
			else{
				pegPosition = i;
			}
		}
		if ((pegCount == 1) && ((lastPeg_ == pegPosition) || (lastPeg_ == 16))){
			return true;
		}

		return false;
	}

	public static void printBoard(char[] board_) {

		System.out.println("    " + board_[1]);
		System.out.println("   " + board_[2] + " " + board_[3]);
		System.out.println("  " + board_[4] + " " + board_[5] + " "+ board_[6]);
		System.out.println(" " + board_[7] + " " + board_[8] + " " + board_[9] + " " + board_[10]);
		System.out.println(board_[11] + " " + board_[12] + " " + board_[13] + " " + board_[14] + " " + board_[15]);
		System.out.println();
	}

	public static Stack<char[]> flip(Stack<char[]> charArray_) {

		Stack<char[]> flippedStack = new Stack<char[]>();

		while (!charArray_.empty()) {
			char[] temp = charArray_.pop();
			flippedStack.push(temp);
		}
		return flippedStack;
	}
	
	public static void main(String[] args) {
		int[][] validMoves = {   // ALL VALID MOVES
						{1, 2, 4},
						{1, 3, 6},
						{2, 4, 7},
						{2, 5, 9},
						{3, 5, 8},
						{3, 6, 10},
						{4, 2 ,1},
						{4, 5, 6},
						{4, 7, 11},
						{4, 8, 13},
						{5, 8, 12},
						{5, 9, 14},
						{6, 3, 1},
						{6, 5, 4},
						{6, 9, 13},
						{6, 10, 15},
						{7, 4, 2},
						{7, 8, 9},
						{8, 5, 3},
						{8, 9, 10},
						{9, 5, 2},
						{9, 8, 7},
						{10, 6, 3},
						{10, 9, 8},
						{11, 7, 4},
						{11, 12, 13},
						{12, 8, 5},
						{12, 13, 14},
						{13, 12, 11},
						{13, 8, 4},
						{13, 9, 6},
						{13, 14, 15},
						{14, 13, 12},
						{14, 9, 5},
						{15, 10, 6},
						{15, 14, 13}
					};


		int startHole;
		int lastPeg = 16;

		char[] pegBoard = new char[16];
		Stack<char[]> stackBoard = new Stack<char[]>();
		Stack<String> stackMoves = new Stack<String>();


		if (args.length == 0) { // DEFAULT TO STARTING POINT 13
			startHole = 13;
		}
		else {	// CUSTOM STARTING POINT
			startHole = Integer.parseInt(args[0]);

			if (args.length == 2){ // CUSTOM ENDING POINT
				lastPeg = Integer.parseInt(args[1]);
			}
		}

		for (int i = 1; i <= 15; i++) {

			if (i == startHole)
				pegBoard[i] = 'o';
			else
				pegBoard[i] = 'x';
		}

		printBoard(pegBoard);
		System.out.println("Initial Board; Starting Hole at: " + startHole);
		System.out.println("-------------------------------------");

		if (isValidJump(pegBoard, validMoves, stackBoard, stackMoves, lastPeg)) {

			stackBoard = flip(stackBoard);

			while(!stackBoard.empty()) {
				printBoard(stackBoard.pop());
				System.out.println(stackMoves.pop());
				System.out.println("-------------------------------------");
			}
		}
		else{
			System.out.println("No solution");
		}
	}
}