package com.globant.project.exceptions;

@SuppressWarnings("serial")
public class UserExistsException extends Exception {
	public UserExistsException(String msg) {
		super(msg);
	}
}
