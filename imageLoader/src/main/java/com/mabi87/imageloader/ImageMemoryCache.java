/*
 * ImageLoader
 * https://github.com/mabi87/Android-ImageLoader
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2015-08-11
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
