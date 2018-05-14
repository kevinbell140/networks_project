/*
 * Computer Networks and Distributed Processing
 * Tyrone Arthurs, Kevin Bell, Andrew Greer, Campbell Metz, Thorn Prescott, Tim Tuite
 * Program will create a server with a menu system
 * Light and Heavy loads and calculates the response time
 * Program also uses Linux commands to find the Date, Uptime, Memory, Netstat, Current Users, and Uptime
 * We will be using servers 111 and 112
 * Client will be on one of the IP addresses and the server will be on another.
 * Server must be launched first, then the client using java -jar P1Server.jar for the server 
 * Client must be launched next on the other IP address using java -jar NetworksP1.jar 198.168.100.111 to connect to the ip address
 * We will use SSH and Linux on a Cisco VPN. Virtual Private Network
 * A virtual private network (VPN) is a network that is constructed using public wires — usually the Internet — to connect to a private network
 */
package p2server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Defines a thread for the server
 */
public class serverThread extends Thread{
    Socket socket;
    BufferedReader fromClient;
    PrintStream toClient;
    
    
    /**
     * Constructor for the server thread
     * @param socket The socket for the server thread
     */
    public serverThread(Socket socket){
        this.socket = socket; 
    }//end constuctor
    
    
    /**
     * Runs the server thread
     */
    @Override
    public void run(){
                      
            fromClient = null;
            toClient = null;
            
            //establish connection with client
            try{
                fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                toClient = new PrintStream(socket.getOutputStream());
                System.out.println("Establishing new connection");
                
                //while connected to client
                while(socket != null && fromClient != null && toClient != null){
                    
                    String request;
                    //serve client request
                    switch(request = fromClient.readLine()){
                        case "1":
                            toClient.println(getCmd("date"));
                            break;          
                        case "2": 
                            toClient.println(getCmd("uptime"));
                            break;
                        case "3":
                            toClient.println(getCmd("free -m"));
                            break;
                        case "4": 
                            toClient.println(getCmd("netstat"));
                            break;
                            
                        case "5": 
                            toClient.println(getCmd("w"));
                            break;
                        case "6":
                            toClient.println(getCmd("ps -aux"));
                            break;
                            
                        case "7":
                            toClient.println("Quit");
                            socket.close();
                            fromClient.close();
                            toClient.close();
                            break;
                        }//end switch                        
                }//end while
            }catch (SocketException e){
                System.out.println("Problem connecting");
            }catch (IOException e){
                System.out.println("Connection Terminated");
            }//end try
    }//end go()
    
    /**
     * Executes requested command
     * @param cmd The requested command
     * @return The command output
     * @throws IOException 
     */
    private String getCmd(String cmd){
            
        String reply = "";
            
        try{
            Process commander = Runtime.getRuntime().exec(cmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(commander.getInputStream()));
                        
            reply = stdInput.readLine();
            reply += ("\n");
            
            String nextLine = null;
            
            while((nextLine = stdInput.readLine()) != null){
                reply += nextLine;
                reply += ("\n");
            }//end while
            
            stdInput.close();
        }catch (Exception e){
                e.printStackTrace(System.err);
        }//end try
        
        return reply;
    }//end getCmd()    
}//end class
    
