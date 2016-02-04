package com.globant.project.interfaces;

public interface NextLiner {
	default String nextLine(){
		return System.getProperty("line.separator");
	}
}
