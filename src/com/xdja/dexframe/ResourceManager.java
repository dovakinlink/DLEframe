package com.xdja.dexframe;

import java.lang.reflect.Method;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ResourceManager {
	
	public Resources loadUninstalledAPKRes(String apkFilePath){
		AssetManager assets = null;
		try{
			assets = AssetManager.class.getConstructor(null).newInstance(null);
			Method method = assets.getClass().getMethod("addAssetPath",
					new Class[]{String.class});
			Object r = method.invoke(assets, apkFilePath);
			
			DisplayMetrics metrics = null;
			Configuration config = null;
			Resources res = new Resources(assets, metrics, config);
			return res;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
