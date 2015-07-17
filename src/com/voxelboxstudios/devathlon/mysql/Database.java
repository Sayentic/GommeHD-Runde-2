package com.voxelboxstudios.devathlon.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	/** Credentials **/
	
	private String host, data, user, pass;
	
	
	/** Port **/
	
	private int port;
	
	
	/** Automatic reconnect **/
	
	private boolean autoReconnect;
	
	
	/** Connection **/
	
	private Connection connect;
	
	
	/** Constructor **/
	
	public Database(String address, int port, String database, String username, String password, boolean autoReconnect) {
		/** Credentials **/
		
		this.host = address;
		this.port = port;
		this.data = database;
		this.user = username;
		this.pass = password;
		
		
		/** Automatic reconnect **/
		
		this.autoReconnect = autoReconnect;
	}
	
	
	/** Open connection **/
	
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		/** Load JDBC Drivers **/
		
		Class.forName("com.mysql.jdbc.Driver");
		
		
		/** Open connection **/
		
		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + data + "?user=" + user + "&password=" + pass + "&autoReconnect=" + autoReconnect);
		
		
		/** Set connection **/
		
		this.connect = con;
		
		
		/** Return connection **/
		
		return connect;
	}
	
	
	/** Get connection **/
	
	public Connection getConnection() {
		return this.connect;
	}
	
	
	/** Has connection **/
	
	public boolean hasConnection() {
		try {
			return this.connect != null || this.connect.isValid(1);
		} catch(SQLException e) {
			return false;
		}
	}
	
	
	/** Reconnect **/
	
	public void reconnect() throws ClassNotFoundException, SQLException {
		if(connect == null || connect.isClosed()) {
			openConnection();
		} else {
			return;
		}
	}
	
	
	/** Update query **/
	
	public void queryUpdate(String query) throws SQLException {
		/** Prepared statement **/
		
	    PreparedStatement st = null;
	    
	    
	    /** Prepare statement **/
	    
	    st = connect.prepareStatement(query);
	    
	    
	    /** Execute update **/
	    
	    st.executeUpdate();
	    
	    
	    /** Close resources **/
	    
	    closeRessources(null, st);
	}
	
	
	/** Get query **/
	
	public ResultSet getQuery(String query) throws SQLException {
		/** Result **/
		
		ResultSet rs = null;
		
		
		/** Prepared statement **/
		
		PreparedStatement stmt = connect.prepareStatement(query);
		
		
		/** Execute query **/
		
		rs = stmt.executeQuery(query);
		
		
		/** Return result **/
		
		return rs;
	}
	
	
	/** Close resources **/
	
	public void closeRessources(ResultSet rs, PreparedStatement st) throws SQLException {
		if(rs != null) {
			rs.close();
		}
		
		if(st != null) {
			st.close();
		}
	}
	
	
	/** Close connection **/
	
	public void closeConnection() throws SQLException {
		this.connect.close();
		this.connect = null;
	}
}