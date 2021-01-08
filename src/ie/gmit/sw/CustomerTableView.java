package ie.gmit.sw;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.util.*;

/**
* This class was abstracted to encapsulate the getTabeView method.
* The method is built to display class details. The class
* details are displayed in multiple columns.
*
* @author  Conor Shortt
* @version 1.0
* @since   2020-12-17
*/
public class CustomerTableView extends TableView<Alpha>{
	
	AlphaFactory cf = AlphaFactory.getInstance();
	private ObservableList<Alpha> customers = cf.getAlphas();
	private TableView<Alpha> tv = new TableView<>(customers);; // The View - a composite of GUI components
	
	/**
	 * This method builds a table to display the customer details.
	 * 
	 * @return TableView<Alpha> - The table view to display customer details.
	 */
	protected TableView<Alpha> getTableView() {
		
		/*
		 * The next line is **very important**. We configure a View (TableView) with a
		 * Model (ObservableList<Customer>). The Model is observable and will propagate
		 * any changes to it to the View or Views that render it.
		 */

		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Stretch columns to fit the window

		/*
		 * Create a TableColumn from the class Customer that displays some attribute as
		 * a String. This field is Observable and the method call() will be fired when
		 * the table is being refreshed or when the model is updated. The instance of
		 * the interface Callback is implemented as an anonymous inner class and acts as
		 * a Controller for the table column. Callback is also an example of an Observer
		 * and the inner class a ConcreteObserver. The method call() is analogous to the
		 * notify() method in the Observer Pattern.
		 */
		TableColumn<Alpha, String> name = new TableColumn<>("Class Name");
		name.setCellValueFactory(new Callback<CellDataFeatures<Alpha, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Alpha, String> p) {
				return new SimpleStringProperty(p.getValue().getName());
			}
		});

		TableColumn<Alpha, String> packageName = new TableColumn<>("Package Name");
		packageName.setCellValueFactory(new Callback<CellDataFeatures<Alpha, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Alpha, String> p) {
				return new SimpleStringProperty(p.getValue().getPackageName().toString());
			}
		});

		// Creates an observable table column from a String field extracted from the
		// Customer class
		TableColumn<Alpha, Boolean> iface = new TableColumn<>("IsIface");
		iface.setCellValueFactory(new Callback<CellDataFeatures<Alpha, Boolean>, ObservableValue<Boolean>>() {
			public ObservableValue<Boolean> call(CellDataFeatures<Alpha, Boolean> p) {
				return new SimpleBooleanProperty(p.getValue().isIface());
			}
		});

		// Creates an observable table column from an Image attribute of the Customer
		// class
		TableColumn<Alpha, String> interfaces = new TableColumn<>("Interfaces");
		interfaces.setCellValueFactory(new Callback<CellDataFeatures<Alpha, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Alpha, String> p) {
				String interfaces = "";
				for (Class c : p.getValue().getInterfaces()) {
					interfaces += c.toString() + "\n";
				}

				return new SimpleStringProperty(interfaces);
			}
		});

		TableColumn<Alpha, String> constructors = new TableColumn<>("Constructors");
		constructors.setCellValueFactory(new Callback<CellDataFeatures<Alpha, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Alpha, String> p) {
				String constructors = "";
				for (Constructor c : p.getValue().getConstructors()) {
					constructors += c.toString() + "\n";
				}

				return new SimpleStringProperty(constructors);
			}
		});

		TableColumn<Alpha, String> fields = new TableColumn<>("Fields");
		fields.setCellValueFactory(new Callback<CellDataFeatures<Alpha, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Alpha, String> p) {
				String fields = "";
				for (Field f : p.getValue().getFields()) {
					fields += f.toString() + "\n";
				}

				return new SimpleStringProperty(fields);
			}
		});

		TableColumn<Alpha, String> methods = new TableColumn<>("Methods");
		methods.setCellValueFactory(new Callback<CellDataFeatures<Alpha, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Alpha, String> p) {
				String methodsString = "";
				for (Method m : p.getValue().getMethods()) {
					methodsString += m.toString() + "\n";
				}
				return new SimpleStringProperty(methodsString);
			}
		});

		tv.getColumns().add(name); // Add nodes to the tree
		tv.getColumns().add(packageName); // Add nodes to the tree
		tv.getColumns().add(iface); // Add nodes to the tree
		tv.getColumns().add(interfaces); // Add nodes to the tree
		tv.getColumns().add(constructors); // Add nodes to the tree
		tv.getColumns().add(fields); // Add nodes to the tree
		tv.getColumns().add(methods); // Add nodes to the tree
		return tv;
	}
	
	public TableView<Alpha> getTableViewInstance()
	{
		return tv;
	}
}
