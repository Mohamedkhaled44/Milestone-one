package model.player;

import model.Colour;
import model.card.Card;
import java.util.ArrayList;

public class Player {
    private final String name;
    private final Colour colour;
    private ArrayList<Card> hand;
    private final ArrayList<Marble> marbles;
    private Card selectedCard; 
    private final ArrayList<Marble> selectedMarbles; // Neither READ nor WRITE.

    public Player(String name, Colour colour ) {
        this.name = name;
        this.colour = colour;
        this.hand = new ArrayList<>();
        this.marbles = new ArrayList<>();
        this.selectedMarbles = new ArrayList<>();
        // Create 4 marbles with the same colour.
        for (int i = 0; i < 4; i++) {
            marbles.add(new Marble(colour));
        }
        this.selectedCard = null;
    }
    private void playerCards(){
    	for (int i = 0; i <4; i++) {
    		this.hand.add(getSelectedCard());
			}; 	
			
		}
    
    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
    	
        this.hand = hand;
    }

    public ArrayList<Marble> getMarbles() {
        return marbles;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }
    // Setter for selectedCard removed (attribute is READ ONLY).
    // No getter for selectedMarbles is provided.
}