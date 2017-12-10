package data;

import java.util.HashSet;

//import java.util.Collection;

import org.ejml.simple.SimpleMatrix;
import data.Construction;

public class Calculation {
	public final int maxBars = 100;

	private static SimpleMatrix[] simpleMatrices;
	private static SimpleMatrix[] simpleCos;
	private static SimpleMatrix[] tableKe;
	private SimpleMatrix[] tableQ;
	private SimpleMatrix[] tableK;
	private SimpleMatrix tableR0;
	private SimpleMatrix tableR;
	private SimpleMatrix globalK;
	private SimpleMatrix reactionsTable;
	private SimpleMatrix[] resultsq;
	private SimpleMatrix[] results;
	private SimpleMatrix resultsR;
	private SimpleMatrix helpTable;

	public Calculation() {

		simpleMatrices = new SimpleMatrix[maxBars];
		simpleCos = new SimpleMatrix[maxBars];

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

	private static int tableNumber = 0;
	// macierz alokacji

	public void createTable(Construction con) {
		Bar[] prety = con.getBars();
		for (int i = 0; i < con.getBarsNumber(); i++) {
			simpleMatrices[tableNumber] = new SimpleMatrix(4, 2 * (Point.getNextId() - 1));
			simpleMatrices[i].set(0, (2 * prety[i].start.getId() - 2), 1);
			simpleMatrices[i].set(1, 2 * prety[i].start.getId() - 1, 1);
			simpleMatrices[i].set(2, (2 * prety[i].end.getId() - 2), 1);
			simpleMatrices[i].set(3, 2 * prety[i].end.getId() - 1, 1);
			tableNumber++;
		}
	}

	// Macierz kosinusów kierunkowych ce[]
	public void createCos(Construction con) {
		Bar[] prety = con.getBars();

		for (int i = 0; i < con.getBarsNumber(); i++) {
			double x = (prety[i].end.getX() - prety[i].start.getX());
			double y = (prety[i].end.getY() - prety[i].start.getY());
			double x1 = x / prety[i].dlugoscPreta();
			double y1 = y / prety[i].dlugoscPreta();

			simpleCos[i] = new SimpleMatrix(2, 4);
			simpleCos[i].set(0, 0, x1);
			simpleCos[i].set(0, 1, y1);
			simpleCos[i].set(1, 2, x1);
			simpleCos[i].set(1, 3, y1);
		}
	}

	// EA - Young modulus multiple by area of cross section of the bar
	private static double EA = 1;

	// macierz sztywności lokalnej preta
	public void stiffnessKe(Construction con) {
		Bar[] prety = con.getBars();
		for (int i = 0; i < con.getBarsNumber(); i++) {
			double x = (prety[i].dlugoscPreta() / 2);
			tableKe[i] = new SimpleMatrix(2, 2);
			tableKe[i].set(0, 0, 0.5 / x * EA);
			tableKe[i].set(0, 1, -0.5 / x * EA);
			tableKe[i].set(1, 0, -0.5 / x * EA);
			tableKe[i].set(1, 1, 0.5 / x * EA);
		}
	}

	public void stiffnessK(Construction con) {
		int dim = 2 * (Point.getNextId() - 1);
		globalK = new SimpleMatrix(dim, dim);
		reactionsTable = new SimpleMatrix(dim, dim);
		helpTable = new SimpleMatrix(dim, dim);

		for (int i = 0; i < con.getBarsNumber(); i++) {
			tableK[i] = new SimpleMatrix();
			tableK[i] = (simpleMatrices[i].transpose()).mult(simpleCos[i].transpose()).mult(tableKe[i])
					.mult(simpleCos[i]).mult(simpleMatrices[i]);

			globalK = globalK.plus(tableK[i]);
			helpTable = helpTable.plus(tableK[i]);
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
		// reakcje w wezlach
		resultsR = helpTable.mult(reactionsTable).minus(tableR0.transpose());
		}

	// global table of forces
	public void tableR0(Construction con) {
		HashSet<Force> forces = con.getForces();
		int dim = 2 * (Point.getNextId() - 1);
		tableR0 = new SimpleMatrix(1, dim);
		
		for (Force e : forces) {
			tableR0.set(0, 2 * e.getNumberOfPoint() - 2, e.getvalueX());
			tableR0.set(0, 2 * e.getNumberOfPoint() - 1, e.getvalueY());
		}
		tableR0.print();
	}

	// table of local displacement and forces in bars
	public void resultsq() {
		for (int i = 0; i < tableNumber; i++) {
			resultsq[i] = simpleCos[i].mult(simpleMatrices[i]).mult(reactionsTable);
			results[i] = tableKe[i].mult(resultsq[i]);
		}
	}

	// global table of supports
	public void tableR(Construction con) {
		HashSet<Support> supports = con.getSupport();
		int dim = 2 * (Point.getNextId() - 1);
		tableR = new SimpleMatrix(1, dim);
		for (Support tmp : supports) {
			tableR.set(0, 2 * tmp.getPointNumber() - 2, 1);
			tableR.set(0, 2 * tmp.getPointNumber() - 1, 1);
		}
		tableR.print();
	}

	public void multipleTable(Construction con) {
		for (int i = 0; i < con.getBarsNumber(); i++) {
			tableQ[i] = new SimpleMatrix(4, 4);
			tableQ[i] = simpleCos[i].mult(simpleMatrices[i]);
		}
	}

	public void printMatrix() {
		for (int i = 0; i < tableNumber; i++) {
			simpleMatrices[i].print();
			simpleCos[i].print();
			tableKe[i].print();
			// tableQ[i].print();
			// resultsq[i].print();
			System.out.println("Sila osiowa w precie nr.: " + (i + 1));
			results[i].print();
		}
		tableR0.print();
		reactionsTable.print();
		System.out.println("Reakcje wezlowe:");
		resultsR.print();
		helpTable.print();
		tableR.print();
		globalK.print();
	}
}