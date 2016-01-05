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

public class GamePanel extends adairpub.graphics.AnimatedSpritePanel {
	private AsteroidSpriteList sl;

	public GamePanel() {
		setLayout(null);
		setBackground(java.awt.Color.black);
		setSize(600, 400);
		SymKey aSymKey = new SymKey();
		this.addKeyListener(aSymKey);
		sl = new AsteroidSpriteList(600, 400);
		sprites = sl;
	}

	public void MeteorPanel_keyPressed(java.awt.event.KeyEvent event) {
		switch (event.getKeyCode()) {
		case java.awt.event.KeyEvent.VK_LEFT:
			sl.rotateLeft = true;
			break;
		case java.awt.event.KeyEvent.VK_UP:
			sl.thrust = true;
			break;
		case java.awt.event.KeyEvent.VK_RIGHT:
			sl.rotateRight = true;
			break;
		case java.awt.event.KeyEvent.VK_SPACE:
			sl.fire = true;
			break;
		}
	}

	public void MeteorPanel_keyReleased(java.awt.event.KeyEvent event) {
		switch (event.getKeyCode()) {
		case java.awt.event.KeyEvent.VK_LEFT:
			sl.rotateLeft = false;
			break;
		case java.awt.event.KeyEvent.VK_UP:
			sl.thrust = false;
			break;
		case java.awt.event.KeyEvent.VK_RIGHT:
			sl.rotateRight = false;
			break;
		case java.awt.event.KeyEvent.VK_SPACE:
			sl.fire = false;
			break;
		}
	}

	public void nextFrame() {
		sl.action();
		repaint();
	}

	class SymKey extends java.awt.event.KeyAdapter {
		public void keyReleased(java.awt.event.KeyEvent event) {
			Object object = event.getSource();
			if (object == GamePanel.this)
				MeteorPanel_keyReleased(event);
		}

		public void keyPressed(java.awt.event.KeyEvent event) {
			Object object = event.getSource();
			if (object == GamePanel.this)
				MeteorPanel_keyPressed(event);
		}
	}
}