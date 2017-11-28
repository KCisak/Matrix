package app;

public class ConstructionApp {

	public static void main(String[] args) {

		final String appName = "FEM v1.0";
		System.out.println(appName);
		ConstructionControl control = new ConstructionControl();
		control.controlLoop();

	}
}
