package com.globant.project.users;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;

import junit.framework.TestCase;

public class AdminTest extends TestCase {
	
	Admin admin;
	
	@Override
	protected void setUp() throws Exception {
		admin = new Admin("Sheldon","Bazinga");
		admin.registerUser("Nahue","sarasa");
	}
	
	@Override
	protected void tearDown() throws Exception {
		Catalog.emptyUsers();
	}
	
	public void testAdminCanRegisterAnUserInAnEmptyCatalog(){
		Catalog.emptyUsers();
		admin.registerUser("Nahue","sarasa");
		admin.registerUser("Batman","bati");
		assertFalse(admin.getUsers().isEmpty());
		assertEquals(2, admin.getUsers().size());
		assertEquals("Nahue", admin.getUser("Nahue").getId());
		assertEquals("sarasa", admin.getUser("Nahue").getPassword());
		assertEquals("Batman", admin.getUser("Batman").getId());
		assertEquals("bati", admin.getUser("Batman").getPassword());
	}
	
	public void testAdminChecksIfUserExists(){
		assertTrue(admin.userExist("Nahue"));
	}
	
	public void testAdminGetsAnExistentUser(){
		assertEquals("Nahue", admin.getUser("Nahue").getId());
		assertEquals("sarasa", admin.getUser("Nahue").getPassword());
	}
	
	public void testAdminRevokesAnUserSuccessfully(){
		admin.revokeUser("Nahue");
		assertFalse(admin.userExist("Nahue"));
	}
	
	public void testAnAdminCanAddAComicToAnEmptyCatalog(){
		Comic spidermanComic = new Comic("Spiderman"); 
		admin.registerComic(spidermanComic);
		assertEquals(spidermanComic, admin.searchComic("Spiderman"));
	}
	
	
}
