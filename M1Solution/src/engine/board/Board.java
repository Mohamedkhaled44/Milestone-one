package engine.board;

import java.util.ArrayList;

import java.util.Collections;

import engine.GameManager;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.IllegalSwapException;
import exception.InvalidMarbleException;//added from line 99 Board

import model.player.Marble;

import engine.GameManager;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;

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
]
	
	
	//Milestone 2
	
	//12
	//Kan leh tary2a tanya bas atwal
	
	
	@Override
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



























	//M2
	private ArrayList<Cell> getSafeZone(Colour colour){
		for (SafeZone sz : safeZones) {
			if(sz.getColour().equals(colour)) {
				return sz.getCells();
			}
		}
		return null;
	}
	
	private int getPositionInPath(ArrayList<Cell> path, Marble marble) {
		for (int i = 0; i < path.size(); i++) {
	        if (path.get(i).getMarble() == marble) {
	            return i;
	        }
	    }
	    return -1;
	}
	
	private int getBasePosition(Colour colour) {
		for (int i = 0; i < track.size(); i++) {
	        Cell cell = track.get(i);
	        if (cell.getCellType()== CellType.BASE && cell.getMarble().getColour() == colour) {
	            return i;
	        }
	    }
	    return -1;
	}
	
	private int getEntryPosition(Colour colour) {
		for (int i = 0; i < track.size(); i++) {
	        Cell cell = track.get(i);
	        if (cell.getCellType()== CellType.ENTRY && cell.getMarble().getColour() == colour) {
	            return i;
	        }
	    }
	    return -1;
	}
	//5.6.7
	private ArrayList<Cell> validateSteps(Marble marble, int steps) throws IllegalMovementException{
		//ba set up place ashan a save all spots that the marble will visit
		ArrayList<Cell> fullPath = new ArrayList<>();
		//location el marble dlwa2ty
		int trackPosition = getPositionInPath(track, marble);
		ArrayList<Cell> safeZone = getSafeZone(marble.getColour());
	    int safeZonePosition = safeZone != null ? getPositionInPath(safeZone, marble) : -1;
	    
	    //bashof lw el marble on the board aslun
	    if (trackPosition == -1 && safeZonePosition == -1) {
	        throw new IllegalMovementException("The marble cannot be moved.");
	    }
	    //ba remember el color ashan hatfr2 fy el safe zone
	    Colour marbleColour = marble.getColour();
	    
	    
	    //handling the movement on track
	    if(trackPosition !=-1) {
	    	int entryPosition = getEntryPosition(marbleColour);
	    	//calculate distance to entry point
	    	int distanceToEntry;
	    	if (entryPosition > trackPosition) {
	            distanceToEntry = entryPosition - trackPosition;
	    	}else {
	            distanceToEntry = (track.size() - trackPosition) + entryPosition;
	        }
	    	//checking if steps exceeds max possible movement on track
	    	if (Math.abs(steps) > track.size()) {
	            throw new IllegalMovementException("The rank of the card played is too high.");
	        }
	    	//check is the movement will go to the safe zone
	    	if(steps >0 && steps>distanceToEntry) {
	    		//movement will go safe zone
	    		int safeZoneSteps = steps - distanceToEntry;
	    		for (int i = 0; i <= distanceToEntry; i++) {
	                int pos = (trackPosition + i) % track.size();
	                fullPath.add(track.get(pos));
	            }
	    		for (int i = 0; i < safeZoneSteps; i++) {
	                if (i >= safeZone.size()) {
	                    throw new IllegalMovementException("The rank of the card played is too high.");
	                }
	                fullPath.add(safeZone.get(i));
	            }
	        } else {
	        	//movement stays on track
	        	int direction = steps>0 ? 1:-1;
	        	int absSteps= Math.abs(steps);
	        	
	        	for(int i=0;i<=absSteps;i++) {
	        		int pos= (trackPosition +(i*direction)+track.size())% track.size();
	        		fullPath.add(track.get(pos));
	        	}
	        }
	    }
	    
	    
	    //handling movement on safe zone
	    else if(safeZonePosition != -1) {
	    	//backward movement check (card 4 rule)
	    	if(steps<0) {
	    		 throw new IllegalMovementException("Cannot move backwards in Safe Zone");
	    	}
	    	//check if steps exceeds safe zone
	    	if (safeZonePosition + steps >= safeZone.size()) {
	            throw new IllegalMovementException("The rank of the card played is too high for safe zone movement.");
	        }
	    	//add safe zone to the path
	    	for (int i = 0; i <= steps; i++) {
	            fullPath.add(safeZone.get(safeZonePosition + i));
	        }
	    }
	    
	    
	    return fullPath;   
	}
	
	
	private void validatePath(Marble marble, ArrayList<Cell> path, boolean destroy) throws IllegalMovementException {
		    
		    // Check if path is empty
		    if (path == null || path.isEmpty()) {
		        throw new IllegalMovementException("Path cannot be empty");
		    }
		    
		    // Get marble's colour and current position
		    Colour marbleColour = marble.getColour();
		    
		    // Check each cell in the path (excluding the target position)
		    for (int i = 0; i < path.size() - 1; i++) {
		        Cell currentCell = path.get(i);
		        Cell nextCell = path.get(i + 1);
		        
		        // Check if next cell is in a Safe Zone (marbles are safe from interference)
		        if (nextCell.getCellType() == CellType.SAFE) {
		            // King card can't bypass or land on Safe Zone marbles
		            if (destroy) {
		                throw new IllegalMovementException("King cannot bypass or land on Safe Zone marbles");
		            }
		            
		            // Only owner can enter their own Safe Zone
		            for (SafeZone safeZone : safeZones) {
		                if (safeZone.getCells().contains(nextCell) && safeZone.getColour() != marbleColour) {
		                    throw new IllegalMovementException("Cannot enter opponent's Safe Zone");
		                }
		            }
		        }
		        
		        // Check if next cell is occupied (except when using King card)
		        if (nextCell.getMarble() != null && !destroy) {
		            // Check if occupied by own marble (can't land on or bypass)
		            if (nextCell.getMarble().getColour() == marbleColour) {
		                throw new IllegalMovementException("Cannot bypass or land on your own marble");
		            }
		            
		            // Check if occupied by opponent's marble (can't land on but can bypass)
		            if (i == path.size() - 2) { // Last movement to target position
		                throw new IllegalMovementException("Cannot land on opponent's marble");
		            }
		        }
		        
		        // Check if moving through a trap (only applies to path, not target)
		        if (currentCell.isTrap() && !destroy) {
		            throw new IllegalMovementException("Cannot move through a trap cell");
		        }
		    }
		    
		    // Special validation for target position (last cell in path)
		    Cell targetCell = path.get(path.size() - 1);
		    
		    // Check if target is a Base cell (only allowed for fielding)
		    if (targetCell.getCellType() == CellType.BASE) {
		        throw new IllegalMovementException("Cannot move to Base cell during normal movement");
		    }
		    
		    // Check if target is an Entry cell (special rules may apply)
		    if (targetCell.getCellType() == CellType.ENTRY) {
		        // Additional validation could be added here if needed
		    }
		    
		    // Check if target is occupied (unless using King card)
		    if (targetCell.getMarble() != null && !destroy) {
		        throw new IllegalMovementException("Target position is already occupied");
		    }
		    
		    // If marble is moving into Safe Zone, validate it's their own
		    if (targetCell.getCellType() == CellType.SAFE) {
		        boolean validSafeZone = false;
		        for (SafeZone safeZone : safeZones) {
		            if (safeZone.getCells().contains(targetCell) && safeZone.getColour() == marbleColour) {
		                validSafeZone = true;
		                break;
		            }
		        }
		        if (!validSafeZone) {
		            throw new IllegalMovementException("Cannot move into opponent's Safe Zone");
		        }
		    }
		}
	
	private void move(Marble marble, ArrayList<Cell> fullPath, boolean destroy)  throws IllegalDestroyException {
		    
		    // Validate input parameters
		    if (marble == null || fullPath == null || fullPath.isEmpty()) {
		        throw new IllegalArgumentException("Invalid parameters for move operation");
		    }
		    
		    // Get the current and target cells
		    Cell currentCell = fullPath.get(0);
		    Cell targetCell = fullPath.get(fullPath.size() - 1);
		    
		    // (a) Remove the marble from its current cell
		    currentCell.setMarble(null);
		    
		    // (b) Handle marble destroying (special King card logic)
		    if (targetCell.getMarble() != null) {
		        if (destroy) {
		            // King card can destroy opponent's marble
		            Marble destroyedMarble = targetCell.getMarble();
		            if (destroyedMarble.getColour() != marble.getColour()) {
		                // Send destroyed marble home
		                gameManager.sendHome(destroyedMarble);
		            } else {
		                throw new IllegalDestroyException("Cannot destroy your own marble");
		            }
		        } else {
		            // This should have been caught by validatePath, but just in case
		            throw new IllegalDestroyException("Target cell is occupied");
		        }
		    }
		    
		    // (c) Place the marble in the target cell
		    targetCell.setMarble(marble);
		    
		    // (d) Handle trap cell logic
		    if (targetCell.isTrap()) {
		        // Destroy the marble (send it home)
		        gameManager.sendHome(marble);
		        
		        // Deactivate the trap
		        targetCell.setTrap(false);
		        
		        // Assign a new trap cell
		        assignTrapCell();
		    }
		}
	        
	 }

