package vn.aptech.project4.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	@Column(name = "customer_id")
	private int customer_id;
	@Column(name = "customer_email")
	private String customerEmail;
	@Column(name = "customer_password")
	private String customer_password;
	@Column(name = "customer_name")
	private String customer_name;
	@Column(name = "customer_phone")
	private int customer_phone;
	@Column(name = "address")
	private String address;
	@Column(name = "total_expense")
	private int total_expense;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="membership_id")
	private Membership membership;
	@Column(name="authority")
	private String authority;
	
}
