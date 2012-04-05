/**
java  * Display.java
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
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This display panel will be used to display the game's object's and their world
 * @author ricky barrette
 */
public class Display extends JPanel {

	private static final long serialVersionUID = -9105117186423881937L;
	private AsteroidGame mGame;
	private Container mContainer;
	private String mText;

	/**
	 * Creates a new Dispay
	 * @param c
	 * @param g
	 * @author ricky barrette
	 */
	public Display(Container c, AsteroidGame g) {
		mGame = g;
		mContainer = c;
		this.setBackground(new Color(0, 0, 0));
		mContainer.add(this, BorderLayout.CENTER); 
	}

	/**
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(mText != null){
			g.setColor(Color.ORANGE);
			g.drawString(mText, (this.getWidth() / 2) - ((int) g.getFontMetrics().getStringBounds(mText, g).getWidth() / 2), this.getHeight() /2 );
		}

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
	}

	public void setDisplayText(String string) {
		mText = string;		
	}
}