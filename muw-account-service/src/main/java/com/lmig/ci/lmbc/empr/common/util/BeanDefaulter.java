/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on May 16, 2016
 */

package com.lmig.ci.lmbc.empr.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

/**
 * Provide dynamic default values for bean.
 * Intended to be useful for write methods.
 * 
 * @author n0074214
 */
public class BeanDefaulter {
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanDefaulter.class);
	
	private Map<Class<?>,Object> defaultValues = new HashMap<>();
	private List<String> ignoredFields = new ArrayList<>();
	private Map<String,Object> customValues = new HashMap<>();
	
	public BeanDefaulter() throws ParseException {
		// Time stuff
		Date defaultDate = new SimpleDateFormat("yyyy.MM.dd").parse("1900.01.01");
		putDefault(defaultDate);
		putDefault(new Timestamp(defaultDate.getTime()));
		putDefault(new java.sql.Date(defaultDate.getTime()));
		// String
		putDefault(" ");
		// Whole Numbers
		putDefault(Long.valueOf(0));
		putDefault(Integer.valueOf(0));
		putDefault(Short.valueOf((short)0));
		// Real Numbers
		putDefault(BigDecimal.ZERO);
		putDefault(Float.valueOf(0.0f));
		putDefault(Double.valueOf(0.0));
		// Boolean
		putDefault(Boolean.FALSE);
	}
	
	public void setDefaultValues(Object bean) {
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
		LOGGER.trace("Found {} properties for bean {}", pds.length, bean.getClass().getName());
		for (PropertyDescriptor pd : pds) {
			try {
				// Check current value
				Method readMethod = pd.getReadMethod();
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				Object value = readMethod.invoke(bean);
				if (value != null) {
					// A value exists, bypass...
					continue;
				}
				// If null, look for a default value
				if (ignoredFields.contains(pd.getName())) {
					LOGGER.trace("Ignoring property {} on bean {}", pd.getName(), bean.getClass().getName());
				} else if (customValues.containsKey(pd.getName())) {
					LOGGER.trace("Setting custom value for {} on bean {}", pd.getName(), bean.getClass().getName());
					setValueInternal(pd, bean, customValues.get(pd.getName()));
				} else if (defaultValues.containsKey(pd.getPropertyType())) {
					LOGGER.trace("Setting default value for {} on bean {}", pd.getName(), bean.getClass().getName());
					setValueInternal(pd, bean, defaultValues.get(pd.getPropertyType()));
				} else {
					LOGGER.trace("No default values available for {} on bean {}", pd.getName(), bean.getClass().getName());
				}
			} catch (Throwable ex) {
				throw new FatalBeanException("Unable to reference property '" + pd.getName() + "' from ", ex);
			}
		}
	}
	
	private void setValueInternal(PropertyDescriptor pd, Object target, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method writeMethod = pd.getWriteMethod();
		if (writeMethod != null) {
			if (ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], value.getClass())) {
				if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
					writeMethod.setAccessible(true);
				}
				writeMethod.invoke(target, value);
			}
		}
	}
	
	public void putDefault(Object value) {
		defaultValues.put(value.getClass(), value);
	}
	public Map<Class<?>, Object> getDefaultValues() {
		return defaultValues;
	}
	public List<String> getIgnoredFields() {
		return ignoredFields;
	}
	public Map<String, Object> getCustomValues() {
		return customValues;
	}
}
