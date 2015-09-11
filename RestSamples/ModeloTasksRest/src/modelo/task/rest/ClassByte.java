package modelo.task.rest;

import java.io.Serializable;

public class ClassByte implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7940613446749322608L;
	private String name;
	private byte[] bytes;

	public ClassByte(String name_, byte[] bytes_){
		name  = name_;
		bytes = bytes_;
	}

	public String getName(){
		return name;
	}

	public byte[] getBytes(){
		return bytes;
	}

}
