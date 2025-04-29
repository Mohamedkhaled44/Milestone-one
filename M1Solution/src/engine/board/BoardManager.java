package engine.board;
import java.util.ArrayList;
import exception.*;
import model.player.Marble;

import java.util.ArrayList;

import exception.CannotFieldException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.IllegalSwapException;
import exception.InvalidMarbleException;
import model.player.Marble;
import model.player.Player;

public interface BoardManager {

   int getSplitDistance();

	
	//Milestone 2
	 
	public void moveBy(Marble marble, int steps, boolean destroy)
			throws IllegalMovementException, IllegalDestroyException;
	
	 public void swap(Marble marble_1, Marble marble_2) 
			 throws IllegalSwapException;
	 
	 public void destroyMarble(Marble marble) 
			 throws IllegalDestroyException;
	 
	 public void sendToBase(Marble marble)
			 throws CannotFieldException,IllegalDestroyException;
	 
	 public void sendToSafe(Marble marble) 
			 throws InvalidMarbleException;
	
	
	ArrayList<Marble> getActionableMarbles();  //3aml creat lewa7do besabab line 26 fel CPU

	
	
	
	public static BoardManager getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cell getBaseCell(Player owner);
	// TODO Auto-generated method stub

	public void moveMarbleToCell(Marble marble, Cell baseCell);
	// TODO Auto-generated method stub

	public void removeMarble(Marble other);
	// TODO Auto-generated method stub

	public ArrayList<Cell> getSafeZone(Player owner);
	// TODO Auto-generated method stub

	public void setMarbleToCell(Marble marble_2, Cell cell1);
	// TODO Auto-generated method stub

	public Cell getMarbleCell(Marble marble_2);
	// TODO Auto-generated method stub

	public void setMarbleTocell(Marble marble, Cell targetCell);
	// TODO Auto-generated method stub

	public boolean isValidDestroy(Marble marble, Marble occupyingMarble);
	// TODO Auto-generated method stub

	public Cell getTargetCell(Cell currentCell, int steps, Marble marble);
	// TODO Auto-generated method stub
	
}

	int getSplitDistance();

	

	public void moveBy(Marble marble, int steps, boolean destroy) throws IllegalMovementException, IllegalDestroyException;
	public void swap(Marble marble_1, Marble marble_2) throws IllegalSwapException;
	public void destroyMarble(Marble marble) throws IllegalDestroyException;
	public void sendToBase(Marble marble) throws CannotFieldException,IllegalDestroyException;
	public void sendToSafe(Marble marble) throws InvalidMarbleException;
	public ArrayList<Marble> getActionableMarbles();

}

