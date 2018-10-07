import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
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

import javax.swing.*;



public class LineFollowingCar
{
  static JFrame frame;
  static Properties prop = new Properties();
  static	InputStream input = null;
  static	String path1 = null;
  static	String ipAddress = null;
  static	String port = null;
  static	String mailfrom = null;
  static	String mailto = null;
  static	String password = null;
  static  DatagramSocket socket; //for connection
  static	InetAddress address;


	byte[] buf;

  static void displayJFrame()
  {
	  input = LineFollowingCar.class.getResourceAsStream("config.properties");
		// load a properties file
		try {
			prop.load(input);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

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
	  
		try {
			Thread.sleep(1316);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}				//Minimum time for arduino to read 1312 miliseconds
//		 uno.serialWrite("start");
		 
		 
		 DatagramSocket socket; //for connection
			InetAddress address;
		 
		 
			byte[] buf;
	  
	  
			try {
				socket = new DatagramSocket();
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
	        
			try {
				address = InetAddress.getByName(ipAddress);
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			int portnumber = Integer.parseInt(port);
	  
	  
	  
	  
	  
	  
    frame = new JFrame("Line Following Car");

    // create our jbutton
    JButton showDialogButton = new JButton("START");
    JButton button2 = new JButton("STOP");
    
    // add the listener to the jbutton to handle the "pressed" event
    showDialogButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        // display/center the jdialog when the button is pressed
			 // Sends arduino to rotate clockwise
    	  String msg = "start";
	        buf = msg.getBytes();
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portnumber);
	        socket.send(packet);
	        packet = new DatagramPacket(buf, buf.length);
		
       
      }
    });
    button2.addActionListener(new ActionListener() {  
    	public void actionPerformed(ActionEvent e1) {
    		 String msg1 = "stop";
		        buf = msg1.getBytes();
		        DatagramPacket packet1 = new DatagramPacket(buf, buf.length, address, portnumber);
		        socket.send(packet1);
		        packet1 = new DatagramPacket(buf, buf.length);
		        
		        socket.close();
    		
    	}	
    });
    

    // put the button on the frame
    frame.getContentPane().setLayout(new FlowLayout());
    frame.add(showDialogButton);
    frame.add(button2);

    // set up the jframe, then display it
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(500, 400));
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  public static void main(String[] args) throws InterruptedException
  {

	  
	 
	  System.out.println("Connected");
	  
	  
	  
	  
	  

	  
    // schedule this for the event dispatch thread (edt)
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        displayJFrame();
      }
    });
  }

}