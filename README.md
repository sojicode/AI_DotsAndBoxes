## AI - Dots And Boxes

The game you implement will be slightly different than the traditional dots-and-boxes.
A player will **not** move again after completing a box. Instead the players take turns.
When the game board is generated each box will be given a random value between 1 and 5. When a player completes a box, her score will increase by the value of the box.
A player’s final score will be the sum of the values of the boxes claimed.

### AI
* The AI will rely on the minimax algorithm to choose which dots to connect at each step.

* When the game starts the human player will specify
> * The size of the board;
> * how many plys the AI will search (i.e., the horizon for the minimax).

* Use the following scoring function to evaluate the leaves of the tree:
> * score(Human) – score(AI)

* Use alpha-beta pruning to allow the AI to search deeper into the tree by pruning unnecessary branches.
