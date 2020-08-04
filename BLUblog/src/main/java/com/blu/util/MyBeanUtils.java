package com.blu.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class MyBeanUtils {

	public static String[] getNullPropertyNames(Object source) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
		List<String> nullPorpertyNames = new ArrayList<>();
		for (PropertyDescriptor pd : pds) {
			String propertyName = pd.getName();
			if (beanWrapper.getPropertyValue(propertyName) == null) {
				nullPorpertyNames.add(propertyName);
			}
		}
		return nullPorpertyNames.toArray(new String[nullPorpertyNames.size()]);
	}
}
