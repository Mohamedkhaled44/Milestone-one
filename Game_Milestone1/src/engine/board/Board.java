package engine.board;

import engine.GameManager;
import model.Colour;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board implements BoardManager {
    private final GameManager gameManager;
    private final ArrayList<Cell> track;
    private final ArrayList<SafeZone> safeZones;
    private int splitDistance;
	private int noOfCell;

    public Board(ArrayList<Colour> colourOrder, GameManager gameManager) {
        this.gameManager = gameManager;
        this.track = new ArrayList<>();
        this.safeZones = new ArrayList<>();
        this.splitDistance = 3; // default split distance.
        noOfCell=100;

        // Create 100 cells 
        for (int i = 0; i < noOfCell ; i++) {
            if (i % 25==0) {
                track.add(new Cell(CellType.BASE));
            } else if ((i+2)%25==0) {
                track.add(new Cell(CellType.ENTRY));
            } else {
                track.add(new Cell(CellType.NORMAL));
            }
        }

        // Flag 8 random NORMAL cells as traps.
        for (int i = 0; i < 8; i++) {
            assignTrapCell();
        }

        // Create safe zones for each colour.
        for (Colour colour : colourOrder) {
            safeZones.add(new SafeZone(colour));
        }
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

    @Override
    public int getSplitDistance() {
        return splitDistance;
    }

    public void setSplitDistance(int splitDistance) {
        this.splitDistance = splitDistance;
    }

    // assignTrapCell must be private
    private void assignTrapCell() {
        Random rand = new Random();
        int index = rand.nextInt(track.size());
        Cell cell = track.get(index);
        if (cell.getCellType() == CellType.NORMAL && !cell.isTrap()) {
            cell.setTrap(true);
        } else {
            assignTrapCell();
        }
    }
}