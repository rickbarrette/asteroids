/**
 * Main.java
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
 * This is the main class of my asteroids game.
 * This application is based from "A Guide to Programming Asteroids As a Java Applet" by Brandon Carroll.
 * @author ricky barrette
 */
public class Main {

	public static final boolean DEBUG = false;
	
	/**
	 * Creates a new Main
	 * @author ricky barrette
	 */
	public Main() {
		AsteroidGame game = new AsteroidGame();
		game.newGame();
	}

	/**
	 * @param args
	 * @author ricky barrette
	 */
	public static void main(String[] args) {
		new Main();
	}
}