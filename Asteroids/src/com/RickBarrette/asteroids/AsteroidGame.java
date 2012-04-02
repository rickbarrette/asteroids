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

import java.util.Vector;

/**
 * This class maintain's the game logic. It is the main driver
 * @author ricky barrette
 */
public class AsteroidGame extends Thread {

	private static final int DELAY_IN_MSEC = 50;
	private Vector<Object> mWorld;
	private GameFrame mGameFrame;
	public static boolean isStarted = false;

	/**
	 * Creates an new Asteroids game 
	 * @author ricky barrette
	 */
	public AsteroidGame() {
		mGameFrame = new GameFrame(this);
		//TODO simulate game play unitll game ist started
		this.start();
	}

	/**
	 * Adds an object to the world
	 * @param add
	 * @author ricky barrette
	 */
	public void addElement(Object o) {
		if(o instanceof Shot)
			mGameFrame.getStatusBar().setShotCount(mGameFrame.getStatusBar().getShotCount()+1);
		
		if(o instanceof Asteroid)
			mGameFrame.getStatusBar().setAsteroidCount(mGameFrame.getStatusBar().getAsteroidCount()+1);
		
		mWorld.addElement(o);
	}

	/**
	 * popoluates the world for a new game 
	 * @author ricky barrette
	 */
	public void createGame() {
		mWorld = new Vector<Object>();
		mWorld.add(new Ship(100,100,0,.35,.98,.4,1));
		addElement(new Asteroid(500, 500, 1, 10, 50, 3, 3, this));
		mGameFrame.getStatusBar().setShipCount(3);
	}

	public Vector<Object> getWorld() {
		return mWorld;
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
		createGame();
	}

	/**
	 * Pauses the game 
	 * @author ricky barrette
	 */
	public synchronized void pause(){
		isStarted = false;
		mGameFrame.setDisplayText("Paused");
	}

	/**
	 * Removes an object from this world.
	 * @param o object to be removed
	 * @author ricky barrette
	 */
	public void removeElement(Object o) {
		if(o instanceof Shot)
			mGameFrame.getStatusBar().setShotCount(mGameFrame.getStatusBar().getShotCount()-1);
		
		if(o instanceof Asteroid)
			mGameFrame.getStatusBar().setAsteroidCount(mGameFrame.getStatusBar().getAsteroidCount()-1);
		mWorld.removeElement(o);
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
	
				mGameFrame.repaintDisplay();
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
						c = (Collider) o;
						for(int index = 0; index < wolrd.size(); index++)
							if(c.checkForCollision(wolrd.get(index)))
								//check to see if the ship blew up
								if(wolrd.get(index) instanceof Ship)
									downShip();
					}
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
		
		mGameFrame.getStatusBar().setShipCount(mGameFrame.getStatusBar().getShipCount() -1);
		
		if(mGameFrame.getStatusBar().getShipCount() > 0){
			pause();
			mWorld.add(new Ship(100,100,0,.35,.98,.4,1));
			mGameFrame.setDisplayText("You died, press start when ready.");
		} else {
			mGameFrame.setDisplayText("Game Over");
		}
		mGameFrame.repaint();
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
		mGameFrame.setMovingSpaceObjectsEnabled(true);
		isStarted = true;
		
	}
}