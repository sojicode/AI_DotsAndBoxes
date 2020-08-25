import java.util.*;

/**
 * 
 * Game-board modeled by weighted dots and boxes.
 * 
 * For example, dimension (2 * 2) board filled with random number
 * 
 * [y][x]                                     [0][0]                       [0][0]
 *   •               •               •          •        •        •          • –––––– • –––––– •
 *    (random number) (random number)               02       04              |   Hu   |   AI   |
 *   •               •               •   --->   •        •        •   --->   • –––––– • –––––– • 
 *    (random number) (random number)               05       03              |   AI   |   AI   |
 *   •               •               •          •        •        •          • –––––– • –––––– •
 *   
 *   									              y           x
 *  Draw a line from left to right(Horizontal) : [dimension+1][dimension] 
 * 
 *      [0][0]   [0][1]
 *  [0][0] • ––––> • ––––>
 *         |       |      |       boolean[3][2] --> F F 
 *  [1][0] • ––––> • ––––>                          F F 
 *         |       |      |                         F F
 *  [2][0] • ––––> • ––––>    
 *   
 *   					
 *   				                             y           x   
 *  Draw a line from up to down(Vertical) : [dimension][dimension+1]   
 * 
 *      [0][0]  [0][1]  [0][1]
 *  [0][0] • ––––– • ––––– •
 *         |       |       |      boolean[3][2] --> F F F
 *         v       v       v                        F F F
 *  [1][0] • ––––– • ––––– •
 *         |       |       | 
 *         v       v       v  
 *           –––––   –––––
 *           
 */

public class Gameboard {
	
	//how big is the board
	public int dimension;
	
	//get the dot-box information about a number in the dot-box and whether it is occupied by AI or Human
	public Dotbox[][] dotboxx;
	
	//represents a horizontal line
	public boolean[][] axisX;
	
	//represents a vertical line
	public boolean[][] axisY;
	
	public int scoreAI;
	
	public int scoreHuman;
	
	public int leftLines; //for counting how many lines are left
	
	public int evaluation; //for calculation to choose the best option later
	
	public Dotbox endMove; //to follow up
	

	//constructor 1
	public Gameboard(int dimension, Dotbox[][] dotboxx, boolean[][] axisX, boolean[][] axisY, int scoreAI,
			int scoreHuman, int leftLines, int evaluation, Dotbox endMove) {
		
		super();
		this.dimension = dimension;
		this.dotboxx = dotboxx;
		this.axisX = axisX;
		this.axisY = axisY;
		this.scoreAI = scoreAI;
		this.scoreHuman = scoreHuman;
		this.leftLines = leftLines;
		this.evaluation = evaluation;
		this.endMove = endMove;
	}
	
	//constructor 2 
	public Gameboard(int dimension) {
		
		this.dimension = dimension;
		
		this.scoreAI = 0;
		this.scoreHuman = 0;
		
		this.evaluation = 0;
		
		//for marking availability of a line((Horizontal)
		axisX = new boolean[dimension+1][dimension]; //example)dimension=2, axisX = new boolean[3][2]
		checkBooleanArray(axisX);
		
		//for marking availability of a line((Vertical)
		axisY = new boolean[dimension][dimension+1];//example)dimension=2, axisY = new boolean[2][3]
		checkBooleanArray(axisY);
		
		dotboxx = new Dotbox[dimension][dimension]; //create a game-board with dot-boxes(by dimension)
		setRandomBoxNum(dotboxx);
		
		//depends on dimension of a game-board, it gives you number how many lines are available
		leftLines = ((dimension+1) * (dimension)) * 2;
	
	}

	// getter and setter 
	public int getScoreAI() {
		return scoreAI;
	}

	public int getScoreHuman() {
		return scoreHuman;
	}

	//for calculation how many lines are left
	public int getLeftLines() {
		return leftLines;
	}

	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}

	public Dotbox getEndMove() {
		return endMove;
	}

	public void setEndMove(Dotbox endMove) {
		this.endMove = endMove;
	}
	
	//Boolean arrays filled with false by default, check it again for safety
	private void checkBooleanArray(boolean[][] axis) {
		
		int ylength = axis.length;
		int xlength = axis[0].length;
		
		for(int i = 0; i < ylength; i++) {
			for(int j = 0; j < xlength; j++) {
				axis[i][j] = false;
			}
		}
		
		//for checking initial boolean arrays
		for(int i = 0; i < ylength; i++) {
			for(int j = 0; j < xlength; j++) {
				if(axis[i][j]) {
					//System.out.print(" T ");
				} else {
					//System.out.print(" F ");
				}
			}
			//System.out.println();
		}
		//System.out.println();
		
	}
	
	//generate random numbers and give it to the dot-box
	private void setRandomBoxNum(Dotbox[][] dotboxx2) {
		
		for(int i = 0; i < dotboxx.length; i++) {
			for(int j = 0; j < dotboxx[i].length; j++) {
				dotboxx[i][j] = new Dotbox(new Random().nextInt(5)+1);   //choose a random number from 0 to 4 and +1(1~5)
			}
		}
		
	}

	//display a game board
	public void drawGameboard() {

		//show numbers of x-coordinate  
		String xNumber = "      ";
 
		for(int x = 0; x < dimension+1; x++) {
			
			if(x >= 10) {
				xNumber += "x="+ x + "   ";
			}else {
				xNumber += "x="+ x + "    ";
			}
		}
		System.out.println(xNumber);

		//show numbers of y-coordinate 
		for(int y = 0; y < dimension+1; y++) {

			String yNumber = " y=";
			yNumber += y;
			
			if(yNumber.length() <= 4) {
				yNumber += "   ";
			} else {
				yNumber += "  ";
			}

			//if the point is selected, it draws a line
			for(int x = 0; x < dimension; x++) {

				//if the point is selected, it draws a line
				if(axisX[y][x]) {
					yNumber += "\u2022 –––– ";
				} else {
					yNumber += "\u2022      ";
				}
			}

			yNumber += "\u2022"; //end point

			System.out.println(yNumber);

			//get random numbers in each dot-box
			if(y < dimension) {
				getBoxNumAndLine(y);
			}
		}

		//below the game-board, show the score
		System.out.println();
		System.out.println("(Score)   AI  : "+scoreAI);
		System.out.println("(Score) Human : "+scoreHuman + "\n");


	}
	
	//check whether a dot-box is occupied or not. 
	//if nobody occupied, draw a number.
	//it draws a line by a player's decision
	private void getBoxNumAndLine(int y) {

		String areaY = "       "; 
		String mid;

		for(int x = 0; x < dimension+1; x++) {

			if(x < dimension) { 

				String who = dotboxx[y][x].getOccupy();

				//check if it's occupied, get the name from the player
				if(who != "Nobody") {
					mid = who.substring(0,2); //first two letters('AI' or 'Hu')
					
				} else {
					mid = writeNum(y,x); //fill with a random number inside the dot-box
				}
				
			} else {
				mid = " ";
			}

			//draw Y-axis with '|'
			if(axisY[y][x]) {
				areaY += "|  " + mid + "  ";
			} else {
				areaY += "  "+ mid + "   ";
			}
		}
		System.out.println(areaY);

	}

	//organize output with double figures(matches spots)
	private String writeNum(int y, int x) {
		
		String mid = Integer.toString(dotboxx[y][x].getDotboxNum());
		
		switch(mid) {
		case "1":
			mid = "01";
			break;
		case "2":
			mid = "02";
			break;
		case "3":
			mid = "03";
			break;
		case "4":
			mid = "04";
			break;
		case "5":
			mid = "05";
			break;
			
		}
		return mid;
	}
	
	//for getting move options, it will be used by alphaBetaPruning function in Minimax class
	public List<Gameboard> getMovesList(String turn) {
		
		//create a list to save moves
		List<Gameboard> moves = new ArrayList<>();
		
		drawX_axis(moves, turn);
		drawY_axis(moves, turn);
		
		//shuffle moves-list with random function
		Collections.shuffle(moves, new Random());
		
		return moves;
	}
	
	//To draw a horizontal line, iterate through all points and if it's available,
	//add the line to the list.
	public void drawX_axis(List<Gameboard> moves, String turn) {
		
		//X-axis
		for(int y = 0; y < dimension+1; y++) {
			for(int x = 0; x < dimension; x++) {
				
				if(!axisX[y][x]) { //if it's not used
					
					//duplicate a game-board
					Gameboard cloneGB = this.duplicate();
					
					//isX_axis = true
					cloneGB.isLineAdded(y, x, true);
					cloneGB.detectLine(y, x, true, turn);
					moves.add(cloneGB);
	
				}
			}
		}	
		
	}

	private void drawY_axis(List<Gameboard> moves, String turn) {
		
		//Y-axis
		for(int y = 0; y < dimension; y++) {
			for(int x = 0; x < dimension+1; x++) {
				
				if(!axisY[y][x]) {
					
					//duplicate a game-board
					Gameboard cloneGB = this.duplicate();
					
					// isX_axis = false(if it's Y-axis)
					cloneGB.isLineAdded(y, x, false);
					cloneGB.detectLine(y, x, false, turn);
					moves.add(cloneGB);
				}
			}
		}
	}
	

	//purpose to duplicate every element in the game-board
	private Gameboard duplicate() {
		
		//duplicate all boolean value of X-axis
		boolean[][] axisXclone = new boolean[dimension+1][dimension];
		for(int x = 0; x < dimension+1; x++) {
			axisXclone[x] = Arrays.copyOf(axisX[x], dimension);
 		}
		
		//duplicate all boolean value of Y-axis
		boolean[][] axisYclone = new boolean[dimension][dimension+1];
		for(int y = 0; y < dimension; y++) {
			axisYclone[y] = Arrays.copyOf(axisY[y], dimension+1);
 		}
		
		//duplicate all dot-boxes
		Dotbox[][] dotboxClone = new Dotbox[dimension][dimension];
		for(int s = 0; s < dimension; s++) {
			for(int k = 0; k < dimension; k++) {
				dotboxClone[s][k] = dotboxx[s][k].duplicate();
			}
		}
		

		return new Gameboard(dimension, dotboxClone, axisXclone, axisYclone, scoreAI, //create a duplicate game-board
			scoreHuman, leftLines, evaluation, endMove);
	}
	
	//Depends on a given line, it detects whether a dot-box is inside boundary or not
	//also updates end-move and left-line value
	public boolean isLineAdded(int y, int x, boolean isX_axis) {
		
		//if you draw a line to X-axis(––), the boundary is 0 <= y < dimension, 0 <= x < dimension-1
		//if it is over the X-axis boundary
		if(isX_axis && (x < 0 || x > (dimension-1) || y < 0 || y > dimension)) {
			System.out.println("Can't draw a horizontal line, try again!\n");
			
			return false;
			
		//if you draw a line to Y-axis(|), the boundary is 0 <= y < dimension-1, 0 <= x < dimension
		//	if it is over the Y-axis boundary
		} else if(!isX_axis && (x < 0 || x > dimension || y < 0 || y > (dimension-1))) {
			System.out.println("Can't draw a vertical line, try again!\n");
			
			return false;
			
		}
		
		//draw a line to X-axis, the point is available(it was initially false)
		if(isX_axis && !axisX[y][x]) {
			
			//take this X-axis line
			endMove = new Dotbox(y, x, true); //change to true for boolean axisX
			leftLines--;                      //subtract this line from leftLines
			axisX[y][x] = true;
			
			return true;
			
		  //draw a line to Y-axis, the point is available(it was initially false)
		} else if(!isX_axis && !axisY[y][x]) {
			
			//take this Y-axis line
			endMove = new Dotbox(y, x, false); //false for boolean axisX --> axisY
			leftLines--; 					   //subtract this line from leftLines
			axisY[y][x] = true;
			
			return true;	
			
		}
		
		System.out.println("\nThe line is already in use! Please try a different one.\n");
		
		return false;
			
	}
	
	
	//Depends on a given line, it detects whether a dot-box is occupied or not 
	//if a line is middle of attached dot-boxes, it detects both dot-boxes
	//if a line is boundary of a game-board, it detects only the dot-box that includes the line
	public void detectLine(int y, int x, boolean isX_axis, String turn) {
		
		if(isX_axis) { //if line is on X-axis, mid-line goes through both if-statement
			
			if(y > 0) {
				//if it's bottom line of the game-board, 
				boolean point1 = axisY[y-1][x];
				boolean point2 = axisY[y-1][x+1];
				boolean point3 = axisX[y-1][x];
				boolean point4 = axisX[y+1-1][x];
				
				if(point1 && point2 && point3 && point4) { //if all side of a dot-box are used,
					//mark occupancy
					dotboxx[y-1][x].setOccupy(turn); 
					doScore(y-1, x, turn);
				}
			}
			
			if(y < dimension) {
				
				boolean point1 = axisY[y][x];
				boolean point2 = axisY[y][x+1];
				boolean point3 = axisX[y][x];
				boolean point4 = axisX[y+1][x];
				
				if(point1 && point2 && point3 && point4) { //if all side of a dot-box are used,
					//mark occupancy
					dotboxx[y][x].setOccupy(turn); 
					doScore(y, x, turn);
				}
			}	
			
		} else { // if line is on Y-axis, mid-line goes through both if-statement
			
			if(x > 0) {
				//right end side
				boolean point1 = axisY[y][x-1];
				boolean point2 = axisY[y][x+1-1];
				boolean point3 = axisX[y][x-1];
				boolean point4 = axisX[y+1][x-1];
				
				if(point1 && point2 && point3 && point4) { //if all side of a dot-box is used,
					//mark occupancy
					dotboxx[y][x-1].setOccupy(turn); 
					doScore(y, x-1, turn);
				}
			}
			
			if(x < dimension) {
				
				boolean point1 = axisY[y][x];
				boolean point2 = axisY[y][x+1];
				boolean point3 = axisX[y][x];
				boolean point4 = axisX[y+1][x];
				
				if(point1 && point2 && point3 && point4) { //if all side of a dot-box is used,
					//mark occupancy
					dotboxx[y][x].setOccupy(turn); 
					doScore(y, x, turn);
				}
			}
		}
		
	}

	//updates a score and evaluates a heuristic value
	//E(n) = Human(n):total score for human - AI(n):total score for AI
	private int doScore(int y, int x, String turn) {
		
		if(turn == "AI") {							  //if it's AI's turn
			scoreAI += dotboxx[y][x].getDotboxNum();  //add it to the score from the number of the dot-box 
		} else {									  //if it's Human's turn
			scoreHuman += dotboxx[y][x].getDotboxNum();
		}
		
		evaluation = scoreHuman - scoreAI;            //E(n) =  Human(n) - AI(n)
		
		return evaluation;
	
	}
	
	
	

}
