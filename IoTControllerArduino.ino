#include <SoftwareSerial.h>
#include <Servo.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <string.h>


#define LED_PIN 3  
#define FAN_PIN 5  
#define SENSOR_PIN 4
#define RX_BT 10
#define TX_BT 11

double tempCelsius;
Servo servo;
SoftwareSerial bluetooth(RX_BT, TX_BT); 
OneWire oneWire(SENSOR_PIN);
DallasTemperature tempSensor(&oneWire);
void setup() {
  pinMode(LED_PIN, OUTPUT);  
  pinMode(FAN_PIN, OUTPUT);
  servo.attach(9);  
  servo.write(0);
  tempSensor.begin();              
  Serial.begin(9600);
  bluetooth.begin(9600);
}

void loop() {
  if (bluetooth.available()){
    char data = bluetooth.read();
    Serial.println(data);
    
    if (data >= 'A' && data <= 'U') {
      int brightness = (data - 'A') * 12.75;
      analogWrite(LED_PIN, brightness);
    }
    else if (data >= '0' && data <= '9') {
      int speed = (data - '0') * 25.5;
      analogWrite(FAN_PIN, speed);
    }
    else if (data == 'W') {
      for (int pos = 0; pos <= 160; pos += 1) {
        servo.write(pos);
        delay(5);
      }
    }
    else if (data == 'X') {
      for (int pos = 160; pos >= 0; pos -= 1) {
        servo.write(pos);
        delay(5);
      }
    }
    else if(data == 'Z'){
      tempSensor.requestTemperatures();
      tempCelsius = tempSensor.getTempCByIndex(0)+162.5;
      String data2 = String(tempCelsius,2);
      String data1 = "Temperature: "+data2+"°C";
      Serial.println(data1);
      bluetooth.print(data1);
      delay(500);
    }
  }
}
