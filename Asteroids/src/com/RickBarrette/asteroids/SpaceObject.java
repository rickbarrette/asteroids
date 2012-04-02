/**
 * SpaceObject.java
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

/**
 * This class will be used for keeping tack of the space objects within the game.
 * More accuractly the object's location and color.
 * @author ricky barrette
 */
public class SpaceObject {

	protected Color color;
	protected double mX;
	protected double mY;

	/**
	 * @return the color of this space object
	 * @author ricky barrette
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @return the space object's x location
	 * @author ricky barrette
	 */
	public double getX(){
		return mX;
	}

	/**
	 * @return the space object's y location
	 * @author ricky barrette
	 */
	public double getY(){
		return mY;
	}
	
	/**
	 * Sets the color of this space object 
	 * @param c
	 * @author ricky barrette
	 */
	public void setColor(Color c) {
		this.color = c;
	}
	
	/**
	 * Sets the location of this space object
	 * @param x1
	 * @param y1
	 * @author ricky barrette
	 */
	public void setLocation(int x, int y) {
		mX = x;
		mY = y;
	}
}