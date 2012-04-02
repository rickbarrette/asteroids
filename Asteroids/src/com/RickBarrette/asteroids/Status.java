/**
 * Status.java
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

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class will be used for keeping track of and displaying the game status
 * information.
 * @author ricky barrette
 */
public class Status extends JPanel {

	private static final long serialVersionUID = -169321993637429941L;
	private JLabel status;

	/**
	 * Creates a new Status
	 * @param container
	 * @param g
	 * @author ricky barrette
	 */
	public Status(Container container, AsteroidGame g) {
		JPanel northSubPanel = new JPanel();
		status = new JLabel("Missiles 0 Asteroids 0 Ships 0 Score 0 Time: 0");
		northSubPanel.add(status);
		container.add(northSubPanel, BorderLayout.NORTH);
	}

	/**
	 * Sets the status
	 * @param temp
	 * @author ricky barrette
	 */
	public void setStatus(String temp) {
		status.setText(temp);
	}
}