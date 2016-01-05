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
package adairpub.graphics;

import java.awt.*;

/**
 * a class that provides a data structure to represent a 2D rectangle
 */
public class Rect {
	public int left;
	public int right;
	public int top;
	public int bottom;

	public Rect() {
		left = 0;
		right = 0;
		top = 0;
		bottom = 0;
	}

	public Rect(int l, int r, int t, int b) {
		left = l;
		right = r;
		top = t;
		bottom = b;
	}

	public Rect(Point x1, Point x2) {
		left = x1.x;
		top = x1.y;
		right = x2.x;
		bottom = x2.y;
	}

	public boolean isInRect(Point p) {
		if ((p.x >= left) && (p.x <= right) && (p.y >= top) && (p.y <= bottom))
			return true;
		else
			return false;
	}

	public void normalize() {
		int temp;
		if (left > right) {
			temp = right;
			right = left;
			left = temp;
		}
		if (top > bottom) {
			temp = bottom;
			bottom = top;
			top = temp;
		}
	}

	public void drawRect(Graphics g) {
		g.drawRect(left, top, (right - left), (bottom - top));
	}
}