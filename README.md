Find-The-Treasure
=================
My own attempt at making a procedurally generated map (with the goal of finding a treasure to have a "story").

How to play
===========
Just double-click dist/FindTheTreasure.jar.
The game should pop up at the start with a brief introduction. You can also select the difficulty level here.

Click anywhere to dig. This will dig a square of 5x5 blocks, lowering every block in that square by 1 level. 
There are 8 levels in total, from darkest rock to surface (grass or other).

Procedural generation
=====================
The procedural generation uses a self-designed function to decide the odds that a block of type A (e.g. grass) will have a block of type B (e.g. sand) next to it.
It's a modified matrix function that uses certain thresholds to decide the block type for a given position.
It also randomly decides the height level above rock (1-4 levels of dirt).

Next block algorithm
--------------------
There are base odds of getting a certain block type next to another block type (basic matrix function, with several 0 values)
The odds for the surrounding blocks are added, and based on that the next block type is decided.
It makes for a relatively smooth transition (though certainly not perfect).
