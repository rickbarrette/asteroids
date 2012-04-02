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

	private JMenuBar bar;
	private JMenu menu;
	private JMenuItem newGame;
	private JMenuItem quit;
	private Status statusBar;
	private Display mDisplay;
	private Container container;
	private FlowLayout layout;
	private menuListener xlistener;
	private AsteroidGame game;

	/**
	 * Creates a new GameFrame
	 * 
	 * @param g
	 * @author ricky barrette
	 */
	public GameFrame(AsteroidGame g) {
		super("ASTEROIDS");
		game = g;

		bar = new JMenuBar();
		setJMenuBar(bar);

		menu = new JMenu("File");

		newGame = new JMenuItem("New Game");
		quit = new JMenuItem("Quit");

		menu.add(newGame);
		menu.addSeparator();
		menu.add(quit);

		bar.add(menu);

		layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);


		container = getContentPane();
		statusBar = new Status(container, game);
		mDisplay = new Display(container, game);


		xlistener = new menuListener();
		newGame.addActionListener(xlistener);
		quit.addActionListener(xlistener);

		addKeyListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// sets up window's location and sets size****
//		setLocation(0, 0); // the default location
		setSize(1000, 800);
		setVisible(true);

	}

	public void setStatus(String temp) {
		statusBar.setStatus(temp);
	}

	public void repaintDisplay() {
		mDisplay.repaint();
	}

	private class menuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("New Game")) {
				game.newGame();
				mDisplay.repaint();

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
		for (Object item : game.getWorld()) {

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
		for (Object item : game.getWorld()) {
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
}