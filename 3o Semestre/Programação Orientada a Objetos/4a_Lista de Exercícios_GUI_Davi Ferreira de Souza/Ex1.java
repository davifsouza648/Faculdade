import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Ex1
{
    static JFrame mainFrame;
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

            painelBotoes.add(botaoAbrir);
            painelBotoes.add(botaoSalvar);
            painelBotoes.add(botaoSalvarComo);
            painelBotoes.add(botaoSair);

            JTextArea fieldTexto = new JTextArea(30,20);

            painelPrincipal.add(painelBotoes, BorderLayout.LINE_START);
            painelPrincipal.add(fieldTexto, BorderLayout.CENTER);

            mainFrame.add(painelPrincipal);
            mainFrame.setVisible(true);

        });

        
    }
}
