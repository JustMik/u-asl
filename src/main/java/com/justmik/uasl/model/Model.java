package com.justmik.uasl.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public abstract class Model {
	
	
	private String host = "jdbc:postgresql://localhost:5432/asldb";
	private String user = "asldb";
	private String pwd = "asldb";
	
	public Model () throws ClassNotFoundException{
		Class.forName("org.postgresql.Driver");
	}
	
	
	public String getHost(){
		return host;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getPwd(){
		return pwd;
	}

	
	public static Vector<Vector<Object>> modelTable(ResultSet res){

		Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
		
		ResultSetMetaData metaData;
		try {
			metaData = res.getMetaData();
			int columnCount = metaData.getColumnCount();

		    while (res.next()) {
		        Vector<Object> vector = new Vector<Object>();
		        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		            vector.add(res.getObject(columnIndex));
		        }
		        data.add(vector);
		    }
	    
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	
	}
}
