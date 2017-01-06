package com.somnus.solo.validation;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.somnus.solo.validation.model.User;

public class UserValidator {
	
	public void verify(@Valid User u) {
		
	}
	
	@Size(min = 1)
	public List<User> getUsers() {
		return Collections.emptyList();
	}

}
