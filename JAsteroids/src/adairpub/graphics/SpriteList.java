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

import java.util.*;
import java.awt.*;

/**
 * SpriteList is an sprite list storage class. It also provides methods for
 * drawing the sprites in the list to a java.awt.Graphics object.
 *
 * @author Adam W Adair
 * @version 1.0
 */
public class SpriteList {
	protected Vector v;

	public SpriteList() {
		v = new Vector(1, 1);
	}

	public int count() {
		return v.size();
	}

	public void addSprite(Sprite s) {
		v.addElement(s);
		s.setOwner(this);
	}

	public void paintAll(Graphics g) {
		Sprite temp;
		int i;
		int top = v.size();
		for (i = 1; i <= top; ++i) {
			temp = (Sprite) v.elementAt(i - 1);
			temp.paint(g);
		}
	}

	public void removeSprite(int s) {
		v.removeElementAt(s);
	}

	public void removeAllSprites() {
		v.removeAllElements();
	}

	// updateAll - should be overridden to update all the sprites properly
	public void updateAll() {
		Sprite temp;
		int i;
		int top = v.size();
		for (i = 1; i <= top; ++i) {
			temp = (Sprite) v.elementAt(i - 1);
			temp.update();
		}
	}

	// repaintAll - should be overridden to update and repaint all the sprites
	// properly
	public void repaintAll(Graphics g) {
		Sprite temp;
		int i;
		int top = v.size();
		for (i = 1; i <= top; ++i) {
			temp = (Sprite) v.elementAt(i - 1);
			temp.repaint(g);
		}
	}
}