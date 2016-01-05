/*
 *  JAsteroids - an Asteroids clone in Java.
 *  Copyright (C) 2001, Adam Adair
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  ---
 *
 *  See the "COPYRIGHT", "COPYING" and "README" files for all the
 *  related information and legal notices. It is expected that any future
 *  projects/authors will amend these files as needed.
 */
package adairpub.jasteroids;

import java.awt.*;

import adairpub.graphics.*;

/**
 * This class extends the sprite list class to provide scoring and
 * sprite bounds checking for the Java Meteors.
 */
public class AsteroidSpriteList extends adairpub.graphics.SpriteList
{

   private static int  ASTEROID_COUNT = 8;
   public boolean      rotateLeft,    // flags set when the player presses keys
                       rotateRight,
                       thrust,
                       fire;
   private long        score;
   private int         shipsLeft,
                       newShipCountdown,
                       shotDelay,
                       level,
                       levelCountDown,
                       gameOverCountDown,
                       shotDelayReset;
   public boolean      levelOver, gameOver, eogMessageDisplayed;   
   private Ship        ship;
   private Message     scoreDisplay,
                       gameTitle,
                       copyRight,
                       instructions,
                       gameOverMessage,
                       shipDisplay;

   private Point       origin;
   private Size        frameSize;



   public AsteroidSpriteList(int w, int h)
   {
      super();

      gameOver=true;
      frameSize = new Size(w, h);
      origin = new Point( (w/2), (h/2) );
      rotateLeft = rotateRight = thrust = fire = false;
      shotDelay = shotDelayReset = 5;
      score=0;
      shipsLeft=4;
      gameOver=true;
      newShipCountdown=0;
      shipDisplay = new Message(new Point(10, 25),frameSize,"");
      scoreDisplay = new Message(new Point(10, 50),frameSize,"");
      gameTitle = new Message(new Point(250, 100), frameSize,"JAsteroids!");
      gameTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
      gameTitle.setFgColoR(Color.yellow);
      copyRight = new Message(new Point(225, 150), frameSize,"Copyright (c) Adam Adair 2000");
      instructions = new Message(new Point(225, 200), frameSize, "Press Spacebar to start game");
      gameOverMessage = new Message(new Point(265, 200), frameSize, "Game Over");
      eogMessageDisplayed = false;
   }

   public void repaintAll(Graphics g)
   {
      Sprite temp;
      int    current = 0;
      boolean canExit = false;
      if (v.size() == 0) canExit=true;

      while (!canExit)
      {
          temp = (Sprite) v.elementAt(current);
          if (!temp.isCondemned())
          {
              temp.repaint(g);
              ++current;
          }
           else
           {
               v.removeElementAt(current);
           }
           if ((current+1)>v.size()) canExit=true;
       }
   }

   public long getScore(){return score;}
   public long addScore(int s)
   {
      score += s;
      return score;
   }

   public void startGame()
   {
      gameOver = false;
      score = 0;
      level = 0;
      removeAllSprites();
      shipsLeft = 5;
      newShip();
      scoreDisplay.setText("");
      addSprite(scoreDisplay);
      addSprite(shipDisplay);
      startNextLevel();
   }

   private void endGame()
   {
      this.removeAllSprites();
      this.addSprite(gameTitle);
      this.addSprite(copyRight);
      this.addSprite(instructions);
      eogMessageDisplayed=true;
   }

   public void startNextLevel()
   {
      int asteroidNum;
      levelOver = false;
      ++level;
      asteroidNum = 3 + level;
      if (asteroidNum > ASTEROID_COUNT) asteroidNum = ASTEROID_COUNT;
      for (int i=0; i < asteroidNum; ++i)
      {
         addSprite(new Asteroid( new Point( (int)(Math.random() * frameSize.cx),
                              (int)(Math.random() * frameSize.cy) ),
		              frameSize,
                              new Size( (int)(Math.random() * 5)-3,
                              (int)(Math.random() * 5)-3 ),
                              3,
                              3 ));
      }
   }

   public void newShip()
   {
      int i;
      String sh="";
      ship = new Ship(origin, frameSize, this);
      addSprite(ship);
      shipsLeft--;
      for (i=0;i<shipsLeft;++i)
      {
         sh = sh + "^";
      }
      shipDisplay.setText(sh);
   }

   public Sprite checkForHit(Point p)
   {
      Sprite temp;
      int i;
      int top = v.size();
      for(i=1; i<=top; ++i)
      {
         temp = (Sprite) v.elementAt(i-1);
         if (temp.toString().startsWith("Asteroid"))
         {
            if (temp.getBoundingRect().isInRect(p))
            {
               return temp;
            }
         }
      }
      return null;
   }

   public Asteroid checkForCollision(Point p)
   {
      Sprite temp;
      int i;
      int top = v.size();

      for(i=1; i<=top; ++i)
      {
         temp = (Sprite) v.elementAt(i-1);
         if (temp.toString().startsWith("Asteroid"))
         {
            if (temp.getBoundingRect().isInRect(p))
            {
               return (Asteroid)temp;
            }
         }
      }
      return null;
   }

   public void checkLevel()
   {
      if(!levelOver)
      {
         Sprite temp;
         int i;
         int meteorNum=0;
         int top = v.size();
         for(i=1; i<=top; ++i)
         {
            temp = (Sprite) v.elementAt(i-1);
            if (temp.toString().startsWith("Asteroid"))
            {
               ++meteorNum;
            }
         }
         if (meteorNum==0)
         {
            levelCountDown = 100;
            levelOver = true;
         }
      }
      else
      {
         if (levelCountDown>0)
         {
            --levelCountDown;
         }
         else startNextLevel();
       }
    }

   public void action()
   {
      if (!gameOver)
      {
          //Game is not over, so lets do some calculations.
         if (newShipCountdown>0)
         {
            newShipCountdown--;
            if (newShipCountdown==0) {
               newShip();
            }
         }
          // depending on what keys the user has pressed, adjust the
          // ship position/thrust/etc
          if (ship!=null)
          {
             checkForShipEvents();
          }
          // update the score display
         if (score>0)
         {
            scoreDisplay.setText(String.valueOf(score));
         }
         checkLevel();
      }
      else
      {
         //game is over
         if (gameOverCountDown > 0)
         {
            gameOverCountDown--;
	      }
         else
         {
	         if (!eogMessageDisplayed) endGame();
         }
         if (fire) startGame();
      }
   }

   private void checkForShipEvents()
   {
      if (rotateLeft) ship.rotate(15);
      if (rotateRight) ship.rotate(-15);

      if (thrust)
         ship.addThrust(1);
      else
         ship.addThrust( -1 );

      if ((fire) && (shotDelay==0))
      {
         addSprite( ship.createNewShot() );
         shotDelay = shotDelayReset;
      }
      if (shotDelay>0)
         shotDelay--;

      if (ship.isHit)
      {  // ship was hit
         ship.explode();
         ship = null;
         if (shipsLeft < 1)
         {
             gameOver=true;
             eogMessageDisplayed=false;
             gameOverCountDown=140;
             addSprite(gameOverMessage);
         }
         else newShipCountdown = 100;
      }
   }
}