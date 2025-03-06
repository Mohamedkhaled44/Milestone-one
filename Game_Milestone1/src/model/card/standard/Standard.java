package model.card.standard;
import model.card.Card;
import engine.*;
import engine.board.*;

public class Standard extends Card{
		
			private int rank; //Attribute
			private Suit suit;
			public Standard(String name, String description, int rank, Suit suit, BoardManager boardManager,
					 GameManager gameManager){
				super(name , description, boardManager,gameManager);
				
				this.rank = rank;
				this.suit = suit;
			}
			public int getRank() {
				return rank;
			}
		
			public Suit getSuit() {
				return suit;
			}
		
			
		
			
			
			


}