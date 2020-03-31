import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Help implements Command {
    private final String[] command;
    private Player player;

    public Help(String[] command, AtomicReference<Player> playerRef) {
        this.command = command;
        this.player = playerRef.get();
    }
    @Override
    public void execute() {
        ArrayList<Item> playerItems = player.getInventory().getInventory();
        String playerInventory = "";
        String playerInventoryFinal;
        if (playerItems.size() != 0){
            playerInventoryFinal = Inventory.printInventory(player.getInventory().getInventory());
        } else {
            playerInventoryFinal = "Your current inventory is empty";
        }
        System.out.println(Main.ANSI_BLUE + "\nYou are currently located in " + player.getCurrentRoom().getRoomName() +
                ".\n" + playerInventoryFinal + ".\n\nTo move, type " + Main.ANSI_MAGENTA +  "'move to <location>'" +
                Main.ANSI_BLUE +  ".\nTo see where to move to next and what items are in this location, type " +
                Main.ANSI_MAGENTA + "'look around'" + Main.ANSI_BLUE + ".\nTo add an item to your inventory, type " +
                Main.ANSI_MAGENTA +  "'pick up <item>'" + Main.ANSI_BLUE + ".\nTo learn more about an item, type " +
                Main.ANSI_MAGENTA + "'inspect <item>'" + Main.ANSI_BLUE + ".\nTo use an item on an obstacle in the " +
                "room you're currently in, type " + Main.ANSI_MAGENTA + "'use <item>'" + Main.ANSI_BLUE + ".\nTo " +
                "open the chat room, type " + Main.ANSI_MAGENTA + "'chat'" + Main.ANSI_BLUE + ".\nTo quit the game, " +
                "type " + Main.ANSI_MAGENTA + "'quit'" + Main.ANSI_BLUE + ".\n" + Main.ANSI_RESET);
    }
}
