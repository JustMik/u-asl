package it.justmik.dev.model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminModel extends Model{
	
	public AdminModel() throws ClassNotFoundException, SQLException{
		super();
	}
	
	

	public boolean addMedico(ArrayList<String> list){
		///// email, pwd, cf, nome, cognome, via, citta, prov, cap //////
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try (PreparedStatement st = con.prepareStatement("insert into Utenti (ID_email, password, ruolo) "
						+ "values (?, crypt(?, gen_salt('bf', 8)), 'MEDICO');\n"
						+ "insert into Medico (cf_medico, nome, cognome, email, via, citta, provincia, cap)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ? );")){
				
				st.setString(1, list.get(0));
				st.setString(2, list.get(1));
				st.setString(3, list.get(2));
				st.setString(4, list.get(3));
				st.setString(5, list.get(4));
				st.setString(6, list.get(0));
				st.setString(7, list.get(5));
				st.setString(8, list.get(6));
				st.setString(9, list.get(7));
				st.setString(10, list.get(8));
				st.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public boolean addPersonale(ArrayList<String> list){
		///// email, pwd, ruolo //////
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( PreparedStatement st = con.prepareStatement("insert into Utenti (ID_email, password, ruolo) "
						+ "values (?, crypt(?, gen_salt('bf', 8)), ?);\n")){
				
				st.setString(1, list.get(0));
				st.setString(2, list.get(1));
				st.setString(3, list.get(2));
				st.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addAmbulatorio(ArrayList<String> list){
		//// id, spec, nome, via, citta, prov, cap, annoStip, id_azienda /////
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( PreparedStatement st = con.prepareStatement("insert into Ambulatorio "
					+ "(id_ambulatorio, specializzazione, nome, via, citta, provincia, cap, anno_stip, id_azienda) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?);\n")){
				st.setString(1, list.get(0));
				st.setString(2, list.get(1));
				st.setString(3, list.get(2));
				st.setString(4, list.get(3));
				st.setString(5, list.get(4));
				st.setString(6, list.get(5));
				st.setString(7, list.get(6));
				st.setInt(8, Integer.parseInt(list.get(7)));
				st.setString(9, list.get(8));
				st.executeUpdate();

				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	
	public boolean addAsl(ArrayList<String> list){
		//// id,  nome, via, citta, prov, cap, num_dip /////
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( PreparedStatement st = con.prepareStatement("insert into azienda_sanitaria "
					+ "(id_azienda, nome, via, citta, provincia, cap, num_dip) "
					+ "values (?, ?, ?, ?, ?, ?, ?);\n")){
				st.setString(1, list.get(0));
				st.setString(2, list.get(1));
				st.setString(3, list.get(2));
				st.setString(4, list.get(3));
				st.setString(5, list.get(4));
				st.setString(6, list.get(5));
				st.setInt(7, Integer.parseInt(list.get(6)));
				st.executeUpdate();
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
		
	}
	
	public boolean addPrest(ArrayList<String> list){
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( PreparedStatement st = con.prepareStatement("insert into Prestazioni "
					+ "(id_prestazione, NomePrestazione, descrizione) "
					+ "values (?, ?, ?);\n")){
				st.setString(1, list.get(0));
				st.setString(2, list.get(1));
				st.setString(3, list.get(2));
				st.executeUpdate();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public ArrayList<String> getAslList(String prov){
		
        ArrayList<String> temp= new ArrayList<>();

		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery("SELECT id_azienda FROM Azienda_sanitaria where "
						+ "provincia = '"+prov+"'");
					
				while (rs.next())
					temp.add(rs.getString("id_azienda"));
		
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 

		return temp;
		
	}
	

}

















