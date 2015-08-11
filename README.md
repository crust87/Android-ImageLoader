# Android-ImageLoader
Async Image Loader for Android

## Example

create ImageLoader
```java
mImageLoader = new ImageLoader(getApplicationContext());
```

or
```java
mImageLoader = new ImageLoader(getApplicationContext(), useMemCache, useDiskCache);
```

set Event Listener<br/>
onImageChangeTaskStart is called before AsyncTask execute for get image from disk or internet<br/>
onImageChangeTaskComplete is called AsyncTask onPostExecute<br/>
```java
mImageLoader.setOnImageChangeListener(new ImageLoader.OnImageChangeListener() {
	private ConcurrentHashMap<Integer, AnimatorSet> animatorOfView = new ConcurrentHashMap<Integer, AnimatorSet>();

	@Override
	public void onImageChange(ImageView imageView) {
		AnimatorSet set = animatorOfView.get(imageView.hashCode());
		if(set != null) {
			set.cancel();
		}
	}

	@Override
	public void onImageLoadTaskStart(ImageView imageView) {
		// change alpha for fade in
		imageView.setAlpha(0f);
	}

	@Override
	public void onImageLoadTaskComplete(ImageView imageView, Bitmap bitmap) {
		// set image with fade in
		imageView.setImageBitmap(bitmap);
		ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imageView, "alpha", 0.0f, 1f);
		fadeIn.setDuration(1000);
		AnimatorSet fadeSet = new AnimatorSet();
		fadeSet.play(fadeIn);
		fadeSet.start();
		animatorOfView.put(imageView.hashCode(), fadeSet);
	}
});
```

load image with ImageLoader
```java
ImageView imageView = findViewById(id);
String imagePath = "http://some.where.image/placed.png"

mImageLoader.loadImage(imageView, imagePath);
```

## Licence
Copyright 2015 Mabi

Licensed under the Apache License, Version 2.0 (the "License");<br/>
you may not use this work except in compliance with the License.<br/>
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software<br/>
distributed under the License is distributed on an "AS IS" BASIS,<br/>
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br/>
See the License for the specific language governing permissions and<br/>
limitations under the License.
