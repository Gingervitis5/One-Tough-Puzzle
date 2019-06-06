import java.util.ArrayList;
/* Puzzle #b89a65ca
   'C' = Circle
   'D' = Diamond
   'S' = Square
   '+' = Protruding
   '-' = Non-Protruding
*/

public class Main {
	
	public static ArrayList<PuzzlePiece> pieces = new ArrayList<PuzzlePiece>();
	public static ArrayList<PuzzlePiece> used = new ArrayList<PuzzlePiece>();
	public static PuzzlePiece[][] board = new PuzzlePiece[5][5];

	public static void main(String[] args) {
		
		initPieces();
		solve(board, 1, 1);
		int count = 0;
		for (int i = 1; i < board.length-1; i++) {
			for (int o = 1; o < board[0].length-1; o++) {
				System.out.print(board[i][o].toString(count, i ,o) + "\n");
				count++;
			}
		}
	}
	
	public static boolean solve(PuzzlePiece[][] array, int row, int col) {
		boolean solved = false;
		for (int i = 0; !solved && i < pieces.size(); i++){
			PuzzlePiece p1 = pieces.get(i); 
			used.add(pieces.get(i)); 
			pieces.remove(i); 
			PuzzlePiece p2 = Rotate(p1);
			PuzzlePiece p3 = Rotate(p2);
			PuzzlePiece p4 = Rotate(p3);
			if (canFit(p1, array, row, col)) { array[row][col] = p1; solved = next(array, row, col); }
			if (canFit(p2, array, row, col)) { array[row][col] = p2; solved = next(array, row, col); }
			if (canFit(p3, array, row, col)) { array[row][col] = p3; solved = next(array, row, col); }
			if (canFit(p4, array, row, col)) { array[row][col] = p4; solved = next(array, row, col); }
			if (!solved) { pieces.add(i, p1); 
                        	 used.remove(p1);}
		}
		if (!solved) {
			returnPrevious(array, row, col);
		}
		
		return solved;
	}
	
	public static void returnPrevious(PuzzlePiece[][] array, int row, int col) {
		col = col - 1;
		if (col < 1) {
			array[row-1][3] = null;
		}
		else {
			array[row][col] = null;
		}
	}
	
	public static boolean next(PuzzlePiece[][] array, int row, int col) {
		col = col + 1;
		if (row >= 3 && col > 3) { 
			return true;
		}
		else if (col > 3){
			return solve(array, row+1, 1);
		}
		else{
		   return solve(array, row, col);
      }
	}
	
	public static boolean canFit(PuzzlePiece p, PuzzlePiece[][] array, int row, int col) {
		if ( sameCriteria(p, array[row-1][col], "UP") && sameCriteria(p, array[row+1][col], "DOWN") && sameCriteria(p, array[row][col-1], "LEFT") && sameCriteria(p, array[row][col+1], "RIGHT")) {
			return true;
		}
		return false;
	}
	
	public static boolean sameCriteria(PuzzlePiece p1, PuzzlePiece p2, String direction) {
		if (p2 == null) {
			return true;
		}
		switch(direction) {
		case "UP":
			if (p1.top.shape == p2.bottom.shape && p1.top.count == p2.bottom.count && p1.top.cutOut != p2.bottom.cutOut) {
			return true;
			}
			return false;
			
		case "DOWN":
			if (p1.bottom.shape == p2.top.shape && p1.bottom.count == p2.top.count && p1.bottom.cutOut != p2.top.cutOut) {
				return true;
			}
			return false;
			
		case "LEFT":
			if (p1.left.shape == p2.right.shape && p1.left.count == p2.right.count && p1.left.cutOut != p2.right.cutOut) {
				return true;
			}
			return false;
			
		case "RIGHT":
			if (p1.right.shape == p2.left.shape && p1.right.count == p2.left.count && p1.right.cutOut != p2.left.cutOut) {
				return true;
			}
			return false;
			
		default:
			return false;
		}
	}
	
	public static PuzzlePiece Rotate(PuzzlePiece p) {
		PuzzlePiece newPiece = new PuzzlePiece(null, null, null, null);
		newPiece.top = p.right;
		newPiece.right = p.bottom;
		newPiece.bottom = p.left;
		newPiece.left = p.top;
		return newPiece;
	}
	
	public static void initPieces() {
		PuzzlePiece p0 = new PuzzlePiece(new PuzzlePiece.Side('C', 1, false),
										new PuzzlePiece.Side('D', 2, false),
										new PuzzlePiece.Side('S', 1, true),
										new PuzzlePiece.Side('D', 1, true)); pieces.add(p0);
				
		PuzzlePiece p1 = new PuzzlePiece(new PuzzlePiece.Side('S', 1, false),
										new PuzzlePiece.Side('C', 1, false),
										new PuzzlePiece.Side('D', 2, true),
										new PuzzlePiece.Side('D', 1, true)); pieces.add(p1);
		
		PuzzlePiece p2 = new PuzzlePiece(new PuzzlePiece.Side('C', 1, false),
										new PuzzlePiece.Side('S', 1, false),
										new PuzzlePiece.Side('S', 1, true),
										new PuzzlePiece.Side('C', 1, true)); pieces.add(p2);
			
		PuzzlePiece p3 = new PuzzlePiece(new PuzzlePiece.Side('S', 1, false),
										new PuzzlePiece.Side('D', 2, false),
										new PuzzlePiece.Side('C', 1, true),
										new PuzzlePiece.Side('D', 2, true)); pieces.add(p3);
						
		PuzzlePiece p4 = new PuzzlePiece(new PuzzlePiece.Side('C', 1, false),
										new PuzzlePiece.Side('D', 2, false),
										new PuzzlePiece.Side('S', 1, true),
										new PuzzlePiece.Side('D', 2, true)); pieces.add(p4);
						
		PuzzlePiece p5 = new PuzzlePiece(new PuzzlePiece.Side('C', 1, false),
										new PuzzlePiece.Side('C', 1, false),
										new PuzzlePiece.Side('C', 1, true),
										new PuzzlePiece.Side('D', 2, true)); pieces.add(p5);
						
		PuzzlePiece p6 = new PuzzlePiece(new PuzzlePiece.Side('D', 1, false),
										new PuzzlePiece.Side('S', 1, false),
										new PuzzlePiece.Side('S', 1, true),
										new PuzzlePiece.Side('C', 1, true)); pieces.add(p6);
						
		PuzzlePiece p7 = new PuzzlePiece(new PuzzlePiece.Side('C', 1, false),
										new PuzzlePiece.Side('S', 1, false),
										new PuzzlePiece.Side('S', 1, true),
										new PuzzlePiece.Side('D', 2, true)); pieces.add(p7);
						
		PuzzlePiece p8 = new PuzzlePiece(new PuzzlePiece.Side('D', 2, false),
										new PuzzlePiece.Side('D', 2, false),
										new PuzzlePiece.Side('D', 2, true),
										new PuzzlePiece.Side('S', 1, true)); pieces.add(p8);
	}

}

class PuzzlePiece {
	
	public Side top;
	public Side right;
	public Side bottom;
	public Side left;
	
	static class Side {
		public final char shape;
		public final int count;
		public final boolean cutOut;
		
		public Side(char shape, int count, boolean cutOut) {
			this.shape = shape;
			this.count = count;
			this.cutOut = cutOut;
		}
	}
	
	public PuzzlePiece(Side s1, Side s2, Side s3, Side s4) {
		this.top = s1;
		this.right = s2;
		this.bottom = s3;
		this.left = s4;
	}
	
	public String toString(int num, int row, int col) {
		String str = "Piece #" + (num) + " at " + (row-1) + "," + (col-1) + "\n";
		switch (this.top.shape) {
		case 'D': 
			if (this.top.count >= 2) {
				str += "top: DD\n";
				break;
			}
			else {
				str += "top: D\n";
				break;
			}
		case 'S':
			str += "top: S\n";
			break;
		case 'C':
			str += "top: C\n";
			break;
		}
		
		switch (this.right.shape) {
		case 'D': 
			if (this.right.count >= 2) {
				str += "right: DD\n";
				break;
			}
			else {
				str += "right: D\n";
				break;
			}
		case 'S':
			str += "right: S\n";
			break;
		case 'C':
			str += "right: C\n";
			break;
		}
		
		switch (this.bottom.shape) {
		case 'D': 
			if (this.bottom.count >= 2) {
				str += "bottom: DD\n";
				break;
			}
			else {
				str += "bottom: D\n";
				break;
			}
		case 'S':
			str += "bottom: S\n";
			break;
		case 'C':
			str += "bottom: C\n";
			break;
		}
		
		switch (this.left.shape) {
		case 'D': 
			if (this.left.count >= 2) {
				str += "left: DD\n";
				break;
			}
			else {
				str += "left: D\n";
				break;
			}
		case 'S':
			str += "left: S\n";
			break;
		case 'C':
			str += "left: C\n";
			break;
		}
		
		return str;
	}
	
}