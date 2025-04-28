package engine.board;

import java.util.ArrayList;

import model.Colour;

public class SafeZone {

	private final  Colour colour;
	private final  ArrayList<Cell> cells;

	public Colour getColour() {
		return colour;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public SafeZone(Colour colour) {
		this.colour = colour;
		cells = new ArrayList<Cell>();
		for (int i = 0; i < 4; i++) {
			cells.add(new Cell(CellType.SAFE));
		}
	}

		
	
	
	//Milestone 2
	boolean isFull(){
		
		for (Cell cell : cells) {
            if (!cell.isOccupied()) {
                return false; // If even one cell is not occupied, SafeZone is not full
            }
        }
        return true; // All cells are occupied                   
	} 

	

//M2
	public boolean isFull() {
		for(Cell cell: cells) {
		if(cell.getCellType()!=CellType.SAFE) 
			return false;
		}
	return true;
	}

}
