# Assignment 3

### Summary of changes of Assignment 2
Author(s): Sarah, Sam

* Made each of our actions into separate methods in the **Action** class to simplify out our previously long and complex switch statement
* Added an **Inventory** class which holds everything relevant to a player's inventory (printInventory, addToInventory, etc) in order to keep our code organized and concise
* Updated class diagram to properly represent JSON objects 
* Updated object diagram to represent the completed implementation and any changes that were made from the assignment 2 code
* Added association names to the class diagram 
* Added the chat room feature (Added **ChatClient** class) so that you can communicate with other players (use command "chat")
* Implemented time limit feature to make the game slightly more complex
* Added obstacles to the game using the **Obstacle** class to make the game more complex
* Added the "use" command (implemented as the **Use** class) to the game to apply **Item** to **Obstacles** to clear them
* Implemented persistence to allow a user to save their progress. The objective behind this feature is to allow users to begin a new game, play, exit the terminal, and then restart in the same place next time they open it. This feature gives users the choice of whether or not to save their current game state, including the items they hold, their current location, and their time within the time limit, and pick up where they left off the next time they want to play. 

### Application of design patterns
Author(s): Sarah

![Class Diagram with design patterns labelled](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/Copy%20of%20class%20diagram.png)

| ID  | DP1  |
|---|---|
| **Design pattern**  | Decorator |
| **Problem**  | We wanted a way to give the player the ability to get rid of the time limit in the game, but had no easy way to do so. |
| **Solution**  | The decorator pattern allowed us to have a new decorated player so that when the player finds the broken clock in the game, they are turned into a decorated player who no longer has a time limit. |
| **Intended use**  | We have a "broken clock" item in each game, and in our "pick" class/command, it checks whether the item picked up was the broken clock. If it was, the player's reference is changed to a TimeDecoratedPlayer rather than a RegularPlayer. This makes it so that the player's checkTime() method no longer counts down the time. |
| **Constraints**  | N/A |

| ID  | DP2  |
|---|---|
| **Design pattern**  | Command |
| **Problem**  | We wanted a way to firstly separate our commands, since our Action class was getting complicated and hard to follow, and secondly be able to store the user's commands. |
| **Solution**  | This design pattern allowed us to split each of the commands into separate classes, solving our first problem. It also allows us to store our commands like any other objects, solving the second problem. |
| **Intended use**  | Now, when a user types a command, it calls the respective class's execute() method, which overrides the Command interface's execute() method. Now, each command can be stored as a separate object rather than just existing as a method in the Action class.  |
| **Constraints**  | N/A |

Maximum number of words for this section: 2000

## Class diagram									
Author(s): Sarah

![Class Diagram](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/class%20diagram%20(2).png)

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
Author(s): Sam

![Object Diagram](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/object%20diagram%20final.png)
  
Each of the objects represented in the object diagram match their descriptions as given in the explanation of the class 
diagram. Notably, all objects are now represented by boxes in the default black color, as all planned features have now
been implemented.

At a given state in the game, there is always a **Player** object representing the current player of the game. It
connects to an **Inventory** object, a **Room** object, an **Action** object, and a **Setting** object. The associated
**Inventory** object represents the items that are in the player's current inventory - in this case, just "ovChipkaart".
The **Action** object indicates an action currently being taken by the player - in this case, "use ovChipkaart" -
through a specific command that represents a snapshot of a moment in the game. Notably, this **Action** is also
associated to the **Item** because it constitutes "using" the item. Furthermore, this **Action** is connected to the
**Obstacle** in the **Room** because using an item will clear the obstacle if it is the correct item. The **Room** 
object indicates the room the player is currently located in - in this case, Station Zuid. Finally, the **Setting**
object - Amsterdam - is a collection and description of all of the rooms.

In turn, the **Room** object connects to an **Item** object and an **Obstacle** object. The Item object, a coffee, is
connected because it represents an item found in that particular room. The **Obstacle** object - in this case, a stop at
the metro - represents a barrier to continued movement by the player that can be removed by using the correct item.

Finally, the **Setting** object - Amsterdam - connects to a **TimeLimit** object and back to the **Room** of
StationZuid. The room is one of many in the setting, while the **TimeLimit** will indicate how much time is left for the
player to reach the goal.

## State machine diagrams									
Author(s): Elizabeth, Gemma

#### Player State Machine

This state machine diagram represents the state of the Player class throughout the life cycle of a single game. 

![Player State Machine with Persistence](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/Player%20State%20Machine%20(2).png)

When the terminal is first open, no player is initialized. In A3, we added a persistence feature which allows users to save their place and then start a game again the next time the terminal is opened. Therefore, there are two possible transitions that can occur to initialize a player. 
1. The user can specify that they want to continue a previous game. They will be prompted to input a JSON file with their username, and then a player will be initialized using the previous game state, i.e. their name, the location they were in when they quit, their inventory of items that they had already picked up, and the time spent in the game previously. This “Previous Player” will have the previous username, the “current location” specified in the JSON file attached to their username, an inventory with the items they’ve picked up, and time. 
2. The user can specify that they want to begin a new game, in which case they will input a name and then a new player will be initialized. This “New Player” will have a new name, begin in the “start room” fetched from the original JSON file, and will have an empty inventory with no items.

Now that the player has been initialized, whether it is a new player or one who already has made progress in the game, the player enters a game state entitled “Player with Inventory in Location”. The player can now move to a new location, pick up items, or put down items. Note that there are also other commands, such as look around, inspect, etcetera which help the player throughout the game but do not significantly change the state of the player. 

If the player chooses to move, they must specify a location with a valid name which is adjacent to their currentLocation. If there is an obstacle located at the entry of this location, then they must possess the "key" in their inventory in order to use it and unlock the next room. If the player chooses to pick up an item, it must be one with a valid name that is in their currentLocation and is not already held. If the player puts down an item, similar conditions apply, but the item must already be in their inventory. 

This cycle continues until either:
The player runs out of time, i.e. their time is greater than the time limit specified in the JSON file and they are not yet located in the end room. In this case, a lose statement is printed out and the game is terminated.
The player reaches their final destination, the “end room”, within the time limit. In this case, a win statement is printed out and the game finishes.


#### Item State Machine

The item state machine represents the internal behavior of the **Item** class.

![Item State Machine Diagram](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/Item%20State%20Machine%20Diagram%20(1).jpg)

The state machine diagram describes the possible states an item may have over the course of the game. The game begins with no items. The user is prompted to provide the name of a JSON file, which contains the information needed to initialize the items. An item being initialized is represented by the first arrow in the diagram, labeled initialize Item(), which occurs only if a valid JSON file is provided, and then the following state. Upon entry into this state, the "Initial Item" is located in the room specified in the JSON file, and the boolean attributes *held* and *used* are set to false.

If an item is picked up by the player, meaning the item now belongs to the player's inventory, the item changes state to "Item in player inventory". Upon entry to this state, the attribute *held* is set to true, meaning item is in the player's inventory and no longer in the room. Now, every time the player moves location, the item's *location* is updated as well.

From the "Item in player inventory" state, the Item can be used, but only on the correct obstacle, which is represented by the arrow between the Item held and Item used states. 

If the item is used from the "item in player inventory" state, the attribute *used* is set to true and the item is dropped from the inventory because it is no longer needed, thus terminating the item.

Maximum number of words for this section: 4000

## Sequence diagrams									
Author(s): Ben

#### Change Room Sequence Diagram

 ![Inspect Sequence Diagram](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/Inspect%20Sequence%20Diagram.png)

This diagram describes the interaction a user undertakes when inspecting an item with their virtual character in the game. First, the user types the command "inspect \<item\>" into the terminal. At this stage in program execution, control flow lies within a while loop in the main method of the **main** class. When this entry happens, the **main** class parses the input from the command line and sends it to the **action** class via the doAction() function. This function call involves passing two parameters, the **player** object and a tokenized version of the command string the user typed stored as a string array. Once here, the doAction() function processes the meaning of the command and executes it accordingly using the command design pattern. First, it passes through a switch statement to begin to analyze the command. If it is an invalid command, the user will be informed of this, encouraged to use the “help” command, and control flow will return to the main method. Otherwise, as in this example, the user has typed a correct “inspect” command. The function will now instantiate an **Inspect** object and call its execute method(). First, execute() will check that the input command has a length of 2 words. If the length of the command is not correct, the user will be informed that they entered an invalid command and control flow will return to the main class. Otherwise, execute() will request the public hashmap field of item objects mapped to their names from the **Main** class. Next, it isolates the \<item\> part of the user's command from the previously passed function parameter. Then, the execute method will attempt to access the correct **item** object by looking it up in the  hashmap, using its string name as the key. If the **item** is in the map and get() does not return null, the program will continue properly. The execute() function will call the **item's** getUsage() function that returns a string about how to use it. This string is then displayed to the user in the console and control flow returns to the main class. However, if the item does not exist, the user is informed that the item does not exist in the console and control flow resumes in the main. 

#### Initial Setup Sequence Diagram

 ![Initial Startup Sequence Diagram](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/Initial%20Startup%20Sequence%20Diagram.png)

This diagram describes the interaction a user undertakes when selecting the story file to use and how the program initializes all of the necessary objects from the json data. This step occurs after the program has initially been run and the user is prompted to input the name of the story file they intend to use. First, the user will type the absolute file path to this json. Here, the main method of the **main** class will begin to attempt parsing. It will check if the file exists. If there is a file error, an exception will be thrown, the user will be informed that they supplied an invalid file, and program execution will terminate. Otherwise, as can be seen in the diagram, the user enters a valid file and parsing begins. A correctly formatted file will describe the start **room** and the end **room** by their names. It will also have a full description of each **room** in the game, including its name, a list of **room** objects it connects to, a list of **item** objects inside of it, and a string script that is associated with the **room** object’s role in the story. **Item** objects will have a name field and a usage description field. Finally, it will have a time limit for the game. The main method will process and save the time limit first. Then, it will call the initRooms() method. First, this will save the start **room** name, end **room** name, and list of **room** descriptions. Next, it will traverse this list of **room** objects as described by the json in a loop. Inside of this loop, it will traverse the list of **item** objects as described by the json in the **room**. Here, all of the **item** objects in a room will be initialized, passing their name and usage data from the json to the **item** constructor. They are stored in a map from their name to the **item** object. After all the **item** objects in the **room** are traversed and mapped, the process of initializing the **room** object begins. All of the data from the json about the **room** object’s name, connections, and script is parsed and, in conjunction with the previously created map of **item** objects, passed to the **room** constructor. Now that the base world is built, the beginGame() method is called. The user is prompted for his or her name and it is loaded into the game. Finally, the whileGame() method is called and the main game logic begins.


## Implementation									
Author(s): Elizabeth, Sam, Gemma, Ben

When initially designing our UML models, we had to consider how descriptive and/or perscriptive they would be. Designing these models in this iterative process helped us to formulate our ideas in a more concrete way and translate our thoughts to code in an organized and concerted fashion. The initial designs from assignments 1 and 2 were more perscriptive, while the final versions in this assignment are exclusively descriptive. Designing them this way enabled thought and discussion amongst our group about future implementation decisions and enabled our teaching assistants to provide useful feedback about improving our designs. They also enabled us to better visualize our system and see how design patterns could be implemented to better the readability and style of our execution.

For the implementation of the **Obstacle** class, more mutators and accessors were added to the other classes. These
were necessary to let obstacles print messages when a player examined their surroundings, determine movement based on
whether an obstacle was blocking an exit, and more. Furthermore, the **Main** class was updated to allow JSON files to
include obstacles and to ensure they were instantiated at the running of the game. Finally, the JSON files had
**Obstacles** added to allow them to run with the updated **Main**.

In order to implement persistence, the JSON file was primarily used for saving data. When a user types the command
“quit”, they are asked whether or not they want to save their place. If they answer yes, their current game state is
saved to a new JSON file entitled ‘<your_name>.json’. This JSON file contains all of the information in the initial
file, plus the player’s current location, the items currently in their inventory as a JSONArray, name, and current time.
Next time they want to play, they are asked if they want to load a previous game. If yes, they are prompted to enter
this JSON file, and resume. Once this command is parsed, the game loads in their current location as the new start room,
rather than the initial start room, re-initializes their inventory with their current items, and restarts the clock
against the time limit based on the time they already spent in the game.

An additional strategy we followed to implement the recommendations in our feedback was to create separate classes for
all of our different actions, following the command design pattern. This allowed us to better organize all of the code relating to each action, as well as simplify a long and extremely complicated switch statement we had in the original **Action** class.

Another effort in simplifying and organizing our code was made in implementing the **Inventory** class. It allows us to
organize the different **Items** a **Player** may be holding and somewhat reduces the unnecessary complexity of managing
all of their items.

In order to implement the decorator object pattern, we redefined our initial **Player** class as an interface. We then created two classes extending it, **RegularPlayer** and **PlayerDecorator**. While **RegularPlayer** was a concrete class,  **PlayerDecorator** was abstract, and extended by its child **TimeDecoratedPlayer**. Due to implementing this pattern, we can add special functionality to the **player** object in a more streamlined, efficient way. In the game, if a **player** picks up the special "Broken Clock" object, the original **Player** object is decorated and its checkTime() method is functionally altered so that the user never runs out of time while playing the game.

Another feature we added to the game was a live chatroom for players to communicate and help one another during gameplay. If the user enters the "chat" command, a **ChatClient** object is created that connects to a remote amazon ec2 server located at 13.58.146.33 on port 59001. The server code runs 7 days a week, from 8 to 16 UTC. If a user attempts to connect outside of chatroom hours they will be informed that the server is closed at that time.

The main Java class needed for executing the system in the source code can be found at the directory location:
software-design-vu/src/main/java/Main.java

The Jar file can be found at the directory location:
software-design-vu/out/artifacts/software_design_vu_2020_jar/software-design-vu-2020-jar.jar

![30-Second Demo](https://github.com/sarahpazik/software-design-vu/blob/Assignment3/Assignment3Demo1.mp4)
This demo first shows the new Persistence feature by loading a JSON file that saved a previous game state, allowing the user to resume a previously played game. The demo then demonstrates the Obstacle feature by having to *use* an object before moving between rooms. The player in the demo loses the game by running out of time in order to demonstrate the Time Limit feature.

One final note is that for a full game experience, the White Orchard JSON file inspired by the video game The Witcher 3
should be played. The Amsterdam JSON mainly functions to illustrate the adaptability of the game to different settings
as required by the assignment and is therefore not as extensive, but it is still fully playable.

#### References

The White_Orchard.json file is inspired by early levels of the video game The Witcher 3 by CD Projekt Red. Naturally,
only the "plot" is referenced, all of the code is unique.
