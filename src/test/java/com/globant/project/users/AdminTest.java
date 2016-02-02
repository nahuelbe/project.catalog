package com.globant.project.users;

import com.globant.project.catalog.Catalog;

import junit.framework.TestCase;

public class AdminTest extends TestCase {
	
	Admin admin;
	
	@Override
	protected void setUp() throws Exception {
		this.admin = new Admin("Sheldon","Bazinga");
	}
	
	public void testAdminCanRegisterAnUserInAnEmptyCatalog(){
		admin.register("Nahue","sarasa");
		assertEquals(1, Catalog.getInstance().getUsers().size());
		assertEquals("Nahue", Catalog.getInstance().getUsers().get(0).getId());
		assertEquals("sarasa", Catalog.getInstance().getUsers().get(0).getPassword());
	}
}
