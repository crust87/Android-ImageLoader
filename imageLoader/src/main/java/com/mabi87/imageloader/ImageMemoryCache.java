package com.mabi87.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageMemoryCache implements CacheInterface<String, Bitmap> {
	
	// Image Cache
	private LruCache<String, Bitmap> mCache;

	// Attributes
	private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
	private final int cacheSize = maxMemory / 8;
	
	private ImageMemoryCache() {
		
		mCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount() / 1024;
			}
		};
	}
	
	private static class ImageMemoryCacheHolder {
		private static final ImageMemoryCache INSTANCE = new ImageMemoryCache();
	}
	
	public static ImageMemoryCache getInstance() {
		return ImageMemoryCacheHolder.INSTANCE;
	}

	@Override
	public synchronized void put(String key, Bitmap value) {
		mCache.put(key, value);
	}
	
	@Override
	public synchronized Bitmap get(String key) {
		return mCache.get(key);
	}
	
	@Override
	public synchronized void evictAll() {
		mCache.evictAll();
	}

}
