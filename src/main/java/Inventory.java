import java.lang.reflect.Array;
import java.util.*;

public class Inventory {

    private ArrayList<Item> items;

    public Inventory(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getInventory() { return items; }

    public void addToInventory(Item item) { this.items.add(item); }

    public String getStringInventory() {
        String playerInventory = "";

        for (Item item : items){
            playerInventory = item.getNameFromItem() + ", " + playerInventory;
        }

        int unnecessaryComma = playerInventory.lastIndexOf(",");

        return playerInventory.substring(0, unnecessaryComma);
    }

    public static String printInventory(ArrayList<Item> playerItems) {
        String playerInventory = "";
        String playerInventoryFinal;

        for (Item item : playerItems){
            playerInventory = item.getNameFromItem() + ", " + playerInventory;
        }
        int unnecessaryComma = playerInventory.lastIndexOf(",");
        playerInventoryFinal = "Your current inventory: " + playerInventory.substring(0, unnecessaryComma);

        return playerInventoryFinal;
    }
}
