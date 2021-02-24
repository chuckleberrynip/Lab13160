//Mohammed Azad
//This is a basic unit converter. Can be expanded to use other units.
import java.util.*;
import java.lang.*;
import java.io.*;
public class Forecast { 
	public static void main(String[] args) throws Exception { 
		File file = new File("forecast.txt");
		Scanner scan = new Scanner(file);
		System.out.println("This is a conversion of units based on the provided text file");
		while(scan.hasNext()){ 
			String holder = scan.next();
			if(holder.equals("C") || holder.equals("F")) { 
				double temp = scan.nextDouble();
				if(holder.equals("F")) { 
					double cel = ((temp-32))/1.8;
					System.out.println("Conversion from Farenheit to Celsius: " + cel + "degrees Celsius");
				}
				else { 
					double fah = ((temp*1.8))+32;
					System.out.println("Conversion from Celsius to Farenheit: " + fah + "degrees Farenheit");
				}
			}
			else { 
				System.out.println("Invalid unit of temperature");
				double trash = scan.nextDouble();
			}
		}

	}
}
