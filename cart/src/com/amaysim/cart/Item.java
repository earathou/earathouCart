package com.amaysim.cart;

/**
 * Items in the cart
 * @author Earathou Berdin
 *
 */
public class Item {
	
	private String productCode;
	private int quantity;
	private double price;
	private double totalPrice;
	
	public Item() {
		
	}
	
	public Item(String productCode, int quantity, double price) {
		this.productCode = productCode;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
