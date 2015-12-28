package com.xdja.dexframe;

import java.security.Permissions;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.link.dexframe.engine.DexApp;
import org.link.dexframe.engine.DexLoader;
import org.link.dexframe.plugin.initiator.Initiator;
import org.link.dexframe.plugin.structure.Plugin;

import android.Manifest.permission;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DexFrameActivity extends ListActivity {
	
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private DexLoader dexLoader;
	public static Resources currentRes = null;
	public DexApp da;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		da = (DexApp)getApplication();
		
		PackageManager pm = getPackageManager();
		Context cc = getApplicationContext();
		dexLoader = DexLoader.getInstance(this);
		
		addItem("加载dex插件", null, "com.xdja.dexplugindemo.MAIN_ACTIVITY",null);
		
		/*ResourceManager rm = new ResourceManager();
		Resources res = rm.loadUninstalledAPKRes("/data/data/com.xdja.dexframe/dexPlugindemo.apk");*/
		
		addItem("dexPlugindemo.apk", "/data/data/com.xdja.dexframe/dexPlugindemo.apk",null,null);
		addItem("dexdemo.apk","/data/data/com.xdja.dexframe/dexdemo.apk",null,null);
		//attachAPK();
		
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 });
		setListAdapter(adapter);
		
	}
	
	/*private void attachAPK(){
		try {
			AssetManager asset = getAssets();
			for (String s : asset.list("apks")) {
				InputStream abpath = getClass().getResourceAsStream("/assets/apks/" + s);
				String path = new String(InputStreamToByte(abpath));
				ResourceManager rm = new ResourceManager();
				Resources res = rm.loadUninstalledAPKRes(path);
				addItem(s, "apks/" + s, "",res);
			}
		} catch (Exception e) {
		}
	}
*/
	
	private void addItem(String title, String path, String action, Resources res) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("path", path);
		map.put("action", action);
		map.put("resources", res);
		data.add(map);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		if(position == 0){
			String action = data.get(position).get("action").toString();
			action = "plugin.activity";
			Intent i = new Intent();
			i.setAction(action);
			startActivity(i);
			return;
		} else if(position == 1){
			/*Map<String, Object> item = data.get(position);
			ResourceManager rm = new ResourceManager();
			Resources res = rm.loadUninstalledAPKRes("/data/data/com.xdja.dexframe/dexPlugindemo.apk");
			currentRes = res;
			da.switchResources(res);
			//addItem("dexPlugindemo.apk","/data/data/com.xdja.dexframe/dexPlugindemo.apk",null,res);
			dexLoader.attachDex(item.get("title").toString(), item.get("path").toString(),da,null);*/
			Plugin plugin = new Plugin();
			plugin.setName("dexPlugindemo.apk");
			plugin.setDirectory("/data/data/com.xdja.dexframe/dexPlugindemo.apk");
			Initiator.getInstance(DexFrameActivity.this).startPlugin(plugin, da);
		} else if(position == 2){
			/*Map<String, Object> item = data.get(position);
			ResourceManager rm = new ResourceManager();
			Resources res = rm.loadUninstalledAPKRes("/data/data/com.xdja.dexframe/dexdemo.apk");
			currentRes = res;
			da.switchResources(res);
			//addItem("dexPlugindemo.apk","/data/data/com.xdja.dexframe/dexPlugindemo.apk",null,res);
			dexLoader.attachDex(item.get("title").toString(), item.get("path").toString(),da,null);*/
			
			Plugin plugin = new Plugin();
			plugin.setName("dexdemo.apk");
			plugin.setDirectory("/data/data/com.xdja.dexframe/dexdemo.apk");
			Initiator.getInstance(DexFrameActivity.this).startPlugin(plugin, da);
			
		}
		
		
		
	}
	
/*	public byte[] InputStreamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while((ch = is.read()) != -1){
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}*/
}
