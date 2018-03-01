package com.winway.android.edcollection.base.entity;

import java.io.Serializable;

public class pointGeometry extends geometry implements Serializable{
	private double[] coordinates;
	
	public pointGeometry()
	{
		super("Point");
		coordinates=new double[2];
	}
	
	public pointGeometry(double[] xy) {
		this.coordinates = xy;
	}

	public pointGeometry(double x, double y) {
		this.coordinates = new double[] { x, y };
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public double getX() {
		return coordinates[0];
	}

	public void setX(double value) {
		coordinates[0] = value;
	}

	public double getY() {
		return coordinates[1];
	}

	public void setY(double value) {
		coordinates[1] = value;
	}
}
