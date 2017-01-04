package com.somnus.solo.validation.service;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.somnus.solo.validation.model.User;

@Validated // 告诉MethodValidationPostProcessor此Bean需要开启方法级别验证支持
public class ValidationServiceImpl {

	@Size(min = 1)
	public List<User> getUsers(@Valid User user) {
		return Collections.emptyList();
	}
}
