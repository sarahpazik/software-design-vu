# Assignment 3

Maximum number of words for this document: 18000

**IMPORTANT**: In this assignment you will fully model and impement your system. The idea is that you improve your UML models and Java implementation by (i) applying (a subset of) the studied design patterns and (ii) adding any relevant implementation-specific details (e.g., classes with “technical purposes” which are not part of the domain of the system). The goal here is to improve the system in terms of maintainability, readability, evolvability, etc.    

**Format**: establish formatting conventions when describing your models in this document. For example, you style the name of each class in bold, whereas the attributes, operations, and associations as underlined text, objects are in italic, etc.

### Summary of changes of Assignment 2
Author(s): Sarah

Provide a bullet list summarizing all the changes you performed in Assignment 2 for addressing our feedback.

* Made each of our actions into separate methods in the Action class to simplify out previously long and complex switch statement
* Added an Inventory class which holds everything relevant to a player's inventory (printInventory, addToInventory, etc) in order to keep our code organized and concise
* Updated class diagram to properly represent JSON objects 
* Added association names to the class diagram 
* Added the chat room feature (Added ChatClient class) so that you can communicate with other players (use command "chat")
* Implemented time limit feature to make the game slightly more complex
* Added obstacles to the game using the Obstacle class to make the game more complex
* Implemented persistence to allow a user to save their progress 

Maximum number of words for this section: 1000

### Application of design patterns
Author(s): `name of the team member(s) responsible for this section`

`Figure representing the UML class diagram in which all the applied design patterns are highlighted graphically (for example with a red rectangle/circle with a reference to the ID of the applied design pattern`

For each application of any design pattern you have to provide a table conforming to the template below.

| ID  | DP1  |
|---|---|
| **Design pattern**  | Decorator |
| **Problem**  | A paragraph describing the problem you want to solve |
| **Solution**  | A paragraph describing why with the application of the design pattern you solve the identified problem |
| **Intended use**  | A paragraph describing how you intend to use at run-time the objects involved in the applied design patterns (you can refer to small sequence diagrams here if you want to detail how the involved parties interact at run-time |
| **Constraints**  | Any additional constraints that the application of the design pattern is imposing, if any |
| **Additional remarks**  | Optional, only if needed |

| ID  | DP2  |
|---|---|
| **Design pattern**  | Command |
| **Problem**  | A paragraph describing the problem you want to solve |
| **Solution**  | A paragraph describing why with the application of the design pattern you solve the identified problem |
| **Intended use**  | A paragraph describing how you intend to use at run-time the objects involved in the applied design patterns (you can refer to small sequence diagrams here if you want to detail how the involved parties interact at run-time |
| **Constraints**  | Any additional constraints that the application of the design pattern is imposing, if any |
| **Additional remarks**  | Optional, only if needed |

Maximum number of words for this section: 2000

## Class diagram									
Author(s): Sarah

This chapter contains the specification of the UML class diagram of your system, together with a textual description of all its elements.

`Figure representing the UML class diagram`

The **Player** interface represents the user, i.e. the person moving through the game. The *getCurrentRoom* method returns the player's current location, and the *setCurrentRoom* method changes the player's location to the given room. The player class is associated with the Room class because it needs to be able to access the player's current location. It is also associated with the Item class, because it needs to keep track of the player's inventory. The association with the Action class is because a Player needs to be able to perform actions in order to change their location or inventory. 

The **RegularPlayer** class represents a regular player who has not yet found the item needed to get rid of the time limit. Their *name* is a string object that contains whatever name the user chooses. Their *inventory* is a list of Item objects, containing any objects that were picked up but not yet put down. This way, we keep a list of all items the player can use at a given time. The *currentLocation* is a Room object that indicates which room the player is in at a given time. *IsChatting* is a boolean that indicates whether the user has stated using the chat room feature or not. This class implements all of the methods from the Player interface because it is one of two types of players in the game.

The **PlayerDecorator** class is what we used to implement the Decorator Pattern. It also implements the Player interface, because TimeDecoratedPlayer, which extends PlayerDecorator, is the other type of player in our game.

The **TimeDecoratedPlayer** class represents a player who has found the broken clock which gets rid of the time limit in the game. This player now has unlimited time to reach the goal location. It has the same attributes and methods as RegularPlayer. 

The **Action** class represents all of the actions and movements a user can perform in the game. The *commandName* field is a string that the user inputs in order to perform an action. The *item* is the Item object that the action is being used on. The *room* is the Room object that the item is located in. The Action class is connected to the Item class because each action needs to be performed on a specific item. The same holds for the Obstacle class; each obstacle will have actions that can be used on them. It is also associated with the ChatClient class, because the chat method needs access to the functionality given in the ChatClient class.

The **Command** interface has an execute() function that is implemented by each of the following command classes.

The **Move**, **Pick**, **Use**, **Inspect**, **Help**, **Look**, and **Chat** classes all implement the Command class and override the execute method, which is then used in the Action class when executing each command. 

The **Item** class represents the items in the game that can be used by the player. The *name* field holds the name of the item as a string, the *location* field holds the Room that the item is in, and *usage* holds a string that explains what the item is used for. The *held* field represents whether or not the item is currently in the user's inventory. The *used* field represents whether or not the user has already used the item. The *getNameFromItem* method returns the name of the item and the Usage method returns the item's purpose, i.e. how the player can use it. *GetUsage* returns a string with what the item is used for. *HoldItem* changes the item's state to held. *isHeld* simply returns a boolean indicating whether the item is currently held or not. It is connected to the Room class because each room can have an unlimited number of items, and the Room class needs to access that information.

The **Obstacle** class represents any obstacles in a room, such as something blocking a door. The *itemNeeded* field contains what item the user needs to use in order to overcome the obstacle. The *location* is the room the obstacle is located in. *RoomsBlocked* is a list of the rooms you cannot enter because of the obstacle. *Description* is an explanation of the obstacle and how to overcome it, and *clearMessage* is the message a user will see once they have overcome it. The *cleared* field contains whether or not the player has overcome the obstacle. The *clearObstacle* method clears the obstacle from the room (this will be used when the player uses the proper command/item combination). *GetItemNeeded* returns the item the player needs to use to overcome the obstacle, *getLocation* returns the room it is in, *getRoomsBlocked* returns the rooms that are blocked by the obstacle, and *getDescription* returns the description of the obstacle. *isCleared* returns a boolean indicating whether the player has overcome the obstacle, and *blocksRoom* returns whether or not the obstacle is currently blocking a room. The Obstacle class, like the Item class, is associated with the Room class because each Room can have unlimited obstacles, and it needs to keep an inventory of those.

The **Setting** class represents the place the user is in, and contains all of the locations a player can go. The *rooms* field is a list of Room objects that holds every room that exists in the current game. The Setting class is composed with the Room class, because each setting can have unlimited rooms, and a room cannot exist without a setting. 

The **Room** class represents an individual room within the setting of the game. The *name* field is a string stating the name of the room. The *items* hashmap holds each Item object that is in the given room. The same holds for *obstacles*, except it contains Obstacle objects. *NextRooms* is a hashmap of rooms to which you can get to from the current room. The *script* is the string that the user will read when they enter the given room. The *getItemFromName* method returns the item object based on the name, and the *getNextRoomFromName* does the same, just for room objects. The *getNextRooms* method returns a hashmap containing all of the possible rooms you can get to from the current room. The *getRoomName* method returns the name of the room, and *getScript* returns the script. *RemoveItemsFromRoom* allows you to take an item out of a room based on the item name. *GetItems* returns a list of all of the items in the room. 

The **TimeLimit** class represents the time a user has left in the game. It will keep track of the player's time since starting the game, and assist the Main class in assessing whether the player has won the game. The *currentTime* field contains the user's current time since starting, and the *timeLimit* contains the maximum time the player has to complete the game. 

The **Main** class is where the execution of the game happens. It prompts the user to input a JSON file and then reads in the JSON file to execute the game from the file. The *startRoom* variable is a JSONObject indicating the room that the game starts in, and *endRoom* indicates the room that the game ends in. *RoomMap* and *itemMap* are hashmaps indicating which rooms contain which items, based on the information in the JSON file. *PlayerName* is simply a string containing the player's name, which comes from the user when the game prompts them to input their name. The Main class is able to access the Room class and TimeLimit because the Main class needs to know where the player is and whether they have won yet or not. 

The **Inventory** class holds all of the information about a player's inventory. The *items* attribute is an ArrayList of every item that is in the current inventory. The *getInventory* method returns the player's current inventory, i.e. what items they are currently holding, while *addToInventory* adds an item to this list. *PrintInventory* simply prints the name of each item in the items list, so a user can see what items they can perform actions on. *InventoryIsEmpty* checks whether the inventory has items in it, and *isInInventory* checks whether a specific item is in the inventory. This class is associated with the Player class because the player needs access to their inventory, and it is also associated with the Item class because the inventory holds items, and therefore needs access to them. 

The **ChatClient** class is what allows a player to communicate with other players through a chat room. The *serverAddress* holds the server IP that will be used. *TextField*, *messageArea*, and *frame* are attributes that are used to build the window that the chat room is in. *In* and *out* represent the text coming in and out of the chat room. 

In our original class diagram, we did not have an Inventory class. This led to the methods such as getInventory and printInventory being scattered across multiple classes, making things harder to understand and use. Adding an extra class for the inventory made more sense, since there are a lot more necessary methods involving the inventory than we thought. We also added a ChatClient class, where we implemented our chat room feature for our game. Now, our game supports the command "chat," and it will open a chat room on your screen, which you can then talk to other players on. In addition, we added association names to the diagram to make the arrows easier to follow. We also added an abstract class to represent the JSON file that the game reads in. 

## Object diagrams								
Author(s): `name of the team member(s) responsible for this section`

This chapter contains the description of a "snapshot" of the status of your system during its execution. 
This chapter is composed of a UML object diagram of your system, together with a textual description of its key elements.

`Figure representing the UML class diagram`
  
`Textual description`

Maximum number of words for this section: 1000

## State machine diagrams									
Author(s): `name of the team member(s) responsible for this section`

This chapter contains the specification of at least 2 UML state machines of your system, together with a textual description of all their elements. Also, remember that classes the describe only data structures (e.g., Coordinate, Position) do not need to have an associated state machine since they can be seen as simple "data containers" without behaviour (they have only stateless objects).

For each state machine you have to provide:
- the name of the class for which you are representing the internal behavior;
- a figure representing the part of state machine;
- a textual description of all its states, transitions, activities, etc. in a narrative manner (you do not need to structure your description into tables in this case). We expect 3-4 lines of text for describing trivial or very simple state machines (e.g., those with one to three states), whereas you will provide longer descriptions (e.g., ~500 words) when describing more complex state machines.

The goal of your state machine diagrams is both descriptive and prescriptive, so put the needed level of detail here, finding the right trade-off between understandability of the models and their precision.

Maximum number of words for this section: 4000

## Sequence diagrams									
Author(s): `name of the team member(s) responsible for this section`

This chapter contains the specification of at least 2 UML sequence diagrams of your system, together with a textual description of all its elements. Here you have to focus on specific situations you want to describe. For example, you can describe the interaction of player when performing a key part of the videogame, during a typical execution scenario, in a special case that may happen (e.g., an error situation), when finalizing a fantasy soccer game, etc.

For each sequence diagram you have to provide:
- a title representing the specific situation you want to describe;
- a figure representing the sequence diagram;
- a textual description of all its elements in a narrative manner (you do not need to structure your description into tables in this case). We expect a detailed description of all the interaction partners, their exchanged messages, and the fragments of interaction where they are involved. For each sequence diagram we expect a description of about 300-500 words.

The goal of your sequence diagrams is both descriptive and prescriptive, so put the needed level of detail here, finding the right trade-off between understandability of the models and their precision.

Maximum number of words for this section: 4000

## Implementation									
Author(s): `name of the team member(s) responsible for this section`

In this chapter you will describe the following aspects of your project:
- the strategy that you followed when moving from the UML models to the implementation code;
- the key solutions that you applied when implementing your system (for example, how you implemented the syntax highlighting feature of your code snippet manager, how you manage fantasy soccer matches, etc.);
- the location of the main Java class needed for executing your system in your source code;
- the location of the Jar file for directly executing your system;
- the 30-seconds video showing the execution of your system (you can embed the video directly in your md file on GitHub).

IMPORTANT: remember that your implementation must be consistent with your UML models. Also, your implementation must run without the need from any other external software or tool. Failing to meet this requirement means 0 points for the implementation part of your project.

Maximum number of words for this section: 2000

## References

References, if needed.