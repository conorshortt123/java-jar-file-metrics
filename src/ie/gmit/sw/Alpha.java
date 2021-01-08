package ie.gmit.sw;

import java.lang.reflect.*;
import java.time.*;
import javafx.scene.image.*;

/**
* This is the <i>class</i> object that stores the respective
* metrics for the class (i.e name, package, interfaces).
* It contains all of the necessary getters and setters, 
* and an overridden toString() method that outputs formatted
* class details.
*
* @author  Conor Shortt
* @version 1.0
* @since   2020-12-17
*/
public class Alpha {
	
	private String className;
	private String packageName;
	private boolean iface;
	private Class[] interfaces;
	private Constructor[] constructors;
	private Field[] fields;
	private Method[] methods;
	
	public Alpha(String className, String packageName, boolean iface, Class[] interfaces,
			Constructor[] constructors, Field[] fields, Method[] methods) {
		super();
		this.className = className;
		this.packageName = packageName;
		this.iface = iface;
		this.interfaces = interfaces;
		this.constructors = constructors;
		this.fields = fields;
		this.methods = methods;
	}
	
	public String getName() {
		return className;
	}
	public void setName(String className) {
		this.className = className;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isIface() {
		return iface;
	}
	public void setIface(boolean iface) {
		this.iface = iface;
	}
	public Class[] getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(Class[] interfaces) {
		this.interfaces = interfaces;
	}
	public Constructor[] getConstructors() {
		return constructors;
	}
	public void setConstructors(Constructor[] constructors) {
		this.constructors = constructors;
	}
	public Field[] getFields() {
		return fields;
	}
	public void setFields(Field[] fields) {
		this.fields = fields;
	}
	public Method[] getMethods() {
		return methods;
	}
	public void setMethods(Method[] methods) {
		this.methods = methods;
	}

	@Override
	public String toString() {
		String classinfo = null;
		
		classinfo = "| Class : " + getName() + " | Package : " + getPackageName() + " | Interface: " + isIface() +
				" | Interfaces: " + getInterfaces().length + " | Constructors: " + getConstructors().length + 
				" | Fields: " + getFields().length + " | Methods: " + getMethods().length + " |";
		
		return classinfo;
	}
	
	
}
