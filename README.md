# Software Development Term Project

## Overview 
#### This project was the capstone for Fall 2020 Software Development. It employs an MVC design for the GUI elements and necessary data to run the game. Setup for the GUI and all aspects of the "View" portion were provided by the instructor; students were tasked with implementing control logic, core gameplay systems, and the data model of the game while maintaining loose coupling between the three portions of the application. 

## Tank Game
#### The game supports a single level, with one player tank and a variable amount of AI tanks. It will continue to run until either all enemies are destroyed or the player is. 

## Functionality
#### Tanks, player or AI, can withstand 5 shell collisions before being destroyed and removed from the game. Entity class hierarchy can be extended to add support for different shell/tank types. Game also supports destructible environments as well as custom tile maps, so alternative level layouts are easily achievable.  