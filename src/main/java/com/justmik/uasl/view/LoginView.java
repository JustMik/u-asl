package com.justmik.uasl.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.justmik.uasl.controller.Controller;
import com.justmik.uasl.model.LoginModel;


public class LoginView extends FocusablePanel implements ActionListener{
	
	private JTextField textEmail, textPwd;
	private String email_user, id_user;
	
	public LoginView(){
		initialize();
	}
	
	private void initialize() {
				
		JLabel label1 = new JLabel();
		label1.setText("E-mail:");
		textEmail = new JTextField(15);
		textEmail.setFocusable(true);
		
		
		
		
		JLabel label2 = new JLabel();
		label2.setText("Password:");
		textPwd = new JPasswordField(15);
 
		JButton SUBMIT=new JButton("LOGIN");
		JButton REGISTER=new JButton("REGISTER"){
			@Override
			public String toString(){
				return "REGISTER";
			}
		};
		
		JPanel panel=new JPanel(new GridLayout(3,1));
		panel.add(label1);
		panel.add(textEmail);
		panel.add(label2);
		panel.add(textPwd);
		panel.add(SUBMIT);
		panel.add(REGISTER);
		add(panel,BorderLayout.CENTER);
		
		Controller.getView().getRootPane().setDefaultButton(SUBMIT);
		
		SUBMIT.addActionListener(this);
		REGISTER.addActionListener(this);
		
		
	}
	
	public String getEmailUser(){
		return email_user;
	}
	
	public String getIdUser(){
		return id_user;
	}
	
	@Override
	public String toString(){
		return "LOGIN";
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		String act = e.getActionCommand();
		String[] str=null;
		String ruolo="";
		
		if (e.getSource().toString().equals("REGISTER")){
			if (!Controller.getInstance().displayContent(act, ruolo)){
				JOptionPane.showMessageDialog(this,"Registrazione fallita \n\t\t\t\t\t\t\t\t\tRiprova! ", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if ( !textEmail.getText().equals("") && !textPwd.getText().equals("") ) {
			try {
				str = ((LoginModel) Controller.getInstance().getModel()).login(textEmail.getText(), textPwd.getText());
				ruolo = str[0];
				email_user = str[1];
				id_user = str[2];
			} catch (SQLException e1) {}
		
			if (str == null)
				JOptionPane.showMessageDialog(this,"Errore Di Connessione!", "Error",JOptionPane.ERROR_MESSAGE);
			else if (!Controller.getInstance().displayContent(act, ruolo)){
				JOptionPane.showMessageDialog(this,"Autenticazione Fallita! \n\t\t\t\t\t\t\t\t\tRiprova! ", "Error",JOptionPane.ERROR_MESSAGE);
			}
		
		}
		else if (textEmail.getText().equals("") || textPwd.getText().equals("") )
			JOptionPane.showMessageDialog(this,"Inserire  Tutti I Dati Di Autenticazione", "Error",JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public JComponent getFocus() {
		textEmail.requestFocus();
		return textEmail;
	}
	

	


}
