import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;

// Utiliza um JComboBox para seleção de opções e um JButton para exibir a opção selecionada.
// O ActionListener do JComboBox atualiza a variável com a opção escolhida pelo usuário,
// enquanto o ActionListener do JButton exibe essa informação em uma caixa de diálogo.


public class Exemplo1 
{
    static JFrame mainFrame;
    static String opcaoSelecionada;
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(()->
        {
            mainFrame = new JFrame("Exemplo 1 - Ex3");
            mainFrame.setSize(new Dimension(800,800));
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String opcoes[] = {"Opcao 1","Opcao 2","Opcao 3","Opcao 4"};

            opcaoSelecionada = opcoes[0];


            JComboBox<String> caixaSelecao = new JComboBox<>(opcoes);
            JButton botao = new JButton("Mostra seleção");
            JPanel painelPrincipal = new JPanel(new FlowLayout());

            caixaSelecao.setSelectedIndex(0);

            caixaSelecao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    opcaoSelecionada = String.valueOf(caixaSelecao.getSelectedItem());
                }
            });

            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                JOptionPane.showMessageDialog(mainFrame, "Opção selecionada: " + opcaoSelecionada, "Função botão", 0);
                }
            });
            
            painelPrincipal.add(botao);
            painelPrincipal.add(caixaSelecao);

            mainFrame.add(painelPrincipal);
            mainFrame.setVisible(true);


            

        });
    }
}
