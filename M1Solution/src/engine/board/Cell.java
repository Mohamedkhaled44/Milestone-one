package engine.board;

import model.player.Marble;

public class Cell {

	private Marble marble;
	private CellType cellType;
	private boolean trap;

	public Marble getMarble() {
		return marble;
	}

	public void setMarble(Marble marble) {
		this.marble = marble;
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public boolean isTrap() {
		return trap;
	}

	public void setTrap(boolean trap) {
		this.trap = trap;
	}

	public Cell(CellType cellType) {
		this.cellType = cellType;
	}

	public static boolean isOccupied() {          //Deh Zeyada men error fe class safeZone line 33
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {                    //Added from class board line119
		// TODO Auto-generated method stub
		return false;
	}

	public Marble getOccupyingMrable() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
