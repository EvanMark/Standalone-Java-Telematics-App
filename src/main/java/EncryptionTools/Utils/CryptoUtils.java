package EncryptionTools.Utils;

import EncryptionTools.Exception.CryptoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    //method that encrypts a file.Takes as attributes a String key  
    //the file which will encrypt and an output file where the result will be stored
    public static File encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);//calls doCrypto function that does the actual work
        return outputFile;
    }

    //same goes for this function only it is for decrypt mode.
    public static File decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
        return outputFile;
    }

    //takes the same attributes as the two methods above and plus an integer that defines the purpose of crypto
    //whether encrypt and decrypt and is taken as an field (ENCRYPT_MODE = 1 || DECRYPT_MODE = 2) from Cipher class.For additional info check the Cipher class
    //go to navagate and go to source to see the implementation of the class
    //and throws custom_made exception
    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
        try {
            //creating a secretKey using the constructor of SecretKeySpec where it generates a key in byte array display 
            //and in AES ciphering
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            //In order to create a Cipher object, the application calls the Cipher's getInstance method, and passes the name of the requested transformation to it.
            //Here the transformation is AES as its obvious from the declaration of the String
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);//initiate the cipher

            FileOutputStream outputStream;
            try (FileInputStream inputStream = new FileInputStream(inputFile) //open a stream for the file
                    ) {
                byte[] inputBytes = new byte[(int) inputFile.length()];//creating an array of bytes with space as big as inputFile length
                inputStream.read(inputBytes);
                byte[] outputBytes = cipher.doFinal(inputBytes);//do the encryption here
                outputStream = new FileOutputStream(outputFile);
                outputStream.write(outputBytes);//write the encryption into the new created file
            } //creating an array of bytes with space as big as inputFile length
            outputStream.close();
            //because this method throws so many exception,for adaptibility purposes an new exception is created to throw one specific output for every case.
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | FileNotFoundException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);

        } catch (IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}
