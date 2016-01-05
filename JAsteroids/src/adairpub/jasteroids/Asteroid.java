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

public class Asteroid extends adairpub.graphics.Sprite {
	// Exported Constants
	// values used to initialize asteroids. these values are randomly
	// tweaked, so each asteroid looks different
	private static final int angle1[] = { 0, 45, 90, 135, 180, 225, 270, 315, 0, 0 };
	private static final int radius1[] = { 30, 30, 30, 30, 30, 30, 30, 30, 0, 0 };
	private static final int count1 = 8;

	// Private attributes
	private int angularMomentum, // how fast the asteroid is spinning
			currentAngle; // current angle of rotation
	private Point points[] = new Point[10];
	private Line lines[] = new Line[9];
	private int angle[] = new int[10]; // angle and radius of the points
	private int radius[] = new int[10]; // which define the asteroid

	private int count; // # of points which define the asteroid
	private int size; // 1=tiny, 2=medium, 3=large rock
	private int spawnCount; // how many smaller asteroids to create when hit
	private Color bgColor; // background color
	private Color asteroidColor; // Color of the asteroid's lines.

	// Public constructors

	public Asteroid(Point aOrigin, Size aScreenSize, Size aMomentum, int aSize, int aSpawnCount) {
		super(aScreenSize);
		origin.x = aOrigin.x;
		origin.y = aOrigin.y;
		mx = aMomentum.cx;
		my = aMomentum.cy;
		size = aSize;
		spawnCount = aSpawnCount;
		bgColor = Color.black;
		asteroidColor = Color.white;
		for (int i = 0; i < 10; i++) {
			angle[i] = ((angle1[i] + (int) (Math.random() * 21) - 10) % 360);
			radius[i] = ((radius1[i] + (int) (Math.random() * 11) - 5) / (4 - size));
			points[i] = new Point(aOrigin);
			if (i < 9)
				lines[i] = new Line();
		}
		angularMomentum = (int) (Math.random() * (8)) - 4;
		currentAngle = 0;
		count = count1;
	}

	public void paint(Graphics g) {
		// This method is derived from class Sprite
		// to do: code goes here
		int i;

		for (i = 0; i < count; i++) {
			lines[i].drawLine(g);
		}
	}

	// Public methods
	/**
	 * Update() updates the position and the rotation of the asteroid.
	 */
	public void update() {
		int i;

		origin.x += mx;
		origin.y += my;
		wrap();
		currentAngle = (currentAngle + angularMomentum + 360) % 360;
		resetBoundingRect();
		for (i = 0; i < count; i++) {
			points[i].setLocation((int) (radius[i % count] * sind(angle[i % count] + currentAngle)),
					(int) (radius[i % count] * cosd(angle[i % count] + currentAngle)));
			points[i].translate(origin.x, origin.y);
			expandBoundingRect(points[i]);
		}
		points[count] = points[0];
		for (i = 0; i < count; i++) {
			lines[i].setLine(points[i], points[i + 1]);
		}
	}

	public int GetSize() {
		return size;
	}

	public void hit() {
		int i;
		size--;
		if (size > 0) {
			for (i = 0; i < spawnCount; i++)
				owner.addSprite(new Asteroid(origin, screenSize,
						new Size((int) (Math.random() * 11) - 5, (int) (Math.random() * 10) - 5), size, size));
		}
		condemned = true;
	}

	public String toString() {
		return "Asteroid@" + hashCode();
	}

	public void repaint(Graphics g) {
		g.setColor(bgColor);
		paint(g);
		update();
		g.setColor(asteroidColor);
		paint(g);
	}
}