
//int pinGreen = 10;
//int pinRed = 11;
int something = 0;
String state;
String condition = "default";
//Include the Arduino Stepper Library
#include <Stepper.h>

// Define Constants
#define x1 9   
#define x2 8   
#define x3 7   
#define x4 6   
const float STEPS_PER_REV = 32; 
Stepper steppermotor(STEPS_PER_REV,x1 , x3, x2, x4);


void setup() {
//pinMode(pinGreen, OUTPUT);
//pinMode(pinRed, OUTPUT);
Serial.begin(9600);            


}

void loop() {
  delay(100);
  
   condition = Serial.readString();
   String line1 = condition.substring(0, 16);
    if(line1.equals("xfw")){

      steppermotor.setSpeed(1024);
      steppermotor.step(2048);
      
      delay(1000);
    }
    if(line1 == "xbw"){
      
      steppermotor.setSpeed(1024);
      steppermotor.step(-2048);
      
      delay(1000);
    }
    
    
    
    
    
}
