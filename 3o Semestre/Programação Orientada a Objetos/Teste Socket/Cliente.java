import java.net.*;
import java.io.*;
import java.util.*;

public class Cliente 
{
    private static Socket cliente;
    private static PrintWriter out;
    private static BufferedReader in;

    private void startConnection(String ip, int port)
    {
        try
        {
            cliente = new Socket(ip, port);
            out = new PrintWriter(cliente.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        }catch(Exception e){e.printStackTrace();}

    }

    public String sendMessage(String msg) 
    {
        out.println(msg);
        try
        {
            String resp = in.readLine();
            return resp;
        }catch(Exception e){e.printStackTrace();}

        return "";
    }

    
    public void stopConnection() 
    {
        try
        {
            in.close();
            out.close();
            cliente.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public static void main(String[] args)
    {
        Cliente c = new Cliente();
        Scanner scan = new Scanner(System.in);

        c.startConnection("localhost", 1234);
        while(scan.hasNext())
        {
            c.sendMessage(scan.nextLine());
        }
        c.stopConnection();

    }

}
