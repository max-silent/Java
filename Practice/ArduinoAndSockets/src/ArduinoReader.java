import java.io.*;

//import java.net.*;
import jssc.SerialPort;
import jssc.SerialPortList;
import jssc.SerialPortException;

//import javax.swing.*;



//import java.awt.event.*;
//import processing.serial.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ArduinoReader{
	public static void main(String[] args){
		
		String[] serialPortList = SerialPortList.getPortNames();
		boolean cycled=true;
		Socket clientSocket=null;
		OutputStream clientToServerStream=null;
		BufferedReader serverToClientStream=null;
		
		if (serialPortList.length==0){
			System.out.println("There is no serial ports detected.\nExiting program...");
			System.exit(0);
		}
		SerialPort highestPort = new SerialPort(serialPortList[serialPortList.length-1]);
		int inputValue=0, prevValue=0;

		System.out.println("Available serial ports are:");
		for (int i=0; i<serialPortList.length; i++){
			System.out.println(serialPortList[i]);
		}
		System.out.println("Chosen (highest) serial port is:");
		System.out.println(serialPortList[serialPortList.length-1]);
		
		try{
		clientSocket = new Socket("localhost", 3000);
		clientToServerStream = clientSocket.getOutputStream();
		serverToClientStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		System.out.println("Opening port");
		highestPort.openPort();
		highestPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		while (cycled){
			inputValue=highestPort.readIntArray(1)[0];

			if (inputValue!=prevValue){
				System.out.println("Current value is: "+inputValue);
				prevValue=inputValue;
				System.out.println("Writing to Server");
				clientToServerStream.write(inputValue);
				System.out.println("Reading from Server");
				System.out.println(serverToClientStream.read());
			}
		}
		highestPort.closePort();
	}
		catch (UnknownHostException uhe){
	        System.out.println("UnknownHostException: " + uhe);
		} catch (IOException ioe){
		   System.err.println("IOException: " + ioe);
		}
		catch(SerialPortException ex){
			System.out.println(ex);
		}
		//System.out.println(Serial.list()[0]);
		//serialPort=new Serial (null,Serial.list()[0],9600);
		//printValue();
		finally{
			// Close the streams
			 try{
				 clientToServerStream.close();
				 serverToClientStream.close();
			     clientSocket.close();
			 } catch(IOException e){
			      System.out.println("Can not close streams..." + 
		                                           e.getMessage());
			 }
		      }
}
}