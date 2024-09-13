package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord record;

	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.record = new CheckoutRecord(new ArrayList<>());
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public CheckoutRecord getRecord() {
		return record;
	}

	public void setRecord(CheckoutRecord record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
