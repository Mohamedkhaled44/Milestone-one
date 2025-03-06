package model.player;

import java.util.ArrayList;

import model.Colour;
import model.card.Card;

public class Player {

	private final  String name ;
	private Card selectedCard ;
	private  ArrayList<Card> hand= new ArrayList<>()  ;
	private ArrayList<Marble> marbles= new ArrayList<>();
	private final ArrayList<Marble> selectedMarbles= new ArrayList<>();
	private final Colour colour;
	
	public  Player(String name, Colour colour){
		this.name=name;
		this.colour=colour;
		for (int i = 0; i < 4; i++) {
			marbles.add(new Marble(colour));
			
		}
		
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public String getName() {
		return name;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public Colour getColour() {
		return colour;
	}

	public void setMarbles(ArrayList<Marble> marbles) {
		this.marbles = marbles;
	}
	
}
