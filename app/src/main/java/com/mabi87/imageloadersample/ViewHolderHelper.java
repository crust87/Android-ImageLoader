package com.mabi87.imageloadersample;

import android.util.SparseArray;
import android.view.View;

// View Holder Helper Class
public class ViewHolderHelper {

	public static <T extends View> T get(View convertView, int id) {

		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();

		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}

		View childView = viewHolder.get(id);

		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}

		return (T) childView;
	}

}