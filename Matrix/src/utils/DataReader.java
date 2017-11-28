package utils;

import java.util.Scanner;
import data.Bar;
import data.Point;
import data.Force;
import data.Support;

public class DataReader {
	private Scanner sc;

	public DataReader() {
		sc = new Scanner(System.in);
	}

	public void close() {
		sc.close();
	}

	public int getInt() {
		int number = sc.nextInt();
		sc.nextLine();
		return number;
	}

	public Bar readAndCreateBar() {
		System.out.println("[początek] Podaj współrzędną X: ");
		Double x = sc.nextDouble();
		System.out.println("[początek] Podaj współrzędną Y: ");
		Double y = sc.nextDouble();
		Point start = new Point(x, y);
		System.out.println("[koniec] Podaj współrzędną X: ");
		Double x2 = sc.nextDouble();
		System.out.println("[koniec] Podaj współrzędną Y: ");
		Double y2 = sc.nextDouble();
		Point end = new Point(x2, y2);
		sc.nextLine();
		return new Bar(start, end);
	}
	public Force readAndCreateForce() {
		System.out.println("Czy kierunek X (0: nie; 1: tak (znak +/- oznacza kierunek wektora): ");
		int x = sc.nextInt();
		System.out.println("Czy kierunek Y (0: nie; 1: tak (znak +/- oznacza kierunek wektora): ");
		int y = sc.nextInt();
		System.out.println("Podan numer punktu, w kotrym przylozona jest sila: ");
		int numberOfPoint = sc.nextInt();
		sc.nextLine();
		return new Force(x, y, numberOfPoint);
	}
	public Support readAndCreateSupport() {
		System.out.println("Poddaj numery wezlow z podporami: ");
		int x = sc.nextInt();
		sc.nextLine();
		return new Support(x);
	}
}