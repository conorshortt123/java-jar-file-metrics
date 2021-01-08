package ie.gmit.sw;

import javafx.application.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
* This class brings all of the separated class components together,
* and implements them into a stage, which contains the tableview,
* file chooser pane, a toolbar which contains multiple buttons
* for running queries, and text box for displaying query output.
*
* @author  Conor Shortt
* @version 1.0
* @since   2020-12-17
*/
public class AppWindow extends Application {
	private ObservableList<Alpha> customers; // The Model - a list of observers.
	private TableView<Alpha> tv; // The View - a composite of GUI components
	private FileChoice fc = new FileChoice();
	private CustomerTableView ctv = new CustomerTableView();
	private AlphaDB adb = new AlphaDB();
	private VBox box;

	@Override
	public void start(Stage stage) throws Exception { // This is a ***Template Method***
		AlphaFactory cf = AlphaFactory.getInstance(); // Get the singleton instance
		customers = cf.getAlphas(); // Get the Model
		tv = ctv.getTableViewInstance();
		/*
		 * The GUI is based on the ** Composite Pattern ** and is a tree of nodes, some
		 * of which are composite nodes (containers) and some are leaf nodes (controls).
		 * A stage contains 1..n scenes, each of which is a container window for other
		 * containers or controls.
		 * 
		 * JavaFX, Android and most GUI frameworks allow the creation of windows using a
		 * declarative format, typically XML. In the case of JavaFX, the syntax is
		 * called FXML. The idea of this (which is quite an old one!), is to separate
		 * the View from the Controller and Model (good practice) and allow
		 * non-programmers to create Views in XML using SceneBuilder or the equivalent
		 * that should integrate seamlessly with a suite of controllers and models
		 * designed by a programmer. In practice, I find that XML slows down development
		 * to a crawl and I prefer to programme the GUI from scratch, as it's much
		 * quicker, even if it is verbose.
		 */
		stage.setTitle("GMIT - B.Sc. in Computing (Software Development)");
		stage.setWidth(800);
		stage.setHeight(600);

		/*
		 * The following is an example of the ** Observer Pattern**. Use a lambda
		 * expression to plant an EventHandler<WindowEvent> observer on the stage close
		 * button. The "click" will be queued and handled by an event dispatch thread
		 * when it gets a chance.
		 */
		stage.setOnCloseRequest((e) -> System.exit(0));

		/*
		 * Create the MVC View using the **Composite Pattern**. We can Build the GUi
		 * tree using one or more composites to create branches and one or more controls
		 * to handle user interactions. Composites and containers and controls are leaf
		 * nodes that the user can send gestures to.
		 * 
		 * The root container we will use is a VBox. All the subtypes of the class Pane
		 * are composite nodes and can be used as containers for other nodes (composites
		 * and leaf nodes). The choice of container is also an example of the **Strategy
		 * Pattern**. The Scene object is the Context and the layout container
		 * (AnchorPane, BorderPanel, VBox, FlowPane etc) is a concrete strategy.
		 */
		box = new VBox();
		box.setPadding(new Insets(10));
		box.setSpacing(8);

		// **Strategy Pattern**. Configure the Context with a Concrete Strategy
		Scene scene = new Scene(box);
		stage.setScene(scene);

		ToolBar toolBar = new ToolBar(); // A ToolBar is a composite node for Buttons (leaf nodes)

		Button btnQuit = new Button("Quit"); // A Leaf node
		btnQuit.setOnAction(e -> System.exit(0)); // Plant an observer on the button
		toolBar.getItems().add(btnQuit); // Add to the parent node and build the tree

		Button btnDelete = new Button("Delete from DB"); // A Leaf node
		btnDelete.setOnAction(e -> { // Plant an observer on the button
			/*
			 * Get the selected Customer object from the View (TableView) and remove it from
			 * the Model (ObservableList). Do not try to update the view directly.
			 */

			Alpha c = tv.getSelectionModel().getSelectedItem();
			System.out.println("Selected class = " + c);
			customers.remove(c);
			adb.remove(c);
		});
		toolBar.getItems().add(btnDelete); // Add to the parent node and build the tree
		
		Button btnQuery = new Button("Query Classes"); // A Leaf node
		btnQuery.setOnAction(e -> { // Plant an observer on the button
			/*
			 * Get the selected Customer object from the View (TableView) and remove it from
			 * the Model (ObservableList). Do not try to update the view directly.
			 */

			adb.queryAllClasses();
		});
		toolBar.getItems().add(btnQuery); // Add to the parent node and build the tree
		
		Button btnPackageQuery = new Button("Query Packages"); // A Leaf node
		btnPackageQuery.setOnAction(e -> { // Plant an observer on the button
			/*
			 * Get the selected Customer object from the View (TableView) and remove it from
			 * the Model (ObservableList). Do not try to update the view directly.
			 */

			adb.queryPackageNames();
		});
		toolBar.getItems().add(btnPackageQuery); // Add to the parent node and build the tree
		
		Button btnInterfaceQuery = new Button("Query Interfaces"); // A Leaf node
		btnInterfaceQuery.setOnAction(e -> { // Plant an observer on the button
			/*
			 * Get the selected Customer object from the View (TableView) and remove it from
			 * the Model (ObservableList). Do not try to update the view directly.
			 */

			adb.queryInterfaces();
		});
		toolBar.getItems().add(btnInterfaceQuery); // Add to the parent node and build the tree
		
		Button btnConstructorQuery = new Button("Query Constructors"); // A Leaf node
		btnConstructorQuery.setOnAction(e -> { // Plant an observer on the button
			/*
			 * Get the selected Customer object from the View (TableView) and remove it from
			 * the Model (ObservableList). Do not try to update the view directly.
			 */

			adb.queryConstructors();
		});
		toolBar.getItems().add(btnConstructorQuery); // Add to the parent node and build the tree


		/*
		 * Add all the sub trees of nodes to the parent node and build the tree
		 */
		box.getChildren().add(fc.getFileChooserPane(stage)); // Add the sub tree to the main tree
		box.getChildren().add(ctv.getTableView()); // Add the sub tree to the main tree
		box.getChildren().add(toolBar); // Add the sub tree to the main tree
		box.getChildren().add(adb.queryText); // Add the sub tree to the main tree
		// Display the window
		stage.show();
		stage.centerOnScreen();
	}
	
}