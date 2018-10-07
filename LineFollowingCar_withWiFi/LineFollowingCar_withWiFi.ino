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

#include <ESP8266WiFi.h>
#include <WiFiUDP.h>


int something = 0;
String state;
String condition = "default";
//Include the Arduino Stepper Library
#include <Stepper.h>

// Define Constants
#define left 16   //pin4
#define right 15   //pin3
#define forward 2   //pin9
#define backwards 0   //pin8

#define left_sensor  4 //pin14
#define right_sensor  5 //pin15

int value = 0;

int left_state, right_state;













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
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
  pinMode(forward, OUTPUT);
  pinMode(backwards, OUTPUT);
  
  pinMode(right_sensor, INPUT); // sensör pinleri giriş pini olarak atandı
  pinMode(left_sensor, INPUT);
  
 
 
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

  Serial.println("At the top");
  int noBytes = Udp.parsePacket();
  String received_command = "";
  
  if ( noBytes ) {while(1){
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
    
    Serial.println(received_command);
    Serial.println();
   // end if
    Serial.println(received_command);

    if(received_command == "start"){
      ESP.wdtDisable();
       value=1;
       Serial.println("Value: 1");
    }
    
      
     if(received_command == "stop"){
      ESP.wdtDisable();
      value =0;
      Serial.println("Value: 0");
      }
      ESP.wdtDisable();
       
      received_command = "";
      noBytes = Udp.parsePacket();
              
      if(value == 1){
      
      left_state = digitalRead(left_sensor); // sol ve sağ sensör okunup değişkenlere kaydedildi.
      right_state = digitalRead(right_sensor);
      Serial.println("Now in initialization part");

        if (left_state == LOW && right_state == LOW) // iki sensör de siyah görmüyor ise motorlar ileri gidecek şekilde çalışıtırıldı.
          {ESP.wdtDisable();
 
            //    digitalWrite(sag_i, HIGH);
            //    digitalWrite(sag_g, LOW);
            //    digitalWrite(sol_i, HIGH);
            //    digitalWrite(sol_g, LOW);
             digitalWrite(left, LOW);
              digitalWrite(right, LOW);
              digitalWrite(forward, HIGH);            //forward
              digitalWrite(backwards, LOW);
    
              Serial.println("Go forward");

    
  }
         else if (left_state == LOW && right_state == HIGH) // sağ sensör siyah görüyor ise motorlar sağa dönecek şekilde çalıştırıldı.
          {ESP.wdtDisable();

          //    digitalWrite(sag_i, HIGH);
          //    digitalWrite(sag_g, HIGH);
          //    digitalWrite(sol_i, HIGH);
          //    digitalWrite(sol_g, LOW);
          digitalWrite(left, LOW);              
            digitalWrite(right, HIGH);          //right
            digitalWrite(forward, HIGH);
            digitalWrite(backwards, LOW);
          
            Serial.println("Go right");
    
  }
          else if (left_state == HIGH && right_state == LOW) // sol sensör siyah görüyor ise motorlar sola dönecek şekilde çalıştırıldı.
          {ESP.wdtDisable();
 

          
        //    digitalWrite(sag_i, HIGH);
        //    digitalWrite(sag_g, LOW);
        //    digitalWrite(sol_i, HIGH);
        //    digitalWrite(sol_g, HIGH);
        
        digitalWrite(left, HIGH);
          digitalWrite(right, LOW);             //left
          digitalWrite(forward, HIGH);
          digitalWrite(backwards, LOW);


        Serial.println("Go left");
  }
          else  //eğer yukarıda şartların 3 üde değilse dur.
          {ESP.wdtDisable();


          
        //    digitalWrite(sag_i, LOW);
        //    digitalWrite(sag_g, LOW);
        //    digitalWrite(sol_i, LOW);
        //    digitalWrite(sol_g, LOW);
         digitalWrite(left, LOW);
          digitalWrite(right, LOW);
          digitalWrite(forward, LOW);           //default
          digitalWrite(backwards, LOW);
            Serial.println("Stop");
  }ESP.wdtDisable();
          delay(15);                      // ivme kırıcı kodlar, bunların mantığı videolarda anlatıldı arkadaşlar.
        //  digitalWrite(sag_i, LOW); 
        //  digitalWrite(sag_g, LOW);
        //  digitalWrite(sol_i, LOW);
        //  digitalWrite(sol_g, LOW);
         digitalWrite(left, LOW);
          digitalWrite(right, LOW);
          digitalWrite(forward, LOW);           //default
          digitalWrite(backwards, LOW);
          Serial.println("Slow down");
          delay(15);
        
         }
         
      }
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
