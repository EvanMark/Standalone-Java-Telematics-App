
package EncryptionTools;

//New exception to manage many others in class CryptoUtils.
//Check there to understand its purpose
class CryptoException extends Exception {

    public CryptoException() {
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
