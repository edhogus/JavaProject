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
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Gustav
 */
public class ClientThread implements Runnable{
    
    public ClientThread(){
        
    }
    
    public void run(){
        // Socket som ansluter till servern
        Socket clientSocket = null;

	// StrÃ¶mmar fÃ¶r att lÃ¤sa frÃ¥n/skriva till servern
        PrintWriter out = null;
        BufferedReader in = null;

	// StrÃ¶m fÃ¶r att lÃ¤sa frÃ¥n terminalen
	BufferedReader stdIn;
	String userInput;

	// Ã„ndra nedanstÃ¥ende rad till lÃ¤mplig serveradress
	String hostAddress = "130.229.177.60"; //denna adress ändras från mellan olika nätverk, hårdkoda ej!

	// Anslut till server:
        try {
            clientSocket = new Socket(hostAddress, 4444);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host.\n" + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to host.\n" + e);
            System.exit(1);
        }

	// Kommer vi hit har anslutningen gÃ¥tt bra
	System.out.println("Connection successful!");

	// Anslut stdIn till terminalen
	stdIn = new BufferedReader(new InputStreamReader(System.in));
                                   
	// LÃ¤s in frÃ¥n terminalen och skicka till servern:
        try{
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (IOException e) {
            
        };

	// Hit kommer vi troligtvis aldrig,
	// men sÃ¥ hÃ¤r stÃ¤nger man alla inblandade strÃ¶mmar
	out.close();
        try{
            in.close();
            stdIn.close();
            clientSocket.close();
        } catch (IOException e) {
            
        }
    }
    
}
