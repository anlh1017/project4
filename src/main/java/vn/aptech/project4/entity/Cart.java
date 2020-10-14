package vn.aptech.project4.entity;

import javax.persistence.Entity;



public class Cart {
	int idCart;
	int productId;	
	String productName;
    int quantity;
    int sizeId;
    String sizeName;
    int price;
    public Cart() {
    }


	public Cart(int productId, int quantity,int sizeId,int idCart,int price,String productName,String sizeName) {
        this.productId = productId;
        this.quantity = quantity;
        this.sizeId = sizeId;
        this.idCart =idCart;
        this.price = price;
        this.productName = productName;
        this.sizeName=sizeName;
    }


	public String getSizeName() {
		return sizeName;
	}


	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}


	public int getIdCart() {
		return idCart;
	}


	public void setIdCart(int idCart) {
		this.idCart = idCart;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public int getSizeId() {
		return sizeId;
	}


	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}
	
    
}
