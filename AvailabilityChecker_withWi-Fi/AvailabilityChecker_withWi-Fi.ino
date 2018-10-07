/*
 * 31 mar 2015
 * This sketch display UDP packets coming from an UDP client.
 * On a Mac the NC command can be used to send UDP. (nc -u 192.168.1.101 2390). 
 *
 * Configuration : Enter the ssid and password of your Wifi AP. Enter the port number your server is listening on.
 *
 */
/*
  
  


*/
//---------------------------------------------------------------------------------------------------




int something = 0;
String state;
String condition = "default";
//Include the Arduino Stepper Library
#include <Stepper.h>

// Define Constants
#define x1 0   //pin8
#define x2 2   //pin9
#define x3 4   //pin14
#define x4 5   //pin15
const float STEPS_PER_REV = 32; 
Stepper steppermotor(STEPS_PER_REV,x1 , x3, x2, x4);











#include <ESP8266WiFi.h>
#include <WiFiUDP.h>
int pinRed = 16;     // pin4
int pinGreen = 15;   // pin3
//int pinq = 4;
//int pinw= 5;

int temp = 1;

extern "C" {  //required for read Vdd Voltage
#include "user_interface.h"
}

int status = WL_IDLE_STATUS;
const char* ssid = "TestProline";  //  your network SSID (name)
const char* pass = "!2345qawsedrf";       // your network password

unsigned int localPort = 666;      // local port to listen for UDP packets

byte packetBuffer[512]; //buffer to hold incoming and outgoing packets

// A UDP instance to let us send and receive packets over UDP
WiFiUDP Udp;

void setup()
{ 
//  pinMode(pinq, OUTPUT);
//  pinMode(pinw, OUTPUT);
  
  
  pinMode(pinRed, OUTPUT);
  pinMode(pinGreen, OUTPUT);
  digitalWrite(pinRed, LOW);
  digitalWrite(pinGreen, HIGH);
  
 
 
  // Open serial communications and wait for port to open:
  Serial.begin(115200);
  // setting up Station AP
  WiFi.begin(ssid, pass);
  
  // Wait for connect to AP
  Serial.print("[Connecting]");
  Serial.print(ssid);
  int tries=0;
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
    tries++;
    if (tries > 30){
      break;
    }
  }
  Serial.println();


printWifiStatus();

  Serial.println("Connected to wifi");
  Serial.print("Udp server started at port ");
  Serial.println(localPort);
  Udp.begin(localPort);
  
}

void loop()
{ 
//  digitalWrite(pinq, HIGH);
//  digitalWrite(pinw, LOW);
//  delay(500);
//  digitalWrite(pinq, LOW);
//  digitalWrite(pinw, HIGH);
//  delay(500);
//   

  
  int noBytes = Udp.parsePacket();
  String received_command = "";
  if ( noBytes ) {
//    Serial.print(millis() / 1000);
//    Serial.print(":Packet of ");
//    Serial.print(noBytes);
//    Serial.print(" received from ");
//    Serial.print(Udp.remoteIP());
//    Serial.print(":");
//    Serial.println(Udp.remotePort());
    // We've received a packet, read the data from it
    Udp.read(packetBuffer,noBytes); // read the packet into the buffer

    // display the packet contents in HEX
    for (int i=1;i<=noBytes;i++)
    {
      //Serial.print(packetBuffer[i-1],HEX);
      received_command = received_command + char(packetBuffer[i - 1]);
      if (i % 32 == 0)
      {
//        Serial.println();
      }
    } // end for
    Serial.println();
    
//    Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
//    Udp.write("Answer from ESP8266 ChipID#");
//    Udp.print(system_get_chip_id());
//    Udp.write("#IP of ESP8266#");
//    Udp.println(WiFi.localIP());
//    Udp.endPacket();
    
    Serial.println(received_command);
    Serial.println();
   // end if
    Serial.println(received_command);

    if(received_command == "available"){
      digitalWrite(pinGreen, HIGH);
      digitalWrite(pinRed, LOW);
          
        ESP.wdtDisable();
  
        steppermotor.setSpeed(1024);
        steppermotor.step(-2048);
        }
  ESP.wdtDisable();
  
    
    if(received_command == "busy"){
      digitalWrite(pinGreen, LOW);
      digitalWrite(pinRed, HIGH);
      Serial.println("RED LIGHT");
      digitalWrite(pinGreen, LOW);
      
        ESP.wdtDisable();
  
        steppermotor.setSpeed(1024);
        steppermotor.step(2048);
        }
    
     
ESP.wdtDisable();
  
      
          
    }  
    
    
   
}

void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);
}
