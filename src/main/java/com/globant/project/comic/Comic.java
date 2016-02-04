package com.globant.project.comic;

public class Comic {
	
	String name, genre;
	int copies = 1;
	
	public Comic(String name, String genre) {
		this.name = name;
		this.genre = genre;
	}

	public String getName() {
		return name;
	}
	
	public int getCopies(){
		return copies;
	}
	
	public String getGenre(){
		return genre;
	}
	
	public void addCopy(){
		copies = copies + 1;
	}
	
	public boolean equals(Comic aComic) {
		return (name.equals(aComic.getName()));
	}

	public void setGenre(String aGenre) {
		genre = aGenre;
	}
}
