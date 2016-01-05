/*
 *  JAsteroids - an Asteroids clone in Java.
 *  Copyright (C) 2001, Adam Adair
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  ---
 *
 *  See the "COPYRIGHT", "COPYING" and "README" files for all the
 *  related information and legal notices. It is expected that any future
 *  projects/authors will amend these files as needed.
 */
package adairpub.jasteroids;

import java.awt.*;

import adairpub.graphics.*;

/**
 * Debris -- a floating line. it floats about for a few seconds and disappears.
 * the player/enemy ship is turned into these when it is destroyed.
 */
public class Debris extends adairpub.graphics.Sprite {
	private Point p1, p2;
	private Line l1;
	private int timeToDie;
	private Color bgColor; // Background color
	private Color debrisColor; // Color of the debris

	// Public constructor
	public Debris(Point aP1, Point aP2, Size aScreenSize) {
		super(aScreenSize);
		p1 = new Point(aP1);
		p2 = new Point(aP2);
		l1 = new Line(p1, p2);
		origin = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
		bgColor = Color.black;
		debrisColor = Color.white;
		mx = (int) (Math.random() * 10) - 5;
		my = (int) (Math.random() * 10) - 5;		
		timeToDie = 20 + (int) (Math.random() * 5);
	}

	public void paint(Graphics g) {
		// This method is derived from class com.home.evilted.graphics.Sprite
		// to do: code goes here
		l1.drawLine(g);
	}

	public String toString() {
		return "Debris@" + hashCode();
	}

	public void update() {
		if (timeToDie > 0) {
			p1.translate((int) mx, (int) my);
			p2.translate((int) mx, (int) my);
			l1.setLine(p1, p2);
			timeToDie--;
		} else
			condemn();
	}

	public void repaint(Graphics g) {
		g.setColor(bgColor);
		paint(g);
		update();
		g.setColor(debrisColor);
		paint(g);
	}
}
