import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Properties;

import com.fazecast.jSerialComm.SerialPort;

import arduino.Arduino;

public class AvailabilityChecker{

	static InputStream inputStream;
	static byte readBuffer[];
	public static void main(String[] args) {
		
		
		
		Properties prop = new Properties();
		InputStream input = null;
		String path1 = null;
		String mailfrom = null;
		String mailto = null;
		String password = null;
       
		
		try {

			input = AvailabilityChecker.class.getResourceAsStream("config.properties");
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("path"));
			
			path1 = prop.getProperty("path");
			System.out.println("Path: "+ path1);
			mailfrom = prop.getProperty("mailfrom");
			System.out.println("Mailfrom :"+ mailfrom);
			mailto = prop.getProperty("mailto");
			System.out.println("Mailto"+ mailto);
			password= prop.getProperty("Password");
			
			
			 String port = Registery.readRegistry("HKEY_LOCAL_MACHINE\\HARDWARE\\DEVICEMAP\\SERIALCOMM","\\Device\\Serial2");
			 System.out.println("port: " +port);
			 Arduino uno = new Arduino(port, 9600);
		
				try {
					uno.openConnection();}catch(Exception e) {
						System.out.println("Couldn't connect");
						
					}
		
			
		

				
		
		 try {
			 Thread.sleep(1316);				//Minimum time for arduino to read 1312 miliseconds
//			 uno.serialWrite("start");
			 
		
			 
			 
		
		    
		 
		  
		        
			 
			 System.out.println("I'm here");
			 
			 
			 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			 Date date = new Date();
			 String time1 = date.toString();
			Process x1 = Runtime.getRuntime().exec("java -jar "+ path1);
			x1.waitFor();
			Thread.sleep(803);					//Minimum time for arduino to read 1312 miliseconds
			uno.serialWrite("quit");
			
			
			
			
			
			
			
			
			
			
			 Date date1 = new Date();
			String time2 = date1.toString();
			MailSender.send(mailfrom,password,mailto,"Testt","Test start time: "+ time1+ "\n"+ "Test end time: "+ time2);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
