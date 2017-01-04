package com.somnus.solo.validation.constraints;

import java.text.ParseException;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @ClassName:     FutureValidator.java
 * @Description:   TODO
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年10月26日 下午2:14:13
 */
public class FutureValidator implements ConstraintValidator<Future, Date> {

	@Override
	public void initialize(Future constraintAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(Date date, ConstraintValidatorContext context) {
		// null values are valid
		if (date == null) {
			return true;
		}
		Date today = null;
		try {
			today = DateUtils.parseDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return DateUtils.isSameDay(date, today)||date.after(today);
	}

}
