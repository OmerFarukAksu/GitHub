#define pinGreen 10
#define  pinRed 11
int value =0; // Serial.available() resets its value over time this a its values summed
void setup() {
  // put your setup code here, to run once:
  pinMode(pinGreen, OUTPUT),
  pinMode(pinRed, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(pinGreen, HIGH);
  digitalWrite(pinRed, LOW);                          //State 1
  
  
  while(value == 0){
                                                      //State 2
  if(Serial.readString() == "start"){
    value = 1;
    }
  
 
  //Wait for input
  }
  digitalWrite(pinGreen, LOW);                        //State 3
  digitalWrite(pinRed, HIGH);

  
  while(value>0){
    
 
   if(Serial.readString() == "quit"){                 //State 4
    value=0;
    } 
    
  }    }


