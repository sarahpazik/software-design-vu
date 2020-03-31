import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Action {
    private String commandName;
    private Item item;
    private Room room;

    public Action(String commandName, Item item, Room room) {
        this.commandName = commandName;
        this.item = item;
        this.room = room;
    }

    public static void doAction(String[] command, AtomicReference<Player> playerRef) {
        Action result;
        String keyword = command[0];

        switch (keyword) {
            case "move":
                Move m = new Move(command, playerRef);
                m.execute();
                break;
            case "pick":
                Pick p = new Pick(command, playerRef);
                p.execute();
                break;
            case "inspect":
                Inspect i = new Inspect(command, playerRef);
                i.execute();
                break;
            case "help":
                Help h = new Help(command, playerRef);
                h.execute();
                break;
            case "look":
                Look l = new Look(command, playerRef);
                l.execute();
                break;
            case "chat":
                Chat c = new Chat(command, playerRef);
                c.execute();
                break;
            case "use":
                Use u = new Use(command, playerRef);
                u.execute();
                break;
            default:
                System.out.println(Main.ANSI_BLUE + "\nThe command name is invalid. Please ask for help.\n" + Main.ANSI_RESET);
        }
    }
}
