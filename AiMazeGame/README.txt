The Game
I have implemented a start menu for this game. The start menu allows the user to select a difficulty level. Each 
difficulty level has a set number of black spiders and red spiders. The red spiders follow a random path, with the 
black spiders using the A star search to find and kill the player character.

I have abstracted the main game into a number of classes. This follows the core principles of OOP. All of these
 classes are found within the package ie.gmit.sw.ai. The class GameRunner, is where most of the action is happening. 
 This class implements a cocktail of Fuzzy Logic, Neural Network and Heuristic Searches.
 
 The template class that was given to us had to be chopped up and rearranged. This was to use the heuristic searches. The
 reason for this is that the searches needed to use a complex type ("Node"), apposed to a basic type (char) to navigate the maze.
 
How to Play
To play the game, use the arrow keys to move, and the 'z' character to view the map. The character has 3 pickups. These are Health Points,
Armour points, and a Weapon. The Health and Armour are incremented in units of 50 and there are 3 Weapons; Sword, Bomb and H-Bomb, with the sword
being the weakest and the H-Bomb being the strongest. There is also Help points along the way. When a player finds these Help points, a route
to the goal node will be displayed as a track of Coins. This track will also be seen by a blue line on the map.

The character must avoid the spiders or be prepared to fight. The better equipped the character is, the better chance of survival. If a spider dies,
their corpse will remain in the position of where they died. The game generates 1 exit, as far from the player as possible. If the player spawns in
the bottom left corner, the exit will be in the top right corner. The spiders that patrol the maze are fully threaded, they move independently of
each other (see the Spider class). The black spiders use A Star Heuristic search to find the player. This is updated every time the player moves.
 
Fuzzy Logic
I have implemented a simplistic fcl file for the fuzzy logic. The fuzzy logic is used to control a fight between the character and a spider,
and decide its outcome depending on the given variables (health, armor and weapon strength). The outcome is either the player dies, or the spider dies.
The death percentage is calculated and displayed in the console after the battle.

Neural Network
The neural network in this game does not work as well as intended. The idea is, depending on how strong the player is (Health and weapon),
and depending on the anger level of the spiders (the more spiders the player kills, the angrier the remaining spiders are), the spiders will
behave in a certain way. For some reason that I cannot figure out, the neural network gives me incorrect outputs most of the time, although the training
shows that it only has a slight chance of error.

Heuristic Search
The A Star search works extremely well. It is used for the spiders search for the players, along with being used in the Help points.
The other searches I have included in the package 'ie.gmit.sw.ai.searchAlgorithms' also work, but work very rarely. They all give a certain path
but that path is wrong most of the time. For this reason, I have not included them into the game. If you wish to test these searches, 
change line 304 in GameRunner.

Issues
- Other searches do not work as intended
- Neural Network not accurate
- Game crashes relatively frequently (due to to many threads being ran & program being stuck in an infinite loop)
- Fuzzy logic could be tweaked