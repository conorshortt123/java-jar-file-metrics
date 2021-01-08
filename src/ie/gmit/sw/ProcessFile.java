package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
* This class processes the jar file inputed by the user.
* The jar file is read line and parsed to the correct format.
* The class is then passed to the getClassInfo method which
* returns an object Alpha which contains all of the necessary
* class information such as class name, methods, etc. The Alpha
* object is then passed to the AlphaFactory instance which adds it
* to the model.
*
* @author  Conor Shortt
* @version 1.0
* @since   2020-12-17
*/
public class ProcessFile {

	private File f;
	private int loc;
	private String className;
	private String pack;
	private boolean iface;
	private Class[] interfaces;
	private Constructor[] cons;
	private Field[] fields;
	private Method[] methods;

	public ProcessFile(File f) {
		this.f = f;
	}
	
	/**
	 * Loads each class contained in the jar file (excluding standard library classes),
	 * formats the name correctly, passes the class to getClassInfo and gets back the
	 * Alpha object which is then passed to AlphaFactory.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void LoadClasses() throws FileNotFoundException, IOException, ClassNotFoundException {

		JarInputStream in = new JarInputStream(new FileInputStream(f));
		JarEntry next = in.getNextJarEntry();
		Class cls = null;

		while (next != null) {
			if (next.getName().endsWith(".class")) {
				String name = next.getName().replaceAll("/", "\\.");
				name = name.replaceAll(".class", "");
				if (!name.contains("$"))
					name.substring(0, name.length() - ".class".length());

				cls = Class.forName(name);
				
				// Create alpha object which contains a variety of class information
				Alpha alpha = getClassInfo(cls);
				
				// Get a handle on the singleton factory and pass alpha to the addAlpha method.
				AlphaFactory af = AlphaFactory.getInstance();
				af.addAlpha(alpha);
			}
			next = in.getNextJarEntry();
		}
		// Error check of invalid class names
		System.out.println("Finished while loop");
	}

	private Alpha getClassInfo(Class cls) {
		className = cls.getSimpleName();
		pack = cls.getPackageName();
		iface = cls.isInterface();
		interfaces = cls.getInterfaces();
		cons = cls.getDeclaredConstructors();
		fields = cls.getFields();
		methods = cls.getDeclaredMethods();

		Alpha a = new Alpha(className, pack, iface, interfaces, cons, fields, methods);

		return a;
	}

}
