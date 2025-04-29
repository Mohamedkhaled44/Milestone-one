package model.card.standard;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import model.card.Card;
import model.player.Marble;

public class Standard extends Card {

	private final int rank;
	private final Suit suit;

	public Standard(String name, String description, int rank, Suit suit, BoardManager boardManager,
			GameManager gameManager) {
		super(name, description, boardManager, gameManager);
		this.suit = suit;
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public boolean validateMarbleColours(ArrayList<Marble> selectedMarbles) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateMarbleSize(ArrayList<Marble> selectedMarbles) {
		// TODO Auto-generated method stub
		return false;
	}

}
