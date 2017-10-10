package com.journaldev.spring.controller;

public class EmpRestURIConstants {

	public static final String DUMMY_EMP = "/rest/emp/dummy";
	public static final String GET_EMP = "/rest/emp/{id}";
	public static final String GET_ALL_EMP = "/rest/emps";
	public static final String CREATE_EMP = "/rest/emp/create";
	public static final String DELETE_EMP = "/rest/emp/delete/{id}";
	
	
	public static final String GET_ALL_EMP2 = "/rest/emps2";
	
	
	//prima chimata
	
	//seconda chiamata
//	public static final String GET_URL_PNG = "/rest/grass/img/{status}";
	
	
	
	//prima chiamata con cordinate e tempo
	public static final String GET_COORDINATE_TEMPO = "/rest/grass/status/{starTime}/{endTime}/{lon}/{lat}";
	
	//seconda chiamata 
	public static final String GET_SAVE_AND_CUT_IMG = "/rest/grass/status/{ambiente}/{sceneId}/{south_lat}/{north_lat}/{east_lon}/{west_lon}/{south_lat_small}/{north_lat_small}/{east_lon_small}/{west_lon_small}";

	
	//TERZA CHIAMATA non usata
	public static final String GET_URL_PNG = "/rest/grass/img2/{resource_id}/{ambiente}";
	
	
	
	public static final String GET_SAVE_IMG = "/rest/grass/status/{ambiente}/{sceneId}/{south_lat}/{north_lat}/{east_lon}/{west_lon}";
	
	
	
	

}
