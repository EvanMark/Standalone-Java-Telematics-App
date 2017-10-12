
package Threads;

import Utilities.Tools;
import basics.Connection;
import basics.Location;
import java.util.Map;


public class ConnectionsThread implements Runnable {

    private Map<String, Connection> conmap;
    private Map.Entry<String, Location> en1;
    private Map.Entry<String, Location> en2;

    public ConnectionsThread(Map.Entry<String, Location> en1, Map.Entry<String, Location> en2, Map<String, Connection> conmap) {
        this.en1 = en1;
        this.en2 = en2;
        this.conmap = conmap;
    }

    @Override
    public void run() {

        if (en1.getValue().getid() != 0 && en2.getValue().getid() != 0) {//getting rid of unwanted calls(where the response is null)
            if (Tools.GetJSONCon(en1.getValue().getid(), en2.getValue().getid())) {////Using GetJSONCon method(returns true for direct links)
                //creating Connection object from two locations called previously in the API
                Connection con = new Connection(en1.getValue(), en2.getValue());

                synchronized (conmap) {
                    conmap.put(en1.getKey() + ":" + en2.getKey(), con);//storing connection in HashMap
                }
            }
        }
    }
}
