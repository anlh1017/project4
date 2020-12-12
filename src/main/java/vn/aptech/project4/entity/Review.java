package vn.aptech.project4.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="review")

public class Review {
	@Id
	@Column(name="review_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
 private int review_id;
	
	@OneToOne
	@JoinColumn(name="customer_id")
 private Customer customer;
	
	@Column(name = "content")
 private String content;
	@Column(name = "rating")
private Float rating;
	@Column(name = "date", columnDefinition = "date")
 private Date date;
	



	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getReview_id() {
		return review_id;
	}

	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}

	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}





}
