package ie.gmit.sw;

import java.lang.reflect.Constructor;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

/**
* This class manages the MicroStream DB for storing
* class details. It contains all of the necessary query
* methods for displaying on the AppWindow.
*
* @author  Conor Shortt
* @version 1.0
* @since   2020-12-17
*/
public class AlphaDB {

	public TextArea queryText = new TextArea();
	
	private EmbeddedStorageManager db = null;
	private ObservableList<Alpha> root = FXCollections.observableArrayList();
	private StringBuilder sb = new StringBuilder();
	private int i = 0;

	private void go() {
		root = AlphaFactory.getInstance().getAlphas();
		db = EmbeddedStorage.start(root.toArray(), Paths.get("./data"));
		db.storeRoot();
		shutdown();
	}

	/**
	 * Runs the method to display all classes
	 * ordered by name on the AppWindow.
	 */
	public void queryAllClasses() {
		// Query : Show all classes ordered by class name
		ClearStringBuilder();
		go();
		System.out.println("\n[Query] show all classes ordered by name");
		root.stream()
			.sorted((s, t) -> s.getName().compareTo(t.getName()))
			.sorted(Comparator.comparing(Alpha::getName))
			.forEach((s) -> {
				if(s.getName().equals("")) {}
				// Classname null so don't print or add to StringBuilder.
				else {
					System.out.println(s.toString());
					sb.append(s.toString() + "\n");
				}
			});
		this.queryText.setText("\n[Query] show all classes ordered by name \n" + sb);
	}

	/**
	 * Displays all package names along with class names
	 * on the AppWindow.
	 */
	public void queryPackageNames() {
		// Query : Show all package names along with class names
		ClearStringBuilder();
		go();
		System.out.println("\n[Query] show all class packages");
		root.stream()
			.forEach((s) ->  {
				if(s.getName().equals("")) {}
				// Classname null so don't print or add to StringBuilder.
				else {
					System.out.println("| Package: " + s.getPackageName() + " | Class: " + s.getName() + " | \n");
					sb.append("| Package: " + s.getPackageName() + " | Class: " + s.getName() + " | \n");
				}
			});
		this.queryText.setText("\n[Query] show all class packages \n" + sb);
	}

	/**
	 * Displays all interfaces for each class on the AppWindow.
	 */
	public void queryInterfaces() {
		ClearStringBuilder();
		go();
		System.out.println("\n[Query] show all interfaces ordered by class name:");
		root.stream()
			.sorted((s, t) -> s.getName().compareTo(t.getName()))
			.sorted(Comparator.comparing(Alpha::getName))
			.forEach((s) -> {

				String interfaces = "";

				for (Class c : s.getInterfaces()) {
					if(c.toString().equals(""))
						interfaces += "undefined";
					else {
						interfaces += c.toString() + ", ";
					}
				}
				
				if(s.getName().equals("")) {}
				// Classname null so don't print or add to StringBuilder.
				else {
					System.out.println("| Class: " + s.getName() + " |\n | Interfaces: " + interfaces + " | \n");
					sb.append("| Class: " + s.getName() + " | Interfaces: " + interfaces + " | \n");
				}
			});
		this.queryText.setText("\n[Query] show all interfaces ordered by class name:\n" + sb);
	}
	
	/**
	 * Displays all constructors for each class on the AppWindow.
	 */
	public void queryConstructors() {
		ClearStringBuilder();
		go();
		System.out.println("\n[Query] show all constructors ordered by class name");
		root.stream()
			.sorted((s, t) -> s.getName().compareTo(t.getName()))
			.sorted(Comparator.comparing(Alpha::getName))
			.forEach((s) -> {
				String constructors = "";

				for (Constructor c : s.getConstructors()) {
					if(c.toString().equals(""))
						constructors += "undefined";
					else {
						constructors += c.toString() + ", ";
					}
				}
				
				if(s.getName().equals("")) {}
				// Classname null so don't print or add to StringBuilder.
				else {
					System.out.println("| Class: " + s.getName() + " |\n | Constructors: " + constructors + " | \n");
					sb.append("| Class: " + s.getName() + " |\n | Constructors: " + constructors + " | \n");
				}
			});
		this.queryText.setText("\n[Query] show all interfaces ordered by class name:\n" + sb);
	}

	/**
	 * Removes a class from the database.
	 * 
	 * @param a - the class to remove.
	 */
	public void remove(Alpha a) {
		root.remove(a);
		db.storeRoot();
	}
	
	public void shutdown()
	{
		db.shutdown();
	}
	
	private void ClearStringBuilder()
	{
		if(sb.length() > 0)
			sb.delete(0, sb.length() - 1);
	}
}
