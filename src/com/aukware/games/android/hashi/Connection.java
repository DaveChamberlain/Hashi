package com.aukware.games.android.hashi;

import android.util.Log;

public class Connection {
	int distance = 0;
	int number = 0;
	
	Node node;
	
	public void setNode (Node node) {
		this.node = node;
	}

	public Node getNode () {
		return node;
	}
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void increment () {
		if (!node.exists) 
			Log.d("Connections", "updating non existent node");
		setNumber (getNumber() + 1);
	}
}
