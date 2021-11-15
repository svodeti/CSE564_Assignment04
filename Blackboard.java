import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Blackboard {
	private static Blackboard _instance;
	private ArrayList<City> cities = new ArrayList<City>();
	
	private int[] shortestPath;
	
	
	private Blackboard() {
	}
	
	/**
	 * This function is used to create the object class Blackboard and if it is already created, it returns the old object.
	 * That is, there will only be one object this class with different references.
	 * 
	 * @return _instance object of class Blackboard. 
	 */
	public static Blackboard createInstance() {
		if (_instance == null) {
			_instance = new Blackboard();
		}
		
		return _instance;
	}
	
	/**
	 * This function is used to add the new object of class City to the collection of objects(in ArrayList).
	 * 
	 * @param city object of class City. 
	 */
	public void addCity(City city) {
		cities.add(city);
	}
	
	/**
	 * This function is used to empty the ArrayList<City>. 
	 */
	public void clearCities() {
		cities.clear();
	}
	
	/**
	 * This function is used to get all the cities in ArrayList.
	 * 
	 *  @return cities object of class ArrayList.
	 */
	public ArrayList<City> getCities() {
		return cities;
	}
	
	/**
	 * This function reads the file.
	 * 
	 * @param path name of the file which is to be read.
	 */
	public void readTextFile(String path) {
		int index = 0;
		int dataLength = 0;
		String data[] = null;
		boolean startStoringData = false;

		File file = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String str;
			while ((str = br.readLine()) != null) {
				if (str.contains("EOF")) {
					break;
				}
				if (startStoringData && index < dataLength) {
					String delimiters = "\\s+";
					String splitedStrData[] = str.split(delimiters);
					
					if (splitedStrData.length > 3) {
						cities.add(new City(splitedStrData[3], index, Integer.parseInt(splitedStrData[1]),
								Integer.parseInt(splitedStrData[2]), 20, "#FFFFFF"));
					} else if (splitedStrData.length == 3) {
						cities.add(new City("", index, (int)Double.parseDouble(splitedStrData[1]), 
								(int)Double.parseDouble(splitedStrData[2]), 20, "#FFFFFF"));
					}
					index++;						
				}
				if(str.contains("DIMENSION")) {
					try {
						dataLength = Integer.parseInt(str.substring(str.indexOf(":") + 1).trim());
						data = new String[dataLength];
						data[0] = "";
					} catch(Exception e) {
						System.out.println("Try again! Something went wrong");
						e.printStackTrace();
					}
				} else if (str.contains("NODE_COORD_SECTION")) {
					startStoringData = true;
				}
			}
			/*for (int itr = 0; itr < cities.size(); itr++) {
				System.out.println(cities.get(itr).getIndex() + " " + cities.get(itr).getCityName() + " " + cities.get(itr).getX() + " " + cities.get(itr).getY());
			}*/
 		} catch (FileNotFoundException e) {
			System.out.println("Try again! Something went wrong");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Try again! Something went wrong");
			e.printStackTrace();
		}
	}

	/**
	 * This function writes the data about cities in the file.
	 * 
	 * @param path name of the file which in data is to be written.
	 */
	public void writeInFile(String path) {
		FileWriter fileWriter = null;
		try 
		{
			File file = new File(path);
			file.createNewFile();
			int cityCount = 0;
			
			String data[] = new String[cities.size() + 4];
			data[0] = "NAME : " + path;
			data[1] = "TYPE : TSP";
			data[2] = "DIMENSION : " + cities.size();
			data[3] = "NODE_COORD_SECTION";
			
			for (int itr = 4; itr < cities.size() + 4; itr++) {
        		data[itr] = (cityCount + 1) + " " + cities.get(cityCount).getX() + " " + cities.get(cityCount).getY() + " " + cities.get(cityCount).getCityName();
        		cityCount++;
			}
			
            fileWriter = new FileWriter(file);
 
            System.out.println("Writing content to a file.");
            for (int itr = 0; itr < data.length; itr++) {
                fileWriter.write(data[itr] + "\n");
            }
            
            fileWriter.close();
	    } catch (IOException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * This is a getter function for shortestPath, class data member.
	 * 
	 * @return shortestPath class data member.
	 */
	public int[] getShortestPath() {
		return shortestPath;
	}
	
	/**
	 * This is a setter function for shortestPath, class data member.
	 * 
	 * @param sp array of integers.
	 */
	public void setShortestPath(int[] sp) {
		shortestPath = sp;
	}
	
}
