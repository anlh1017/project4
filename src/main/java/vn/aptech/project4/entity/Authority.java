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
@Data
@IdClass(AuthorityId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
	@Id
	@Column(name="username")
	private String username;
	@Id
	@Column(name="authority")
	private String authority;
	
}
