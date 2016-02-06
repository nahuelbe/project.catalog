package com.globant.project.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exceptions.NoMoreComicsException;
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

	public void borrowComic(String comicName) throws NoMoreComicsException {
		Comic aComic = Catalog.getInstance().getComics().stream().filter(comic -> comic.getName().equals(comicName)).findFirst().get();
		if(aComic.getCopies() > 0){
			Loan loan = new Loan(this,aComic);
			loans.add(loan);
			Catalog.getInstance().addLoan(loan);
			Catalog.getInstance().removeCopy(aComic.getName());
		}
		else{
			throw new NoMoreComicsException("No more comics are available.");
		}
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public boolean hasBorrowedComic(Comic spidermanComic) {
		return loans.stream().anyMatch(loan -> loan.getComic().getName().equals(spidermanComic.getName()));
	}

	public void returnComic(String comicName) throws Exception {
		if(loans.stream().anyMatch(loan -> loan.getComic().getName().equals(comicName))){
			loans = loans.stream().filter(loan -> !loan.getComic().getName().equals(comicName)).collect(Collectors.toList());
			Catalog.getInstance().returnComic(comicName);
		}
		else
			throw new Exception("Return failed. You don't have that comic.");
	}
	
	public void removeLoan(Loan aLoan){
		loans.remove(aLoan);
	}

}
