package tsp;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DistanceParser {

	public static void read(String filename, ArrayList<Route> routes){
		try{
			
		}catch(Exception e){
			
			System.out.println("DistanceParser.read error: " +e);
		}
	}
	
	public static String stringInvertor(String s){
		String temp = "";
		for(int i=s.length()-2; i>=0; i--){
			if(s.charAt(i) != ';'){
				if(s.charAt(i) == '0'){
					temp += "2";
				}else if(s.charAt(i) == '1'){
					temp += "3";
				}else if(s.charAt(i) == '2'){
					temp += "0";
				}else{
					temp += "1";
				}
			}else{
				temp += ";";
			}
		}
		
		temp += ";";
		
		return temp;
	}

}

