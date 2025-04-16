import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;


public class Ex1
{
    static JFrame mainFrame;
    static String nomeArquivo = "";
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->
        {
            
            mainFrame = new JFrame("Ex1");
            mainFrame.setSize(new Dimension(800,800));
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel painelPrincipal = new JPanel(new BorderLayout());
            
            JPanel painelBotoes = new JPanel();
            painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
    

            JButton botaoAbrir = new JButton("Abrir");
            JButton botaoSalvar = new JButton("Salvar");
            JButton botaoSalvarComo = new JButton("Salvar como");
            JButton botaoSair = new JButton("Sair");

            JTextArea fieldTexto = new JTextArea(30,20);

            botaoAbrir.setMaximumSize(new Dimension(200,50));
            botaoSalvar.setMaximumSize(new Dimension(200,50));
            botaoSalvarComo.setMaximumSize(new Dimension(200,50));
            botaoSair.setMaximumSize(new Dimension(200,50));

            painelBotoes.add(botaoAbrir);
            painelBotoes.add(botaoSalvar);
            painelBotoes.add(botaoSalvarComo);
            painelBotoes.add(botaoSair);

            //Função botões:
            botaoAbrir.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    nomeArquivo = JOptionPane.showInputDialog("Insira o nome do arquivo: ");
                    StringBuffer txt = new StringBuffer();
                    try {
                        FileInputStream in = new FileInputStream(nomeArquivo);
                        Scanner sin = new Scanner(in);
                        while (sin.hasNextLine()) 
                        {
                            txt.append(sin.nextLine()).append("\n");
                        }
                        in.close();

                        fieldTexto.setText(txt.toString());
                    }
                    catch (FileNotFoundException ex) 
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Erro: Arquivo não encontrado.");
                    } 
                    catch (IOException ex) 
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Erro ao ler o arquivo: " + ex.getMessage());
                    }


                }
                
            });

            botaoSalvar.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(nomeArquivo == "")
                    {
                        nomeArquivo = JOptionPane.showInputDialog("Insira o nome do arquivo: ");
                    }

                    
                    String texto = fieldTexto.getText();
                    
                    try
                    {
                        FileOutputStream out = new FileOutputStream(nomeArquivo);
                        out.write(texto.getBytes("UTF-8"));
                        out.close();
                    
                    }
                    catch (FileNotFoundException ex) 
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Erro: Arquivo não encontrado.");
                    } 
                    catch (IOException ex) 
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Erro ao ler o arquivo: " + ex.getMessage());
                    }

                }
            });

            botaoSalvarComo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e)
                {

                    nomeArquivo = JOptionPane.showInputDialog("Insira o nome do arquivo: ");
                           
                    String texto = fieldTexto.getText();
                    
                    try
                    {
                        FileOutputStream out = new FileOutputStream(nomeArquivo);
                        out.write(texto.getBytes("UTF-8"));
                        out.close();
                    
                    }
                    catch (FileNotFoundException ex) 
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Erro: Arquivo não encontrado.");
                    } 
                    catch (IOException ex) 
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Erro ao ler o arquivo: " + ex.getMessage());
                    }

                }
            });

            botaoSair.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    System.exit(0);
                }
            });

            

            painelPrincipal.add(painelBotoes, BorderLayout.LINE_START);
            painelPrincipal.add(fieldTexto, BorderLayout.CENTER);

            mainFrame.add(painelPrincipal);
            mainFrame.setVisible(true);

        });

        
    }
}
