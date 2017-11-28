package data;

import java.io.Serializable;

public class Support implements Serializable {

	private static final long serialVersionUID = -5140370011381966707L;

	private int pointNumber;

	public void setPointNumber(int pointNumber) {
		this.pointNumber = pointNumber;
	}

	public Support(int pointNumber) {
		super();
		this.pointNumber = pointNumber;
	}

	public int getPointNumber() {
		return pointNumber;
	}

	@Override
	public String toString() {
		return "Support [pointNumber=" + pointNumber + "]";
	}
	
}
