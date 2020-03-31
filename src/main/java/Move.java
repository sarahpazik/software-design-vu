import java.util.concurrent.atomic.AtomicReference;

public class Move implements Command {
    private final String[] command;
    private Player player;

    public Move(String[] command, AtomicReference<Player> playerRef) {
        this.command = command;
        this.player = playerRef.get();
    }

    @Override
    public void execute(){
        if (command[1].equals("to")) {
            Room currentRoom = player.getCurrentRoom();
            String wholeName;
            if (command.length > 3) {
                String firstWord = command[2];
                String secondWord = command[3];
                wholeName = firstWord + " " + secondWord;
            } else {
                wholeName = command[2];
            }
            Room nextRoom = currentRoom.getNextRoomFromName(wholeName);

            if (nextRoom == null) {
                System.out.println(Main.ANSI_BLUE + "\nThe room name is invalid. Please ask for help.\n" +
                        Main.ANSI_RESET);
            }
            else if (currentRoom.hasObstacle()) {
                Obstacle currentObstacle = currentRoom.getObstacle();
                if ((!currentObstacle.isCleared()) && currentObstacle.blocksRooms()) {
                    String[] blockedRooms = currentObstacle.getRoomsBlocked();
                    boolean destinationBlocked = false;
                    for (int n = 0; n < currentRoom.getNextRooms().length - 1; n++) {
                        if (wholeName.equals(blockedRooms[n])) {
                            System.out.println(Main.ANSI_BLUE + "\n" + currentObstacle.getDescription() + " You won't be " +
                                    "able to go this way until you deal with the obstacle.\n" + Main.ANSI_RESET);
                            destinationBlocked = true;
                        }
                    }
                    if (!destinationBlocked) {
                        player.setCurrentRoom(nextRoom);
                        System.out.println(Main.ANSI_BLUE + "\nYou have moved to " + nextRoom.getRoomName() + ".\n" +
                                nextRoom.getScript() + "\n" + Main.ANSI_RESET);
                    }
                }
                else {
                    player.setCurrentRoom(nextRoom);
                    System.out.println(Main.ANSI_BLUE + "\nYou have moved to " + nextRoom.getRoomName() + ".\n" +
                            nextRoom.getScript() + "\n" + Main.ANSI_RESET);
                }
            }
            else {
                player.setCurrentRoom(nextRoom);
                System.out.println(Main.ANSI_BLUE + "\nYou have moved to " + nextRoom.getRoomName() + ".\n" +
                        nextRoom.getScript() + "\n" + Main.ANSI_RESET);
            }
        }
        else {
            System.out.println(Main.ANSI_BLUE + "\nDid you mean 'move to'?\n" + Main.ANSI_RESET);
        }



    }
}
