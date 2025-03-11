package model.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
import engine.GameManager;
import engine.board.BoardManager;

public class Deck {
	private final static String CARDS_FILE = "Cards.csv";
	private static ArrayList<Card> cardsPool = new ArrayList<Card>();	

	public static void loadCardPool(BoardManager boardManager,
			GameManager gameManager) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE));
		cardsPool = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null) {
			String[] blaizo = line.split(",");
			int code = Integer.parseInt(blaizo[0]);
			int Frequency = Integer.parseInt(blaizo[1]);
			String name = blaizo[2];
			String description = blaizo[3];

			if (code == 14) {
				for (int i = 0; i < Frequency; i++) {
					cardsPool.add(new Burner(name, description, boardManager,
							gameManager));
				}
			}

			else if (code == 15) {
				for (int i = 0; i < Frequency; i++) {
					cardsPool.add(new Saver(name, description, boardManager,
							gameManager));
				}
			}

			else {
				Suit suit = Suit.valueOf(blaizo[5]);
				int rank = Integer.parseInt(blaizo[4]);

				for (int i = 0; i < Frequency; i++) {

					switch (rank) {
					case 1:
						cardsPool.add(new Ace(name, description, suit,
								boardManager, gameManager));
						break;
					case 13:
						cardsPool.add(new King(name, description, suit,
								boardManager, gameManager));
						break;
					case 12:
						cardsPool.add(new Queen(name, description, suit,
								boardManager, gameManager));
						break;
					case 4:
						cardsPool.add(new Four(name, description, suit,
								boardManager, gameManager));
						break;
					case 5:
						cardsPool.add(new Five(name, description, suit,
								boardManager, gameManager));
						break;
					case 7:
						cardsPool.add(new Seven(name, description, suit,
								boardManager, gameManager));
						break;
					case 10:
						cardsPool.add(new Ten(name, description, suit,
								boardManager, gameManager));
						break;
					case 11:
						cardsPool.add(new Jack(name, description, suit,
								boardManager, gameManager));
						break;

					default:
						cardsPool.add(new Standard(name, description, rank,
								suit, boardManager, gameManager));
					}

				}
			}
		}

	}

	public static ArrayList<Card> drawCards() {
		Collections.shuffle(cardsPool);
		ArrayList<Card> drawnCards = new ArrayList<>();
		for (int i = 0; i < 4 && !cardsPool.isEmpty(); i++) {
			drawnCards.add(cardsPool.remove(0));
		}
		return drawnCards;

	}

}