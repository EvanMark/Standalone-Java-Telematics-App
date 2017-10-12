package Threads;

import basics.Location;
import java.util.HashMap;

public class LocationsThread implements Runnable {

    private Location city;
    private String citystr;
    private HashMap<String, Location> threadmap;

    public LocationsThread(String acity) {
        this.citystr = acity;
    }

    @Override
    public void run() {
        this.city = new Location(citystr);
        threadmap = new HashMap<>();
        threadmap.put(citystr, city);
    }

    public HashMap<String, Location> getThreadMap() {
        return threadmap;
    }

}
