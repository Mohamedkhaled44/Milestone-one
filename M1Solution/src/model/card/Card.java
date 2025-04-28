package model.card;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import model.player.Marble;

public abstract class Card {

	private final String name;
	private final String description;
	protected BoardManager boardManager;
	protected GameManager gameManager;

	public BoardManager getBoardManager() {
		return boardManager;
	}

	public void setBoardManager(BoardManager boardManager) {
		this.boardManager = boardManager;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Card(String name, String description, BoardManager boardManager,
			GameManager gameManager) {
		this.name = name;
		this.description = description;
		this.boardManager = boardManager;
		this.gameManager = gameManager;
	}

	public void act(ArrayList<Marble> selectedMarbles) {
		// TODO Auto-generated method stub
		
	}

	public abstract boolean validateMarbleColours(ArrayList<Marble> selectedMarbles);

	public abstract boolean validateMarbleSize(ArrayList<Marble> selectedMarbles);

	
}
