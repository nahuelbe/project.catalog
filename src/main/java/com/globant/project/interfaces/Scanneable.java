package com.globant.project.interfaces;

import java.util.Scanner;

public interface Scanneable {
	@SuppressWarnings("resource")
	default int scanIntOption(){
		Scanner scan = new Scanner(System.in);
    	return scan.nextInt();
	}
	@SuppressWarnings("resource")
	default String scanStringWithMessage(String message){
		Scanner scan = new Scanner(System.in);
    	System.out.println(message);
    	return scan.nextLine();
	}

}
