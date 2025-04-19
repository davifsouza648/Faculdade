import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;

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

            botaoSoma.setMaximumSize(new Dimension(100,50));
            botaoSubtrai.setMaximumSize(new Dimension(100,50));
            botaoMultiplica.setMaximumSize(new Dimension(100,50));
            botaoDivide.setMaximumSize(new Dimension(100,50));

            JLabel label = new JLabel(" = 0");

            label.setMaximumSize(new Dimension(100,50));

            JPanel painelPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JPanel painelBotoes = new JPanel();
            painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

            textField1.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(Character.isLetter(e.getKeyChar()))
                    {
                        e.consume();
                    }
                }   
            });
            
            textField2.addKeyListener(new KeyAdapter() 
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(Character.isLetter(e.getKeyChar()))
                    {
                        e.consume();
                    }
                }   
            });

            botaoSoma.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(textField1.getText().isEmpty() || textField2.getText().isEmpty())
                    {
                        if(textField1.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                        if(textField2.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                    }
                    else
                    {
                        label.setText(" = " + String.valueOf(Double.parseDouble(textField1.getText()) + Double.parseDouble(textField2.getText())));
                        label.revalidate();
                        label.repaint();
                    }

                }
            });

            botaoSubtrai.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(textField1.getText().isEmpty() || textField2.getText().isEmpty())
                    {
                        if(textField1.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                        if(textField2.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                    }
                    else
                    {
                        label.setText(" = " + String.valueOf(Double.parseDouble(textField1.getText()) - Double.parseDouble(textField2.getText())));
                        label.revalidate();
                        label.repaint();
                    }
                }
            });

            botaoMultiplica.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(textField1.getText().isEmpty() || textField2.getText().isEmpty())
                    {
                        if(textField1.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                        if(textField2.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                    }
                    else
                    {
                        label.setText(" = " + String.valueOf(Double.parseDouble(textField1.getText()) * Double.parseDouble(textField2.getText())));
                        label.revalidate();
                        label.repaint();
                    }
                }
            });

            botaoDivide.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(textField1.getText().isEmpty() || textField2.getText().isEmpty())
                    {
                        if(textField1.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                        if(textField2.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Insira um número válido no input 1.", null, 0);
                        }
                        
                    }
                    else if(Double.parseDouble(textField2.getText()) == 0)
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Não é possível realizar divisões por zero.", null, 0);
                    }
                    else
                    {
                        label.setText(" = " + String.format("%.2f", Double.parseDouble(textField1.getText()) / Double.parseDouble(textField2.getText())));
                        label.revalidate();
                        label.repaint();
                    }
                }
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
