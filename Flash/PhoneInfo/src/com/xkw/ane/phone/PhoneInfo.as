package com.xkw.ane.phone
{
	import flash.external.ExtensionContext;
	
	/**
	 * 封装获取手机信息的方法,供其他项目调用
	 * extContext.call()方法中传入的参数需要跟安卓项目中获取相应信息的方法相同
	 * 例:要获取imei号,则传入getImei
	 */
	
	public class PhoneInfo
	{
		private var extContext:ExtensionContext;
		public static const EXTENSION_ID:String = "com.xkw.ane.phoneinfo";
		public static const FAILED_RESULT:String = "ExtensionContext创建失败";
		public static const KEY:String = "getPhone";
		
		public function PhoneInfo()
		{
			//创建扩展的上下文
			extContext = ExtensionContext.createExtensionContext(EXTENSION_ID, "");
		}
		
		/**
		 * 获取手机IMEI号
		 */
		public function getImei():String {
			if(extContext){
				return extContext.call(KEY,"getImei") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机系统版本号
		 */
		public function getSystemVersion():String {
			if(extContext){
				return extContext.call(KEY,"getSystemVersion") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机当前系统的可用运行内存大小
		 */
		public function getAvailMemory():String {
			if(extContext){
				return extContext.call(KEY,"getAvailMemory") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机屏幕的宽度
		 */
		public function getWidth():String {
			if(extContext){
				return extContext.call(KEY,"getWidth") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机屏幕的高度
		 */
		public function getHeight():String {
			if(extContext){
				return extContext.call(KEY,"getHeight") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机分辨率:宽*高
		 */
		public function getDpi():String{
			return getWidth()+"*"+getHeight();
		}
		
		/**
		 * 获取手机SIM卡的IMSI号
		 */
		public function getImsi():String {
			if(extContext){
				return extContext.call(KEY,"getImsi") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机型号
		 */
		public function getPhoneMobel():String {
			if(extContext){
				return extContext.call(KEY,"getPhoneMobel") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机品牌,如xiaomi
		 */
		public function getPhoneBrand():String {
			if(extContext){
				return extContext.call(KEY,"getPhoneBrand") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机号码(个别手机无法获取,如小米4)
		 */
		public function getPhoneNumber():String {
			if(extContext){
				return extContext.call(KEY,"getPhoneNumber") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机MAC地址(需要在开启手机WIFI时才能调用到)
		 */
		public function getMacAddress():String {
			if(extContext){
				return extContext.call(KEY,"getMacAddress") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机CPU型号
		 */
		public function getCpuMobel():String {
			if(extContext){
				return extContext.call(KEY,"getCpuMobel") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机CPU频率
		 */
		public function getCpuFrequency():String {
			if(extContext){
				return extContext.call(KEY,"getCpuFrequency") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机运营商
		 * 返回结果:
		 * 	中国联通
		 * 	中国电信
		 * 	中国移动
		 */
		public function getPhoneOperators():String {
			if(extContext){
				return extContext.call(KEY,"getPhoneOperators") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 返回用户手机是否处于漫游状态,
		 * 两种结果
		 * 	true:  漫游 
		 * 	false:  非漫游
		 */
		public function getNetWorkRoaming():String {
			if(extContext){
				return extContext.call(KEY,"getNetWorkRoaming") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 返回手机当前是否有网络连接
		 */
		public function isConnect():String {
			if(extContext){
				return extContext.call(KEY,"isConnect") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 返回手机的当前网络连接状态,
		 * 返回结果取值如下:
		 * NULL :无网络连接
		 * WIFI: 连接wifi
		 * 2G
		 * 3G
		 * 4G
		 */
		public function getNetWorkStatus():String {
			if(extContext){
				return extContext.call(KEY,"getNetWorkStatus") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机sd路径
		 */
		public function getSDPath():String {
			if(extContext){
				return extContext.call(KEY,"getSDPath") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 获取手机data路径
		 */
		public function getDataDirectory():String {
			if(extContext){
				return extContext.call(KEY,"getDataDirectory") as String;
			}
			return FAILED_RESULT;
		}
		
		public function getRootDirectory():String {
			if(extContext){
				return extContext.call(KEY,"getRootDirectory") as String;
			}
			return FAILED_RESULT;
		}
		
		
		/**
		 * GPS是否已打开
		 * 打开: true
		 * 关闭: false
		 */
		public function isGpsEnabled():String {
			if(extContext){
				return extContext.call(KEY,"isGpsEnabled") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 跳转至指定的手机系统页面,如跳转至手机wifi设置界面
		 * 取值只能使用Settings类中的静态常量
		 */
		public function skipToSetting(setString:String):void {
			if(extContext){
				extContext.call(KEY,"skipToSetting", setString);
			}
		}
		
		/**
		 * wifi是否开启(即手机wifi开关是否打开)
		 * 返回true(开户), false(关闭)
		 */
		public function isWifiOpened():String {
			if(extContext){
				return extContext.call(KEY,"isWifiOpened") as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 调用输入法键盘
		 */
		public function callKeyboard():void {
			if(extContext){
				extContext.call(KEY,"callKeyboard");
			}
		}
		
		/**
		 *消息通知 
		 * title: 通知的
		 */		
		public function messageNotify(title:String, content:String, url:String):String {
			if(extContext){
				return extContext.call(KEY,"messageNotify",title, content, url) as String;
			}
			return FAILED_RESULT;
		}
		
		
		public function installApk(apkPath:String):String {
			if(extContext){
				return extContext.call(KEY,"installApk", apkPath) as String;
			}
			return FAILED_RESULT;
		}
		
		
		public function disDormant():String {
			if(extContext){
				return extContext.call(KEY,"disDormant") as String;
			}
			return FAILED_RESULT;
		}
		
		
		/**
		 * 调用系统浏览器
		 */
		public function callSystemBrowser(url:String):String{
			if(extContext){
				return extContext.call(KEY,"callSystemBrowser",url) as String;
			}
			return FAILED_RESULT;
		}
		
		
		/**
		 * 测试, 获取应用元数据
		 */
		public function getMeteData(key:String):String{
			if(extContext){
				return extContext.call(KEY,"getMeteData",key) as String;
			}
			return FAILED_RESULT;
		}
		
		/**
		 * 测试, 获取应用元数据
		 * uri:下载地址
		 * title: 通知栏标题
		 * desc: 通知栏介绍
		 */
		public function downloadApk(uri:String, title:String, desc:String, downloadDir:String):String{
			if(extContext){
				return extContext.call(KEY, "downloadApk",uri, title, desc, downloadDir) as String;
			}
			return FAILED_RESULT;
		}
		
		
		/**
		 * android自带的分享功能
		 */
		public function shareMsg(activityTitle:String, msgText:String, imgPath:String):String{
			if(extContext){
				return extContext.call(KEY,"shareMsg", activityTitle, msgText, imgPath) as String;
			}
			return FAILED_RESULT;
		}
		
		
		/**
		 * 显示消息
		 * toast(String content, String timeLength)
		 */
		public function toast(content:String, timeLength:String):String{
			if(extContext){
				return extContext.call(KEY,"toast", content, timeLength) as String;
			}
			return FAILED_RESULT;
		}
		
	}
}