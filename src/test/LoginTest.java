package test;

import business.*;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginTest {
    static DataAccess dataAccess;
    static ControllerInterface controller;

    @BeforeAll
    static void setup() {
        dataAccess = new DataAccessFacade(true);
        controller = new SystemController(dataAccess);
    }

    @AfterEach
    void clean() {
        dataAccess.cleanUpTestStorage();
    }

    void addUser(String id, String password, Auth auth) {
        controller.addUser(id, password, auth);
    }

    @Test()
    void testLoginFailed() {
        String id = "librarian";
        String password = "123";
        String incorrectPassword = "incorrectPassword";
        addUser(id, password, Auth.LIBRARIAN);

        IAuthStateListener mockListener = mock(IAuthStateListener.class);
        SystemController.registerAuthStateListener(mockListener);
        assertThrows(LoginException.class, () -> controller.login(id, incorrectPassword));
        verify(mockListener, never()).onLogin(Auth.LIBRARIAN);
        verify(mockListener, never()).onLogout();

        SystemController.deregisterAuthStateListener(mockListener);
    }

    @Test()
    void testLoginSuccessful() {
        String id = "librarian";
        String password = "123";
        addUser(id, password, Auth.LIBRARIAN);

        IAuthStateListener mockListener = mock(IAuthStateListener.class);
        SystemController.registerAuthStateListener(mockListener);
        assertDoesNotThrow(() -> controller.login(id, password));
        verify(mockListener).onLogin(Auth.LIBRARIAN);
        verify(mockListener, never()).onLogout();

        SystemController.deregisterAuthStateListener(mockListener);
    }

    @Test()
    void testLogout() {
        String id = "librarian";
        String password = "123";
        addUser(id, password, Auth.LIBRARIAN);

        IAuthStateListener mockListener = mock(IAuthStateListener.class);
        SystemController.registerAuthStateListener(mockListener);
        assertDoesNotThrow(() -> controller.login(id, password));
        verify(mockListener).onLogin(Auth.LIBRARIAN);
        verify(mockListener, never()).onLogout();

        controller.logout();
        verify(mockListener).onLogout();

        SystemController.deregisterAuthStateListener(mockListener);
    }
}
