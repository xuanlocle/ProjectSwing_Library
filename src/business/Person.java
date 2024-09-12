package business;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 3665880920647848288L;
	private String firstName;
	private String lastName;
	private String telephone;
	private Address address;
	public Person(String f, String l, String t, Address a) {
		firstName = f;
		lastName = l;
		telephone = t;
		address = a;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTelephone() {
		return telephone;
	}
	public Address getAddress() {
		return address;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Person other = (Person) obj;
		if (!other.getFirstName().equals(this.getFirstName())) {
			return false;
		}
		if (!other.getLastName().equals(this.getLastName())) {
			return false;
		}
		if (!other.getTelephone().equals(this.getTelephone())) {
			return false;
		}
		if (!other.getAddress().equals(this.getAddress())) {
			return false;
		}
		return true;
	}
}
