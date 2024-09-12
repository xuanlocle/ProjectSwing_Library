package business;

import java.io.Serializable;

/* Immutable */
final public class Address implements Serializable {
	
	private static final long serialVersionUID = -891229800414574888L;
	private String street;
	private String city;
	private String state;
	private String zip;
	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	@Override
	public String toString() {
		return "(" + street + ", " + city + ", " + zip + ")";
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Address other = (Address) obj;
		if (!other.getStreet().equals(this.getStreet())) {
			return false;
		}
		if (!other.getCity().equals(this.getCity())) {
			return false;
		}
		if (!other.getState().equals(this.getState())) {
			return false;
		}
		if (!other.getZip().equals(this.getZip())) {
			return false;
		}
		return true;
	}
}
