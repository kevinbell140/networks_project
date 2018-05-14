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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class for Server
 */
public class Main {
    
    /**
     * Launches a concurrent server to respond to client requests 
     * @param args 
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(9090);
        System.out.println("Server Launched.");      
        while(true){
            Socket socket = server.accept();          
            serverThread st = new serverThread(socket);
            st.start();
            System.out.println("Thread started");
        }//end while
    }//end main()
}//end class
