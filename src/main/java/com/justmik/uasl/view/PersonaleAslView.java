package com.justmik.uasl.view;


import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;





public class PersonaleAslView extends FocusablePanel {

	private JTabbedPane tabbedPane;
		
	public PersonaleAslView()  {

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
		tabbedPane.addTab("Inserisci/Modifica Ambulatorio", null, p1, null);

		JPanel p2 = new JPanel();		
		tabbedPane.addTab("Visualizza Visite", null, p2, null);
		

		
	}
	
	
	@Override
	public JComponent getFocus() {
		return null;
	}



	
	



}
	













