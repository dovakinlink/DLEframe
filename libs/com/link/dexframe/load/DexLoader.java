package com.link.dexframe.load;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.link.dexframe.DexApp;

import dalvik.system.DexClassLoader;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class DexLoader {
	
	protected Context mContext;
	
	public ClassLoader ORIGINAL_LOADER = null;
	public ClassLoader CUSTOM_LOADER = null;
	
	public DexLoader(Context context , ClassLoader original, ClassLoader custom){
		mContext = context;
		ORIGINAL_LOADER = original;
		CUSTOM_LOADER = custom;
	}
	
	public void attachDex(Map<String, Object> item){
		String title = item.get("title").toString();
		String path = item.get("path").toString();

		try {
			File dex = mContext.getDir("dex", Context.MODE_PRIVATE);
			dex.mkdir();
			File f = new File(dex, title);
			File apk = new File(path);
			InputStream fis = new FileInputStream(apk);  /*mContext.getAssets().open(path);*/
			FileOutputStream fos = new FileOutputStream(f);
			byte[] buffer = new byte[0xFF];
			int len;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fis.close();
			fos.close();

			File fo = mContext.getDir("outdex", Context.MODE_PRIVATE);
			fo.mkdir();
			DexClassLoader dcl = new DexClassLoader(f.getAbsolutePath(),
					fo.getAbsolutePath(), null,
					ORIGINAL_LOADER.getParent());
			CUSTOM_LOADER = dcl;

			Toast.makeText(mContext, title + "已选择",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(mContext, "Unable to load " + title, Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
			CUSTOM_LOADER = null;
		}
	}
	

}