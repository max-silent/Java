import java.io.*;
import java.net.*;
public class ArduinoWriter {
 public static void main(java.lang.String[] args) {
	 int receivedValue;
	 ServerSocket serverSocket = null;
	 Socket clientSocket = null;

   BufferedReader clientToServerStream = null;
   OutputStream serverToClientStream = null;

   try
	 {
	 // Create a server socket
	 serverSocket = new ServerSocket(3000);
	 // Wait for a  request
 	 System.out.println("Waiting for request");
	 clientSocket = serverSocket.accept();
	 // Get the streams to/from Client
	 clientToServerStream=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	 serverToClientStream = clientSocket.getOutputStream();

	 while (true)
	 {
  	   receivedValue = clientToServerStream.read();
 	   System.out.println("\nValue \""+receivedValue+"\" is received from the Client.");
 	   serverToClientStream.write(receivedValue);
	}
   }
   catch (IOException ioe) {
	System.out.println("Error in Server: " + ioe);
  }  finally{
      try{
    	  clientToServerStream.close();
          serverToClientStream.close();   
        }catch(Exception e){
       	 System.out.println("StockQuoteServer: can't close streams" + e.getMessage());
        }
    }  
  }
 }

