package model.player;

import java.util.ArrayList;
import java.util.Collections;

import engine.board.BoardManager;
import exception.GameException;
import model.Colour;
import model.card.Card;

public class CPU extends Player {
	private final BoardManager boardManager;
	private ArrayList<Marble> actionableMarbles;//created from line 41 from CPU

	public CPU(String name, Colour colour, BoardManager boardManager) {
		super(name, colour);
		this.boardManager = boardManager;
		// TODO Auto-generated constructor stub
	}

	public BoardManager getBoardManager() {
		return boardManager;
	}
	//Milestone 2
	public void play()throws GameException{
		//bey7aded el marbles el momken al3ab beyha
		ArrayList<Marble>actionMarbles= boardManager.getActionableMarbles();
		
		//Shuffel el cards el fe 2ed el player
		ArrayList<Card>cards= new ArrayList<>();
		cards.addAll(this.getHand());
		int initialHandSize = cards.size();
		Collections.shuffle(cards);
		
		//chooses the card that he will play with
		for(Card card: cards) {
			this.selectCard(card);
			
			//makes sure that the  card can be played with 0,1,or2 marbles (3ala 7asab el rules beta3et el game)
			ArrayList<Integer>counts=new ArrayList<>();
			for(int i=0; i<3; i++) {
				if(actionableMarbles.size() >= i) {
					ArrayList<Marble> testMarbles= new ArrayList<>();
					for(int j=0; j<i; j++) {
						testMarbles.add(actionableMarbles.get(j));
					}
					
					if(card.validateMarbleSize(testMarbles)) {
						counts.add(i);
					}
				}
			}
			
			//beymsh be4akl 3a4wa2y 34an yegarb kaza tary2a (ben 0,1 or 2 34an yeshof hoa haysta5dem kam kora fel round dah)	
			Collections.shuffle(counts);
			
			
			for(int i=0; i< counts.size(); i++) {
				
				//law mafeesh marbles aslun
				if(counts.get(i)==0) {
					try {
						
						getSelectedCard().act(new ArrayList<>());
						return;
					}
					catch(Exception e) {
						
					}
				}
				//law fe 1 marble
				else if(counts.get(i)==1) {
					ArrayList<Marble> toSend=new ArrayList<>();
					Collections.shuffle(actionMarbles);
					for(Marble marble : actionableMarbles) {
						toSend.add(marble);
						if(card.validateMarbleColours(toSend)) {
							try {
								getSelectedCard().act(toSend);
								return;
							}
							catch(Exception e) {
								
							}
						}
						toSend.clear();
					}
				}
				//Law fe 2 marbles
				else {
					
					ArrayList<Marble>toSend= new ArrayList<>();
					Collections.shuffle(actionableMarbles);
					for(int j=0; j< actionableMarbles.size(); j++) {
						for(int k=j+1; k< actionableMarbles.size(); k++) {
							toSend.add(actionableMarbles.get(j));
							toSend.add(actionableMarbles.get(k));
							if(card.validateMarbleColours(toSend)) {
								try {
									getSelectedCard().act(toSend);
									return;
								}
								catch(Exception e) {
									
								}
							}
							toSend.clear();
						}
					}
				}
			}
		}
		if(cards.size()== initialHandSize)
			this.selectCard(this.getHand().get(0));//Law wala kart et7a2a2, ye5tar awel karet 5alas

	}
//
	public void selectCard(Card card) {
		// TODO Auto-generated method stub
		// created from line 36
	}

}
