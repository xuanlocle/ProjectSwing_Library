package business.logic;

import business.LibraryMember;
import dataaccess.Auth;

import java.util.List;

public interface IUserMgmt {
    void addUser(String id, String password, Auth auth);
    List<String> allMemberIds();

    List<LibraryMember> getLibraryMembers();
    void addLibraryMember(String firstName, String lastName, String phone, String street, String city, String state, String zip);
}
