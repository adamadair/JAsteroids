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
import java.awt.event.*;
import javax.swing.*;

/**
 * AnimatedSpritePanel provides a container used to animate a list of Spite
 * objects.
 *
 * @author Adam W Adair
 * @version 1.0
 */
public class AnimatedSpritePanel extends JPanel implements ActionListener {
	/**
	 * The list of sprites to animate in the panel
	 */
	protected SpriteList sprites;

	/**
	 * Timer to control animation updates to the panel.
	 */
	protected Timer animationTimer;

	/**
	 * public constructor
	 */
	public AnimatedSpritePanel() {
		setSize(getPreferredSize());
		animationTimer = new Timer(50, this);
		sprites = new SpriteList();
	}

	/**
	 * Constructor that sets the list of sprites to animate
	 * 
	 * @param sl
	 *            is the SpriteList that contains the sprites to animate.
	 */
	public AnimatedSpritePanel(SpriteList sl) {
		setSize(getPreferredSize());
		animationTimer = new Timer(50, this);
		sprites = sl;
	}

	/**
	 * sets the delay between updates in milliseconds.
	 * 
	 * @param d
	 *            is the delay in milliseconds.
	 */
	public void setDelay(int d) {
		animationTimer.setDelay(d);
	}

	/**
	 * gets the delay between updates in milliseconds
	 */
	public int getDelay() {
		return animationTimer.getDelay();
	}

	/**
	 * sets the SpriteList containing the sprites to animate
	 */
	public void setSpriteList(SpriteList sl) {
		sprites = sl;
	}

	/**
	 * adds s Sprite to the animated sprite list
	 */
	public void addSprite(Sprite s) {
		sprites.addSprite(s);
	}

	public SpriteList getSpriteList() {
		return sprites;
	}

	public void startAnimation() {
		if (!animationTimer.isRunning())
			animationTimer.start();
	}

	public void stopAnimation() {
		animationTimer.stop();
	}

	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}

	public void actionPerformed(ActionEvent event) {
		nextFrame();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		sprites.repaintAll(g);
	}

	public void nextFrame() {
		repaint();
	}
}