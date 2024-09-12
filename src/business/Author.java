package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private static final long serialVersionUID = 7508481940058530471L;

	private String bio;
	private Boolean isExpert;
	public String getBio() {
		return bio;
	}
	public Boolean getIsExpert() {
		return isExpert;
	}
	
	public Author(String f, String l, String t, Address a, String bio, Boolean isExpert) {
		super(f, l, t, a);
		this.bio = bio;
		this.isExpert = isExpert;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Author other = (Author) obj;
		if (!other.getBio().equals(this.getBio())) {
			return false;
		}
		if (!other.getIsExpert().equals(this.getIsExpert())) {
			return false;
		}
		return true;
	}
}
