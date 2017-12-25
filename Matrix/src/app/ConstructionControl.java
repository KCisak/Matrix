package app;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import data.Bar;
import data.Construction;
import utils.DataReader;
import utils.FileManager;
import data.Force;
import data.Point;
import data.Support;
import data.Calculation;

public class ConstructionControl {

	// zmienna do komunikacji z użytkownikiem
	private DataReader dataReader;
	private FileManager fileManager;

	// "biblioteka" przechowująca dane
	private Construction construction;
	private Calculation calculation;

	public ConstructionControl() {
		dataReader = new DataReader();
		fileManager = new FileManager();
		try {
			construction = fileManager.readLibraryFromFile();
			System.out.println("Wczytano dane z pliku ");
			
		} catch (ClassNotFoundException | IOException e) {
			construction = new Construction();
			calculation = new Calculation();
			System.out.println("Utworzono nowy plik.");
		}
	}

	/*
	 * Główna pętla programu, która pozwala na wybór opcji i interakcję
	 */
	public void controlLoop() {
		Option option = null;
		while (option != Option.EXIT) {
			try {
				printOptions();
				option = Option.createFromInt(dataReader.getInt());
				switch (option) {
				case ADD_BAR:
					addBar();
					break;
				/*
				 * case ADD_POINT: addPoint(); break;
				 */
				case EXIT:
					exit();
				case ADD_FORCE:
					addForce();
					break;
				case ADD_SUPPORT:
					addSupport();
					break;
				case PRINT:
					printConstruction();
					break;
				case CALCULATE:
					calculation();
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Wprowadzono niepoprawne dane, publikacji nie dodano");
			} catch (NumberFormatException | NoSuchElementException e) {
				System.out.println("Wybrana opcja nie istnieje, wybierz ponownie:");
			}
		}
		// zamykamy strumień wejścia
		dataReader.close();
	}

	private void exit() {
		fileManager.writeLibraryToFile(construction);
	}

	private void addBar() {
		Bar bar = new Bar();
		int n = construction.getPointsNumber();

		System.out.println("Podaj punkt początkowy: ");
		Point start = dataReader.readAndCreatePoint();
		construction.addPoint(start);
		if (n != construction.getPointsNumber()) {
			bar.setStart(start);
		} else {
			Bar[] bars = construction.getBars();
			System.out.println("nesxt id for bars: " + Bar.getNextId());
			for (int i = 0; i < Bar.getNextId() - 2; i++) {
				if (start.equals(bars[i].start)) {
					bar.setStart(bars[i].start);
				}
				if (start.equals(bars[i].end)) {
					bar.setStart(bars[i].end);
				}
			}
		}
		int s = construction.getPointsNumber();

		System.out.println("Podaj punkt końcowy: ");
		Point end = dataReader.readAndCreatePoint();
		construction.addPoint(end);
		if (s != construction.getPointsNumber()) {
			bar.setEnd(end);
		} else {
			Bar[] bars = construction.getBars();
			for (int i = 0; i < Bar.getNextId() - 2; i++) {
				if (end.equals(bars[i].start)) {
					bar.setEnd(bars[i].start);
				}
				if (end.equals(bars[i].end)) {
					bar.setEnd(bars[i].end);
				}
			}
		}
		construction.addBar(bar);
	}

	private void addForce() {
		Force force = dataReader.readAndCreateForce();
		construction.addForce(force);
	}

	private void addSupport() {
		Support support = dataReader.readAndCreateSupport();
		construction.addSupport(support);
	}

	private void printConstruction() {
		construction.printBars();
		construction.printForces();
		construction.printSupport();
	//	System.out.println(Point.getNextId());
	//	System.out.println(construction.getPointsNumber());
	//	calculation.printMatrix();
	}

	private void calculation() {
		if (construction.getSupportNumber() == 0) {
			System.out.println("Przed wykonaniem obliczeń zdefiniuj podpory.");
		} else {
			calculation.createTable(construction);
			calculation.tableR0(construction);
			calculation.tableR(construction);
			calculation.createCos(construction);
			calculation.stiffnessKe(construction);
			calculation.multipleTable(construction);
			calculation.stiffnessK(construction);
			calculation.resultsq();
		}
	}

	private void printOptions() {
		System.out.println("Wybierz opcję: ");
		for (Option o : Option.values()) {
			System.out.println(o);
		}
	}
}
