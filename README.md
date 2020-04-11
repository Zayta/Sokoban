<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-131292087-4"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-131292087-4');
</script>

Main code files are in the core folder

# How to run:
If you have gradle installed,
gradlew desktop:run


# Credits:
Sokoban levels taken from https://www.sourcecode.se/sokoban/levels
Crates taken from Kenney's Sokoban pack [https://www.kenney.nl/assets/sokoban] and modified .
Floor tiles (modified) and UI Buttons were taken from OpenGameArt https://opengameart.org/content/ui-iconsbuttons-set
All other assets are original.

# Implementation

The main file/screen controller is Game.java. 

Singleton pattern is used to create a single instance of UserData and ensure that only one copy of UserData is used per game.

State pattern is used for transitioning between game screens, with Game.java being the screen manager.

Factory pattern is used to create the entities in the game so that there is a centralized entity creator and the created units are correctly placed and reusable.

Command pattern is used to store moves history in sokoban puzzle game to support undo-moves

