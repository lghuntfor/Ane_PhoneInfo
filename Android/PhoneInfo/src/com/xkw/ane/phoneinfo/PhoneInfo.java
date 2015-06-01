package com.xkw.ane.phoneinfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.phoneinfo.R;

/**
 * 此类封装了手相相关信息的具体获取方法： 每个方法对应一个FREFunction的具体实现类
 */

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class PhoneInfo {
	private Context context = null;

	private TelephonyManager tm = null;
	private ActivityManager am = null;
	private WindowManager wm = null;
	private WifiManager wifiMgr = null;
	private ConnectivityManager connectMgr = null;
	private LocationManager locationManager = null;
	private InputMethodManager inputMgr = null;
	private NotificationManager notifMgr = null;
	private DownloadManager downloadMgr = null;

	public PhoneInfo(Context context) {
		this.context = context;
		if (context != null) {
			// 获取手机管理器
			tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			// 获取活动管理器
			am = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			// 获取窗口管理器
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			// 获取WIFI管理器
			wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			// 获取连接管理器
			connectMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			// 获取位置管理器
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			// 获取输入管理器
			inputMgr = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			//消息管理器
			notifMgr = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			//下载管理器
			downloadMgr = (DownloadManager) context
					.getSystemService(Context.DOWNLOAD_SERVICE);
		}
	}

	// 1.手机IMEI号
	public String getImei() {
		if (tm != null) {
			return tm.getDeviceId();
		}
		return "NULL";
	}

	// 2.手机系统版本号
	public String getSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	// 3.手机当前系统的可用内存大小
	public String getAvailMemory() {
		MemoryInfo mi = new MemoryInfo();
		if (am == null) {
			return "NULL";
		}
		am.getMemoryInfo(mi);
		// 将获取的内存大小格式化
		return Formatter.formatFileSize(context, mi.availMem) + "";
	}

	// 4.获取手机屏幕的宽度
	@SuppressWarnings("deprecation")
	public String getWidth() {
		return wm.getDefaultDisplay().getWidth() + "";
	}

	// 5.获取手机屏幕的高度
	@SuppressWarnings("deprecation")
	public String getHeight() {
		return wm.getDefaultDisplay().getHeight() + "";
	}

	// 6.获取手机SIM卡的IMSI号
	public String getImsi() {
		String imsi = tm.getSubscriberId();
		if (imsi != null) {
			return imsi;
		}
		return "未插入手机卡,无法获取到手机SIM卡的imsi号";
	}

	// 7.获取手机型号
	public String getPhoneMobel() {
		return android.os.Build.MODEL;
	}

	// 8.获取手机品牌
	public String getPhoneBrand() {
		return android.os.Build.BRAND;
	}

	// 9.获取手机号码
	public String getPhoneNumber() {
		return tm.getLine1Number();
	}

	// 10.获取手机MAC地址(需要在开启手机WIFI时才能调用到)
	public String getMacAddress() {
		if (wifiMgr != null) {
			WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
			if (wifiInfo != null) {
				wifiInfo.getMacAddress();
				return wifiInfo.getMacAddress();
			}
		}
		return "需要在开启wifi时才能获取到mac地址";
	}

	// 获取手机CPU信息
	private String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" }; // 1-CPU型号 //2-CPU频率
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return cpuInfo;
	}

	// 11.获取手机CPU型号
	public String getCpuMobel() {
		return getCpuInfo()[0];
	}

	// 12.获取手机CPU频率
	public String getCpuFrequency() {
		return getCpuInfo()[1];
	}

	// 13.获取手机运营商
	public String getPhoneOperators() {
		String IMSI = getImsi();
		String ProvidersName = null;
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		return ProvidersName;
	}

	// 14.返回用户手机是否处于漫游状态
	public String getNetWorkRoaming() {
		if (tm.isNetworkRoaming()) {
			return "漫游";
		}
		return "非漫游";
	}

	// 15.Wife是否打开
	public String isWifiOpened() {
		if (wifiMgr != null) {
			return wifiMgr.isWifiEnabled() + "";
		}
		return "false";
	}

	// 16.GPS是否打开
	public String isGpsEnabled() {
		if (locationManager != null) {
			return locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER) + "";
		}
		return "false";
	}

	//跳转到指定的系统设置页面
	public String skipToSetting(String setStr) {
		Field field = null;
		try {
			
			field = Settings.class.getField(setStr);
			String action = field.get(new Settings()).toString();
			context.startActivity(new Intent(action));
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return "NULL";
	}

	// 17.返回手机当前是否有网络连接
	public String isConnect() {
		if (connectMgr != null) {
			NetworkInfo[] networkInfos = connectMgr.getAllNetworkInfo();
			if (networkInfos != null && networkInfos.length > 0) {
				for (NetworkInfo netWorkInfo : networkInfos) {
					if (netWorkInfo != null
							&& netWorkInfo.getState() == NetworkInfo.State.CONNECTED) {
						return "true";
					}
				}
			}
		}
		return "false";
	}

	// 18.返回手机的当前网络连接状态详情
	public String getNetWorkStatus() {
		if (connectMgr != null) {
			NetworkInfo info = connectMgr.getActiveNetworkInfo();
			if (info != null) {
				if (info.getType() == ConnectivityManager.TYPE_WIFI) {
					return "WIFI";
				} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
					switch (info.getSubtype()) {
					case TelephonyManager.NETWORK_TYPE_GPRS:
					case TelephonyManager.NETWORK_TYPE_EDGE:
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN:
						return "2G";

					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
					case TelephonyManager.NETWORK_TYPE_HSPAP:
						return "3G";

					case TelephonyManager.NETWORK_TYPE_LTE:
						return "4G";
					}
				}
			}
		}
		return "NULL";
	}

	// 19.获取sd卡路径
	public String getSDPath() {
		return Environment.getExternalStorageDirectory().toString();
	}

	// 20.获取data目录
	public String getDataDirectory() {
		return Environment.getDataDirectory().toString();
	}
	
	//获取根目录
	public String getRootDirectory() {
		return Environment.getRootDirectory().toString();
	}

//	public String downloadFile(final String requestUrl, final String filename, final String requestMethod) {
//		
//		new Thread() {
//			public void run() {
//				String apkPath = downloadFile(requestUrl, filename, requestMethod);
//				installApk(apkPath);
//			};
//		}.start();
//		return getSDPath()+"/"+filename;
//	}
	
	// 21.文件下载
	@SuppressLint("DefaultLocale")
	public String downloadFile(String requestUrl, String filename, String requestMethod) {
		String downloadDir = getSDPath();
		requestMethod = requestMethod.toUpperCase();
		if(!"POST".equals(requestMethod)) {
			requestMethod = "GET";
		}
		
		String filePath = downloadDir + "/" + filename;
		File destFile = new File(filePath);
		if (destFile.exists()) {
			destFile.delete();
		}

		HttpURLConnection conn = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setRequestMethod(requestMethod);
			conn.setDoInput(true);
			conn.setDoInput(true);
			conn.connect();
			in = new BufferedInputStream(conn.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(filePath));
			
			byte[] buf = new byte[100*1024];
			int len = 0;
			
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "NULL";
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return filePath;
	}

	
	//调用系统键盘
	public String callKeyboard() {
		inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		inputMgr.showSoftInput(new View(context), InputMethodManager.SHOW_FORCED);
		return "NULL";
	}
	
	
	/**
	 * 发送消息通知
	 * @param title 消息推送标题
	 * @param content 消息内容
	 * @param url 跳转链接
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String messageNotify(String title, String content, String url) {
		 int icon = R.drawable.ic_launcher;
		 long when = System.currentTimeMillis();
		 Notification notification = new Notification(icon, title, when);
		
		 //定义下拉通知栏时要展现的内容信息
		 Context ctx = context.getApplicationContext();
		 Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		 PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		 notification.setLatestEventInfo(ctx, title, content, contentIntent); 
		 notification.defaults = Notification.DEFAULT_SOUND;
		 notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_AUTO_CANCEL; //不可清除，自动取消 
		
		 //用notifMgr的notify方法通知用户生成标题栏消息通知
		 notifMgr.notify(1, notification);
		
		 return "messageNotify";
	}
	
	
	public String installApk(String apkPath) {
		File file = new File(apkPath);//apk的路径
		Uri uri = Uri.fromFile(file);//file是sd卡上app文件的对象
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
		return "installApk";
	}
	
	
	/**
	 * 防止系统休眠
	 * @return
	 */
	public String disDormant() {
		((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		return "disDormant";
	}
	
	
	/**
	 * 调用系统浏览器
	 * @param url
	 * @return
	 */
	public String callSystemBrowser(String url) {
		Intent intent= new Intent();       
	    intent.setAction("android.intent.action.VIEW");   
	    Uri content_url = Uri.parse(url);  
	    intent.setData(content_url); 
	    context.startActivity(intent);
		return "callSystemBrowser";
	}
	
	
	/**
	 * 获取项目配置文件中mete_data数据
	 * @param key
	 * @return
	 */
	public String getMeteData(String key) {
		ApplicationInfo appInfo = null ;
		String meteDate = null;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			meteDate = appInfo.metaData.getString(key);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return meteDate;
	}
	
	
	/**
	 * apk下载
	 * @param uri
	 * @param title
	 * @param desc
	 * @param d
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public String downloadApk(String uri, String title, String desc, String downloadDir) {
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
		request.allowScanningByMediaScanner();
		request.setTitle(title);//设置下载中通知栏提示的标题
		request.setDescription(desc);//设置下载中通知栏提示的介绍
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setMimeType("application/cn.trinea.download.file");
		request.setDestinationInExternalPublicDir(downloadDir, "学易爱听写_v1.210.apk");
		long enqueue = downloadMgr.enqueue(request);
		return enqueue+"";
	}
	
	
	/**
	 * android自带的分享功能
	 * @param activityTitle
	 * @param msgTitle
	 * @param msgText
	 * @param imgPath
	 */
	public String shareMsg(String activityTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
	        intent.setType("text/*");//文本 
        } else {
            File f = new File(Environment.getExternalStorageDirectory()+File.separator+imgPath);
            if (f != null && f.exists() && f.isFile()) {
            	intent.setType("image/*");
            	Uri u = Uri.fromFile(f);
            	intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
        
        return "shareMsg";
    }
	
	
	/**
	 * 显示消息
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public String toast(String content, String timeLength) {
		if("long".equals(timeLength.toLowerCase())) {
			Toast.makeText((Activity)context, content,
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText((Activity)context, content,
					Toast.LENGTH_SHORT).show();
		}
		return content;
	}
}