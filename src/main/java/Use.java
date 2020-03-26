import java.util.concurrent.atomic.AtomicReference;

public class Use implements Command {
    private final String[] command;
    private Player player;

    public Use(String[] command, AtomicReference<Player> playerRef) {
        this.command = command;
        this.player = playerRef.get();
    }
    @Override
    public void execute(){
        if (command.length == 2) {
            String itemName = command[1];

            Item item = Main.itemMap.get(itemName);

            if (item == null) {
                System.out.println(Main.ANSI_BLUE + "\nThat item does not exist.\n" + Main.ANSI_RESET);
            }

            Room playerLocation = player.getCurrentRoom();
            Obstacle currentObstacle = playerLocation.getObstacle();

            // Check if there even is an obstacle
            if (currentObstacle == null) {
                System.out.println(Main.ANSI_BLUE + "\nThere is no obstacle in this room to use an item on.\n" +
                        Main.ANSI_RESET);
            }
            // Check if it's already been cleared
            else if (currentObstacle.isCleared()) {
                System.out.println(Main.ANSI_BLUE + "\n The obstacle in this room has already been cleared.\n" +
                        Main.ANSI_RESET);
            }
            // Check if the player has the right item to clear this obstacle
            else if (!(itemName.equals(currentObstacle.getItemNeeded()))) {
                System.out.println(Main.ANSI_MAGENTA + "'" + itemName + "'" + Main.ANSI_BLUE + " wouldn't help " +
                        "you here.\n" + Main.ANSI_RESET);
            }
            // Command has passed all checks, clear the obstacle
            else {
                if (currentObstacle.dropsItem()) {
                    playerLocation.addItemToRoom(currentObstacle.getItemDropped());
                }
                currentObstacle.clearObstacle();
            }
        }
        else {
            System.out.println(Main.ANSI_BLUE + "\nType " + Main.ANSI_MAGENTA + "'use <item>'" + Main.ANSI_BLUE +
                    " to use an item.\n" + Main.ANSI_RESET);
        }
    }
}
