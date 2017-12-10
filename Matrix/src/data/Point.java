package data;

import java.io.Serializable;

public class Point implements Serializable{

	private static final long serialVersionUID = -7564154470576190237L;
	private double x;
	private double y;
	private int number;
	// private double z;

	public static int nextId = 1;
	private int id;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public static void setNextId(int nextId) {
		Point.nextId = nextId;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		id = nextId;
		nextId++;
	}
	
	public Point(double x, double y, int number) {
		this.x = x;
		this.y = y;
		this.number = number;
		id = nextId;
		nextId++;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setId() {
		id = nextId;
		nextId++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static int getNextId() {
		return nextId;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", number=" + number + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
}
