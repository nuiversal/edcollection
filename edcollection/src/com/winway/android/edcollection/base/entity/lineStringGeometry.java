package com.winway.android.edcollection.base.entity;


public class lineStringGeometry extends geometry {
	private double[][] coordinates;
	
	public lineStringGeometry()
	{
		super("LineString");
		coordinates=null;
	}
	public lineStringGeometry(double[][] coordinates)
	{
		super("LineString");
		this.coordinates = coordinates;
	}

	public double[][] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[][] coordinates) {
		this.coordinates = coordinates;
	}
}
