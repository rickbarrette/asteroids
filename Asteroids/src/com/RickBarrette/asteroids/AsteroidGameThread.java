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
public class AsteroidGameThread extends Thread {

	private static final int DELAY_IN_MSEC = 50;
	private final ArrayList<Object> mWorld;
	private final GameApplet mGameApplet;
	public boolean isStarted = false;
	private long mLastTime;

	/**
	 * Creates an new Asteroids game 
	 * @author ricky barrette
	 */
	public AsteroidGameThread(GameApplet gameFrame) {
		mGameApplet = gameFrame;
		mWorld = new ArrayList<Object>();
		//TODO simulate game play unitll game ist started
		this.start();
	}

	/**
	 * Adds an object to the world
	 * @param add
	 * @author ricky barrette
	 */
	public synchronized void addElement(final Object o) {
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
			s.hyperJump();
		}
		
		mGameApplet.getStatusBar().decrementShipCount();
		
		if(mGameApplet.getStatusBar().getShipCount() > 0){
			pauseGame();
			mGameApplet.setDisplayText("You died, You can hyper jump to a safe place now... Press start when ready.");
		} else {
			mGameApplet.setDisplayText("Game Over");
			if(s != null)
				mWorld.remove(s);
		}
		mGameApplet.repaint();
	}

	/**
	 * @return the game's frame
	 * @author ricky barrette
	 */
	public GameApplet getGameFrame() {
		return this.mGameApplet;
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
		Random gen = new Random();
		/*
		 * added a asteroid per level
		 */
		for(int i = 0; i < mGameApplet.getStatusBar().getLevel(); i ++)
			addElement(new Asteroid(gen.nextInt(mGameApplet.getDisplayWidth()), gen.nextInt(mGameApplet.getDispalyHeight()), 1, 10, 50, 3, 3, this));
		
		notification("Level "+ mGameApplet.getStatusBar().getLevel());
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
		mGameApplet.setDisplayText(null);
		
		mGameApplet.getStatusBar().setShipCount(3);
		mGameApplet.getStatusBar().setScore(0);
		mGameApplet.getStatusBar().setAsteroidCount(1);
		mGameApplet.getStatusBar().setTime(0);
		mGameApplet.getStatusBar().setShotCount(0);
		mGameApplet.getStatusBar().setLevel(1);
		
		mWorld.add(new Ship(gen.nextInt(mGameApplet.getDisplayWidth()), gen.nextInt(mGameApplet.getDispalyHeight()), 0, .35, .98, .2, 1, this));
		
		initLevel();
		
		startGame();
		
		notification("Level "+ mGameApplet.getStatusBar().getLevel());
		mGameApplet.repaintDispaly();
	}

	/**
	 * Displays a nofication to the user for 2 seconds
	 * @param string
	 * @author ricky barrette
	 */
	private void notification(final String string) {
		mGameApplet.setDisplayText(string);
		
		new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(isStarted)
					mGameApplet.setDisplayText(null);
			}
		}).start();
	}

	/**
	 * Pauses the game 
	 * @author ricky barrette
	 */
	public synchronized void pauseGame(){
		isStarted = false;
		mGameApplet.setDisplayText("Paused");
		setMovingSpaceObjectsEnabled(false);
	}

	/**
	 * Removes an object from this world.
	 * @param o object to be removed
	 * @author ricky barrette
	 */
	public synchronized void removeElement(final Object o) {
		if(o instanceof Asteroid) {
			mGameApplet.getStatusBar().incrementScore(2);
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
				 * brute force focus,
				 * this seems to be the only fix I can find, for now 
				 */
				mGameApplet.requestFocus();
				
				/*
				 * increment time
				 */
				mGameApplet.getStatusBar().incrementTime(System.currentTimeMillis() - mLastTime);
				mLastTime = System.currentTimeMillis();
				
				/*
				 * update the display and stats
				 */
				mGameApplet.repaintDispaly();
				mGameApplet.getStatusBar().updateStatus();
				
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
					mGameApplet.getStatusBar().incrementLevel();
					initLevel();
				}
				
				/*
				 * 1up every 200 points
				 */
				if(mGameApplet.getStatusBar().getScore() > 0 && mGameApplet.getStatusBar().getScore() % 200 == 0){
					if(!hasOneUped){
						mGameApplet.getStatusBar().incrementShipCount();
						hasOneUped = true;
										
						notification("1up!");
					}
				} else
					hasOneUped = false;
				
				/*
				 * update the status bar with the new counts
				 */
				mGameApplet.getStatusBar().setShotCount(shotCount);
				mGameApplet.getStatusBar().setAsteroidCount(asteroidCount);
				
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
	public void setMovingSpaceObjectsEnabled(final boolean b) {
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
		mLastTime = System.currentTimeMillis();
		mGameApplet.setDisplayText(null);
		setMovingSpaceObjectsEnabled(true);
		isStarted = true;	
	}
}