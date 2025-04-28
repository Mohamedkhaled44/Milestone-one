package engine.board;

import java.util.ArrayList;
import java.util.Collections;

import engine.GameManager;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.IllegalSwapException;
import exception.InvalidMarbleException;//added from line 99 Board
import model.Colour;
import model.player.Marble;
import model.player.Player;//Added from line 90 Board

public class Board implements BoardManager {

	BoardManager boardManager= BoardManager.getInstance();
	
	private final GameManager gameManager;
	private final ArrayList<Cell> track;
	private final ArrayList<SafeZone> safeZones;
	private int splitDistance;
	

	public int getSplitDistance() {
		return splitDistance;
	}

	public void setSplitDistance(int splitDistance) {
		this.splitDistance = splitDistance;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public ArrayList<Cell> getTrack() {
		return track;
	}

	public ArrayList<SafeZone> getSafeZones() {
		return safeZones;
	}

	public Board(ArrayList<Colour> colourOrder, GameManager gameManager) {
		this.gameManager = gameManager;
		track = new ArrayList<Cell>();
		safeZones = new ArrayList<SafeZone>();
		splitDistance = 3;

		for (int i = 0; i < 100; i++) {
			if (i == 0 || i == 25 || i == 50 || i == 75)
				track.add(new Cell(CellType.BASE));
			else if (i == 98 || i == 23 || i == 48 || i == 73)
				track.add(new Cell(CellType.ENTRY));
			else
				track.add(new Cell(CellType.NORMAL));
		}

		for (int i = 0; i < 8; i++) {
			assignTrapCell();
		}

		for (int i = 0; i < colourOrder.size(); i++) {
			SafeZone s = new SafeZone(colourOrder.get(i));
			safeZones.add(s);
		}

	}

	private void assignTrapCell() {

		boolean found = false;

		while (!found) {
			int i = (int) (Math.random() * 100);
			if (track.get(i).getCellType().equals(CellType.NORMAL)
					&& !track.get(i).isTrap()) {
				track.get(i).setTrap(true);
				found = true;
			}
		}

	}
	
	
	//Milestone 2
	
	//12
	
	
	
	
	
	
	
	
	/*@Override
	public void moveBy(Marble marble, int steps, boolean destroy) 
			throws IllegalMovementException, IllegalDestroyException {
		
		//First get the exact location of the marble
		Cell currentCell = boardManager.getMarbleCell(marble);
		
		//Second Select the targeted cell according to number of steps
		Cell targetCell = boardManager.getTargetCell(currentCell, steps, marble);
		
		//if the targeted cell is not empty, therefore
		if(targetCell.isOccupied()) {
			Marble occupyingMarble = targetCell.getOccupyingMrable();
			
			if(destroy) {
				if(!boardManager.isValidDestroy(marble, occupyingMarble)) {
					throw new IllegalDestroyException("Invalid attempt to destroy another marble.");
					
				}
			}
			//Move the marble to the new place
			boardManager.setMarbleTocell(marble, targetCell);
		}
		
		
	}
	*/
	
	
	
	//13
	@Override
	public void swap(Marble marble_1, Marble marble_2) throws IllegalSwapException {
		//First make sure that the two marbles can be swaped
		if(!isValidSawp(marble_1, marble_2)) {
			throw new IllegalSwapException("This swap is not allowed based on game rules.");
		}
		
		//Second mark the 2 marbles and its places that need to be switched
		Cell cell1 = boardManager.getMarbleCell(marble_1);
		Cell cell2 = boardManager.getMarbleCell(marble_2);
		
		//Third Make the switch between the two marbles
		boardManager.setMarbleToCell(marble_1, cell2);//Marble 1 in cell 2
		boardManager.setMarbleToCell(marble_2, cell1);//Marble 2 in cell 1
	}
	
	
	
	
	

	

	//14
	@Override
	public void destroyMarble(Marble marble) throws IllegalDestroyException {
		Player  = Player; currentPlayer().gameManager.getCurrentPlayer();//The current player who is playing
		Player marbleOwner= marble.getOwner();//The owner of the marble
		
		if(!currentPlayer.equals(marbleOwner)) {//Law el marble m4 beta3et el player el beyl3ab
			if(!canDestroy(marble)) {// w law mayenfa34 enena ne3melaha destroy //<--then
				throw new IllegalDestroyException("Your not allowed to destroy this marble.");
			}
		}
		boardManager.removeMarble(marble); //Remove the marble from its current location
		
		gameManager.returnToHome(marbleOwner, marble);//Return the removed Marble to its owners home zone
	}
	
	
	

	//15
	@Override
	public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
		//Find to whom the marble belongs to
		Player owner =marble.getOwner();
		//then go to the base cell that belongs to this player
		Cell baseCell= boardManager.getBaseCell(owner);
		
		if(baseCell.isEmpty()) {
			boardManager.moveMarbleToCell(marble, baseCell);//law el base feyha makan fady ba7ot el marble feyha 3ady
		}else {
			if(!canField(baseCell, marble)) {//???
				throw new CannotFieldException("This marble can't be sent to base.");	
			}
			
			//law el marble el fel base beta3 el palyer el asly beta3et palyer tany ba destroy el marble deh
			Marble other= baseCell.getMarble();
			if(!other.getOwner().equals(owner)) {
				if(!canDestroy(other)) {
				throw new IllegalDestroyException("Can't destroy opponent's marble.");
				
			}
			
			boardManager.removeMarble(other);
			boardManager.moveMarbleToCell(marble, baseCell);
			
		}else{//??????
			 throw new CannotFieldException("Base already occupied by player's own marble.");
			 }
			}
	}
	
	
	//16
	@Override
	public void sendToSafe(Marble marble) throws InvalidMarbleException{
		//bat2ked law el safezone yenfa3 yetne2el feh el marble (Fel 3omom men abl ma7ded beta3 anyh player (ta2ryban))
		if(!isValidToMoveToSafe(marble)) {
			throw new InvalidMarbleException("This marble can't be sent to the Safe Zone.");	
		}
		
		//ba3daha baro7 le sa7eb el marble el 3ayez yen2elha lel safeZone
		Player owner= marble.getOwner();
		
		//badawar gowa el safezone beta3 el palyer
		ArrayList<Cell> safeZone = boardManager.getSafeZone(owner);
		
		//badawar 3ala empty cell goea el safeZone (Rendomly)
		Collections.shuffle(safeZone);
		for(Cell cell: safeZone) {
			if(cell.isEmpty()) {
				//Then move the marble to this cell
				boardManager.moveMarbleToCell(marble, cell);
				return;
			}
		}
		//fe exception 3ady law mafee4 cell fadya aslun fel safeZone aw el cell
		throw new InvalidMarbleException("Safe Zone is full. Can't move marble.");
		
		
	}
	
	
	
	//17
	@Override
	public ArrayList<Marble>getActionableMarbles(){
		ArrayList<Marble> allMarbles = new ArrayList<>();// Box bengama3 feh kol el marbles fel path wel safezone
		
		allMarbles.addAll(Player.getTrackMarbles());//all marbles on the track
		
		allMarbles.addAll(Player.getSafeZoneMarbles());//all marbles in safeZone
		
		return allMarbles;
	}
	
	
	
	
	
	
	
	private boolean canDestroy(Marble other) {
		// TODO Auto-generated method stub
		return true;
	}
	private boolean canField(Cell baseCell, Marble marble) {
		// TODO Auto-generated method stub
		return true;
	}
	private Object currentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
	private boolean isValidSawp(Marble marble_1, Marble marble_2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cell getBaseCell(Player owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveMarbleToCell(Marble marble, Cell baseCell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMarble(Marble other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Cell> getSafeZone(Player owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMarbleToCell(Marble marble_2, Cell cell1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cell getMarbleCell(Marble marble_2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMarbleTocell(Marble marble, Cell targetCell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValidDestroy(Marble marble, Marble occupyingMarble) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cell getTargetCell(Cell currentCell, int steps, Marble marble) {
		// TODO Auto-generated method stub
		return null;
	}
	}


























