import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.atomic.AtomicReference;

public class Chat implements Command {
    private final String[] command;
    private Player player;

    public Chat(String[] command, AtomicReference<Player> playerRef) {
        this.command = command;
        this.player = playerRef.get();
    }
    @Override
    public void execute(){
        if (player.isChatting())
        {
            System.out.println(Main.ANSI_BLUE + "\nYou are already chatting! \n" + Main.ANSI_RESET);
        }
        else {
            //Separate thread for chatroom
            Thread chatThread = new Thread(() -> {
                try {
                    player.setIsChatting(true);
                    ChatClient chatroom = new ChatClient("13.58.146.33"); //Amazon EC2 instance IP Address where server is hosted
                    chatroom.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                    //custom chatroom frame exit handler to account for Player object's isChatting variable change of state
                    WindowListener exitListener = new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            player.setIsChatting(false);
                            chatroom.frame.dispose();
                        }
                    };
                    chatroom.frame.addWindowListener(exitListener);
                    chatroom.frame.setVisible(true);
                    chatroom.run();
                } catch(Exception e){
                    System.out.println(Main.ANSI_BLUE + "\nThe chat server is only available from 8 to 16 UTC.");
                }
            });
            chatThread.start();
        }
    }
}
