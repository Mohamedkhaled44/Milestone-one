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
	private String CARDS_FILE="CARDS_FILE";
	private static ArrayList<Card> cardsPool= new  ArrayList<>() ;
	
	
	public String getCARDS_FILE() {
		return CARDS_FILE;
	}
	public void setCARDS_FILE(String cARDS_FILE) {
		CARDS_FILE = cARDS_FILE;
	}
	public static ArrayList<Card> getCardspool() {
		return cardsPool;
	}
	public static void setCardspool(ArrayList<Card> cardspool) {
		cardsPool = cardspool;
	}
	public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader("CARDS_FILE"));
	    String line;

	    while ((line = reader.readLine()) != null) {
	        String[] parts = line.split(",");

	        int code = Integer.parseInt(parts[0]);
	        int frequency = Integer.parseInt(parts[1]);
	        String name = parts[2];
	        String description = parts[3];

	        for (int i = 0; i < frequency; i++) {
	            Card card = null;

	            if (code >= 1 && code <= 13) {  // Standard Cards
	                int rank = Integer.parseInt(parts[4]);
	                Suit suit = Suit.valueOf(parts[5].toUpperCase());

	                switch (rank) {
	                    case 1:
	                        card = new Ace(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 4:
	                        card = new Four(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 5:
	                        card = new Five(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 7:
	                        card = new Seven(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 10:
	                        card = new Ten(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 11:
	                        card = new Jack(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 12:
	                        card = new Queen(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 13:
	                        card = new King(name, description, suit, boardManager, gameManager);
	                        break;
	                }
	            } else if (code == 14) {  // Burner Card
	                card = new Burner(name, description, boardManager, gameManager);
	            } else if (code == 15) {  // Saver Card
	                card = new Saver(name, description, boardManager, gameManager);
	            }

	            if (card != null) {
	                cardsPool.add(card);  // Add each copy based on frequency
	            }
	        }
	    }

	    reader.close();
	}      
	public static ArrayList<Card> drawCards() {
		
		 if (cardsPool.size() < 4) {
		        throw new IllegalStateException("Not enough cards in the deck to draw 4 cards!");  // jUst in case that the deck is empty
		    }

	    Collections.shuffle(cardsPool);  

	    ArrayList<Card> drawnCards = new ArrayList<>(cardsPool.subList(0, 4));  

	    cardsPool.subList(0, 4).clear();  

	    return drawnCards;  
	}                                                                                                                                                  // Chatgpt prompt: Make public static void loadCardPool(BoardManager boardManager, GameManager gameManager)throws IOException: reads the csv file and instantiates the right card based on the code (first column of the csv). This card is added to the cardsPool according to the number of its frequency. For example, a frequency of 5 means the card will have 5 copies of that card added to thecardsPool.
		 
	}


