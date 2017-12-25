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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pointNumber;
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
		Support other = (Support) obj;
		if (pointNumber != other.pointNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Support [pointNumber=" + pointNumber + "]";
	}
}
