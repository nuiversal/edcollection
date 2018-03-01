package com.winway.android.edcollection.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.widget.EditText;

/**
 * viewHolder基类
 * @author bobo
 *
 */
public class BaseViewHolder {

	/**
	 * 根据viewHolder对象设置实体对象的值，前提必须保证属性名称一致
	 * 目前只支持EditText设置Entity的String类型
	 * @param viewHolder
	 */
	public <T> T getEntity(Class<T> entityClass){
		T entity = null;
		try {
			entity = entityClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Field[] eFs = entityClass.getDeclaredFields();
		Field[] vhFs = getClass().getDeclaredFields();
		Map<String,Field> viewFMap = new HashMap<String, Field>();
		for(Field f:vhFs){
			f.setAccessible(true);
			viewFMap.put(f.getName(),f);
		}
		for(Field f:eFs){
			if(viewFMap.containsKey(f.getName())){
				try {
					f.setAccessible(true);
					Object val = viewFMap.get(f.getName()).get(this);
					if(val instanceof EditText){
						f.set(entity,((EditText)val).getText().toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
		return entity;
	}
}
