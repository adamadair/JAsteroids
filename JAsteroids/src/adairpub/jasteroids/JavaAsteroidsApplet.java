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

import javax.swing.*;
import java.awt.event.*;

public class JavaAsteroidsApplet extends JApplet {
	public void init() {
		getContentPane().setLayout(null);
		setSize(600, 400);
		meteorPanel1.setLayout(null);
		getContentPane().add(meteorPanel1);
		meteorPanel1.setBackground(java.awt.Color.black);
		meteorPanel1.setBounds(0, 0, 600, 400);
		meteorPanel1.startAnimation();
	}

	GamePanel meteorPanel1 = new GamePanel();

	public JavaAsteroidsApplet() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.addKeyListener(new JavaAsteroidsApplet_this_keyAdapter(this));
	}

	void this_keyPressed(KeyEvent e) {
		meteorPanel1.MeteorPanel_keyPressed(e);
	}

	void this_keyReleased(KeyEvent e) {
		meteorPanel1.MeteorPanel_keyReleased(e);
	}
}

class JavaAsteroidsApplet_this_keyAdapter extends java.awt.event.KeyAdapter {
	JavaAsteroidsApplet adaptee;

	JavaAsteroidsApplet_this_keyAdapter(JavaAsteroidsApplet adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.this_keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		adaptee.this_keyReleased(e);
	}
}
