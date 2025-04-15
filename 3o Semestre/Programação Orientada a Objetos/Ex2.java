import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


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

            botaoSoma.setMaximumSize(new Dimension(100,30));
            botaoSubtrai.setMaximumSize(new Dimension(100,30));
            botaoMultiplica.setMaximumSize(new Dimension(100,30));
            botaoDivide.setMaximumSize(new Dimension(100,30));

            JLabel label = new JLabel(" = 0");

            JPanel painelPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JPanel painelBotoes = new JPanel();
            painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

            textField1.addKeyListener(new KeyListener() 
            {
                @Override
                public void keyPressed(KeyEvent k){}

                @Override
                public void keyTyped(KeyEvent k)
                {
                    if(!Character.isDigit(k.getKeyChar()))
                    {
                        k.consume();
                    }
                }

                @Override
                public void keyReleased(KeyEvent k){}
            });

            textField2.addKeyListener(new KeyListener() 
            {
                @Override
                public void keyPressed(KeyEvent k){}

                @Override
                public void keyTyped(KeyEvent k)
                {
                    if(!Character.isDigit(k.getKeyChar()))
                    {
                        k.consume();
                    }
                }

                @Override
                public void keyReleased(KeyEvent k){}
            });

            botaoSoma.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    label.setText(" = " + String.valueOf(Integer.parseInt(textField1.getText()) + Integer.parseInt(textField2.getText())));
                    label.revalidate();
                    label.repaint();
                }

                @Override
                public void mousePressed(MouseEvent e){}

                @Override
                public void mouseExited(MouseEvent e){}

                @Override
                public void mouseReleased(MouseEvent e){}

                @Override
                public void mouseEntered(MouseEvent e){}
            });

            botaoSubtrai.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    label.setText(" = " + String.valueOf(Integer.parseInt(textField1.getText()) - Integer.parseInt(textField2.getText())));
                    label.revalidate();
                    label.repaint();
                }

                @Override
                public void mousePressed(MouseEvent e){}

                @Override
                public void mouseExited(MouseEvent e){}

                @Override
                public void mouseReleased(MouseEvent e){}

                @Override
                public void mouseEntered(MouseEvent e){}
            });

            botaoMultiplica.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    label.setText(" = " + String.valueOf(Integer.parseInt(textField1.getText()) * Integer.parseInt(textField2.getText())));
                    label.revalidate();
                    label.repaint();
                }

                @Override
                public void mousePressed(MouseEvent e){}

                @Override
                public void mouseExited(MouseEvent e){}

                @Override
                public void mouseReleased(MouseEvent e){}

                @Override
                public void mouseEntered(MouseEvent e){}
            });

            botaoDivide.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    label.setText(" = " + String.valueOf(Integer.parseInt(textField1.getText()) / Integer.parseInt(textField2.getText())));
                    label.revalidate();
                    label.repaint();
                }

                @Override
                public void mousePressed(MouseEvent e){}

                @Override
                public void mouseExited(MouseEvent e){}

                @Override
                public void mouseReleased(MouseEvent e){}

                @Override
                public void mouseEntered(MouseEvent e){}
            });

            

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
