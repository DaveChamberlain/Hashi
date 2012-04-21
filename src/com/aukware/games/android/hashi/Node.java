package com.aukware.games.android.hashi;

public class Node {
	int locationX = -1;
	int locationY = -1;
	
	boolean isUsed = false;
	boolean exists = true;
	Connection north, south, east, west;
	Connection unorth, usouth, ueast, uwest;
	
	public Node (int x, int y) {
		locationX = x;
		locationY = y;
		
		north = new Connection();
		south = new Connection();
		east = new Connection();
		west = new Connection();
		
		unorth = new Connection();
		usouth = new Connection();
		ueast = new Connection();
		uwest = new Connection();

	}
	
	public int getX () {
		return locationX;
	}
	
	public int getY () {
		return locationY;
	}
	
	public int getConnectionCount () {
		return north.number + south.number + east.number + west.number;
	}

}
