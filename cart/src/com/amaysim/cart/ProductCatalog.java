package com.amaysim.cart;

/**
 * Product Information
 * @author Earathou Berdin
 *
 */
public class ProductCatalog {
	
	private String productCode;
	private String productName;
	private double price;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
