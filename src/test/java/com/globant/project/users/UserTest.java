package com.globant.project.users;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	Admin admin;
	
	@Override
	protected void setUp() throws Exception {
		admin = new Admin("Sheldon","Bazinga");
	}
	
	public void testAnUserBorrowsAValidComicWithTwoCopiesSuccessfully(){
		User user = new User("Batman","bati");
		Catalog.getInstance().addUser(user);
		Comic spidermanComic = new Comic("Spiderman", "Superheroes");
		admin.registerComic(spidermanComic);
		admin.registerComic(new Comic("Spiderman", "Superheroes"));
		user.borrowComic(spidermanComic);
		//assertTrue(user.hasBorrowedComic(spidermanComic));
		assertEquals(1, spidermanComic.getCopies());
//		assertEquals(1, admin.getLoans());
		assertEquals(1, user.getLoans().size());
	}
	
	
	
}
