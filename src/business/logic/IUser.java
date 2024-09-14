package business.logic;

import business.port.IAuthStateListener;
import business.exception.LoginException;

public interface IUser {
    void login(String id, String password) throws LoginException;
    void logout();

    void registerAuthStateListener(IAuthStateListener listener);
    void deregisterAuthStateListener(IAuthStateListener listener);
}
