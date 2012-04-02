/**
 * MovingSpaceObject.java
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

/**
 * This class will track the information required for moving objects.
 * @author ricky barrette
 */
public class MovingSpaceObject extends SpaceObject implements Moveable{
	
	protected double mAngle;
	protected double mVelocityDecay;
	protected double mRotationalSpeed;
	protected double xVelocity = 0;
	protected double yVelocity = 0;
	protected double mAcceleration;	
	protected boolean isTurningLeft = false, isTurningRight = false, isAccelerating = false, isActive;

	/**
	 * @return true if the space object is accelerating
	 * @author ricky barrette
	 */
	public boolean isAccelerating(){
		return isAccelerating;
	}
	
	/**
	 * @return true if the space object is active
	 * @author ricky barrette
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @return true if the space object is turning left
	 * @author ricky barrette
	 */
	public boolean isTurningLeft(){
		return isTurningLeft;
	}
	
	/**
	 * @return true if the space object is turning right
	 * @author ricky barrette
	 */
	public boolean isTurningRight(){
		return isTurningRight;
	}
	
	/**
	 * Called when the object's location is to be updated
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Moveable#move(int, int)
	 */
	@Override
	public void move(int scrnWidth, int scrnHeight) {
		
		if(Main.DEBUG){
			System.out.println("Move "+ scrnWidth +" x "+ scrnHeight);
			System.out.println("angle "+mAngle);
			System.out.println("xVelocity = "+xVelocity);
			System.out.println("yVelocity = "+yVelocity);
			System.out.println("yX = "+mX);
			System.out.println("yY = "+mY);
		}
		
		/*
		 * this is backwards from typical polar coordinates
		 * because positive y is downward.
		 */
		if (isTurningLeft)
			mAngle -= mRotationalSpeed; 
		/*
		 * Because of that, adding to the angle is
		 * rotating clockwise (to the right)
		 */
		if (isTurningRight)
			mAngle += mRotationalSpeed; 

		// Keep angle within bounds of 0 to 2*PI
		if (mAngle > (2 * Math.PI)) 
			mAngle -= (2 * Math.PI);
		else if (mAngle < 0)
			mAngle += (2 * Math.PI);
		
		// adds accel to velocity in direction pointed
		if (isAccelerating) {
			if(Main.DEBUG)
				System.out.println("accelerating by "+mAcceleration);
			
			// calculates components of accel and adds them to velocity
			xVelocity += mAcceleration * Math.cos(mAngle);
			yVelocity += mAcceleration * Math.sin(mAngle);
		}
		
		// move the space object by adding velocity to position
		mX += xVelocity; 
		mY += yVelocity;
		
		/*
		 *  slows ship down by percentages (velDecay
		 *  should be a decimal between 0 and 1
		 */
		xVelocity *= mVelocityDecay; 
		yVelocity *= mVelocityDecay; 
		
		/*
		 * wrap the ship around to the opposite side of the screen
		 * when it goes out of the screen's bounds
		 */
		if (mX < 0)
			mX += scrnHeight; 
		else if (mX > scrnHeight)
			mX -= scrnHeight;
		if (mY < 0)
			mY += scrnWidth;
		else if (mY > scrnWidth)
			mY -= scrnWidth;
	}
	
	/**
	 * Sets wether or not this space object is accelerating
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Moveable#setAccelerating(boolean)
	 */
	@Override
	public void setAccelerating(boolean accelerating) {
		this.isAccelerating = accelerating; // start or stop accelerating the ship
	}

	/**
	 * Sets wether or not this space object is active
	 * @param active
	 * @author ricky barrette
	 */
	public void setActive(boolean active) {
		this.isActive = active; // used when the game is paused or unpaused
	}
	
	/**
	 * Sets wether or not this space object is turning left
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Moveable#setTurningLeft(boolean)
	 */
	@Override
	public void setTurningLeft(boolean turningLeft) {
		this.isTurningLeft = turningLeft; // start or stop turning the ship
	}

	/**
	 * Sets wether or not this space object is turning right
	 * (non-Javadoc)
	 * @see com.RickBarrette.asteroids.Moveable#setTurningRight(boolean)
	 */
	@Override
	public void setTurningRight(boolean turningRight) {
		this.isTurningRight = turningRight;
	}
	
}