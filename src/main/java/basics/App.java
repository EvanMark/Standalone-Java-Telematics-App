package basics;


import Graph.Dijkstra;
import Storage.DBHasDataException;
import Storage.Database;
import Storage.FileUtilities;
import Utilities.Tools;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This main class has been created for testing and running methods not available to gui
 * 
 */
public class App {

    public static void main(String[] args) {
        boolean flag = false;
        while (!flag) {
            Scanner input = new Scanner(System.in);
            Tools.PrintMenu();
            int decision = input.nextInt();
            HashMap<String, Location> locmap;
            HashMap<String, Connection> conmap;
            switch (decision) {
                case 1:
                    input = new Scanner(System.in);
                    System.out.println("Type the name of the file you want to be created:");
                    String newfilename = input.nextLine();
                    locmap = new HashMap<>();
                    Location.CreateLocationsMap(locmap);
                    FileUtilities.writeToFile(newfilename + ".dat", true, locmap);
                    break;
                case 2:
                    input = new Scanner(System.in);
                    System.out.println("Type the name of the file:");
                    String filename = input.nextLine();
                    locmap = FileUtilities.readFromFile(filename + ".dat");
                    Location.PrintLocationsHashMap(locmap);
                    break;
                case 3:
//                    locmap = new HashMap();
//                    Database.connect();
//                    try {
//                        Database.writeCitiesToDB(Location.CreateLocationsMap(locmap));
//                        System.out.println("Database created with success!!");
//                        System.out.println();
//                    } catch (DBHasDataException ex) {
//                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//                        Database.DBError("DELETE FROM JLOCATION");
//                    }
//                    Database.disconnect();
                    System.out.println("Deprecated, please use option 4");
                    break;
                case 4:
                    input = new Scanner(System.in);
                    System.out.println("Type the name of the file:");
                    filename = input.nextLine();
                    Database.connect();
                    try {
                        Database.writeCitiesToDB(FileUtilities.readFromFile(filename + ".dat"));
                        System.out.println("Database created with success!!");
                        System.out.println();
                    } catch (DBHasDataException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        Database.DBError("DELETE FROM JLOCATION");
                    }
                    Database.disconnect();
                    break;
                case 5:
                    Database.connect();
                    locmap = Database.readCitiesFromDB();
                    Location.PrintLocationsHashMap(locmap);
                    Database.disconnect();
                    break;
                case 6:
                    input = new Scanner(System.in);
                    System.out.println("Type the name of the locations file you want to find connections:");
                    filename = input.nextLine();
                    locmap = FileUtilities.readFromFile(filename + ".dat");
                    input = new Scanner(System.in);
                    System.out.println("Type the name of the connections file you want to create :");
                    filename = input.nextLine();
                    conmap = new HashMap<>();
                    Connection.CreateConnectionMap(locmap, conmap);
                    FileUtilities.writeToFile(filename + ".dat", true, conmap);
                    break;
                case 7:
                    input = new Scanner(System.in);
                    System.out.println("Type the name of the connections file you want to read:");
                    filename = input.nextLine();
                    conmap = FileUtilities.readFromFile(filename + ".dat");
                    Connection.PrintConnectionsHashMap(conmap);
                    break;
                case 8:
                    input = new Scanner(System.in);
                    System.out.println("Type the name of the connections file you want:");
                    filename = input.nextLine();
                    Database.connect();
                     {
                        try {
                            Database.writeConnectionsToDB(FileUtilities.readFromFile(filename + ".dat"));
                        } catch (DBHasDataException ex) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                            Database.DBError("DELETE FROM JCONNECTION");
                        }
                    }
                    Database.disconnect();
                    break;
                case 9:
                    Database.connect();
                    conmap = Database.readConnectionsFromDB();
                    Connection.PrintConnectionsHashMap(conmap);
                    Database.disconnect();
                    break;
                case 10:
                    conmap = FileUtilities.readFromFile("directlinks.dat");
                    for (Map.Entry<String, Connection> con1 : conmap.entrySet()) {
                        for (Map.Entry<String, Connection> con2 : conmap.entrySet()) {
                            if (con1 != con2) {
                                Dijkstra hipster = new Dijkstra(conmap, con1.getValue().getFrom().getStation().getName(), con2.getValue().getTo().getStation().getName());
                            }
                        }
                    }
                case 11:
                    System.out.println("Cheers mate");
                    flag = true;
                    break;
                default:
                    System.out.println("No such a choice. Type a number from 1 to 10!");
                    break;
            }
        }
    }

}
