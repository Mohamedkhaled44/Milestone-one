package model.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import model.card.standard.*;
import model.card.wild.*;
import engine.GameManager;
import engine.board.BoardManager;

public class Deck {
    private static final String CARDS_FILE = "Cards.csv";
    private static ArrayList<Card> cardsPool = new ArrayList<>();

    /**
     * Loads the card pool from the CSV file and correctly assigns card types.
     */
    public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException {
        cardsPool.clear(); // Ensure the deck is empty before loading new cards
        BufferedReader reader = new BufferedReader(new FileReader(CARDS_FILE));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int code = Integer.parseInt(parts[0].trim());
            int frequency = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
            String description = parts[3].trim();
            Card card = null;

            if (code == 14) {
                card = new Burner(name, description, boardManager, gameManager);
            } else if (code == 15) {
                card = new Saver(name, description, boardManager, gameManager);
            } else {
                int rank = Integer.parseInt(parts[4].trim());
                String suitStr = parts[5].trim();
                Suit suit = Suit.valueOf(suitStr.toUpperCase());

                switch (rank) {
                    case 1:
                        card = new Ace(name, description, suit, boardManager, gameManager);
                        break;
                    case 13:
                        card = new King(name, description, suit, boardManager, gameManager);
                        break;
                    case 12:
                        card = new Queen(name, description, suit, boardManager, gameManager);
                        break;
                    case 11:
                        card = new Jack(name, description, suit, boardManager, gameManager);
                        break;
                    case 10:
                        card = new Ten(name, description, suit, boardManager, gameManager);
                        break;
                    case 7:
                        card = new Seven(name, description, suit, boardManager, gameManager);
                        break;
                    case 5:
                        card = new Five(name, description, suit, boardManager, gameManager);
                        break;
                    case 4:
                        card = new Four(name, description, suit, boardManager, gameManager);
                        break;
                    default:
                        card = new Standard(name, description, rank, suit, boardManager, gameManager);
                        break;
                }
            }

            // ✅ Fix: Ensure each added card is a unique instance
            for (int i = 0; i < frequency; i++) {
                cardsPool.add(createCardInstance(card));
            }
        }

        reader.close();
    }

    /**
     * Draws 4 cards from the shuffled deck and removes them.
     * @return A list of 4 drawn cards or fewer if the deck is small.
     */
    public static ArrayList<Card> drawCards() {
        if (cardsPool.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }

        Collections.shuffle(cardsPool); // Shuffle before drawing
        ArrayList<Card> drawn = new ArrayList<>();

        int drawCount = Math.min(4, cardsPool.size());
        for (int i = 0; i < drawCount; i++) {
            Card removedCard = cardsPool.remove(0);
            drawn.add(removedCard);
            System.out.println("Removed card: " + removedCard.getName()); // Debugging
        }

        System.out.println("Cards left in pool: " + cardsPool.size()); // Debugging
        return drawn;
    }

    /**
     * Creates a deep copy of a given card to ensure separate instances.
     */
    private static Card createCardInstance(Card card) {
        if (card instanceof Ace) {
            return new Ace(card.getName(), card.getDescription(), ((Ace) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof King) {
            return new King(card.getName(), card.getDescription(), ((King) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof Queen) {
            return new Queen(card.getName(), card.getDescription(), ((Queen) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof Jack) {
            return new Jack(card.getName(), card.getDescription(), ((Jack) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof Ten) {
            return new Ten(card.getName(), card.getDescription(), ((Ten) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof Seven) {
            return new Seven(card.getName(), card.getDescription(), ((Seven) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof Five) {
            return new Five(card.getName(), card.getDescription(), ((Five) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof Four) {
            return new Four(card.getName(), card.getDescription(), ((Four) card).getSuit(), card.boardManager, card.gameManager);
        } else if (card instanceof Burner) {
            return new Burner(card.getName(), card.getDescription(), card.boardManager, card.gameManager);
        } else if (card instanceof Saver) {
            return new Saver(card.getName(), card.getDescription(), card.boardManager, card.gameManager);
        } else {
            Standard standardCard = (Standard) card;
            return new Standard(card.getName(), card.getDescription(), standardCard.getRank(), standardCard.getSuit(), card.boardManager, card.gameManager);
        }
    }
}