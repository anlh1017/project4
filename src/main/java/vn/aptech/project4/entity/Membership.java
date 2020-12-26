package vn.aptech.project4.entity;

import javax.persistence.*;
import java.util.List;

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
	@Column(name="discount_value")
	private float discountValue;

	public float getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(float discountValue) {
		this.discountValue = discountValue;
	}

	public Membership() {
	}
	public Membership(String membership_name, String membership_description, Float discountValue) {
		this.membership_name = membership_name;
		this.membership_description = membership_description;
		this.discountValue = discountValue;
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
