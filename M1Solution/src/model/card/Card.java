package model.card;


import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import model.player.Marble;
=======
import java.util.ArrayList;      //Added Automatic

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;      //Added Automatic


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
	
	
	
	//Milestone2
	//Checks if the number of marbles is valid for this card????
	 public boolean validateMarbleSize(ArrayList<Marble> marbles){
		 int cardType = 0;
		switch(cardType){
		 
		 case 0: 
			 return marbles.size()==0;
		 case 1:
			 return marbles.size()==1;
		 case 2:
			 return marbles.size()==2;
		 default:
			 return false;
		 }
			 
		 }
	  //Checks if the colors of the marbles are valid
	  public boolean validateMarbleColours(ArrayList<Marble> marbles) {
		return true;
		
		 
	 }
    //Performs the card action – must be overridden in subclasses?????
	public abstract void act(ArrayList <Marble> marbles)
	throws ActionException, InvalidMarbleException;


	public void act(ArrayList<Marble> selectedMarbles) {
		// TODO Auto-generated method stub
		
	}

	public abstract boolean validateMarbleColours(ArrayList<Marble> selectedMarbles);

	public abstract boolean validateMarbleSize(ArrayList<Marble> selectedMarbles);

	
}
