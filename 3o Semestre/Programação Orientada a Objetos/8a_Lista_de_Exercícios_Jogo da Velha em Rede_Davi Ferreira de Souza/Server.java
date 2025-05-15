import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;        
import java.awt.event.*;  
import javax.swing.*;


public class Server 
{
private ServerSocket server;
    private Socket cliente1;
    private Socket cliente2;

    private PrintWriter out1;
    private BufferedReader in1;
    private PrintWriter out2;
    private BufferedReader in2;

    

    public void start(int port)
    {
        try
        {
            server = new ServerSocket(port);
            
            cliente1 = server.accept();
            out1 = new PrintWriter(cliente1.getOutputStream(), true);
            in1 = new BufferedReader(new InputStreamReader(cliente1.getInputStream()));
            
            cliente2 = server.accept();
            out2 = new PrintWriter(cliente2.getOutputStream(), true);
            in2 = new BufferedReader(new InputStreamReader(cliente2.getInputStream()));

            out1.println("START");
            out2.println("START");


        }catch(Exception e){e.printStackTrace();}


    }

    public void stop() 
    {
        try
        {
            in1.close();
            out1.close();
            cliente1.close();
            in2.close();
            out2.close();
            cliente2.close();
            server.close();
        }catch(Exception e){e.printStackTrace();}
    }
    
    public static void main(String[] args)
    {
        Server a = new Server();
        a.start(1234);
    }
}
