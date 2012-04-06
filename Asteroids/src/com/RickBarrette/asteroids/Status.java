/**
 * Status.java
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
	private final JLabel status;
	private StringBuffer mBuffer;
	private int mShotCount = 0;
	private int mAsteroidCount = 0;
	private int mShipCount = 0;
	private long mScore = 0;
	private long mTime = 0;
	private int mLevel = 0;

	/**
	 * Creates a new Status
	 * @param container
	 * @param g
	 * @author ricky barrette
	 */
	public Status(final Container container, final AsteroidGame g) {
		JPanel northSubPanel = new JPanel();
		status = new JLabel("Missiles 0 Asteroids 0 Ships 0 Score 0 Time: 0");
		northSubPanel.add(status);
		container.add(northSubPanel, BorderLayout.NORTH);
		mBuffer = new StringBuffer();
	}

	/**
	 * decreases total asteroid count by 1 
	 * @author ricky barrette
	 */
	public synchronized void decrementAsteroidCount() {
		if(this.mAsteroidCount > 0)
			this.mAsteroidCount -= 1;
	}

	/**
	 * reduces the level by one 
	 * @author ricky barrette
	 */
	public void decrementLevel() {
		if(this.mLevel > 0)
			this.mLevel -= 1;
	}

	/**
	 * reduces total score by x amount
	 * @param score
	 * @author ricky barrette
	 */
	public synchronized void decrementScore(final int score) {
		if(this.mScore - mScore > 0)
			this.mScore -= mScore;
	}

	/**
	 * reduces total ships by 1 
	 * @author ricky barrette
	 */
	public synchronized void decrementShipCount() {
		if(this.mShipCount > 0)
			this.mShipCount -= 1;
	}

	/**
	 * reduces total shots by 1 
	 * @author ricky barrette
	 */
	public synchronized void decrementShotCount() {
		if(this.mShotCount > 0)
			this.mShotCount -= 1;
	}

	/**
	 * Reduces time be x amount
	 * @param mTime
	 * @author ricky barrette
	 */
	public synchronized void decrementTime(final long mTime) {
		if(this.mTime - mTime > 0)
		this.mTime -= mTime;
	}
	
	/**
	 * @return the mAsteroidCount
	 */
	public synchronized int getAsteroidCount() {
		return mAsteroidCount;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return mLevel;
	}

	/**
	 * @return the mScore
	 */
	public synchronized long getScore() {
		return mScore;
	}
	
	/**
	 * @return the mShipCount
	 */
	public synchronized int getShipCount() {
		return mShipCount;
	}
	
	/**
	 * @return the mShotCount
	 */
	public synchronized int getShotCount() {
		return mShotCount;
	}

	/**
	 * @return the mTime
	 */
	public synchronized long getTime() {
		return mTime;
	}

	/**
	 * increases total asteroid count by 1 
	 * @author ricky barrette
	 */
	public synchronized void incrementAsteroidCount() {
		this.mAsteroidCount += 1;
	}
	
	/**
	 * increases the level by one 
	 * @author ricky barrette
	 */
	public void incrementLevel() {
		this.mLevel += 1;
	}

	/**
	 * increases total score by x amount
	 * @param score
	 * @author ricky barrette
	 */
	public synchronized void incrementScore(final int score) {
		this.mScore += score;
	}
	
	/**
	 * increases total ships by one 
	 * @author ricky barrette
	 */
	public synchronized void incrementShipCount() {
		this.mShipCount += 1;
	}
	
	/**
	 * increases total shots by one 
	 * @author ricky barrette
	 */
	public synchronized void incrementShotCount() {
		this.mShotCount += 1;
	}

	/**
	 * increates time by x amount
	 * @param mTime
	 * @author ricky barrette
	 */
	public synchronized void incrementTime(final long mTime) {
		this.mTime += mTime;
	}
	
	/**
	 * convince method for formating the seconds string
	 * @param seconds
	 * @return formated string
	 * @author ricky barrette
	 */
	private String padTime(final int time){
		if (time <= 9)
			return "0"+ time;
		return ""+ time;
	}
	
	/**
	 * @param mAsteroidCount the mAsteroidCount to set
	 */
	public synchronized void setAsteroidCount(final int mAsteroidCount) {
		this.mAsteroidCount = mAsteroidCount;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(final int level) {
		this.mLevel = level;
	}

	/**
	 * @param mScore the mScore to set
	 */
	public synchronized void setScore(final long mScore) {
		this.mScore = mScore;
	}

	/**
	 * @param mShipCount the mShipCount to set
	 */
	public synchronized void setShipCount(final int mShipCount) {
		this.mShipCount = mShipCount;
	}
	
	/**
	 * @param mShotCount the mShotCount to set
	 */
	public synchronized void setShotCount(final int mShotCount) {
		this.mShotCount = mShotCount;
	}
	
	/**
	 * @param mTime the mTime to set
	 */
	public synchronized void setTime(final long mTime) {
		this.mTime = mTime;
	}

	/**
	 * convince method for formating milliseconds into hour : minutes format
	 * @param mills
	 * @return human readable hour : minutes format
	 * @author ricky barrette
	 */
	private String stringTime(final long mills){
		final int hours = (int) (mills / 3600000);
		final long millsMinusHours = mills - (hours * 3600000);
		final int minutes = (int) ( millsMinusHours / 60000);
		final int seconds = ((int) (millsMinusHours % 60000)) / 1000;
		return hours +" : "+ padTime(minutes) +" : "+ padTime(seconds);
	}
	
	/**
	 * Sets the status
	 * @param temp
	 * @author ricky barrette
	 */
	public void updateStatus() {
		mBuffer.append("Level: ");
		mBuffer.append(mLevel);
		mBuffer.append("       Missiles: ");
		mBuffer.append(getShotCount());
		mBuffer.append("       Asteroids: ");
		mBuffer.append(getAsteroidCount());
		mBuffer.append("       Ships: ");
		mBuffer.append(getShipCount());
		mBuffer.append("       Score: ");
		mBuffer.append(getScore());
		mBuffer.append("       Time: ");
		mBuffer.append(stringTime(getTime()));
		status.setText(mBuffer.toString());
		mBuffer = new StringBuffer();
	}
}