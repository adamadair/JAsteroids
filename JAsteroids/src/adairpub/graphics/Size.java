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

/**
 * This class provides a data structure to describe the size of a 2D plane.
 * 
 * @author Adam W Adair
 * @version 1.0
 */
public class Size {
	/**
	 * change of X
	 */
	public int cx;
	/**
	 * change of y
	 */
	public int cy;

	/**
	 * default Size constructor, no size
	 */
	public Size() {
		cx = 0;
		cy = 0;
	}

	/**
	 * constuctor that sets the plane size to dx,dy
	 */
	public Size(int dx, int dy) {
		cx = dx;
		cy = dy;
	}

	/**
	 * adds Size ds a Size to object and returns the result.
	 * 
	 * @return the new Size after the size is added.
	 */
	public Size addSize(Size ds) {
		Size aSize = new Size();
		aSize.cx = this.cx + ds.cx;
		aSize.cy = this.cy + ds.cy;
		return aSize;
	}

	/**
	 * adds Size ds a Size to object and returns the result.
	 * 
	 * @return the new Size after the size is added.
	 */
	public void add(Size ds) {
		cx += ds.cx;
		cy += ds.cy;
	}

	/**
	 * sets this size attributes to ds attributes
	 * 
	 * @param ds
	 *            is the new size
	 */
	public void setSize(Size ds) {
		cx = ds.cx;
		cy = ds.cy;
	}

	/**
	 * Sets the attributes of size.
	 * 
	 * @param sx
	 *            is the new cx value
	 * @param sy
	 *            is the new cy value
	 */
	public void setSize(int sx, int sy) {
		cx = sx;
		cy = sy;
	}
}