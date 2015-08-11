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

	public static final String getFileName(final String s) throws NoSuchAlgorithmException {
		// Create MD5 Hash
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(s.getBytes());
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
