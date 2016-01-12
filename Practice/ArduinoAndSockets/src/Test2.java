import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import jssc.SerialPort;


public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket clientSocket=null;
		BufferedOutputStream manipulatorToActuatorStream = null;
		BufferedInputStream actuatorToManipulatorStream = null;
		try{
		clientSocket=new Socket("localhost", 3000);
		manipulatorToActuatorStream = new BufferedOutputStream(clientSocket.getOutputStream());
		actuatorToManipulatorStream = new BufferedInputStream(clientSocket.getInputStream());
		System.out.println("Cycle started:");
			for (int i=0; i<255; i++){

					manipulatorToActuatorStream.write(i);
					System.out.println(actuatorToManipulatorStream.read());

			}
		}
		catch (Exception exc){
			System.out.println("Error: "+exc.getMessage());
		}
			//highestPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				// Close the streams
				 try{
					 manipulatorToActuatorStream.close();
					 actuatorToManipulatorStream.close();
				     clientSocket.close();
				 } catch(IOException e){
				      System.out.println("Can not close streams..." + 
			                                           e.getMessage());
			      }

			}
}
