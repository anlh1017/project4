package vn.aptech.project4.entity;


import java.util.Date;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="customer")
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
	private String customer_phone;
	@Column(name = "address")
	private String address;
	@Column(name = "total_expense")
	private int total_expense;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="membership_id")
	private Membership membership;
	@Column(name="authority")
	private String authority;
	@Column(name="password_token")
	private String resetPasswordToken;
	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider")
	private AuthenticationProvider authProvider;
	@Column(name = "date", columnDefinition = "date")
    private Date customerDate;
	public Customer() {
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomer_password() {
		return customer_password;
	}

	public void setCustomer_password(String customer_password) {
		this.customer_password = customer_password;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_phone() {
		return customer_phone;
	}

	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTotal_expense() {
		return total_expense;
	}

	public void setTotal_expense(int total_expense) {
		this.total_expense = total_expense;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public AuthenticationProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthenticationProvider authProvider) {
		this.authProvider = authProvider;
	}

	public Date getCustomerDate() {
		return customerDate;
	}

	public void setCustomerDate(Date customerDate) {
		this.customerDate = customerDate;
	}

	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", customerEmail=" + customerEmail + ", customer_password="
				+ customer_password + ", customer_name=" + customer_name + ", customer_phone=" + customer_phone
				+ ", address=" + address + ", total_expense=" + total_expense + ", membership=" + membership
				+ ", authority=" + authority + ", resetPasswordToken=" + resetPasswordToken + ", authProvider="
				+ authProvider + ", customerDate=" + customerDate + "]";
	}
	
}
