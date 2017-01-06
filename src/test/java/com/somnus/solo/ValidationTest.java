package com.somnus.solo;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.Test;

import com.somnus.solo.validation.UserValidator;
import com.somnus.solo.validation.model.User;

public class ValidationTest {
	
	@Test
	public void defaultValidator() throws ParseException{
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		User user = new User("admin","123456",DateUtils.parseDate("2014-11-11", new String[] {"yyyy-MM-dd"}));
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		for(ConstraintViolation<User> data:violations){
			System.out.println(data.getPropertyPath().toString() + ":" + data.getMessage());
		}
	}
	
	@Test
	public void hibernateValidator(){
		Validator validator = Validation.byProvider(HibernateValidator.class).configure()
				.messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("message/validate")))
				.failFast(false)
				.buildValidatorFactory().getValidator();
		User user = new User("adm#in","12345",new Date());
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		for(ConstraintViolation<User> data:violations){
			System.out.println(data.getPropertyPath().toString() + ":" + data.getMessage());
		}
	}
	
	@Test
	public void validateParameters() throws NoSuchMethodException{
		ExecutableValidator executableValidator = Validation.byProvider(HibernateValidator.class).configure()
				.messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("message/validate")))
				.failFast(false)
				.buildValidatorFactory().getValidator().forExecutables();
		UserValidator object = new UserValidator();
		Method method = object.getClass().getMethod( "verify", new Class[]{User.class} );
		Object[] parameterValues = {new User("adm#in","12345",new Date())};
		Set<ConstraintViolation<UserValidator>> violations = executableValidator.validateParameters(
				object,
				method,
				parameterValues
		);
		for(ConstraintViolation<UserValidator> data:violations){
			System.out.println(data.getPropertyPath().toString() + ":" + data.getMessage());
		}
	}
	
	@Test
	public void validateReturnValue() throws NoSuchMethodException{
		ExecutableValidator executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();
		UserValidator object = new UserValidator();
		Method method = object.getClass().getMethod( "getUsers", new Class[]{} );
		Object returnValue = object.getUsers();
		Set<ConstraintViolation<UserValidator>> violations = executableValidator.validateReturnValue(
				object,
				method,
				returnValue
		);
		for(ConstraintViolation<UserValidator> data:violations){
			System.out.println(data.getPropertyPath().toString() + ":" + data.getMessage());
		}
	}
	
}
