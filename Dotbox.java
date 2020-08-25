
/**
 * Create dot-box elements to draw a game-board.
 * This individual dot-box gathers together and makes the entire game-board.
 * 
 * y = row, x = column
 * 
 * (y,x)  x --> axisX
 *    • ––––––––––––> 
 *  y |             |
 *    |   occupy? -------> dot-box has an initial value: dotboxNum(designated random number)
 *    |             |
 *    v ––––––––––––– 
 *
 */
public class Dotbox {
	
	public int y;
	public int x;
	public boolean axisX; //horizontal line(T/F)
	public String occupy; 
	public int dotboxNum;
	
	//constructor 1
	public Dotbox(int dotboxNum) {
		this.occupy = "Nobody"; //nobody gets the dot-box
		this.dotboxNum = dotboxNum;	
	}
	//constructor 2
	public Dotbox(String occupy, int dotboxNum) {
		this.occupy = occupy;
		this.dotboxNum = dotboxNum;
	}
	
	//constructor 3
	public Dotbox(int y, int x, boolean axisX) {
		this.y = y;
		this.x = x;
		this.axisX = axisX;
		
	}

	//constructor 4
	public Dotbox(int y, int x, boolean axisX, String occupy, int dotboxNum) {
		super();
		this.y = y;
		this.x = x;
		this.axisX = axisX;
		this.occupy = occupy;
		this.dotboxNum = dotboxNum;
	}

	//getter and setter
	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	// get T/F about X-axis
	public boolean isAxisX() {
		return axisX;
	}

	//get a name of occupancy
	public String getOccupy() {
		return occupy;
	}

	//update occupancy
	public void setOccupy(String occupy) {
		this.occupy = occupy;
	}

	//get a value of a dot-box
	public int getDotboxNum() {
		return dotboxNum;
	}

	//for duplicate a dot-box
	public Dotbox duplicate() {
		return new Dotbox(this.y, this.x, axisX, this.occupy, this.dotboxNum);
	}
	
	
	

}
