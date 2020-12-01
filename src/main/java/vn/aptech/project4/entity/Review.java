package vn.aptech.project4.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	@Id
	@Column(name="review_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
 private int review_id;
	@Column(name = "email")
 private String email;
	@Column(name = "content")
 private String content;
	@Column(name = "rating")
private Float rating;
	@Column(name = "date", columnDefinition = "date")
 private Date date;
}
