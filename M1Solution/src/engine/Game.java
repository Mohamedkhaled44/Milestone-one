package engine;

import java.io.IOException;
import java.util.ArrayList;

import engine.board.Board;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.CPU;
import model.player.Player;

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
}
