package com.bq.phoneinformation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class PakageInfoProvider {
	private Context context;
	private List<AppInfo> appInfos;
	private AppInfo appInfo;

	public PakageInfoProvider(Context context) {
		super();
		this.context = context;
	}

	public List<AppInfo> getAppInfo() {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> pakageinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		appInfos = new ArrayList<AppInfo>();
		for (PackageInfo packageInfo : pakageinfos) {
			appInfo = new AppInfo();
			
			//获取字符串方法
			context.getString(R.string.app_name);
			context.getResources().getString(R.string.app_name);
			//获取尺寸资源方法
			context.getResources().getDimension(R.dimen.test);
			//获取xml文件并且返回的是XmlResourceParse类，其继承与XmlPullParse
			XmlResourceParser xmlrp = context.getResources().getXml(R.xml.yo);
			// 获取应用程序的名称，不是包名，而是清单文件中的labelname
			String str_name = packageInfo.applicationInfo.loadLabel(pm).toString();
			appInfo.setAppName(str_name);

			// 获取应用程序的版本号码
			String version = packageInfo.versionName;
			appInfo.setAppVersion(version);

			// 获取应用程序的快捷方式图标
			Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
			appInfo.setDrawable(drawable);

			// 获取应用程序是否是第三方应用程序
			appInfo.setIsUserApp(filterApp(packageInfo.applicationInfo));
			
			//给一同程序设置包名
			appInfo.setPackageName(packageInfo.packageName);

			appInfos.add(appInfo);
			appInfo = null;
		}

		return appInfos;
	}

	/**
	 * 三方应用程序的过滤器
	 * 
	 * @param info
	 * @return true 三方应用 false 系统应用
	 */
	public boolean filterApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			// 代表的是系统的应用,但是被用户升级了. 用户应用
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			// 代表的用户的应用
			return true;
		}
		return false;
	}

}