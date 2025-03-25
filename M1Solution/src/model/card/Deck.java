package model.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import engine.GameManager;
import engine.board.BoardManager;
import model.card.standard.Ace;
import model.card.standard.Five;
import model.card.standard.Four;
import model.card.standard.Jack;
import model.card.standard.King;
import model.card.standard.Queen;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.standard.Ten;
import model.card.wild.Burner;
import model.card.wild.Saver;

public class Deck {

	private final static String CARDS_FILE = "Cards.csv";
	private static ArrayList<Card> cardsPool;

	public static void loadCardPool(BoardManager boardManager,
			GameManager gameManager) throws IOException {

		String filePath = CARDS_FILE;
		cardsPool = new ArrayList<Card>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length < 4)
					continue; // Ensure valid data

				int code = Integer.parseInt(parts[0].trim());
				int frequency = Integer.parseInt(parts[1].trim());
				String name = parts[2].trim();
				String description = parts[3].trim();

				Card card = null;

				int rank = 0;
				Suit suit = null;

				if (code < 14) {
					rank = Integer.parseInt(parts[4]);
					suit = Suit.valueOf(parts[5]);
				}

				switch (code) {
				case 0:
					card = new Standard(name, description, rank, suit,boardManager,gameManager);
					break;
				case 1:
					card = new Ace(name, description, suit, boardManager,gameManager);
					break;
				case 4:
					card = new Four(name, description, suit, boardManager,gameManager);
					break;
				case 5:
					card = new Five(name, description, suit, boardManager,gameManager);
					break;
				case 7:
					card = new Seven(name, description, suit, boardManager,gameManager);
					break;
				case 10:
					card = new Ten(name, description, suit, boardManager,gameManager);
					break;
				case 11:
					card = new Jack(name, description, suit, boardManager,gameManager);
					break;
				case 12:
					card = new Queen(name, description, suit, boardManager,gameManager);
					break;
				case 13:
					card = new King(name, description, suit, boardManager,gameManager);
					break;

				case 14:
					card = new Burner(name, description, boardManager,gameManager);
					break;
				case 15:
					card = new Saver(name, description, boardManager,gameManager);
					break;

				default:
					card = null;
				}

				if (card != null) {
					for (int i = 0; i < frequency; i++) {
						cardsPool.add(card);
					}
				}
			}
		}

	}

	public static ArrayList<Card> drawCards() {
		Collections.shuffle(cardsPool);

		ArrayList<Card> drawnCards = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			drawnCards.add(cardsPool.remove(0));
		}

		return drawnCards;

	}

}
