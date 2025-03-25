package engine.board;

import java.util.ArrayList;

import engine.GameManager;
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

}
