package com.joshjcarrier.minecontrol.services.storage;

public interface IStorage 
{
	public abstract void commit();

	public abstract void load();

	public abstract String read(String section, String name);

	public abstract boolean readBoolean(String section, String name);

	public abstract int readInt(String section, String name, int defaultValue);

	public abstract void write(String section, String name, String value);

	public abstract void writeBoolean(String section, String name, boolean value);

	public abstract void writeInt(String section, String name, int value);

}