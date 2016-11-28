package com.somnus.solo.support.aspect;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.hibernate.validator.method.MethodValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.somnus.solo.message.Message;

@SuppressWarnings("deprecation")
@Aspect
@Component
public class ValidationAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /** Match any public methods in a class annotated with @AutoValidating*/
    @Autowired
    private javax.validation.Validator validator;

    @Around("execution(public * *(..)) && @within(org.springframework.validation.annotation.Validated)")
    public Object validateMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        try{
            MethodValidator methodValidator = validator.unwrap(MethodValidator.class);
            Set<MethodConstraintViolation<Object>> parametersViolations = methodValidator.
            		validateAllParameters(pjp.getTarget(), signature.getMethod(), pjp.getArgs());
            if (!parametersViolations.isEmpty()) {
                throw new MethodConstraintViolationException(parametersViolations);
            }
            result = pjp.proceed(); //Execute the method

            Set<MethodConstraintViolation<Object>> returnValueViolations = methodValidator.
            		validateReturnValue(pjp.getTarget(), signature.getMethod(), result);
            if (!returnValueViolations.isEmpty()) {
                throw new MethodConstraintViolationException(returnValueViolations);
            }
        }catch (Throwable throwable){
            log.error("接口数据验证不通过：",throwable);
            Message message=(Message)signature.getReturnType().newInstance();
            message.setRepCode("999999");
            message.setRepMsg("处理失败了");
            result = exceptionHandle(throwable,message);
        }

        return result;
    }

    private Message exceptionHandle(Throwable throwable,Message message){
        if(throwable instanceof ValidationException){
            if(throwable instanceof MethodConstraintViolationException){
                for (ConstraintViolation<?> constraintViolation : ((MethodConstraintViolationException)throwable).getConstraintViolations()) {
                    /*IncomeResourceImpl#bankIncome(arg0).feeWay*/
                	String path = constraintViolation.getPropertyPath().toString();
                    int index = path.indexOf('.');
                    if(index>0){
                        index = index+1;
                    }else{
                        index = 0;
                    }
                    message.setRepCode("112211");
                    message.setRepMsg(path.substring(index).concat(" ").concat(constraintViolation.getMessage()));
                    log.error("校验失败：{}",path.substring(index).concat(" ").concat(constraintViolation.getMessage()));
                    break;
                }
            }
        }
        return message;
    }

}