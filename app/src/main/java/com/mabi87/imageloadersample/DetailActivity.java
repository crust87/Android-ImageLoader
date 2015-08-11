package com.mabi87.imageloadersample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabi87.imageloader.*;

public class DetailActivity extends Activity {

	// Components
	private ImageView mGameImage;
	private TextView mGameText;
	private TextView mGameContent;
	
	private ImageLoader mImageLoader;
	
	// Attributes
	private String mImagePath;
	private String mTitle;
	private String mContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		mImageLoader = new ImageLoader(getApplicationContext());
		
		// get Attributes
		Intent intent = getIntent();
		mImagePath = intent.getStringExtra("path");
		mTitle = intent.getStringExtra("title");
		mContent = intent.getStringExtra("content");
		
		// Load Layout Components
		mGameImage = (ImageView) findViewById(R.id.gameImage);
		mGameText = (TextView) findViewById(R.id.gameText);
		mGameContent = (TextView) findViewById(R.id.gameContent);
		
		// set Content
		mImageLoader.loadImage(mGameImage, mImagePath);
		mGameText.setText(mTitle);
		mGameContent.setText(mContent);
	}
	
}
