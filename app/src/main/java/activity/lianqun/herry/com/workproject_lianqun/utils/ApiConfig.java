package activity.lianqun.herry.com.workproject_lianqun.utils;

/**
 * @author lxj
 * @version V1.0
 * @Title: ApiConfig.java
 * @Package com.herry.shequ.api
 * @Description: 接口api
 * @date 2016-1-13 上午8:37:01
 */
public interface ApiConfig {
    // final String BASE_URL_front = "http://222.173.68.166:7777/";
    // final String BASE_URL_front = "http://223.255.32.230:8888/";//生产
    String BASE_URL_front = "http://192.168.1.102:8888/waiqin/client/";

    // final String BASE_URL_front = "http://119.166.249.3:8888/";
    // final String BASE_URL_front = "http://192.168.1.102:8080/";
//      String BASE_URL = BASE_URL_front + "SmartCommunity_API/";
    String BASE_URL =
            BASE_URL_front;// 测试用

    // 图片url
    // final String IMAGE_HOST_URL =
    // "http://223.255.32.230:8888/SmartCommunity/";
    String IMAGE_HOST_URL = "http://www.lianqunwuye.com/SmartCommunity/";
    // final String IMAGE_HOST_URL =
    // "http://222.173.68.166:7777/SmartCommunity/";


    // 登录
    String LOGIN_URL = BASE_URL + "manager/login";

    // 公司列表
    String COMPANY_LISTS = BASE_URL + "manager/selectcompany";

    //修改密码
    String XIUGAI_MIMA = BASE_URL + "/manager/updatepass";

    //申请 请假/报销/其他
    String APPLY = BASE_URL + "/apply/add";

    // 签到-签退
    String QIANDAO_QIANTUI = BASE_URL + "sign/signin";

    // 查询签到记录
    String CHAXUN_QIANDAO = BASE_URL + "sign/query";


    // 摄像头/camera/query      传cid  pagenum  pagesize

    String SHEXIANGTOU = BASE_URL + "camera/query";

    //通知列表 传userid,cid,pagenum,pagesize
    String TONGZHI_LIST = BASE_URL + "note/query";

    //通知详情 传id
    String TONGZHI_DETAILS = BASE_URL + "note/info";
}
