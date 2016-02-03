package com.globant.project.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.globant.project.comic.Comic;
import com.globant.project.users.Admin;
import com.globant.project.users.User;

public class Catalog {
	
	private static Catalog singleton = new Catalog();
	private List<User> users = new ArrayList<User>();
	private Set<Comic> comics = new HashSet<Comic>();
	
	
	private Catalog(){
		users.add(new Admin("Sheldon","Bazinga"));
	}
	
	public static Catalog getInstance(){
		return singleton;
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User anUser) {
		singleton.users.add(anUser);
	}
	
	public static void emptyUsers(){
		getInstance().users = new ArrayList<User>();
	}

	public void registerComic(Comic aComic) {
		if(comics.contains(aComic))
			comics.stream().filter(comic -> comic.getName().equals(aComic.getName())).findFirst().get().addCopy();
		comics.add(aComic);
	}

	public Comic searchComic(String aName) {
		return comics.stream().filter(comic -> comic.getName().equals(aName)).findFirst().get();
	}
}
