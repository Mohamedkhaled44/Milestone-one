package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import engine.board.Board;
import engine.board.Cell;
import exception.CannotDiscardException;
import exception.CannotFieldException;
import exception.GameException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.InvalidSelectionException;
import exception.SplitOutOfRangeException;
import model.Colour;
import model.*;
import model.card.*;
import model.player.CPU;
import model.player.Marble;
import model.player.Player;
import engine.*;

public class Game implements GameManager {

	private final Board board;
	private final ArrayList<Player> players;
	private final ArrayList<Card> firePit;
	private int currentPlayerIndex;
	private int turn;
 

	public Board getBoard() {
		return board;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getFirePit() {
		return firePit;
	}

	public Game(String playerName) throws IOException {

		ArrayList<Colour> colours = new ArrayList<Colour>();

		int count = 0;
		while (count < 4) {
			int i = (int) (Math.random() * 4);
			Colour c = Colour.values()[i];
			if (!colours.contains(c)) {
				colours.add(c);
				count++;
			}
		}
		board = new Board(colours, this);
		Deck.loadCardPool(board, this);
		Player human = new Player(playerName, colours.get(0));
		human.setHand(Deck.drawCards());
		CPU cpu1 = new CPU("CPU 1", colours.get(1), board);
		cpu1.setHand(Deck.drawCards());
		CPU cpu2 = new CPU("CPU 2", colours.get(2), board);
		cpu2.setHand(Deck.drawCards());
		CPU cpu3 = new CPU("CPU 3", colours.get(3), board);
		cpu3.setHand(Deck.drawCards());

		players = new ArrayList<Player>();

		players.add(human);
		players.add(cpu1);
		players.add(cpu2);
		players.add(cpu3);

		firePit = new ArrayList<Card>();
		currentPlayerIndex = 0;
		turn = 0;
		

	}
	public void selectCard(Card card) throws InvalidCardException {
		 Player current=getPlayers().get(currentPlayerIndex);
	    if (card == null || !getPlayers().get(currentPlayerIndex).getHand().contains(card)) {
	        throw new InvalidCardException("Invalid or unavailable card");
	    }
	  current.getHand().set(currentPlayerIndex, card);
	}
	public void selectMarble(Marble marble) throws InvalidMarbleException {
	    if (marble == null || !getPlayers().get(currentPlayerIndex).getMarbles().contains(marble)) {
	        throw new InvalidMarbleException("Invalid or unavailable marble");
	    }
	    if (getPlayers().get(currentPlayerIndex).getMarbles().size() < 2) {
	    	getPlayers().get(currentPlayerIndex).getMarbles().add(marble);
	    } else {
	        throw new InvalidMarbleException("Cannot select more than two marbles");
	    }
	}
	public void deselectAll() {
		ArrayList<Marble> selectedMarbles=getPlayers().get(currentPlayerIndex).getMarbles();
		int splitDistance=getBoard().getSplitDistance();
		selectedMarbles =null;
	    selectedMarbles.clear();
	    splitDistance = 0;
	   ArrayList<Card> selectedCard=getPlayers().get(currentPlayerIndex).getHand();
	}
	public void editSplitDistance(int splitDistance) throws SplitOutOfRangeException {
	    if (splitDistance < 1 || splitDistance > 6) {
	        throw new SplitOutOfRangeException("Split distance must be between 1 and 6.");
	    }
	    board.setSplitDistance(splitDistance);
	}
	public boolean canPlayTurn() {
	    return getPlayers().get(currentPlayerIndex).getHand().size() >= turn;
	}
	public void playPlayerTurn() throws GameException {
		ArrayList<Card> selectedCard=getPlayers().get(currentPlayerIndex).getHand();
		int splitDistance=getBoard().getSplitDistance();
		ArrayList<Marble> selectedMarbles=getPlayers().get(currentPlayerIndex).getMarbles();
	    if (!canPlayTurn()) {
	        throw  new IllegalMovementException("Player cannot play this turn due to insufficient cards.");
	    }
	    if (getPlayers().get(currentPlayerIndex).getSelectedCard() == null) {
	        throw new InvalidCardException("No card selected to play.");
	    }
	    if (getPlayers().get(currentPlayerIndex).getMarbles().isEmpty()) {
	        throw new InvalidMarbleException("No marbles selected to play.");
	    }
	    // Handle Seven card with split movement
	    if (getPlayers().get(currentPlayerIndex).getHand().size() == 7 && selectedMarbles.size() == 2 && splitDistance > 0) {
	        board.moveBy(selectedMarbles.get(0), board.getSplitDistance());
	        board.moveBy(selectedMarbles.get(1), 7 - board.getSplitDistance(), boolean destroy);
	    } else {
	        // Standard movement for other cards
	        for (Marble marble : selectedMarbles) {
	            board.moveby(marble, getPlayers().get(currentPlayerIndex).getHand().size(), destroy);
	        }
	    }
	}
	
	public void endPlayerTurn() {
		 Player currentPlayer=getPlayers().get(currentPlayerIndex);
		ArrayList<Card> selectedCard=getPlayers().get(currentPlayerIndex).getHand();
	    // Remove selected card and add to firePit
	    if (selectedCard != null) {
	        currentPlayer.getHand().remove(selectedCard);
	        firePit.addAll(selectedCard.);
	    }
	    // Deselect everything
	    deselectAll();

	    // Move to the next player
	    int currentIndex = players.indexOf(currentPlayer);
	    currentPlayer = players.get((currentIndex + 1) % players.size());

	    // Update turn and round
	    turn++;
	    if (turn > players.size()) {
	        turn = 1;
	        
	        // Refill hands at the start of a new round
	        for (Player player : players) {
	            while (player.getHand().size() < 4 && Deck.getPoolSize!=0()) {
	                player.setHand(Deck.drawCards());
	            }
	        }
	    }

	    // Refill deck if low on cards
	    if (Deck.getPoolSize < 4) {
	        Deck.refillPool(firePit);
	        firePit.clear();
	    }
	}
	public Colour checkWin() {
	    for (Player player : players) {
	        if (player.getSafeZone().isFullyOccupied()) {
	            return player.getColour();
	        }
	    }
	    return null;
	}
	@Override
	public void sendHome(Marble marble) {
	    if (marble != null) {
	        for (Player player : players) {
	            if (player.getColour() == marble.getColour()) {
	                player.regainMarble(marble);
	                board.destroyMarble(marble);
	                break;
	            }
	        }
	    }
	}
	
	public Colour getActivePlayerColour() {
	    return getPlayers().get(currentPlayerIndex).getColour();
	}
	
	@Override
	public void fieldMarble() throws CannotFieldException, IllegalDestroyException {
		Player currentPlayer=getPlayers().get(currentPlayerIndex);
	    Marble marble = currentPlayer.getHomeZone().getMarble();
	    if (marble == null) {
	        throw new CannotFieldException("No marble available to field.");
	    }
	    Cell baseCell = board.getSafeZones(currentPlayer.getColour());
	    validateFielding(baseCell);
	    if (!baseCell.canFieldMarble()) {
	        throw new IllegalDestroyException("Cannot field marble to an invalid base cell.");
	    }
	    baseCell.add(marble);
	    currentPlayer.getHomeZone().removeMarble(marble);
	}
	@Override
	public void discardCard(Colour colour) throws CannotDiscardException {
	    Player player = getPlayers().get(currentPlayerIndex).
	    if (player == null || player.getHand().isEmpty()) {
	        throw new CannotDiscardException("No cards available to discard for player " + colour);
	    }
	    Random random = new Random();
	    List<Card> hand = player.getHand();
	    Card cardToDiscard = hand.get(random.nextInt(hand.size()));
	    player.getHand().remove(cardToDiscard);
	    firePit.add(cardToDiscard);
	}
	@Override
	public void discardCard() throws CannotDiscardException {
		Player currentPlayer=getPlayers().get(currentPlayerIndex);
	    List<Player> otherPlayers = new ArrayList<>(players);
	    otherPlayers.remove(currentPlayer);
	    if (otherPlayers.isEmpty()) {
	        throw new CannotDiscardException("No other players available to discard from.");
	    }
	    Random random = new Random();
	    Player targetPlayer;
	    do {
	        targetPlayer = otherPlayers.get(random.nextInt(otherPlayers.size()));
	    } while (targetPlayer.getHand().isEmpty());
	    if (targetPlayer.getHand().isEmpty()) {
	        throw new CannotDiscardException("No cards available to discard from any other player.");
	    }
	    List<Card> hand = targetPlayer.getHand();
	    Card cardToDiscard = hand.get(random.nextInt(hand.size()));
	    targetPlayer.getHand().remove(cardToDiscard);
	    firePit.add(cardToDiscard);
	}
	@Override
	public Colour getNextPlayerColour() {
		Player currentPlayer=getPlayers().get(currentPlayerIndex);
	    int currentIndex = players.indexOf(currentPlayer);
	    Player nextPlayer = players.get((currentIndex + 1) % players.size());
	    return nextPlayer.getColour();
	}
}
