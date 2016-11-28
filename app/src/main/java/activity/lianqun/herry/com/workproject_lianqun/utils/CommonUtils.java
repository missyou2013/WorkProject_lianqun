package activity.lianqun.herry.com.workproject_lianqun.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import activity.lianqun.herry.com.workproject_lianqun.R;
import activity.lianqun.herry.com.workproject_lianqun.widgets.pull.ChooseTime.DateTimePickerDialog;

/**
 * @ClassName: CommonUtils
 * @Description: 工具�?
 * @author: liuxj
 * @date: 2014-4-15 下午1:11:43
 */
public class CommonUtils {
    // Log.i("TAG", "开关按钮状态=" + isChecked);
    // if (isChecked) {
    // // 密码隐藏状态
    // loginpassword.setInputType(0x90);
    // loginpassword
    // .setTransformationMethod(HideReturnsTransformationMethod
    // .getInstance());
    // } else {
    // // 密码显示状态
    // loginpassword.setInputType(0x81);
    // loginpassword
    // .setTransformationMethod(PasswordTransformationMethod
    // .getInstance());
    // }
    // Log.i("togglebutton", "" + isChecked);
    // loginpassword.postInvalidate();
    // /**
    // * 压缩图片，并把压缩后的图片保存到cance文件夹里
    // *
    // * @param systemPath
    // * @param imgPath
    // * @param fileName
    // * @return
    // */
    // public static String getUrl(Context c, String systemPath, String imgPath,
    // String fileName) {
    // String imgUrl = "";
    // String suffixImg = fileName.substring(fileName.lastIndexOf(".") + 1,
    // fileName.length());
    // int imgType = 1;
    // if (suffixImg.contains("png")) {
    // imgType = 2;
    // }
    // if (Utils.isPicOrNot(imgPath)) {
    // if (!fileName.contains(APP_FOLDER)) {
    // fileName = APP_FOLDER + fileName;
    // double size = new NewUtils().getFileSize(c, imgPath);
    // int opt = 0;
    // if (size <= 1) {
    // opt = 2;
    // } else {
    // opt = 5;
    // }
    // HttpUtil.transImage(imgPath, systemPath + "/" + fileName, 60,
    // imgType, opt);
    // }
    // imgUrl = systemPath + "/" + fileName;
    // return imgUrl;
    // }
    // return "";
    // }

    // /**
    // * @Title: goToLoginActivity
    // * @Description: 账号异地登陆或者店铺被禁止后
    // * @param @param from_activity 设定文件
    // * @return void 返回类型
    // * @throws
    // */
    // public static void goToLoginActivity(Activity from_activity) {
    // Intent intent = new Intent(from_activity, LoginActivity.class);
    // from_activity.startActivity(intent);
    // from_activity.overridePendingTransition(R.anim.push_left_in,
    // R.anim.push_left_out);
    // }

    /**
     * 非空判断（"",null,"null"）
     *
     * @param value 要验证字符串
     * @return
     */
    public static boolean isNull(String value) {
        if (value == null || value.trim().equals("") || value.trim().equals("null")) {
            return true;
        } else
            return false;
    }

    /**
     * 转义
     *
     * @param s
     * @return
     */
    public static String convert(String s) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = s.indexOf("\\u", pos)) != -1) {
            sb.append(s.substring(pos, i));
            if (i + 5 < s.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(s.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }

    /**
     * 四舍五入
     *
     * @param
     * @return string
     */
    public static String FloatToStr(float f) {
        // float f = (float) 34.232323;
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        String str = String.valueOf(f1);
        return str;
    }

    /**
     * 取整数
     *
     * @param str
     * @return
     */
    public static String FourFiveTo(String str) {
        String d = new BigDecimal(str).setScale(0, BigDecimal.ROUND_HALF_UP) + "";
        return d;
    }

    /**
     * 判断是否数字(只能判断整数，带小数的不可以)
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否数字,小数也可以判断
     *
     * @param s
     * @return
     */
    public static boolean isDigit(String s) {
        Pattern p = Pattern.compile("\\d+\\.?\\d*");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isOnline(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断手机号码
     *
     * @param mobiles
     * @return false不是手机号，true是手机号
     */
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        // System.out.println(m.matches() + "---");
        return m.matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /***
     * 获取屏幕的宽 尺寸像素
     *
     * @param
     * @return int
     */
    public static int getScreenWidth(Activity cont) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        cont.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        return dm.widthPixels;
    }

    public static int getScreenWidthDP(Activity cont) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        cont.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        return (int) (dm.widthPixels / dm.density);
    }

    /***
     * 获取屏幕的高 尺寸像素
     *
     * @param
     * @return int
     */
    public static int getScreenHeight(Activity cont) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        cont.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        return dm.heightPixels;
    }


    // // 显示错误信息
    // public static void showErrorMsg(Context context, VolleyError error) {
    // if (error.networkResponse != null) {
    // if (error.networkResponse.statusCode == 500) {
    // Toast.makeText(context, "服务器返回异常，请稍后再试！", Toast.LENGTH_SHORT)
    // .show();
    // return;
    // }
    // } else {
    // Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
    // return;
    // }
    // if (Base64.decode(
    // error.networkResponse.headers.get("errorDescription"), 0) != null) {
    // String str = new String(Base64.decode(
    // error.networkResponse.headers.get("errorDescription"), 0));
    // Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    // } else {
    // Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
    // }
    // }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 转换图片成圆角
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        // final float roundPx = pixels;

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, 22, 22, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * GetDistance:根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param @param  lng1
     * @param @param  lat1
     * @param @param  lng2
     * @param @param  lat2
     * @param @return
     * @return double
     * @throws
     * @author zuyp
     * @version
     * @Date 2013 2013-5-14 上午11:40:31
     * @since Ver 1.1
     */
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000 / 1000.0;
        DecimalFormat df = new DecimalFormat("###.0");

        return Double.parseDouble(df.format(s));
    }

    /**
     * 检查SD卡状态，如果sd卡不存在或者SD卡剩余空间不足禁止程序启动
     *
     * @return
     */
    public static boolean checkSDCardState() {
        String state = Environment.getExternalStorageState();
        File sd = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(sd.getPath());
        double blockSize = stat.getBlockSize();
        double freeBlocks = stat.getAvailableBlocks();
        double countSize = blockSize * freeBlocks;
        long role = 1024 * 1024 * 10;
        if (state.equals(Environment.MEDIA_MOUNTED) && countSize > role) {
            return true;
        }
        return false;
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static String getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);// 秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    // 1 => '查询成功',
    // 2 => '发送成功',
    // 3 => '登陆成功',
    // 6 => '修改成功',
    // 7 => '回复成功',
    // 8 => '引用成功',
    // 9 => '添加成功',
    // -1 => '未登录或是同一个账号在其他手机登陆',
    // -2 => '参数不全',
    // -3 => '手机号或密码不正确',
    // -4 => '短信验证码错误',
    // -5 => '你输入的原密码错误',
    // -6 => '用户名或是密码不存在',
    // -10=> '亲，没有符合您要求的',
    // -7=> '删除成功',

    /**
     * // * 后台返回的信息提示 // * // * @param context // * @param e //
     */
    // public static void showServicePrompt(Context context, int e) {
    // String msg[] = { "查询成功", "发送成功", "登陆成功", "修改成功", "回复成功", "引用成功",
    // "添加成功", "未登录或是同一个账号在其他手机登陆", "参数不全", "手机号或密码不正确", "短信验证码错误",
    // "你输入的原密码错误", "用户名或是密码不存在", "亲，没有符合您要求的", "删除成功" };
    // switch (e) {
    // case 1:
    // ShowToast.showToast(msg[0], context);
    // break;
    // case 2:
    // ShowToast.showToast(msg[1], context);
    // break;
    // case 3:
    // ShowToast.showToast(msg[2], context);
    // break;
    // // case 4:
    // // ShowToast.showToast(msg[3], context);
    // // break;
    // // case 5:
    // // ShowToast.showToast(msg[4], context);
    // // break;
    // case 6:
    // ShowToast.showToast(msg[3], context);
    // break;
    // case 7:
    // ShowToast.showToast(msg[4], context);
    // break;
    // case 8:
    // ShowToast.showToast(msg[5], context);
    // break;
    // case 9:
    // ShowToast.showToast(msg[6], context);
    // break;
    // case -1:
    // ShowToast.showToast(msg[7], context);
    // break;
    // case -2:
    // ShowToast.showToast(msg[8], context);
    // break;
    // case -3:
    // ShowToast.showToast(msg[9], context);
    // break;
    // case -4:
    // ShowToast.showToast(msg[10], context);
    // break;
    // case -5:
    // ShowToast.showToast(msg[11], context);
    // break;
    // case -6:
    // ShowToast.showToast(msg[12], context);
    // break;
    // case -10:
    // ShowToast.showToast(msg[13], context);
    // break;
    // case -7:
    // ShowToast.showToast(msg[14], context);
    // break;
    // default:
    // break;
    // }
    //
    // }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getVersionName
     * @Description: 获取当前版本号
     */
    public static String getVersionName(Activity activity) {
        // 获取packagemanager的实例
        PackageManager packageManager = activity.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    // 圆角----内存溢出
    public static Bitmap roundCorners(final Bitmap source, final float radius) {
        try {
            int width = source.getWidth();
            int height = source.getHeight();
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(android.graphics.Color.WHITE);
            Bitmap clipped = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(clipped);
            canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(source, 0, 0, paint);
            source.recycle();
            return clipped;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return source;
    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

//	/**
//	 * post传参数
//	 *
//	 * @param keyValues
//	 * @return
//	 */
//	public static BaseAjaxParams AddAjaxParamValuse(List<NameValues> keyValues, Activity context) {
//		BaseAjaxParams params = new BaseAjaxParams();
//		params.put("version", CommonUtils.getVersionName(context));
//		params.put("apikey", Constants.API_KEY);
//		params.put("secretKey", Constants.SECRET_KEY);
//		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
//		params.put("deviceType", "1");
//		for (int i = 0; i < keyValues.size(); i++) {
//			params.put(keyValues.get(i).getKeyName(), keyValues.get(i).getKeyValues().toString());
//		}
//		return params;
//	}
//
//	/**
//	 * dialog
//	 *
//	 * @param context
//	 * @return
//	 */
//	public static MyDialog dialog;
//
//	//
//	public static void showDialog(Context context, String title, String str) {
//		if (dialog != null) {
//			dialog.dismiss();
//		}
//		// 引用自定义布局
//		View v = LayoutInflater.from(context).inflate(R.layout.my_dialog_textview, null);
//		TextView textView = (TextView) v.findViewById(R.id.textv_content);
//		textView.setText(title);
//		dialog = new MyDialog(context, title, v, new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// 关闭dialog
//				dialog.dismiss();
//				dialog.dismiss();
//				// finish();
//			}
//		}, 1, str);
//		// 显示dialog
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.setCancelable(false);
//		dialog.show();
//	}
    /**
     * 显示加载框
     *
     * @param context
     * @param msg
     */
    private static CustomDialog loadingDialog = null;

    public static void showLoadingDialog(Context context) {
        loadingDialog = new CustomDialog(context, R.style.CustomDialog);
        loadingDialog.show();
    }

    /**
     * 隐藏加载框
     *
     * @param context
     * @param
     */
    public static void hideLoadingDialog(Context context) {
        try {
            if (loadingDialog != null || loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 时间选择框
     */
    public static void showDialog(final Activity activity,
                                  final TextView textView) {
        DateTimePickerDialog dialog = new DateTimePickerDialog(activity,
                System.currentTimeMillis());
        dialog.setOnDateTimeSetListener(new DateTimePickerDialog.OnDateTimeSetListener() {
            public void OnDateTimeSet(AlertDialog dialog, long date) {
                Toast.makeText(activity, "您输入的日期是：" + getStringDate(date),
                        Toast.LENGTH_LONG).show();

                String date_string = String.valueOf(getStringDate(date));

                textView.setText(date_string);


            }
        });
        dialog.show();
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(Long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);

        return dateString;
    }
}
