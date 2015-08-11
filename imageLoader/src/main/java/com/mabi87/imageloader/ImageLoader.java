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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoader {
	private static ConcurrentHashMap<Integer, String> PATH_OF_VIEW = new ConcurrentHashMap<Integer, String>(); // Bitmap path of Image view
	
	// Components
	private Context mContext;
	private ImageMemoryCache mImageMemoryCache;
	private ImageDiskCache mImageDiskCache;

	// Attributes
	private boolean mUseMemoryCache;
	private boolean mUseDiskCache;
	
	// Constructor
	public ImageLoader(Context pContext) {
		this(pContext, true, true);
	}

	public ImageLoader(Context pContext, boolean useMemoryCache, boolean useDiskCache) {
		mContext = pContext;
		mImageMemoryCache = ImageMemoryCache.getInstance();
		mImageDiskCache = new ImageDiskCache(mContext);
		mUseMemoryCache = useMemoryCache;
		mUseDiskCache = useDiskCache;
	}

	public void loadImage(ImageView pImageView, String pImagePath) {
		int viewHash = pImageView.hashCode();
		String postPath = PATH_OF_VIEW.get(viewHash);

		// check if image already set
		if(pImagePath.equals(postPath)) {
			return;
		}

		PATH_OF_VIEW.put(viewHash, pImagePath);

		// if bitmap is cached, set image and return
		if(mUseMemoryCache) {
			Bitmap lPicture = mImageMemoryCache.get(pImagePath);

			if (lPicture != null) {
				pImageView.setImageBitmap(lPicture);
				pImageView.setAlpha(1f);

				return;
			}
		}

		pImageView.setAlpha(0f);
		new LoadImageTask(pImageView, pImagePath).execute();
	}

	private class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {

		// Working Variables
		private WeakReference<ImageView> mImageViewReference;
		private String mImagePath;

		public LoadImageTask(ImageView pImageView, String pImagePath) {
			// Set variables
			mImageViewReference = new WeakReference<ImageView>(pImageView);
			mImagePath = pImagePath;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
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

		protected void onPostExecute(Bitmap pResultBitmap) {
			ImageView lImageView = mImageViewReference.get();
			if (pResultBitmap != null && lImageView != null && !isCancelled()) {
				setImageWithFadeIn(lImageView, pResultBitmap); // set image with fade in
			}
		}

	}

	// Method for set image wit fade in
	private static void setImageWithFadeIn(ImageView pImageView, Bitmap pBitmap) {
		if (pImageView != null && pBitmap != null) {
			pImageView.setImageBitmap(pBitmap);
			ObjectAnimator fadeIn = ObjectAnimator.ofFloat(pImageView, "alpha", 0.0f, 1f);
			fadeIn.setDuration(1000);
			AnimatorSet fadeSet = new AnimatorSet();
			fadeSet.play(fadeIn);
			fadeSet.start();
		}
	}

}
