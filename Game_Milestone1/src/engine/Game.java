package engine;

import engine.board.Board;
import engine.board.BoardManager;
import model.Colour;
import model.card.Deck;
import model.card.Card;
import model.player.Player;
import model.player.CPU;
import java.util.ArrayList;
import java.util.Collections;

public class Game implements GameManager {
    private final Board board;
    private final ArrayList<Player> players;
    private final ArrayList<Card> firePit;
    private int currentPlayerIndex; // Not READ: no getter provided.
    private int turn;             // Not READ: no getter provided.

    public Game(String playerName) {
        try {
            ArrayList<Colour> colours = new ArrayList<>();
            colours.add(Colour.RED);
            colours.add(Colour.GREEN);
            colours.add(Colour.BLUE);
            colours.add(Colour.YELLOW);
            Collections.shuffle(colours);

            this.board = new Board(colours, this);
            // Load the card pool from the CSV file.
            Deck.loadCardPool(board, this);

            this.players = new ArrayList<>();
            // Human player with the first colour.
            players.add(new Player(playerName, colours.get(0)));
            // Create CPU players for the remaining colours.
            for (int i = 1; i < colours.size(); i++) {
                players.add(new CPU("CPU " + i, colours.get(i), this.board));
            }
            // Distribute 4 cards to each player's hand.
            for (Player player : players) {
                player.setHand(Deck.drawCards());
            }
            this.currentPlayerIndex = 0;
            this.turn = 0;
            this.firePit = new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getFirePit() {
        return firePit;
    }

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCurrentPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWinner() {
		// TODO Auto-generated method stub
		return false;
	}
}