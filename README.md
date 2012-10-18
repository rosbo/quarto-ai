Quarto AI
=========
Project 2 of the course IT3105 - Artificial Intelligence Programming taught at NTNU on fall 2012

How to use
-----------
To build the jar type the following maven command:

    mvn assembly:single

To see the usage information

    java -jar quarto-canita.jar

To test with the game master

    java -jar gm.jar "-jar quarto-canita.jar -p novice -p human -g" "-jar quarto-canita.jar -p human -p novice -g" -q -n 10
