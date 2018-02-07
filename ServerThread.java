/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Gustav
 */
public class ServerThread implements Runnable{
       
    // StrÃ¶mmar fÃ¶r att lÃ¤sa/skriva
    private PrintWriter out;
    private BufferedReader in;

    // texten som lÃ¤ses in/skickas tillbaka
    private String echo;

    // Sockets till uppkopplingen
    private ServerSocket serverSocket;
    private Socket clientSocket = null;
    
    public ServerThread(){
        
    }
    
    public void run(){
        // Koppla upp serverns socket
	try {
	    serverSocket = new ServerSocket(4444);
	} catch (IOException e) {
	    System.out.println("Could not listen on port: 4443");
	    System.exit(-1);
	}
	
	// Lyssna efter en klient
	try {
	    clientSocket = serverSocket.accept();
	} catch (IOException e) {
	    System.out.println("Accept failed: 4444");
	    System.exit(-1);
	}

	// Anslut till klienten
	try{
	    out = new PrintWriter(
				  clientSocket.getOutputStream(), true);
	}catch(IOException e){
	    System.out.println("getOutputStream failed: " + e);
	    System.exit(1);
	}

	try{
	    in = new BufferedReader(new InputStreamReader(
	            clientSocket.getInputStream()));
	}catch(IOException e){
	    System.out.println("getInputStream failed: " + e);
	    System.exit(1);
	}

	// Kommer vi hit har det gÃ¥tt bra
	// Vi skriver ut IP-adressen till klienten
	System.out.println("Connection Established: " 
			   + clientSocket.getInetAddress());

	// LÃ¤s frÃ¥n klienten och skicka tillbaka 
	// medelandet i versaler tills klienten
	// kopplar ner
	while(true){
	    try{
		echo = in.readLine();
		if(echo==null){
		    System.out.println("Client disconnect!");
		    System.exit(1);
		}
		System.out.println("Recieved: " + echo);
		out.println(echo.toUpperCase());
	    }catch(IOException e){
		System.out.println("readLine failed: " + e);
		System.exit(1);
	    }
	}
    }
}
    
