package com.globant.project.users;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exceptions.NoMoreComicsException;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	Admin admin;
	
	@Override
	protected void setUp() throws Exception {
		admin = new Admin("Sheldon","Bazinga");
	}
	
	@Override
	protected void tearDown() throws Exception {
		Catalog.emptyLoans();
		Catalog.emptyComics();
		Catalog.emptyUsers();
	}
	
	public void testAnUserBorrowsAValidComicWithTwoCopiesSuccessfully() throws NoMoreComicsException{
		User user = new User("Batman","bati");
		Catalog.getInstance().addUser(user);
		Comic spidermanComic = new Comic("Spiderman", "Superheroes");
		admin.registerComic(spidermanComic);
		admin.registerComic(new Comic("Spiderman", "Superheroes"));
		user.borrowComic(spidermanComic.getName());
		assertTrue(user.hasBorrowedComic(spidermanComic));
		assertEquals(1, spidermanComic.getCopies());
		assertEquals(1, admin.getLoans().size());
		assertEquals(1, user.getLoans().size());
	}
	
	public void testAnUserCantBorrowAValidComicWithNoCopies() throws NoMoreComicsException{
		User user = new User("Batman","bati");
		User user2 = new User("Nahue","sarasa");
		Catalog.getInstance().addUser(user);
		Catalog.getInstance().addUser(user2);
		Comic spidermanComic = new Comic("Spiderman", "Superheroes");
		admin.registerComic(spidermanComic);
		user.borrowComic(spidermanComic.getName());
		try {
			user2.borrowComic(spidermanComic.getName());
		} catch (Exception e) { }
		assertTrue(user2.getLoans().isEmpty());
		
	}
	
	public void testAnUserReturnsAComic() throws NoMoreComicsException{
		User user = new User("Nahue","sarasa");
		Catalog.getInstance().addUser(user);
		Comic spidermanComic = new Comic("Spiderman", "Superheroes");
		admin.registerComic(spidermanComic);
		user.borrowComic(spidermanComic.getName());
		user.returnComic(spidermanComic.getName());
		assertEquals(0, user.getLoans().size());
		assertEquals(0, admin.getLoans().size());
		assertEquals(1, spidermanComic.getCopies());
	}
}
