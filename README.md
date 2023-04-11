Project name: 
Kwadraciki


Technology used:
Java 16


How to run the program?
Unpack the project and run src/main.java


Project description:

This is an educational game aimed towards children, eldelrly as well as people learning how to use computers by the name "Kwadraciki"
The aim of this project was to create fun and simple tool to teach how to use a computer mouse.
As this game was my university project it is fully in polish.
The mechanics are fairy simple, though, and there are only 3 buttons to use, so the language barrier shouldn't be much of a problem.

The aim of the game is to pull all the squares appearing on the screen to the containers of corresponding color in the bottom of the screen
within given time limit using the computer mouse.
- To start the game click the "START" button.
- To stop the game click "STOP" button.
- Use spinner to change the level. If the game is currently running you have to stop it first in order to change level.
- You cannot pause or resume the game.
- To exit click the "WYJSCIE" button.
- Label "CZAS" shows how much time you have left to finish the level.
- Label "POCZĄTKOWY POZIOM" shows the number of level you started playing first.
- Label "OBECNY POZIOM" shows your current level.

There are 5 levels, but you are free to add your own in the Poziomy class.
It is reccomended to start numbering additional levels form 6 onward. 
To add the level insert the following into the switch inside the method by the same name:

    case LEVEL_NUMBER:

      tlo = new ImageIcon("PATH_TO_THE_BACKGROUND_PHOTO");

      GenerujKwadrat.ileKwadratow = NUBER_OF_SQUARES_IN_THE_LEVEL;       
      CountdownTimer.czas = TIME_IN_SECONDS;               

      dostepneKolory.add(FIRST_COLOR); 
      dostepneKolory.add(SECOND_COLOR);
      dostepneKolory.add(THIRD_COLOR);
      dostepneKolory.add(FOURTH_COLOT);

      break;
  
as well as change the maxPoziom variable in line 21 to the highest LEVEL_NUMBER (i.e. if you added level 6 change the value to maxPoziom = 6)

Good luck :)
