package engine;

import exception.CannotDiscardException;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import model.Colour;
import model.player.Marble;
//import model.player.Player;
import model.player.Player;

public interface GameManager {


	//Mileston 2
	void sendHome(Marble marble);
	void fieldMarble() throws CannotFieldException, IllegalDestroyException;
	void discardCard(Colour colour) throws CannotDiscardException;
	void discardCard() throws CannotDiscardException;
	Colour getActivePlayerColour();
	Colour getNextPlayerColour();
	
	
	public void returnToHome(Player marbleOwner, Marble marble);//created by line 149 from Class Board
}


