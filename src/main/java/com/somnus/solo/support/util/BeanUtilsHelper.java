package com.somnus.solo.support.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;

public class BeanUtilsHelper {

	static {
		// 这里一定要注册默认值，使用null也可以
		BigDecimalConverter bd = new BigDecimalConverter(null);
		ConvertUtils.register(bd, java.math.BigDecimal.class);
		
		// 这里一定要注册默认值，使用null也可以
		DateConverter dc = new DateConverter(null); 
	    dc.setPattern("yyyy-MM-dd");
	    //Date顺利被转换成String
	    ConvertUtils.register(dc, String.class);
	    //null可以用来正常转换
	    ConvertUtils.register(dc, java.util.Date.class);

	}
}
