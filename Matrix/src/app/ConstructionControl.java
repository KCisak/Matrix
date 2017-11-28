package app;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import data.Bar;
import data.Construction;
import utils.DataReader;
import utils.FileManager;
import data.Force;
import data.Support;

public class ConstructionControl {

	// zmienna do komunikacji z użytkownikiem
	private DataReader dataReader;
	private FileManager fileManager;

	// "biblioteka" przechowująca dane
	private Construction construction;

	public ConstructionControl() {
		dataReader = new DataReader();
		fileManager = new FileManager();
		try {
			construction = fileManager.readLibraryFromFile();
			System.out.println("Wczytano dane biblioteki z pliku ");
		} catch (ClassNotFoundException | IOException e) {
			construction = new Construction();
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
				case CORRECT:
					deletePoints();
					break;
				case CALCULATE:
					construction.tableR0();
					construction.tableR();
					construction.createTable();
					construction.createCos();
					construction.stiffnessKe();
					construction.multipleTable();
					construction.stiffnessK();
					construction.resultsq();
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
		Bar bar = dataReader.readAndCreateBar();
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
		construction.printMatrix();
	}

	private void deletePoints() {
		construction.deletePoints();
		construction.renumerPoints();
	}

	private void printOptions() {
		System.out.println("Wybierz opcję: ");
		for (Option o : Option.values()) {
			System.out.println(o);
		}
	}
}
