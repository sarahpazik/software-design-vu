import java.util.*;

public class Obstacle {
    private Item itemNeeded;
    private Item itemDropped;
    private Room roomIn;
    private String[] roomsBlocked;
    private boolean cleared;
    private String clearMessage;

    public Obstacle(Item itemNeeded, Item itemDropped, Room roomIn, String[] roomsBlocked, String clearMessage) {
        this.itemNeeded = itemNeeded;
        this.itemDropped = itemDropped;
        this.roomIn = roomIn;
        this.roomsBlocked = roomsBlocked;
        this.cleared = false;
        this.clearMessage = clearMessage;
    }

    public void clearObstacle() {
        if (this.dropsItem()) {
            this.roomIn.addItemToRoom(this.itemDropped);
        }
        this.cleared = true;
        System.out.println(this.clearMessage);
    }

    public boolean dropsItem() {
        if (this.itemDropped != null) { return true; }
        else { return false; }
    }
}
