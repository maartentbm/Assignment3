package tsp;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductsParser {
	public static ArrayList<Location> read(File fid, File fid2){
		try{
			ArrayList<Location> locations = new ArrayList<Location>();
			//Read coordinates
			
			Scanner sc = new Scanner(fid);
			
			
			locations.add(new Location(Integer.parseInt(sc.next().replace(",", "")), Integer.parseInt(sc.next().replace(";", ""))));
			locations.add(new Location(Integer.parseInt(sc.next().replace(",", "")), Integer.parseInt(sc.next().replace(";", ""))));
			
			sc.close();
			
			
			//Read products
			Scanner sc2 = new Scanner(fid2);
			int size = Integer.parseInt(sc2.next().replace(";", ""));
			//System.out.println(size);
			
			for(int i=0; i<size; i++){
				sc2.next();
				locations.add(new Location(Integer.parseInt(sc2.next().replace(",", "")), Integer.parseInt(sc2.next().replace(";", ""))));
			}
			
			sc2.close();
			return locations;
			
		}catch(Exception e){
			
			System.out.println("ProductsParser.read error: " +e);
			return new ArrayList<Location>();
		}
	}
}
