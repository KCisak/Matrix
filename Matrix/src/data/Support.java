package data;

import java.io.Serializable;

public class Support implements Serializable{

	 /**
	 *
	 */
	private static final long serialVersionUID = -5140370011381966707L;
	final String zamocowanie;
	 final String przegubowoPrzesuwna;

	public Support(String zamocowanie, String przegubowoPrzesuwna) {
		this.zamocowanie = zamocowanie;
		this.przegubowoPrzesuwna = przegubowoPrzesuwna;
	}

}
