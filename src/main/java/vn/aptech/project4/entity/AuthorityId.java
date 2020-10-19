package vn.aptech.project4.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthorityId implements Serializable {
	private String username;
	private String authority;

	public AuthorityId() {
	}

	public AuthorityId(String username, String authority) {
		this.username = username;
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(Object obj) {
	      if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        AuthorityId authorityId = (AuthorityId) obj;
	        return username.equals(authorityId.username) &&
	        		authority.equals(authorityId.authority);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, authority);
	}
	
}
