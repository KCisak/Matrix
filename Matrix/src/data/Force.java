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

}
