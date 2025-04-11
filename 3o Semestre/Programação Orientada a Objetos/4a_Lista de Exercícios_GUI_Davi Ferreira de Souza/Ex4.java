import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Ex4
{
    static JFrame mainFrame;
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->
        {
            mainFrame = new JFrame("Ex4");
            mainFrame.setSize(new Dimension(600,100));
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextField textField1 = new JTextField(15);
            JTextField textField2 = new JTextField(15);

            String[] opcoes = {"Soma", "Subtrai", "Multiplica", "Divide"};
            JComboBox caixaSelecao = new JComboBox<>(opcoes);
            caixaSelecao.setSelectedIndex(0);

            JLabel label = new JLabel(" = 0");

            JButton botaoCalcular = new JButton("Calcular");
            
            JPanel painelPrincipal = new JPanel(new BorderLayout());
            JPanel painelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));


            painelBotao.add(botaoCalcular, BorderLayout.CENTER);

            painelEntrada.add(textField1);
            painelEntrada.add(caixaSelecao);
            painelEntrada.add(textField2);
            painelEntrada.add(label);


            painelPrincipal.add(painelEntrada, BorderLayout.NORTH);
            painelPrincipal.add(painelBotao, BorderLayout.CENTER);

            mainFrame.add(painelPrincipal);

            mainFrame.setVisible(true);

        });
    }
}
