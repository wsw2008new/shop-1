package cn.my360.shop.util;

public class UrlEntry {
	// public static final String ip = "http://www.my360.cn";// ip��ַ
	// public static final String ip = "http://192.168.0.193:9080/animalshop";//
	// ip��ַ
	// public static final String ip = "http://192.168.0.165:8888/animalshop";//
	// ip��ַ
	public static final String ip = "http://182.116.57.252:8201/animalshop";// ip��ַ
	public static final String LOGIN_URL = ip + "/appUser!login";// ��¼
	public static final String REISTER_URL = ip + "/appUser!regist";// ע��
	public static final String UPLOAD_USERINFO_URL = ip + "/appUser!upload";// �޸ĸ���ͷ��
	public static final String UPDATE_USERINFO_URL = ip + "/appUser!update";// �޸ĸ���ͷ��
	public static final String QUERY_ALL_URL = ip + "/appOrder!selectList";// ��ѯ���г�����Ϣ
	public static final String UPLOAD_FILE_URL = ip + "/appOrder!upload";// �ϴ�ͼƬ
	public static final String DELETE_MYINFO_BYID_URL = ip + "/appOrder!delete";// ����idɾ����Ϣ
	public static final String UPDATE_MYINFO_BYID_URL = ip + "/appOrder!update";// ����id�޸���Ϣ
	public static final String ADD_MYINFO_URL = ip + "/appOrder!insert";// ����������Ϣ
	public static final String QUERY_INFODETAIL_URL = ip
			+ "/appOrder!showOrder";// ��ѯ��Ʒ����

	public static final String QUERY_VEHIVLE_ALL_URL = ip
			+ "/appVehicle!selectList"; // ��ѯ����������Ϣ
	public static final String DELETE_VEHIVLE_BYID_URL = ip
			+ "/appVehicle!delete";// ɾ��������Ϣ
	public static final String UPDATE_VEHIVLE_BYID_URL = ip
			+ "/appVehicle!update";// �޸�������Ϣ
	public static final String ADD_VEHIVLE_BYID_URL = ip + "/appVehicle!insert";// ����������Ϣ
	public static final String UPLOAD_VEHIVLEFILE_URL = ip
			+ "/appVehicle!upload";// �ϴ�ͼƬ
	public static final String QUERY_VEHIVLE_BYID_URL = ip + "/appVehicle!show";

	public static final String CHECK_VERSION_URL = ip
			+ "/appVersions!getNewest";// ���汾����

	public static final String PAY_URL = ip + "/appOrder!toPay";// ֧�� [���� msg]
	public static final String GETSTRING_URL = ip + "/appOrder!getString";

	public static final String QUERY_PIGFARM_URL = ip
			+ "/appPigFarm!getMyPigFarm"; // uuid
	public static final String INSERT_PIGFARM_URL = ip + "/appPigFarm!insert";// add��֤��
	public static final String UPDATE_PIGFARM_URL = ip + "/appPigFarm!update";// �޸���֤��

	public static final String QUERY_AGENT_URL = ip + "/appBroker!getByUuid"; // uuid
	public static final String INSERT_AGENT_URL = ip + "/appBroker!insert";// add��֤������
	public static final String UPDATE_AGENT_URL = ip + "/appBroker!update";// �޸���֤������

	public static final String QUERY_SLAUGHTERHOUSE_URL = ip
			+ "/appSlaughter!getByUuid"; // uuid
	public static final String INSERT_SLAUGHTERHOUSE_URL = ip
			+ "/appSlaughter!insert";// add��֤���׳�
	public static final String UPDATE_SLAUGHTERHOUSE_URL = ip
			+ "/appSlaughter!update";// �޸���֤���׳�

	public static final String QUERY_INDUSTRY_URL = ip
			+ "/appUser!getBusinessList";// ��ҵ

	/**************************** appProductFront!productList.action?catalogCode= **/
	public static final String QUERY_PRODUCT_ALL_URL = ip
			+ "/appProduct!productList.action";// ��Ʒ��Ϣ
	public static final String QUERY_PRODUCT_TYPE_URL = ip
			+ "/appProduct!getCatalogs";// ��Ʒ���
	public static final String QUERY_PRODUCT_BYNAME_URL = ip
			+ "/appProduct!search.action";// ���ݹؼ��ֲ�ѯ
	public static final String QUERY_PRODUCT_BYID_URL = ip
			+ "/appProduct!product";// ���ݹؼ��ֲ�ѯ
	public static final String PAY_ORDER_URL = ip
			+ "/szmyOrdersActionFront!toPay";// ֧��
	public static final String QUERY_ORDER_URL = ip
			+ "/szmyOrdersActionFront!getOrdersList";// ��ѯ����
	// public static final String UPDATE_ORDER_STATUS_URL = ip +
	// "/szmyOrdersActionFront!updateOrderStatus";//�޸Ķ���״̬
	public static final String UPDATE_ORDER_STATUS_URL = ip
			+ "/szmyOrdersActionFront!receive";// ȷ���ջ�

}
