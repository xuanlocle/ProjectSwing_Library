package business;

public interface IUserAction {
     void login(String username, String password);
     void logout();
     void getUserInfo();
}

