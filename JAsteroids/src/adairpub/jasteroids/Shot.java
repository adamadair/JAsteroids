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
 * a bullet (5 pixels in a cross pattern)
 * 
 * @author Adam W Adair
 * @version 1.0
 */
public class Shot extends adairpub.graphics.Sprite {
	// Private Attributes
	public static final int SHOT_FRIENDLY = 0;
	public static final int SHOT_UFO = 1;
	private int timeToDie;
	private Color bgColor; // background color
	private Color shotColor; // Shot Color
	private Point p1;
	private Point p2;
	private Point p3;
	private Point p4;
	private int shotType;
	private AsteroidSpriteList sl;

	// Public Constructors
	public Shot(Point aOrigin, Size aScreenSize, Size aMomentum, int aTimeToDie, AsteroidSpriteList msl, int type) {
		super(aScreenSize);
		shotType = type;
		origin.x = aOrigin.x;
		origin.y = aOrigin.y;
		mx = aMomentum.cx;
		my = aMomentum.cy;
		timeToDie = aTimeToDie;
		bgColor = Color.black;
		shotColor = Color.white;
		this.setOwner(msl);
		sl = msl;
		p1 = new Point(1, 0);
		p2 = new Point(0, 1);
		p3 = new Point(-1, 0);
		p4 = new Point(0, -1);
	}

	// Implemented abstact Methods
	public void paint(Graphics g) {
		// This method is derived from class com.home.evilted.graphics.Sprite
		if (timeToDie > 0) {
			drawPoint(p2, g);
			drawPoint(p2, g);
			drawPoint(p3, g);
			drawPoint(p4, g);
			drawPoint(origin, g);
		}
	}

	// public methods
	public void update() {
		// This methods overides the Sprite update method

		if (timeToDie > 0) {
			origin.translate((int) mx, (int) my);
			timeToDie--;
			if (timeToDie == 0) // shots die after a fixed amount of time
				condemned = true;
		}
		wrap(); // if the shot is off the screen, wrap to other side
		p1.translate(origin.x, origin.y);
		p2.translate(origin.x, origin.y);
		p3.translate(origin.x, origin.y);
		p4.translate(origin.x, origin.y);

		// check against all meteors in the list, to see if any are
		// colliding with ourself

		Sprite spt = sl.checkForHit(origin);

		if (spt != null) {
			if (spt.toString().startsWith("Asteroid")) {
				Asteroid meteor = (Asteroid) spt;
				meteor.hit();
				condemned = true;
				int size = meteor.GetSize();
				switch (size) {
				case 0:
					sl.addScore(500);
				case 1:
					sl.addScore(50);
				case 2:
					sl.addScore(5);
				}
			}
		}
	}

	private void drawPoint(Point p, Graphics g) {
		g.drawLine(p.x, p.y, p.x, p.y);
	}

	public void repaint(Graphics g) {
		g.setColor(bgColor);
		paint(g);
		update();
		g.setColor(shotColor);
		paint(g);
	}
}