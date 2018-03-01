package com.winway.android.ewidgets.simpletable;

import android.view.View;
import android.view.ViewGroup;

public interface TableItemCreator {
	public View createItemView(ViewGroup itemContaiuner,Object[] item);
}
