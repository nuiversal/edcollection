package com.winway.android.edcollection.base.entity;


public class polygonGeometry extends geometry {
	private double[][][][] coordinates;
	
	public polygonGeometry()
	{
		super("MultiPlygon");
		coordinates=null;
	}
	public polygonGeometry(double[][][][] coordinates)
	{
		super("MultiPlygon");
		this.coordinates = coordinates;
	}

	public double[][][][] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[][][][] coordinates) {
		this.coordinates = coordinates;
	}
}
