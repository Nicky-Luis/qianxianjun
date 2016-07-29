package com.luis.nicky.qianxianjun.base.base_adapter_helper;

//支持多类型item
public interface MultiItemTypeSupport<T> {
	int getLayoutId(int position, T t);

	int getViewTypeCount();

	int getItemViewType(int postion, T t);
}