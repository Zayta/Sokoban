
Main code files are in the core folder

# Run as a libGDX project

# Credits:

* Sokoban levels taken from the [sokoban archive] (https://www.sourcecode.se/sokoban/levels)

* Crates taken from [Kenney's Sokoban pack] (https://www.kenney.nl/assets/sokoban) and modified .

* Floor tiles were taken from [princess-phoneix's virtual tournament room tileset on Deviantart](https://www.deviantart.com/princess-phoenix/art/Virtual-tournament-room-tileset-656505643) and modified.

* UI Buttons were taken from [pix3Icat's UI Icons/Buttons Set on OpenGameArt] (https://opengameart.org/content/ui-iconsbuttons-set)

* Game backgrounds and parallaxes were taken from [KokoroReflections Three Sky Backgrounds - Day, Sunset/Sunrise,Night on OpenGameArt](https://opengameart.org/content/three-sky-backgrounds-day-sunsetsunrise-night) (https://creativecommons.org/licenses/by/4.0/)

* All other assets are original.

# Implementation

The main file/screen controller is Game.java. 

Singleton pattern is used to create a single instance of UserData and ensure that only one copy of UserData is used per game.

State pattern is used for transitioning between game screens, with Game.java being the screen manager.

Factory pattern is used to create the entities in the game so that there is a centralized entity creator and the created units are correctly placed and reusable.

Command pattern is used to store moves history in sokoban puzzle game to support undo-moves

