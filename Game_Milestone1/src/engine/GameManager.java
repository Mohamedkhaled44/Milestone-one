package engine;

public interface GameManager {
	void startGame();
	void endGame(); //mafish end gheer lma yekon fe winner
	int getCurrentPlayer();
	void nextTurn();
	boolean isWinner();//motrateb aleeha el end game

}