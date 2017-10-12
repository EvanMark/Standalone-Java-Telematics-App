package basics;

import Threads.ConnectionsThread;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Serializable {
//creates two checkpoint-type objects 

    private CheckPoint to;
    private CheckPoint from;

    //constructor that takes two location parameters and creates two checkpoint objects with them
    public Connection(Location loc1, Location loc2) {
        this.from = new CheckPoint(loc1);
        this.to = new CheckPoint(loc2);
    }

    public CheckPoint getTo() {
        return to;
    }

    public CheckPoint getFrom() {
        return from;
    }

    public void setTo(CheckPoint newTo) {
        this.to = newTo;
    }

    public void setFrom(CheckPoint newFrom) {
        this.from = newFrom;
    }

   //method that creates a HashMap with the direct connections from the API using Thread operations 
    //In this case synchronizedMap() is the suitable function
    //parameters are a HashMap containing the all the locations needed 
    //and a Hashmap where all the direct connections will be stored
    public static void CreateConnectionMap(HashMap<String, Location> locmap, HashMap<String, Connection> conmap) {
        ArrayList<ConnectionsThread> threads = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        Map map=Collections.synchronizedMap(conmap);
        //double for each loop(location HashMap) used to check every possible from-to combination
        for (Map.Entry<String, Location> en1 : locmap.entrySet()) {
            for (Map.Entry<String, Location> en2 : locmap.entrySet()) {
                ConnectionsThread conth = new ConnectionsThread(en1, en2,map);
                executor.execute(conth);
                threads.add(conth);
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        conmap.putAll(map);
    }

    //print the contents of the hashmap
    public static void PrintConnectionsHashMap(HashMap<String, Connection> map) {
        for (Map.Entry<String, Connection> en : map.entrySet()) {
            System.out.println(en.getValue().getFrom().getStation().getName() + " --> " + en.getValue().getTo().getStation().getName());
            System.out.println();

        }
    }
}
