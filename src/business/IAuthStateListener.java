package business;

import dataaccess.Auth;

public interface IAuthStateListener {
    public void onLogin(Auth auth);
    public void onLogout();
}
