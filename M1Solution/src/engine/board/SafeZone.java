package engine.board;

import java.util.ArrayList;

import model.Colour;

public class SafeZone {

	final private Colour colour;
	final private ArrayList<Cell> cells;

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
//M2
	public boolean isFull() {
		for(Cell cell: cells) {
		if(cell.getCellType()!=CellType.SAFE) 
			return false;
		}
	return true;
	}
}
