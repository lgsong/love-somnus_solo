package com.somnus.solo;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.solo.support.holder.ApplicationContextHolder;
import com.somnus.solo.validation.model.User;
import com.somnus.solo.validation.service.ValidationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-validation.xml")
public class ValidationSpringTest {
	
	@Test
	public void validateParameters(){
		try {
			ValidationServiceImpl service = ApplicationContextHolder.getBean(ValidationServiceImpl.class);
			service.getUsers(new User("ad#min", "123456",new Date()));
		} catch (Throwable throwable) {
			System.out.println(throwable.getClass());
			if(throwable instanceof ConstraintViolationException){
				Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException)throwable).getConstraintViolations();
				for(ConstraintViolation<?> constraint:constraintViolations){
					System.out.println(constraint.getPropertyPath().toString());
					System.out.println(constraint.getMessage());
					System.out.println(constraint.getMessageTemplate());
				}
			}
			throwable.printStackTrace();
		}
	}
	
	@Test
	public void validateReturnValue(){
		try {
			ValidationServiceImpl service = ApplicationContextHolder.getBean(ValidationServiceImpl.class);
			service.getUsers(new User("admin", "123456",new Date()));//不满足后置条件的返回值  
		}  catch (Throwable throwable) {
			System.out.println(throwable.getClass());
			if(throwable instanceof ConstraintViolationException){
				Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException)throwable).getConstraintViolations();
				for(ConstraintViolation<?> constraint:constraintViolations){
					System.out.println(constraint.getPropertyPath().toString());
					System.out.println(constraint.getMessage());
					System.out.println(constraint.getMessageTemplate());
				}
			}
			throwable.printStackTrace();
		}
	}
	
}
