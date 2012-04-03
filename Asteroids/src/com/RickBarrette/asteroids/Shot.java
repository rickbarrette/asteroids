/**
 * Shot.java
 * @date Apr 2, 2012
 * @author ricky barrette
 * @author Twenty Codes, LLC
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

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class will bt the shots fired bt the ship. Their job is to destroy the asteroids
 * @author ricky barrette
 */
public class Shot extends MovingSpaceObject implements Drawable {
	
	public static final int TOTAL_DRAWS = 50;
	public static final int SPEED = 10;
	private int mCount = 0;
	private AsteroidGame mGame;;

	/**
	 * Creates a new shot
	 * @author ricky barrette
	 * @param mGame 
	 */
	public Shot(double x, double y, double angle, double shipXVel, double shipYVel, AsteroidGame game) {
		mX = x;
		mY = y;
		mAngle = angle;
		mAcceleration = SPEED;
		mColor = Color.WHITE;
		mGame = game;
		mXVelocity = SPEED*Math.cos(angle)+shipXVel;
		mYVelocity = SPEED*Math.sin(angle)+shipYVel;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(mColor);

		g.fillOval((int)(mX-.5), (int)(mY-.5), 3, 3);

		/*
		 * only stick around for x draws
		 */
		if(++mCount > TOTAL_DRAWS)
			mGame.removeElement(this);
	}

}
