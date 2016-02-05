package com.globant.project.users;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	Admin admin;
	
	@Override
	protected void setUp() throws Exception {
		admin = new Admin("Sheldon","Bazinga");
		admin.registerUser("Nahue","sarasa");
	}
	
	
	
}
