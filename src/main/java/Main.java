import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

                copyObj.put("currentRoom", player.getCurrentRoom().getRoomName());
                copyObj.put("currentInventory", player.inventory.getStringInventory());

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
                    + " and your inventory is currently empty.\n Type " + ANSI_MAGENTA + "'help'" +ANSI_BLUE + " if you ever need help.\n" + ANSI_RESET);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    static void whileGame(BufferedReader in, int timeLimitInt, JSONObject tomJsonObject) {

        TimeLimit timeLimit = new TimeLimit(timeLimitInt, System.currentTimeMillis()/1000);
        Inventory inventory = new Inventory(new ArrayList<Item>());
        Player player = new Player(playerName,  inventory,roomMap.get(startRoom.getString("name")));

        System.out.println(ANSI_BLUE + "\n" + startRoom.getString("script")  + "\n" + ANSI_RESET);

        while(!player.getCurrentRoom().getRoomName().equals(endRoom.getString("name"))
                && timeLimit.getCurrentTime()<timeLimit.getTimeLimit()){
            try {
                String[] input2 = in.readLine().split("\\s+");
                if(input2[0].equals("quit") && input2.length == 1) {
                    promptQuit(player, tomJsonObject);
                }
                else {
                    Action.doAction(input2, player);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(player.getCurrentRoom().getRoomName().equals(endRoom.getString("name"))){
                System.out.println(ANSI_BLUE + "\nCONGRATULATIONS, " + playerName +
                        "! You made it to " + endRoom.getString("name") + "\n" + ANSI_RESET);
            }

            if(timeLimit.getCurrentTime() >= timeLimit.getTimeLimit()){
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
                JSONArray rooms = tomJsonObject.getJSONArray("rooms");
                int timeLimit = tomJsonObject.getInt("time limit");

                for (int i = 0; i < rooms.length(); i++){
                    JSONObject room = rooms.getJSONObject(i);
                    String name = room.getString("name");
                    if (name.equals(start)){
                        startRoom = room;
                    } else if (name.equals(end)){
                        endRoom = room;
                    }

                    String script = room.getString("script");
                    JSONArray items = room.getJSONArray("items");
                    HashMap<String, Item> thisItemMap = new HashMap<>();

                    for (int j = 0; j < items.length(); j++){
                        JSONObject item = items.getJSONObject(j);
                        String itemName = item.getString("name");
                        String usage = item.getString("usage");
                        thisItemMap.put(itemName, new Item(itemName, name, usage));
                        itemMap.put(itemName, new Item(itemName, name, usage));
                    }

                    JSONArray nextRoomsJson = room.getJSONArray("connects to");
                    String[] nextRooms = new String[nextRoomsJson.length()];

                    for (int k = 0; k < nextRoomsJson.length(); k++){
                        String nextName = nextRoomsJson.getString(k);
                        nextRooms[k] = nextName;
                    }
                    roomMap.put(name, new Room(name, thisItemMap, nextRooms, script));
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