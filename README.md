# Gameplay
## Goals
* Fuel the indicated lanterns/compartments by turning blobs into flares


### Entities
* Souls- have color and hp.
* Essence- has color and state. state: blob, flare. 
  * blob: 
   if collide with soul of same color: turns into flare
   if collide with soul of different color: changes color
  * flare:
  if collide with soul of same color: turns back to blob
  if collide with soul of different color: burns the soul (decreases entity hp)
* Monster- damages soul upon contact
* Blocks: hinder movement
  
### Player Controls
#### Keyboard
Use arrow keys to move playable characters.
#### Phone/Tablet
Swipe to move.
*Swipe up and all playable characters move up, down and all down, etc*

[See progress log](https://nurexperiments.home.blog/log/ "NUR Experiment GDX Developer's Log")

[Intro](Intro.html)

