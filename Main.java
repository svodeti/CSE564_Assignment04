import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Assignment 03.Patterns, CSE 564
 * Professor: Javier Gonzalez-Sanchez
 *
 * @author Kenil Vipulkumar Patel (kpatel99@asu.edu), Nimil Sanjeev Shah(nshah55@asu.edu)
 */
public class Main extends JFrame {
	
	/**
	 * This constructor is used to initialize the menu bar (with it's options) and the object of Workspace, 
	 * where cities will be created and dragged.
	 * It also creates the instance of class TSPSolver and adds the object of class Workspace as a observer in class TSPSolver.
	 */
	public Main() {
		Workspace drawArea = new Workspace();
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");

        JMenuItem newData = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
  
        newData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				drawArea.newMenuItem();
	    	}
		});
        
        save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				drawArea.saveCities();
	    	}
		});
        
        load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				drawArea.loadCities();
	    	}
		});
        
        menu.add(newData);
        menu.add(save);
        menu.add(load);
  
        mb.add(menu);
        setJMenuBar(mb);
        
		add(drawArea);
		TSPSolver tsp = TSPSolver.createInstance();
		tsp.addObserver(drawArea);
	}
	
	/**
	 * It sets the size of Main(JFrame) and makes it visible.
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(1910, 1000);
		main.setVisible(true);
	}

}