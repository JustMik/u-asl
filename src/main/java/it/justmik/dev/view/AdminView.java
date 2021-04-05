package it.justmik.dev.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.justmik.dev.controller.Controller;
import it.justmik.dev.model.AdminModel;

public class AdminView extends FocusablePanel {


	private JPasswordField pwd;
	private Utenti utenti;
	private Ambulatorio ambulatorio;
	private Asl asl;
	private Prestazioni prestazioni;
	/**
	 * Create the panel.
	 */
	public AdminView() {
		utenti = new Utenti();
		ambulatorio = new Ambulatorio();
		asl = new Asl();
		prestazioni = new Prestazioni();
		
		initialize();
	}

	
	private void initialize() {
		
		setLayout(new BorderLayout());
			
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int choice = tabbedPane.getSelectedIndex();
				if (choice==0){
					Controller.getView().getRootPane().setDefaultButton(utenti.aggiungi);
					utenti.getFocus();
				}
				else if (choice==1){
					Controller.getView().getRootPane().setDefaultButton(ambulatorio.ADD_SPEC);
					ambulatorio.getFocus();
				}
				else if (choice==2){
					Controller.getView().getRootPane().setDefaultButton(asl.ADD_ASL);
					asl.getFocus();
				}
				else if (choice==3){
					Controller.getView().getRootPane().setDefaultButton(prestazioni.ADD_PREST);
					prestazioni.getFocus();
				}
			}
		});
		
		tabbedPane.addTab("Aggiungi Utente", null, utenti, null);
		
		tabbedPane.addTab("Aggiungi Ambulatorio", null, ambulatorio, null);

		tabbedPane.addTab("Aggiungi ASL", null, asl, null);
		
		tabbedPane.addTab("Aggiungi Prestazione", null, prestazioni, null);
		
		add(tabbedPane, BorderLayout.CENTER);
		
		

	}


	public ArrayList<String> getParamUtenti(){
		return utenti.getParam();
	}
	
	public ArrayList<String> getParamAmbul(){
		return ambulatorio.getParam();
	}
	
	public ArrayList<String> getParamAsl(){
		return asl.getParam();
	}
	
	public ArrayList<String> getParamPrest(){
		return prestazioni.getParam();
	}
	
	
	
//////////////////////////// TABBED PANE AGGIUNTA UTENTI CLASS ///////////////////////////
	
	private class Utenti extends FocusablePanel implements ActionListener{
		
		private JComboBox combo, prov;
		private JTextField via, cap, citta, email, cf, nome, cognome;
		private JButton aggiungi;
		
		public Utenti(){

			
			JPanel panel = new JPanel();
			add(panel);
			panel.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel label_12 = new JLabel("");
			panel.add(label_12);
			
			JLabel label_11 = new JLabel("");
			panel.add(label_11);
			
			JLabel lblNewLabel_3 = new JLabel("Seleziona");
			panel.add(lblNewLabel_3);
			
			combo = new JComboBox(){
				@Override
				public String toString(){
					return "COMBO";
				}
			};
			combo.setModel(new DefaultComboBoxModel(new String[] {"Seleziona", "ADMIN", "MEDICO", "PERSONALE ASL", "PERSONALE AMBULATORIO"}));
			panel.add(combo);
			combo.addActionListener(this);
			
			JLabel label_9 = new JLabel("");
			panel.add(label_9);
			
			JLabel lblNewLabel_4 = new JLabel("");
			panel.add(lblNewLabel_4);
			
			JLabel lblNewLabel_5 = new JLabel("E-mail");
			panel.add(lblNewLabel_5);
			
			email = new JTextField();
			email.setEditable(false);
			panel.add(email);
			email.setColumns(10);
			
			JLabel label = new JLabel("Password");
			panel.add(label);
			
			pwd = new JPasswordField();
			pwd.setEditable(false);
			pwd.setColumns(10);
			panel.add(pwd);

			JLabel label_0 = new JLabel("CF");
			panel.add(label_0);
			
			cf = new JTextField();
			cf.setEditable(false);
			cf.setColumns(10);
			panel.add(cf);
			
			JLabel label_1 = new JLabel("Nome");
			panel.add(label_1);
			
			nome = new JTextField();
			nome.setEditable(false);
			nome.setColumns(10);
			panel.add(nome);
			
			JLabel label_2 = new JLabel("Cognome");
			panel.add(label_2);
			
			cognome = new JTextField();
			cognome.setEditable(false);
			cognome.setColumns(10);
			panel.add(cognome);
			
			JLabel label_3 = new JLabel("Via");
			panel.add(label_3);
			
			via = new JTextField();
			via.setEditable(false);
			via.setColumns(10);
			panel.add(via);
			
			
			JLabel label_5 = new JLabel("Città");
			panel.add(label_5);
			
			citta = new JTextField();
			citta.setEditable(false);
			citta.setColumns(10);
			panel.add(citta);
			
			JLabel label_6 = new JLabel("Provincia");
			panel.add(label_6);
			
			prov = new JComboBox(new DefaultComboBoxModel( new String[] {"--- Seleziona ---", 
					"BL", "PD", "RO", "TV", "VE", "VR", "VI"}  ) );
			prov.setEnabled(false);
			panel.add(prov);
			
			JLabel label_4 = new JLabel("CAP");
			panel.add(label_4);
			
			cap = new JTextField();
			cap.setEditable(false);
			cap.setColumns(10);
			panel.add(cap);
			
			JLabel lblNewLabel_6 = new JLabel("");
			panel.add(lblNewLabel_6);
			
			JLabel label_8 = new JLabel("");
			panel.add(label_8);
			
			JLabel label_10 = new JLabel("");
			panel.add(label_10);
			
			aggiungi = new JButton("Aggiungi"){
				@Override
				public String toString(){
					return "ADD_UTENTI";
				}
			};
			

			panel.add(aggiungi);

					
			aggiungi.addActionListener(this);
			//returnBtn.addActionListener(this);
		}
		
		public ArrayList<String> getParam(){
			String[] strs = {email.getText(), String.valueOf(pwd.getPassword()) , cf.getText(), nome.getText(),
					cognome.getText(), via.getText(), citta.getText(), (String)prov.getSelectedItem(), cap.getText()};
			
			return new ArrayList<>(Arrays.asList(strs));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();
			String ruolo = (String) combo.getSelectedItem();
			
			if (act.equals("COMBO")){
				
				switch (ruolo){
					case "MEDICO":
						resetFields();
						email.setEditable(true);
						pwd.setEditable(true);
						cf.setEditable(true);
						nome.setEditable(true);
						cognome.setEditable(true);
						via.setEditable(true);
						cap.setEditable(true);
						citta.setEditable(true);
						prov.setEnabled(true);
						getFocus();
						break;
					case "PERSONALE ASL": 
					case "PERSONALE AMBULATORIO":
					case "ADMIN":
						resetFields();
						email.setEditable(true);
						pwd.setEditable(true);
						cf.setEditable(false);
						nome.setEditable(false);
						cognome.setEditable(false);
						via.setEditable(false);
						cap.setEditable(false);
						citta.setEditable(false);
						prov.setEnabled(false);
						getFocus();
						break;
					default:
						resetFields();
						email.setEditable(false);
						pwd.setEditable(false);
						cf.setEditable(false);
						nome.setEditable(false);
						cognome.setEditable(false);
						via.setEditable(false);
						cap.setEditable(false);
						citta.setEditable(false);
						prov.setEnabled(false);				}
			}
			else if (act.equals("ADD_UTENTI")){
				
				if (combo.getSelectedIndex() != 0){
					if ( !((String)combo.getSelectedItem()).equals("MEDICO") && !(email.getText().equals("")) && 
							(!String.valueOf(pwd.getPassword()).equals(""))) {
						if (Controller.getInstance().displayContent(act, ruolo)){
							resetFields();
							combo.setSelectedIndex(0);
						}
					}
					else if ( !(email.getText().equals("")) && (!String.valueOf(pwd.getPassword()).equals("")) &&
							(!cf.getText().equals("")) && (!nome.getText().equals("")) && (!cognome.getText().equals("")) &&
							(!via.getText().equals("")) && (!citta.getText().equals("")) && (prov.getSelectedIndex() != 0) &&
							(!cap.getText().equals(""))){
						if (Controller.getInstance().displayContent(act, ruolo)){
							resetFields();
							combo.setSelectedIndex(0);
						}
					}
					else
						JOptionPane.showMessageDialog(this,"Riempire tutti i campi!", "Error",JOptionPane.ERROR_MESSAGE);	
				}
				else
					JOptionPane.showMessageDialog(this,"Riempire tutti i campi!", "Error",JOptionPane.ERROR_MESSAGE);
				
			}
			
		}
		
		private void resetFields(){
			email.setText("");
			pwd.setText("");
			cf.setText("");
			nome.setText("");
			cognome.setText("");
			via.setText("");
			cap.setText("");
			citta.setText("");
			prov.setSelectedIndex(0);;
		}

		@Override
		public JComponent getFocus() {		
			email.requestFocus();
			return email;
		}
		
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
//////////////////////////// TABBED PANE AGGIUNTA AMBULATORI CLASS ///////////////////////////
	
	private class Ambulatorio extends FocusablePanel implements ActionListener{
		
		private JButton ADD_SPEC;
		private JTextField id, spec, nome, via, cap, citta, annoStip;
		private JComboBox id_azienda, prov;
		private String azienda;
		
		public Ambulatorio() {
			
			JLabel label10 = new JLabel("");
			label10.setVisible(false);
			
			JLabel label13= new JLabel("");
			label13.setVisible(false);
			
			JLabel label14 = new JLabel("");
			label14.setVisible(false);
			
			JLabel label11 = new JLabel("");
			label11.setVisible(false);
			
			JLabel label00 = new JLabel("ID:");
			id = new JTextField(15);
			
			JLabel label0 = new JLabel("Specializzazione:");
			spec = new JTextField(15);
			
			JLabel label1 = new JLabel("Nome:");
			nome = new JTextField(15);
			
			JLabel label2 = new JLabel("Via:");
			via = new JTextField(15);

			JLabel label3 = new JLabel("Citta:");
			citta = new JTextField(15);

			JLabel label4 = new JLabel("Provincia:");
			prov = new JComboBox(new DefaultComboBoxModel( new String[] {"--- Seleziona ---", 
					"BL", "PD", "RO", "TV", "VE", "VR", "VI"}  ) ){
				@Override
				public String toString(){
					return "PROVINCIA";
				}
			};
			
			prov.addActionListener(this);
			

			
			JLabel label5 = new JLabel("Cap:");
			cap = new JTextField(15);
			
			JLabel label6 = new JLabel("Anno Stipula:");
			annoStip = new JTextField(15);

			JLabel label7 = new JLabel("Azienda Sanitaria:");
			id_azienda = new JComboBox(){
				@Override
				public String toString(){
					return "COMBO_ASL";
				}
			};

			
			JLabel label9 = new JLabel("");
			label9.setVisible(false);
	 
			ADD_SPEC=new JButton("Aggiungi"){
				@Override
				public String toString(){
					return "ADD_AMBUL";
				}
			};
			
			ADD_SPEC.addActionListener(this);
	  			
			JPanel panel=new JPanel(new GridLayout(0,2));
			panel.add(label10);
			panel.add(label11);
			panel.add(label00);
			panel.add(id);
			panel.add(label0);
			panel.add(spec);
			panel.add(label1);
			panel.add(nome);
			panel.add(label2);
			panel.add(via);
			panel.add(label3);
			panel.add(citta);
			panel.add(label4);
			panel.add(prov);
			panel.add(label5);
			panel.add(cap);
			panel.add(label6);
			panel.add(annoStip);
			panel.add(label7);
			panel.add(id_azienda);
			panel.add(label13);
			panel.add(label14);
			panel.add(label9);
			panel.add(ADD_SPEC);
			add(panel,BorderLayout.CENTER);
		}
		
		public ArrayList<String> getParam(){
			String[] strs = {id.getText(), spec.getText(),  nome.getText(), via.getText(), citta.getText(), 
					(String)prov.getSelectedItem(), cap.getText(), annoStip.getText(), (String) id_azienda.getSelectedItem()};
			
			return new ArrayList<>(Arrays.asList(strs));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();

			if (act.equals("PROVINCIA")){
				 id_azienda.removeAllItems();
				 id_azienda.addItem("--- Seleziona ---");
				 for (String s: ((AdminModel) (Controller.getInstance().getModel())).getAslList((String)prov.getSelectedItem()) ){
					 id_azienda.addItem(s);
				 }
			}
			else if (act.equals("ADD_AMBUL")){
				if ( !(id.getText().equals("")) &&  (!spec.getText().equals("")) &&  (!nome.getText().equals("")) &&
						(!via.getText().equals("")) && (!citta.getText().equals("")) && (prov.getSelectedIndex() != 0) &&
						(!cap.getText().equals(""))){
					if (Controller.getInstance().displayContent(act, "ADMIN")){
						JOptionPane.showMessageDialog(this,"Inserimento avvenuto con successo!: ", "",JOptionPane.INFORMATION_MESSAGE);
						resetFields();
					}	
				}
				else
					JOptionPane.showMessageDialog(this,"Riempire tutti i campi! ", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}

		private void resetFields() {
			id.setText("");
			spec.setText("");
			nome.setText("");
			via.setText("");
			citta.setText("");
			prov.setSelectedIndex(0);;
			cap.setText("");
			annoStip.setText("");
			id_azienda.removeAllItems();
		}

		@Override
		public JComponent getFocus() {
			id.requestFocus();
			return id;
		}

	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
////////////////////////////TABBED PANE AGGIUNTA UTENTI CLASS ///////////////////////////

	private class Asl extends FocusablePanel implements ActionListener{

		private JComboBox prov;
		private JTextField  id, nome, via, cap, citta, numDip;
		private JButton ADD_ASL;
		
		public Asl() {

			JLabel label10 = new JLabel("");
			label10.setVisible(false);
			
			JLabel label11 = new JLabel("");
			label11.setVisible(false);
			
			JLabel label7 = new JLabel("");
			label7.setVisible(false);
			
			JLabel label8 = new JLabel("");
			label8.setVisible(false);
	
			JLabel label0 = new JLabel("ID:");
			id = new JTextField(15);
			
			JLabel label1 = new JLabel("Nome:");
			nome = new JTextField(15);
	
			JLabel label2 = new JLabel("Via:");
			via = new JTextField(15);
	
			JLabel label3 = new JLabel("Citta:");
			citta = new JTextField(15);
	
			JLabel label4 = new JLabel("Provincia:");
			prov = new JComboBox(new DefaultComboBoxModel( new String[] {"--- Seleziona ---", 
					"BL", "PD", "RO", "TV", "VE", "VR", "VI"}  ) );
	
			JLabel label5 = new JLabel("Cap:");
			cap = new JTextField(15);
	
			JLabel label6 = new JLabel("Numero Dipendenti:");
			numDip = new JTextField(15);
	
	
			JLabel label9 = new JLabel("");
			label9.setVisible(false);
	
			ADD_ASL=new JButton("Aggiungi"){
				@Override
				public String toString(){
					return "ADD_ASL";
				}
			};
			
			ADD_ASL.addActionListener(this);
		
			JPanel panel=new JPanel(new GridLayout(0,2));
			panel.add(label8);
			panel.add(label7);
			panel.add(label0);
			panel.add(id);
			panel.add(label1);
			panel.add(nome);
			panel.add(label2);
			panel.add(via);
			panel.add(label3);
			panel.add(citta);
			panel.add(label4);
			panel.add(prov);
			panel.add(label5);
			panel.add(cap);
			panel.add(label6);
			panel.add(numDip);
			panel.add(label10);
			panel.add(label11);
			
			panel.add(label9);
			panel.add(ADD_ASL);
			add(panel,BorderLayout.CENTER);
		}
	
		public ArrayList<String> getParam(){
			String[] strs = {id.getText(), nome.getText(),  via.getText(), citta.getText(), 
					(String)prov.getSelectedItem(), cap.getText(), numDip.getText()};
			
			return new ArrayList<>(Arrays.asList(strs));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();

			if (act.equals("ADD_ASL")){
				if ( !(id.getText().equals("")) && (!nome.getText().equals("")) && (!via.getText().equals("")) && 
						(!citta.getText().equals("")) && (prov.getSelectedIndex() != 0) &&
						(!cap.getText().equals("")) && (!numDip.getText().equals(""))) {
					if(Controller.getInstance().displayContent(act, "ADMIN")){
						resetFields();
					}
				}
				else
					JOptionPane.showMessageDialog(this,"Riempire tutti i campi!", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private void resetFields() {
			id.setText("");
			nome.setText("");
			via.setText("");
			citta.setText("");
			prov.setSelectedIndex(0);;
			cap.setText("");
			numDip.setText("");
		}

		@Override
		public JComponent getFocus() {
			id.requestFocus();
			return id;
		}
	
	}
	
	private class Prestazioni extends FocusablePanel implements ActionListener{
		private JTextField id_prest, nome;
		private JTextPane desc;
		private JButton ADD_PREST;
		private boolean areaClicked = false;
		
		public Prestazioni() {
						
			JPanel princ = new JPanel(new GridLayout(0, 1));
			JPanel panel=new JPanel(new GridLayout(0,2));
			JPanel panelDesc = new JPanel(new GridLayout(0, 1));
			JPanel panelBot = new JPanel();
				
			JLabel label0 = new JLabel("");
			label0.setVisible(false);
			JLabel label00 = new JLabel("");
			label00.setVisible(false);
			JLabel label1 = new JLabel("");
			label1.setVisible(false);
			JLabel label2 = new JLabel("");
			label2.setVisible(false);
			JLabel label3 = new JLabel("ID Prestazione:");
			JLabel label4 = new JLabel("Nome:");
			
			JScrollPane scroll = new JScrollPane();		
			
			
			id_prest = new JTextField(15);
			nome = new JTextField(15);
	
			
			desc = new JTextPane();
			desc.setText("Descrizione");
			desc.setForeground(Color.GRAY);

			desc.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if (desc.getText().equals("Descrizione") && !areaClicked){
						areaClicked = true;
						desc.setForeground(Color.BLACK);
						desc.setText("");
						repaint();
						revalidate();
					}
				}
			});
	
			

				
			ADD_PREST=new JButton("Aggiungi"){
				@Override
				public String toString(){
					return "ADD_PREST";
				}
			};
			ADD_PREST.addActionListener(this);	
			
			scroll.setViewportView(desc);


			panel.add(label1);
			panel.add(label2);
			panel.add(label3);
			panel.add(id_prest);
			panel.add(label4);
			panel.add(nome);
			panel.add(label0);
			panel.add(label00);
			
			panelDesc.add(scroll);

			panelBot.add(ADD_PREST);
			
			princ.add(panel);
			princ.add(panelDesc);
			princ.add(panelBot);
			
			add(princ);
			


		}
		
		public ArrayList<String> getParam(){
			String[] strs = {id_prest.getText(), nome.getText(),  desc.getText()};
			
			return new ArrayList<>(Arrays.asList(strs));
		}
		

		
		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();
			
			if (act.equals("ADD_PREST")){
				if ( (!id_prest.getText().equals("")) && (!nome.getText().equals("")) ){
					if (Controller.getInstance().displayContent(act, "ADMIN")){
						resetFields();
					}
				}
				else
					JOptionPane.showMessageDialog(this,"Riempire tutti i campi!", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}

		private void resetFields() {
			id_prest.setText("");
			nome.setText("");
			desc.setText("Descrizione");
			desc.setForeground(Color.GRAY);
			
		}

		@Override
		public JComponent getFocus() {
			id_prest.requestFocus();
			return id_prest;
		}
		
	}

	@Override
	public JComponent getFocus() {
		utenti.combo.requestFocus();;
		return utenti.combo;
	}


	
}










