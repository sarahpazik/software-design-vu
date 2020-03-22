import java.util.*;

public interface Player {

    String getName();

    Room getCurrentRoom();

    void setCurrentRoom(Room room);

    void setIsChatting(boolean status);

    Inventory getInventory();

    boolean isChatting();

    boolean checkTime();

}
