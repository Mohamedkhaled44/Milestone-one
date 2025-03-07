package engine.board;
import java.util.*;
import engine.GameManager;
import model.Colour;

public class Board implements BoardManager{
	private final GameManager gameManager;
	private final ArrayList<Cell> track;
	private final ArrayList<SafeZone> safeZones;
	private int splitDistance;
	
	public Board(ArrayList<Colour> colourOrder, GameManager gameManager){
		this.gameManager=gameManager;
		this.track=new ArrayList<>();
		this.safeZones=new ArrayList<>();
		this.splitDistance=3;
		
		int noOfCells=100;
		for(int i=0;i<noOfCells;i++) {
//each player got 25 cells + el game btbtdy mn index zero
			if(i%25==0) 
				track.add(new Cell(CellType.BASE));
//el base cell obara aan two cells baadeha badkhol ala l el safe zone fa i+2
			else if((i+2)%25==0)
				track.add(new Cell(CellType.ENTRY));
			
			else
				track.add(new Cell(CellType.NORMAL));
		}
	
		for (Colour colour : colourOrder) {
		    safeZones.add(new SafeZone(colour));  // Add SafeZones based on colour order
		}

		// Call assignTrapCell only once to assign 8 traps
		assignTrapCell();
	}
	public void assignTrapCell()
	{
		Random randCell=new Random();
		ArrayList<Integer> randNormal = new ArrayList<>();		
		
		for(int i=0;i<track.size();i++) {
			if(track.get(i).getCellType()==CellType.NORMAL && !(track.get(i).getTrap()==true)) {
			randNormal.add(i);
			}
		}
		if(!randNormal.isEmpty()) {
			int randomTrap=randNormal.get(randCell.nextInt(randNormal.size()));
			track.get(randomTrap).setTrap(true);
		}
		
	}
	
	//EL GETTERS AND SETTERS
	    public GameManager getGameManager() {
	    	return gameManager;
	    }
		public ArrayList<Cell> getTrack(){
			return track;
		}
		public ArrayList<SafeZone> getSafeZones(){
			return safeZones;
		}
		public void setSplitDistance(int splitDistance) {
			this.splitDistance=splitDistance;
		}
		public int getSplitDistance() {
			return splitDistance;
		}
		
}