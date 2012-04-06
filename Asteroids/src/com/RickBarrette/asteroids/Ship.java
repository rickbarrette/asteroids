/**
 * Ship.java
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

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * This class will be the user's ship. I will be used to destroy the asteroids, with it's laser! Pew Pew Pew!!!
 * @author ricky barrette
 */
public class Ship extends MovingSpaceObject {

	private final int[] mOrigXpoints = { 14, -10, -6, -10 };
	private final int[] mOrigYpoints = { 0, -8, 0, 8 };
	private final int[] mOrigFlameXpoints = { -6, -23, -6 };
	private final int[] mOrigFlameYpoints = { -3, 0, 3 };
	private final int mShotDelay;
	private int[] mXpoints = new int[4], mYpoints = new int[4], mFlameXpoints = new int[3], mFlameYpoints = new int[3];
	private int mShotDelayLeft;
	private final AsteroidGame mGame;

	/**
	 * radius of circle used to approximate the ship
	 */
	private final int mRadius = 6;
	
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
	 * @param game 
	 */
	public Ship(final double x, final double y, final double angle, final double acceleration, final double velocityDecay, final double rotationalSpeed, final int shotDelay, final AsteroidGame game) {
		this.mX = x;
		this.mY = y;
		this.mAngle = angle;
		this.mAcceleration = acceleration;
		this.mVelocityDecay = velocityDecay;
		this.mRotationalSpeed = rotationalSpeed;
		this.mColor = Color.CYAN;
		mGame = game;

		// start off paused
		this.isActive = false;

		// # of frames between shots
		this.mShotDelay = shotDelay;

		// ready to shoot
		this.mShotDelayLeft = 0;
	}

	/**
	 * @return true if the ship can shoot
	 * @author ricky barrette
	 */
	public boolean canShoot() {
		/*
		 * check if the shot delay has been satifiyed
		 */
		if (mShotDelayLeft > 0)
			return false;
		else
			return true;
	}

	/**
	 * Called by the Display panel when it needs to draw the ship
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Drawable#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(final Graphics g) {
		
		if(Main.DEBUG)
			System.out.println("draw()"+ mX + ", "+ mY);
		
		/*
		 * rotate the points, translate them to the ship's location (by
		 * adding x and y), then round them by adding .5 and casting them
		 * as integers (which truncates any decimal place)
		 */
		
		/*
		 * draw the ship's flame, if accelerating
		 */
		if (isAccelerating && isActive) {
			for (int i = 0; i < 3; i++) {
				mFlameXpoints[i] = (int) (mOrigFlameXpoints[i] * Math.cos(mAngle) - mOrigFlameYpoints[i] * Math.sin(mAngle) + mX + .5);
				mFlameYpoints[i] = (int) (mOrigFlameXpoints[i] * Math.sin(mAngle) + mOrigFlameYpoints[i] * Math.cos(mAngle) + mY + .5);
			}
			
			/*
			 * draw the flame
			 */
			g.setColor(Color.red);
			g.fillPolygon(mFlameXpoints, mFlameYpoints, 3);
		}
		
		/*
		 * calculate the polygon for the ship, then draw it
		 */
		for (int i = 0; i < 4; i++) {
			this.mXpoints[i] = (int) (this.mOrigXpoints[i] * Math.cos(mAngle) - this.mOrigYpoints[i] * Math.sin(mAngle) + mX + .5);
			this.mYpoints[i] = (int) (this.mOrigXpoints[i] * Math.sin(mAngle) + this.mOrigYpoints[i] * Math.cos(mAngle) + mY + .5);
		}

		/*
		 * draw the ship dark gray if the game is paused
		 */
		if (isActive)
			g.setColor(mColor);
		else
			g.setColor(Color.darkGray);
		
		g.fillPolygon(mXpoints, mYpoints, 4);
	}
	
	/**
	 * @return radius of circle that approximates the ship
	 * @author ricky barrette
	 */
	public double getRadius() {
		return mRadius;
	}

	/**
	 * Hyperjumps the sip
	 * @param ship
	 * @author ricky barrette
	 */
	public void hyperJump() {
//		boolean isSafe = true;
		Random gen = new Random();
//		do{
			System.out.println("hyper jumping");
			setLocation(gen.nextInt(mGame.getGameFrame().getDisplayWidth()), gen.nextInt(mGame.getGameFrame().getDispalyHeight()));
//			for(int i = 0; i < mWorld.size(); i++)
//				if(mWorld.get(i) instanceof Collider)
//					if(((Collider) mWorld.get(i)).checkForCollision(ship))
//						isSafe = false;
//		} while (!isSafe);
			mGame.getGameFrame().repaintDispaly();		
	}

	/**
	 * Called when ...
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.MovingSpaceObject#move(int, int)
	 */
	@Override
	public void move(final int scrnWidth, final int scrnHeight) {
		/*
		 * move() is called every frame that the game
		 * is run, so this ticks down the shot delay
		 */
		if (mShotDelayLeft > 0)
			mShotDelayLeft--; 
		
		super.move(scrnWidth, scrnHeight);
	}
	
	/**
	 * Fires a shot from the ship
	 * @author ricky barrette
	 */
	public void shoot() {
		if(canShoot()) {
			mGame.addElement(new Shot(mX, mY, mAngle, mXVelocity, mYVelocity, mGame));
			mShotDelayLeft += mShotDelay;
		}
	}
}