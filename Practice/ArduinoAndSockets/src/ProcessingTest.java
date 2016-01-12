//import java.io.*;
//import java.net.*;
import jssc.SerialPort;
import jssc.SerialPortList;
import jssc.SerialPortException;
import javax.swing.*;
import java.awt.event.*;
//import processing.serial.*;

public class ProcessingTest{
	String[] serialPortList = SerialPortList.getPortNames();
	boolean cycled=true;
	//Serial serialPort;
	SerialPort highestPort = new SerialPort(serialPortList[serialPortList.length-1]);
	int inputValue=0, prevValue=0;
	ProcessingTest(){
		System.out.println("Available serial ports are:");
		for (int i=0; i<serialPortList.length; i++){
			System.out.println(serialPortList[i]);
		}
		System.out.println("The highest serial port is:");
		System.out.println(serialPortList[serialPortList.length-1]);
		try{
		highestPort.openPort();
		highestPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		while (cycled){
		inputValue=highestPort.readIntArray(1)[0];
				//inputValue=highestPort.readBytes(1)[0];
			if (inputValue!=prevValue){
				System.out.println("Current bgcolor: "+inputValue);
				prevValue=inputValue;
			}
			if (System.in.equals('q')) cycled=false;
		}
		highestPort.closePort();
		}
		catch(SerialPortException ex){
			System.out.println(ex);
		}
		//System.out.println(Serial.list()[0]);
		//serialPort=new Serial (null,Serial.list()[0],9600);
		//printValue();
	}

public static void main(String[] args){
new ProcessingTest();	
}
}