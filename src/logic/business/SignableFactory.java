package logic.business;

import interfaces.Signable;

/**
 * An abstract class representing a factory for creating objects of type
 * Signable. This class provides a static method to retrieve an implementation
 * of Signable.
 */
public abstract class SignableFactory {

    private static final  Signable obj = new Client();

    /**
     * Returns an instance of an object that implements the Signable interface.
     *
     * @return An object of type Signable representing the implementation.
     */
    public static Signable getImplementation() {
        return obj;
    }
}
