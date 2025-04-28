package engine.board;

import java.util.ArrayList;
import model.player.Marble;

import engine.GameManager;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import model.Colour;

public class Board implements BoardManager {

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
