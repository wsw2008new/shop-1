package cn.my360.shop.entity;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6287274224772851514L;
	private String id;// | int(11) |
	private String account;// �û�
	private String payType;// | int(11) |
	private String carry;// | int(11) |
	private String rebate;// | decimal(10,2) |�ۿ�
	private String createdate;// | datetime |��������
	private String status;// | varchar(10) |����״̬
	private String refundStatus;// | varchar(45) |�˿�˻�״̬
	private String amount;// | decimal(20,2) |�����ܽ��
	private String fee;// | decimal(20,2) |�����ͷ�
	private String ptotal;// | decimal(20,2) |��Ʒ�ܽ��
	private String quantity;// | int(11) |��Ʒ����
	private String paystatus;// | varchar(2) |����״̬
	private String updateAmount;// | varchar(1) |�Ƿ��޸Ĺ������ܽ��
	private String expressCode;// | varchar(45) |��ݶ�Ӧ����
	private String expressName;// | varchar(45) |�û�ѡ����ʵݷ�ʽ
	private String otherRequirement;// | varchar(50) |��������
	private String remark;// | varchar(545) |����֧��ʱ����ʾ������
	private String expressNo;// | varchar(45) |��ݵ���
	private String expressCompanyName;// | varchar(45) |�����������
	private String lowStocks;// | varchar(1) |
	private String confirmSendProductRemark;// | varchar(100) |�Ƿ�����
	private String closedComment;// | varchar(1) |�˶��������ж������Ӧ����Ʒ�����������ۣ�
	private List<OrderProductDetails> orderdetail;
	private String compID;// | varchar(10) |��˾id
	private String compName;// | varchar(100) |��˾����
	private String returnReason;// | varchar(100) |�˻��˿�ԭ��
	private String returnExpressName;// | varchar(45) |�˻��������
	private String returnExpressNo;// | varchar(45) |�˻���ݵ���
	private String returnMoney;// | decimal(10,2) |�˿���
	private String parentId;// | varchar(255) |�ϲ�������id
	private String isParent;// | varchar(255) |�Ƿ�Ϊ������

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

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getReturnExpressName() {
		return returnExpressName;
	}

	public void setReturnExpressName(String returnExpressName) {
		this.returnExpressName = returnExpressName;
	}

	public String getReturnExpressNo() {
		return returnExpressNo;
	}

	public void setReturnExpressNo(String returnExpressNo) {
		this.returnExpressNo = returnExpressNo;
	}

	public String getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(String returnMoney) {
		this.returnMoney = returnMoney;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCarry() {
		return carry;
	}

	public void setCarry(String carry) {
		this.carry = carry;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getPtotal() {
		return ptotal;
	}

	public void setPtotal(String ptotal) {
		this.ptotal = ptotal;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getUpdateAmount() {
		return updateAmount;
	}

	public void setUpdateAmount(String updateAmount) {
		this.updateAmount = updateAmount;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getOtherRequirement() {
		return otherRequirement;
	}

	public void setOtherRequirement(String otherRequirement) {
		this.otherRequirement = otherRequirement;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public String getLowStocks() {
		return lowStocks;
	}

	public void setLowStocks(String lowStocks) {
		this.lowStocks = lowStocks;
	}

	public String getConfirmSendProductRemark() {
		return confirmSendProductRemark;
	}

	public void setConfirmSendProductRemark(String confirmSendProductRemark) {
		this.confirmSendProductRemark = confirmSendProductRemark;
	}

	public String getClosedComment() {
		return closedComment;
	}

	public void setClosedComment(String closedComment) {
		this.closedComment = closedComment;
	}

	public List<OrderProductDetails> getOrderdetail() {
		return orderdetail;
	}

	public void setOrderdetail(List<OrderProductDetails> orderdetail) {
		this.orderdetail = orderdetail;
	}

}
