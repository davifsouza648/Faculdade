import javax.swing.*;
import java.awt.*;


public class Ex2 
{
    static JFrame mainFrame;
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->
        {
            mainFrame = new JFrame("Ex2");
            mainFrame.setSize(new Dimension(600,150));
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextField textField1 = new JTextField(15);
            JTextField textField2 = new JTextField(15);

            JButton botaoSoma = new JButton("Soma");
            JButton botaoSubtrai = new JButton("Subtrai");
            JButton botaoMultiplica = new JButton("Multiplica");
            JButton botaoDivide = new JButton("Divide");

            JLabel label = new JLabel(" = 0");

            JPanel painelPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JPanel painelBotoes = new JPanel();
            painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

            

            painelBotoes.add(botaoSoma);
            painelBotoes.add(botaoSubtrai);
            painelBotoes.add(botaoMultiplica);
            painelBotoes.add(botaoDivide);

            painelPrincipal.add(textField1);
            painelPrincipal.add(painelBotoes);
            painelPrincipal.add(textField2);
            painelPrincipal.add(label);


            mainFrame.add(painelPrincipal);

            mainFrame.setVisible(true);

        });
    }
}
