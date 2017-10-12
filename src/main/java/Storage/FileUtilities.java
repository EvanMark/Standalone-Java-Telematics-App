package Storage;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtilities {

    //method that writes the Location objects into a file 
    //takes the name of the file to be created, a boolean value for overwriting data(or not)
    //and a collection from which the function will draw data.
    //the function is used for both the location and the connection data
    public static void writeToFile(String filename, boolean overwrite, HashMap map) {
        FileOutputStream F_output = null;
        try {
            File dataFile = new File(filename);
            if (dataFile.exists()) {//here is the if function for checking the existence
                System.out.println("The file is already been created and has data inside.So as a result it will be overwritten!!! ");
                overwrite = false;
            }
            F_output = new FileOutputStream(dataFile, overwrite); //true to append----false to overwrite 
            ObjectOutputStream O_output = new ObjectOutputStream(F_output);
            O_output.writeObject(map);
            O_output.flush();
            O_output.close();
            F_output.close();
            System.out.println("SUCCESS");
            System.out.println();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                F_output.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //method that reads the Location or Connection Objects from the file and place them into a HashMap for creating an output
    //takes the name of the file for reading.. CAUTION the file first must be created otherwise throws exception  
    public static HashMap readFromFile(String filename) {
        FileInputStream F_input = null;
        HashMap mapInFile = new HashMap();
        try {
            File dataFile = new File(filename);
            F_input = new FileInputStream(dataFile);
            ObjectInputStream O_input = new ObjectInputStream(F_input);
            mapInFile = (HashMap) O_input.readObject();//reads from file into a hashmap as object data therefore info is not lost 
            O_input.close();
            F_input.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR : FILE NOT FOUND ");//If the file is not found throws a message
            return mapInFile;//and returns an empty list
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                F_input.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mapInFile;
    }

}
