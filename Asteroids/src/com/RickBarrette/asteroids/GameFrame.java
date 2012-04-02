/**
 * GameFrame.java
 * @date Mar 31, 2012
 * @author ricky barrette
 * @author Twenty Codes, LLC
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

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This class will maintian the game's frame, and handle key events from the user
 * @author ricky barrette
 */
public class GameFrame extends JFrame implements KeyListener{

	private static final long serialVersionUID = -2051298505681885632L;

	private JMenuBar mMenuBar;
	private JMenu mMenu;
	private JMenuItem mMenuNewGame;
	private JMenuItem mMenuQuit;
	private Status mStatusBar;
	private Display mDisplay;
	private Container mContainer;
	private FlowLayout mLayout;
	private menuListener xlistener;
	private AsteroidGame mGame;

	private JMenuItem mMenuStartGame;

	private JMenuItem mMenuPauseGame;

	/**
	 * Creates a new GameFrame
	 * 
	 * @param g
	 * @author ricky barrette
	 */
	public GameFrame(AsteroidGame g) {
		super("ASTEROIDS");
		mGame = g;

		mMenuBar = new JMenuBar();
		setJMenuBar(mMenuBar);

		mMenu = new JMenu("File");

		mMenuNewGame = new JMenuItem("New Game");
		mMenuStartGame = new JMenuItem("Start");
		mMenuPauseGame = new JMenuItem("Pause");
		mMenuQuit = new JMenuItem("Quit");

		mMenu.add(mMenuNewGame);
		mMenu.addSeparator();
		mMenu.add(mMenuStartGame);
		mMenu.add(mMenuPauseGame);
		mMenu.addSeparator();
		mMenu.add(mMenuQuit);

		mMenuBar.add(mMenu);

		mLayout = new FlowLayout();
		mLayout.setAlignment(FlowLayout.LEFT);


		mContainer = getContentPane();
		mStatusBar = new Status(mContainer, mGame);
		mDisplay = new Display(mContainer, mGame);


		xlistener = new menuListener();
		mMenuNewGame.addActionListener(xlistener);
		mMenuQuit.addActionListener(xlistener);
		mMenuStartGame.addActionListener(xlistener);
		mMenuPauseGame.addActionListener(xlistener);
		
		addKeyListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// sets up window's location and sets size****
		setSize(1000, 800);
		setVisible(true);

	}

	public void repaintDisplay() {
		mDisplay.repaint();
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.Component#repaint()
	 */
	@Override
	public void repaint() {
		mDisplay.repaint();
		super.repaint();
	}

	private class menuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("New Game")) {
				mGame.newGame();
				mDisplay.repaint();

			}
			if (e.getActionCommand().equals("Start")) {
				mGame.startGame();
			}
			if (e.getActionCommand().equals("Pause")) {
				mGame.pause();
			}
			if (e.getActionCommand().equals("Quit"))
				System.exit(0);
		}

	}

	/**
	 * Called when a key is pressed
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		Object item;
		for (int i = 0; i < mGame.getWorld().size(); i++) {
			item = mGame.getWorld().get(i);
			if (item instanceof Ship) {
				Ship ship = (Ship) item;
				switch (e.getKeyCode()) {

					/*
					 * [Left arrow] or [A]
					 * Rotate the ship left
					 */
					case KeyEvent.VK_A:
					case KeyEvent.VK_LEFT:
						ship.setTurningLeft(true);
						break;

					/*
					 * [Space] Pew Pew Pew!!!
					 */
					case KeyEvent.VK_SPACE:
						mGame.addElement(new Shot(ship.getX(), ship.getY(), ship.getAngle(), ship.getXVelocity(), ship.getYVelocity(), mGame));
						break;

					/*
					 * [Right arrow] or [D] 
					 * Rotate the ship right
					 */
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						ship.setTurningRight(true);
						break;

					/*
					 * [Up arrow] or [W]
					 * Increase the ship's speed
					 */
					case KeyEvent.VK_W:
					case KeyEvent.VK_UP:
						ship.setAccelerating(true);
						break;

					/*
					 * [Down arrow] or [S] 
					 * Slow the ship
					 */
					case KeyEvent.VK_S:
					case KeyEvent.VK_DOWN:
						ship.setAccelerating(false);
						break;

					/*
					 * [H] Hyoer jump
					 */
					case KeyEvent.VK_H:
						Random myRNG = new Random();
						ship.setLocation(myRNG.nextInt(mDisplay.getHeight()), myRNG.nextInt(mDisplay.getWidth()));
						break;
					}
			}

			mDisplay.repaint();

		}
		
	}

	/**
	 * Called when a key is released
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		for (Object item : mGame.getWorld()) {
			if (item instanceof Ship) {
				Ship ship = (Ship) item;
				
				switch (e.getKeyCode()) {

					/*
					 * [Left arrow] or [A] 
					 * Rotate the ship left
					 */
					case KeyEvent.VK_A:
					case KeyEvent.VK_LEFT:
						ship.setTurningLeft(false);
						break;
	
					/*
					 * [Right arrow] or [d] Rotate the ship right
					 */
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						ship.setTurningRight(false);
						break;
	
					/*
					 * [Up arrow] or [W] 
					 * Increase the ship's speed
					 */
					case KeyEvent.VK_W:
					case KeyEvent.VK_UP:
						ship.setAccelerating(false);
						break;
	
					/*
					 * [Down arrow] or [S] 
					 * Slow the ship
					 */
					case KeyEvent.VK_S:
					case KeyEvent.VK_DOWN:
						ship.setAccelerating(false);
						break;

				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the height of the game display panel
	 * @author ricky barrette
	 */
	public int getDispalyHeight() {
		return mDisplay.getHeight();
	}

	/**
	 * @return the width of the game dispaly panel
	 * @author ricky barrette
	 */
	public int getDisplayWidth() {
		return mDisplay.getWidth();
	}

	/**
	 * Sets the enabled state of Moving Space Objects
	 * @param b
	 * @author ricky barrette
	 */
	public void setMovingSpaceObjectsEnabled(boolean b) {
		for(Object item : mGame.getWorld())
			if(item instanceof MovingSpaceObject)
				((MovingSpaceObject) item).setActive(b);	
	}

	/**
	 * @return the mStatusBar
	 */
	public Status getStatusBar() {
		return mStatusBar;
	}

	public void setDisplayText(String string) {
		mDisplay.setDisplayText(string);
		this.repaint();
	}
}