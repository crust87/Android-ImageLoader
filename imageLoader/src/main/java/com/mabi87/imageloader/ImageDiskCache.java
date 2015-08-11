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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDiskCache implements CacheInterface<String, Bitmap> {

	private Context mContext;

	public ImageDiskCache(Context pContext) {
		mContext = pContext;
	}

	@Override
	public void put(String key, Bitmap value) {
		FileOutputStream outStream = null;
		
		try {
			String fileName = getFileName(key);
			outStream = new FileOutputStream(mContext.getCacheDir() + "/" + fileName);
			value.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			try {
				if(outStream != null) {
					outStream.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Bitmap get(String key) {
		
		FileInputStream inputStream = null;
		
		try {
			String fileName = getFileName(key);
			inputStream = new FileInputStream(mContext.getCacheDir() + "/" + fileName);
			
			Bitmap lPicture = BitmapFactory.decodeStream(inputStream);
			
			return lPicture;
		} catch (FileNotFoundException e) {
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			try {
				if(inputStream != null) {
					inputStream.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public void evictAll() {

	}

	/**
	 * @param source
	 * 				the String url of image source located.
	 * @return
	 * 				the String value of hashed source with ".png"
	 */
	public static final String getFileName(String source) throws NoSuchAlgorithmException {
		// Create MD5 Hash
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(source.getBytes());
		byte messageDigest[] = digest.digest();
		
		// Create Hex String
		StringBuilder hexString = new StringBuilder();
		for (byte aMessageDigest : messageDigest) {
			String h = Integer.toHexString(0xFF & aMessageDigest);

			while (h.length() < 2) {
				h = "0" + h;
			}

			hexString.append(h);
		}
		
		return hexString.toString() + ".png";
	}
}
