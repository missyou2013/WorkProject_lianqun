package activity.lianqun.herry.com.workproject_lianqun.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: JsonParseUtil
 * @Description: json解析工具类
 * @author liuxj
 * @date 2014-10-15 上午11:38:34
 * 
 */
public class JsonUtil {
	private JsonUtil() {
	}

	/**
	 * 对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	// /**
	// * @Title: detailOrderJsonParseMethod
	// * @Description: 商户订单中的detail
	// * @param @param jsonstr
	// * @param @param i
	// * @param @return 设定文件
	// * @return List<DetailModel> 返回类型
	// * @throws
	// */
	// private static List<DetailModel> detailOrderJsonParseMethod(String
	// jsonstr,
	// int i) {
	// List<DetailModel> detailModels = new ArrayList<DetailModel>();
	// String result = "";
	// try {
	// result = new JSONObject(jsonstr).getJSONObject("data")
	// .getJSONArray("list").get(i).toString();
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// Type listType = new TypeToken<List<DetailModel>>() {
	// }.getType();
	// Gson gson = new Gson();
	// try {
	// detailModels = gson.fromJson(
	// new JSONObject(result).getString("detail"), listType);
	// } catch (JsonSyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return detailModels;
	// }
	//
	// /**
	// * @Title: merchantDetailJsonParseMethod
	// * @Description:订单详情
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return MerchantOrderModel 返回类型
	// * @throws
	// */
	// public static MerchantOrderModel merchantDetailJsonParseMethod(
	// String jsonstr) {
	// Gson gson = new Gson();
	// MerchantOrderModel bean = null;
	// try {
	// bean = gson.fromJson(new JSONObject(jsonstr).getString("data"),
	// MerchantOrderModel.class);
	// } catch (JsonSyntaxException e2) {
	// // TODO Auto-generated catch block
	// e2.printStackTrace();
	// } catch (JSONException e2) {
	// // TODO Auto-generated catch block
	// e2.printStackTrace();
	// }
	// try {
	// DeliveryModel deliveryModel = gson.fromJson(new JSONObject(jsonstr)
	// .getJSONObject("data").getString("delivery"),
	// DeliveryModel.class);
	// bean.setDeliveryModel(deliveryModel);
	// } catch (JsonSyntaxException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (JSONException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// List<DetailModel> detailModels = new ArrayList<DetailModel>();
	// Type listType = new TypeToken<List<DetailModel>>() {
	// }.getType();
	// try {
	// detailModels = gson.fromJson(
	// new JSONObject(jsonstr).getJSONObject("data").getString(
	// "detail"), listType);
	// } catch (JsonSyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// bean.setDetailModels(detailModels);
	// return bean;
	// }
	//
	// /**
	// * @Title: GoodsCatalogueModelJsonParseMethod
	// * @Description: 商品分类json解析
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return List<GoodsCatalogueModel> 返回类型
	// * @throws
	// */
	// public static List<GoodsCatalogueModel>
	// GoodsCatalogueModelJsonParseMethod(
	// String jsonstr) {
	// String result = "";
	// try {
	// result = new JSONObject(jsonstr).getJSONObject("data").getString(
	// "items");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// Type listType = new TypeToken<List<GoodsCatalogueModel>>() {
	// }.getType();
	// Gson gson = new Gson();
	// List<GoodsCatalogueModel> linkedList = gson.fromJson(result, listType);
	// return linkedList;
	// }
	//
	// /**
	// * @Title: PurchaseRecordModelJsonParseMethod
	// * @Description: 采购记录
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return PurchaseRecordModel 返回类型
	// * @throws
	// */
	//
	// public static List<PurchaseRecordModel>
	// PurchaseRecordModelJsonParseMethod(
	// String jsonstr) {
	// String result = "";
	// try {
	// result = new JSONObject(jsonstr).getJSONObject("data").getString(
	// "list");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// Type listType = new TypeToken<List<PurchaseRecordModel>>() {
	// }.getType();
	// Gson gson = new Gson();
	// List<PurchaseRecordModel> linkedList = gson.fromJson(result, listType);
	// return linkedList;
	// }
	//
	// /**
	// * @throws JSONException
	// * @Title: PurchaseRecordDetailJsonParseMethod
	// * @Description: 采购详情
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return List<PurchaseRecordModel> 返回类型
	// * @throws
	// */
	// public static PurchaseRecordModel PurchaseRecordDetailJsonParseMethod(
	// String jsonstr) throws JSONException {
	// JSONObject jsonObject = new JSONObject(jsonstr);
	// JSONObject myJsonObject = jsonObject.getJSONObject("data");
	// String result = "";
	// try {
	// result = myJsonObject.getString("detail");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// Type listType = new TypeToken<List<DetailModel>>() {
	// }.getType();
	// Gson gson = new Gson();
	// List<DetailModel> linkedList = gson.fromJson(result, listType);
	//
	// PurchaseRecordModel bean = gson.fromJson(myJsonObject.toString(),
	// PurchaseRecordModel.class);
	// bean.setDetailModels(linkedList);
	// return bean;
	// }
	//
	// /**
	// * @Title: SafetyQuestionModelJsonParseMethod
	// * @Description: 安全问题，密保问题
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return List<SafetyQuestionModel> 返回类型
	// * @throws
	// */
	// public static List<SafetyQuestionModel>
	// SafetyQuestionModelJsonParseMethod(
	// String jsonstr) {
	// String result = "";
	// try {
	// result = new JSONObject(jsonstr).getString("data");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// Type listType = new TypeToken<List<SafetyQuestionModel>>() {
	// }.getType();
	// Gson gson = new Gson();
	// List<SafetyQuestionModel> linkedList = gson.fromJson(result, listType);
	// return linkedList;
	// }
	//
	// /**
	// * @Title: StoreGoodsModelJsonParseMethod
	// * @Description: 店铺商品列表-采购
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return List<StoreGoodsModel> 返回类型
	// * @throws
	// */
	// public static List<StoreGoodsModel> StoreGoodsModelJsonParseMethod(
	// String jsonstr) {
	// String result = "";
	// try {
	// result = new JSONObject(jsonstr).getJSONObject("data").getString(
	// "list");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// Type listType = new TypeToken<List<StoreGoodsModel>>() {
	// }.getType();
	// Gson gson = new Gson();
	// List<StoreGoodsModel> linkedList = gson.fromJson(result, listType);
	// return linkedList;
	// }
	//
	// /**
	// * @Title: StoreGoods_SubmitModelJsonParseMethod
	// * @Description: 店铺商品列表-提报
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return List<StoreGoods_SubmitModel> 返回类型
	// * @throws
	// */
//	public static List<WuYeRepairTypeModel> WuYeRepairTypeModelListJsonParseMethod(String jsonstr) {
//		Type listType = new TypeToken<List<WuYeRepairTypeModel>>() {
//		}.getType();
//		Gson gson = new Gson();
//		List<WuYeRepairTypeModel> linkedList = gson.fromJson(jsonstr, listType);
//		return linkedList;
//	}

	/**
	 * 报异常： com.google.gson.internal.StringMap cannot be cast to com
	 * 
	 * @param jsonstr
	 * @param class1
	 * @return
	 */
	public static <T> List<T> getJsonToModelList(String jsonstr, Class<T> class1) {

		Type listType = new TypeToken<List<T>>() {
		}.getType();
		Gson gson = new Gson();
		List<T> linkedList = gson.fromJson(jsonstr, listType);
		return linkedList;
	}

	/**
	 * 解析数组
	 * 
	 * @param s
	 * @param clazz
	 * @return
	 */ 
	public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
		T[] arr = new Gson().fromJson(s, clazz);
		return Arrays.asList(arr);
	}
	//
	// /**
	// * @Title: MyWalletModelJsonParseMethod
	// * @Description:我的钱包json
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return MyWalletModel 返回类型
	// * @throws
	// */
	//
	// public static List<MyWalletModel> MyWalletModelJsonParseMethod(
	// String jsonstr) {
	// String result = "";
	// try {
	// result = new JSONObject(jsonstr).getJSONObject("data").getString(
	// "list");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// Type listType = new TypeToken<List<MyWalletModel>>() {
	// }.getType();
	// Gson gson = new Gson();
	// List<MyWalletModel> linkedList = gson.fromJson(result, listType);
	// return linkedList;
	// }
	//
	// /**
	// * @throws JSONException
	// * @throws JsonSyntaxException
	// * @Title: Merchant_StoreJsonParseMethod
	// * @Description: 扫描二维码json解析
	// * @param @param jsonstr
	// * @param @return 设定文件
	// * @return MerchantModel 返回类型
	// * @throws
	// */
	// public static MerchantModel Merchant_StoreJsonParseMethod(String jsonstr)
	// throws JsonSyntaxException, JSONException {
	// // "data": {
	// // "id": "42",
	// // "storeId": "24",
	// // "goods": {
	// // "id": "1",
	// // "name": "iphone6移动4G土豪金",
	// // "title": "iphone6移动4G土豪金",
	// // "price": "4999.00",
	// // "barcode": "222222"
	// // },
	// // "picture": ""
	// // },
	// Gson gson = new Gson();
	// MerchantModel bean = gson.fromJson(
	// new JSONObject(jsonstr).getString("data"), MerchantModel.class);
	// String result = "";
	// try {
	// result = new JSONObject(jsonstr).getJSONObject("data").getString(
	// "goods");
	// MerchantModel.Goods_m goods_m = gson.fromJson(result,
	// MerchantModel.Goods_m.class);
	// bean.setGoods_m(goods_m);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return bean;
	// }

}
