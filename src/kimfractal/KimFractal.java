/*
 * Daniel Kim
 * January 7, 2022
 * This program use recursion to draw a fractal, snowflake. The user can enter the level of fractal they want to display.
 * The user also can choose whether or not to slow down the drawing of snow flake in order to see in more detail how it is formed.
 */
package kimfractal;

import TurtleGraphics.*;
import java.awt.Color;
import javax.swing.JOptionPane;


public class KimFractal {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // set the size of the snow flake 
        double size = 400.0;
        
        //prompt user to enable/disable slow mode
        //if the user press YES option -> mode = 0
        //if the user press NO option -> mode = 1
        //if the user click close (X button on right top corner) -> mode = -1
        int mode = JOptionPane.showConfirmDialog(null, "Slow Mode?", "mode", JOptionPane.YES_NO_OPTION);
        
        //exit the program if mode is -1 (which means the user choose to close)
        if(mode == -1) 
        {
            System.exit(0);
        }
        
        //declare and initialize level and previousLevel variables
        int level = 0;
        int previousLevel = 0;
        
        //promt the user to enter the level of snow flake 
        try
        {
            level = Integer.parseInt(JOptionPane.showInputDialog("What level will the SnowFlake be?\n1~10 recommended\n(Or type 0 or below to exit)"));
        }catch(NumberFormatException e) //if the user enter non-integer value
        {
            System.out.print(e); //print the error
        }
        
        //instantiate StandardPen object to draw the snowflake
        StandardPen p = new StandardPen(600,600);
        //set width of the StandardPen 
        p.setWidth(1);
        //move the initial pen position in order to locate the snowflake middle of the page.
        p.up();
        p.setDirection(-90); //set the direction toward the bottom
        p.move((int)Math.round(1/6.0 * Math.sqrt(3) * size)); //move the pen (the distance depends on the size of the snowflake)
        p.setDirection(0); //set the direction toward right side
        p.move(-size/2); //move the pen (the distance depends on the size of the snowflake)
        p.down(); //pen down
        
        
        //while loop until the user does not enter a positive integer
        while(level > 0)
        {
            //set the pen color as black
            p.setColor(Color.black);
            
            //Slow mode
            if(mode == 0)
            {
                //(level-1) times loop - it depends on the level value that the user entered
                for(int x = 1; x < level; x++)
                {
                    p.setColor(Color.black); //set the pen colour back as black
                    drawSnowFlake(p, x, size); //draw snow flakes of level 1, 2, 3, ... (level)
                    Thread.sleep(1500); //wait 1.5sec before erasing the snowflake
                    //draw the same snowflake with white pen in order to erase it
                    p.setColor(Color.white); 
                    drawSnowFlake(p, x, size);
                }
                p.setColor(Color.black); //set the pen colour back as black
                drawSnowFlake(p, level, size); //draw the final snowflake
            }
            else if(mode == 1) //original mode
            {
                drawSnowFlake(p, level, size); //draw snow flake
            }
            
            previousLevel = level; //save current level value by the previousLevel variable
            //let the user to select slow mode or original mode
            
            mode = JOptionPane.showConfirmDialog(null, "Slow Mode?", "mode", JOptionPane.YES_NO_OPTION);
            //if mode is -1 (which means the user choose to close)
            if(mode == -1) 
            {
                break; //end the loop
            }
            
            
            try
            {
                //prompt user for the new level value
                level = Integer.parseInt(JOptionPane.showInputDialog("What level will the SnowFlake be?\n1~10 recommended\n(Or type 0 or below to exit)"));
                //erase previous snowflake by drawing it again with white pen and saved previousLevel value
                p.setColor(Color.white);
                drawSnowFlake(p, previousLevel, size); 
            }catch(NumberFormatException e) //if the user enter non-integer value for the level
            {
                System.out.print(e); //print the error
                break; //end the loop
            }
        }
        System.exit(0); //exit the whole program after escaping the loop
    }
    
    /**
     * This method draws the three fractal sides to make one snowflake
     * @param p - StandardPen to draw
     * @param level - level of the fractal(snowflake)
     * @param size - size of the snowflake
     */
    private static void drawSnowFlake(StandardPen p, int level, double size) 
    {
        for(int i = 0; i < 3; i++) //loop three times because a triangle has three sides
        {
            snowFlakeSide(p, level, size); //draw one side
            p.turn(120); //turn 120 degree 
        }
    }
    
    /**
     * recursive method that draws one side of snowflake triangle
     * @param p - StandardPen to draw
     * @param level - level of the fractal(snowflake)
     * @param size - size of the snowflake
     */
    private static void snowFlakeSide(StandardPen p, int level, double size) 
    {
        if (level == 1) //base case 
        {
            p.move(size); //draw basic line of the snowflake (size value varies by the level of fractal)
        }
        else //recursive case
        {
            //divide a line by 3 and replace 1/3 line in the middle by a new horn
            
            //left side 1/3 line
            snowFlakeSide(p, level-1, size/3.0);
            //left side of new curve(triangle) in the middle of the line
            p.turn(-60);
            snowFlakeSide(p, level-1, size/3.0);
            //right side of new curve(triangle) in the middle of the line
            p.turn(120);
            snowFlakeSide(p, level-1, size/3.0);
            //right side 1/3 line
            p.turn(-60);
            snowFlakeSide(p, level-1, size/3.0);
        }
    }
    
}
