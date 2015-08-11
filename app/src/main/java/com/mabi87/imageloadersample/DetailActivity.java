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

package com.mabi87.imageloadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabi87.imageloader.ImageLoader;

public class DetailActivity extends ActionBarActivity {

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
