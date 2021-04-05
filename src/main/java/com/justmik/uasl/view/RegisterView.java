package com.justmik.uasl.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.justmik.uasl.controller.Controller;
import com.justmik.uasl.model.LoginModel;

public class RegisterView extends FocusablePanel implements ActionListener{
	
	private JTextField cf, nome, cognome, email, via, cap, citta;
	private JComboBox asl, prov;
	private JPasswordField pwd;


	public RegisterView(){

		initialize();
	}
	
	private void initialize() {
		
		setLayout(new BorderLayout());
		
		JPanel panelCenter = new JPanel( new FlowLayout(FlowLayout.CENTER));
		JPanel panelContent=new JPanel(new GridLayout(0,2));
		JPanel panelTitolo=new JPanel( new FlowLayout(FlowLayout.CENTER));
		
		
		JButton EXE_REGISTER=new JButton("REGISTER"){
			@Override
			public String toString(){
				return "EXE_REGISTER";
			}
		};
		
		
		JLabel label0 = new JLabel("CF:");
		JLabel label1 = new JLabel("Nome:");	
		JLabel label2 = new JLabel("Cognome:");
		JLabel label3 = new JLabel("E-mail:");
		JLabel label4 = new JLabel("Password:");	
		JLabel label5 = new JLabel("Via:");	
		JLabel label6 = new JLabel("Città:");
		JLabel label7 = new JLabel("Provincia:");
		JLabel label8 = new JLabel("CAP:");
		JLabel titolo = new JLabel("REGISTRA NUOVO PAZIENTE");
		titolo.setFont(new Font("", Font.BOLD, 16));
		
		JLabel label9 = new JLabel("");
		label9.setVisible(false);
		JLabel label10 = new JLabel("");
		label10.setVisible(false);
		JLabel label11 = new JLabel("");
		label11.setVisible(false);
		JLabel label12 = new JLabel("");
		label12.setVisible(false);
		JLabel label13 = new JLabel("");
		label13.setVisible(false);
		JLabel label14 = new JLabel("Asl:");
		
		panelTitolo.add(titolo);
		
		cf = new JTextField(15);
		nome = new JTextField(15);
		cognome = new JTextField(15);
		email = new JTextField(15);
		pwd = new JPasswordField(15);
		via = new JTextField(15);
		cap = new JTextField(15);
		citta = new JTextField(15);
		prov = new JComboBox(new DefaultComboBoxModel( new String[] {"--- Seleziona ---", 
				"BL", "PD", "RO", "TV", "VE", "VR", "VI"}  ) );
		
		prov.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (prov.getSelectedIndex()!=0)
					asl.setModel(getAsl());
				else
					asl.removeAllItems();				
			}
		});
		
		
		asl = new JComboBox();
		
		
		panelContent.add(label10);
		panelContent.add(label11);
		panelContent.add(label0);
		panelContent.add(cf);
		panelContent.add(label1);
		panelContent.add(nome);
		panelContent.add(label2);
		panelContent.add(cognome);
		panelContent.add(label3);
		panelContent.add(email);
		panelContent.add(label4);
		panelContent.add(pwd);
		panelContent.add(label5);
		panelContent.add(via);
		panelContent.add(label6);
		panelContent.add(citta);
		panelContent.add(label7);
		panelContent.add(prov);
		panelContent.add(label8);
		panelContent.add(cap);
		panelContent.add(label14);
		panelContent.add(asl);
		panelContent.add(label9);
		panelContent.add(label12);
		panelContent.add(label13);
		panelContent.add(EXE_REGISTER);

		
		
		panelCenter.add(panelContent);
		
		add(panelTitolo, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		
		Controller.getView().getRootPane().setDefaultButton(EXE_REGISTER);
		
		EXE_REGISTER.addActionListener(this);
		


	}

	private DefaultComboBoxModel getAsl() {
		return ( ( (LoginModel) Controller.getInstance().getModel() ).getAmbulatorioFromProv((String)prov.getSelectedItem()) );
	}

	public ArrayList<String> getParam(){
		String[] strs = {email.getText(), String.valueOf(pwd.getPassword()) , cf.getText(), nome.getText(),
				cognome.getText(), via.getText(), citta.getText(), (String)prov.getSelectedItem(), cap.getText(), (String) asl.getSelectedItem()};
		
		return new ArrayList<>(Arrays.asList(strs));
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( (!cf.getText().equals("")) && (!nome.getText().equals("")) && (!cognome.getText().equals("")) &&
				(!email.getText().equals("")) && (!String.valueOf(pwd.getPassword()).equals("")) &&
				(!via.getText().equals("")) && (!citta.getText().equals("")) && (prov.getSelectedIndex() != 0) &&
				(!cap.getText().equals("")) && (asl.getSelectedIndex() != 0))
			
			if (checkCF(cf.getText()) && checkEmail(email.getText()) && checkCap(cap.getText()))
				Controller.getInstance().displayContent(e.getSource().toString(), "PAZIENTE");
			else
				JOptionPane.showMessageDialog(this,"Formato CF, E-mail o CAP non validi!", "Error",JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(this,"Riempire tutti i campi!", "Error",JOptionPane.ERROR_MESSAGE);
	
	}

	@Override
	public JComponent getFocus() {
		cf.requestFocus();
		return cf;
	} 
	
	private boolean checkCF (String cf){
		String regex = "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cf);
		if (matcher.matches())
			return true;
		else
			return false;
	}
	
	private boolean checkEmail (String email){
		String regex = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches())
			return true;
		else
			return false;
	}
	
	private boolean checkCap (String cap){
		String regex = "[0-9]{5}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cap);
		if (matcher.matches())
			return true;
		else
			return false;
	}
	




}
