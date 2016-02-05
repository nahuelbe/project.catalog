package com.globant.project.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.interfaces.NextLiner;
import com.globant.project.interfaces.Scanneable;
import com.globant.project.loan.Loan;

public class User implements Scanneable, NextLiner{
	
	String id, password;
	List<Loan> loans = new ArrayList<Loan>();

	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Set<Comic> getComics() {
		return Catalog.getInstance().getComics();
	}
	
	public void setPassword(String newPassword){
		password = newPassword;
	}

	public void setId(String newId) {
		id = newId;
	}
	
	public List<Comic> getComicsByGenre(String genre) {
		return Catalog.getInstance().getComicsByGenre(genre);
	}

	public Set<String> getGenres() {
		return Catalog.getInstance().getGenres();
	}

	public void borrowComic(Comic aComic) {
		Loan loan = new Loan(this,aComic);
		loans.add(loan);
		Catalog.getInstance().addLoan(loan);
		Catalog.getInstance().removeCopy(aComic.getName());
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public boolean hasBorrowedComic(Comic spidermanComic) {
		return loans.stream().anyMatch(loan -> loan.getComic().equals(spidermanComic.getName()));
	}

}
