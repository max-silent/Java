import java.io.*;
import java.net.*;
public class ArduinoWriter {
 public static void main(java.lang.String[] args) {
	 int receivedValue;
	 ServerSocket serverSocket = null;
	 Socket clientSocket = null;

	 BufferedInputStream clientToServerStream = null;
	 OutputStream serverToClientStream = null;

   try {
	 // Create a server socket
	 serverSocket = new ServerSocket(3000);
	 // Wait for a  request
 	 System.out.println("Waiting for request");
	 clientSocket = serverSocket.accept();
	 // Get the streams to/from Client
	 clientToServerStream = new BufferedInputStream(clientSocket.getInputStream());
	 serverToClientStream = clientSocket.getOutputStream();

	 while (true)
	 {
  	   receivedValue = clientToServerStream.read();
 	   System.out.println("\nValue \""+receivedValue+"\" is received from Client.");
 	   serverToClientStream.write(receivedValue);
	}
   }
   catch (SocketException se) {
	   System.out.println("Failed to exchange data - connection has been lost: " + se.getMessage());
   }
   catch (IOException ioe) {
	System.out.println("Error in Server: " + ioe);
   }
   finally{
      try{
    	  clientToServerStream.close();
          serverToClientStream.close();   
        }catch(Exception e){
       	 System.out.println("Failed to close streams" + e.getMessage());
        }
    }  
  }
 }

