public class Item {
    private String name;
    private String location;
    private String usage;
    private boolean held;
    private boolean used;

    public Item(String name, String location, String usage) {
        this.name = name;
        this.location = location;
        this.usage = usage;
        this.held = false;
        this.used = false;

    }
}