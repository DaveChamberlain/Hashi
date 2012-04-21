package com.aukware.games.android.hashi;

import java.util.Random;

import android.util.Log;

import com.aukware.android.games.framework.Graphics;
import com.aukware.android.games.framework.Screen;

public class Grid {
	Node[][] nodes;
	int width = 0;
	int height = 0;
	Random r = new Random();

	int connections = 0;
	int uconnections = 0;
	int maxConnections = 0;
	
	int radius = 16;
	int fontsize = 16;
	int spacing=50;
	int margin = 40;
	
	public Grid (int x, int y, Graphics g) {
		width = x;
		height = y;
		
		// 
		// create grid nodes and initialize each node
		//
		nodes = new Node[x][y];
		
		for (int i = 0; i < x; i++) {
			for (int i2 = 0; i2 < y; i2++) {
				nodes[i][i2] = new Node (i, i2);
			}
		}
		
		spacing = ((g.getWidth () - margin) / y);
		radius = (spacing - margin/2) / 2;
		fontsize = radius;
		
		connections = 0;
		uconnections = 0;

		maxConnections = height * width;
	}
	
	public int checkLength (int x, int y, int d, int l) {
		if (d == 2) { // going south
			// check to see if there is enough room
			if (x + l > height - 1)
				return 0;
			
			if (l + x > height) {
				l = (height - 1) - x;
			}
			
			// enough room, check for missing nodes
			for (int i = 1; i <= l; i++) {
				//if (nodes[x+i][y].isUsed)
				//	return i;
				if (nodes[x+i][y].exists)
					return i;
			}
			
			// got to the destination so all is good
			return l;
		}
		
		if (d == 1) { // going east
			// check to see if there is enough room
			if (y + l > width - 1)
				return 0;
			
			if (l + y > width) {
				l = (width - 1) - y;
			}
			
			// enough room, check for missing nodes
			for (int i = 1; i <= l; i++) {
				//if (nodes[x][y+i].isUsed)
				//	return i;
				if (nodes[x][y+i].exists)
					return i;
			}
			
			// got to the destination so all is good
			return l;
		}

		return 0;
	}
	public int adjustLength (int x, int y, int d, int l) {
		if (d == 0) { // going north
			// check to see if there is enough room
			if (x == 0)
				return 0;
			
			if (l > x) {
				l = x;
			}
			
			// if already know something in that direction, calculate offset and return it.
			// or don't go there again (to reduce number of duplicate paths
			if (nodes[x][y].north.node != null) {
				if (r.nextInt(3) == 1)
					return x - nodes[x][y].north.node.getX();
				else
					return 0;
			}
			
			// enough room, check for missing nodes
			for (int i = 1; i <= l; i++) {
				if (nodes[x-i][y].getConnectionCount() > 2)
					return i-1;
				if (nodes[x-i][y].isUsed)
					return i;
				if (!nodes[x-i][y].exists)
					return i-1;
			}
			
			// got to the destination so all is good
			return l;
		}
		
		if (d == 3) { // going west
			// check to see if there is enough room
			if (y == 0)
				return 0;
			
			if (l > y) {
				l = y;
			}
			
			if (nodes[x][y].west.node != null) 
				if (r.nextInt(3) == 1)
					return y - nodes[x][y].west.node.getY();
				else
					return 0;

			// enough room, check for missing nodes
			for (int i = 1; i <= l; i++) {
				if (nodes[x][y-i].getConnectionCount() > 2)
					return i-1;
				if (nodes[x][y-i].isUsed)
					return i;
				if (!nodes[x][y-i].exists)
					return i-1;
			}
			
			// got to the destination so all is good
			return l;
		}
		
		if (d == 2) { // going south
			// check to see if there is enough room
			if (x + l > height - 1)
				return 0;
			
			if (l + x > height) {
				l = (height - 1) - x;
			}
			
			if (nodes[x][y].south.node != null) 
				if (r.nextInt(3) == 1)
					return nodes[x][y].south.node.getX() - x;
				else
					return 0;

			// enough room, check for missing nodes
			for (int i = 1; i <= l; i++) {
				if (nodes[x+i][y].getConnectionCount() > 2)
					return i-1;
				if (nodes[x+i][y].isUsed)
					return i;
				if (!nodes[x+i][y].exists)
					return i-1;
			}
			
			// got to the destination so all is good
			return l;
		}
		
		if (d == 1) { // going east
			// check to see if there is enough room
			if (y + l > width - 1)
				return 0;
			
			if (l + y > width) {
				l = (width - 1) - y;
			}
			
			if (nodes[x][y].east.node != null) 
				if (r.nextInt(3) == 1)
					return nodes[x][y].east.node.getY() - y;
				else
					return 0;

			// enough room, check for missing nodes
			for (int i = 1; i <= l; i++) {
				if (nodes[x][y+i].getConnectionCount() > 2)
					return i-1;
				if (nodes[x][y+i].isUsed)
					return i;
				if (!nodes[x][y+i].exists)
					return i-1;
			}
			
			// got to the destination so all is good
			return l;
		}

		return 0;
	}
	
	public boolean linkNodes (int x, int y, int d, int l) {
		l = adjustLength (x, y, d, l);
		
		if (l == 0)
			return false;
		
		//
		// can move
		if (d == 0) {
			if (nodes[x][y].north.getNumber() < 2) {
				nodes[x][y].north.node = nodes[x - l][y];
				nodes[x - l][y].south.node = nodes[x][y];
				nodes[x - l][y].isUsed = nodes[x][y].isUsed = true;
				nodes[x][y].north.increment();
				nodes[x][y].north.node.south.increment();
				connections++;
				
				if (l > 1) {
					for (int i = 1; i < l; i++)
						nodes[x - i][y].exists = false;
				}
				return true;
			}
		}
		if (d == 1) {
			if (nodes[x][y].east.getNumber() < 2) {
				nodes[x][y].east.node = nodes[x][y + l];
				nodes[x][y + l].west.node = nodes[x][y];
				nodes[x][y + l].isUsed = nodes[x][y].isUsed = true;
				nodes[x][y].east.increment();
				nodes[x][y].east.node.west.increment();
				connections++;
				
				if (l > 1) {
					for (int i = 1; i < l; i++)
						nodes[x][y + i].exists = false;
				}
				return true;
			}
		}
		if (d == 2) {
			if (nodes[x][y].south.getNumber() < 2) {
				nodes[x][y].south.node = nodes[x + l][y];
				nodes[x + l][y].north.node = nodes[x][y];
				nodes[x + l][y].isUsed = nodes[x][y].isUsed = true;
				nodes[x][y].south.increment();
				nodes[x][y].south.node.north.increment();
				connections++;
				
				if (l > 1) {
					for (int i = 1; i < l; i++)
						nodes[x + i][y].exists = false;
				}
				return true;
			}
		}
		if (d == 3) {
			if (nodes[x][y].west.getNumber() < 2) {
				nodes[x][y].west.node = nodes[x][y - l];
				nodes[x][y - l].east.node = nodes[x][y];
				nodes[x][y - l].isUsed = nodes[x][y].isUsed = true;
				nodes[x][y].west.increment();
				nodes[x][y].west.node.east.increment();
				connections++;
				
				if (l > 1) {
					for (int i = 1; i < l; i++)
						nodes[x][y - i].exists = false;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean somethingBetween (int sx, int sy, int dx, int dy) {
		int direction;
		int len;
		
		if (sx > dx || sy > dy) {
			int temp = sx;
			sx = dx;
			dx = temp;
			temp = sy;
			sy = dy;
			dy = temp;
		}
		
		if (sx != dx) {
			len = dx - sx;
			direction = 2;
		} else {
			len = dy - sy;
			direction = 1;
		}
		
		int l = checkLength (sx, sy, direction, len);
		
		return l != len;
	}
	
	
	public void connect (int fromX, int fromY, int toX, int toY) {
		// get screen x/y and convert it to nodes
		// set the unode of the correct nodes to show the connection
		// in the drawGrid routine, if not showing the answer, show the user attempt.
		int thisX = (fromX) / spacing;
		int thisY = (fromY) / spacing;
		int thatX = (toX) / spacing;
		int thatY = (toY) / spacing;
		
		if (thisX < 0 || thisY < 0 || thatX < 0 || thatY <0)
			return;
		if (thisX >= width || thisY >= height || thatX >= width || thatY >= height)
			return;
		
		if (thisX == thatX && thisY == thatY)
			return;
		
		if (somethingBetween (thisX, thisY, thatX, thatY))
			return;
		
		if (nodes[thisX][thisY].isUsed && nodes[thatX][thatY].isUsed){ 
			if (thisX == thatX) { //have east west
				if (thisY > thatY) { // have west bound
					if (nodes[thisX][thisY].uwest.number == 2) {
						nodes[thisX][thisY].uwest.number = 0;
						nodes[thisX][thisY].uwest.node.ueast.number = 0;
						uconnections -= 2;
						nodes[thisX][thisY].uwest.node.ueast = new Connection();
						nodes[thisX][thisY].uwest = new Connection();

					} else {
						uconnections++;
						nodes[thisX][thisY].uwest.node = nodes[thatX][thatY];
						nodes[thisX][thisY].uwest.number++;
						nodes[thisX][thisY].uwest.node.ueast.node = nodes[thisX][thisY];
						nodes[thisX][thisY].uwest.node.ueast.number++;
					}
				} else { // have east bound
					if (nodes[thisX][thisY].ueast.number == 2) {
						nodes[thisX][thisY].ueast.number = 0;
						nodes[thisX][thisY].ueast.node.uwest.number = 0;
						uconnections -= 2;
						nodes[thisX][thisY].ueast.node.uwest = new Connection();
						nodes[thisX][thisY].ueast = new Connection();

					} else {
						uconnections++;
						nodes[thisX][thisY].ueast.node = nodes[thatX][thatY];
						nodes[thisX][thisY].ueast.number++;
						nodes[thisX][thisY].ueast.node.uwest.node = nodes[thisX][thisY];
						nodes[thisX][thisY].ueast.node.uwest.number++;
					}
				}
			} else if (thisY == thatY){ // have north/south
				if (thisX > thatX) { // have north bound
					if (nodes[thisX][thisY].unorth.number == 2) {
						nodes[thisX][thisY].unorth.number = 0;
						nodes[thisX][thisY].unorth.node.usouth.number = 0;
						uconnections -= 2;
						nodes[thisX][thisY].unorth.node.usouth = new Connection();
						nodes[thisX][thisY].unorth = new Connection();

					} else {
						uconnections++;
						nodes[thisX][thisY].unorth.node = nodes[thatX][thatY];
						nodes[thisX][thisY].unorth.node.usouth.node = nodes[thisX][thisY];
						nodes[thisX][thisY].unorth.node.usouth.number++;
						nodes[thisX][thisY].unorth.number++;
					}
				} else { // have south bound
					if (nodes[thisX][thisY].usouth.number == 2) {
						nodes[thisX][thisY].usouth.number = 0;
						nodes[thisX][thisY].usouth.node.unorth.number = 0;
						uconnections -= 2;
						nodes[thisX][thisY].usouth.node.unorth = new Connection();
						nodes[thisX][thisY].usouth = new Connection();

					} else {
						uconnections++;
						nodes[thisX][thisY].usouth.node = nodes[thatX][thatY];
						nodes[thisX][thisY].usouth.number++;
						nodes[thisX][thisY].usouth.node.unorth.node = nodes[thisX][thisY];
						nodes[thisX][thisY].usouth.node.unorth.number++;
					}
				}
			}
		}
	}
	
	public void clearUserConnections () {
		for (int x = 0; x < height; x++)
			for (int y = 0; y < width; y++) {
				nodes[x][y].unorth = new Connection();
				nodes[x][y].usouth = new Connection();
				nodes[x][y].ueast = new Connection();
				nodes[x][y].uwest = new Connection();
				uconnections = 0;
			}
	}
	
	public boolean isWinner () {
		for (int x = 0; x < height; x++)
			for (int y = 0; y < width; y++)
				if ((nodes[x][y].ueast.node != nodes[x][y].east.node) ||
						(nodes[x][y].usouth.node != nodes[x][y].south.node) ||
						(nodes[x][y].usouth.number != nodes[x][y].south.number) ||
						(nodes[x][y].ueast.number != nodes[x][y].ueast.number))
						return false;
		return true;
	}
	
	public int addConnection (int x, int y, int d, int l) {
		int attempts = 0;
		
		while (attempts++ < 4 && !linkNodes (x, y, d, l)) {
			d = ++d % 4;
		}
		
		if (attempts < 4)
			return d;
		else
			return -1;
	}
	
	public void createPath (int x, int y, int nestingLevel) {
		
		int length = r.nextInt(3)+1;
		
		if (connections > maxConnections)
			return;

		// have initial node.  Create a new connection if we can
		if (connections < maxConnections) {
			// pick direction (0 = north, 1 = east, 2 = south, 3 = west);
			int newConnection = r.nextInt(4);

			if ((newConnection = addConnection(x, y, newConnection, length)) != -1) {
				switch (newConnection) {
				case 0:
					createPath(nodes[x][y].north.node.getX(),
							nodes[x][y].north.node.getY(), nestingLevel + 1);
					break;
				case 1:
					createPath(nodes[x][y].east.node.getX(),
							nodes[x][y].east.node.getY(), nestingLevel + 1);
					break;
				case 2:
					createPath(nodes[x][y].south.node.getX(),
							nodes[x][y].south.node.getY(), nestingLevel + 1);
					break;
				case 3:
					createPath(nodes[x][y].west.node.getX(),
							nodes[x][y].west.node.getY(), nestingLevel + 1);
					break;
				}

				if (r.nextInt(100) <= 50)
					createPath(x, y, nestingLevel + 1);

			} 

		}
	}
	
	public void randomize () {
		int x = r.nextInt (width);
		int y = r.nextInt (height);
		
		createPath (x, y, 0);
	}
	
	public void drawGrid (Graphics g, boolean showAnswer) {
		
		int count = 0;
		//
		// NOTE: X and Y have to be flipped here.  When we think of the grid, X is the row and Y the column,
		// when plotting, the X is how far to the right and Y how far down.  This rotates everything.
		

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (nodes[x][y].isUsed) {
					g.drawCircle(margin + nodes[x][y].getY()*spacing, margin + nodes[x][y].getX()*spacing, radius, 0xFFFF0000);
					
					count += nodes[x][y].getConnectionCount();
					
					g.drawText("" + nodes[x][y].getConnectionCount(), 
							margin-(fontsize/4)+nodes[x][y].getY()*spacing, margin+(fontsize/4)+nodes[x][y].getX()*spacing, fontsize);
				}
			}
		}
		if (showAnswer) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (nodes[x][y].east.number == 1) {
						g.drawLine(radius + margin + nodes[x][y].getY()
								* spacing, margin + nodes[x][y].getX()
								* spacing,
								margin + nodes[x][y].east.node.getY() * spacing
										- radius, margin
										+ nodes[x][y].east.node.getX()
										* spacing, 0xFF000000);
					} else if (nodes[x][y].east.number == 2) {
						g.drawLine(radius + margin + nodes[x][y].getY()
								* spacing, margin - 3 + nodes[x][y].getX()
								* spacing,
								margin + nodes[x][y].east.node.getY() * spacing
										- radius, margin - 3
										+ nodes[x][y].east.node.getX()
										* spacing, 0xFF000000);
						g.drawLine(radius + margin + nodes[x][y].getY()
								* spacing, margin + 3 + nodes[x][y].getX()
								* spacing,
								margin + nodes[x][y].east.node.getY() * spacing
										- radius, margin + 3
										+ nodes[x][y].east.node.getX()
										* spacing, 0xFF000000);
					}

					if (nodes[x][y].south.number == 1) {
						g.drawLine(margin + nodes[x][y].getY() * spacing,
								radius + margin + nodes[x][y].getX() * spacing,
								margin + nodes[x][y].south.node.getY()
										* spacing, margin
										+ nodes[x][y].south.node.getX()
										* spacing - radius, 0xFF000000);
					} else if (nodes[x][y].south.number == 2) {
						g.drawLine(margin - 3 + nodes[x][y].getY() * spacing,
								radius + margin + nodes[x][y].getX() * spacing,
								margin - 3 + nodes[x][y].south.node.getY()
										* spacing, margin
										+ nodes[x][y].south.node.getX()
										* spacing - radius, 0xFF000000);
						g.drawLine(margin + 3 + nodes[x][y].getY() * spacing,
								radius + margin + nodes[x][y].getX() * spacing,
								margin + 3 + nodes[x][y].south.node.getY()
										* spacing, margin
										+ nodes[x][y].south.node.getX()
										* spacing - radius, 0xFF000000);
					}

					// g.drawCircle(40 + x*40, 40 + y*40, 10, 0xFFFF0000);
				}

			}
		} else {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (nodes[x][y].ueast.number == 1) {
						g.drawLine(radius + margin + nodes[x][y].getY()
								* spacing, margin + nodes[x][y].getX()
								* spacing,
								margin + nodes[x][y].ueast.node.getY() * spacing
										- radius, margin
										+ nodes[x][y].ueast.node.getX()
										* spacing, 0xFF000000);
					} else if (nodes[x][y].ueast.number == 2) {
						g.drawLine(radius + margin + nodes[x][y].getY()
								* spacing, margin - 3 + nodes[x][y].getX()
								* spacing,
								margin + nodes[x][y].ueast.node.getY() * spacing
										- radius, margin - 3
										+ nodes[x][y].ueast.node.getX()
										* spacing, 0xFF000000);
						g.drawLine(radius + margin + nodes[x][y].getY()
								* spacing, margin + 3 + nodes[x][y].getX()
								* spacing,
								margin + nodes[x][y].ueast.node.getY() * spacing
										- radius, margin + 3
										+ nodes[x][y].ueast.node.getX()
										* spacing, 0xFF000000);
					}

					if (nodes[x][y].usouth.number == 1) {
						g.drawLine(margin + nodes[x][y].getY() * spacing,
								radius + margin + nodes[x][y].getX() * spacing,
								margin + nodes[x][y].usouth.node.getY()
										* spacing, margin
										+ nodes[x][y].usouth.node.getX()
										* spacing - radius, 0xFF000000);
					} else if (nodes[x][y].usouth.number == 2) {
						g.drawLine(margin - 3 + nodes[x][y].getY() * spacing,
								radius + margin + nodes[x][y].getX() * spacing,
								margin - 3 + nodes[x][y].usouth.node.getY()
										* spacing, margin
										+ nodes[x][y].usouth.node.getX()
										* spacing - radius, 0xFF000000);
						g.drawLine(margin + 3 + nodes[x][y].getY() * spacing,
								radius + margin + nodes[x][y].getX() * spacing,
								margin + 3 + nodes[x][y].usouth.node.getY()
										* spacing, margin
										+ nodes[x][y].usouth.node.getX()
										* spacing - radius, 0xFF000000);
					}

					// g.drawCircle(40 + x*40, 40 + y*40, 10, 0xFFFF0000);
				}

			}
		}
		
		connections = count / 2;
		if (showAnswer)
			g.drawText("Total connections: " + count/2, 5, 5*g.getHeight()/6, 18, 0xFF000000);
		else
			g.drawText("Connections: " + uconnections + "/" + count/2, 5, 5*g.getHeight()/6, 18, 0xFF000000);
	}
}
