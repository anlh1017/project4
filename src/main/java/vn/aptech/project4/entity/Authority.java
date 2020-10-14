package vn.aptech.project4.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="authorities")
@IdClass(AuthorityId.class)
public class Authority {
	@Id
	@Column(name="username")
	private String username;
	@Id
	@Column(name="authority")
	private String authority;

	public Authority() {
	}

	public Authority(String username, String authority) {
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
}
