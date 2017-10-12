
package basics;

import java.io.Serializable;


public class CheckPoint implements Serializable{ 

    private Location station; //creates an object named station, which is Location type
    private String arrival;
    private String departure;

    public CheckPoint(Location station) {
        this.station = station;
    }

    public Location getStation() {
        return station;
    }
    
    public void setStation(Location newStation){
        this.station=newStation;
    }
}
