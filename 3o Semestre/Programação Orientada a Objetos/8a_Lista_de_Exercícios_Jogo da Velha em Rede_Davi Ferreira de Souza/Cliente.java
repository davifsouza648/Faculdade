import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;        
import java.awt.event.*;  
import javax.swing.*;


public class Cliente
{
    private static JFrame mainFrame;



    //Cores
    private static Color backgroundColor = new Color(249, 249, 249); // #F9F9F9
    private static Color foregroundColor = new Color(51, 51, 102);   // #333366
    private static Color accentColor = new Color(255, 111, 97);      // #FF6F61
    private static Color accentHover = new Color(255, 211, 182);     // #FFD3B6
    private static Color inputBorderColor = new Color(204, 204, 204); // #CCCCCC
    private static Color secondaryButtonColor = new Color(108, 91, 123); // #6C5B7B
    private static Color inputBackground = new Color(255, 255, 255); // #FFFFFF
    private static Color formBackground = new Color(252, 238, 245);  // #FCEEF5
    private static Color highlightColor = new Color(255, 215, 0);    // #FFD700

    //Fonte
    private static Font fonte = new Font("Helvetica", Font.PLAIN, 16);

    //Rede
    private static Socket cliente;
    private static PrintWriter out;
    private static BufferedReader in;

    //Jogo
    private static int numJogador = 0;
    private static JButton[] botoes = new JButton[9];
    private static boolean minhaVez = false;



    public static void main(String[] args)
    {
        mainFrame = new JFrame("Jogo da velha");
        mainFrame.setSize(new Dimension(800,800));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuConexao();

        mainFrame.setVisible(true);
        
    }

    private static void menuConexao()
    {
        mainFrame.getContentPane().removeAll();

        JPanel painelPrincipal = new JPanel();
        JPanel painelTextFields = new JPanel(new GridBagLayout());

        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        //Declaração JLabel
        JLabel labelIp = new JLabel("Insira o IP do servidor: ");
        JLabel labelPort = new JLabel("Insira o port do servidor: ");

        //Declaração TextField
        JTextField fieldIp = new JTextField(15);
        JTextField fieldPort = new JTextField(15);

        //Declaração botão
        JButton botao = new JButton("Continuar");

        //Estilos

        JLabel[] labels = {labelIp, labelPort};
        JTextField[] fields = {fieldIp, fieldPort};

        for(JLabel j:labels)
        {
            j.setForeground(foregroundColor);
            j.setFont(fonte.deriveFont(Font.BOLD, 16));
            j.setPreferredSize(new Dimension(200, 80));
        }

        for(JTextField f:fields)
        {
            f.setForeground(foregroundColor);
            f.setBackground(inputBackground);
            f.setFont(fonte.deriveFont(Font.PLAIN, 24));
            f.setPreferredSize(new Dimension(200, 50));
            f.setBorder(BorderFactory.createLineBorder(inputBorderColor));

            f.addKeyListener(new KeyAdapter() 
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(!Character.isDigit(e.getKeyChar()) && !(e.getKeyChar() == '.'))
                    {
                        e.consume();
                    }
                }
            });
        }

        botao.setPreferredSize(new Dimension(200, 60));
        botao.setBackground(accentColor);
        botao.setForeground(Color.WHITE);
        botao.setFont(fonte.deriveFont(Font.BOLD, 18));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(highlightColor, 2));
        botao.setOpaque(true); 
        botao.setContentAreaFilled(true);

        painelPrincipal.setBackground(backgroundColor);
        mainFrame.setBackground(backgroundColor);
        painelTextFields.setBackground(backgroundColor);

        //Funcionalidades
        botao.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                botao.setBackground(accentHover);    
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                botao.setBackground(accentColor);
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(!fieldIp.getText().isEmpty() && !fieldPort.getText().isEmpty())
                {
                    try
                    {
                        cliente = new Socket(fieldIp.getText(), Integer.parseInt(fieldPort.getText()));
                        out = new PrintWriter(cliente.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    }
                    catch(Exception excep){JOptionPane.showMessageDialog(mainFrame, excep.getStackTrace(), excep.getMessage(),0);}
                    finally
                    {
                        String recebido;
                        try
                        {
                            recebido = in.readLine();
                            if(("START1").equals(recebido))
                            {
                                numJogador = 1;
                            }
                            else if(("START2").equals(recebido))
                            {
                                numJogador = 2;
                            }

                            menuJogo();
                            listenerServidor();
                        }catch(Exception excep){JOptionPane.showMessageDialog(mainFrame, excep.getStackTrace(), excep.getMessage(),0);}

                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(mainFrame, "Entrada inválida em um dos campos", "Entrada inválida", 0);
                }
            }
        });


        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;

        painelTextFields.add(labelIp, gbc);

        gbc.gridx++;

        painelTextFields.add(labelPort, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;

        painelTextFields.add(fieldIp, gbc);

        gbc.gridx++;

        painelTextFields.add(fieldPort, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);

        painelTextFields.add(botao, gbc);
    
        painelPrincipal.add(painelTextFields);

        mainFrame.add(painelPrincipal);

    }

    private static void menuJogo()
    {
        mainFrame.getContentPane().removeAll();
        mainFrame.setTitle("Jogador " + numJogador);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 3, 5, 5));



        for(int i = 0; i < 9; i++)
        {
            JButton botao = new JButton();
            final int index = i;

            botao.setFont(fonte);
            botao.setPreferredSize(new Dimension(80,80));
            
            botao.setBackground(accentColor);
            botao.setForeground(Color.WHITE);
            botao.setFont(fonte.deriveFont(Font.BOLD, 24));
            botao.setFocusPainted(false);
            botao.setBorder(BorderFactory.createLineBorder(highlightColor, 2));
            botao.setOpaque(true); 
            botao.setContentAreaFilled(true);
            
            botao.getModel().addChangeListener(e -> {
                ButtonModel model = botao.getModel();
                if (model.isRollover() && minhaVez && botao.getText().isEmpty()) {
                    botao.setBackground(accentHover);
                } else {
                    String text = botao.getText();
                    if ("X".equals(text)) {
                        botao.setBackground(new Color(100, 149, 237)); 
                    } else if ("O".equals(text)) {
                        botao.setBackground(new Color(152, 251, 152));
                    } else {
                        botao.setBackground(accentColor);
                    }
                }
            });

            botao.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e) 
                {
                    if (minhaVez && botao.getText().isEmpty()) 
                    {
                        out.println("JOGADA:" + index);
                    }
                }
            });

            botoes[i] = botao;
            painelBotoes.add(botao);

        }

        mainFrame.add(painelBotoes);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void listenerServidor()
    {
        new Thread(() -> 
        {
            try 
            {   
                String linha;
                while ((linha = in.readLine()) != null) 
                {
                    String[] parts = linha.split(":");
                    switch (parts[0]) 
                    {
                        case "ESTADO":
                            String grid = parts[1];
                            minhaVez = Integer.parseInt(parts[2]) == 1;
                            SwingUtilities.invokeLater(() -> updateBoard(grid, minhaVez));
                            break;
                        case "GAMEOVER":
                            System.out.println("DEBUG: Recebido GAMEOVER - " + linha); 
                            String finalGrid = parts[1];
                            String result = parts[2];
                            SwingUtilities.invokeLater(() -> {
                                updateBoard(finalGrid, false);
                                showGameOver(result);
                            });
                            break; 
                    }
                }
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }).start();
    }

    
    private static void updateBoard(String grid, boolean minhaVezAtual) 
    {
        for (int i = 0; i < 9; i++) 
        {
            char c = grid.charAt(i);
            botoes[i].setText(c == '-' ? "" : String.valueOf(c));
            botoes[i].setEnabled(minhaVezAtual && botoes[i].getText().isEmpty());

            if (botoes[i].getText().equals("X")) 
            {
                botoes[i].setBackground(new Color(100, 149, 237)); 
            } 
            else if (botoes[i].getText().equals("O")) 
            {
                botoes[i].setBackground(new Color(152, 251, 152)); 
            } 
            else 
            {
                botoes[i].setBackground(accentColor);
            }
        }


    }

    private static void setButtonsEnabled(boolean enabled) 
    {
        for (JButton b : botoes) 
        {
            b.setEnabled(enabled);
        }
    }

    private static void showGameOver(String result) 
    {
        String msg;
        if (result.equals("WIN" + numJogador)) msg = "Você venceu!";
        else if (result.equals("WIN" + (3 - numJogador))) msg = "Você perdeu!";
        else msg = "Empate!";
        JOptionPane.showMessageDialog(mainFrame, msg, msg, JOptionPane.PLAIN_MESSAGE);
        setButtonsEnabled(false);

        System.exit(0);
    }

}