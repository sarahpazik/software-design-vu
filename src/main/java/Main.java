import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_MAGENTA = "\u001b[35m";
    private static final String ANSI_RED = "\u001b[31m";

    private static JSONObject startRoom;
    private static JSONObject endRoom;

    static Map<String, Room> roomMap = new HashMap<>();
    static Map<String, Item> itemMap = new HashMap<>();
    static ArrayList<Item> startInventory = new ArrayList<>();

    private static String playerName;

    private static void promptQuit(Player player, JSONObject originalJSON) {

        System.out.println(ANSI_RED + "\nWould you like to save your place, and return later? Answer: yes / no. \n" + ANSI_RESET);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String saveResponse;

        try {
            saveResponse = in.readLine();
            if (saveResponse.equals("no")) {
                System.out.println(ANSI_RED + "\nYou lose!\n" + ANSI_RESET);
                System.exit(0);
            } else if (saveResponse.equals("yes")) {

                JSONObject copyObj = new JSONObject(originalJSON, JSONObject.getNames(originalJSON));
                JSONArray inventoryArray = new JSONArray();

                copyObj.put("current room", player.getCurrentRoom().getRoomName());

                for (Item item : player.getInventory().getInventory()) {
                    inventoryArray.put(item.getNameFromItem());
                }

                copyObj.put("player name", playerName);
                copyObj.put("current inventory", inventoryArray);

                String fileName = playerName + ".json";

                try (FileWriter file = new FileWriter(fileName)) {

                    file.write(copyObj.toString());
                    file.flush();

                    System.out.println(ANSI_RED + "\nNext time you want to play, use the file 'your_name.json'. \n" + ANSI_RESET);
                    System.out.println(ANSI_RED + "\nSee you later!\n" + ANSI_RESET);
                    System.exit(0);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(ANSI_RED + "\nYou need to answer 'yes' or 'no'. Try again.\n" + ANSI_RESET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void beginGame(BufferedReader in) {

        System.out.println(ANSI_BLUE + "\nWhat is your name?\n" + ANSI_RESET);

        try {
            playerName = in.readLine();
            System.out.println(ANSI_BLUE + "\n Welcome, " + playerName +". \n Let's begin. Your goal is to get to "
                    + endRoom.getString("name") + ".\n You are currently located at " + startRoom.getString("name")
                    + "." + Inventory.printInventory(startInventory) + ".\n Type "
                    + ANSI_MAGENTA + "'help'" +ANSI_BLUE + " if you ever need help." + ANSI_RESET);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    static void whileGame(BufferedReader in, int timeLimitInt, JSONObject tomJsonObject) {

        Inventory inventory = new Inventory(new ArrayList<Item>());
        Player player = new RegularPlayer(playerName,  inventory,roomMap.get(startRoom.getString("name")), new TimeLimit(timeLimitInt, System.currentTimeMillis()/1000));

        System.out.println(ANSI_BLUE + "\n" + startRoom.getString("script")  + "\n" + ANSI_RESET);

        while(!player.getCurrentRoom().getRoomName().equals(endRoom.getString("name"))
                && player.checkTime()){
            try {
                String[] input2 = in.readLine().split("\\s+");
                if(input2[0].equals("quit") && input2.length == 1) {
                    promptQuit(player, tomJsonObject);
                }
                else {
                    AtomicReference<Player> ref = new AtomicReference<Player>(player);
                    Action.doAction(input2, ref);
                    player = ref.get();
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(player.getCurrentRoom().getRoomName().equals(endRoom.getString("name"))){
                System.out.println(ANSI_BLUE + "\nCONGRATULATIONS, " + playerName +
                        "! You made it to " + endRoom.getString("name") + "\n" + ANSI_RESET);
            }

            if(!player.checkTime()){
                System.out.println(ANSI_BLUE + "\nSORRY, " + playerName +  ". You did not make it to "
                        + endRoom.getString("name") + " in time.\n" + ANSI_RESET);
            }
        }
    }

    public static void main (String[] args){

        System.out.println(ANSI_BLUE + "\n Welcome to the Text Adventure Game. \n Please enter the name of the json file you want to load. \n"
        + ANSI_RESET);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String input = in.readLine();
            File jsonFile = new File(input);
            int dotIndex = input.lastIndexOf('.');
            String fileType = input.substring(dotIndex+1);
            if (jsonFile.exists() && fileType.equals("json")){
                String content = FileUtils.readFileToString(jsonFile, "utf-8");
                JSONObject tomJsonObject = new JSONObject(content);

                String start = tomJsonObject.getString("start room");
                String end = tomJsonObject.getString("end room");
                String current = tomJsonObject.getString("current room");
                JSONArray rooms = tomJsonObject.getJSONArray("rooms");
                int timeLimit = tomJsonObject.getInt("time limit");

                JSONArray inventory = tomJsonObject.getJSONArray("current inventory");
                Set<String> inventoryNameSet = new HashSet<>();

                for (int i = 0; i < inventory.length(); i++) {
                    String itemName = inventory.getString(i);
                    inventoryNameSet.add(itemName);
                }

                for (int i = 0; i < rooms.length(); i++){
                    JSONObject room = rooms.getJSONObject(i);
                    String name = room.getString("name");
                    if (name.equals(current) && !current.equals("")) {
                        startRoom = room;
                    } else if (name.equals(start) && startRoom == null){
                        startRoom = room;
                    } else if (name.equals(end)){
                        endRoom = room;
                    }

                    String script = room.getString("script");
                    JSONArray items = room.getJSONArray("items");
                    HashMap<String, Item> thisItemMap = new HashMap<>();

                    for (int j = 0; j < items.length(); j++){
                        JSONObject itemObject = items.getJSONObject(j);
                        String itemName = itemObject.getString("name");
                        String usage = itemObject.getString("usage");
                        Item item = new Item(itemName, name, usage);
                        itemMap.put(itemName, new Item(itemName, name, usage));

                        if (inventoryNameSet.contains(itemName)) {
                            item.holdItem(true);
                            startInventory.add(item);
                        } else {
                            thisItemMap.put(itemName, item);
                        }

                    }

                    JSONArray nextRoomsJson = room.getJSONArray("connects to");
                    String[] nextRooms = new String[nextRoomsJson.length()];

                    JSONObject obstacleObj = room.getJSONObject("obstacle");

                    String itemNeeded = obstacleObj.getString("item needed");
                    String itemName = obstacleObj.getString("item dropped name");
                    String usage = obstacleObj.getString("item dropped usage");

                    Item itemDropped = new Item(itemName, name, usage);

                    JSONArray roomsBlockedJson = obstacleObj.getJSONArray("rooms blocked");
                    String[] roomsBlocked = new String[roomsBlockedJson.length()];
                    for (int m = 0; m < roomsBlockedJson.length(); m++) {
                        String nextName = roomsBlockedJson.getString(m);
                        roomsBlocked[m] = nextName;
                    }

                    String exitBlockedMessage = obstacleObj.getString("description");
                    String clearMessage = obstacleObj.getString("clear message");
                    Obstacle obstacle = new Obstacle(itemNeeded, itemDropped, name, roomsBlocked, exitBlockedMessage,
                            clearMessage);

                    for (int k = 0; k < nextRoomsJson.length(); k++){
                        String nextName = nextRoomsJson.getString(k);
                        nextRooms[k] = nextName;
                    }
                    roomMap.put(name, new Room(name, thisItemMap, nextRooms, obstacle, script));
                }

                beginGame(in);

                whileGame(in, timeLimit, tomJsonObject);

            } else{
                System.out.println(ANSI_BLUE + "\nNot a valid json file.\n" + ANSI_RESET);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}