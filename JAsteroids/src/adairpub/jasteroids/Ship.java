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
 * The players space ship.
 * 
 * @author Adam W Adair
 * @version 1.0
 */
public class Ship extends adairpub.graphics.Sprite {
	private int angle;
	private double thrust;
	private Point p1, p2, p3, p4;
	private Line l1, l2, l3, l4;
	private Color bgColor; // background color
	private Color shipColor; // color of the ship
	public boolean isHit; // true if ship was hit
	private boolean aftOn; // true if aft rockets are being fired
	private boolean leftBoosterOn; // true if turning right
	private boolean rightBoosterOn; // true if turing left
	private AsteroidSpriteList sl;

	// Static Variables
	private static int maxSpeed = 7;
	private static int radius = 10;
	private static double thrustRate = 0.5;
	private static int turnRate = 15;

	public Ship(Point aOrigin, Size aScreenSize, AsteroidSpriteList msl) {
		super(aScreenSize);
		origin.x = aOrigin.x;
		origin.y = aOrigin.y;
		angle = 0;
		thrust = 0;
		isHit = false;
		this.setOwner(msl);
		sl = msl;
		bgColor = Color.black;
		shipColor = Color.white;
		p1 = new Point((int) (radius * sind(angle)), (int) (radius * cosd(angle)));
		p1.translate(origin.x, origin.y);
		p2 = new Point((int) (radius * sind(angle + 130)), (int) (radius * cosd(angle + 130)));
		p2.translate(origin.x, origin.y);
		p3 = new Point((int) ((radius / 4) * sind(angle + 180)), (int) ((radius / 4) * cosd(angle + 180)));
		p3.translate(origin.x, origin.y);
		p4 = new Point((int) (radius * sind(angle + 230)), (int) (radius * cosd(angle + 230)));
		p4.translate(origin.x, origin.y);
		l1 = new Line(p1, p2);
		l2 = new Line(p2, p3);
		l3 = new Line(p3, p4);
		l4 = new Line(p4, p1);
	}

	/**
	 * Paints the ship to g This method is derived from class
	 * com.home.evilted.graphics.Sprite
	 * 
	 * @param g
	 *            is the object to draw the ship to.
	 */
	public void paint(Graphics g) {
		l1.drawLine(g);
		l2.drawLine(g);
		l3.drawLine(g);
		l4.drawLine(g);
	}

	public void update() {
		int len;
		mx += thrust * sind(angle);
		my += thrust * cosd(angle);
		len = (int) (Math.sqrt((mx * mx) + (my * my)));
		if (len > maxSpeed) {
			mx = mx / len * maxSpeed;
			my = my / len * maxSpeed;
			thrust = 0;
		}
		origin.x += mx;
		origin.y += my;
		wrap();
		updateShipLines();

		// check to see if we hit a meteor
		Asteroid m = sl.checkForCollision(origin);
		if (m != null)
			isHit = true;
		// TODO: Check to see if we were shot by a UFO
	}

	public void aftRocketsOn() {
		aftOn = true;
	}

	public void aftRocketsOff() {
		aftOn = false;
	}

	public void leftBoostersOn() {
		leftBoosterOn = true;
	}

	public void leftBoostersOff() {
		leftBoosterOn = false;
	}

	public void rotate(int adjust) {
		angle = (angle + adjust + 360) % 360;
		updateShipLines();
	}

	public void addThrust(int adjust) {
		thrust += adjust;
		if (thrust < 0)
			thrust = 0;
	}

	public void explode() {
		condemn();
		owner.addSprite(new Debris(p1, p2, screenSize));
		owner.addSprite(new Debris(p2, p3, screenSize));
		owner.addSprite(new Debris(p3, p4, screenSize));
		owner.addSprite(new Debris(p4, p1, screenSize));
	}

	public String toString() {
		return "Ship@" + hashCode();
	}

	public Shot createNewShot() {
		Size shotDir = new Size((int) (10 * sind(angle)), (int) (10 * cosd(angle)));
		Point shotOrigin = new Point((int) (radius * sind(angle)), (int) (radius * cosd(angle)));
		shotOrigin.translate(origin.x, origin.y);
		return new Shot(shotOrigin, screenSize, shotDir.addSize(new Size((int) mx, (int) my)), 20, sl,
				Shot.SHOT_FRIENDLY);
	}

	public void setBackGroundColor(Color c) {
		bgColor = c;
	}

	public void setShipColor(Color c) {
		shipColor = c;
	}

	public void repaint(Graphics g) {
		g.setColor(bgColor);
		paint(g);
		update();
		g.setColor(shipColor);
		paint(g);
	}

	private void updateShipLines() {
		p1.setLocation((int) (radius * sind(angle)), (int) (radius * cosd(angle)));

		p1.translate(origin.x, origin.y);

		p2.setLocation((int) (radius * sind(angle + 130)), (int) (radius * cosd(angle + 130)));

		p2.translate(origin.x, origin.y);
		p3.setLocation((int) ((radius / 4) * sind(angle + 180)), (int) ((radius / 4) * cosd(angle + 180)));

		p3.translate(origin.x, origin.y);

		p4.setLocation((int) (radius * sind(angle + 230)), (int) (radius * cosd(angle + 230)));

		p4.translate(origin.x, origin.y);
		l1.setLine(p1, p2);
		l2.setLine(p2, p3);
		l3.setLine(p3, p4);
		l4.setLine(p4, p1);
	}
}