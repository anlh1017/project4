package vn.aptech.project4.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Table(name="products_size")
@Data
@NoArgsConstructor
@AllArgsConstructor

@ToString
public class Products_size {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ProductSizeId")
	private int ProductSizeId;
	
	@Column(name="ProductsId")
	private int ProductsId;
	
	@Column(name="SizeId")
	private int SizeId;  
	
	
	@Column(name="Price")
	private int Price;
}
