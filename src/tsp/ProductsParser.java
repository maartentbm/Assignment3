package tsp;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductsParser {
	public static ArrayList<Location> read(File fid){
		try{
			Scanner sc = new Scanner(fid);
			ArrayList<Location> locations = new ArrayList<Location>();
			int size = Integer.parseInt(sc.next().replace(";", ""));
			//System.out.println(size);
			
			for(int i=0; i<size; i++){
				sc.next();
				locations.add(new Location(Integer.parseInt(sc.next().replace(",", "")), Integer.parseInt(sc.next().replace(";", ""))));
			}
			
			sc.close();
			return locations;
			
		}catch(Exception e){
			
			System.out.println("ProductsParser.read error: " +e);
			return new ArrayList<Location>();
		}
	}
}
