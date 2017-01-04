package com.somnus.solo.support.aspect;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.executable.ExecutableValidator;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.somnus.solo.message.Message;

@Aspect
@Component
public class ValidationAspect {

    private transient Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("execution(public * *(..)) && @within(org.springframework.validation.annotation.Validated)")
    public Object validateMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        try{
        	ExecutableValidator executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();
            log.info("args:{}",ArrayUtils.toString(pjp.getArgs()));
            Set<ConstraintViolation<Object>> parametersViolations = executableValidator.
            		validateParameters(pjp.getTarget(), signature.getMethod(), pjp.getArgs());
            if (!parametersViolations.isEmpty()) {
                throw new ConstraintViolationException(parametersViolations);
            }
            result = pjp.proceed(); //Execute the method

            Set<ConstraintViolation<Object>> returnValueViolations = executableValidator.
            		validateReturnValue(pjp.getTarget(), signature.getMethod(), result);
            if (!returnValueViolations.isEmpty()) {
                throw new ConstraintViolationException(returnValueViolations);
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
            if(throwable instanceof ConstraintViolationException){
                for (ConstraintViolation<?> constraintViolation : ((ConstraintViolationException)throwable).getConstraintViolations()) {
                    /*IncomeResourceImpl#bankIncome(arg0).feeWay*/
                	String path = constraintViolation.getPropertyPath().toString();
                    int index = path.lastIndexOf('.');
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