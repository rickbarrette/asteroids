/**
 * AsteroidGame.java
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

import java.util.ArrayList;
import java.util.Vector;

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
		if(o instanceof Shot)
			mGameFrame.getStatusBar().incrementShotCount();
		
		if(o instanceof Asteroid)
			mGameFrame.getStatusBar().incrementAsteroidCount();
		
		mWorld.add(o);
	}

	/**
	 * When this methods is called, the player's ship is down. 
	 * @author ricky barrette
	 */
	private void downShip() {
		System.out.println("Ship collision dected");
		/*
		 * remove the players ship's
		 */
		Object o;
		for (int i = 0; i < mWorld.size(); i++){
			o = mWorld.get(i);
			if(o instanceof Ship)
				mWorld.remove(i);
		}
		
		mGameFrame.getStatusBar().decrementShipCount();
		
		if(mGameFrame.getStatusBar().getShipCount() > 0){
			pauseGame();
			mWorld.add(new Ship(100,100,0,.35,.98,.4,1));
			mGameFrame.setDisplayText("You died, You can hyper jump to a safe place now...\nPress start when ready.");
		} else {
			mGameFrame.setDisplayText("Game Over");
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
	 * populates the world for a new game 
	 * @author ricky barrette
	 */
	public void initLevel() {	
		/*
		 * added a asteroid per level
		 */
		for(int i = 0; i < mGameFrame.getStatusBar().getLevel(); i ++)
			addElement(new Asteroid(500, 500, 1, 10, 50, 3, 3, this));
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
		mWorld.clear();
		mGameFrame.setDisplayText(null);
		
		mGameFrame.getStatusBar().setShipCount(3);
		mGameFrame.getStatusBar().setScore(0);
		mGameFrame.getStatusBar().setAsteroidCount(1);
		mGameFrame.getStatusBar().setTime(0);
		mGameFrame.getStatusBar().setShotCount(0);
		mGameFrame.getStatusBar().setLevel(1);
		
		mWorld.add(new Ship(100,100,0,.35,.98,.4,1));
		
		initLevel();
		
		startGame();
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
		if(o instanceof Shot)
			mGameFrame.getStatusBar().decrementShotCount();
		
		if(o instanceof Asteroid) {
			mGameFrame.getStatusBar().decrementAsteroidCount();
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
		while (true){
			if(isStarted) {
				boolean isThereAsteroids = false;
	
				/*
				 * update the display and stats
				 */
				mGameFrame.repaintDispaly();
				mGameFrame.getStatusBar().updateStatus();
				
				/*
				 * check for collsions
				 */
				Object o;
				Collider c;
				Vector<Object> wolrd = new Vector<Object>(mWorld);
				for (int i = 0; i < wolrd.size(); i++){
					o = wolrd.get(i);
					if(o instanceof Collider){
						isThereAsteroids = true;
						c = (Collider) o;
						for(int index = 0; index < wolrd.size(); index++)
							if(c.checkForCollision(wolrd.get(index)))
								//check to see if the ship blew up
								if(wolrd.get(index) instanceof Ship)
									downShip();
					}
				}
				
				/*
				 * if there are no more asteroids, then increment the level
				 */
				if(!isThereAsteroids){
					mGameFrame.getStatusBar().incrementLevel();
					initLevel();
				}
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