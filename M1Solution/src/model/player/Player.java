package model.player;

import java.util.ArrayList;
import java.util.Collection;

import exception.GameException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import model.Colour;
import model.card.Card;

public class Player {

	private final String name;
	private final Colour colour;
	private ArrayList<Card> hand;
	private final ArrayList<Marble> marbles;
	private Card selectedCard;
	private final ArrayList<Marble> selectedMarbles;

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public String getName() {
		return name;
	}

	public Colour getColour() {
		return colour;
	}

	public ArrayList<Marble> getMarbles() {
		return marbles;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public Player(String name, Colour colour) {
		this.name = name;
		this.colour = colour;
		hand = new ArrayList<Card>();
		selectedMarbles = new ArrayList<Marble>();
		marbles = new ArrayList<Marble>();

		for (int i = 0; i < 4; i++) {
			marbles.add(new Marble(colour));
		}

	}

	public static Collection<? extends Marble> getTrackMarbles() {//added from line 90 Class board
		// TODO Auto-generated method stub
		return null;
	}

	public static Collection<? extends Marble> getSafeZoneMarbles() {//added from line 92 class board
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	//Milestone 2
	
	public void regainMarble(Marble marble) {
		if (marble != null) {
			marbles.add(marble); 
		}
		}
		public Marble getOneMarble() {
		    return marbles.isEmpty() ? null : marbles.get(0);
		}
		
		public void selectCard(Card card) throws InvalidCardException {
		    if (!hand.contains(card)) {
		        throw new InvalidCardException("Card not in hand.");
		    }
		    selectedCard = card;
		}
		public void selectMarble(Marble marble) throws InvalidMarbleException {
		    if (selectedMarbles.size() >= 2) {
		        throw new InvalidMarbleException("Cannot select more than two marbles.");
		    }
		    selectedMarbles.add(marble);
		}
		public void deselectAll() {
		    selectedCard = null;
		    selectedMarbles.clear();
		}
		
		public void play() throws GameException {
		    if (selectedCard == null) {
		        throw new InvalidCardException("No card selected.");
		    }

		    if (!selectedCard.validateMarbleSize(selectedMarbles)) {
		        throw new InvalidMarbleException("Incorrect number of marbles for the selected card.");
		    }

		    if (!selectedCard.validateMarbleColours(selectedMarbles)) {
		        throw new InvalidMarbleException("Invalid marble colors for the selected card.");
		    }

		    selectedCard.act(selectedMarbles);
		
	}

}

















