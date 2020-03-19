import java.util.*;

public class Player {
    private String name;
    public Inventory inventory;
    private Room currentRoom;
    public boolean isChatting;

    public Player(String name, Inventory inventory, Room currentRoom) {
        this.name = name;
        this.inventory = inventory;
        this.currentRoom = currentRoom;
        this.isChatting = false;
    }

    public Room getCurrentRoom() { return currentRoom; }

    public void setCurrentRoom(Room room) { this.currentRoom = room; }

    public void setIsChatting(boolean status){ this.isChatting = status; }

}
