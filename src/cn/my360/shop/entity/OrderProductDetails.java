package cn.my360.shop.entity;

import java.io.Serializable;

/** t_orderdetail ������Ʒ���� */
public class OrderProductDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4390170738489958392L;
	private String id;
	private String orderID;// ����id
	private String productID;// ��Ʒid
	private String price;// ����
	private String number;// ����
	private String productName;// ��Ʒ����
	private String fee;// ���ͷ�
	private String total0;// С��
	private String isComment;// �Ƿ��Ѿ�����
	private String lowStocks;// n:��治�㣻y:�����㡣Ĭ��n
	private String score;// ����
	private String specInfo;// ��Ʒ�����Ϣ
	private String giftID;// ��Ʒid
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
