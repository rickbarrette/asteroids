/**
 * Ship.java
 * @date Mar 31, 2012
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
 * This class will be the user's ship. I will be used to destroy the asteroids, with it's laser! Pew Pew Pew!!!
 * @author ricky barrette
 */
public class Ship extends MovingSpaceObject implements Drawable {

	private final int[] mOrigXpoints = { 14, -10, -6, -10 };
	private final int[] mOrigYpoints = { 0, -8, 0, 8 };
	private final int[] mOrigFlameXpoints = { -6, -23, -6 };
	private final int[] mOrigFlameYpoints = { -3, 0, 3 };
	
	/*
	 * store the current locations of the points used to draw the ship and its
	 * flame
	 */
	int[] mXpoints = new int[4], mYpoints = new int[4], mFlameXpoints = new int[3], mFlameYpoints = new int[3];
	int shotDelay, shotDelayLeft; // used to determine the rate of firing

	/*
	 * radius of circle used to approximate the ship
	 */
	private final int radius = 6;
	
	/**
	 * Creates a new ship
	 * @param x location of the ship
	 * @param y location of the ship
	 * @param angle of the ship
	 * @param acceleration of the ship
	 * @param velocityDecay of the ship
	 * @param rotationalSpeed of the ship
	 * @param shotDelay of the ship
	 * @author ricky barrette
	 */
	public Ship(double x, double y, double angle, double acceleration, double velocityDecay, double rotationalSpeed, int shotDelay) {
		// this.x refers to the Ship's x, x refers to the x parameter
		this.mX = x;
		this.mY = y;
		this.mAngle = angle;
		this.mAcceleration = acceleration;
		this.mVelocityDecay = velocityDecay;
		this.mRotationalSpeed = rotationalSpeed;
		this.mColor = Color.CYAN;

		// start off paused
		this.isActive = false;

		// # of frames between shots
		this.shotDelay = shotDelay;

		// ready to shoot
		this.shotDelayLeft = 0;
	}

	/**
	 * @return true if the ship can shoot
	 * @author ricky barrette
	 */
	public boolean canShoot() {
		if (shotDelayLeft > 0) // checks to see if the ship is ready to
			return false;
		// shoot again yet or if it needs to wait longer
		else
			return true;
	}

	/**
	 * Called by the Display panel when it needs to draw the ship
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Drawable#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		
		if(Main.DEBUG)
			System.out.println("draw()"+ mX + ", "+ mY);
		
		// rotate the points, translate them to the ship's location (by
		// adding x and y), then round them by adding .5 and casting them
		// as integers (which truncates any decimal place)
		if (isAccelerating && isActive) { // draw flame if accelerating
			for (int i = 0; i < 3; i++) {
				mFlameXpoints[i] = (int) (mOrigFlameXpoints[i] * Math.cos(mAngle) - mOrigFlameYpoints[i] * Math.sin(mAngle) + mX + .5);
				mFlameYpoints[i] = (int) (mOrigFlameXpoints[i] * Math.sin(mAngle) + mOrigFlameYpoints[i] * Math.cos(mAngle) + mY + .5);
			}
			
			g.setColor(Color.red); // set color of flame
			g.fillPolygon(mFlameXpoints, mFlameYpoints, 3);
		}
		// calculate the polygon for the ship, then draw it
		for (int i = 0; i < 4; i++) {
			this.mXpoints[i] = (int) (this.mOrigXpoints[i] * Math.cos(mAngle) - this.mOrigYpoints[i] * Math.sin(mAngle) + mX + .5);
			this.mYpoints[i] = (int) (this.mOrigXpoints[i] * Math.sin(mAngle) + this.mOrigYpoints[i] * Math.cos(mAngle) + mY + .5);
		}

		if (isActive)
			g.setColor(mColor);
		else
			// draw the ship dark gray if the game is paused
			g.setColor(Color.darkGray);
		
		g.fillPolygon(mXpoints, mYpoints, 4); // 4 is the number of points
	}
	
	/**
	 * @return the ship's current angle
	 * @author ricky barrette
	 */
	public double getAngle() {
		return mAngle;
	}

	/**
	 * @return radius of circle that approximates the ship
	 * @author ricky barrette
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Called when ...
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.MovingSpaceObject#move(int, int)
	 */
	@Override
	public void move(int scrnWidth, int scrnHeight) {
		/*
		 * move() is called every frame that the game
		 * is run, so this ticks down the shot delay
		 */
		if (shotDelayLeft > 0)
			shotDelayLeft--; 
		
		super.move(scrnWidth, scrnHeight);
	}

	public double getXVelocity() {
		return this.mXVelocity;
	}

	public double getYVelocity() {
		return this.mYVelocity;
	}
}