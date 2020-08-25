import java.util.*;

/**
 * 
 * This class shows a Minimax algorithm with alpha-beta pruning and it helps AI moves
 *
 */

public class Minimax {
	
	public boolean is_max = true;
	
	final static int negativeInfinity = Integer.MIN_VALUE;
	final static int positiveInfinity = Integer.MAX_VALUE;
	
	//In main class, when it is AI's turn to play, it calls this function and it returns the best option for moves
	public static Dotbox minimax(Gameboard root, int ply) {
		
		Gameboard gameboard = alphaBetaPruning(root, ply, true, negativeInfinity, positiveInfinity); //initially 'is_max' = true
		Dotbox endMove = gameboard.getEndMove();                 //get an end moves
		
		return endMove;
	}

	//Using Minimax algorithm with alpha-beta pruning (recursive)
	private static Gameboard alphaBetaPruning(Gameboard root, int ply, boolean is_max, int alpha, int beta) {
		
		//condition for terminating(no more line left or depth)
		if(ply == 0 || root.leftLines == 0) {
			return root;
		}
		
		if(is_max) { //this part for MAX player
			
			int best = negativeInfinity;
			
			Gameboard bestOptionA = null;
			
			List<Gameboard> children = root.getMovesList("Human");

			for(Gameboard child : children) { //expand a tree and get child-nodes
				
				//recursive, change value of boolean('is_max') to false --> go to else-statement
				int alphaNow = alphaBetaPruning(child, ply-1, false, alpha, beta).getEvaluation();
				
				if(best < alphaNow) {
					best = alphaNow;                        //update the max value
					bestOptionA = child;   					    
					bestOptionA.setEvaluation(best);        //update an EvaluationScore(scoreAI - scoreHuman) 
				}
				
				//this part is the alpha beta pruning
				if(best >= beta) {
					//System.out.println(" stop search max!");
					break; //stop searching
				}
				alpha = Math.max(alpha, best); //compares and gets a max value and updates alpha
			}
			return bestOptionA;

		} else { //this part for MIN player
			
			int best = positiveInfinity;
			
			Gameboard bestOptionB = null;
			
			List<Gameboard> children = root.getMovesList("AI");
			
			for(Gameboard child : children) { //expand a tree and get child-nodes
				
				//recursive, change value of boolean('is_max') to true --> go to the if-statement
				int betaNow = alphaBetaPruning(child, ply-1, true, alpha, beta).getEvaluation();
				
				if(betaNow < best) {
					best = betaNow;                         //update the minimum value
					bestOptionB = child;
					bestOptionB.setEvaluation(best);        //update an EvaluationScore(scoreAI - scoreHuman) 
				}
				
				//this part is the alpha beta pruning
				if(best <= alpha) {
					//System.out.println(" stop search min!");
					break; //stop searching
				}
				
				beta = Math.min(beta, best); //compares and gets a minimum value and updates beta
			}
			return bestOptionB;
		}
		
	}
	
	
	
	

}
