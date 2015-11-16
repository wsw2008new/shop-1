package cn.my360.shop.entity;

import java.io.Serializable;

/** t_orderdetail 订单商品详情 */
public class OrderProductDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4390170738489958392L;
	private String id;
	private String orderID;// 订单id
	private String productID;// 商品id
	private String price;// 单价
	private String number;// 数量
	private String productName;// 商品名称
	private String fee;// 配送费
	private String total0;// 小计
	private String isComment;// 是否已经评论
	private String lowStocks;// n:库存不足；y:库存充足。默认n
	private String score;// 积分
	private String specInfo;// 商品规格信息
	private String giftID;// 赠品id
	private String picture;

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTotal0() {
		return total0;
	}

	public void setTotal0(String total0) {
		this.total0 = total0;
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public String getLowStocks() {
		return lowStocks;
	}

	public void setLowStocks(String lowStocks) {
		this.lowStocks = lowStocks;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getSpecInfo() {
		return specInfo;
	}

	public void setSpecInfo(String specInfo) {
		this.specInfo = specInfo;
	}

	public String getGiftID() {
		return giftID;
	}

	public void setGiftID(String giftID) {
		this.giftID = giftID;
	}

}
