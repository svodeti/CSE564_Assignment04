import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

class Workspace extends JPanel implements MouseListener, MouseMotionListener, Observer {

	City clickedCity;
	
	int preX, preY;
	boolean pressOut = false;
	
	/**
	 * This constructor initializes the mouse listeners.
	 */
	public Workspace() {
//		Graphics g =
		
		getGraphics();
//        g.setColor(Color.decode("#A0BAE1")); // blue
//		System.out.println(g[]);
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setColor(Color.red);
//        g.fillRect(20, 20, 30, 30);
//        g.setColor(Color.black);
//        g.setColor(Color.decode("#F0B57A")); // orange
//        g.fillOval(60, 20, 30, 30);
//        g.setColor(Color.decode("#00B050")); // green
//        g.fillPolygon(new int[] {10, 20, 30}, new int[] {100, 20, 100}, 3);
//        g.fillRect(0, 0, 30, 30);
        
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	/**
	 * This function draws the cities in GUI and connect them with line.
	 * 
	 * @param graphics object of class Graphics used to draws the cities in GUI and connect them with line.
	 */	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		g2.setColor(Color.red);
		Blackboard bb = Blackboard.createInstance();
		ArrayList<City> cities = bb.getCities();
		int shortestPath[] = bb.getShortestPath();
		if (cities.size() >= 1) {
			for (int itr = 0; itr < shortestPath.length; itr++) {
				if (itr == 0) {
					cities.get(shortestPath[itr]).draw(g2);
				} else {
					cities.get(shortestPath[itr]).drawConnect(cities.get(shortestPath[itr - 1]), g2);
					if (itr == shortestPath.length - 1) {
						cities.get(shortestPath[itr]).drawConnect(cities.get(shortestPath[0]), g2);
						cities.get(shortestPath[0]).draw(g2);
					}
					cities.get(shortestPath[itr - 1]).draw(g2);
					cities.get(shortestPath[itr]).draw(g2);
				}
			}
		}
	}
	
	/**
	 * This function clears the GUI i.e removes the drawn cities.
	 */
	public void newMenuItem() {
		Blackboard bb = Blackboard.createInstance();
		bb.clearCities();
		repaint();
	}
	
	/**
	 * This function saves the cities with its information in the file.
	 */
	public void saveCities() {
		String fileName = JOptionPane.showInputDialog(this, "Please enter the file name with it's extension : ", null);
		if (fileName != null && fileName.trim().length() > 0 && fileName.trim().contains(".")) {
			Blackboard bb = Blackboard.createInstance();
			bb.writeInFile(fileName);
		}
	}

	/**
	 * This function loads the cities with its information from the file.
	 */
	public void loadCities() {
		Blackboard bb = Blackboard.createInstance();
		bb.clearCities();
		String fileName = JOptionPane.showInputDialog(this, "Please enter the file name with it's extension : ", null);
		if (fileName != null && fileName.trim().length() > 0 && fileName.trim().contains(".")) {
			bb.readTextFile(fileName);			
			TSPSolver tsp = TSPSolver.createInstance();
	    	Thread thread = new Thread(tsp);
	    	thread.start();
		}
		
	}
	
	/**
	 * This function specifies the operations to be performed when mouse is pressed.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mousePressed(MouseEvent e) {
		try {
			boolean isItCity = false;
			Blackboard bb = Blackboard.createInstance();
			ArrayList<City> cities = bb.getCities();
			for (int itr = 0; itr < cities.size(); itr++) {
				if(cities.get(itr).contains(e.getX(), e.getY())) {
					isItCity = true;
					clickedCity = cities.get(itr); 
					break;
				}
			}
			
			if (isItCity) {
				pressOut = false;
				if(SwingUtilities.isRightMouseButton(e)) {
					editCity();
				}

			} else {
				pressOut = true;
			}
		}  catch (Exception ex) {
			ex.printStackTrace();
		}
	
		
	}
	public void editCity() {
		String[] shapeDecorators = new String[] {"Square", "Circle Square", "Circle Square group", "Square Group"};
		JComboBox<String> shapeDecoratorCombo = new JComboBox<String>(shapeDecorators);
		String[] colors = new String[] {"cyan", "red", "blue", "yellow", "green"};
		String[] hexColors = new String[] {"#00FFFF", "#ff0000", "#0000FF", "#FFFF00", "#90EE90"};
		JComboBox<String> colorsCombo = new JComboBox<String>(colors);
		SpinnerModel model = new SpinnerNumberModel(20, 20, 80, 10);     
		JSpinner spinner = new JSpinner(model);
		Object[] fields = {
				"Select Decorator", shapeDecoratorCombo,
				"Select color", colorsCombo,
				"Select size", spinner
		};
		
		JOptionPane.showConfirmDialog(null, fields, "Edit a City ", JOptionPane.OK_CANCEL_OPTION);
		int selectedDecorator = shapeDecoratorCombo.getSelectedIndex();
		String selectedColor = hexColors[colorsCombo.getSelectedIndex()];
		int selectedSize = (int) spinner.getValue();
		City newCity = null;
		
		switch(selectedDecorator) {
			case 0:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedColor);
					break;
			case 1:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedDecorator, selectedColor);
					break;
			case 2:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedDecorator, selectedColor);
					break;
			case 3:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedDecorator, selectedColor);
					break;
		}
		Blackboard bb = Blackboard.createInstance();
		ArrayList<City> cities = bb.getCities();
		if(newCity != null) {
			cities.remove(clickedCity.getIndex());
			cities.add(clickedCity.getIndex(), newCity);
			repaint();	
		}
	}

	/**
	 * This function specifies the operations to be performed when mouse is dragged.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseDragged(MouseEvent e) {
		try {
			if (!pressOut) {
				clickedCity.move(preX + e.getX(), preY + e.getY());
				repaint();
			}
		}  catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This function specifies the operations to be performed when mouse is released.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseReleased(MouseEvent e) {
		try {
			if (!SwingUtilities.isRightMouseButton(e) && !pressOut && clickedCity.contains(e.getX(), e.getY())) {
				clickedCity.move(preX + e.getX(), preY + e.getY());
				
				TSPSolver tsp = TSPSolver.createInstance();
				Thread thread = new Thread(tsp);
		    	thread.start();
	        	
				repaint();
			} else if (!SwingUtilities.isRightMouseButton(e) && pressOut){
				pressOut = false;
				String cityName = JOptionPane.showInputDialog(this, "Please enter the name of city you want to add : ", null);
	            
				FactoryCity fc = FactoryCity.getFC();
				if (cityName != null && cityName.trim().length() > 0) {
					fc.createCity(cityName, e.getX(), e.getY());
					
					/*if (cities.size() > 0) {
						bb.addCity(new City(cityName, cities.size(), e.getX(), e.getY(), 20, 20));
					} else {
						bb.addCity(new City(cityName, 0, e.getX(), e.getY(), 20, 20));
					}*/
					
					TSPSolver tsp = TSPSolver.createInstance();
					Thread thread = new Thread(tsp);
			    	thread.start();
	            }
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This function is used to specify the operations to be performed when mouse is moved.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * This function is used to specify the operations to be performed when mouse click happens.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * This function is used to specify the operations to be performed when mouse is entered.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * This function is used to specify the operations to be performed when mouse is exited.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * This function specifies the operations to be performed when TSPSovler is done executing.
	 * 
	 * @param o object of class Observable.
	 * 
	 * @param arg object of class Object.
	 */
	public void update(Observable o, Object arg) {
		repaint();
	}
}
