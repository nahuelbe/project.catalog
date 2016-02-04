package com.globant.project.interfaces;

import java.util.Scanner;

public interface Scanneable {
	default int scanIntOption(){
		Scanner scan = new Scanner(System.in);
    	return scan.nextInt();
	}
	
	default String scanStringWithMessage(String message){
		Scanner scan = new Scanner(System.in);
    	System.out.println(message);
    	return scan.nextLine();
	}

}
