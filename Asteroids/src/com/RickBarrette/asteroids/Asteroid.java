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
 * @author ricky barrette
 */
public class Asteroid extends MovingSpaceObject implements Collider, Drawable {

	private final int mNumberSplit;
	private final int mHitsLeft;
	private final int mRadius;
	private final double mMinVelocity;
	private final double mMaxVelocity;
	private AsteroidGame mGame;

	/**
	 * Creates a new Asteroid
	 * @param x
	 * @param y
	 * @param xVelocity
	 * @param yVelocity
	 * @param numberSplit number of smaller asteroids to create after being blown up
	 * @param hitsLeft number of hits left
	 * @author ricky barrette
	 */
	public Asteroid(double x, double y, double minVelocity, double maxVelocity, int radius, int numberSplit, int hitsLeft, AsteroidGame game) {
		mGame = game;
		mColor = Color.GRAY;
		mX = x;
		mY = y;
		mMinVelocity = minVelocity;
		mMaxVelocity = maxVelocity;
		double vel = minVelocity + Math.random() * (maxVelocity - minVelocity);
		mAngle = 2 * Math.PI * Math.random(); // random direction
		mXVelocity = vel * Math.cos(mAngle);
		mYVelocity = vel * Math.sin(mAngle);

		mNumberSplit = numberSplit;
		mHitsLeft = hitsLeft;
		mRadius = radius;
	}
	
	/**
	 * Called when a collision check needs to be made.
	 * Only checks for ship and shots
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Collider#checkForCollision(java.lang.Object)
	 */
	@Override
	public boolean checkForCollision(Object o) {
		
		if(o instanceof Asteroid){
			//TODO inverse directions of both asteroids 
		}
		
		if(o instanceof Ship) {
			return shipCollision((Ship) o);
		}

		if(o instanceof Shot) {
			return shotCollision((Shot) o);
		}
		
		return false;
	}
	
	/**
	 * Creates a smaller asteroid
	 * @param minVelocity
	 * @param maxVelocity
	 * @return new smaller asteroid
	 * @author ricky barrette
	 */
	public Asteroid createSplitAsteroid(double minVelocity, double maxVelocity){
		/*
		 * Dividing the radius by sqrt(numSplit) makes the
		 * sum of the areas taken up by the smaller asteroids equal to
		 * the area of this asteroid. Each smaller asteroid has one
		 * less hit left before being completely destroyed.
		 */
		return new Asteroid(mX,mY, minVelocity, maxVelocity, (int) (mRadius/Math.sqrt(mNumberSplit)), mNumberSplit, mHitsLeft-1, mGame);
	}

	/**
	 * Called when the Asteroid needs to be drawn
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Drawable#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(mColor);
		g.fillOval((int) (mX - mRadius + .5), (int) (mY - mRadius + .5), (int) (2 * mRadius), (int) (2 * mRadius));
	}

	/**
	 * @return the asteroid's radius
	 * @author ricky barrette
	 */
	public int getRadius() {
		return mRadius;
	}

	/**
	 * Checks for a collision with the ship 
	 * @param ship
	 * @return true if there is a collision
	 * @author ricky barrette
	 */
	public boolean shipCollision(Ship ship) {
		/*
		 *  Use the distance formula to check if the ship is touching this
		 * asteroid: Distance^2 = (x1-x2)^2 + (y1-y2)^2 ("^" denotes
		 * exponents). If the sum of the radii is greater than the
		 * distance between the center of the ship and asteroid, they are
		 * touching.
		 * if (shipRadius + asteroidRadius)^2 > (x1-x2)^2 + (y1-y2)^2,
		 * then they have collided.
		 * It does not check for collisions if the ship is not active
		 * (the player is waiting to start a new life or the game is paused).
		 */
		if ( Math.pow(mRadius + ship.getRadius(), 2) > Math.pow(ship.getX() - mX, 2) + Math.pow(ship.getY() - mY, 2)){
			return true;
		}
		return false;
	}

	/**
	 * Checks for a collision with s shot
	 * @param shot
	 * @return true if there is a collision
	 * @author ricky barrette
	 */
	public boolean shotCollision(Shot shot) {
		if( Math.pow(mRadius, 2) > Math.pow(shot.getX() - mX, 2) + Math.pow(shot.getY() - mY, 2)){
			/**
			 * remove the asteroid and the shot
			 */
			mGame.removeElement(this);
			mGame.removeElement(shot);

			/*
			 * if there is a collsion, and there are hits left,
			 * create new astroids, and remove self
			 */
			if(mHitsLeft > 0)
				for(int i = 0; i < mNumberSplit; i++)
					mGame.addElement(createSplitAsteroid(mMinVelocity, mMaxVelocity));
			
			return true;
		}
		return false;
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