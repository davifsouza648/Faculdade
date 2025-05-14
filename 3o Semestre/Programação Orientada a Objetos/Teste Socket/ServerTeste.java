import java.net.*;
import java.io.*;

public class ServerTeste {
    private ServerSocket server;
    private Socket cliente;

    private PrintWriter out;
    private BufferedReader in;

    public void start(int port)
    {
        try
        {
            server = new ServerSocket(port);
            cliente = server.accept();
            out = new PrintWriter(cliente.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        }catch(Exception e){e.printStackTrace();}

        String a;
        try
        {
            while ((a = in.readLine()) != null) {
                System.out.println("Cliente: " + a);
                out.println("Servidor recebeu: " + a); 
            }
        }catch(Exception e){e.printStackTrace();}



    }

    public void stop() {
        try
        {
            in.close();
            out.close();
            cliente.close();
            server.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public static void main(String[] args)
    {
        ServerTeste a = new ServerTeste();
        a.start(1234);
    }
}
