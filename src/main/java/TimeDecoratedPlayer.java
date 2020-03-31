public class TimeDecoratedPlayer extends PlayerDecorator{

    private String name;
    private Inventory inventory;
    private Room currentRoom;
    private  boolean isChatting;

    public TimeDecoratedPlayer(Player p) {
        super(p);
        this.name = p.getName();
        this.inventory = p.getInventory();
        this.currentRoom = p.getCurrentRoom();
        this.isChatting = p.isChatting();
    }

    public String getName() { return this.name; }

    public Room getCurrentRoom() { return currentRoom; }

    public void setCurrentRoom(Room room) { this.currentRoom = room; }

    public void setIsChatting(boolean status){ this.isChatting = status; }

    public Inventory getInventory(){ return this.inventory;}

    public boolean isChatting(){return this.isChatting;}

    public boolean checkTime() {
        return true;
    }

}
