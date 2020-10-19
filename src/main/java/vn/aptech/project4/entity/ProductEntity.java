package vn.aptech.project4.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ProductEntity {
	@Column(name="productId")
	private int productId;

	@Column(name="productName")
	private String productName;

	@Column(name="description")
	private String description;
	
	@Column(name="category")
	private String categoryName;
	
	@Column(name="categoryId")
	private int categoryId;
	
	@Column(name="image")
	private String image;
	
	private int sizeS;
	private int sizeM;
	private int sizeL;
	public int getSizeS() {
		return sizeS;
	}
	public void setSizeS(int sizeS) {
		this.sizeS = sizeS;
	}
	public int getSizeM() {
		return sizeM;
	}
	public void setSizeM(int sizeM) {
		this.sizeM = sizeM;
	}
	public int getSizeL() {
		return sizeL;
	}
	public void setSizeL(int sizeL) {
		this.sizeL = sizeL;
	}
	public ProductEntity() {
		// TODO Auto-generated constructor stub
	}
	public ProductEntity(int productId,String productName,String description,String categoryName,int categoryId,String image,int sizeS,int sizeM,int sizeL) {
		// TODO Auto-generated constructor stub
		this.categoryId = categoryId;
		this.categoryName=categoryName;
		this.productId = productId;
		this.productName = productName;
		this.image = image;
		this.description=description;
		this.sizeS=sizeS;
		this.sizeM=sizeM;
		this.sizeL=sizeL;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
