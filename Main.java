
import java.util.*;

/**
 * 
 * This class is a main class to execute the code, 'Dots and Boxes'.
 * The human player always starts first and the AI player has the next turn. These turns alternate.
 * The game goes on until all the lines are drawn by the human or AI.
 * The console asks the human player for their move-option each time and it updates a game-board. 
 * You can also check the score of each player and how long it takes AI to make its moves.
 *
 */
public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		int y = 0; //y-coordinate
		int x = 0; //x-coordinate
		int dimension = 0;
		int plys = 0;
		String shift;
		boolean isX_axis = false;

		System.out.print("What dimension of the board do you want (dimension >= 2)? ");
		
		Scanner sc = new Scanner(System.in);
		dimension = Integer.parseInt(sc.nextLine().trim());

		System.out.print("How many plys the AI will search (plys >= 1)? ");
		plys = Integer.parseInt(sc.nextLine().trim());

		System.out.println();

		//create a game-board object
		Gameboard gameboard = new Gameboard(dimension);                

		String playTurn = "Human"; //The human has the first turn

		System.out.print("Algorithm >>> ");
		System.out.println("minimax using alpha-beta pruning " + plys + " plys\n");
		System.out.println("Please click the <ENTER> button to start a game!");

		sc.nextLine();

		while(!(gameboard.leftLines == 0)) { //until all lines are taken
			
			System.out.println("Player turn >> " + playTurn + "\n");

			gameboard.drawGameboard();

			if(playTurn == "AI") {

				Dotbox readyPlayerOne;

				//measure time (AI moves)
				long begin = System.currentTimeMillis();

				//call the minimax search(using alpha-beta pruning)
				readyPlayerOne = Minimax.minimax(gameboard, plys);

				long finish  = System.currentTimeMillis();
				long total = finish - begin;
				
				System.out.println("scoreHuman - scoreAI = "+gameboard.getEvaluation()+"\n");
	
				y = readyPlayerOne.getY();
				x = readyPlayerOne.getX();
				
				isX_axis = readyPlayerOne.isAxisX();

				if(gameboard.isLineAdded(y, x, isX_axis)) {  

					gameboard.detectLine(y, x, isX_axis, playTurn);

					System.out.println("AI move >> "+ "x=" + x + ", y=" + y + 
							(isX_axis? ", Horizontal" : ", Vertical") +", "+ total +"ms"+"\n");

					playTurn = "Human"; //change the turn 

				}

			} else {
				try { 
					
					sc = new Scanner(System.in);

					//input human player moves
					System.out.print("Enter x and y (ex. 0 0 or 0 1): "); 
					x = Integer.parseInt(sc.next());
					y = Integer.parseInt(sc.next());
					
					
					System.out.print("Enter h(horizontal) or v(vertical) (ex. h or v): "); 
					shift = sc.next().trim();
					
					System.out.println();

					//input for Horizontal
					if(shift.equals("h")) {
						isX_axis = true;
						
					} else if(shift.equals("v")) { //Vertical
						isX_axis = false;
						
					} else { //to catch the wrong input
						throw new NoSuchElementException();
					}

					if(gameboard.isLineAdded(y, x, isX_axis)) {
						gameboard.detectLine(y, x, isX_axis, playTurn);

						playTurn = "AI"; //change the turn 
					}

				} catch(Exception ex) {
					System.out.println("Something wrong, please check your input again!\n");
				}
			}

		}
		//updates
		gameboard.drawGameboard();
		
		//shows the result of the game
		int scoreAI = gameboard.getScoreAI();
		int scoreHu = gameboard.getScoreHuman();
		
		if(scoreAI > scoreHu) {
			System.out.println("AI vs. Human >>>> AI won!\n");
			
		} else if(scoreAI < scoreHu) {
			System.out.println("AI vs. Human >>>> Congratulations, you won!\n");
		} else {
			System.out.println("AI vs. Human >>>> It's a tie!\n");
		}
		
		sc.close();
	}

}
