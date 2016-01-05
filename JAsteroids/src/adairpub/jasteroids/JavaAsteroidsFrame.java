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

public class JavaAsteroidsFrame extends javax.swing.JFrame {
	public JavaAsteroidsFrame() {
		getContentPane().setLayout(null);
		setSize(624, 422);
		setVisible(false);
		meteorPanel1.setLayout(null);
		getContentPane().add(meteorPanel1);
		meteorPanel1.setBackground(java.awt.Color.black);
		meteorPanel1.setBounds(12, 12, 600, 400);
		meteorPanel1.startAnimation();
		SymKey aSymKey = new SymKey();
		this.addKeyListener(aSymKey);
	}

	public JavaAsteroidsFrame(String sTitle) {
		this();
		setTitle(sTitle);
	}

	public void setVisible(boolean b) {
		if (b)
			setLocation(50, 50);
		super.setVisible(b);
	}

	static public void main(String args[]) {
		(new JavaAsteroidsFrame()).setVisible(true);
	}

	public void addNotify() {
		// Record the size of the window prior to calling parents addNotify.
		Dimension size = getSize();

		super.addNotify();

		if (frameSizeAdjusted)
			return;
		frameSizeAdjusted = true;

		// Adjust size of frame according to the insets and menu bar
		Insets insets = getInsets();
		javax.swing.JMenuBar menuBar = getRootPane().getJMenuBar();
		int menuBarHeight = 0;
		if (menuBar != null)
			menuBarHeight = menuBar.getPreferredSize().height;
		setSize(insets.left + insets.right + size.width, insets.top + insets.bottom + size.height + menuBarHeight);
	}

	// Used by addNotify
	boolean frameSizeAdjusted = false;

	GamePanel meteorPanel1 = new GamePanel();

	class SymKey extends java.awt.event.KeyAdapter {
		public void keyReleased(java.awt.event.KeyEvent event) {
			Object object = event.getSource();
			if (object == JavaAsteroidsFrame.this)
				JavaMeteorFrame_keyReleased(event);
		}

		public void keyPressed(java.awt.event.KeyEvent event) {
			Object object = event.getSource();
			if (object == JavaAsteroidsFrame.this)
				JavaMeteorFrame_keyPressed(event);
		}
	}

	void JavaMeteorFrame_keyPressed(java.awt.event.KeyEvent event) {
		meteorPanel1.MeteorPanel_keyPressed(event);
	}

	void JavaMeteorFrame_keyReleased(java.awt.event.KeyEvent event) {
		meteorPanel1.MeteorPanel_keyReleased(event);
	}
}