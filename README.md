# Survival Game

"Survival Game" is my final project for Professor Steven Brandt's CSC 1351 course.

The requirements for this project was that it;

1. Use the professor's [BasicGraphics](https://github.com/stevenrbrandt/BasicGraphics) library
2. Be 1000 lines in length, as dictated by the professor's line checker tool which can be found [here](https://csc1351.cct.lsu.edu/)

**The full project requirements can be found [here](https://www.cct.lsu.edu/~sbrandt/csc1351/), aswell as the full course syllabus.**



At the top level, this game in **intended** to be a sandbox-based survival game, however due to time and project constraints (and programming knowledge), 
I was unable to get the sandbox aspect functioning. 

However, it is still a decently fun scoller-wave game.

Overall, this project helped me increase my knowledge and experience in video game & object-oriented programming.

---

<ins>Controls</ins>:
<ul>
<li>WASD or Arrow Keys for movement</li>
<li>Left Click for attack</li>
<li>Escape to return to Menu</li>
</ul>

<ins>Features</ins>:
<ul>
<li>Main menu, with random splash screen, logo, start & exit buttons</li>
<li>Randomly generating background</li>
<li>2D perspective</li>
<li>Sprite animation</li>
<li>Randomly spawning enemies</li>
<li>Various attack patterns</li>
<li>Wave-based</li>
<li>Health boosts as Life Crystals</li>
</ul>

<ins>Bugs</ins>:
<ul>
<li>
Collision only <b>sometimes</b> works as expected, I believe it to be an issue with the collision method using classes instead of instances of that class, which 
cause multiple collisions of the same class to not register.
<ul>
<li>
<i>This has now been partially fixed, by just avoiding using collision altogether. Not optimal but I had to get this turned in on time.</i>
</li>
</ul>
</li>
<li>
Trees may only half-load in, no clue why.
<ul>
<li><i>It may be an issue with clipping from the Background.</i></li>
</ul>
</li>
<li>
For some reason, at the right edge of the map, all sprites of subclass <b>Entity</b> disappear from the screen.
<ul>
<li>
<i>Likely an issue with sizing of the SpriteComponent, Scene, or MainCard.</i>
</li>
</ul>
</li>
<li>
If you land on a platform and walk off or walk off the ground, you still float in the air. This is due to
how I program gravity, however fixing this would add additional lag and this program
is already laggy enough.
</li>
</ul>

---

*This project contains assets from Terraria, owned by Re-Logic, used soley for educational and non-commerical purposes. 
No profit is being made from this project and all rights remain to their respective owners.*
