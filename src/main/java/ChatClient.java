import java.io.IOException;
import java.io.PrintWriter;
import java.awt.BorderLayout;
import javax.swing.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    String serverAddress;
    JTextField textField = new JTextField(44);
    JTextArea messageArea = new JTextArea(18, 44);
    Scanner in;
    PrintWriter out;
    JFrame frame = new JFrame("Chat Room");


    public ChatClient(String serverIP) {
        this.serverAddress = serverIP;

        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();

        textField.setEditable(false);

        textField.addActionListener(e -> {
            out.println(textField.getText());
            textField.setText("");
        });

        messageArea.setEditable(false);
    }

    private String name() {
        return JOptionPane.showInputDialog(
                this.frame,
                "Choose a name for the chatroom:",
                "Chatroom name selection",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void run() throws IOException {
        try{
            Socket socket = new Socket(serverAddress, 59001);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());

            while (in.hasNextLine()) {
                String text = in.nextLine();

                if (text.startsWith("TEXT")) {
                    messageArea.append(text.substring(5) + "\n");
                }
                if (text.startsWith("ENTERNAME")) {
                    out.println(name());
                }
                if (text.startsWith("NAMEOKAY")) {
                    textField.setEditable(true);

                    this.frame.setTitle("Chat Room: " + text.substring(9));

                }
            }
        } catch(Exception e) {
            this.frame.dispose();
            throw new IOException();
        }
    }
}