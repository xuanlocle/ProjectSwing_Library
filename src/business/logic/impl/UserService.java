package business.logic.impl;

import business.port.IAuthStateListener;
import business.exception.LoginException;
import business.logic.IUser;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserService implements IUser {
    public static Auth currentAuth = null;
    public static List<IAuthStateListener> authStateListeners = new ArrayList<IAuthStateListener>();
    DataAccess dataAccess;

    public UserService() {}
    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public void registerAuthStateListener(IAuthStateListener listener) {
        authStateListeners.add(listener);
    }

    @Override
    public void deregisterAuthStateListener(IAuthStateListener listener) {
        authStateListeners.remove(listener);
    }

    @Override
    public void login(String id, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if(!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if(!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();

        for (IAuthStateListener l : authStateListeners) {
            l.onLogin(currentAuth);
        }
    }

    @Override
    public void logout() {
        currentAuth = null;
        for (IAuthStateListener l : authStateListeners) {
            l.onLogout();
        }
    }
}
