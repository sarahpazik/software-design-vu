import java.util.*;

public class Room {
    private String name;
    private HashMap<String, Item> items;
    private String[] nextRooms;
    private String script;
    private Obstacle obstacle;

    public Room(String name, HashMap<String, Item> items, String[] nextRooms, Obstacle obstacle, String script) {
        this.name = name;
        this.items = items;
        this.nextRooms = nextRooms;
        this.obstacle = obstacle;
        this.script = script;
    }

    public String getRoomName(){
        return name;
    }

    public boolean hasObstacle() { return (this.obstacle.getRoomsBlocked().length > 0); }

    public Obstacle getObstacle() { return obstacle; }

    public String getScript(){
        return script;
    }

    public Item getItemFromName(String itemName) { return items.get(itemName); }

    public void removeItemFromRoom(String itemName) { items.remove(itemName); }

    public void addItemToRoom(Item thisItem) { items.put(thisItem.getNameFromItem(), thisItem); }

    public Room getNextRoomFromName(String roomName) {
        List<String> nextList = Arrays.asList(nextRooms);
        if (nextList.contains(roomName)){
            return  Main.roomMap.get(roomName);
        } else{
            return null;
        }
    }

    public String[] getNextRooms() { return nextRooms; }

    public HashMap<String, Item> getItems() { return items; }

}
