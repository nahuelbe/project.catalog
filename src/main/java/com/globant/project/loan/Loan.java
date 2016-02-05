package com.globant.project.loan;

import com.globant.project.comic.Comic;
import com.globant.project.users.User;

public class Loan {
	User user;
	Comic comic;
	
	public Loan(User user, Comic comic) {
		this.user = user;
		this.comic = comic;
	}
	
	public Comic getComic() {
		return comic;
	}
	
	public User getUser() {
		return user;
	}
	
}
