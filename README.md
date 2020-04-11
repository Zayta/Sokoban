<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-131292087-4"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-131292087-4');
</script>

# Implementation

The main file/screen controller is Game.java. 

Singleton pattern is used to create a single instance of UserData and ensure that only one copy of UserData is used per game.

State pattern is used for transitioning between game screens, with Game.java being the screen manager.

Factory pattern is used to create the entities in the game so that there is a centralized entity creator and the created units are correctly placed and reusable.

Command pattern is used to store moves history in sokoban puzzle game to support undo-moves

