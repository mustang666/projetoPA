package com.example.icd10_version2010;

import android.content.Context;
import android.widget.ExpandableListView;

public class ExpListView extends ExpandableListView {

	public ExpListView(Context context) {
		super(context);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
