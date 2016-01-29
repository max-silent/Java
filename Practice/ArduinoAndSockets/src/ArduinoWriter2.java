import java.io.*;

import jssc.SerialPort;
import jssc.SerialPortList;
import jssc.SerialPortException;

public class ArduinoWriter2 {
 public static void main(java.lang.String[] args) {
	String[] serialPortList = SerialPortList.getPortNames();
	if (serialPortList.length==0){
		System.out.println("There is no serial ports detected.\nExiting program...");
		System.exit(0);
	}
	SerialPort highestPort = new SerialPort(serialPortList[serialPortList.length-1]);
	int inputValue=0, prevValue=0;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String readString;
	System.out.println("Available serial ports are:");
	for (int i=0; i<serialPortList.length; i++){
		System.out.println(serialPortList[i]);
	}
	
	System.out.println("Chosen (highest) serial port is:");
	System.out.println(serialPortList[serialPortList.length-1]);
	
	try{
	System.out.println("Opening port");
	highestPort.openPort();
	highestPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	while (true){
		System.out.print("\nEnter the Servo desired angle: ");
		//inputValue = (byte) System.in.read();
		readString=reader.readLine();
		if (readString=="q"||readString=="Q") break;
		else{
			inputValue = Integer.parseInt(readString);
			highestPort.writeInt(inputValue);
		}
	}

		//highestPort.writeByte(inputValue);
		//highestPort.writeInt(150);

	}

	catch (SerialPortException spe) {
		System.out.println("Error in Serial Port: " + spe);
	}
	catch (IOException ioe) {
		System.out.println("Error in Input-Output: " + ioe);
	}
	finally{
      try{
    		highestPort.closePort();
    		reader.close();
        }catch(Exception e){
       	 System.out.println("Error occured while closing the Serial Port: " + e.getMessage());
        }
    }  
  }
 }

