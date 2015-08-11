# Android-ImageLoader
Async Image Loader for Android

## Example
```java
private ImageLoader mImageLoader;

protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	mImageLoader = new ImageLoader(getApplicationContext());
	
	// ...
}
```

or

```java
private ImageLoader mImageLoader;

protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	mImageLoader = new ImageLoader(getApplicationContext(), isMemoryCacheEnable, isDiskCacheEnable);

	// ...
}
```

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
