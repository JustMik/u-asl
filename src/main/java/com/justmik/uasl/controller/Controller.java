package com.justmik.uasl.controller;

import java.util.ArrayList;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.justmik.uasl.model.Model;
import com.justmik.uasl.view.MedicoView;
import com.justmik.uasl.view.PazientiView;
import com.justmik.uasl.view.PersonaleAmbulatorioView;
import com.justmik.uasl.model.AdminModel;
import com.justmik.uasl.model.LoginModel;
import com.justmik.uasl.model.PazientiModel;
import com.justmik.uasl.view.AdminView;
import com.justmik.uasl.view.LoginView;
import com.justmik.uasl.view.PersonaleAslView;
import com.justmik.uasl.view.RegisterView;
import com.justmik.uasl.view.View;

public class Controller 
{
	static Controller controller;
	private static View view;
	private Model model;

	
	public Controller() {
		view = new View();
		try {
			model = new LoginModel();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(view,"Incorrect email or password: "+ e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
		view.setPane("LOGIN",new LoginView());		
		controller = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */

	
	
	public static Controller getInstance(){
		return controller;
	}
	
	public Model getModel(){
		return model;
	}
	
	public static View getView(){
		return view;
	}
	
	

	
	public boolean displayContent(String act, String ruolo){
		
	////// GESTIONE LOGIN VIEW //////	
		if (act.equals("LOGIN")){
			if (ruolo.equals("PAZIENTE")){
				try {
					
					model = new PazientiModel( ( (LoginView) (getView().getContentPane())).getEmailUser(),
							( (LoginView) (getView().getContentPane())).getIdUser());
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(view,"Incorrect login or password: "+ e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				view.setPane("PAZIENTI" ,new PazientiView());
				return true;
			}
			else if(ruolo.equals("MEDICO")){
				System.out.println(ruolo);
				view.setPane("MEDICO" ,new MedicoView());
				return true;
				
			}
			else if(ruolo.equals("PERSONALE AMBULATORIO")){
				System.out.println(ruolo);
				view.setPane("MEDICO" ,new PersonaleAmbulatorioView());
				return true;
				
			}
			else if(ruolo.equals("PERSONALE ASL")){
				System.out.println(ruolo);
				view.setPane("MEDICO" ,new PersonaleAslView());
				return true;
	
			}
			else if(ruolo.equals("ADMIN")){
				System.out.println(ruolo);
				try {
					model = new AdminModel();
				} catch (ClassNotFoundException | SQLException e) {
					JOptionPane.showMessageDialog(view,"Qualcosa e andato storto. Riprova!! ", "Error",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				view.setPane("ADMIN", new AdminView());
				return true;
			}
			return false;
		}
		else if (act.equals("REGISTER")){
			view.setPane("REGISTER", new RegisterView());
			return true;

		}
	////// GESTIONE REGISTER VIEW ////////
		else if (act.equals("EXE_REGISTER")){
			ArrayList<String> list = ((RegisterView)(Controller.view.getContentPane())).getParam();
			
			int caso = ((LoginModel)model).register(list);
			if ( caso == 1){
				JOptionPane.showMessageDialog(view,"Registrazione Avvenuta Con Successo", "Success",JOptionPane.INFORMATION_MESSAGE);
				view.setPane("LOGIN", new LoginView());
			}
			else if (caso == 0)
				JOptionPane.showMessageDialog(view,"Connection Error", "Error",JOptionPane.ERROR_MESSAGE);
			else 
				JOptionPane.showMessageDialog(view,"CF o E-mail già esistenti!! \nInserisci i dati corretti!!", "Error",JOptionPane.ERROR_MESSAGE);
				
			
		}
	/////// GESTIONE ADMIN VIEW /////////
		else if (act.equals("ADD_UTENTI")){
			ArrayList<String> list = ((AdminView)(view.getContentPane())).getParamUtenti();

			switch (ruolo) {
			case "MEDICO":
				if (((AdminModel) model).addMedico(list)){
					JOptionPane.showMessageDialog(view,"Inserimento avvenuto con Successo", "",JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
				else{
					JOptionPane.showMessageDialog(view,"Qualcosa e andato storto. Riprova!! ", "Error",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
			case "PERSONALE ASL": 
			case "PERSONALE AMBULATORIO":
			case "ADMIN":
				ArrayList<String> l = new ArrayList<>();
				l.add(list.get(0));
				l.add(list.get(1));
				l.add(ruolo);
				if (((AdminModel) model).addPersonale(l)){
					JOptionPane.showMessageDialog(view,"Inserimento avvenuto con Successo", "",JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
				else {
					JOptionPane.showMessageDialog(view,"Qualcosa e andato storto. Riprova!! ", "Error",JOptionPane.ERROR_MESSAGE);
					return false;
				}
			default:
				JOptionPane.showMessageDialog(view,"Esegui una scelta!!! ", "Error",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		}
		else if (act.equals("ADD_AMBUL")){
			ArrayList<String> list = ((AdminView)(view.getContentPane())).getParamAmbul();
			if (((AdminModel) model).addAmbulatorio(list)){
				JOptionPane.showMessageDialog(view,"Inserimento avvenuto con Successo", "",JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			else {
				JOptionPane.showMessageDialog(view,"Qualcosa e andato storto. Riprova!! ", "Error",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		}
		else if (act.equals("ADD_ASL")){
			ArrayList<String> list = ((AdminView)(view.getContentPane())).getParamAsl();
			if (((AdminModel) model).addAsl(list)){
				JOptionPane.showMessageDialog(view,"Inserimento avvenuto con Successo", "",JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			else {
				JOptionPane.showMessageDialog(view,"Qualcosa e andato storto. Riprova!! ", "Error",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		}
		else if (act.equals("ADD_PREST")){
			ArrayList<String> list = ((AdminView)(view.getContentPane())).getParamPrest();
			if (((AdminModel) model).addPrest(list)){
				JOptionPane.showMessageDialog(view,"Inserimento avvenuto con Successo", "",JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			else {
				JOptionPane.showMessageDialog(view,"Qualcosa e andato storto. Riprova!! ", "Error",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		}
/////////////////////////////////// RETURN ///////////////////////////////////////////////////////////
		else if (act.equals("RETURN")){
			
			try {
				model = new LoginModel();
				
			} catch (ClassNotFoundException | SQLException e1) {
				JOptionPane.showMessageDialog(view,"Qualcosa e andato storto! "+ e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			view.setPane("LOGIN", new LoginView());
			
			return true;
		}
		
/////////////////////////////////// PAZIENTI ///////////////////////////////////////////////////////////

			
		else if (ruolo.equals("PAZIENTE")){
			

	/////////// VISUALIZZA AMBULATORI ////////////
			 if(act.equals("PRENOTA_VISITA")){
				
			}
		}
		
		return false;
	}



	
	


}








