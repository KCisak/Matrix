package data;

import java.io.Serializable;

public class Construction implements Serializable {

	private static final long serialVersionUID = -1824336916175014578L;

	public static final int maxBars = 100;

	private Bar[] bars;
	private Force[] forces;
	private Support[] supports;
	
	public static int barsNumber;
	private int forceNumber;
	private int supportNumber;

	public Construction() {
		bars = new Bar[maxBars];
		forces = new Force[maxBars];
		supports = new Support[maxBars];

	}

	public int getForceNumber() {
		return forceNumber;
	}
	public Force[] getForces() {
		return forces;
	}
	
	public int getSupportNumber() {
		return supportNumber;
	}
	
	public Support[] getSupport() {
		return supports;
	}
	
	public int getBarsNumber() {
		return barsNumber;
	}

	public Bar[] getBars() {
		return bars;
	}

	public void addBar(Bar bar) throws ArrayIndexOutOfBoundsException {

		if (barsNumber == maxBars) {
			throw new ArrayIndexOutOfBoundsException("Maksymalna liczba pretów: " + maxBars);
		}
		bars[barsNumber] = bar;
		barsNumber++;
	}

	public void printBars() {
		if (barsNumber == 0) {
			System.out.println("Zdefiniuj konstrukcje");
		}
		for (int i = 0; i < barsNumber; i++) {
			bars[i].printInfo();
			bars[i].lengthOfBar();
		}
	}

	public void printForces() {
		if (forceNumber == 0) {
			System.out.println("Zdefiniuj obciazenie");
		}
		for (int i = 0; i < forceNumber; i++) {
			System.out.println(forces[i]);
		}
	}

	public void printSupport() {
		if (supportNumber == 0) {
			System.out.println("Zdefiniuj podpory");
		}
		for (int i = 0; i < supportNumber; i++) {
			System.out.println(supports[i]);
		}
	}

	public void addForce(Force force) {
		forces[forceNumber] = force;
		System.out.println("dodano obciążenie");
		forceNumber++;
	}

	public void addSupport(Support support) {
		supports[supportNumber] = support;
		System.out.println("dodano podpore");
		supportNumber++;
	}

	public void deletePoints() {
		Bar.setNextId(barsNumber + 1);
		for (int i = 0; i < Bar.getNextId() - 1; i++) {
			for (int k = 0; k < Bar.getNextId() - 1; k++) {
				if (bars[i].start.equals(bars[k].start) && k != i) {
					bars[k].setStart(bars[i].start);
				}
				if (bars[i].start.equals(bars[k].end) && k != i) {
					bars[k].setEnd(bars[i].start);
				}
				if (bars[i].end.equals(bars[k].end) && k != i) {
					bars[k].setEnd(bars[i].end);
				}
			}
		}
	}

	public void renumerPoints() {
		int newNumbers = 1;
		for (int k = 0; k < Bar.getNextId() - 1; k++) {
			bars[k].start.setId(-1);
			bars[k].end.setId(-1);
		}

		for (int j = 0; j < Bar.getNextId() - 1; j++) {
			if (bars[j].start.getId() < 0) {
				bars[j].start.setId(newNumbers);
				newNumbers++;
			}
			if (bars[j].end.getId() < 0) {
				bars[j].end.setId(newNumbers);
				newNumbers++;
			}
		}

		Point.setNextId(newNumbers);
		System.out.println("barsNumber: " + barsNumber);
		System.out.println("Point.getNextId(): " + Point.getNextId());
	}

}
