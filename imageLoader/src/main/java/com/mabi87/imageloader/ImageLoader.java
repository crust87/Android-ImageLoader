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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

public class ImageLoader {
	private static ConcurrentHashMap<Integer, String> PATH_OF_VIEW = new ConcurrentHashMap<Integer, String>(); // Bitmap path of Image view
	
	// Components
	private Context mContext;
	private ImageMemoryCache mImageMemoryCache;
	private ImageDiskCache mImageDiskCache;
	private OnImageChangeListener mOnImageChangeListener;

	// Attributes
	private boolean mUseMemoryCache;
	private boolean mUseDiskCache;
	
	// Constructor

	/**
	 * @param pContext
	 * 				the application context.
	 */
	public ImageLoader(Context pContext) {
		this(pContext, true, true);
	}

	/**
	 * @param pContext
	 * 				the application context.
	 * @param useMemoryCache
	 * 				the boolean value whether use memory cache.
	 * @param useDiskCache
	 * 				the boolean value whether use disk cache.
	 */
	public ImageLoader(Context pContext, boolean useMemoryCache, boolean useDiskCache) {
		mContext = pContext;
		mImageMemoryCache = ImageMemoryCache.getInstance();
		mImageDiskCache = new ImageDiskCache(mContext);
		mUseMemoryCache = useMemoryCache;
		mUseDiskCache = useDiskCache;
	}

	/**
	 * @param imageView
	 * 				the ImageView target of loading
	 * @param imagePath
	 * 				the String url of image source located.
	 */
	public void loadImage(ImageView imageView, String imagePath) {
		int viewHash = imageView.hashCode();
		String postPath = PATH_OF_VIEW.get(viewHash);

		// check if image already set
		if(imagePath.equals(postPath)) {
			return;
		}

		// set current image path of image view
		PATH_OF_VIEW.put(viewHash, imagePath);

		if(mOnImageChangeListener != null) {
			mOnImageChangeListener.onImageChange(imageView);
		}

		// if bitmap is cached, set image and return
		if(mUseMemoryCache) {
			Bitmap lPicture = mImageMemoryCache.get(imagePath);

			if (lPicture != null) {
				imageView.setImageBitmap(lPicture);
				imageView.setAlpha(1f);

				return;
			}
		}

		if(mOnImageChangeListener != null) {
			mOnImageChangeListener.onImageLoadTaskStart(imageView);
		} else {
			imageView.setAlpha(0f);
		}

		new LoadImageTask(imageView, imagePath).execute();
	}

	private class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {

		// Working Variables
		private WeakReference<ImageView> mImageViewReference;
		private String mImagePath;

		/**
		 * @param imageView
		 * 				the ImageView target of loading
		 * @param imagePath
		 * 				the String url of image source located.
		 */
		public LoadImageTask(ImageView imageView, String imagePath) {
			// Set variables
			mImageViewReference = new WeakReference<ImageView>(imageView);
			mImagePath = imagePath;
		}

		protected Bitmap doInBackground(Void... params) {
			Bitmap lPicture = null;

			// return null if picture path is null
			if (mImagePath == null || mImagePath.equals("null") || mImagePath.equals("")) {
				return null;
			}
			
			// if bitmap is cached, return image
			if(mUseDiskCache) {
				lPicture = mImageDiskCache.get(mImagePath);

				if(lPicture != null) {
					if(mUseMemoryCache) {
						mImageMemoryCache.put(mImagePath, lPicture);
					}

					return lPicture;
				}
			}

			try {
				// decode bitmap and put bitmap to cache
				InputStream inputStream = new java.net.URL(mImagePath).openStream();
				lPicture = BitmapFactory.decodeStream(inputStream);

				if(mUseDiskCache) {
					mImageDiskCache.put(mImagePath, lPicture);
				}

				if(mUseMemoryCache) {
					mImageMemoryCache.put(mImagePath, lPicture);
				}

				inputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			return lPicture;
		}

		protected void onPostExecute(Bitmap result) {
			ImageView lImageView = mImageViewReference.get();

			// if current path of image view is same as loaded image path, change image
			if (result != null && lImageView != null && mImagePath.equals(PATH_OF_VIEW.get(lImageView.hashCode()))) {
				if(mOnImageChangeListener != null) {
					mOnImageChangeListener.onImageLoadTaskComplete(lImageView, result);
				} else {
					lImageView.setImageBitmap(result);
					lImageView.setAlpha(1f);
				}
			}
		}
	}

	public void setOnImageChangeListener(OnImageChangeListener pOnImageChangeListener) {
		mOnImageChangeListener = pOnImageChangeListener;
	}

	public interface OnImageChangeListener {
		/**
		 * @param imageView
		 * 				the ImageView target of loading
		 */
		public abstract void onImageChange(ImageView imageView);

		/**
		 * @param imageView
		 * 				the ImageView target of loading
		 */
		public abstract void onImageLoadTaskStart(ImageView imageView);

		/**
		 * @param imageView
		 * 				the ImageView target of loading
		 * @param bitmap
		 * 				the Bitmap image as result
		 */
		public abstract void onImageLoadTaskComplete(ImageView imageView, Bitmap bitmap);
	}

}
