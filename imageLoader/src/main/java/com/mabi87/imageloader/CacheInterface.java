package com.mabi87.imageloader;

public interface CacheInterface<K, V> {
	
	public abstract void put(K key, V value);
	public abstract V get(K key);
	public abstract void evictAll();
}
