
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package it.justmik.dev.view;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import com.toedter.calendar.JDateChooser;

import it.justmik.dev.controller.Controller;
import it.justmik.dev.model.PazientiModel;


public class PazientiView extends FocusablePanel {


	private JTabbedPane tabbedPane;
	private VisualizzaAmbulatori ambulatori = new VisualizzaAmbulatori();
	private Prenota prenota = new Prenota();
	private VisiteEffettuate visiteEffettuate = new VisiteEffettuate();
	private VisualizzaPrenotazioni visPrenot = new VisualizzaPrenotazioni();
	
	
	public PazientiView()  {

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
		
		tabbedPane.addChangeListener(new ChangeListener() {
	
			@Override
			public void stateChanged(ChangeEvent e) {
				int choice = tabbedPane.getSelectedIndex();
				if (choice==0){
					Controller.getView().getRootPane().setDefaultButton(ambulatori.btnCerca);
					ambulatori.getFocus();
				}
				else if (choice==1){
					Controller.getView().getRootPane().setDefaultButton(prenota.btnPrenota);
					prenota.getFocus();
				}
				else if (choice==2){
					Controller.getView().getRootPane().setDefaultButton(visiteEffettuate.btnCerca);
					visiteEffettuate.getFocus();
				}
				else if (choice==3){
					Controller.getView().getRootPane().setDefaultButton(visPrenot.btnCerca);
					visPrenot.getFocus();
				}
			}
		});
		
		
		add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab("Ambulatori", null, ambulatori, null);

		tabbedPane.addTab("Prenota Visita", null, prenota, null);
		
		tabbedPane.addTab("Esito Visite", null, visiteEffettuate, null);
		
		tabbedPane.addTab("Visualizza Prenotazioni", null, visPrenot, null);
		

		
	}
	
	public ArrayList<String> getParamAmbulatori(){
		return ambulatori.getParam();
	}
	
	
	private class VisualizzaAmbulatori extends FocusablePanel implements ActionListener{
		private JTextField spec;
		private JTextField via;
		private JTextField citta;
		private JTextField cap;
		private JTextField anno_stip;
		private JTable tableAmb;
		private JTable tablePrest;
		private JComboBox comboBoxProv, comboBoxAsl;
		private JButton btnCerca;
		private DefaultTableModel modelAmb, modelPrest;

		/**
		 * Create the panel.
		 */
		public VisualizzaAmbulatori() {
			setLayout(new BorderLayout(0, 0));
			
			JPanel panel_13 = new JPanel();
			add(panel_13, BorderLayout.CENTER);
			panel_13.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_1 = new JPanel();
			panel_13.add(panel_1, BorderLayout.NORTH);
			panel_1.setLayout(new GridLayout(0, 2, 0, 0));
			
			JPanel panel_14 = new JPanel();
			panel_1.add(panel_14);
			panel_14.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel label = new JLabel("Seleziona la tua provincia d'interesse: ");
			panel_14.add(label);
			
			comboBoxProv = new JComboBox(){
				@Override
				public String toString(){
					return "COMBO_PROV";
				}
			};
			comboBoxProv.addItem("--- Tutte ---");
			comboBoxProv.addItem("BL");
			comboBoxProv.addItem("PD");
			comboBoxProv.addItem("RO");
			comboBoxProv.addItem("TV");
			comboBoxProv.addItem("VE");
			comboBoxProv.addItem("VI");
			comboBoxProv.addItem("VR");
			comboBoxProv.addActionListener(this);
			
			panel_14.add(comboBoxProv);
			
			JLabel lblNewLabel_2 = new JLabel("");
			panel_1.add(lblNewLabel_2);
			
			JPanel panel_15 = new JPanel();
			panel_1.add(panel_15);
			panel_15.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel lblAslDisponibiliNella = new JLabel("ASL disponibili nella provincia:");
			panel_15.add(lblAslDisponibiliNella);
			
			comboBoxAsl = new JComboBox();
			comboBoxAsl.addItem("--- Tutte ---");
			panel_15.add(comboBoxAsl);
			
			JLabel label_3 = new JLabel("");
			panel_1.add(label_3);
			
			JLabel lblNewLabel_3 = new JLabel("");
			panel_13.add(lblNewLabel_3, BorderLayout.SOUTH);
			
			JPanel panel_2 = new JPanel();
			panel_13.add(panel_2, BorderLayout.CENTER);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_3 = new JPanel();
			panel_2.add(panel_3, BorderLayout.NORTH);
			panel_3.setLayout(new GridLayout(0, 2, 0, 0));
			
			JPanel panel = new JPanel();
			panel_2.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_5 = new JPanel();
			panel.add(panel_5, BorderLayout.NORTH);
			panel_5.setLayout(new GridLayout(0, 2, 0, 0));
			
			JPanel panel_16 = new JPanel();
			FlowLayout flowLayout_1 = (FlowLayout) panel_16.getLayout();
			flowLayout_1.setAlignment(FlowLayout.RIGHT);
			panel_5.add(panel_16);
			
			btnCerca = new JButton("Cerca"){
				@Override
				public String toString(){
					return "CERCA_AMBUL";
				}
			};
			panel_16.add(btnCerca);
			btnCerca.addActionListener(this);
			
			JPanel panel_4 = new JPanel();
			panel.add(panel_4, BorderLayout.CENTER);
			panel_4.setLayout(new GridLayout(0, 2, 0, 0));
			
			JPanel panel_6 = new JPanel();
			panel_4.add(panel_6);
			panel_6.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_7 = new JPanel();
			panel_6.add(panel_7);
			panel_7.setLayout(new BorderLayout(0, 0));
			
			JLabel label_2 = new JLabel("    ");
			panel_7.add(label_2, BorderLayout.EAST);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panel_7.add(scrollPane_1, BorderLayout.CENTER);
			
			modelAmb = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int collumn){
					return false;
				}
				@Override
				public String toString(){
					return "MODEL_AMB";
				}
			};
			modelAmb.addColumn("ID AMBULATORIO");
			modelAmb.addColumn("NOME AMBULATORIO");
			
			tableAmb = new JTable(modelAmb);
			tableAmb.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					String id_amb = (String)tableAmb.getModel().getValueAt(tableAmb.getSelectedRow(), 0);
					
				///// POPULATE PRESTAZIONI TABLE ////
					Vector<String> colName= new Vector<String>();
					colName.add("PRESTAZIONE");
					colName.add("DESCRIZIONE");
					modelPrest.setDataVector(( (PazientiModel) (Controller.getInstance().getModel())).getPrestFromAmb(id_amb), colName);
					
				///// POPULATE AMBULATORIO FIELD ////
					String [] str = ( (PazientiModel) (Controller.getInstance().getModel())).getInfoAmb(id_amb);
					if (str != null){
						spec.setText(str[0]);
						via.setText(str[1]);
						citta.setText(str[2]);
						cap.setText(str[3]);
						anno_stip.setText(str[4]);
					}
					else{
						spec.setText("");
						via.setText("");
						citta.setText("");
						cap.setText("");
						anno_stip.setText("");
					}
				}
			});
			

			scrollPane_1.setViewportView(tableAmb);
			
			JPanel panel_8 = new JPanel();
			panel_4.add(panel_8);
			panel_8.setLayout(new GridLayout(0, 1, 0, 0));
			
			JPanel panel_9 = new JPanel();
			panel_8.add(panel_9);
			panel_9.setLayout(new GridLayout(2, 0, 0, 0));
			
			JPanel panel_10 = new JPanel();
			panel_9.add(panel_10);
			panel_10.setLayout(new GridLayout(8, 2, 0, 0));
			
			JLabel lblSpecializzazione = new JLabel("Specializzazione");
			panel_10.add(lblSpecializzazione);
			
			spec = new JTextField();
			panel_10.add(spec);
			spec.setEditable(false);
			
			JLabel lblVia = new JLabel("Via");
			panel_10.add(lblVia);
			
			via = new JTextField();
			via.setEditable(false);
			panel_10.add(via);
			
			JLabel lblCitt = new JLabel("Città");
			panel_10.add(lblCitt);
			
			citta = new JTextField();
			citta.setEditable(false);
			panel_10.add(citta);
			
			JLabel lblNewLabel = new JLabel("CAP");
			panel_10.add(lblNewLabel);
			
			cap = new JTextField();
			cap.setEditable(false);
			panel_10.add(cap);
			
			JLabel lblNewLabel_1 = new JLabel("Anno stipula");
			panel_10.add(lblNewLabel_1);
			
			anno_stip = new JTextField();
			panel_10.add(anno_stip);
			anno_stip.setEditable(false);
			
			JPanel panel_11 = new JPanel();
			panel_9.add(panel_11);
			panel_11.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_12 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_12.getLayout();
			panel_11.add(panel_12, BorderLayout.NORTH);
			
			JLabel label_1 = new JLabel("PRESTAZIONI");
			panel_12.add(label_1);
			
			JScrollPane scrollPane = new JScrollPane();
			panel_11.add(scrollPane, BorderLayout.CENTER);
			
			modelPrest = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column){
					return false;
				}
			};
			modelPrest.addColumn("PRESTAZIONE");
			modelPrest.addColumn("DESCRIZIONE");
			

			tablePrest = new JTable(modelPrest);
			scrollPane.setViewportView(tablePrest);

		}

		@Override
		public JComponent getFocus() {
			comboBoxProv.requestFocus();
			return comboBoxProv;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();
			String prov = (String) comboBoxProv.getSelectedItem();
						
			if (act.equals("COMBO_PROV")){
				comboBoxAsl.removeAllItems();
				for (String s: ((PazientiModel) (Controller.getInstance().getModel())).getAmbulatorioFromProv(prov))
					comboBoxAsl.addItem(s);
			}
			/////////// VISUALIZZA AMBULATORI ////////////
			if (act.equals("CERCA_AMBUL")){
				ArrayList<String> list = getParamAmbulatori();

				Vector<String> colName= new Vector<String>();
				colName.add("ID AMBULATORIO");
				colName.add("NOME AMBULATORIO");
				Vector<Vector<Object>> data = ((PazientiModel) Controller.getInstance().getModel()).getDataAmbulatori(list);
				modelAmb.setDataVector(data, colName);

				if (data.isEmpty())
					JOptionPane.showMessageDialog(this,"Nessuna voce trovata!! ", "Error",JOptionPane.ERROR_MESSAGE);

				
				int num = modelPrest.getRowCount();
				for (int i=0; i<num; i++)
					modelPrest.removeRow(0);
				
				//reset fields ambulatorio info
				spec.setText("");
				via.setText("");
				citta.setText("");
				cap.setText("");
				anno_stip.setText("");


			}
		}
		
		public ArrayList<String> getParam(){
			String[] strs = {(String) comboBoxProv.getSelectedItem(), (String) comboBoxAsl.getSelectedItem()};
			
			return new ArrayList<>(Arrays.asList(strs));
		}

	}
	
	private class Prenota extends FocusablePanel implements ActionListener{
		
		private JComboBox comboAmb, comboPrest, comboOra, regime;
		private JTextPane note;
		private Date date;
		private JButton btnPrenota;
		private JDateChooser dateChooser;
		private boolean areaClicked = false;

		public Prenota(){

			
			setLayout(new BorderLayout());
			
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			add(content, BorderLayout.CENTER);
			
			JPanel pa = new JPanel(new BorderLayout());
			
			JLabel spaceLeft =  new JLabel("                                                 ");
			JLabel spaceRight = new JLabel("                                                 ");
			JLabel spaceNorth = new JLabel("             ");
			
			content.add(spaceLeft, BorderLayout.WEST);
			content.add(spaceRight, BorderLayout.EAST);
			content.add(spaceNorth, BorderLayout.NORTH);
			content.add(pa, BorderLayout.CENTER);
	  
			JPanel panel=new JPanel();
			panel.setLayout(new GridLayout(0, 2, 0, 0));
			pa.add(panel, BorderLayout.NORTH);
			
			JLabel label_3 = new JLabel("Seleziona ambulatorio:");
			panel.add(label_3);
			
			comboAmb = new JComboBox( getAmbulatori() ){		
				@Override
				public String toString(){
					return "COMBO_SET_PREST";
				}
			};
			comboAmb.addActionListener(this);
			panel.add(comboAmb);
			
			JLabel label_2 = new JLabel("Seleziona prestazione:");
			panel.add(label_2);
			
			comboPrest = new JComboBox();
			panel.add(comboPrest);
		
			JLabel label_4 = new JLabel("Data:");
			panel.add(label_4);
			
			dateChooser = new JDateChooser();
			date = new Date();
			dateChooser.setDate(date);
			dateChooser.setMinSelectableDate(date);
			dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if ( comboAmb.getSelectedIndex()!=0 ){
						comboOra.removeAllItems();
						comboOra.setModel(getOra());
					}
				}
			});
			
			panel.add(dateChooser);
			
			JLabel label_5 = new JLabel("Ora:");
			panel.add(label_5);
			
			comboOra = new JComboBox();
			comboOra.addItem("--- Seleziona ---");
			
			panel.add(comboOra);
			
			JLabel lregime = new JLabel("Regime: ");
			regime = new JComboBox();
			regime.addItem("--- Seleziona ---");
			regime.addItem("Nazionale");
			regime.addItem("Di Solvenza");
			
			panel.add(lregime);
			panel.add(regime);
			
			JLabel label_1 = new JLabel("");
			panel.add(label_1);
			
			JLabel label = new JLabel("");
			panel.add(label);
			
			JPanel panel_2 = new JPanel();
			pa.add(panel_2, BorderLayout.CENTER);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_1 = new JPanel();
			panel_2.add(panel_1, BorderLayout.NORTH);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_3 = new JPanel(new GridLayout(0, 2));
			JLabel l = new JLabel("");
			l.setVisible(false);
			
			panel_1.add(panel_3, BorderLayout.NORTH);
			
			note = new JTextPane();
			note.setText("Descrizione");
			note.setForeground(Color.GRAY);
			note.setPreferredSize(new Dimension(640, 120));
			
			note.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if (!areaClicked){
						areaClicked = true;
						note.setForeground(Color.BLACK);
						note.setText("");
						repaint();
						revalidate();
					}
				}
			});
			panel_3.add(l);
			
			JScrollPane s = new JScrollPane(note);
			
			panel_3.add(s);
			
			JPanel panel_4 = new JPanel();
			FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
			flowLayout_1.setAlignment(FlowLayout.RIGHT);
			panel_1.add(panel_4, BorderLayout.SOUTH);
			
			btnPrenota = new JButton("PRENOTA"){
				@Override
				public String toString(){
					return "BTN_PRENOTA";
				}
			};
			panel_4.add(btnPrenota);
			btnPrenota.addActionListener(this);

			
			

		}


		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();
			
			if (act.equals("BTN_PRENOTA")){

				Date data = dateChooser.getDate();
				String ora = (String) comboOra.getSelectedItem();
				String amb = (String) comboAmb.getSelectedItem();
				String prest = (String) comboPrest.getSelectedItem();
				String reg = (String) regime.getSelectedItem();
				
				if ( (comboAmb.getSelectedIndex() != 0) && (comboPrest.getSelectedIndex() != 0) 
						&& (comboOra.getSelectedIndex() != 0) && (regime.getSelectedIndex() != 0) ) {
				
					if (( (PazientiModel) (Controller.getInstance().getModel()) ).setPrenotazione(data, ora, amb, note.getText(), prest, reg)){
						JOptionPane.showMessageDialog(Controller.getView(),"Prenotazione Avvenuta Con Successo", "Success",JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(Controller.getView(), "Problem to Add Prenotation", "Error",JOptionPane.ERROR_MESSAGE);
					}
					updateView();
				}
				else
					JOptionPane.showMessageDialog(Controller.getView(), "Riempire tutti i campi!", "Error",JOptionPane.ERROR_MESSAGE);
				
			}
			else if (act.equals("COMBO_SET_PREST")){
				if ( comboAmb.getSelectedIndex()!=0 ){
					
					comboPrest.removeAllItems();
					comboPrest.setModel(getPrestazioni());
					
					comboOra.removeAllItems();
					comboOra.setModel(getOra());
					
				}
			}
			
		
		}

		@Override
		public JComponent getFocus() {
			comboAmb.requestFocus();
			return comboAmb;
		} 
		
		private void updateView(){

			comboAmb.setSelectedIndex(0);
	
			comboPrest.removeAllItems();		
			comboOra.removeAllItems();
			
			note.setText("Descrizione");
			note.setForeground(Color.GRAY);
			areaClicked = false;
			
			dateChooser.setDate(new Date());
			
			regime.setSelectedIndex(0);
		}
		
		private DefaultComboBoxModel<String> getPrestazioni() {
			String amb = (String) comboAmb.getSelectedItem();
			return (( (PazientiModel) (Controller.getInstance().getModel()) ).getPrestFromAmbName(amb));	
		}

		private DefaultComboBoxModel<String> getAmbulatori() {
			return ( (PazientiModel) (Controller.getInstance().getModel()) ).getAmbFromUser() ;
		}

		private DefaultComboBoxModel<String> getOra(){
						
			String data = (new SimpleDateFormat( "dd-MM-yyyy")).format(dateChooser.getDate().getTime()) ;
			String amb = (String) comboAmb.getSelectedItem();
			
			
			String [] s = new String [] { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00" };
			ArrayList<String> oreDisponibili = new ArrayList<>();
			
			oreDisponibili.add("--- Seleziona ---");
			for (String a: s)
				oreDisponibili.add(a);
			
			ArrayList<String> oreOccupate = ( (PazientiModel) (Controller.getInstance().getModel()) ).getOraFromDate(data , amb);


			for (String a: oreOccupate)
				oreDisponibili.remove(a);

			
			return new DefaultComboBoxModel (oreDisponibili.toArray()); 
			
		}
		




	}
	
	
	//////////////////////////// VISUALIZZA PRENOTAZIONI ////////////////////////////
	
	
	
	private class VisiteEffettuate extends FocusablePanel implements ActionListener{
		private JTable table;
		private JTextField medico;
		private JTextField urgenza;
		private JTextField regime;
		private JTextField prest;
		private JButton btnCerca;
		private JDateChooser dateChooserDa, dateChooserA;
		private DefaultTableModel mod;
		private JTextPane esitoArea;
		private JButton btnVisualizzaPdf;

		
		public VisiteEffettuate() {
			setLayout(new BorderLayout());
			
			JPanel panel_6 = new JPanel();
			add(panel_6, BorderLayout.NORTH);
			panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JPanel panel_7 = new JPanel();
			panel_6.add(panel_7);
			
			JLabel lblCerca = new JLabel("Da   ");
			panel_7.add(lblCerca);
			
			dateChooserDa = new JDateChooser();
			dateChooserDa.setPreferredSize(new Dimension(150, 20));
			dateChooserDa.setMaxSelectableDate(new Date());
			
			dateChooserDa.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (dateChooserDa.getDate() != null)
						dateChooserA.setMinSelectableDate(dateChooserDa.getDate());
				}
			});
			
			panel_7.add(dateChooserDa);
			
			JLabel lblA = new JLabel("   a   ");
			panel_7.add(lblA);
			
			dateChooserA = new JDateChooser();
			dateChooserA.setPreferredSize(new Dimension(150, 20));
			dateChooserA.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (dateChooserA.getDate() != null)
						dateChooserDa.setMaxSelectableDate(dateChooserA.getDate());
				}
			});
			dateChooserA.setMaxSelectableDate(new Date());
			
			
			panel_7.add(dateChooserA);
			
			btnCerca = new JButton("Cerca"){
				@Override
				public String toString(){
					return "BTN_CERCA";
				}
			};
			btnCerca.addActionListener(this);
			panel_7.add(btnCerca);
			
			
			JPanel panel_2 = new JPanel(new BorderLayout());
			add(panel_2, BorderLayout.CENTER);
			
			final JSplitPane splitPane = new JSplitPane();
			splitPane.setBorder(null);
			splitPane.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e){
					splitPane.setDividerLocation(0.5);	
				}
			});
			
			splitPane.setDividerSize(20);
			panel_2.add(splitPane);
			
			JPanel p = new JPanel(new BorderLayout());
			
			JScrollPane scrollPane = new JScrollPane();
			splitPane.setLeftComponent(p);
			p.add(scrollPane, BorderLayout.CENTER);
			
			JPanel p2 = new JPanel(new GridLayout(0, 1));
			JLabel l = new JLabel(" ");
			JLabel l2 = new JLabel(" ");
			p2.add(l);
			p2.add(l2);

			p.add(p2, BorderLayout.SOUTH);
			
			mod = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column){
					return false;					
				}
			};
			
			mod.setColumnIdentifiers(new String[] {"ID VISITA", "AMBULATORIO", "DATA", "ORA"});
			
			table = new JTable(mod);
			
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (table.getSelectedRow() > -1 ) { 
						int id_vis =  (int) table.getModel().getValueAt(table.getSelectedRow(), 0);
						
						ArrayList<String> str = ((PazientiModel) (Controller.getInstance().getModel())).getEsitoVisita(id_vis);
						
						if (str.size() != 0) {
							
							regime.setText(str.get(0));
							prest.setText(str.get(1));
							esitoArea.setText(str.get(2));
							
							if (str.size() > 2  && str.get(3) != null)
								medico.setText(str.get(3));
							else
								urgenza.setText("Non Specificato");
							
							if (str.size() > 3 && str.get(4) != null)
								urgenza.setText(str.get(4));
							else
								urgenza.setText("Bassa");
						
						}
						
						btnVisualizzaPdf.setEnabled(true);	
					}
				}
			});
			
			
			
			scrollPane.setViewportView(table);
			
			
			JPanel panel_3 = new JPanel();
			splitPane.setRightComponent(panel_3);
			panel_3.setLayout(new GridLayout(2, 1, 0, 0));
			
			JPanel panel_4 = new JPanel();
			panel_3.add(panel_4);
			panel_4.setLayout(new GridLayout(9, 2, 0, 0));
			
			JLabel lblNewLabel = new JLabel("Dettagli e informazioni");
			panel_4.add(lblNewLabel);
			
			JLabel label_6 = new JLabel(" ");
			panel_4.add(label_6);
			
			JLabel label_8 = new JLabel(" ");
			panel_4.add(label_8);
			
			JLabel label = new JLabel(" ");
			panel_4.add(label);
			
			JLabel lblData = new JLabel("Medico");
			panel_4.add(lblData);
			
			medico = new JTextField();
			medico.setColumns(10);
			medico.setEditable(false);
			panel_4.add(medico);
			
			JLabel lblNomeAmbulatorio = new JLabel("Urgenza");
			panel_4.add(lblNomeAmbulatorio);
			
			urgenza = new JTextField();
			urgenza.setColumns(10);
			urgenza.setEditable(false);
			panel_4.add(urgenza);
			
			JLabel lblNomeMedico = new JLabel("Regime");
			panel_4.add(lblNomeMedico);
			
			regime = new JTextField();
			regime.setColumns(10);
			regime.setEditable(false);
			panel_4.add(regime);
			
			JLabel ld = new JLabel("Prestazione");
			panel_4.add(ld);
			
			prest = new JTextField();
			prest.setColumns(10);
			prest.setEditable(false);
			panel_4.add(prest);
			

			
			JPanel panel_5 = new JPanel();
			panel_3.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
			
			JLabel lblEsito = new JLabel("ESITO");
			panel_5.add(lblEsito, BorderLayout.NORTH);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panel_5.add(scrollPane_1, BorderLayout.CENTER);
			
			esitoArea = new JTextPane();
			esitoArea.setEditable(false);
			scrollPane_1.setViewportView(esitoArea);
			
			
			JPanel panel_1 = new JPanel();
			panel_5.add(panel_1, BorderLayout.SOUTH);
			panel_1.setLayout(new GridLayout(0, 3, 0, 0));
			
			JLabel mylbl = new JLabel("");
			panel_1.add(mylbl);
			JLabel mylbl2 = new JLabel("");
			panel_1.add(mylbl2);
			
			btnVisualizzaPdf = new JButton("Visualizza PDF"){
				@Override
				public String toString(){
					return "BTN_VISUALIZZA";
				}
			};
			btnVisualizzaPdf.setEnabled(false);
			btnVisualizzaPdf.addActionListener(this);
			panel_1.add(btnVisualizzaPdf);
			


		}

		@Override
		public JComponent getFocus() {
			btnCerca.requestFocus();
			return btnCerca;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();
			String dataDa, dataA;
			if (act.equals("BTN_CERCA")){
				resetFields();
				if(dateChooserDa.getDate() != null)
					dataDa = (new SimpleDateFormat( "dd-MM-yyyy")).format(dateChooserDa.getDate().getTime());
				else 
					dataDa = "";
				
				if(dateChooserA.getDate() != null)
					dataA = (new SimpleDateFormat( "dd-MM-yyyy")).format(dateChooserA.getDate().getTime()) ;
				else
					dataA = (new SimpleDateFormat( "dd-MM-yyyy")).format(new Date().getTime());
				
				Vector<String> titoli = new Vector<String>( Arrays.asList( new String[] {"ID VISITA", "AMBULATORIO", "DATA", "ORA"} ) );
				Vector<Vector<Object>> data = ((PazientiModel)(Controller.getInstance().getModel())).getPastVisits(dataDa, dataA);
				
				mod.setDataVector( data , titoli );
				
				if (data.isEmpty())
					JOptionPane.showMessageDialog(this,"Nessuna Visita Trovata ", "Error",JOptionPane.ERROR_MESSAGE);

								
			}
			else if (act.equals("BTN_VISUALIZZA")){
				
				visualizzaPdf(table, medico.getText(), esitoArea.getText(), prest.getText(), urgenza.getText(), 
						regime.getText(), true, "ESITO");

			}

		}
		private void resetFields(){
			medico.setText("");
			urgenza.setText("");
			regime.setText("");
			prest.setText("");
			esitoArea.setText("");	
			btnVisualizzaPdf.setEnabled(false);
		}

	}
	
////////////////////////// PANNELLO VISUALIZZA PRENOTAZIONI
	private class VisualizzaPrenotazioni extends FocusablePanel implements ActionListener{
		
		private JTable table;
		private JTextField medico;
		private JTextField urgenza;
		private JTextField regime;
		private JTextField prest;
		private JButton btnCerca, btnVisualizzaPdf, btnInvia;
		private JDateChooser dateChooserDa, dateChooserA;
		private DefaultTableModel mod;
		private JTextPane esitoArea;
		/**
		 * Create the panel.
		 */
		public VisualizzaPrenotazioni() {
			setLayout(new BorderLayout());
			
			JPanel panel_6 = new JPanel();
			add(panel_6, BorderLayout.NORTH);
			panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JPanel panel_7 = new JPanel();
			panel_6.add(panel_7);
			
			JLabel lblCerca = new JLabel("Da   ");
			panel_7.add(lblCerca);
			
			dateChooserDa = new JDateChooser();
			dateChooserDa.setPreferredSize(new Dimension(150, 20));
			dateChooserDa.setMinSelectableDate(new Date());
			dateChooserDa.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (dateChooserDa.getDate() != null)
						dateChooserA.setMinSelectableDate(dateChooserDa.getDate());
				}
			});
			
			panel_7.add(dateChooserDa);
			
			JLabel lblA = new JLabel("   a   ");
			panel_7.add(lblA);
			
			dateChooserA = new JDateChooser();
			dateChooserA.setPreferredSize(new Dimension(150, 20));
			dateChooserA.setMinSelectableDate(new Date());
			dateChooserA.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (dateChooserA.getDate() != null)
						dateChooserDa.setMaxSelectableDate(dateChooserA.getDate());
				}
			});
			
			
			
			panel_7.add(dateChooserA);
			
			btnCerca = new JButton("Cerca"){
				@Override
				public String toString(){
					return "BTN_CERCA";
				}
			};
			btnCerca.addActionListener(this);
			panel_7.add(btnCerca);
			
			
			JPanel panel_2 = new JPanel(new BorderLayout());
			add(panel_2, BorderLayout.CENTER);
			
			final JSplitPane splitPane = new JSplitPane();
			splitPane.setBorder(null);
			splitPane.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e){
					splitPane.setDividerLocation(0.5);	
				}
			});
			
			splitPane.setDividerSize(20);
			panel_2.add(splitPane);
			
			JPanel p = new JPanel(new BorderLayout());
			
			JPanel p2 = new JPanel(new GridLayout(0, 1));
			JLabel l = new JLabel(" ");
			JLabel l2 = new JLabel(" ");
			p2.add(l);
			p2.add(l2);
			
			
			JScrollPane scrollPane = new JScrollPane();
			
			p.add(scrollPane, BorderLayout.CENTER);
			p.add(p2, BorderLayout.SOUTH);
			
			splitPane.setLeftComponent(p);
			
			mod = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column){
					return false;					
				}
			};
			
			mod.setColumnIdentifiers(new String[] {"ID VISITA", "AMBULATORIO", "DATA", "ORA"});
			
			table = new JTable(mod);
			
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (table.getSelectedRow() > -1 ) { 
						int id_vis =  (int) table.getModel().getValueAt(table.getSelectedRow(), 0);
						
						ArrayList<String> str = ((PazientiModel) (Controller.getInstance().getModel())).getInfoPrenot(id_vis);
						
						medico.setText("Non ancora assegnato");
						if (str.get(0) != null)
							urgenza.setText(str.get(0));
						else
							urgenza.setText("Non ancora assegnata");
						
						regime.setText(str.get(1));
						prest.setText(str.get(2));
						esitoArea.setText(str.get(3));
						
						btnVisualizzaPdf.setEnabled(true);
						btnInvia.setEnabled(true);
					}
				}
			});
			
			
			scrollPane.setViewportView(table);
			
			JPanel panel_3 = new JPanel();
			splitPane.setRightComponent(panel_3);
			panel_3.setLayout(new GridLayout(2, 1, 0, 0));
			
			JPanel panel_4 = new JPanel();
			panel_3.add(panel_4);
			panel_4.setLayout(new GridLayout(9, 2, 0, 0));
			
			JLabel lblNewLabel = new JLabel("Dettagli e informazioni");
			panel_4.add(lblNewLabel);
			
			JLabel label_6 = new JLabel(" ");
			panel_4.add(label_6);
			
			JLabel label_8 = new JLabel(" ");
			panel_4.add(label_8);
			
			JLabel label = new JLabel(" ");
			panel_4.add(label);
			
			JLabel lblData = new JLabel("Medico");
			panel_4.add(lblData);
			
			medico = new JTextField();
			medico.setColumns(10);
			medico.setEditable(false);
			panel_4.add(medico);
			
			JLabel lblNomeAmbulatorio = new JLabel("Urgenza");
			panel_4.add(lblNomeAmbulatorio);
			
			urgenza = new JTextField();
			urgenza.setColumns(10);
			urgenza.setEditable(false);
			panel_4.add(urgenza);
			
			JLabel lblNomeMedico = new JLabel("Regime");
			panel_4.add(lblNomeMedico);
			
			regime = new JTextField();
			regime.setColumns(10);
			regime.setEditable(false);
			panel_4.add(regime);
			
			JLabel ld = new JLabel("Prestazione");
			panel_4.add(ld);
			
			prest = new JTextField();
			prest.setColumns(10);
			prest.setEditable(false);
			panel_4.add(prest);
			

			
			JPanel panel_5 = new JPanel();
			panel_3.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
			
			JLabel lblEsito = new JLabel("NOTE");
			panel_5.add(lblEsito, BorderLayout.NORTH);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panel_5.add(scrollPane_1, BorderLayout.CENTER);
			
			esitoArea = new JTextPane();
			esitoArea.setEditable(false);
			scrollPane_1.setViewportView(esitoArea);
			
			
			JPanel panel_1 = new JPanel();
			panel_5.add(panel_1, BorderLayout.SOUTH);
			panel_1.setLayout(new GridLayout(0, 3, 0, 0));
			
			JLabel mylbl = new JLabel("");
			panel_1.add(mylbl);
			

			
			btnInvia = new JButton("Invia"){
				@Override
				public String toString(){
					return "BTN_INVIA";
				}
			};
			btnInvia.addActionListener(this);
			btnInvia.setEnabled(false);
			panel_1.add(btnInvia);
			
			btnVisualizzaPdf = new JButton("Visualizza PDF"){
				@Override
				public String toString(){
					return "BTN_VISUALIZZA";
				}
			};
			btnVisualizzaPdf.addActionListener(this);
			btnVisualizzaPdf.setEnabled(false);
			panel_1.add(btnVisualizzaPdf);

		}

		@Override
		public JComponent getFocus() {
			btnCerca.requestFocus();
			return btnCerca;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String act = e.getSource().toString();
			String dataDa, dataA;

			if (act.equals("BTN_CERCA")){
				resetFields();
				if(dateChooserDa.getDate() != null)
					dataDa = (new SimpleDateFormat("dd-MM-yyyy")).format(dateChooserDa.getDate().getTime());
				else 
					dataDa = (new SimpleDateFormat("dd-MM-yyyy")).format(new Date().getTime());
				
				if(dateChooserA.getDate() != null)
					dataA = (new SimpleDateFormat("dd-MM-yyyy")).format(dateChooserA.getDate().getTime()) ;
				else
					dataA = "";
				
				Vector<String> titoli = new Vector<String>( Arrays.asList( new String[] {"ID VISITA", "AMBULATORIO", "DATA", "ORA"} ) );
				Vector<Vector<Object>> data = ((PazientiModel)(Controller.getInstance().getModel())).getFutureVisits(dataDa, dataA);
				
				mod.setDataVector( data , titoli );
				
				if (data.isEmpty())
					JOptionPane.showMessageDialog(this,"Nessuna Prenotazione Trovata ", "Error",JOptionPane.ERROR_MESSAGE);

				
			}
			else if (act.equals("BTN_VISUALIZZA")){
				String urg = "Non ancora assegnata";
				String med = "Non ancora assegnato";
				
				if (!medico.getText().equals(""))
					med = medico.getText();
				if (!urgenza.getText().equals(""))
					urg = urgenza.getText();
				
				visualizzaPdf(table, med, esitoArea.getText(), prest.getText(), urg, regime.getText(), true, "NOTE");
				
				
			}
			else if (act.equals("BTN_INVIA")){	
				String urg = "Non ancora assegnata";
				String med = "Non ancora assegnato";
				
				if (!medico.getText().equals(""))
					med = medico.getText();
				if (!urgenza.getText().equals(""))
					urg = urgenza.getText();
				
				if (sendMail(table, med, esitoArea.getText(), prest.getText(), urg, regime.getText()))
					JOptionPane.showMessageDialog(this,"Invio avvenuto con successo", "Success",JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this,"Non e stato possibile inviare la mail! \n"
							+ "Riprovare più tardi! ", "Error",JOptionPane.ERROR_MESSAGE);
				
				
			}
		}
		private void resetFields(){
			medico.setText("");
			urgenza.setText("");
			regime.setText("");
			prest.setText("");
			esitoArea.setText("");
			btnVisualizzaPdf.setEnabled(false);
			btnInvia.setEnabled(false);
		}
		
		private boolean sendMail(JTable tab, String medico, String note, String prest, String urgenza, String regime){

			final String sender = "uandasl1.0@gmail.com";
			final String pass = "nonperderetempo1";
			String smtp = "smtp.gmail.com";
			String port = "587";
			
			String receiver = ((PazientiModel) (Controller.getInstance().getModel()) ).getUserEmail();

			
			
			String nomeamb = (String) tab.getModel().getValueAt(tab.getSelectedRow(), 1);
			ArrayList<String> param = ((PazientiModel)(Controller.getInstance().getModel())).getIdAzFromName(nomeamb);
			String nomeAsl = param.get(0);
			String spec = param.get(1);
			String data = new SimpleDateFormat("yyyy-MM-dd").format(tab.getModel().getValueAt(tab.getSelectedRow(), 2));
			
			String s = "<br> "	
			+ " <p> "
			+ "		<font size=\"5\" style=\"font-weight: bold\" > "
			+  			nomeAsl
			+ "		</font> "
			+ " </p> "
			+ " "
			+ " <p> "
			+ "		<font size=\"5\" style=\"font-weight: bold\"> "
			+  			nomeamb
			+ "		</font> "
			+ " </p> "
			+ " "
			+ " <br><br> "
			+ " "
			+ " <p> "
			+ "		<font style=\"font-weight: bold\"> "
			+ "			Data visita:  "
			+ "		</font> "
			+  		data
			+ "		<span style=\"float:right;\"> "
			+ "			<font style=\"font-weight: bold\"> "
			+ "				Medico:  "
			+ "			</font> "
			+  			medico
			+ "		</span> "
			+ " </p>"
			+ " <p>"
			+ "		<font style=\"font-weight: bold\"> "
			+ "			Specializzazione:  "
			+ "		</font> "
			+  		spec
			+ " </p> "
			+ " <p>"
			+ "		<font style=\"font-weight: bold\"> "
			+ "			Prestazione:  "
			+ "		</font> "
			+  		prest
			+ " </p> "
			+ " <p>"
			+ "		<font style=\"font-weight: bold\"> "
			+ "			Regime:  "
			+ "		</font> "
			+  		regime
			+ " </p> "
			+ " <p>"
			+ "		<font style=\"font-weight: bold\"> "
			+ "			Urgenza:  "
			+ "		</font> "
			+  		urgenza
			+ " </p> "
			+ " <br><br>"
			+ " <p align=\"center\""
			+ " 	<font style=\"font-weight: bold\" size=\"5\"> "
			+ " 		<u> Note </u> "
			+ " 	</font> "
			+ " </p> "
			+ " <br> "
			+  note
			+ " <br><br><br><br> "
			+ " <i> Se la mail è stata ricevuta erroneamente o non è il destinatario, è pregato di cancellarla immediatamente </i> "
			+ " <br><br> ";
			
			Properties properties = System.getProperties();
			properties.put("mail.smtp.host", smtp);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			
			
			Session session = Session.getDefaultInstance(properties,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(sender, pass);
						}
					  }
			);
			

			try {
				
			    MimeMultipart content = new MimeMultipart("related");


				MimeBodyPart body = new MimeBodyPart();
				body.setText(s, "US-ASCII", "html");
				content.addBodyPart(body);
				
				
				MimeBodyPart imagePart = new MimeBodyPart();
				try {
					imagePart.attachFile("img/uandasl_logo.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				imagePart.setDisposition(MimeBodyPart.ATTACHMENT);
				content.addBodyPart(imagePart);
				
				
				MimeMessage message = new MimeMessage(session);
				
				message.setContent(content);
				message.setSubject("Conferma Prenotazione");
				
				message.setFrom(new InternetAddress(sender));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));


				Transport.send(message);
				

				
				
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;

			
			
			
			
		}

		
		

	}	
	
	@Override
	public JComponent getFocus() {
		ambulatori.comboBoxProv.requestFocus();
		return ambulatori.comboBoxProv;
	}


	

	private void visualizzaPdf(JTable tab, String medico, String note, String prest, 
			String urgenza, String regime, boolean visualizza, String tipo){

		
		String nomeamb = (String) tab.getModel().getValueAt(tab.getSelectedRow(), 1);
		ArrayList<String> param = ((PazientiModel)(Controller.getInstance().getModel())).getIdAzFromName(nomeamb);
		String nomeAsl = param.get(0);
		String spec = param.get(1);
		String data = new SimpleDateFormat("yyyy-MM-dd").format(tab.getModel().getValueAt(tab.getSelectedRow(), 2));


		Document document = new Document();
		try{
						
			PdfWriter writer = PdfWriter.getInstance(document,  new FileOutputStream(
					("/tmp/esito_visita.pdf")));
			document.open();
			
			com.itextpdf.text.Image img = null;
			try {
				img = com.itextpdf.text.Image.getInstance(this.getClass()
						.getResource("/resources/img/uandasl_logo.png"));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			document.add(img);
			
			document.add(new Paragraph("    "));
			
			document.add(new Phrase("    " + nomeAsl,FontFactory.getFont(FontFactory.TIMES_BOLD, 16,Font.BOLD,BaseColor.BLACK)));
			document.add(new Paragraph("    " + nomeamb,FontFactory.getFont(FontFactory.TIMES_BOLD, 16,Font.BOLD,BaseColor.BLACK)));
			
			document.add(new Paragraph("    "));
			document.add(new Paragraph("    "));

			document.add(new Phrase("      Data visita: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK)));
			document.add(new Phrase(data));
			document.add(new Phrase("                                                      "));
			
			document.add(new Phrase("Medico: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK)));
			document.add(new Phrase(medico));
			document.add(new Paragraph("    "));
			
			document.add(new Phrase("      Specializzazione: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK)));
			document.add(new Phrase(spec));
			
			document.add(new Paragraph("    "));
			
			document.add(new Phrase("      Prestazione: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK)));
			document.add(new Phrase(prest));
			
			document.add(new Paragraph("    "));
			
			document.add(new Phrase("      Regime: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK)));
			document.add(new Phrase(regime));
			
			document.add(new Paragraph("    "));
			
			document.add(new Phrase("      Urgenza: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK)));
			document.add(new Phrase(urgenza));
			
			document.add(new Paragraph("    "));
			document.add(new Paragraph("    "));
			document.add(new Paragraph("    "));
			document.add(new Paragraph("    "));

			
			Paragraph esito_string = new Paragraph(tipo, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK));
			esito_string.setAlignment(Element.ALIGN_CENTER);
			document.add(esito_string);
			
			document.add(new Paragraph("    "));
			
			document.add(new Paragraph(note, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11,Font.PLAIN,BaseColor.BLACK)));
			
	        document.close();
			writer.close();

			if (visualizza){
				File sorgente = new File ("/tmp/esito_visita.pdf");
				try {
					Desktop.getDesktop().open(sorgente);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Files.deleteIfExists(FileSystems.getDefault().getPath("", "/tmp/esito_visita.pdf"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		} catch(DocumentException e){
			e.printStackTrace();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
	}

	
	



}
	













