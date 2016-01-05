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
package adairpub.graphics;

import java.awt.*;

/**
 * Sprite is an abstract class.  It is intended to be implemented as objects
 * displayed graphically.
 *
 * ToDo:
 *    (1) The sind and cosd functions are currently being calculated on the fly.
 *    These value should probably be moved into an array of double size 360, and
 *    calculated only once for each possible angle, since there are only 360
 *    possible cardinal angles.
 *
 * @author Adam W Adair
 * @version 1.0
 */
public abstract class Sprite
{
    /**
     * Sprite not bound to world screenSize.
     */
    public static final int SPRITE_FREE = 0;
    /**
     * Sprite should wrap to other side of screen.
     */
    public static final int SPRITE_WRAP = 1;
    /**
     * Sprite should bounce when edge of screen is reached.
     */
    public static final int SPRITE_BOUNCE = 2;

    /**
     *  ADJUST is constant value to convert degrees to radians
     *  pi can be computed as atan(1)*4,
     *  and there are 2*pi radians in 360 degrees so:
     *  adjust = 2*pi/360 = pi/180 = atan(1)*4/180 = atan(1)/45
     */
    protected static final double ADJUST = Math.atan(1)/45;

    //protected attributes
    protected Point         origin;             //Sprite posistion in world.
    protected Rect          boundingRect;       //a rectangle to completely enclose the sprite
    protected double        mx,my;              //Current direction of moving sprite
    protected boolean       condemned;          //true if the object is dead
    protected Size          screenSize;         //Size of the playing screen
    protected SpriteList    owner;              //A list that owns the object
    protected int           screenBinding;      //Set sprite to wrap, bounce, or not react to
                                                //edge of the world.
    public static double sind( int angle )
    {
        return Math.sin((ADJUST * ((angle+360)%360)));
    }

    public static double cosd( int angle )
    {
        return Math.cos((ADJUST * ((angle+360)%360)));
    }

   /**
    * default public constructor resets all attributes to 0, and the
    * screen binding is set to SPRITE_WRAP.
    */
   public Sprite()
   {
      screenSize = new Size();
      origin = new Point();
      resetBoundingRect();
      mx=my=0;
      condemned = false;
      screenBinding = SPRITE_WRAP;
   }

   /**
    * constructor that allows the user to specify the screen size.
    */
   public Sprite( Size tmpScreenSize )
   {
      screenSize = tmpScreenSize;
      origin = new Point();
      resetBoundingRect();
      mx=my=0;
      condemned = false;
      screenBinding = SPRITE_WRAP;
   }

   /**
    * Sets the x and y momentum (delta per time unit).
    */
   public void setMomentum( Size newMomentum )
   {
      mx = newMomentum.cx;
      my = newMomentum.cy;
   }

   /**
    * Sets the x and y momentum (delta per time unit).
    */
   public void setMomentum( double cx, double cy )
   {
      mx = cx;
      my = cy;
   }

   /**
    * adds the momentum passed in newMomentum
    * to the current momentum of the sprite.
    */
   public void addMomentum( Size newMomentum )
   {
      mx += newMomentum.cx;
      my += newMomentum.cy;
   }

   /**
    * adds the momentum passed in newMomentum
    * to the current momentum of the sprite.
    */
   public void addMomentum( double cx, double cy )
   {
      mx += cx;
      my += cy;
   }

   /**
    * resets the sprites bounding rectangle to a single point its origin.
    * in other words, unless it is a single point, it is boundless.
    */
   public void resetBoundingRect()
   {
      boundingRect = new Rect( origin, origin);
   }

   /**
    * Condemns the sprite to die in the near future.
    */
   public void condemn(){condemned = true;}

   /**
    * returns true if the sprite is comdemned
    */
   public boolean isCondemned(){return condemned;}

   /**
    * expands the sprites bounding rectangle to include Point p.
    */
   public void expandBoundingRect( Point p )
   {
      if (p.x < boundingRect.left)
         boundingRect.left = p.x;
      if (p.y < boundingRect.top)
         boundingRect.top = p.y;
      if (p.x > boundingRect.right)
         boundingRect.right = p.x;
      if (p.y > boundingRect.bottom)
         boundingRect.bottom = p.y;
      boundingRect.normalize();
   }

   /**
    * Changes the sprites x/y position based on it's momentum.
    */
   public void update()
   {
      origin.x+=mx;
      origin.y+=my;

      switch(screenBinding)
      {
         case SPRITE_FREE:
             break;
         case SPRITE_WRAP:
             wrap();
             break;
         case SPRITE_BOUNCE:
             bounce();
             break;
      }
   }

   /**
    * updates the sprites position and repaints it to Graphics object g.
    */
   public void repaint(Graphics g)
   {
   update();
   paint(g);
   }

   /**
    * Wraps the sprite to the oposite edge of the screen.
    */
   public void wrap()
   {
      origin.x = (origin.x + screenSize.cx) % screenSize.cx;
      origin.y = (origin.y + screenSize.cy) % screenSize.cy;
   }

   /**
    * modifies the sprites momentum values to send the sprite in the opposite
    * direction if it reaches a coordinate outside of screenSize rectangle.
    */
   public void bounce()
   {
      if (origin.x < 0)
      {
         origin.x = Math.abs(origin.x);
         mx = -(mx);
      }
      else if (origin.x  > screenSize.cx)
      {
         origin.x = screenSize.cx - (origin.x - screenSize.cx);
         mx = -(mx);
      }
      if (origin.y < 0)
      {
         origin.y = Math.abs(origin.y);
         my = -(my);
      }
      else if (origin.y  > screenSize.cy)
      {
         origin.y = screenSize.cy - (origin.y - screenSize.cy);
         my = -(my);
      }
   }

   /**
    * sets a list as the owner of the sprite.  Since a reference to the list
    * is kept in the Sprite, a Sprite may only have a single owner.
    */
   public void setOwner(SpriteList sl)
   {
      owner = sl;
   }

   /**
    * Returns the class name / hash0code for the object.
    */
    public String toString()
    {
        return "Sprite@" + hashCode();
    }

    /**
     * paints the sprite to Graphics object g.
     */
    public abstract void paint(Graphics g);

    /**
     * returns the sprites bounding Rect.
     */
    public Rect getBoundingRect(){return boundingRect;}
}