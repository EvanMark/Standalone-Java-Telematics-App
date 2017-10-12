package EncryptionTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class Encryption {

    //Returns the preference node from the calling user's preference tree that is associated (by convention) with the specified class's package.
    //The above is the definition of method userNodeForPackage straight from Oracle Documentation
//In this particular code, Encryption class is acquired for use
    static Preferences preferences = Preferences.userNodeForPackage(Encryption.class);

    //setCredentials method takes as attribute a name of a file and acquire the login info from it. 
    public static void setCredentials(String filename) {

        FileInputStream F_input = null;
        try{
            String key = "The key->freedom";//the key is custom-made, the only limitation is that is has to be 16 bytes
            File inputFile = new File(filename);//creates a new reference to the file
            File unlock = new File("Login.dat");//creates a new empty file
            CryptoUtils.decrypt(key, inputFile, unlock);//where with the assist of decrypt method ,the login info is decrypted and stored into that the file
            F_input = new FileInputStream(unlock);
            try (BufferedReader D_input = new BufferedReader(new InputStreamReader(F_input))) {
                String username = D_input.readLine();//i read each line , due to te formation of the file
                String password = D_input.readLine();//first line the username,second line the password
                preferences.put("db_username", username);//and put them into the refernce collection
                preferences.put("db_password", password);
            } //i read each line , due to te formation of the file
            F_input.close();
            unlock.delete();//when done with the decryption deletes the current file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | CryptoException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                F_input.close();
            } catch (IOException ex) {
                Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //returns a String that contains the username using preferences Collection
    public static String getUsername() {
        return preferences.get("db_username", null);
    }

    ////returns a String that contains the password using preferences Collection
    public static String getPassword() {
        return preferences.get("db_password", null);
    }

}
