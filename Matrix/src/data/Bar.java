package data;

import java.io.Serializable;
import data.Point;

public class Bar implements Serializable {

	private static final long serialVersionUID = 8553410132097087416L;
	public Point start;
	public Point end;

	private int id;
	private static int nextId = 1;

	public static void setNextId(int nextId) {
		Bar.nextId = nextId;
	}
	public Bar() {
		id = nextId;
		nextId++;
		System.out.println("next ID: "+ nextId);
	}
	
	public Bar(Point start) {
		this.start = start;
	
	}
	
	public Bar(Point start, Point end) {
		this.start = start;
		this.end = end;
		
		id = nextId;
		nextId++;
		System.out.println("next ID: "+ nextId);
	}

	public Bar(Bar bar) {
		this(bar.start, bar.end);
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId() {
		id = nextId;
		nextId++;
	}

	public static int getNextId() {
		return nextId;
	}

	public void printInfo() {
		String info = getId() + "; " + getStart().getX() + "; " + getStart().getY() + "; " + getEnd().getX() + "; "
				+ getEnd().getY() + " numery punktow: " + getStart().getId() + " & " + getEnd().getId();
		//String info2 = "numer punktu test:" + getStart().getNumber();
		System.out.println(info);
	}

	public void lengthOfBar() {
		double dist = java.awt.Point.distance(getStart().getX(), getStart().getY(), getEnd().getX(), getEnd().getY());
		System.out.println("Dugośc preta: " + String.format("%.2f", dist) + " m");
	}

	public double dlugoscPreta() {
		double y = java.awt.Point.distance(getStart().getX(), getStart().getY(), getEnd().getX(), getEnd().getY());
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bar other = (Bar) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}
