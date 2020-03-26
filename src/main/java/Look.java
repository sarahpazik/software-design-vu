import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Look implements Command {
    private final String[] command;
    private Player player;

    public Look(String[] command, AtomicReference<Player> playerRef) {
        this.command = command;
        this.player = playerRef.get();
    }

    @Override
    public void execute(){
        if (command[1].equals("around") && command.length == 2){
            String[] roomOptions = player.getCurrentRoom().getNextRooms();
            String roomOptionsString = roomOptions[0];
            for (int i = 1; i < roomOptions.length; i++){
                roomOptionsString = roomOptions[i] + ", " + roomOptionsString;
            }
            HashMap<String, Item> roomItems = player.getCurrentRoom().getItems();
            String itemOptionsString = "";
            String itemOptionsFinalString;
            if (roomItems.isEmpty()){
                itemOptionsFinalString = "none";
            } else {
                for (String itemName : roomItems.keySet()){
                    itemOptionsString = itemName + ", " + itemOptionsString;
                }
                int unnecessaryComma1 = itemOptionsString.lastIndexOf(",");
                itemOptionsFinalString = itemOptionsString.substring(0, unnecessaryComma1);
            }
            System.out.println(Main.ANSI_BLUE + "\nYou can move to " + roomOptionsString + ".\nItem(s) located " +
                    "in this room: " + itemOptionsFinalString + ".\n" + Main.ANSI_RESET);

            // If there's an uncleared obstacle in the room, also print its description
            if (player.getCurrentRoom().hasObstacle() && (!(player.getCurrentRoom().getObstacle().isCleared()))) {
                System.out.println(Main.ANSI_BLUE + "\n" + player.getCurrentRoom().getObstacle().getDescription() +
                        " You may want to (or have to) use some sort of item here before moving on.\n" +
                        Main.ANSI_RESET);
            }
        }
    }
}
