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
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
