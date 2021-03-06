package com.nickr.IoT.user.model;

public class Location {

	 private double latitude;
	 private double longitude;
	 
	public Location(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location() {
		// TODO Auto-generated constructor stub
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "location{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}
