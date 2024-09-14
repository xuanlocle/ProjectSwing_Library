package test;

import business.exception.LoginException;
import business.logic.IUser;
import business.logic.IUserMgmt;
import business.logic.impl.UserMgmtService;
import business.logic.impl.UserService;
import business.port.IAuthStateListener;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginTest {
    static DataAccess dataAccess;
    static IUser userService;
    static IUserMgmt userMgmtService;

    @BeforeAll
    static void setup() {
        dataAccess = new DataAccessFacade(true);
        userService = new UserService(dataAccess);
        userMgmtService = new UserMgmtService(dataAccess);
    }

    @AfterEach
    void clean() {
        dataAccess.cleanUpTestStorage();
    }

    void addUser(String id, String password, Auth auth) {
        userMgmtService.addUser(id, password, auth);
    }

    @Test()
    void testLoginFailed() {
        String id = "librarian";
        String password = "123";
        String incorrectPassword = "incorrectPassword";
        addUser(id, password, Auth.LIBRARIAN);

        IAuthStateListener mockListener = mock(IAuthStateListener.class);
        userService.registerAuthStateListener(mockListener);
        assertThrows(LoginException.class, () -> userService.login(id, incorrectPassword));
        verify(mockListener, never()).onLogin(Auth.LIBRARIAN);
        verify(mockListener, never()).onLogout();

        userService.deregisterAuthStateListener(mockListener);
    }

    @Test()
    void testLoginSuccessful() {
        String id = "librarian";
        String password = "123";
        addUser(id, password, Auth.LIBRARIAN);

        IAuthStateListener mockListener = mock(IAuthStateListener.class);
        userService.registerAuthStateListener(mockListener);
        assertDoesNotThrow(() -> userService.login(id, password));
        verify(mockListener).onLogin(Auth.LIBRARIAN);
        verify(mockListener, never()).onLogout();

        userService.deregisterAuthStateListener(mockListener);
    }

    @Test()
    void testLogout() {
        String id = "librarian";
        String password = "123";
        addUser(id, password, Auth.LIBRARIAN);

        IAuthStateListener mockListener = mock(IAuthStateListener.class);
        userService.registerAuthStateListener(mockListener);
        assertDoesNotThrow(() -> userService.login(id, password));
        verify(mockListener).onLogin(Auth.LIBRARIAN);
        verify(mockListener, never()).onLogout();

        userService.logout();
        verify(mockListener).onLogout();

        userService.deregisterAuthStateListener(mockListener);
    }
}
