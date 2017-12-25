package data;

import java.io.Serializable;

public class Force implements Serializable {

	private static final long serialVersionUID = -1061557683692218205L;

	private int valueX;
	private int valueY;
	private int numberOfPoint;

	public Force(int valueX, int valueY, int numberOfPoint) {
		super();
		this.valueX = valueX;
		this.valueY = valueY;
		this.numberOfPoint = numberOfPoint;
	}

	public int getvalueX() {
		return valueX;
	}

	public void setvalueX(int valueX) {
		this.valueX = valueX;
	}

	public int getvalueY() {
		return valueY;
	}

	public void setvalueY(int valueY) {
		this.valueY = valueY;
	}

	public int getNumberOfPoint() {
		return numberOfPoint;
	}

	public void setNumberOfPoint(int numberOfPoint) {
		this.numberOfPoint = numberOfPoint;
	}

	@Override
	public String toString() {
		return "Force [valueX=" + valueX + ", valueY=" + valueY + ", numberOfPoint=" + numberOfPoint + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfPoint;
		result = prime * result + valueX;
		result = prime * result + valueY;
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
		Force other = (Force) obj;
		if (numberOfPoint != other.numberOfPoint)
			return false;
		if (valueX != other.valueX)
			return false;
		if (valueY != other.valueY)
			return false;
		return true;
	}
}
