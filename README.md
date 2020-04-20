
   <head>
          <title>NUR_Nighters</title>
          <meta http-equiv="content-type" content="text/html; charset=UTF-8">
          <meta id="gameViewport" name="viewport" content="width=device-width initial-scale=1">
          <script src="html/distribution/soundmanager2-setup.js"></script>
          <script src="html/distribution/soundmanager2-jsmin.js"></script>
   </head>

   <body>
          <a href="javascript:%7B%20window.__gwt_bookmarklet_params%20%3D%20%7B'server_url'%3A'http%3A%2F%2Flocalhost%3A9876%2F'%7D%3B%20var%20s%20%3D%20document.createElement('script')%3B%20s.src%20%3D%20'http%3A%2F%2Flocalhost%3A9876%2Fdev_mode_on.js'%3B%20void(document.getElementsByTagName('head')%5B0%5D.appendChild(s))%3B%7D">&#8635;</a>
          <div align="center" id="embed-html"></div>
          <script type="text/javascript" src="html/html.nocache.js"></script>
   </body>

   <script>
          document.getElementById('gameViewport').setAttribute('content',
             'width=device-width initial-scale=' + 1/window.devicePixelRatio);

          function handleMouseDown(evt) {
            evt.preventDefault();
            evt.stopPropagation();
            evt.target.style.cursor = 'default';
            window.focus();
          }

          function handleMouseUp(evt) {
            evt.preventDefault();
            evt.stopPropagation();
            evt.target.style.cursor = '';
          }
          document.getElementById('embed-html').addEventListener('mousedown', handleMouseDown, false);
          document.getElementById('embed-html').addEventListener('mouseup', handleMouseUp, false);
   </script>
   <script>
          window.onkeydown =
              function(event) {
                  // prevent all navigation keys except the space key
                  if(event.keyCode >= 33 && event.keyCode <= 40){
                      event.preventDefault();
                      return false;
                  }
              };
   </script>

[View Code](https://bitbucket.org/zayta9348/sokoban/src/sokoban/)

# Run as a libGDX project as described [here](https://libgdx.badlogicgames.com/documentation/gettingstarted/Running%20and%20Debugging.html#html)

# Credits:

* Sokoban levels taken from the [sokoban archive](https://www.sourcecode.se/sokoban/levels)

* Crates taken from [Kenney's Sokoban pack](https://www.kenney.nl/assets/sokoban) and modified .

* Floor tiles were taken from [princess-phoneix's virtual tournament room tileset on Deviantart](https://www.deviantart.com/princess-phoenix/art/Virtual-tournament-room-tileset-656505643) and modified.

* UI Buttons were taken from [pix3Icat's UI Icons/Buttons Set on OpenGameArt](https://opengameart.org/content/ui-iconsbuttons-set)

* Game backgrounds and parallaxes were taken from [KokoroReflections Three Sky Backgrounds - Day, Sunset/Sunrise,Night on OpenGameArt](https://opengameart.org/content/three-sky-backgrounds-day-sunsetsunrise-night) (https://creativecommons.org/licenses/by/4.0/)

* All other assets are original.

# Implementation

The main file/screen controller is Game.java. 

Singleton pattern is used to create a single instance of UserData and ensure that only one copy of UserData is used per game.

State pattern is used for transitioning between game screens, with Game.java being the screen manager.

Factory pattern is used to create the entities in the game so that there is a centralized entity creator and the created units are correctly placed and reusable.
Each entity is created based on the EntityTemplate.java

Command pattern is used to store moves history in sokoban puzzle game to support undo-moves

