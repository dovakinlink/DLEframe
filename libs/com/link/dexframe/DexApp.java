package com.link.dexframe;

import android.app.Application;
import android.content.Context;

public class DexApp extends Application {
	public static ClassLoader ORIGINAL_LOADER;
	public static ClassLoader CUSTOM_LOADER = null;
	
	public static DexApp instance = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		try {
			Context mBase = new ClassFieldGetter<Context>(this, ClassField.mBase).get();

			Object mPackageInfo = new ClassFieldGetter<Object>(mBase, ClassField.mPackageInfo)
					.get();

			ClassFieldGetter<ClassLoader> sClassLoader = new ClassFieldGetter<ClassLoader>(
					mPackageInfo, ClassField.mPackageInfo);
			ClassLoader mClassLoader = sClassLoader.get();
			ORIGINAL_LOADER = mClassLoader;

			MyClassLoader cl = new MyClassLoader(mClassLoader);
			sClassLoader.set(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MyClassLoader extends ClassLoader {
		public MyClassLoader(ClassLoader parent) {
			super(parent);
		}

		@Override
		public Class<?> loadClass(String className)
				throws ClassNotFoundException {
			if (CUSTOM_LOADER != null) {
				try {
					Class<?> c = CUSTOM_LOADER.loadClass(className);
					if (c != null)
						return c;
				} catch (ClassNotFoundException e) {
				}
			}
			return super.loadClass(className);
		}
	}
	
	public static DexApp getInstance(){
		return instance;
	}
}