/**
 * Moveable.java
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

/**
 * This interface will be used to move objects with in the game
 * @author ricky barrette
 */
public interface Moveable {

	public void move(final int scrnWidth, final int scrnHeight);
	
	public void setAccelerating(final boolean accelerating);

	public void setTurningLeft(final boolean turningLeft);

	public void setTurningRight(final boolean turningRight);	
}
