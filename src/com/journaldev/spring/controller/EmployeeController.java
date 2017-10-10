package com.journaldev.spring.controller;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journaldev.spring.model.Employee;



/**
 * Handles requests for the Employee service.
 */
@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	final static String pathSviluppo="/home/leo/Scrivania/Lavoro/Fertilità terreno/png/savedFile.png";
	final static String  pathProduzione="/home/apache-tomcat-7.0.42/webapps/ROOT/immaginiScaricateServizio/savedFile.png";

	
	final static int responsecodeOk=200;
	 
	//Map to store employees, ideally we should use database
	Map<Integer, Employee> empData = new HashMap<Integer, Employee>();
	
	@RequestMapping(value = EmpRestURIConstants.DUMMY_EMP, method = RequestMethod.GET)
	public @ResponseBody Employee getDummyEmployee() {
		logger.info("Start getDummyEmployee");
		Employee emp = new Employee();
		emp.setId(9999);
		emp.setName("Dummy");
		emp.setCreatedDate(new Date());
		empData.put(9999, emp);
		return emp;
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_EMP, method = RequestMethod.GET)
	public @ResponseBody Employee getEmployee(@PathVariable("id") int empId) {
		logger.info("Start getEmployee. ID="+empId);
		
		return empData.get(empId);
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees() {
		logger.info("Start getAllEmployees.");
		List<Employee> emps = new ArrayList<Employee>();
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			emps.add(empData.get(i));
		}
		return emps;
	}
	
	@RequestMapping(value = EmpRestURIConstants.CREATE_EMP, method = RequestMethod.POST)
	public @ResponseBody Employee createEmployee(@RequestBody Employee emp) {
		logger.info("Start createEmployee.");
		emp.setCreatedDate(new Date());
		empData.put(emp.getId(), emp);
		return emp;
	}
	
	@RequestMapping(value = EmpRestURIConstants.DELETE_EMP, method = RequestMethod.PUT)
	public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
		logger.info("Start deleteEmployee.");
		Employee emp = empData.get(empId);
		empData.remove(empId);
		return emp;
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP2, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees2() {
		logger.info("Start getAllEmployees.");
		List<Employee> emps = new ArrayList<Employee>();
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			emps.add(empData.get(i));
		}
		return emps;
	}
	
	
//	@RequestMapping(value = EmpRestURIConstants.GET_URL_PNG, method = RequestMethod.GET)
//	public @ResponseBody String getUrlPng(@PathVariable("status") String status) {
//		logger.info("Start getUrlPng. Status ="+status);
//		
//		
//		String urlPng="";
//		 try {
//			 URL url2 = new URL ("https://actinia.mundialis.de/status/harvestplus/"+status);
//	            System.out.println("URL CHIAMATA :" +url2.toString());
//	            
//	            Base64 b2 = new Base64();
//	            String encoding2= b2.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());
//
//	            HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
//	            connection2.setRequestMethod("GET");
//	            connection2.setDoOutput(true);
//	            connection2.setRequestProperty  ("Authorization", "Basic " + encoding2);
//	            InputStream content2 = (InputStream)connection2.getInputStream();
//
//	            StringBuilder responseStrBuilder2 = new StringBuilder();
//	            BufferedReader in2   = 
//	                new BufferedReader (new InputStreamReader (content2));
//	            String line2;
//	            while ((line2 = in2.readLine()) != null) {
//	            	 responseStrBuilder2.append(line2);
//	            }
//	        
//	            
//	            
//	           JSONObject result2 = new JSONObject(responseStrBuilder2.toString());
//	           System.out.println("RESULT2 "+result2); 
//	           
//	           JSONObject urls=result2.getJSONObject("urls");
//	           
//	           JSONArray resources= urls.getJSONArray("resources");
//	           
//	         urlPng=(String) resources.get(0);
//	        
//	        System.out.println("urlPng "+urlPng);
//	        
//	        content2.close();
//			in2.close();
//		 } 
//	        catch(Exception e) {
//	            e.printStackTrace();
//	        }
//		
//		
//		return urlPng;
//	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_SAVE_AND_CUT_IMG, method = RequestMethod.GET)
	public @ResponseBody String getSaveAndCutImg(@PathVariable("ambiente") String ambiente,@PathVariable("sceneId") String sceneId,
			@PathVariable("south_lat") String south_lat,@PathVariable("north_lat") String north_lat,
			@PathVariable("east_lon") String east_lon,@PathVariable("west_lon") String west_lon,
			@PathVariable("south_lat_small") String south_lat_small,
			@PathVariable("north_lat_small") String north_lat_small,
			@PathVariable("east_lon_small") String east_lon_small,@PathVariable("west_lon_small") String west_lon_small) {


		logger.info("Start getSaveAndCutImg   con ambiente="+ambiente+" sceneId="+sceneId+ " south_lat "+south_lat+" north_lat "+north_lat+ " east_lon "+east_lon+ " west_lon "+west_lon);

		String result_call="";
		String resource_id="";
		String resultSalvataggioImmagine="KO - Salvataggio non avvenuto";
		 try {
			 //passo sceneId

           URL url = new URL ("https://actinia.mundialis.de/sentinel2_process/ndvi/"+sceneId);

           logger.info("URL CHIAMATA getStatus con url  :" +url.toString());

           Base64 b = new Base64();
           String encoding = b.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("POST");
           connection.setDoOutput(true);
           connection.setRequestProperty  ("Authorization", "Basic " + encoding);
           
           int responseCode = connection.getResponseCode();
          logger.info("response code "+connection.getResponseCode());
          if (responseCode == responsecodeOk ) {
			
	
           InputStream content = (InputStream)connection.getInputStream();
          
           
           StringBuilder responseStrBuilder = new StringBuilder();
           BufferedReader in   =
               new BufferedReader (new InputStreamReader (content));
           String line;
           while ((line = in.readLine()) != null) {
           	 responseStrBuilder.append(line);
           }

          JSONObject result = new JSONObject(responseStrBuilder.toString());
         logger.info("Response : \n "+result);


         resource_id=result.getJSONObject("urls").getString("status");
       //ottengo lo stato
        logger.info("Campo STATUS : \n"+resource_id);

       content.close();
		in.close();



		//chiamo per ottenere l'immagine

		 resultSalvataggioImmagine = getUrlPngStatic(resource_id,ambiente);
		 	if(!resultSalvataggioImmagine.equals("")){

//			 south_lat="53.2560298512";
//			 north_lat="52.8703819712";
//			 east_lon="52.8703819712";
//			 west_lon="40.6113931789";
//			 south_lat_small="52.8703819712";
//			 north_lat_small="53.05";
//			 east_lon_small="40.4";
//			 west_lon_small="40.5";

		 	// taglio immagine e la risalvo aggiornata
		 		BufferedImage bimg = null;
				if(ambiente.equals("sviluppo")){
					bimg = ImageIO.read(new File(pathSviluppo));
					logger.info("path sviluppo "+pathSviluppo);
				}
				if(ambiente.equals("produzione")){
					bimg = ImageIO.read(new File(pathProduzione));
					logger.info("path Produzione "+pathProduzione);
				}

				Float west_lon_small_float= Float.parseFloat(west_lon_small);
				Float east_lon_small_float= Float.parseFloat(east_lon_small);
				Float west_lon_float= Float.parseFloat(west_lon);
				Float east_lon_float= Float.parseFloat(east_lon);
				Float north_lat_small_float= Float.parseFloat(north_lat_small);
				Float  south_lat_small_float= Float.parseFloat(south_lat_small);
				Float south_lat_float= Float.parseFloat(south_lat);
				Float north_lat_float= Float.parseFloat(north_lat);
				
				Float w_ratio_num = Math.abs(Math.abs(west_lon_small_float) - Math.abs(east_lon_small_float));
				Float w_ratio_den = Math.abs(Math.abs(west_lon_float) - Math.abs(east_lon_float));

				Float h_ratio_num = Math.abs(Math.abs(north_lat_small_float) - Math.abs(south_lat_small_float));
				Float h_ratio_den = Math.abs(Math.abs(north_lat_float) - Math.abs(south_lat_float));

				Float x_ratio_num = Math.abs(Math.abs(west_lon_small_float) - Math.abs(west_lon_float));
				Float x_ratio_den = Math.abs(Math.abs(east_lon_float) - Math.abs(west_lon_float));

				Float y_ratio_num = Math.abs(Math.abs(north_lat_small_float) - Math.abs(north_lat_float));
				Float y_ratio_den = Math.abs(Math.abs(north_lat_float) - Math.abs(south_lat_float));


				int width_img          = bimg.getWidth();
				int height_img         = bimg.getHeight();

				 int width = Math.round((w_ratio_num / w_ratio_den) * width_img);
				 int height = Math.round((h_ratio_num / h_ratio_den) * height_img);
		
				 int x =Math.round((x_ratio_num / x_ratio_den) * width_img);
				 int y = Math.round((y_ratio_num / y_ratio_den) * height_img);


				 		logger.info("width_img "+width_img);
				 		logger.info("height_img "+height_img);

						logger.info("My width "+width);
						logger.info("My height "+height);

						logger.info("X "+x);
						logger.info("Y "+y);


//				 BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//				 dst.getGraphics().drawImage(src_img, 0, 0, w, h, x, y, x + w, y + h, null);


//						 BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//						 dst.getGraphics().drawImage(src_img, 0, 0, w, h, x, y, x + w, y + h, null);

						if(x+width <=width_img && y+height<=height_img){

						BufferedImage dst = bimg.getSubimage(x, y, width, height);
//						BufferedImage dst = bimg.getSubimage(x, y, w-2*x, h-2*y);
						
								if(ambiente.equals("sviluppo")){
									ImageIO.write(dst, "png", new File(pathSviluppo));
								}
								if(ambiente.equals("produzione")){
									ImageIO.write(dst, "png", new File(pathProduzione));
								}
								
								result_call="L'immagine è stata tagliata e salvata correttamente";
						}
						
						else {
							result_call="Si è verificato un errore nel taglio dell'immagine in quanto i valori inseriti superano la grandezza dell'immagine sorgente!";
						
						logger.error("Si è verificato un errore nel taglio dell'immagine in quanto i valori inseriti superano la grandezza dell'immagine sorgente!");
						}
						 


				 }
		 
	          }else {
	        	  logger.error("Si è verificato un errore nella chiamara verso le API del serve esterno ");
	        	  result_call="Si è verificato un errore nella chiamara verso le API del serve esterno";
	          }
		 }
	        catch(Exception e) {
	        	
	        	logger.error("Si è verificato un errore: "+e.getMessage());
	            e.printStackTrace();
	        }
		 
		 logger.info(result_call);
		 return result_call;
	}
	@RequestMapping(value = EmpRestURIConstants.GET_SAVE_IMG, method = RequestMethod.GET)
	public @ResponseBody String getSaveAndCutImg2(@PathVariable("ambiente") String ambiente,@PathVariable("sceneId") String sceneId,
			@PathVariable("south_lat") String south_lat,@PathVariable("north_lat") String north_lat,
			@PathVariable("east_lon") String east_lon,@PathVariable("west_lon") String west_lon
			) {
	

		logger.info("Start getSaveAndCutImg   con ambiente="+ambiente+" sceneId="+sceneId+ " south_lat "+south_lat+" north_lat "+north_lat+ " east_lon "+east_lon+ " west_lon "+west_lon);
//		logger.info("Start getSaveAndCutImg con    south_lat_small "+south_lat_small+" north_lat_small "+north_lat_small+ " east_lon_small "+east_lon_small+ " west_lon_small "+west_lon_small);
		
		String resource_id="";
		String resultSalvataggioImmagine="KO - Salvataggio non avvenuto";
		 try {
			 //passo sceneId
       	
           URL url = new URL ("https://actinia.mundialis.de/sentinel2_process/ndvi/"+sceneId);
           
           logger.info("URL CHIAMATA getStatus con url  :" +url.toString());
           
           Base64 b = new Base64();
           String encoding = b.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("POST");
           connection.setDoOutput(true);
           connection.setRequestProperty  ("Authorization", "Basic " + encoding);
           InputStream content = (InputStream)connection.getInputStream();

           StringBuilder responseStrBuilder = new StringBuilder();
           BufferedReader in   = 
               new BufferedReader (new InputStreamReader (content));
           String line;
           while ((line = in.readLine()) != null) {
           	 responseStrBuilder.append(line);
           }
       
          JSONObject result = new JSONObject(responseStrBuilder.toString());
         logger.info("Response : \n "+result); 
          
       		   
         resource_id=result.getJSONObject("urls").getString("status");
       //ottengo lo stato
        logger.info("Campo STATUS : \n"+resource_id);
       
       content.close();
		in.close();
		
		
		//chiamo per ottenere l'immagine
		
		 resultSalvataggioImmagine = getUrlPngStatic(resource_id,ambiente);
		
		 	

			
				
				 } 
		 
	        catch(Exception e) {
	            e.printStackTrace();
	        }
		 return resultSalvataggioImmagine;
	}
		
		

//	@RequestMapping(value = EmpRestURIConstants.GET_URL_PNG, method = RequestMethod.GET)
//	public @ResponseBody String getUrlPng(@PathVariable("resource_id") String resource_id, @PathVariable("ambiente") String ambiente) {
//	
//			logger.info("Start getUrlPngNew con \n resource_id ="+resource_id +" ambiente "+ambiente);
//
//		String statoInvio ="";
//		String urlPng="";
//		String destinationFile ="";
//		 try {
//			 
//	        	JSONArray resources = getUrlPngStatic(resource_id);
//	        	
//	        	while (resources.length() ==0) {
//	        		 resources = getUrlPngStatic(resource_id);
//	        		 if(resources.length()>0){
//			        	   	urlPng=(String) resources.get(0);
//			        
//			        	   	logger.info("urlPng dentro: "+urlPng);
//			           }
//				}
//	        	 if(resources.length()>0){
//		        	   	urlPng=(String) resources.get(0);
//		        
//		        	   	logger.info("urlPng  fuori: "+urlPng);
//		           }
//	        	 
//	        	//salvo immagino sul disco
//     		
//
//	        	 if(ambiente.equals("sviluppo")){
//	        		 destinationFile ="/home/leo/Scrivania/Lavoro/Fertilità terreno/png/savedFile.png";
//	        	 }
//	        	 if(ambiente.equals("produzione")){
//	        	 destinationFile="/home/apache-tomcat-7.0.42/webapps/ROOT/immaginiScaricateServizio/savedFile.png";
//	        	 }
//     		
//	        	 if(!urlPng.equals("")){
//     			
//     		 statoInvio = saveImage(urlPng, destinationFile);
//     		}
//		 } 
//	        catch(Exception e) {
//	            e.printStackTrace();
//	        }
//		
//		return statoInvio;
//	}
	
	
	 public static String saveImage(String imageUrl, String destinationFile) throws IOException {
		String statoSalvataggio="KO -Immagine non salvata";
		 try {
		
		 URL url2 = new URL (imageUrl);
            
            logger.info("URL CHIAMATA  salva immagine con URL : \n " +url2.toString());
            
            Base64 b2 = new Base64();
            String encoding2= b2.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

            HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
            connection2.setRequestMethod("GET");
            connection2.setDoOutput(true);
            connection2.setRequestProperty  ("Authorization", "Basic " + encoding2);
            InputStream content2 = (InputStream)connection2.getInputStream();
		
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = content2.read(b)) != -1) {
				os.write(b, 0, length);
			}

			content2.close();
			os.close();
			statoSalvataggio="OK- Savataggio avvenuto con successo";
			} catch (Exception e) {
				 e.printStackTrace();
			}
		 
		 logger.info("Stato Salvataggio immagine sul disco : " +statoSalvataggio);
		return statoSalvataggio;
		}
	 
	 
	 
	 public static JSONArray getUrlPngStatic(String resource_id) {
			
			JSONArray resources = null;
			 try {
//		commentato xke nn fatto come servizio piu il nostro flusso  URL url2 = new URL ("https://actinia.mundialis.de/status/harvestplus/"+resource_id);
				 URL url2 = new URL (resource_id);
		            
				 logger.info("URL CHIAMATA in getUrlPngStatic :" +url2.toString());
		            
		            Base64 b2 = new Base64();
		            String encoding2= b2.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

		            HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
		            connection2.setRequestMethod("GET");
		            connection2.setDoOutput(true);
		            connection2.setRequestProperty  ("Authorization", "Basic " + encoding2);
		            InputStream content2 = (InputStream)connection2.getInputStream();

		            StringBuilder responseStrBuilder2 = new StringBuilder();
		            BufferedReader in2   = 
		                new BufferedReader (new InputStreamReader (content2));
		            String line2;
		            while ((line2 = in2.readLine()) != null) {
		            	 responseStrBuilder2.append(line2);
		            }
		        
		            
		            
		           JSONObject result2 = new JSONObject(responseStrBuilder2.toString());
		           JSONObject urls=result2.getJSONObject("urls");
		           
		            resources= urls.getJSONArray("resources");
		            logger.info("resources "+resources);
		        content2.close();
	 			in2.close();
	 			
			 }
		        catch(Exception e) {
		            e.printStackTrace();
		        }
			
			
			return resources;
			
		}
	
	 //primaChiamata
		@RequestMapping(value = EmpRestURIConstants.GET_COORDINATE_TEMPO, method = RequestMethod.GET)
		public @ResponseBody String getConCordinate(@PathVariable("starTime") String starTime,@PathVariable("endTime") String endTime,@PathVariable("lon") String lon, @PathVariable("lat") String lat) {
			logger.info("Start getStatus getConCordinate starTime ="+starTime);
		 String result = "";
		 try {
            
			 URL url = new URL("https://actinia.mundialis.de/sentinel2_query?start_time="+starTime+"&end_time="+endTime+"&lon="+lon+"&lat="+lat+"&cloud_covert=10");
//			 URL url = new URL("https://actinia.mundialis.de/sentinel2_query?start_time=2017-01-01T12%3A00%3A00&end_time=2017-01-01T12%3A40%3A00&lon=-40.5&lat=-53&cloud_covert=10");
            System.out.println("URL CHIAMATA getConCordinate :" +url.toString());
            
            Base64 b = new Base64();
            String encoding = b.encodeAsString(new String("harvestplus:h#rv35tPlu5Pa55").getBytes());

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();

            StringBuilder responseStrBuilder = new StringBuilder();
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
            	 responseStrBuilder.append(line);
            }
        
            result = responseStrBuilder.toString();
           System.out.println("RESULT prima chiamata con cordinate e tempo "+result); 

        
        content.close();
			in.close();
		
		 } 
	        catch(Exception e) {
	            e.printStackTrace();
	        }
		 return result;
	 }

		
		
		
		
		
		
		
		public static String getUrlPngStatic(String resource_id, String ambiente) {
			
			logger.info("Start getUrlPngStatic con \n resource_id ="+resource_id +" ambiente "+ambiente);

		String statoSalvataggio ="";
		String urlPng="";
		String destinationFile ="";
		 try {
			 
	        	JSONArray resources = getUrlPngStatic(resource_id);
	        	
	        	while (resources.length() ==0) {
	        		 resources = getUrlPngStatic(resource_id);
	        		 if(resources.length()>0){
			        	   	urlPng=(String) resources.get(0);
			        
			        	   	logger.info("urlPng dentro: "+urlPng);
			           }
				}
	        	 if(resources.length()>0){
		        	   	urlPng=(String) resources.get(0);
		        
		        	   	logger.info("urlPng  fuori: "+urlPng);
		           }
	        	 
	        	//salvo immagino sul disco
     		

	        	 if(ambiente.equals("sviluppo")){
	        		 destinationFile =pathSviluppo;
	        	 }
	        	 if(ambiente.equals("produzione")){
	        	 destinationFile=pathProduzione;
	        	 }
     		
	        	 if(!urlPng.equals("")){
     			
	        		 statoSalvataggio = saveImage(urlPng, destinationFile);
     		}
		 } 
	        catch(Exception e) {
	            e.printStackTrace();
	        }
		
		return statoSalvataggio;
	}
		
		
	 
	}





