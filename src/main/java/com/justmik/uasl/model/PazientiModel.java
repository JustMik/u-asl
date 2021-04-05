package com.justmik.uasl.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

public class PazientiModel extends Model{
	private String email_user, id_user;
	
	public String getUserEmail(){
		return email_user;
	}
	
	public PazientiModel(String email_user, String id_user) throws ClassNotFoundException, SQLException{
		super();
		this.email_user = email_user;
		this.id_user = id_user;
	}
	
	


	public ArrayList<String> getAmbulatorioFromProv(String prov) {
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
		
		return temp;
	}



	public Vector<Vector<Object>> getDataAmbulatori(ArrayList<String> list) {
		// list.get(0) = prov ---- list.get(1) = Azienda_Sanitaria.nome ///
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String query = "select AMB.ID_Ambulatorio, AMB.nome from Ambulatorio AS AMB ";
		
		if ( ! list.get(0).equals("--- Tutte ---")){
			if ( ! list.get(1).equals("--- Tutte ---")){
				query += "JOIN Azienda_Sanitaria as ASL "
						+ "ON AMB.ID_Azienda = ASL.ID_Azienda "
						+ "where AMB.Provincia = '"+list.get(0)+"' AND ASL.nome = '"+list.get(1)+"' ";
			}
			else{
				query += "where AMB.Provincia = '"+list.get(0)+"' ";
			}
		}
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(query);
				
			    while (rs.next()) {
			        Vector<Object> vector = new Vector<Object>();
			        for (int columnIndex = 1; columnIndex <= 2; columnIndex++) {
			            vector.add(rs.getObject(columnIndex));
			        }
			        data.add(vector);
			    }
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return data;
	}
	
	public Vector<Vector<Object>> getPrestFromAmb(String id_amb) {
		// list.get(0) = prov ---- list.get(1) = Azienda_Sanitaria.nome ///
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String query = "select PR.nomePrestazione, PR.descrizione from Prestazioni AS PR "
				+ "JOIN erogazioneprestazioni AS EP "
				+ "ON PR.id_prestazione = EP.id_prestazione "
				+ "JOIN ambulatorio AS AMB "
				+ "ON AMB.id_ambulatorio = EP.id_ambulatorio "
				+ "where AMB.id_ambulatorio = '"+id_amb+"'";

		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(query);
				
			    while (rs.next()) {
			        Vector<Object> vector = new Vector<Object>();
			        for (int columnIndex = 1; columnIndex <= 2; columnIndex++) {
			            vector.add(rs.getObject(columnIndex));
			        }
			        data.add(vector);
			    }
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return data;
	}
	
	
	public String[] getInfoAmb(String id_amb) {
		// specializzazione [1], via [3], citta [4], cap[6], anno_stip [7]
		String [] str= null;
		
		String query = "SELECT specializzazione, via, citta, cap, anno_stip FROM Ambulatorio "
				+ "WHERE id_ambulatorio = '"+id_amb+"' ";

		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(query);
				
				rs.next();
				str = new String[]{rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5)};
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return str;
	}




	public DefaultComboBoxModel<String> getAmbFromUser() {
		ArrayList<String> ar = new ArrayList<>();
		
		//String queryProv = "SELECT provincia FROM Pazienti "
		//		+ "WHERE email = '"+email_user+"' ";
		
		String queryAmb = "SELECT AMB.nome FROM Ambulatorio AS AMB "
				+ "JOIN Pazienti As PAZ "
				+ "ON AMB.id_azienda = PAZ.id_azienda "
				+ "WHERE email = '"+email_user+"' ";
				
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryAmb);
				
				//if(rs.next()){
				//	String prov = rs.getString("provincia");
				//	System.out.println(prov);
					

					
					//rs = st.executeQuery(queryAmb);
					
				ar.add("--- Seleziona ---");
				while (rs.next()){
					ar.add(rs.getString("nome"));
				}	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		return new DefaultComboBoxModel( ar.toArray());
	}
	
	public DefaultComboBoxModel<String> getPrestFromAmbName(String amb) {
		ArrayList<String> ar = new ArrayList<>();
		
		String queryPrest = "SELECT PR.nomeprestazione FROM Prestazioni as PR "
				+ "JOIN erogazioneprestazioni as EP "
				+ "ON PR.id_prestazione = EP.id_prestazione "
				+ "JOIN ambulatorio AS AMB "
				+ "ON AMB.id_ambulatorio = EP.id_ambulatorio "
				+ "WHERE AMB.nome = '"+amb+"' ";
	
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryPrest);
				
				ar.add("--- Seleziona ---");
				while (rs.next()){
					ar.add(rs.getString("nomeprestazione"));
				}	
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return new DefaultComboBoxModel( ar.toArray());
	}




	public ArrayList<String> getOraFromDate(String data, String amb) {
		ArrayList<String> ora = new ArrayList<>();
		String id_amb="";
		
		String queryIdAmb = "select id_ambulatorio from ambulatorio where nome = '"+amb+"'";
				

	
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryIdAmb);
				
				if(rs.next())
					id_amb = rs.getString("id_ambulatorio");
				
				String queryOra = "SELECT DISTINCT ora from visite as V "
						+ "JOIN ambulatorio as AMB "
						+ "ON AMB.id_ambulatorio = V.id_ambulatorio "
						+ "WHERE V.data_visita = TO_DATE('"+data+"', 'DD/MM/YYYY')"
						+ "AND AMB.id_ambulatorio = '"+id_amb+"' ";
				
				
				rs = st.executeQuery(queryOra);
				
				while (rs.next()){
					ora.add(rs.getString("ora"));
				}	
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
		
		return ora;
	}




	public boolean setPrenotazione(Date data, String ora, String nomeAmb, String note, String prest, String regime) {
		String queryIdAmb = "Select id_ambulatorio from ambulatorio where nome = '"+nomeAmb+"' ";
		String queryIdPrest = "Select id_prestazione from prestazioni where nomeprestazione = '"+prest+"' ";
		String idAmb;
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd());){
			try ( Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryIdAmb);
				
				if (rs.next()){
					idAmb = rs.getString("id_ambulatorio");
					
					rs = st.executeQuery(queryIdPrest);
					
					if (rs.next()){
						String id_prest = rs.getString("id_prestazione");
						
						PreparedStatement st2 = con.prepareStatement("INSERT INTO visite (data_visita , ora, avvenutavisita, "
								+ "regime, id_ambulatorio, id_paziente, note, id_prestazione) "
								+ "values( ?, ?, 'NO', ? , ?, ?, ?, ? ) ");
						
						java.sql.Date d = new java.sql.Date(data.getTime());

						st2.setDate(1, d);
						st2.setString(2, ora);
						st2.setString(3, regime);
						st2.setString(4, idAmb);
						st2.setString(5, id_user);
						st2.setString(6, note);
						st2.setString(7, id_prest);
						st2.executeUpdate();
						
					}	
					
				}	
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}




	public Vector<Vector<Object>> getPastVisits(String dataDa, String dataA) {
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String queryPastVis = "Select V.id_visita, AMB.nome, V.data_visita, V.ora "
				+ "FROM ambulatorio AS AMB "
				+ "JOIN Visite AS V "
				+ "ON V.id_ambulatorio = AMB.id_ambulatorio "
				+ "WHERE V.id_paziente = '"+id_user+"' "
				+ "AND V.avvenutavisita = 'SI' ";
			if(!dataDa.equals(""))
				queryPastVis += "AND V.data_visita >= TO_DATE('"+dataDa+"', 'DD/MM/YYYY')";

			queryPastVis += "AND V.data_visita <= TO_DATE('"+dataA+"', 'DD/MM/YYYY')";

		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd())){
			try (Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryPastVis);
				
					
				 while (rs.next()) {
			        Vector<Object> vector = new Vector<Object>();
			        for (int columnIndex = 1; columnIndex <= 4; columnIndex++) {
			            vector.add(rs.getObject(columnIndex));
			        }
			        data.add(vector);
			    }
				
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		return data;
	}


	

	public ArrayList<String> getEsitoVisita(int id_vis) {
		Integer miidvis = id_vis;
		miidvis.toString();
		ArrayList<String> data = new ArrayList<>();
		
		String queryEsito = "SELECT V.regime, P.nomeprestazione, E.esito, M.cognome, V.urgenza "
						  + "FROM visite AS V "
						  + "JOIN medico AS M "
						  + "ON  V.id_medico = M.cf_medico "
						  + "JOIN prestazioni AS P "
						  + "ON V.id_prestazione = P.id_prestazione "
						  + "JOIN esito AS E "
						  + "ON V.id_esito = E.id_esito "
						  + "WHERE V.id_visita = '"+id_vis+"' ";
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd())){
			try (Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryEsito);		

				while(rs.next()){
					data.add(rs.getString(1));
					data.add(rs.getString(2));
					data.add(rs.getString(3));
					data.add(rs.getString(4));
					data.add(rs.getString(5));					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public Vector<Vector<Object>> getFutureVisits(String dataDa, String dataA) {
		
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String queryFutVis = "Select V.id_visita, AMB.nome, V.data_visita, V.ora "
				+ "FROM ambulatorio AS AMB "
				+ "JOIN Visite AS V "
				+ "ON V.id_ambulatorio = AMB.id_ambulatorio "
				+ "WHERE V.id_paziente = '"+id_user+"' "
				+ "AND V.avvenutavisita = 'NO' "
				+ "AND V.data_visita >= TO_DATE('"+dataDa+"', 'DD/MM/YYYY')";

			if(!dataA.equals(""))
				queryFutVis += "AND V.data_visita <= TO_DATE('"+dataA+"', 'DD/MM/YYYY')" ;
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd())){
			try (Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryFutVis);
				
					
				 while (rs.next()) {
			        Vector<Object> vector = new Vector<Object>();
			        for (int columnIndex = 1; columnIndex <= 4; columnIndex++) {
			            vector.add(rs.getObject(columnIndex));
			        }
			        data.add(vector);
			    }
				
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		return data;
	}
	
	
	public ArrayList<String> getInfoPrenot(int id_vis) {
		Integer miidvis = id_vis;
		miidvis.toString();
		ArrayList<String> data = new ArrayList<>();
		
		String queryEsito = "SELECT V.urgenza, V.regime, P.nomeprestazione, V.note "
						  + "FROM visite AS V "
						  + "JOIN prestazioni AS P "
						  + "ON V.id_prestazione = P.id_prestazione "
						  + "WHERE V.id_visita = '"+id_vis+"' ";
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd())){
			try (Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryEsito);		

				while(rs.next()){
					data.add(rs.getString(1));
					data.add(rs.getString(2));
					data.add(rs.getString(3));
					data.add(rs.getString(4));				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public ArrayList<String> getIdAzFromName(String nomeAmb) {
		ArrayList<String> val = new ArrayList<>();
		String queryId = "SELECT AZ.nome, A.specializzazione "
						  + "FROM ambulatorio AS A "
						  + "JOIN azienda_sanitaria AS AZ "
						  + "ON AZ.id_azienda = A.id_azienda "
						  + "WHERE A.nome = '"+nomeAmb+"' ";
		
		try(Connection con = DriverManager.getConnection(getHost() , getUser(), getPwd())){
			try (Statement st = con.createStatement()){
				ResultSet rs = st.executeQuery(queryId);		
				
				if(rs.next()) {
					val.add(rs.getString(1));
					val.add(rs.getString(2));
				}

			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	
	
}
