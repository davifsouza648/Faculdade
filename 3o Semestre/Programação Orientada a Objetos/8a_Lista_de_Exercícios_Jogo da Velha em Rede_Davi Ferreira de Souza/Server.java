import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;        
import java.awt.event.*;  
import javax.swing.*;

class JogoVelha
{
    private char[] jogo = new char[9];
    private int jogadorAtual = 1;

    public JogoVelha()
    {
        for(int i = 0; i < 9; i++)
        {
            jogo[i] = '-';
        }
    }

    public synchronized boolean marcaPosicao(int index)
    {
        if(index < 0 || index >= 9 || jogo[index] != '-') return false;

        jogo[index] = (jogadorAtual == 1) ? 'X' : 'O';
        return true;
    }

    public synchronized void updatePlayer()
    {
        jogadorAtual = 3 - jogadorAtual;
    }

    public synchronized String getEstadoAtual()
    {
        return new String(jogo);
    }

    public synchronized int getJogadorAtual() 
    {
        return jogadorAtual;
    }

    public synchronized String checaVitoria() 
    {
        
        int[][] vitorias = 
        {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };
    
        for(int[] v : vitorias) 
        {
            if (jogo[v[0]] != '-' &&
                jogo[v[0]] == jogo[v[1]] &&
                jogo[v[1]] == jogo[v[2]]) 
            {
                return jogo[v[0]] == 'X' ? "WIN1" : "WIN2";
            }
        }
        
        for (char c : jogo) 
        {
            if (c == '-') return null;
        }
        return "DRAW";
    }
}


class EstadoJogo implements Runnable
{

    private Socket s1, s2;
    private PrintWriter o1, o2;
    private BufferedReader i1, i2;
    private JogoVelha jogo = new JogoVelha();

    public EstadoJogo(Socket c1, Socket c2) throws IOException
    {
        this.s1 = c1;
        this.s2 = c2;
        o1 = new PrintWriter(s1.getOutputStream(), true);
        o2 = new PrintWriter(s2.getOutputStream(), true);
        i1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        i2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
    }

    @Override
    public void run() 
    {
        try {
            o1.println("START1");
            o2.println("START2");
    
            Thread updateThread = new Thread(() -> 
            {
                try 
                {
                    while (jogo.checaVitoria() == null) 
                    {
                        String estado = jogo.getEstadoAtual();
                        int vez = jogo.getJogadorAtual();
                        
                        o1.println("ESTADO:" + estado + ":" + (vez == 1 ? 1 : 0));
                        o2.println("ESTADO:" + estado + ":" + (vez == 2 ? 1 : 0));
                        
                        Thread.sleep(1);
                    }
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            });
            updateThread.start();
    
            while(true) 
            {
                int vez = jogo.getJogadorAtual();
                BufferedReader in = (vez == 1) ? i1 : i2;
    
                String line = in.readLine();
                if (line == null || !line.startsWith("JOGADA:")) 
                {
                    break;
                }
    
                int posicaoJogada = Integer.parseInt(line.substring(7));
    
                if(jogo.marcaPosicao(posicaoJogada)) 
                {
                    String resultado = jogo.checaVitoria();
                    String estado = jogo.getEstadoAtual();
    
                    if (resultado != null) 
                    {
                        if (resultado != null) 
                        {
                            if (resultado != null) {

                                o1.println("GAMEOVER:" + estado + ":" + resultado);
                                o2.println("GAMEOVER:" + estado + ":" + resultado);
                                
                                try { Thread.sleep(100); } catch (InterruptedException e) {}
                                
                                // Fecha as conexões
                                o1.close(); o2.close();
                                i1.close(); i2.close();
                                s1.close(); s2.close();
                                
                                break;
                            }
                        }
                    }
    
                    jogo.updatePlayer();
                }
            }
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                i1.close(); o1.close(); s1.close();
                i2.close(); o2.close(); s2.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
}

public class Server 
{
    private ServerSocket server;

    public void start(int port) 
    {
        try {
            server = new ServerSocket(port);
            System.out.println("Servidor iniciado: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);

            while (true) {
                Socket cliente1 = server.accept();
                System.out.println("Cliente 1 conectado: " + cliente1.getRemoteSocketAddress());
                Socket cliente2 = server.accept();
                System.out.println("Cliente 2 conectado: " + cliente2.getRemoteSocketAddress());

                new Thread(new EstadoJogo(cliente1, cliente2)).start();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            stop();
        }
    }

    public void stop() {
        try 
        {
            if (server != null && !server.isClosed()) 
            {
                server.close();
                System.out.println("Servidor encerrado.");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int port;

        System.out.print("Insira o port do servidor: ");
        port = scan.nextInt();

        Server a = new Server();
        a.start(port);

        scan.close();
    }
}
