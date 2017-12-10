package data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Construction implements Serializable {

	private static final long serialVersionUID = -1824336916175014578L;

	public static final int maxBars = 1000;

	private Bar[] bars;
	HashSet<Point> points = new HashSet<>();
	HashSet<Support> supports = new HashSet<>();
	HashSet<Force> forces = new HashSet<>();

	public static int barsNumber;

	public Construction() {
		bars = new Bar[maxBars];
		points = new LinkedHashSet<>();
		forces = new HashSet<>();
		supports = new HashSet<>();
	}

	public HashSet<Point> getPoints() {
		return points;
	}

	public void setPoints(HashSet<Point> points) {
		this.points = points;
	}

	public int getForceNumber() {
		return forces.size();
	}

	public HashSet<Force> getForces() {
		return forces;
	}

	public int getSupportNumber() {
		return supports.size();
	}

	public HashSet<Support> getSupport() {
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

	public void addPoint(Point point) {
		if (points.contains(point) == true) {
			Point.setNextId(Point.getNextId() - 1);
		} else {
			points.add(point);
		}
		/*
		 * for (Point e : points) { System.out.println(e); }
		 */
	}

	public void printBars() {
		barsNumber = 0;
		for (int k = 0; k < maxBars; k++) {
			if (bars[k] != null) {
				barsNumber++;
			}
		}
		
		if (barsNumber == 0) {
			System.out.println("Zdefiniuj konstrukcje");
		}
		for (int i = 0; i < barsNumber; i++) {
			bars[i].printInfo();
			bars[i].lengthOfBar();
		}
	}

	public void printForces() {
		if (forces.size() == 0) {
			System.out.println("Brak zdefiniowanego obciążenia");
		}
		for (Force u : forces) {
			System.out.println(u);
		}
	}

	public void printSupport() {
		if (supports.size() == 0) {
			System.out.println("Zdefiniuj podpory");
		}

		for (Support u : supports) {
			System.out.println(u);
		}
	}

	public void addForce(Force force) {
		forces.add(force);
		System.out.println("dodano obciążenie");
	}

	public void addSupport(Support support) {
		supports.add(support);
		System.out.println("dodano podpore");
	}
}
