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
 * A Sprite that can display text.
 */
public class Message extends adairpub.graphics.Sprite {
	private String text;
	private Color bgColor;
	private Color fgColor;
	private Font font;

	public Message(Point aOrigin, Size aScreenSize, String msg) {
		super(aScreenSize);
		origin.x = aOrigin.x;
		origin.y = aOrigin.y;
		text = msg;
		font = new Font("SansSerif", Font.BOLD, 12);
		bgColor = Color.black;
		fgColor = Color.white;
	}

	public String toString() {
		return "Message@" + hashCode();
	}

	public void setText(String msg) {
		text = msg;
	}

	public void update() {
		origin.x += mx;
		origin.y += my;
		wrap();
	}

	public void paint(Graphics g) {
		g.setFont(font);
		g.drawString(text, origin.x, origin.y);
	}

	public void setFont(Font f) {
		font = f;
	}

	public void setBgColor(Color c) {
		bgColor = c;
	}

	public void setFgColoR(Color c) {
		fgColor = c;
	}

	public void repaint(Graphics g) {
		g.setColor(bgColor);
		paint(g);
		update();
		g.setColor(fgColor);
		paint(g);
	}
}