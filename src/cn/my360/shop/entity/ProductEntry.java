package cn.my360.shop.entity;

import java.io.Serializable;

public class ProductEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;// 商品id
	private String catalogID;// 商品目录ID
	private String name;// 商品名称
	private String introduce;// 商品简介
	private String price;// 定价
	private String chuChangPrice;// 出厂价格
	private String nowPrice;// 现价
	private String productHTML;// 商品介绍的HTML
	private String picture;// 小图片地址
	private String maxPicture;// 大图片地址
	private String images;// 商品图片列表
	private int score;// 赠送积分
	private String isnew;// 是否新品。n：否，y：是
	private String sale;// 是否特价。n：否，y：是
	private int hit;// 浏览次数
	private String unit;// 商品单位。默认“item:件”
	private String createAccount;// 创建者
	private String createtime;// 录入时间
	private String updateAccount;
	private String updatetime;
	private String activityID;// 活动ID
	private String giftID;// 赠品ID
	private int createID;// 录入人
	private int sellcount;// 销售数量
	private int stock;// 库存
	private String searchKey;
	private String isTimePromotion;// 是否限时促销。n：否，y：是
	private int status;// 商品状态。1：新增，2：已上架，3：已下架
	private String title;// 商品页面标题
	private String description;// 页面描述
	private String keywords;// 页面关键字
	private String compID;
	private String compName;// 卖家
	private int orderNum = 10;// 排序字段
	private int checkState;// 审核状态:1:未审核 2:审核通过 3:审核不通过
	private String reason;// 不通过原因
	private String phone;// 存放车辆联系方式
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getCatalogID() {
		return catalogID;
	}

	public void setCatalogID(String catalogID) {
		this.catalogID = catalogID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getChuChangPrice() {
		return chuChangPrice;
	}

	public void setChuChangPrice(String chuChangPrice) {
		this.chuChangPrice = chuChangPrice;
	}

	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getProductHTML() {
		return productHTML;
	}

	public void setProductHTML(String productHTML) {
		this.productHTML = productHTML;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMaxPicture() {
		return maxPicture;
	}

	public void setMaxPicture(String maxPicture) {
		this.maxPicture = maxPicture;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdateAccount() {
		return updateAccount;
	}

	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}

	public String getGiftID() {
		return giftID;
	}

	public void setGiftID(String giftID) {
		this.giftID = giftID;
	}

	public int getCreateID() {
		return createID;
	}

	public void setCreateID(int createID) {
		this.createID = createID;
	}

	public int getSellcount() {
		return sellcount;
	}

	public void setSellcount(int sellcount) {
		this.sellcount = sellcount;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getIsTimePromotion() {
		return isTimePromotion;
	}

	public void setIsTimePromotion(String isTimePromotion) {
		this.isTimePromotion = isTimePromotion;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCompID() {
		return compID;
	}

	public void setCompID(String compID) {
		this.compID = compID;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getCheckState() {
		return checkState;
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
