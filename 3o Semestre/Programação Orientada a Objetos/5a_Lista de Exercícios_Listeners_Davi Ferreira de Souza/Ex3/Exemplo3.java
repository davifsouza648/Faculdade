import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;

// Este exemplo demonstra o uso de MouseListener combinado com uma Thread para implementar a funcionalidade de arrastar (drag and drop) uma JLabel.
// Quando o usuário pressiona o botão do mouse sobre a label, uma thread é iniciada para atualizar continuamente sua posição conforme o mouse se move.
// A movimentação é interrompida quando o botão do mouse é liberado.
// Este exemplo mostra como capturar eventos de clique e movimento do mouse, além de integrar manipulação de interface com controle concorrente (thread).


public class Exemplo3 
{
    static JFrame mainFrame;
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->
        {
            mainFrame = new JFrame("Exemplo 3 - Ex3");
            mainFrame.setSize(new Dimension(800,800));
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel painelPrincipal = new JPanel(null);

            JLabel label = new JLabel("Me arraste");
            label.setBackground(new Color(0,0,0));
            label.setForeground(new Color(255,255,255));
            label.setBounds(200, 200, 100, 30);
            label.setOpaque(true);

            Point offset = new Point();

            label.addMouseListener(new MouseAdapter() 
            {
                boolean arrastando = false;
                public void mousePressed(MouseEvent e) 
                {
                    offset.setLocation(e.getPoint());
                    arrastando = true;
                    new Thread(() -> 
                    {
                        while (arrastando) 
                        {
                            try 
                            {
                                PointerInfo pi = MouseInfo.getPointerInfo();
                                Point p = pi.getLocation(); // posição global do mouse
                                SwingUtilities.convertPointFromScreen(p, painelPrincipal);

                                int newX = p.x - offset.x;
                                int newY = p.y - offset.y;

                                SwingUtilities.invokeLater(() -> 
                                {
                                    label.setLocation(newX, newY);
                                });

                                Thread.sleep(1);
                            } 
                            catch (InterruptedException ex) 
                            {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }

                public void mouseReleased(MouseEvent e) 
                {
                    arrastando = false;
                }
            });
        

            painelPrincipal.add(label);

            mainFrame.add(painelPrincipal);
            mainFrame.setVisible(true);

        });
    }
}
