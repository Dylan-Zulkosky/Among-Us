//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Among Us Game Creation For Beginners
// Course: CS 300 Fall 2023
//
// Author: Dylan Zulkosky
// Email: dzulkosky@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: None
// Partner Email: None
// Partner Lecturer's Name: None
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: I used the GeeksforFreaks article
//////////////// (https://www.geeksforgeeks.org/find-two-rectangles-overlap/) to help with my coding
//////////////// to overlap each Amogus character
// I used the Amogus class
//////////////// (https://cs300-www.cs.wisc.edu/wp/wp-content/uploads/2020/12
// /fall2023/p02/javadocs/Amogus.htm) to help with getting numbers for the random height and width
// the Amogus characters are placed and setting up the entire project with the setup and draw
// methods
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import processing.core.PImage;

/**
 * This class creates a among us game that lets you move each character and have up to a certain 
 * amount of players in the game
 * 
 * @author Dylan Zulkosky
 */
public class SpaceStation {

  // private static int bgColor;
  // private static PImage sprite;
  private static PImage background;

  // Sets constant for NUM_PLAYERS to 8
  final private static int NUM_PLAYERS = 8;

  // Declaring a perfect array named players
  private static Amogus[] players;

  private static int impostorIndex;


  /**
   * This main method starts the application which runs the entire game using code from other 
   * methods in this project
   * 
   * @param args - unused
   */
  public static void main(String[] args) {
    // begins the GUI program’s execution
    Utility.runApplication();

  }

  /**
   * Causes the Amogus characters to be drawn to the game window.
   */
  public static void draw() {
    // Utility.background(bgColor);
    // sets the background color of the window
    Utility.image(background, 600, 500);
    // Utility.image(sprite, 600, 400);

    // Prints out the Amogus characters onto the screen if 'a' is pressed
    // Maximum amount of characters is 8
    for (int i = 0; i < players.length; i++) {
      if (players[i] != null) {
        players[i].draw();
        if (isMouseOver(players[i])) {
          // Print a message and the index of the Amogus when the mouse is over it
          System.out.println("Mouse is over Amogus at index " + i);
          for (int j = 0; j < players.length; j++) {
            if (i != j && players[j] != null && overlap(players[i], players[j])
                && players[i].isImpostor()) {
              // After calling this method, this Amogus will be dead.
              players[j].unalive();
            }
          }
        }
      }
    }
  }

  /**
   * This sets up the background, creates a random impostor, and creates the total number of players
   * in the array called "players".
   */
  public static void setup() {
    // bgColor = Utility.randGen.nextInt();

    // sprite = Utility.loadImage("images" + File.separator + "sprite1.png");
    background = Utility.loadImage("images" + File.separator + "background.jpeg");

    // Initializes each player in the array "players"
    players = new Amogus[NUM_PLAYERS];

    // Creates random color (1,2, or 3) to assign random character color
    int randomColor = Utility.randGen.nextInt(3) + 1;

    players[0] = new Amogus(randomColor);

    // Randomly generates a impostor
    impostorIndex = Utility.randGen.nextInt(NUM_PLAYERS) + 1;
    System.out.println("Impostor index: " + impostorIndex);

  }

  /**
   * This checks to see if the user pressed the letter 'a' on the keyboard to add more players into
   * the game.
   * It also randomly generates where each character spawns on the window screen, what color it is, 
   * and which one is the impostor.
   */
  public static void keyPressed() {
    // Checks to see if the user pressed 'a'
    // returns the char representation of the key being pressed on the keyboard
    if (Utility.key() == 'a') {
      for (int i = 0; i < players.length; i++) {
        if (players[i] == null) {
          // Creates random width location for Amogus character
          float randomX = Utility.randGen.nextInt(Utility.width());
          // Creates random height location for Amogus character
          float randomY = Utility.randGen.nextInt(Utility.height());
          int randomColor = Utility.randGen.nextInt(3) + 1;
          // Reports whether this Amogus is an impostor
          boolean isImpostor = (i + 1) == impostorIndex;
          players[i] = new Amogus(randomColor, randomX, randomY, isImpostor);
          break;
        }
      }
    }
  }

  /**
   * This checks to see if the users mouse is over the amogus characters on the screen.
   * 
   * @param amogus - the among us character
   * @return - true is the mouse is over the character and false otherwise
   */
  public static boolean isMouseOver(Amogus amogus) {
    // returns the current x coordinate of the cursor in the application window
    float mouseX = Utility.mouseX();
    // returns the current y coordinate of the cursor in the application window
    float mouseY = Utility.mouseY();

    // Access the current x-coordinate of this Amogus
    float amogusX = amogus.getX();
    // Access the current y-coordinate of this Amogus
    float amogusY = amogus.getY();

    // Provides the current image (dead or alive) of this Amogus in the correct color
    PImage amogusImage = amogus.image();

    // Calculate the boundaries of the Amogus image based on its center (x, y) coordinates
    // Divide by 2 to account for position of Amogus being in the center
    // (.width) returns the width of the application window in pixels
    // (.height) returns the height of the application window in pixels
    float amogusLeftEdge = amogusX - amogusImage.width / 2;
    float amogusRightEdge = amogusX + amogusImage.width / 2;
    float amogusTopEdge = amogusY - amogusImage.height / 2;
    float amogusBottomEdge = amogusY + amogusImage.height / 2;

    // Check if the mouse coordinates are within the boundaries of the Amogus image (excluding
    // edges)
    if (mouseX >= (amogusLeftEdge) && mouseX <= (amogusRightEdge) && mouseY >= (amogusTopEdge)
        && mouseY <= (amogusBottomEdge)) {
      // True if mouse is over Amogus
      return true;
    } else {
      // False if mouse in not over Amogus
      return false;
    }
  }

  /**
   * This sees if the player clicked on the among us character and starts dragging them around the
   * screen.
   */
  public static void mousePressed() {
    // Loop through the players array to check if the mouse is over an Amogus character
    for (int i = 0; i < players.length; i++) {
      if (players[i] != null) {
        if (isMouseOver(players[i])) {
          // Sets the dragging status of this Amogus to true and begins recording mouse movements
          players[i].startDragging();
        }
      }
    }
  }

  /**
   * This method stops the player dragging the character after they let go of the mouse
   */
  public static void mouseReleased() {
    // Loop through the players array to call stopDragging() for all not null Amogus objects
    for (int i = 0; i < players.length; i++) {
      if (players[i] != null) {
        // Sets the dragging status of this Amogus to false
        players[i].stopDragging();
      }
    }
  }

  /**
   * This method sees if 2 of the amogus characters are overlapping
   * 
   * @param amogus1 - first amogus character
   * @param amogus2  - second amogus character
   * @return true is they are overlapping and false otherwise
   */
  public static boolean overlap(Amogus amogus1, Amogus amogus2) {

    // Access the current x-coordinate of this Amogus1
    float x1 = amogus1.getX();
    // Access the current y-coordinate of this Amogus1
    float y1 = amogus1.getY();
    // Access the current x-coordinate of this Amogus2
    float x2 = amogus2.getX();
    // Access the current y-coordinate of this Amogus2
    float y2 = amogus2.getY();

    // Obtains height and width for each Amogus
    float width1 = amogus1.image().width;
    float height1 = amogus1.image().height;
    float width2 = amogus2.image().width;
    float height2 = amogus2.image().height;

    // Divide by 2 to account for position of Amogus being in the center
    boolean isOverlapping = !(x1 + width1 / 2 < x2 - width2 / 2 || x1 - width1 / 2 > x2 + width2 / 2
        || y1 + height1 / 2 < y2 - height2 / 2 || y1 - height1 / 2 > y2 + height2 / 2);

    // Returns true if they are overlapping and false if they are not
    return isOverlapping;

  }

}
