package cn.my360.shop.entity;

import java.io.Serializable;

public class ProductEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;// ��Ʒid
	private String catalogID;// ��ƷĿ¼ID
	private String name;// ��Ʒ����
	private String introduce;// ��Ʒ���
	private String price;// ����
	private String chuChangPrice;// �����۸�
	private String nowPrice;// �ּ�
	private String productHTML;// ��Ʒ���ܵ�HTML
	private String picture;// СͼƬ��ַ
	private String maxPicture;// ��ͼƬ��ַ
	private String images;// ��ƷͼƬ�б�
	private int score;// ���ͻ���
	private String isnew;// �Ƿ���Ʒ��n����y����
	private String sale;// �Ƿ��ؼۡ�n����y����
	private int hit;// �������
	private String unit;// ��Ʒ��λ��Ĭ�ϡ�item:����
	private String createAccount;// ������
	private String createtime;// ¼��ʱ��
	private String updateAccount;
	private String updatetime;
	private String activityID;// �ID
	private String giftID;// ��ƷID
	private int createID;// ¼����
	private int sellcount;// ��������
	private int stock;// ���
	private String searchKey;
	private String isTimePromotion;// �Ƿ���ʱ������n����y����
	private int status;// ��Ʒ״̬��1��������2�����ϼܣ�3�����¼�
	private String title;// ��Ʒҳ�����
	private String description;// ҳ������
	private String keywords;// ҳ��ؼ���
	private String compID;
	private String compName;// ����
	private int orderNum = 10;// �����ֶ�
	private int checkState;// ���״̬:1:δ��� 2:���ͨ�� 3:��˲�ͨ��
	private String reason;// ��ͨ��ԭ��
	private String phone;// ��ų�����ϵ��ʽ
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
