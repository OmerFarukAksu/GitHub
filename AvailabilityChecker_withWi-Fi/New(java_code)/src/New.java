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

public class New{

	static InputStream inputStream;
	static byte readBuffer[];
	public static void main(String[] args) {
		
		
		
		Properties prop = new Properties();
		InputStream input = null;
		String path1 = null;
		String ipAddress = null;
		String port = null;
		String mailfrom = null;
		String mailto = null;
		String password = null;
       
		
		try {

			input = New.class.getResourceAsStream("config.properties");
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("path"));
			
			path1 = prop.getProperty("path");
			System.out.println("Path: "+ path1);
			ipAddress =prop.getProperty("ipAddress");
			System.out.println("IP Address: "+ ipAddress);
			port = prop.getProperty("port");
			System.out.println("Port: "+ port);
			mailfrom = prop.getProperty("mailfrom");
			System.out.println("Mailfrom :"+ mailfrom);
			mailto = prop.getProperty("mailto");
			System.out.println("Mailto"+ mailto);
			password= prop.getProperty("Password");
			
			
//			 String port = Registery.readRegistry("HKEY_LOCAL_MACHINE\\HARDWARE\\DEVICEMAP\\SERIALCOMM","\\Device\\Serial2");
//			 System.out.println("port: " +port);
//			 Arduino uno = new Arduino(port, 9600);
//		
//				try {
//					uno.openConnection();}catch(Exception e) {
//						System.out.println("Couldn't connect");
//						
//					}
		
			
		

				
		
		 try {
			 Thread.sleep(1316);				//Minimum time for arduino to read 1312 miliseconds
//			 uno.serialWrite("start");
			 
			 
			 DatagramSocket socket; //for connection
				InetAddress address;
			 
			 
				byte[] buf;
				 
			 
			 
			 
			
					socket = new DatagramSocket();
				
		        
					address = InetAddress.getByName(ipAddress);
					int portnumber = Integer.parseInt(port);
				
					
			 
			 
			 
			 String msg = "busy";
		        buf = msg.getBytes();
		        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portnumber);
		        socket.send(packet);
		        packet = new DatagramPacket(buf, buf.length);
//		        socket.receive(packet);
//		        String received = new String(
//		          packet.getData(), 0, packet.getLength());
		        
		    
		 
		  
		        
			 
			 System.out.println("I'm here");
			 
			 
			 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			 Date date = new Date();
			 String time1 = date.toString();
			Process x1 = Runtime.getRuntime().exec("java -jar "+ path1);
			x1.waitFor();
			Thread.sleep(803);					//Minimum time for arduino to read 1312 miliseconds
//			uno.serialWrite("quit");
			
			
			
			
			
			  String msg1 = "available";
		        buf = msg1.getBytes();
		        DatagramPacket packet1 = new DatagramPacket(buf, buf.length, address, portnumber);
		        socket.send(packet1);
		        packet1 = new DatagramPacket(buf, buf.length);
//		        socket.receive(packet1);
//		        String received1 = new String(
//		          packet1.getData(), 0, packet1.getLength());
		        
		    
		 
		  
		        socket.close();
			
			
			
			
			
			
			
			
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
