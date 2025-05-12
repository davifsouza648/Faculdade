import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;


class RodaCacaNiquel implements Runnable
{
    private JTextField textField;
    
    public RodaCacaNiquel(JTextField textField)
    {
        this.textField = textField;
    }

    @Override
    public void run()
    {

        int numeroDeGiros = (int)(Math.random() * 151) + 1;

        for(int i = 0; i < numeroDeGiros; i++)
        {
            SwingUtilities.invokeLater(()->
            {
                textField.setEnabled(true);
                textField.setText(String.valueOf((int)(Math.random() * 8)));
                textField.setEnabled(false);
            });

            try 
            {
                Thread.sleep(50);  
            } 
            catch (InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
        }
        
    }
}

public class Ex7 {

    static JFrame mainFrame;
    public static void main(String[] args)
    {
        
        mainFrame = new JFrame("Caça Niquel");
        mainFrame.setSize(new Dimension(450, 450));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        
        //Declaração painéis
        JPanel painelNumeros = new JPanel();
        painelNumeros.setLayout(new BoxLayout(painelNumeros, BoxLayout.X_AXIS));
        painelNumeros.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        //Declaração JTextFields
        Dimension fieldSize = new Dimension(100, 100);
        Font fieldFont = new Font("SansSerif", Font.BOLD, 36);
        JTextField num1 = new JTextField(2);
        JTextField num2 = new JTextField(2);
        JTextField num3 = new JTextField(2);




        //Declaração botão

        JButton botao = new JButton("Jogar");
        


        //Estilos

        num1.setEnabled(false);
        num2.setEnabled(false);
        num3.setEnabled(false);

        for (JTextField num : new JTextField[]{num1, num2, num3}) 
        {
            num.setPreferredSize(fieldSize);
            num.setMaximumSize(fieldSize);
            num.setHorizontalAlignment(JTextField.CENTER);
            num.setFont(fieldFont);
            num.setEnabled(false);
        }

        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setPreferredSize(new Dimension(200, 60));
        botao.setMaximumSize(new Dimension(200, 60));
        botao.setFont(new Font("SansSerif", Font.BOLD, 18));


        botao.setMaximumSize(new Dimension(300,100));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                Thread t1 = new Thread(new RodaCacaNiquel(num1));
                Thread t2 = new Thread(new RodaCacaNiquel(num2));
                Thread t3 = new Thread(new RodaCacaNiquel(num3));
                
                
                t1.start(); 
                t2.start(); 
                t3.start();

                new Thread(() -> 
                {
                    try 
                    {
                        t1.join(); 
                        t2.join(); 
                        t3.join();
                    } 
                    catch (InterruptedException ex) 
                    {
                        Thread.currentThread().interrupt();
                    }
                    // agendar o diálogo na EDT
                    SwingUtilities.invokeLater(() -> 
                    {
                        if (num1.getText().equals(num2.getText())
                            && num1.getText().equals(num3.getText())) 
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Você venceu!", "Venceu!", 1);
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Você perdeu.", "Perdeu.", 1);
                        }
                    });
                }).start();

            }
        });

    
        painelNumeros.add(num1);
        painelNumeros.add(Box.createRigidArea(new Dimension(20, 0)));
        painelNumeros.add(num2);
        painelNumeros.add(Box.createRigidArea(new Dimension(20, 0)));
        painelNumeros.add(num3);


        mainFrame.add(painelNumeros);
        mainFrame.add(Box.createRigidArea(new Dimension(0, 100)));
        mainFrame.add(botao);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }
}
