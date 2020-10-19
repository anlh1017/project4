package vn.aptech.project4.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="membership")

public class Membership {
	@Id
	@Column(name = "membership_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int membership_id;
	@Column(name = "membership_name")
    private String membership_name;
	@Column(name = "membership_description")
    private String membership_description;
	@OneToMany(mappedBy = "membership",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Customer> customers;
	public Membership() {
	}
	public Membership(String membership_name, String membership_description) {
		this.membership_name = membership_name;
		this.membership_description = membership_description;
	}
	public int getMembership_id() {
		return membership_id;
	}

	public void setMembership_id(int membership_id) {
		this.membership_id = membership_id;
	}

	public String getMembership_name() {
		return membership_name;
	}

	public void setMembership_name(String membership_name) {
		this.membership_name = membership_name;
	}

	public String getMembership_description() {
		return membership_description;
	}

	public void setMembership_description(String membership_description) {
		this.membership_description = membership_description;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}


}
