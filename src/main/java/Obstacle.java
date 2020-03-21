public class Obstacle {
    private String itemNeeded;
    private Item itemDropped;
    private String location;
    private String[] roomsBlocked;
    private boolean cleared;
    private String description;
    private String clearMessage;

    public Obstacle(String itemNeeded, Item itemDropped, String location, String[] roomsBlocked,
                    String description, String clearMessage) {
        this.itemNeeded = itemNeeded;
        this.itemDropped = itemDropped;
        this.location = location;
        this.roomsBlocked = roomsBlocked;
        this.cleared = false;
        this.description = description;
        this.clearMessage = clearMessage;
    }

    public void clearObstacle() {
        this.cleared = true;
        System.out.println(Main.ANSI_BLUE + "\n" + this.clearMessage + "\n" + Main.ANSI_RESET);
    }

    public boolean dropsItem() {
        if (itemDropped.getNameFromItem().equals("")) { return false; }
        else { return true; }
    }

    public String getItemNeeded() { return itemNeeded; }

    public Item getItemDropped() { return itemDropped; }

    public String getLocation() { return location; }

    public String[] getRoomsBlocked () { return roomsBlocked; }

    public String getDescription() { return description; }

    public boolean isCleared() { return cleared; }
}
