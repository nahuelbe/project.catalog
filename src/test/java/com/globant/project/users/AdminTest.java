package com.globant.project.users;

import java.util.List;

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
		Catalog.emptyComics();
	}
	
	public void testAdminCanRegisterAnUserInAnEmptyCatalog(){
		admin.registerUser("Batman","bati");
		assertFalse(admin.getUsers().isEmpty());
		assertEquals(3, admin.getUsers().size());
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
		Comic spidermanComic = new Comic("Spiderman","Superheroes");
		admin.registerComic(spidermanComic);
		assertEquals(spidermanComic, admin.searchComic("Spiderman"));
		assertEquals(1, admin.searchComic("Spiderman").getCopies());
	}
	
	public void testWhenAnAdminAddsAnExistentComicItIncreasesItsCopiesByOne(){
		Comic spidermanComic = new Comic("Spiderman","Superheroes");
		Comic otherSpidermanComic = new Comic("Spiderman","Superheroes");
		admin.registerComic(spidermanComic);
		admin.registerComic(otherSpidermanComic);
		assertEquals(2, admin.searchComic("Spiderman").getCopies());
	}
	
	public void testGetCorrectGenresOfRegisteredComics(){
		admin.registerComic(new Comic("Spiderman","Superheroes"));
		admin.registerComic(new Comic("The walking dead", "Science fiction"));
		admin.registerComic(new Comic("Daredevil","Superheroes"));
		assertEquals(2, admin.getGenres().size());
		assertTrue(admin.getGenres().contains("Science fiction"));
		assertTrue(admin.getGenres().contains("Superheroes"));
	}
	
	public void testWhenEditAnExistentGenreItChangesAllComicsWithThatGenre(){
		admin.registerComic(new Comic("Spiderman","Superheres"));
		admin.registerComic(new Comic("Batman","Superheres"));
		admin.editGenre("Superheres","Superheroes");
		assertTrue(admin.getGenres().contains("Superheroes"));
		assertFalse(admin.getGenres().contains("Superheres"));
		assertEquals(1,admin.getGenres().size());
	}
	
	public void testViewOnlyComicsOfOneGenre(){
		Comic spiderman = new Comic("spiderman","Superheroes"), batman = new Comic("batman","Superheroes"),
				twd = new Comic("The walking dead", "Science fiction"), supercampeones = new Comic("Captain Tsubasa", "Sports"); 
		admin.registerComic(spiderman);
		admin.registerComic(batman);
		admin.registerComic(twd);
		admin.registerComic(supercampeones);
		List<Comic> filteredComicsByGender = admin.getComicsByGenre("Superheroes");
		assertTrue(filteredComicsByGender.contains(spiderman));
		assertTrue(filteredComicsByGender.contains(spiderman));
		assertFalse(filteredComicsByGender.contains(twd));
		assertFalse(filteredComicsByGender.contains(supercampeones));
		assertEquals(2, filteredComicsByGender.size());
	}
	
	public void testEditAnUserSuccessfully(){
		admin.editUsername("Nahue","Duality");
		admin.editPassword("Duality","newPassword");
		assertTrue(admin.userExist("Duality"));
		assertFalse(admin.userExist("Nahue"));
	}
	
	public void testRemoveOneCopyOfThreeSuccessfully(){
		// TODO
	}
	
	public void testRemoveUniqueCopySuccessfully(){
		// TODO
	}
	
	public void testRemovesGenreSuccessfully(){
		// TODO		
	}
	
	public void testEditsGenreAndAllOfItComicsSuccessfully(){
		// TODO
	}
	
}
