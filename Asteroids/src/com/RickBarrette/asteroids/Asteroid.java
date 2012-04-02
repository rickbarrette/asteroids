/**
 * Asteroid.java
 * @date Apr 1, 2012
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
 * This class will be used to make astroids, to destroy the ship!
 * 
 * @author ricky barrette
 */
public class Asteroid extends MovingSpaceObject implements Drawable {

	private final int mNumberSplit;
	private final int mHitsLeft;
	private final int mRadius;
	private AsteroidGame mGame;

	/**
	 * Creates a new Asteroid
	 * 
	 * @param x
	 * @param y
	 * @param xVelocity
	 * @param yVelocity
	 * @param numberSplit
	 *            number of smaller asteroids to create after being blown up
	 * @param hitsLeft
	 *            number of hits left
	 * @author ricky barrette
	 */
	public Asteroid(double x, double y, double minVelocity, double maxVelocity,
			int radius, int numberSplit, int hitsLeft, AsteroidGame game) {
		mGame = game;
		mColor = Color.GRAY;
		mX = x;
		mY = y;
		double vel = minVelocity + Math.random() * (maxVelocity - minVelocity);
		mAngle = 2 * Math.PI * Math.random(); // random direction
		mXVelocity = vel * Math.cos(mAngle);
		mYVelocity = vel * Math.sin(mAngle);

		mNumberSplit = numberSplit;
		mHitsLeft = hitsLeft;
		mRadius = radius;
		mVelocityDecay = 1;
		isActive = true;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(mColor);
		g.fillOval((int) (mX - mRadius + .5), (int) (mY - mRadius + .5),
				(int) (2 * mRadius), (int) (2 * mRadius));

	}

	/**
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.MovingSpaceObject#wrapSpace(int, int)
	 */
	@Override
	public void wrapSpace(int scrnHeight, int scrnWidth) {
		if (mX < 0 - mRadius)
			mX += scrnHeight + 2 * mRadius;
		else if (mX > scrnHeight + mRadius)
			mX -= scrnHeight + 2 * mRadius;
		
		if (mY < 0 - mRadius)
			mY += scrnWidth + 2 * mRadius;
		else if (mY > scrnWidth + mRadius)
			mY -= scrnWidth + 2 * mRadius;
	}
}