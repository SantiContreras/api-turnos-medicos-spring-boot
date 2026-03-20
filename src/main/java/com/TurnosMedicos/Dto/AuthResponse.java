package com.TurnosMedicos.Dto;

public class AuthResponse {

	
private	String username;
private	String token;
private	String role;
private	String type="Bearer";
public AuthResponse( String token,String username, String role) {
	this.token = token;
	this.username = username;
	this.role = role;
	
}
public String getUsername() {
	return username;
}
public String getToken() {
	return token;
}
public String getRole() {
	return role;
}
public String getType() {
	return type;
}





	
}
