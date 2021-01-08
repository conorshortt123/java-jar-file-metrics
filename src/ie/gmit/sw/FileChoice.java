package ie.gmit.sw;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
* This class encapsulates the File Chooser Pane, which
* is used for uploading the jar file. This was abstracted
* to allow for a more loosely coupled design.
*
* @author  Conor Shortt
* @version 1.0
* @since   2020-12-17
*/
public class FileChoice {
	
	private TextField txtFile; //A control, part of the View and a leaf node.

	/**
	 * This method builds a TitledPane containing the controls for the file chooser
	 * part of the application.
	 * 
	 * @param stage
	 * @return TitledPane - The file chooser pane.
	 */
	public TitledPane getFileChooserPane(Stage stage) {
		VBox panel = new VBox(); //** A concrete strategy ***

		txtFile = new TextField(); //A leaf node

		FileChooser fc = new FileChooser(); //A leaf node
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));

		Button btnOpen = new Button("Select File"); //A leaf node
		btnOpen.setOnAction(e -> { //Plant an observer on the button
			File f = fc.showOpenDialog(stage);
			txtFile.setText(f.getAbsolutePath());
		});

		Button btnProcess = new Button("Process"); //A leaf node
		btnProcess.setOnAction(e -> { //Plant an observer on the button
			File f = new File(txtFile.getText());
			ProcessFile pf = new ProcessFile(f);
			try {
				pf.LoadClasses();
			} catch (IOException | ClassNotFoundException e2) {}
		});
		
		ToolBar tb = new ToolBar(); //A composite node
		tb.getItems().add(btnOpen); //Add to the parent node and build a sub tree
		tb.getItems().add(btnProcess); //Add to the parent node and build a sub tree

		panel.getChildren().add(txtFile); //Add to the parent node and build a sub tree
		panel.getChildren().add(tb); //Add to the parent node and build a sub tree

		TitledPane tp = new TitledPane("Select File to Process", panel); //Add to the parent node and build a sub tree
		tp.setCollapsible(false);
		return tp;
	}
	
}
