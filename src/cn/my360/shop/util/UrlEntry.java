package cn.my360.shop.util;

public class UrlEntry {
	// public static final String ip = "http://www.my360.cn";// ip地址
	// public static final String ip = "http://192.168.0.193:9080/animalshop";//
	// ip地址
	// public static final String ip = "http://192.168.0.165:8888/animalshop";//
	// ip地址
	public static final String ip = "http://182.116.57.252:8201/animalshop";// ip地址
	public static final String LOGIN_URL = ip + "/appUser!login";// 登录
	public static final String REISTER_URL = ip + "/appUser!regist";// 注册
	public static final String UPLOAD_USERINFO_URL = ip + "/appUser!upload";// 修改个人头像
	public static final String UPDATE_USERINFO_URL = ip + "/appUser!update";// 修改个人头像
	public static final String QUERY_ALL_URL = ip + "/appOrder!selectList";// 查询所有出售信息
	public static final String UPLOAD_FILE_URL = ip + "/appOrder!upload";// 上传图片
	public static final String DELETE_MYINFO_BYID_URL = ip + "/appOrder!delete";// 根据id删除信息
	public static final String UPDATE_MYINFO_BYID_URL = ip + "/appOrder!update";// 根据id修改信息
	public static final String ADD_MYINFO_URL = ip + "/appOrder!insert";// 新增发布信息
	public static final String QUERY_INFODETAIL_URL = ip
			+ "/appOrder!showOrder";// 查询商品详情

	public static final String QUERY_VEHIVLE_ALL_URL = ip
			+ "/appVehicle!selectList"; // 查询所有物流信息
	public static final String DELETE_VEHIVLE_BYID_URL = ip
			+ "/appVehicle!delete";// 删除物流信息
	public static final String UPDATE_VEHIVLE_BYID_URL = ip
			+ "/appVehicle!update";// 修改物流信息
	public static final String ADD_VEHIVLE_BYID_URL = ip + "/appVehicle!insert";// 新增物流信息
	public static final String UPLOAD_VEHIVLEFILE_URL = ip
			+ "/appVehicle!upload";// 上传图片
	public static final String QUERY_VEHIVLE_BYID_URL = ip + "/appVehicle!show";

	public static final String CHECK_VERSION_URL = ip
			+ "/appVersions!getNewest";// 检查版本更新

	public static final String PAY_URL = ip + "/appOrder!toPay";// 支付 [参数 msg]
	public static final String GETSTRING_URL = ip + "/appOrder!getString";

	public static final String QUERY_PIGFARM_URL = ip
			+ "/appPigFarm!getMyPigFarm"; // uuid
	public static final String INSERT_PIGFARM_URL = ip + "/appPigFarm!insert";// add认证猪场
	public static final String UPDATE_PIGFARM_URL = ip + "/appPigFarm!update";// 修改认证猪场

	public static final String QUERY_AGENT_URL = ip + "/appBroker!getByUuid"; // uuid
	public static final String INSERT_AGENT_URL = ip + "/appBroker!insert";// add认证经纪人
	public static final String UPDATE_AGENT_URL = ip + "/appBroker!update";// 修改认证经纪人

	public static final String QUERY_SLAUGHTERHOUSE_URL = ip
			+ "/appSlaughter!getByUuid"; // uuid
	public static final String INSERT_SLAUGHTERHOUSE_URL = ip
			+ "/appSlaughter!insert";// add认证屠宰场
	public static final String UPDATE_SLAUGHTERHOUSE_URL = ip
			+ "/appSlaughter!update";// 修改认证屠宰场

	public static final String QUERY_INDUSTRY_URL = ip
			+ "/appUser!getBusinessList";// 行业

	/**************************** appProductFront!productList.action?catalogCode= **/
	public static final String QUERY_PRODUCT_ALL_URL = ip
			+ "/appProduct!productList.action";// 商品信息
	public static final String QUERY_PRODUCT_TYPE_URL = ip
			+ "/appProduct!getCatalogs";// 商品类别
	public static final String QUERY_PRODUCT_BYNAME_URL = ip
			+ "/appProduct!search.action";// 根据关键字查询
	public static final String QUERY_PRODUCT_BYID_URL = ip
			+ "/appProduct!product";// 根据关键字查询
	public static final String PAY_ORDER_URL = ip
			+ "/szmyOrdersActionFront!toPay";// 支付
	public static final String QUERY_ORDER_URL = ip
			+ "/szmyOrdersActionFront!getOrdersList";// 查询订单
	// public static final String UPDATE_ORDER_STATUS_URL = ip +
	// "/szmyOrdersActionFront!updateOrderStatus";//修改订单状态
	public static final String UPDATE_ORDER_STATUS_URL = ip
			+ "/szmyOrdersActionFront!receive";// 确认收货

}
