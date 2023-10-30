package logic.business;

import interfaces.Signable;

public abstract class SignableFactory {
    private static Signable obj = new Client();
    
    /** 
     * @return Signable
     */
    public static Signable getImplementation() {
        return obj;
    }
}
