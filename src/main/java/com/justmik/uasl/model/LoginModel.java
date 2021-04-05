package it.justmik.dev.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;


public class LoginModel extends Model{
	
	public LoginModel() throws ClassNotFoundException, SQLException{
		super();
	}
	
	public String[] login(String username, String pwd) throws SQLException{
		String user="", ruolo="", psw, idUser="";
		
		System.out.println(username);
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			if (con == null){
				return null;
			}
			try(Statement st = con.createStatement()){
				ResultSet res = st.executeQuery("SELECT id_email, password, ruolo FROM utenti "
						+ "WHERE id_email='"+username+"' AND password = crypt('"+pwd+"', password) ");
				 
				while (res.next()) {	
					user = res.getString("id_email");
					ruolo = res.getString("ruolo");
					psw = res.getString("password");
				}
				
				res = st.executeQuery("Select cf_paziente from pazienti where email = '"+username+"' ");
				if (res.next()){
					
					idUser = res.getString("cf_paziente");
				}
			
				
		 	} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

			
		return new String[] {ruolo, user, idUser};	
	 }
	

		
	
	
	public int register( ArrayList<String> list){
		
		PreparedStatement st = null;
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try {
				st = con.prepareStatement("Select ID_azienda from azienda_sanitaria "
														+ "where nome = '"+list.get(9)+"'");
				
				ResultSet rs = st.executeQuery();
				
				if (rs.next()){
					
					String id = rs.getString("id_azienda");
					
					String reg = "insert into Utenti (ID_email, password, ruolo) "
							+ "values (?, crypt(?, gen_salt('bf', 8)), 'PAZIENTE');\n "
							+ "insert into Pazienti (cf_paziente, nome, cognome, email, via, citta, provincia, cap, ID_Azienda) "
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ? );";
					
					st = con.prepareStatement(reg);
					
					st.setString(1, list.get(0));
					st.setString(2, list.get(1));
					st.setString(3, list.get(2).toUpperCase());
					st.setString(4, list.get(3));
					st.setString(5, list.get(4));
					st.setString(6, list.get(0));
					st.setString(7, list.get(5));
					st.setString(8, list.get(6));
					st.setString(9, list.get(7));
					st.setString(10, list.get(8));
					st.setString(11, id);
					int result = st.executeUpdate();
					System.out.println(result);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;

	}
	
	
	
	public DefaultComboBoxModel getAmbulatorioFromProv(String prov) {
		
		ArrayList<String> temp= new ArrayList<>();

		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd())){
			try (Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery("SELECT nome FROM Azienda_sanitaria where "
						+ "provincia = '"+prov+"'");
				
				temp.add("--- Tutte ---");
					
				while (rs.next())
					temp.add(rs.getString("nome"));
				
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		
		return new DefaultComboBoxModel(temp.toArray());
	}
	
	
	
	
}
