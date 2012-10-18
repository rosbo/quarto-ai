Quarto AI
=========
Project 2 of the course IT3105 - Artificial Intelligence Programming taught at NTNU on fall 2012

How to use
-----------
To build the jar type the following maven command:

    mvn assembly:single

To get the parameters

    java -jar archive.jar

To test with the game master

    java -jar gm.jar "-jar quarto-canita-jar-with-dependencies.jar -p novice -p human -g" "-jar quarto-canita-jar-with-dependencies.jar -p human -p novice -g" -q -n 10
