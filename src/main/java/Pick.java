import java.util.concurrent.atomic.AtomicReference;

public class Pick implements Command {
    private String[] command;
    private AtomicReference<Player> playerRef;

    public Pick(String[] command, AtomicReference<Player> playerRef){
        this.command = command;
        this.playerRef = playerRef;
    }

    @Override
    public void execute() {
        if (command[1].equals("up")) {
            Player player = playerRef.get();
            Room currentRoom = player.getCurrentRoom();

            if (command.length < 3) {
                System.out.println(Main.ANSI_BLUE + "\nYou cannot pick up nothing.\n" + Main.ANSI_RESET);
            }

            String itemName = "";
            for(int i = 2; i < command.length; i++) {
                if (i > 2)
                    itemName += " ";
                itemName += command[i];
            }

            Item item = currentRoom.getItemFromName(itemName);
            if (item == null) {
                System.out.println(Main.ANSI_BLUE + "\nThe item name is invalid. Please ask for help.\n" + Main.ANSI_RESET);
            } else {
                player.getInventory().addToInventory(item);
                currentRoom.removeItemFromRoom(itemName);
                System.out.println(Main.ANSI_BLUE + "\nYou have picked up " + itemName + "." + Main.ANSI_RESET);
                System.out.println(Main.ANSI_BLUE + Inventory.printInventory(player.getInventory().getInventory())
                        + ".\n" + Main.ANSI_RESET);
                if(itemName.equals("Broken Clock")){
                    playerRef.set(new TimeDecoratedPlayer(player));
                }
            }

        } else {
            System.out.println(Main.ANSI_BLUE + "\nDid you mean 'pick up'?\n" + Main.ANSI_RESET);
        }
    }
}
