# Rodent&#39;s Revenge with improved AI

## The Game

Rodent&#39;s Revenge is a puzzle video game, created in 1991 by Christopher Lee Fraley. It was distributed as part of the Microsoft Entertainment Pack. The player takes on the role of a mouse, with the objective being to trap cats by pushing blocks around, whilst avoiding obstacles.

## Game Design

- Graphics used are based on Java Input/Output for images and key listeners. Images are placed on a two dimensional array.
- Game Class keeps track of all pieces by creating an array of objects, keeping track of score and pieces and game status (cats trapped or mouse dead)
- Game Piece objects can return their coordinates, move them, and check if a spot is available; holes stop mouse from moving for 5 moves; cheese gains a life; trap takes a life;
- Mouse capable of interacting with other game pieces and being controlled by listeners; interaction is used by the addition of vectors to the object being moved away.
- Moveable wall must be able to recursively move other movable walls through addition of vectors

## Artificial Intelligence Types Used

**Simulated annealing** is the AI technique used to enhance the Cat&#39;s ability to find the user Mouse. The concept is used to avoid crashing into walls when finding the minimum distance ( **local optimum** ) and find the **global optimum** of the mouse&#39;s location

**Annealing** or the slow reduction of temperature to remove internal stresses of test tubes is the fundamental process this technique is based on.  Initial high temperatures are introduced then slowly decreased, reducing entropy or randomness of the variable as time goes on. But why introduce randomness in the first place? The randomness of the hot to cool process allows all faults in the glass to fill in the cracks. Metaphorically, simulated annealing is similar: slowly cool down to allow an initial variety of guesses and reach a globally optimum solution rather than a local one.

The probability of choosing the random solution is based on an unintuitive formula of bestsolution−randomsolutioncurrenttemperaturee
. As we can see, the worse the random solution, the less likely we are to pick it though it is still possible. The temperature, which decreases over time according to the **cooling rate** ( thus called annealing), reduces the randomness as well.

In context of the game, the Cat is given all available spaces to choose from randomly to reach the mouse. When not choosing the random space, the distance-minimizing move is chosen. This distance is calculated by **Manhattan distance** (x,y distance) rather than Euclidean distance to improve accuracy. The initial high temperature is set and begins decreasing per move. Initially the cats are looking sporadically for the mouse, searching every crevice of the map; a few moves down the road when the mouse is surrounded, the low temperature allows for the cats to converge upon the mouse.
