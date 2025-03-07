package engine;
import java.io.IOException;
import java.util.*;
import model.Colour;
import engine.board.Board;
import model.card.*;
import model.player.*;
import engine.board.BoardManager;

public class Game implements GameManager {
	
	private final Board board;
	private final ArrayList<Player> players;
	private final ArrayList<Card> firePit;
	private int currentPlayerIndex;
	private int turn;
	private Deck deck;
	
	public Board getBoard() {
		return board;
	}
	public ArrayList<Player> getPlayers(){
		return players;
	}
	public ArrayList<Card> getFirePit(){
		return firePit;
	}
	
	Game(String playerName) throws IOException{
		
		ArrayList<Colour> colourOrder = new ArrayList<>(List.of(Colour.values()));
		 Collections.shuffle(colourOrder);
		 
		this.board=new Board(colourOrder, this);
		
		this.deck= new Deck();
		Deck.loadCardPool(this, this.board);
		
		this.players= new ArrayList<>();
		this.players.add(new Player (playerName, colourOrder.get(0),this.board));
		this.players.add(new Player ("CPU1", colourOrder.get(1),this.board));
		this.players.add(new Player ("CPU2", colourOrder.get(2),this.board));
		this.players.add(new Player ("CPU3", colourOrder.get(3),this.board));
		
		for(Player player:players) {
		Deck.drawCards(player,4);
		}
		this.firePit=new ArrayList<>();
		this.currentPlayerIndex=0;
		this.turn=0;
	}

}