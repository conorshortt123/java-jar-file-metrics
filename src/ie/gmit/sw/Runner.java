package ie.gmit.sw;

import javafx.application.*;

/**
* Runner class for the application, launches the JavaFX application GUI.
*
* @author  Conor Shortt
* @version 1.0
* @since   2020-12-17
*/
public class Runner {
	
	public static void main(String[] args) {
		System.out.println("[INFO] Launching GUI...");
		Application.launch(AppWindow.class, args);
	}
	
}