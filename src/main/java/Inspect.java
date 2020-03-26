import java.util.concurrent.atomic.AtomicReference;

public class Inspect implements Command {
    private final String[] command;
    private Player player;

    public Inspect(String[] command, AtomicReference<Player> playerRef) {
        this.command = command;
        this.player = playerRef.get();
    }

    @Override
    public void execute() {
        if (command.length == 2) {
            String itemName = command[1];

            Item item = Main.itemMap.get(itemName);

            if (item == null) {
                System.out.println(Main.ANSI_BLUE + "\nThat item does not exist.\n" + Main.ANSI_RESET);
            } else {
                System.out.println(Main.ANSI_BLUE + "\n" + item.getUsage() + "\n" + Main.ANSI_RESET);
            }

        } else {
            System.out.println(Main.ANSI_BLUE + "\nDid you mean 'inspect item'?\n" + Main.ANSI_RESET);
        }
    }
}
