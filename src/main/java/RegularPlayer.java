public class RegularPlayer implements Player{
    private String name;
    private Inventory inventory;
    private Room currentRoom;
    private  boolean isChatting;
    private TimeLimit timeLimit;

    public RegularPlayer(String name, Inventory inventory, Room currentRoom, TimeLimit t) {
        this.name = name;
        this.inventory = inventory;
        this.currentRoom = currentRoom;
        this.isChatting = false;
        timeLimit = t;
    }

    public String getName() {return this.name;}

    public Room getCurrentRoom() { return currentRoom; }

    public void setCurrentRoom(Room room) { this.currentRoom = room; }

    public void setIsChatting(boolean status){ this.isChatting = status; }

    public Inventory getInventory(){ return this.inventory;}

    public boolean isChatting(){return this.isChatting;}

    public boolean checkTime() {
        return timeLimit.getCurrentTime() < timeLimit.getTimeLimit();
    }

}
