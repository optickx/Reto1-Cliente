package logic.business;

import interfaces.Signable;

public abstract class SignableFactory {
    public static Signable getImplementation() {
        return new DatabaseConnection();
    }
}
