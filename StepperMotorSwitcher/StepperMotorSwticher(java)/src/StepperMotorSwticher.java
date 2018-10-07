import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.fazecast.jSerialComm.SerialPort;

import arduino.Arduino;


public class StepperMotorSwticher
{
  static JFrame frame;

static SerialPort[] portNames = SerialPort.getCommPorts();
static String port = portNames[0].getSystemPortName();
static Arduino obj = new Arduino(port, 9600);
  static void displayJFrame()
  {obj.openConnection();
    frame = new JFrame("Stepper Motor");

    // create our jbutton
    JButton showDialogButton = new JButton("LIGHTS ON");
    JButton button2 = new JButton("LIGHTS OFF");
    
    // add the listener to the jbutton to handle the "pressed" event
    showDialogButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        // display/center the jdialog when the button is pressed
			obj.serialWrite("xfw"); // Sends arduino to rotate clockwise
			
		
       
      }
    });
    button2.addActionListener(new ActionListener() {  
    	public void actionPerformed(ActionEvent e1) {
    		
    		 try {
    				Thread.sleep(1000);
    				obj.serialWrite("xbw"); // Sends arduino to rotate clockwise
    				Thread.sleep(1000);
    				
    			} catch (InterruptedException e11) {
    				// TODO Auto-generated catch block
    				e11.printStackTrace();
    			}
    		
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

	  
	  System.out.println(port);
//      
      obj.closeConnection();
  
	  
	  
	  
	  
	  

	  
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