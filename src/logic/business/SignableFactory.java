package logic.business;

import interfaces.Signable;

public abstract class SignableFactory {
    private static Signable obj = new Client();
    
    /** 
     * Mehtod returning an object of type Signable
     * @return Signable
     */
    public static Signable getImplementation() {
        return obj;
    }
}
