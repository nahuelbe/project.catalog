package com.globant.project.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
		return users.stream().collect(Collectors.toList());
	}

	public void addUser(User anUser) {
		singleton.users.add(anUser);
	}
	
	public static void emptyUsers(){
		getInstance().users = new ArrayList<User>();
		getInstance().users.add(new Admin("Sheldon","Bazinga"));
	}
	
	public static void emptyComics(){
		getInstance().comics = new HashSet<Comic>();
	}
	
	public void registerComic(Comic aComic) {
		if(comics.stream().anyMatch(comic -> comic.getName().equals(aComic.getName())))
			comics.stream().filter(comic -> comic.getName().equals(aComic.getName())).findFirst().get().addCopy();
		else
			comics.add(aComic);
	}

	public Comic searchComic(String aName) {
		return comics.stream().filter(comic -> comic.getName().equals(aName)).findFirst().get();
	}

	public Set<Comic> getComics() {
		return comics;
	}

	public boolean checkUserProperties(String id, String password) {
		return users.stream().anyMatch(user -> user.getId().equals(id) && user.getPassword().equals(password));
	}

	public boolean userExists(String id, String password) {
		return users.stream().anyMatch(user -> user.getId().equals(id));
	}
	
	public Set<String> getGenres() {
		return comics.stream().map(comic -> comic.getGenre()).collect(Collectors.toSet());
	}


}
