package business.logic.impl;

import business.Address;
import business.logic.IValidator;
import business.LibraryMember;
import business.logic.IUserMgmt;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.*;

public class UserMgmtService implements IUserMgmt {
    DataAccess dataAccess;

    public UserMgmtService() {}
    public UserMgmtService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        migration();
    }

    private void migration() {
        dataAccess.migrationMemberRecord();
    }

    private String generateMemberId() {
        List<Integer> memberIds = allMemberIds().stream().map(Integer::parseInt).toList();
        if (memberIds.isEmpty()) {
            return "1";
        }
        Integer maxMemberId = Collections.max(memberIds);
        return String.valueOf( maxMemberId + 1);
    }

    @Override
    public void addUser(String id, String password, Auth auth) {
        this.dataAccess.addUser(id, password, auth);
    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<LibraryMember> getLibraryMembers() {
        HashMap<String, LibraryMember> members = dataAccess.readMemberMap();
        if (Objects.isNull(members)) {
            return List.of();
        }
        return members.values().stream().toList();
    }

    @Override
    public void addLibraryMember(String firstName, String lastName, String phone, String street, String city, String state, String zip) {
        IValidator validator = new Validator();
        validator.validateFirstName(firstName);
        validator.validateLastName(lastName);
        validator.validatePhoneNumber(phone);
        validator.validateStreet(street);
        validator.validateCity(city);
        validator.validateState(state);
        validator.validateZipCode(zip);
        String memberId = generateMemberId();
        Address address = new Address(street, city, state, zip);
        LibraryMember member = new LibraryMember(memberId, firstName, lastName, phone, address);
        dataAccess.saveNewMember(member);
    }
}
