/**
 * GameFrame.java
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

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This class will maintian the game's frame. 
 * It will be used to display all the game's information to the user and handle key events from the user
 * @author ricky barrette
 */
public class GameFrame extends JFrame implements KeyListener, ActionListener{

	private static final long serialVersionUID = -2051298505681885632L;

	private Status mStatusBar;
	private Display mDisplay;
	private AsteroidGame mGame;

	/**
	 * Creates a new GameFrame
	 * @param g
	 * @author ricky barrette
	 */
	public GameFrame(AsteroidGame g) {
		super("ASTEROIDS");
		mGame = g;

		/*
		 * set up the game's menus
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/*
		 * file menu
		 */
		JMenu fileMenu = new JMenu("File");
		JMenuItem menuNewGame = new JMenuItem("New Game");
		JMenuItem menuStartGame = new JMenuItem("Start");
		JMenuItem menuPauseGame = new JMenuItem("Pause");
		JMenuItem menuQuit = new JMenuItem("Quit");

		fileMenu.add(menuNewGame);
		fileMenu.addSeparator();
		fileMenu.add(menuStartGame);
		fileMenu.add(menuPauseGame);
		fileMenu.addSeparator();
		fileMenu.add(menuQuit);
		menuNewGame.addActionListener(this);
		menuQuit.addActionListener(this);
		menuStartGame.addActionListener(this);
		menuPauseGame.addActionListener(this);

		menuBar.add(fileMenu);
		
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);

		Container container = getContentPane();
		mStatusBar = new Status(container, mGame);
		mDisplay = new Display(container, mGame);
		
		addKeyListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// sets up window's location and sets size****
		setSize(1000, 800);
		setVisible(true);

	}

	/**
	 * Called when a menu item is selected from the benu bar
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("New Game")) {
			mGame.newGame();
			mDisplay.repaint();
		}
		
		if (e.getActionCommand().equals("Start")) {
			mGame.startGame();
		}
		
		if (e.getActionCommand().equals("Pause")) {
			mGame.pauseGame();
		}
		
		if (e.getActionCommand().equals("Quit"))
			System.exit(0);
	}

	/**
	 * Drives the user's ship
	 * @param e
	 * @param isKeyPressed
	 * @author ricky barrette
	 */
	private void driveShip(KeyEvent e, boolean isKeyPressed) {
		Ship ship = null;
		//get the user's ship
		for (Object item : mGame.getWorld()) {
			if (item instanceof Ship) {
				ship = (Ship) item;
			}
		}
		
		if(ship != null)
			switch(e.getKeyCode()){
	
				/*
				 * [Left arrow] or [A] 
				 * Rotate the ship left
				 */
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					ship.setTurningLeft(isKeyPressed);
					break;
	
				/*
				 * [Right arrow] or [d] Rotate the ship right
				 */
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					ship.setTurningRight(isKeyPressed);
					break;
	
				/*
				 * [Up arrow] or [W] 
				 * Increase the ship's speed
				 */
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					ship.setAccelerating(isKeyPressed);
					break;
	
				/*
				 * [Down arrow] or [S] 
				 * Slow the ship
				 */
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					ship.setAccelerating(isKeyPressed);
					break;
					
					
				/*
				 * [H] Hyoer jump
				 */
				case KeyEvent.VK_H:
					if(isKeyPressed){
						mGame.hyperJump(ship);
					}
					break;
					
				/*
				 * [Space] Pew Pew Pew!!!
				 */
				case KeyEvent.VK_SPACE:
					if(isKeyPressed)
						mGame.addElement(new Shot(ship.getX(), ship.getY(), ship.getAngle(), ship.getXVelocity(), ship.getYVelocity(), mGame));
					break;
			}
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
	 * @return the mStatusBar
	 */
	public Status getStatusBar() {
		return mStatusBar;
	}

	/**
	 * Called when a key is pressed
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()){
		/*
		 * [Enter]  
		 * Start of pause the game
		 */
		case KeyEvent.VK_ENTER:
			if(mGame.isStarted)
				mGame.pauseGame();
			else
				mGame.startGame();
			break;
		}
		
		driveShip(e, true);	
	}

	/**
	 * Called when a key is released
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		driveShip(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
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

	/**
	 * updates and repaints all world objects 
	 * @author ricky barrette
	 */
	public void repaintDispaly() {
		mDisplay.repaint();
	}

	/**
	 * Sets test to be displayed in the center of the display
	 * @param string to be displayed
	 * @author ricky barrette
	 */
	public void setDisplayText(String string) {
		mDisplay.setDisplayText(string);
		this.repaint();
	}
}