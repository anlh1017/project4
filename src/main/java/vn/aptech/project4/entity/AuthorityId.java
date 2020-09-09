package vn.aptech.project4.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityId implements Serializable {
	private String username;
	private String authority;
	
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
