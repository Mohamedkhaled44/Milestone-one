package engine.board;
import model.player.Marble;

public class Cell {
	private Marble marble;
	private CellType cellType;
	private boolean trap;
	
	//lw amlt azma sheele el empty constructor da
	
	public Cell(CellType cellType){
		this.cellType=cellType;
		this.marble=null;
		this.trap=false;
	}
	
	
	public Marble getMarble() {
		return marble;
	}
	public void setMarble(Marble marble) {
		this.marble=marble;
	}
	
	
	public CellType getCellType() {
		return cellType;
	}
	public void setCellType(CellType cellType) {
		this.cellType=cellType;
	}
	
	public boolean Trap() {
		return trap;
	}
	public void setTrap(boolean trap) {
		this.trap=trap;
	}


	public boolean isTrap() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}