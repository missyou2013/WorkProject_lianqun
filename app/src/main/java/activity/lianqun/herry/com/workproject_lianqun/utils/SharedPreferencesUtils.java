package activity.lianqun.herry.com.workproject_lianqun.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import activity.lianqun.herry.com.workproject_lianqun.models.User;


public class SharedPreferencesUtils {

    private static final String SharedPreferencesName = "activity.lianqun.herry.com.workproject_lianqun.activity";

    // 纬度坐标
    public static void setLatitude(Activity activity, float lat) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putFloat("latitude", lat);
        editor.commit();
    }

    public static float getLatitude(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        float flag = settings.getFloat("latitude", 0);
        return flag;
    }

    // 经度坐标
    public void setLongitude(Activity activity, float lat) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putFloat("longitude", lat);
        editor.commit();
    }

    public float getLongitude(Activity activity) {

        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        float flag = settings.getFloat("longitude", 0);
        return flag;
    }

    /**
     * 设置是否记住登录状�? true：记�?false：不记住
     *
     * @param activity
     * @param
     */
    public static void setLoadingStatement(Activity activity, Boolean flag) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putBoolean("is_loading", flag);
        editor.commit();
    }

    /**
     * 获取是否记住登录状�?
     *
     * @param activity
     * @param
     * @return
     */
    public static boolean getLoadingStatement(Activity activity) {
        boolean flag = false;
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        flag = settings.getBoolean("is_loading", false);
        return flag;
    }

    /**
     * 设置登录状�? true：登�?false：未登录
     *
     * @param activity
     * @param username
     */
    public static void setLoading(Activity activity, String username, String pwd, Boolean flag) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putBoolean("loading", flag);
        editor.putString("username", username);
        editor.putString("pwd", username);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取登录状�?
     *
     * @param activity
     * @param
     * @return
     */
    public static boolean getLoading(Context activity) {
        boolean flag = false;
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        flag = settings.getBoolean("loading", false);
        return flag;
    }

    /**
     * 设置用户id
     *
     * @param activity
     * @param ID
     * @param
     */
    public static void setUID(Activity activity, String ID) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("uid", ID);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取用户id
     *
     * @param activity
     * @param
     * @return
     */

    public static String getUID(Activity activity) {
        String flag = "";
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        flag = settings.getString("uid", "");
        return flag;
    }

    /**
     * 获取用户�?
     *
     * @param activity
     * @param
     * @return
     */
    public static String getLoadingUserName(Activity activity) {
        String flag = "";
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        flag = settings.getString("username", null);
        return flag;
    }

    /**
     * 设置地址列表状�? true：登�?false：未登录
     *
     * @param activity
     * @param
     */
    public static void setprocitys(Activity activity) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putBoolean("pc", true);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取地址列表状�?
     *
     * @param activity
     * @param
     * @return
     */
    public static boolean getprocitys(Activity activity) {
        boolean flag = false;
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        flag = settings.getBoolean("pc", false);
        return flag;
    }

    /**
     * 设置用户手机�?
     *
     * @param activity
     * @param
     * @param
     */
    public static void setUserPhone(Activity activity, String telphone) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("tel", telphone);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取用户手机�?
     *
     * @param activity
     * @param
     * @return
     */

    public static String getUserPhone(Activity activity) {
        String flag = "";
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        flag = settings.getString("tel", null);
        return flag;
    }

    /**
     * 设置用户是否第一次实用软�?
     *
     * @param activity
     * @param
     * @param
     */
    public static void setFirstUseApp(Activity activity) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putBoolean("first", false);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取用户是否第一次实用软�?
     *
     * @param activity
     * @param
     * @return
     */

    public static boolean getFirstUseApp(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        boolean flag = settings.getBoolean("first", true);
        return flag;
    }

    /**
     * 设置用户token
     *
     * @param activity
     * @param
     * @param
     */
    public static void setCompanyId(Activity activity, String CompanyId) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("userToken", CompanyId);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取用户token
     *
     * @param context
     * @param
     * @return
     */

    public static String getCompanyId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
        String flag = settings.getString("userToken", null);
        return flag;
    }

    /**
     * 设置用户信息�?昵称，�?别，年龄，电话，头像URL
     *
     * @param
     * @param
     * @param
     */
    public static void setUserAllinfor(Context context, String nickname, String gender, String age, String phone,
                                       String photoURL) {// 写入XML数据
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        // editor.putString("shake_user_id", id);
        editor.putString("shake_user_nickname", nickname);
        editor.putString("shake_user_gender", gender);
        editor.putString("shake_user_age", age);
        editor.putString("shake_user_phone", phone);
        editor.putString("shake_user_photo_url", photoURL);
        editor.commit(); // �?��要记得提�?
    }

    // /**
    // * 获取用户信息�?id,昵称，�?别，年龄，电话，头像URL
    // *
    // * @param context
    // * @return
    // */
    //
    // public static User getUserAllinfor(Context context) {
    // User user=new User();
    // SharedPreferences settings = context.getSharedPreferences(
    // SharedPreferencesName, 0);
    // // String id = settings.getString("shake_user_id", null);
    // String nickname = settings.getString("shake_user_nickname", null);
    // String gender = settings.getString("shake_user_gender", null);
    // String age = settings.getString("shake_user_age", null);
    // String phone = settings.getString("shake_user_phone", null);
    // String photoURL = settings.getString("shake_user_photo_url", null);
    //
    //
    // // user.setId(id);
    // user.setNickname(nickname);
    // user.setGender(gender);
    // user.setAge(age);
    // user.setPhone(phone);
    //
    //
    // return user;
    // }

    /**
     * 设置用户余额
     *
     * @param
     * @param
     * @param
     */
    public static void setUserBalance(Context context, String b) {// 写入XML数据
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("balance", b);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取用户余额
     *
     * @param context
     * @param
     * @return
     */

    public static String getUserBalance(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
        String flag = settings.getString("balance", "0");
        return flag;
    }

    //
    // /**
    // * 设置用户金钱余额
    // */
    // public static void setMyBalance(Context context,User user) {// 写入XML数据
    // SharedPreferences settings = context.getSharedPreferences(
    // SharedPreferencesName, 0);
    // SharedPreferences.Editor editor = settings.edit();
    // editor.putString("my_balance", user.getBalance());
    // editor.commit(); // �?��要记得提�?
    // }

    /**
     * 获取用户金钱余额
     */

    public static String getMyBalance(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
        String flag = settings.getString("my_balance", "0");
        return flag;
    }

    /**
     * 获取腾讯微博token
     *
     * @param context
     * @param
     * @return
     */

    public static String getTXToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
        String token = settings.getString("token", "");
        return token;
    }

    /**
     * 获取腾讯微博token_secret
     *
     * @param context
     * @param
     * @return
     */

    public static String getTXSecretToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
        String token_secret = settings.getString("token_secret", "");
        return token_secret;
    }

    /**
     * 设置用户是否第一次获取我的余�?
     *
     * @param activity
     * @param
     * @param
     */
    public static void setFirstGetMyBalance(Activity activity) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putBoolean("first_balance", true);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取用户是否第一次获取我的余�?
     *
     * @param activity
     * @param
     * @return
     */

    public static boolean getFirstGetMyBalance(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        boolean flag = settings.getBoolean("first_balance", false);
        return flag;
    }

    /**
     * 保存头像图片
     *
     * @param activity
     * @param
     */
    public static void setUserPhoto(Activity activity, String photoURL) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("login_bean_picture", photoURL);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取头像图片
     *
     * @param activity
     * @return
     */
    public static String getUserPhoto(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        String photo = settings.getString("login_bean_picture", "");
        return photo;
    }

    /**
     * 保存上一次登录定位的城市
     *
     * @param activity
     * @param
     */
    public static void setUserCity(Activity activity, String city) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("city", city);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取上一次登录定位的城市
     *
     * @param activity
     * @return
     */
    public static String getUserCity(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        String city = settings.getString("city", "");
        return city;
    }

    /**
     * 清除pushTag
     *
     * @param activity
     * @return
     */
    public static void clearPushTag(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor et = settings.edit();
        et.remove("tags");
        et.commit();
    }

    /**
     * 保存上一次登录使用的昵称
     *
     * @param activity
     * @param
     */
    public static void setUserNickName(Activity activity, String city) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("nickname", city);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取上一次登录使用的昵称
     *
     * @param activity
     * @return
     */
    public static String getUserNickName(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        String city = settings.getString("nickname", "");
        return city;
    }

    /**
     * 保存上一次登录使用的年龄
     *
     * @param activity
     * @param
     */
    public static void setUserAge(Activity activity, String age) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("age", age);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取上一次登录使用的年龄
     *
     * @param activity
     * @return
     */
    public static String getUserAge(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        String age = settings.getString("age", "");
        return age;
    }

    /**
     * 保存上一次登录使用的性别
     *
     * @param activity
     * @param
     */
    public static void setUserGender(Activity activity, String gender) {// 写入XML数据
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("gender", gender);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取上一次登录使用的性别
     *
     * @param activity
     * @return
     */
    public static String getUserGender(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        String gender = settings.getString("gender", "");
        return gender;
    }

	/**
	 * 保存用户信息
	 *
	 * @return
	 */
	public static void setUserInfor(Activity activity, User model) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("user_id", DESUtil.encrypt(String.valueOf(model.getId())));
		editor.putString("user_name", DESUtil.encrypt(model.getName()));
		editor.putString("user_cname", DESUtil.encrypt(model.getCname()));
        editor.putString("user_sex", DESUtil.encrypt(String.valueOf(model.getSex())));
        editor.putString("user_phone", DESUtil.encrypt(model.getPhone()));
        editor.putString("user_loginname", DESUtil.encrypt(model.getLoginname()));
        editor.putString("user_status", DESUtil.encrypt(String.valueOf(model.getStatus())));
        editor.putString("user_companyid", DESUtil.encrypt(String.valueOf(model.getCompanyid())));
        editor.putString("user_photo", DESUtil.encrypt(String.valueOf(model.getPhoto())));
        editor.putString("user_userid", DESUtil.encrypt(String.valueOf(model.getUserid())));

		editor.commit();
	}

	/**
	 * 获取用户信息
	 *
	 * @param activity
	 */
	public static User getUserInfor(Context activity) {
        User bean = new User();
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        bean.setId(Integer.parseInt(DESUtil.decrypt(settings.getString("user_id", ""))));
        bean.setName(DESUtil.decrypt(settings.getString("user_name", "")));
        bean.setCname(DESUtil.decrypt(settings.getString("user_cname", "")));
        bean.setSex(Integer.parseInt(DESUtil.decrypt(settings.getString("user_sex", ""))));
        bean.setPhone(DESUtil.decrypt(settings.getString("user_phone", "")));
        bean.setLoginname(DESUtil.decrypt(settings.getString("user_loginname", "")));
        bean.setStatus(Integer.parseInt(DESUtil.decrypt(settings.getString("user_status", ""))));
        bean.setCompanyid(Integer.parseInt(DESUtil.decrypt(settings.getString("user_companyid", ""))));
        bean.setPhoto(DESUtil.decrypt(settings.getString("user_photo", "")));
        bean.setUserid(Integer.parseInt(DESUtil.decrypt(settings.getString("user_userid", ""))));
		return bean;
	}

    /**
     * 获取Authorization
     *
     * @param activity
     * @return
     */
    public static String getLoadingAuthorization(Activity activity) {
        String flag = "";
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        flag = settings.getString("login_bean_authorization", null);
        return flag;
    }

    /**
     * 保存push信息
     *
     * @param activity
     * @param
     */
    public static void setPushInfor(Context activity, String push_channelid, String push_userid) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        Editor editor = settings.edit();
        editor.putString("push_channelid", push_channelid);
        editor.putString("push_userid", push_userid);
        editor.commit(); // �?��要记得提�?
    }

    /**
     * 获取push_channelid信息
     *
     * @param activity
     * @return
     */
    public static String getPushInfor_channelid(Context activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        String gender = settings.getString("push_channelid", "");
        return gender;
    }

    /**
     * 获取push_userid信息
     *
     * @param activity
     * @return
     */
    public static String getPushInfor_userid(Context activity) {
        SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
        String gender = settings.getString("push_userid", "");
        return gender;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
