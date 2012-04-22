/**
 * Display.java
 * @date Mar 31, 2012
 * @author ricky barrette
 * 
 * Copyright 2012 Richard Barrette 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License
 */
package com.RickBarrette.asteroids;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * This display panel will be used to display the game's object's and their world
 * @author ricky barrette
 */
public class Display extends JPanel {

	private static final long serialVersionUID = -9105117186423881937L;
	private final AsteroidGameThread mGame;
	private final Font mFont;
	private String mText;

	/**
	 * Creates a new Dispay
	 * @param c
	 * @param g
	 * @author ricky barrette
	 */
	public Display(final Container c, final AsteroidGameThread g) {
		mGame = g;
		this.setBackground(new Color(0, 0, 0));
		c.add(this, BorderLayout.CENTER);
		int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
	    int fontSize = (int)Math.round(14.0 * screenRes / 72.0);
	    mFont = new Font("Arial", Font.PLAIN, fontSize);
	}

	/**
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);

		/*
		 * Move & Draw the world's objects
		 */
		Object item;
		MovingSpaceObject mso;
		for (int i = 0; i < mGame.getWorld().size(); i++) {
			item = mGame.getWorld().get(i);
			
			if (item instanceof MovingSpaceObject){
				mso = (MovingSpaceObject) item;
				if(mso.isActive)
					mso.move(getHeight(), getWidth());
			}
			
			if(item instanceof Drawable)
				((Drawable) item).draw(g);
		}
		
		/*
		 * draw the text to be displayed
		 */
		if(mText != null){
			g.setColor(Color.ORANGE);
			g.setFont(mFont);
			g.drawString(mText, (this.getWidth() / 2) - ((int) g.getFontMetrics().getStringBounds(mText, g).getWidth() / 2), this.getHeight() /2 );
		}
	}

	/**
	 * Sets the text to be displayed in the center of the game's display
	 * @param string
	 * @author ricky barrette
	 */
	public void setDisplayText(final String string) {
		mText = string;		
	}
}