package com.joshjcarrier.minecontrol.ui.actions;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.Action;

public abstract class SimpleAction implements Action {
	
	public abstract void actionPerformed(ActionEvent e);
	
	private boolean isEnabled = true;
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	private HashSet<PropertyChangeListener> propertyChangeListeners = new HashSet<PropertyChangeListener>();
	
	public void setEnabled(boolean b) {
		this.isEnabled = b;
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeListeners.add(listener);		
	}
	
	public void putValue(String key, Object value) {
		this.properties.put(key, value);
	}
	
	public boolean isEnabled() {
		return this.isEnabled;
	}
	
	public Object getValue(String key) {
		return this.properties.get(key);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeListeners.add(listener);
	}
}
