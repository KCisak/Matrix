package data;

import java.io.Serializable;
import org.ejml.simple.SimpleMatrix;

public class Construction implements Serializable {

	private static final long serialVersionUID = -1824336916175014578L;

	public final int maxBars = 1000;

	private Bar[] bars;
	private Force[] forces;
	private Support[] supports;
	private int barsNumber;
	private int forceNumber;
	private int supportNumber;
	private SimpleMatrix[] simpleMatrices;
	private SimpleMatrix[] simpleCos;
	private SimpleMatrix[] tableQ;
	private SimpleMatrix[] tableKe;
	private SimpleMatrix[] tableK;
	private SimpleMatrix tableR0;
	private SimpleMatrix tableR;
	private SimpleMatrix globalK;
	private SimpleMatrix reactionsTable;
	private SimpleMatrix[] resultsq;
	private SimpleMatrix[] results;
	private SimpleMatrix resultsR;
	private SimpleMatrix helpTable;

	public Construction() {
		bars = new Bar[maxBars];
		forces = new Force[maxBars];
		supports = new Support[maxBars];
		simpleCos = new SimpleMatrix[maxBars];
		simpleMatrices = new SimpleMatrix[maxBars];
		tableQ = new SimpleMatrix[maxBars];
		tableKe = new SimpleMatrix[maxBars];
		tableK = new SimpleMatrix[maxBars];
		resultsq = new SimpleMatrix[maxBars];
		results = new SimpleMatrix[maxBars];
		tableR0 = new SimpleMatrix();
		tableR = new SimpleMatrix();
		globalK = new SimpleMatrix();
		reactionsTable = new SimpleMatrix();
		resultsR = new SimpleMatrix();
		helpTable = new SimpleMatrix();
	
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

	// Tablice alokacji A[]

	private int tableNumber = 0;

	public void createTable() {
		if (barsNumber == 0) {
			System.err.println("Zdefiniuj konstrukcje");
		} else {
			int n = barsNumber;
			for (int i = 0; i < n; i++) {
				simpleMatrices[tableNumber] = new SimpleMatrix(4, 2 * (Point.getNextId() - 1));
				simpleMatrices[i].set(0, (2 * bars[i].start.getId() - 2), 1);
				simpleMatrices[i].set(1, 2 * bars[i].start.getId() - 1, 1);
				simpleMatrices[i].set(2, (2 * bars[i].end.getId() - 2), 1);
				simpleMatrices[i].set(3, 2 * bars[i].end.getId() - 1, 1);

				System.out.print("nowa tablica dla preta: ");
				System.out.println(i + 1);
				System.out.println("tableNumber: " + tableNumber);

				tableNumber++;
			}
		}
	}

	// Macierz kosinusów kierunkowych ce[]

	public void createCos() {

		int n = barsNumber;

		for (int i = 0; i < n; i++) {

			double x = (bars[i].end.getX() - bars[i].start.getX());
			double y = (bars[i].end.getY() - bars[i].start.getY());
			double x1 = x / bars[i].dlugoscPreta();
			double y1 = y / bars[i].dlugoscPreta();

			simpleCos[i] = new SimpleMatrix(2, 4);
			simpleCos[i].set(0, 0, x1);
			simpleCos[i].set(0, 1, y1);
			simpleCos[i].set(1, 2, x1);
			simpleCos[i].set(1, 3, y1);

		}
	}

	// EA - Young modulus multiple by area of cross section of the bar
	private double EA = 1;

	public void stiffnessKe() {
		int n = barsNumber;
		for (int i = 0; i < n; i++) {
			double x = (bars[i].dlugoscPreta() / 2);
			tableKe[i] = new SimpleMatrix(2, 2);
			tableKe[i].set(0, 0, 0.5 / x * EA);
			tableKe[i].set(0, 1, -0.5 / x * EA);
			tableKe[i].set(1, 0, -0.5 / x * EA);
			tableKe[i].set(1, 1, 0.5 / x * EA);

		}
	}

	public void stiffnessK() {
		int n = barsNumber;
		int dim = 2 * (Point.getNextId() - 1);
		globalK = new SimpleMatrix(dim, dim);
		reactionsTable = new SimpleMatrix(dim, dim);
		helpTable = new SimpleMatrix(dim, dim);
		
		for (int i = 0; i < n; i++) {
			tableK[i] = new SimpleMatrix();
			tableK[i] = (simpleMatrices[i].transpose()).mult(simpleCos[i].transpose().mult(tableKe[i]))
					.mult(simpleCos[i]).mult(simpleMatrices[i]);
			globalK = globalK.plus(tableK[i]);
			helpTable= globalK.plus(tableK[i]);
		}
		
		for (int i = 0; i < dim; i++) {
			if (tableR.get(0, i) == 1) {
				for (int j = 0; j < dim; j++) {
					globalK.set(j, i, 0);
					globalK.set(i, j, 0);
				}
				globalK.set(i, i, 1);
			}

		}
		reactionsTable = globalK.invert().mult(tableR0.transpose());
		resultsR = helpTable.mult(reactionsTable).minus(tableR0.transpose()).transpose();
	}

	// global table of forces
	public void tableR0() {
		int dim = 2 * (Point.getNextId() - 1);
		tableR0 = new SimpleMatrix(1, dim);

		for (int i = 0; i < forceNumber; i++) {
			tableR0.set(0, 2 * forces[i].getNumberOfPoint() - 2, forces[i].getvalueX());
			tableR0.set(0, 2 * forces[i].getNumberOfPoint() - 1, forces[i].getvalueY());
		}
	}

	// table of local displacement and forces in bars
	public void resultsq() {
		int n = barsNumber;
		for (int i = 0; i < n; i++) {
			resultsq[i] = simpleCos[i].mult(simpleMatrices[i]).mult(reactionsTable);
			results[i]=tableKe[i].mult(resultsq[i]);
		}
	}
	
	// global table of supports
	public void tableR() {
		int dim = 2 * (Point.getNextId() - 1);
		tableR = new SimpleMatrix(1, dim);

		for (int i = 0; i < supportNumber; i++) {
			tableR.set(0, 2 * supports[i].getPointNumber() - 2, 1);
			tableR.set(0, 2 * supports[i].getPointNumber() - 1, 1);
		}
	}

	public void multipleTable() {
		int n = barsNumber;
		for (int i = 0; i < n; i++) {
			tableQ[i] = new SimpleMatrix(4, 4);
			tableQ[i] = simpleCos[i].mult(simpleMatrices[i]);
		}
	}

	public void printMatrix() {
		for (int i = 0; i < tableNumber; i++) {
			//simpleMatrices[i].print();
			//simpleCos[i].print();
			//tableKe[i].print();
		//	tableQ[i].print();
			resultsq[i].print();
			results[i].print();
		}
	//	tableR0.print();
	//	reactionsTable.print();
	//	tableR.print();
	
	resultsR.print();
		//helpTable.print();
	}
}
