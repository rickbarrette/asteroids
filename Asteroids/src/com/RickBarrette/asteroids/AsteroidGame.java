/**
 * AsteroidGame.java
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

import java.util.ArrayList;
import java.util.Random;

/**
 * This class maintain's the game logic. It is the main driver
 * @author ricky barrette
 */
public class AsteroidGame extends Thread {

	private static final int DELAY_IN_MSEC = 50;
	private final ArrayList<Object> mWorld;
	private final GameFrame mGameFrame;
	public boolean isStarted = false;

	/**
	 * Creates an new Asteroids game 
	 * @author ricky barrette
	 */
	public AsteroidGame() {
		mGameFrame = new GameFrame(this);
		mWorld = new ArrayList<Object>();
		//TODO simulate game play unitll game ist started
		this.start();
	}

	/**
	 * Adds an object to the world
	 * @param add
	 * @author ricky barrette
	 */
	public synchronized void addElement(Object o) {
		mWorld.add(o);
	}

	/**
	 * When this methods is called, the player's ship is down. 
	 * @author ricky barrette
	 */
	private void downShip() {
		System.out.println("Ship collision dected");
		/*
		 * move the players ship's
		 */
		Object o = null;
		Ship s = null;
		for (int i = 0; i < mWorld.size(); i++){
			o = mWorld.get(i);
			if(o instanceof Ship)
				s = (Ship) o;
		}

		if(s != null){
			s.allStop();
			hyperJump(s);
		}
		
		mGameFrame.getStatusBar().decrementShipCount();
		
		if(mGameFrame.getStatusBar().getShipCount() > 0){
			pauseGame();
			mGameFrame.setDisplayText("You died, You can hyper jump to a safe place now... Press start when ready.");
		} else {
			mGameFrame.setDisplayText("Game Over");
			if(s != null)
				mWorld.remove(s);
		}
		mGameFrame.repaint();
	}

	/**
	 * @return the world
	 * @author ricky barrette
	 */
	public ArrayList<Object> getWorld() {
		return mWorld;
	}
	
	/**
	 * Hyperjumps the sip
	 * @param ship
	 * @author ricky barrette
	 */
	public void hyperJump(Ship ship) {
//		boolean isSafe = true;
		Random gen = new Random();
//		do{
			System.out.println("hyper jumping");
			ship.setLocation(gen.nextInt(mGameFrame.getDisplayWidth()), gen.nextInt(mGameFrame.getDispalyHeight()));
//			for(int i = 0; i < mWorld.size(); i++)
//				if(mWorld.get(i) instanceof Collider)
//					if(((Collider) mWorld.get(i)).checkForCollision(ship))
//						isSafe = false;
//		} while (!isSafe);
			mGameFrame.repaintDispaly();		
	}
	
	/**
	 * populates the world for a new game 
	 * @author ricky barrette
	 */
	public void initLevel() {
		Random gen = new Random();
		/*
		 * added a asteroid per level
		 */
		for(int i = 0; i < mGameFrame.getStatusBar().getLevel(); i ++)
			addElement(new Asteroid(gen.nextInt(mGameFrame.getDisplayWidth()), gen.nextInt(mGameFrame.getDispalyHeight()), 1, 10, 50, 3, 3, this));
		
		notification("Level "+ mGameFrame.getStatusBar().getLevel());
	}

	/**
	 * @return true if the world is empty
	 * @author ricky barrette
	 */
	public boolean isEmpty() {
		return mWorld.isEmpty();
	}

	/**
	 * Clears the world, and Creates a new game 
	 * @author ricky barrette
	 */
	public void newGame() {
		Random gen = new Random();
		mWorld.clear();
		mGameFrame.setDisplayText(null);
		
		mGameFrame.getStatusBar().setShipCount(3);
		mGameFrame.getStatusBar().setScore(0);
		mGameFrame.getStatusBar().setAsteroidCount(1);
		mGameFrame.getStatusBar().setTime(0);
		mGameFrame.getStatusBar().setShotCount(0);
		mGameFrame.getStatusBar().setLevel(1);
		
		mWorld.add(new Ship(gen.nextInt(mGameFrame.getDisplayWidth()), gen.nextInt(mGameFrame.getDispalyHeight()),0,.35,.98,.2,1));
		
		initLevel();
		
		startGame();
		
		notification("Level "+ mGameFrame.getStatusBar().getLevel());
	}

	/**
	 * Displays a nofication to the user for 2 seconds
	 * @param string
	 * @author ricky barrette
	 */
	private void notification(final String string) {
		mGameFrame.setDisplayText(string);
		
		new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(isStarted)
					mGameFrame.setDisplayText(null);
			}
		}).start();
	}

	/**
	 * Pauses the game 
	 * @author ricky barrette
	 */
	public synchronized void pauseGame(){
		isStarted = false;
		mGameFrame.setDisplayText("Paused");
		setMovingSpaceObjectsEnabled(false);
	}

	/**
	 * Removes an object from this world.
	 * @param o object to be removed
	 * @author ricky barrette
	 */
	public synchronized void removeElement(Object o) {
		if(o instanceof Asteroid) {
			mGameFrame.getStatusBar().incrementScore(2);
		}
		mWorld.remove(o);
	}

	/**
	 * Main game driver method.
	 * 
	 * @author ricky barrette
	 */
	@Override
	public void run() {
		boolean hasOneUped = false;
		int asteroidCount = 0, shotCount = 0;
		Object o;
		Collider c;
		Object[] world;
		while (true){
			if(isStarted) {
	
				/*
				 * update the display and stats
				 */
				mGameFrame.repaintDispaly();
				mGameFrame.getStatusBar().updateStatus();
				
				/*
				 * check for collsions
				 */
				world = mWorld.toArray();
				for (int i = 0; i < world.length; i++){
					o = world[i];
					if(o instanceof Collider){
						asteroidCount++;
						c = (Collider) o;
						for(int index = 0; index < world.length; index++)
							if(c.checkForCollision(world[index]))
								//check to see if the ship blew up
								if(world[index] instanceof Ship)
									downShip();
					}
					
					//coutn the shots
					if(o instanceof Shot)
						shotCount++;
				}
				
				/*
				 * if there are no more asteroids, then increment the level
				 */
				if(asteroidCount == 0){
					mGameFrame.getStatusBar().incrementLevel();
					initLevel();
				}
				
				/*
				 * 1up every 200 points
				 */
				if(mGameFrame.getStatusBar().getScore() > 0 && mGameFrame.getStatusBar().getScore() % 200 == 0){
					if(!hasOneUped){
						mGameFrame.getStatusBar().incrementShipCount();
						hasOneUped = true;
										
						notification("1up!");
					}
				} else
					hasOneUped = false;
				
				/*
				 * update the status bar with the new counts
				 */
				mGameFrame.getStatusBar().setShotCount(shotCount);
				mGameFrame.getStatusBar().setAsteroidCount(asteroidCount);
				
				/*
				 * reset counters
				 */
				asteroidCount = 0;
				shotCount = 0;
			}
			/*
			 * sleep till next time
			 */
			try {
				sleep(DELAY_IN_MSEC);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the enabled state of Moving Space Objects
	 * @param b
	 * @author ricky barrette
	 */
	public void setMovingSpaceObjectsEnabled(boolean b) {
		for(Object item : mWorld)
			if(item instanceof MovingSpaceObject)
				((MovingSpaceObject) item).setActive(b);	
	}
	
	/**
	 * @return the number of objects in the world
	 * @author ricky barrette
	 */
	public int size() {
		return mWorld.size();
	}

	/**
	 *  Starts the game
	 * @author ricky barrette
	 */
	public synchronized void startGame(){
		mGameFrame.setDisplayText(null);
		setMovingSpaceObjectsEnabled(true);
		isStarted = true;	
	}
}