import javax.swing.*;
import java.awt.*;

class Main
{
    static private JFrame mainFrame;

    public static int direcao_x = 1;
    public static int direcao_y = 1;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> 
        {
            int width = 80;
            int height = 40;



            mainFrame = new JFrame("JUST EAT");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 800);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setLayout(null);
            mainFrame.getContentPane().setBackground(new Color(0,0,0));

            JButton botao = new JButton("Ok");
            botao.setLocation(new Point(100,350));
            botao.setSize(new Dimension(width, height));
            botao.setVisible(true);
            botao.setOpaque(true);

            botao.addActionListener(e->{System.exit(0);});

            new Thread(() -> 
            {
                try 
                {
                    while (true) 
                    {
                        Thread.sleep(10);

                        SwingUtilities.invokeLater(() -> 
                        {
                            int x = botao.getX();
                            int y = botao.getY();

                            if(x + width >= mainFrame.getContentPane().getWidth() || x <= 0) 
                            {
                                direcao_x *= -1;
                            }
                            if(y + height >= mainFrame.getContentPane().getHeight() || y <= 0) 
                            {
                                direcao_y *= -1;
                            }

                            botao.setLocation(x + 2 * direcao_x, y + 2 * direcao_y);

                            Color corAtual = botao.getBackground();
                            int r = (corAtual.getRed() + 1) % 256;
                            int g = (corAtual.getGreen() + 2) % 256;
                            int b = (corAtual.getBlue() + 3) % 256;

                            botao.setBackground(new Color(r, g, b));
                            botao.setForeground(new Color(r,g,b).brighter());
                            

                        });
                    }
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();


            mainFrame.add(botao);
            mainFrame.setVisible(true);
        });
            

        
    }
} 
