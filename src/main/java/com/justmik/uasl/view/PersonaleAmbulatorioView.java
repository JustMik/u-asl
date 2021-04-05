
package com.justmik.uasl.view;


import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;





public class PersonaleAmbulatorioView extends FocusablePanel {

	private JTabbedPane tabbedPane;
		
	public PersonaleAmbulatorioView()  {

		initialize();
	}

	
	
	private void initialize()  {
		
		setLayout(new BorderLayout());
		JLabel l1 = new JLabel("             ");
		JLabel l2 = new JLabel("             ");
		JLabel l3 = new JLabel("             ");
		
		add(l1, BorderLayout.WEST);
		add(l2, BorderLayout.EAST);
		add(l3, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel p1 = new JPanel();
		tabbedPane.addTab("Compilazione Referti", null, p1, null);

		JPanel p2 = new JPanel();		
		tabbedPane.addTab("Visualizza Visite", null, p2, null);
		

		
	}
	
	
	@Override
	public JComponent getFocus() {
		return null;
	}


	
	



}
	













