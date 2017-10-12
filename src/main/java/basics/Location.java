package basics;

import Threads.LocationsThread;
import Utilities.Tools;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class Location implements Serializable {

    private int id;
    private String name;
    private Coordinates coo;

    public Location(String city) throws NullPointerException{
        JSONObject jsonObject = Tools.GetJSONLoc(city);//Using GetJSONLoc method parse the API and take the below
        name = (String) jsonObject.get("name");
        name=name.replaceAll(",","");
        //create a string s to get id from API
        String s = (String) jsonObject.get("id");
        if (s == null) {//change every null address with 0 for easier obtaining of the integers
            id = 0;
        } else {
            id = Integer.parseInt(s);//parse string to int
        }
        jsonObject = (JSONObject) jsonObject.get("coordinate");
        coo = new Coordinates(jsonObject.get("type").toString(), Double.parseDouble(jsonObject.get("x").toString()), Double.parseDouble(jsonObject.get("y").toString()));
        //in above line parse string to double

    }

//Creation of a new Location constructor in purpose of using it to extract better and faster the object's data 
    //More info please check FileUtilities class where it is implemented 
    public Location(String name, int id, String cooType, double cooX, double cooY) {
        this.name = name;
        this.id = id;
        this.coo = new Coordinates(cooType, cooX, cooY);
    }

    //constructor used when creating the connection map
    //check Connection class where it is used
    public Location(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static HashMap<String, Location> CreateLocationsMap(HashMap<String, Location> map) {
        //create an arraylist using URL's wikipedia and store the names of the cities in the arraylist
        ArrayList<String> cities = Tools.GetCityNameList("https://en.wikipedia.org/wiki/List_of_cities_in_Switzerland");
        List<LocationsThread> threads = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(50);

        for (int i = 0; i < cities.size(); i++) {
            //for loop for each city
            LocationsThread city = new LocationsThread(cities.get(i));
            executor.execute(city);
            threads.add(city);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (LocationsThread locth : threads) {
            HashMap<String, Location> threadmap = locth.getThreadMap();
            map.putAll(threadmap);//then place it inside a HashMap Collection
        }
        return map;
    }

    //print the contents of the hashmap
    public static void PrintLocationsHashMap(HashMap<String, Location> map) {
        for (Map.Entry<String, Location> en : map.entrySet()) {
            System.out.println("Name:" + en.getValue().getName());
            System.out.println("ID:" + en.getValue().getid());
            System.out.println("Coords:Type-->" + en.getValue().getCoordsType() + " x-->" + en.getValue().getCoordsX() + " y-->" + en.getValue().getCoordsY());
            System.out.println();
        }
    }

//Following many individual getters for each specific element of the object
    public int getid() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCoordsType() {
        return coo.getType();

    }

    public double getCoordsX() {
        return coo.getX();
    }

    public double getCoordsY() {
        return coo.getY();
    }

}
