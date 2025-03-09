package model.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import java.util.*;

import model.card.standard.*;
import model.card.wild.*;
import engine.GameManager;
import engine.board.BoardManager;

public class Deck {
	private static final String CARDS_FILE = "Cards.csv";;
	private static ArrayList<Card> cardsPool= new  ArrayList<>() ;
	private static int frequency;
	
	
	
	
	public static ArrayList<Card> getCardspool() {
		return cardsPool;
	}
	public static void setCardspool(ArrayList<Card> cardspool) {
		cardsPool = cardspool;
	}
    public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(CARDS_FILE));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int code = Integer.parseInt(parts[0].trim());
            setFrequency(Integer.parseInt(parts[1].trim()));
            String name = parts[2].trim();
            String description = parts[3].trim();
            Card card = null;

            if (code == 14) {
                card = new Burner(name, description, boardManager, gameManager);
            } else if (code == 15) {
                card = new Saver(name, description, boardManager, gameManager);
            } else {
                int rank;
                String suitStr;
                if (parts.length >= 6) {
                    rank = Integer.parseInt(parts[4].trim());
                    suitStr = parts[5].trim();
                } else {
                    rank = 0;
                    suitStr = "HEART";
                }
                switch (rank) {
                    case 1:
                        card = new Ace(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                        break;
                    case 13:
                    	 card = new King(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                     case 12:
                         card = new Queen(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                     case 11:
                         card = new Jack(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                     case 10:
                         card = new Ten(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                     case 7:
                         card = new Seven(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                     case 5:
                         card = new Five(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                     case 4:
                         card = new Four(name, description, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                     default:
                         card = new Standard(name, description, rank, Suit.valueOf(suitStr.toUpperCase()), boardManager, gameManager);
                         break;
                 }
             }
             // Add the card 'frequency' times.
             for (int i = 0; i < 4; i++) {
                 cardsPool.add(card);
             }
         }
         reader.close();
     }

    public static ArrayList<Card> drawCards() {
        Collections.shuffle(cardsPool);
        ArrayList<Card> drawn = new ArrayList<>();
        for (int i = 0; i < 4  ; i++) {
        	if(!cardsPool.isEmpty())
            drawn.add(cardsPool.remove(0));
        }
        return drawn;
    }
                                                                                                                        // Chatgpt prompt: Make public static void loadCardPool(BoardManager boardManager, GameManager gameManager)throws IOException: reads the csv file and instantiates the right card based on the code (first column of the csv). This card is added to the cardsPool according to the number of its frequency. For example, a frequency of 5 means the card will have 5 copies of that card added to thecardsPool.
	public static int getFrequency() {
		return frequency;
	}
	public static void setFrequency(int frequency) {
		Deck.frequency = frequency;
	}
		 
	}


