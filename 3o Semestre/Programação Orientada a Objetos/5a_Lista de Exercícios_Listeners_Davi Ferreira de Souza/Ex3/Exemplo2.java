import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;

// Este exemplo demonstra o uso de KeyListeners para restringir a entrada do usuário em campos de texto (JTextField).
// São apresentados três campos: um que aceita apenas letras, outro que aceita apenas números e um terceiro que aceita apenas símbolos (excluindo letras e números).
// Esse tipo de validação é muito útil em formulários de cadastro ou entradas controladas, garantindo que o usuário digite apenas o tipo de dado esperado.


public class Exemplo2 
{
    
    static JFrame mainFrame;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->
        {
            mainFrame = new JFrame("Exemplo 2 - Ex3");
            mainFrame.setSize(new Dimension(800,400));
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            JPanel painelPrincipal = new JPanel();
            painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

            JLabel labelLetra = new JLabel("Essa TextField só aceita letras: ");
            JLabel labelNumero = new JLabel("Essa TextField só aceita números: ");
            JLabel labelSimbolos = new JLabel("Essa TextField só aceita caracteres que não são letras ou números");

            JTextField textFieldLetra = new JTextField(10);
            JTextField textFieldNumero = new JTextField(10);
            JTextField textFieldSimbolos = new JTextField(10);

            textFieldLetra.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(!Character.isLetter(e.getKeyChar()))
                    {
                        e.consume();
                    }
                }
            });
            
            textFieldNumero.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(!Character.isDigit(e.getKeyChar()))
                    {
                        e.consume();
                    }
                }
            });

            textFieldSimbolos.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(Character.isDigit(e.getKeyChar()) || Character.isLetter(e.getKeyChar()))
                    {
                        e.consume();
                    }
                }
            });

            painelPrincipal.add(labelLetra);
            painelPrincipal.add(textFieldLetra);
            painelPrincipal.add(labelNumero);
            painelPrincipal.add(textFieldNumero);
            painelPrincipal.add(labelSimbolos);
            painelPrincipal.add(textFieldSimbolos);

            mainFrame.add(painelPrincipal);

            mainFrame.setVisible(true);


            



        });
    }
}
