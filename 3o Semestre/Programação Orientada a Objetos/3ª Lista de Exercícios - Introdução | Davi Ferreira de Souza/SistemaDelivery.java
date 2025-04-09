import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;     
import java.awt.*;        
import java.awt.event.*;  
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.OptionPaneUI;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;;

//Modelos
abstract class Usuario
{
    protected String nome;
    protected int id;
    protected String email;

}

class Cliente extends Usuario 
{
    private String nome;
    private int id;
    private String email;

    public Cliente(String nome, int id, String email) 
    {
        this.nome = nome;
        this.id = id;
        this.email = email;
    }

    public String getNome() { return this.nome; }
    public int getId() { return this.id; }
    public String getEmail() { return this.email; }


}

class Entregador extends Usuario
{
    private String meioDeTransporte;
    private boolean statusDisponivel;

    public Entregador(String nome, int id, String email, String meioDeTransporte)
    {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.meioDeTransporte = meioDeTransporte;
        this.statusDisponivel = true;
    }

    public int getId(){return this.id;}
    public String getNome(){return this.nome;}
    public String getEmail(){return this.email;}
    public String getMeioDeTransporte(){return this.meioDeTransporte;}
    public boolean getStatusDisponivel(){return this.statusDisponivel;}
    public void setStatusDisponivel(){this.statusDisponivel = !this.statusDisponivel;}
    public int getID(){return this.id;}

}

class Item 
{
    public double valor;
    public String nome;

    public Item(String nome, double valor)
    {
        this.valor = valor;
        this.nome = nome;
    }

    public String getNome(){return this.nome;}
    public double getValor(){return this.valor;}
}

class Restaurante extends Usuario
{
    private String nomeFantasia;
    private ArrayList<Item> cardapio = new ArrayList<>();
    double precoMedio;

    Restaurante(String nome, int id, String email, String nomeFantasia, ArrayList<Item> cardapio) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.nomeFantasia = nomeFantasia;
        this.cardapio = new ArrayList<>();
        for (Item item : cardapio) {
            this.cardapio.add(new Item(item.getNome(), item.getValor()));
        }
        this.precoMedio = (this.cardapio.stream().mapToDouble(Item::getValor).sum()) / (this.cardapio.size());
    }
    @Override
    public String toString() 
    {
        return "Nome: " + nomeFantasia + "\nID: " + id + "\nEmail: " + email + "\nPreço médio: R$ " + String.format("%.2f", this.precoMedio);
    }

    public int getID(){return this.id;}
    public String getEmail(){return this.email;}
    public String getNomeComercial(){return this.nomeFantasia;}
    public ArrayList<Item> getCardapio(){return this.cardapio;}
    public String getPrecoMedio() {return String.format("%.2f", this.precoMedio);}

}

class Pedido
{
    int numeroPedido;
    Cliente cliente;
    Restaurante restaurante;
    List<Item> itens;
    double valorTotal;
    Entregador entregador;

    enum Status
    {
        REALIZADO, 
        EM_PREPARO,
        PREPARADO, 
        EM_ENTREGA, 
        ENTREGUE
    }

    Status status;

    Pedido(int numeroPedido, Cliente cliente, Restaurante restaurante, List<Item> itens, double valorTotal)
    {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = Status.REALIZADO;
        this.entregador = null;
    }



    public boolean atualizarStatus()
    {
        switch(this.status)
        {
            case REALIZADO:
                this.status = Status.EM_PREPARO;
                return false;
            case EM_PREPARO:
                this.status = Status.PREPARADO;
                return false;
            case PREPARADO:
                this.status = Status.EM_ENTREGA;
                return false;
            case EM_ENTREGA:
                this.status = Status.ENTREGUE;
                return true;
        }

        return false;
    }
    
    public void atribuirEntregador(Entregador entregador)
    {
        this.entregador = entregador;
    }

    public Cliente getCliente(){return this.cliente;}
    public Restaurante getRestaurante(){return this.restaurante;}
    public List<Item> getItens(){return this.itens;}
    public Entregador getEntregador(){return this.entregador;}
    public double getValorTotal(){return this.valorTotal;}
    public int getNumeroPedido(){return this.numeroPedido;}
    public String getStatus()
    {
        switch(this.status)
        {
            case REALIZADO: return "Realizado";
            case EM_PREPARO: return "Em preparo";
            case PREPARADO: return "Preparado";
            case EM_ENTREGA: return "Em entrega";
            case ENTREGUE: return "Entregue";
        }

        return null;
    }

}

//Interface
class BotaoMenu extends JButton {
    // Cores para modo escuro
    private static final Color corFundoEscuro = new Color(255, 101, 0);       // Laranja
    private static final Color corHoverEscuro = new Color(255, 140, 66);      // Laranja claro
    private static final Color corPressionadoEscuro = new Color(204, 82, 0);  // Laranja mais escuro
    private static final Color corTextoEscuro = new Color(224, 224, 224);     // Branco

    // Cores para modo claro
    private static final Color corFundoClaro = new Color(255, 101, 0);        // Laranja
    private static final Color corHoverClaro = new Color(255, 140, 66);       // Laranja claro
    private static final Color corPressionadoClaro = new Color(204, 82, 0);    // Laranja mais escuro
    private static final Color corTextoClaro = new Color(33, 37, 41);         // Preto

    private static final Font fonte = new Font("Helvetica", Font.BOLD, 14);
    
    private boolean modoEscuro;

    public BotaoMenu(String texto, boolean modoEscuro) 
    {
        super(texto);
        this.modoEscuro = modoEscuro;
        configurarEstilo();
        adicionarEfeitoHover();
    }

    private void configurarEstilo() 
    {
        this.setFocusPainted(false);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setMaximumSize(new Dimension(250, 40));
        this.setFont(fonte);
        this.setForeground(modoEscuro ? corTextoEscuro : corTextoClaro);
        this.setBackground(modoEscuro ? corFundoEscuro : corFundoClaro);
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(modoEscuro ? corHoverEscuro : corHoverClaro, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15) 
        ));
        this.setContentAreaFilled(false);
        this.setOpaque(true);
    }

    private void adicionarEfeitoHover() 
    {
        this.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                setBackground(modoEscuro ? corHoverEscuro : corHoverClaro);
            }
            
            @Override
            public void mouseExited(MouseEvent e) 
            {
                setBackground(modoEscuro ? corFundoEscuro : corFundoClaro);
            }
            
            @Override
            public void mousePressed(MouseEvent e) 
            {
                setBackground(modoEscuro ? corPressionadoEscuro : corPressionadoClaro);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                setBackground(modoEscuro ? corHoverEscuro : corHoverClaro);
            }
        });
    }

    public void setModoEscuro(boolean modoEscuro) 
    {
        this.modoEscuro = modoEscuro;
        configurarEstilo();
    }
}

class Footer extends JPanel
{
    public Footer(Color corBackground, Color texto, Font fonte)
    {
        super(new BorderLayout());
        this.setBackground(corBackground);

        JLabel textoCopyright = new JLabel("© 2025 Just Eat - Sistema de Delivery. " +
        "Todos os direitos reservados. " +
        "Desenvolvido por Davi Ferreira de Souza");
        textoCopyright.setFont(fonte);
        textoCopyright.setForeground(texto);
        textoCopyright.setBackground(corBackground);
        textoCopyright.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 0));


        this.add(textoCopyright, BorderLayout.LINE_START);
        
    }
}


class Header extends JPanel
{
    public Header(Color corBackGround)
    {
        super(new FlowLayout(FlowLayout.LEFT));

        // Usando getResource para carregar a imagem do classpath
        ImageIcon originalIcon = new ImageIcon("Assets/logo.png");
        
        // Ajustando o tamanho da imagem
        this.add(new JLabel(new ImageIcon(originalIcon.getImage()
            .getScaledInstance(100, 100, Image.SCALE_SMOOTH))));

        // Configurando o painel
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(corBackGround);
    }
}

class LabelMenu extends JLabel
{
    //Cores de texto
    static private Color textosPrincipalEscuro = new Color(255, 191, 128);
    static private Color textosPrincipaisClaro = new Color(255, 102, 0);
    
    private final Font fonte = new Font("Helvetica", Font.BOLD, 14);
    public LabelMenu(String texto, boolean darkMode)
    {
        super(texto);
        Color corTexto = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        this.setFont(fonte);
        this.setForeground(corTexto);
    }
}

//Gerenciadores
class GerenciadorClientes {
    private ArrayList<Cliente> listaClientes = new ArrayList<>();

    public boolean clienteExiste(Cliente novoCliente) 
    {
        for (Cliente c : listaClientes) 
        {
            if (c.getId() == novoCliente.getId()) 
            {
                return true; 
            }
        }
        return false;
    }

    public boolean adicionarCliente(Cliente novoCliente) 
    {
        if (!clienteExiste(novoCliente)) 
        {
            listaClientes.add(novoCliente);
            return true; 
        }
        return false; 
    }

    public ArrayList<Cliente> getListaClientes(){return this.listaClientes;}
    public Cliente getClienteIndex(int index){return this.listaClientes.get(index);}
}

class GerenciadorRestaurantes 
{
    private ArrayList<Restaurante> listaRestaurantes = new ArrayList<>();

    public boolean restauranteExiste(Restaurante r) 
    {
        for(Restaurante restaurante : listaRestaurantes) 
        {
            if(restaurante.getID() == r.getID()) 
            {
                return true;
            }
        }
        return false;
    }

    public boolean adicionaRestaurante(Restaurante r) 
    {
        listaRestaurantes.add(r);
        return true;
    }

    public ArrayList<Restaurante> getListaRestaurantes(){return this.listaRestaurantes;}
    public Restaurante getRestauranteIndex(int index){return this.listaRestaurantes.get(index);}
}

class GerenciadorEntregadores
{
    private ArrayList<Entregador> listaEntregadores = new ArrayList<>();

    public boolean entregadorExiste(Entregador e)
    {
        for(Entregador entregador : listaEntregadores)
        {
            if(entregador.getID() == e.getID()) return true;
        }

        return false;
    }

    public boolean adicionaEntregador(Entregador e)
    {
        if(!entregadorExiste(e))
        {
            listaEntregadores.add(e);
            return true;
        }

        return false;

    }

    public ArrayList<Entregador> getListaEntregadores(){return this.listaEntregadores;}
}

class GerenciadorPedidos
{
    private ArrayList<Pedido> pedidos = new ArrayList<>();

    public boolean pedidoExiste(Pedido p)
    {
        for(Pedido pedido : pedidos)
        {
            if(pedido.getNumeroPedido() == p.getNumeroPedido()) return true;
        }

        return false;
    }

    public boolean adicionaPedido(Pedido p)
    {
        if(!pedidoExiste(p))
        {
            pedidos.add(p);
            return true;
        }

        return false;
    }

    public Pedido getPedidoIndex(int index)
    {
        return this.pedidos.get(index);
    }

    public ArrayList<Pedido> getListaPedidos(){return this.pedidos;}

}







class Main 
{

    private static GerenciadorClientes clientes = new GerenciadorClientes();
    private static GerenciadorEntregadores entregadores = new GerenciadorEntregadores();
    private static GerenciadorRestaurantes restaurantes = new GerenciadorRestaurantes();
    private static GerenciadorPedidos pedidos = new GerenciadorPedidos();

    private static int proximoCliente = 1;
    private static int proximoRestaurante = 1;
    private static int proximoEntregador = 1;
    private static int proximoPedido = 1;

    static private JFrame mainFrame;
    static private JLabel headerLabel;
    static private JLabel statusLabel;
    static private JPanel controlPanel;

    private static final Font fonte = new Font("Helvetica", Font.BOLD, 14);

    private static boolean darkMode = true;

    // Cores modo escuro
    static private Color fundoPrincipalEscuro = new Color(18, 18, 18);
    static private Color barraSuperioresInferioresEscuro = new Color(36, 36, 36);
    static private Color textosPrincipalEscuro = new Color(255, 191, 128); // Laranja suave
    static private Color textosSecundariosEscuro = new Color(255, 143, 64); // Laranja mais forte

    // Cores modo claro
    static private Color fundoPrincipalClaro = new Color(220, 220, 220);
    static private Color barrasSuperioresInferioresClaro = new Color(230, 230, 230);
    static private Color textosPrincipaisClaro = new Color(255, 102, 0); // Laranja vibrante
    static private Color textosSecundariosClaro = new Color(200, 85, 0); // Laranja mais escuro


    //Icones Botão DarkMode
    static private ImageIcon claroParaEscuro = new ImageIcon("Assets/claroParaEscuro.png");
    static private ImageIcon escuroParaClaro = new ImageIcon("Assets/escuroParaClaro.png");


    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {

            mainFrame = new JFrame("JUST EAT");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1280, 720);
            mainFrame.setLocationRelativeTo(null);

            menuInicial(darkMode);
            
            mainFrame.setVisible(true);
        });
    }

    public static void menuInicial(boolean darkMode)
    {
        mainFrame.getContentPane().removeAll();

        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color textoPricipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;

        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        

        mainFrame.setBackground(fundoPrincipal);

        //Painel para a logo
        Header header = new Header(barrasSuperioresInferiores);
        
        //Painel central definido para colocar elementos na vertical
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(70,0,0,0));
        

        //Botões do menu Principal
        BotaoMenu botaoCadastraCliente = new BotaoMenu("Cadastrar Cliente", darkMode);
        BotaoMenu botaoCadastraRestaurante = new BotaoMenu("Cadastrar Restaurante", darkMode);
        BotaoMenu botaoCadastraEntregador = new BotaoMenu("Cadastrar entregador", darkMode);
        BotaoMenu botaoCriarPedido = new BotaoMenu("Criar novo pedido", darkMode);
        BotaoMenu botaoAtribuirPedido = new BotaoMenu("Atribuir entregador a pedido", darkMode);
        BotaoMenu botaoAtualizarStatus = new BotaoMenu("Atualizar status de pedido", darkMode);
        BotaoMenu botaoListarPedidos = new BotaoMenu("Listar pedidos", darkMode);
        BotaoMenu botaoSair = new BotaoMenu("Sair", darkMode);
        JButton botaoDarkMode = new JButton(iconRedimensionado);
        
        

        //Funções dos botões
        botaoCadastraCliente.addActionListener(e->{menuCadastraCliente(darkMode);});
        botaoCadastraRestaurante.addActionListener(e->{menuCadastraRestaurante(darkMode);});
        botaoCadastraEntregador.addActionListener(e->{menuCadastraEntregador(darkMode);});
        botaoCriarPedido.addActionListener(e->{menuCriarPedido1(darkMode);});
        botaoAtribuirPedido.addActionListener(e->{menuAtribuirEntregador(darkMode);});
        botaoAtualizarStatus.addActionListener(e->{menuAtualizarStatusPedido(darkMode);});
        botaoListarPedidos.addActionListener(e->{menuListarPedidos(darkMode);});
        botaoSair.addActionListener(e->{sair();});
        botaoDarkMode.addActionListener(e->{menuInicial(!darkMode);});

        

        
        //Adicionando no Painel Central os botões.
        painelCentral.add(botaoCadastraCliente);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(botaoCadastraRestaurante);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(botaoCadastraEntregador);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(botaoCriarPedido);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(botaoAtribuirPedido);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(botaoAtualizarStatus);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(botaoListarPedidos);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(botaoSair);
        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.setBackground(fundoPrincipal);
        painelCentral.add(Box.createVerticalStrut(10));

        Footer footer = new Footer(barrasSuperioresInferiores, textoPricipal, fonte);
        footer.add(botaoDarkMode, BorderLayout.LINE_END);
        


        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer,BorderLayout.SOUTH);

        
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static boolean cadastraCliente(String nome, String email, int ID)
    {
        Cliente c = new Cliente(nome, ID, email);
        if(clientes.clienteExiste(c)) return false;
        clientes.adicionarCliente(c);

        return true;
    }

    private static void menuCadastraCliente(boolean darkMode) 
    {
        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;
    
        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        
        mainFrame.getContentPane().removeAll();
        mainFrame.setBackground(fundoPrincipal);
    
        Header header = new Header(barrasSuperioresInferiores);
    
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBackground(fundoPrincipal);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        //Declaração Campos de Input
        JTextField campoNome = new JTextField(25);
        JTextField campoEmail = new JTextField(25);
        
        //Declaração das Labels
        LabelMenu labelNome = new LabelMenu("Insira o nome do cliente:", darkMode);
        LabelMenu labelEmail = new LabelMenu("Insira o email do cliente:", darkMode);
        LabelMenu labelID = new LabelMenu("Insira o ID do cliente:", darkMode);
        
        //Declaração dos botões
        BotaoMenu botaoCadastra = new BotaoMenu("Cadastrar", darkMode);
        BotaoMenu voltar = new BotaoMenu("Voltar para menu Principal", darkMode);
        JButton botaoDarkMode = new JButton(iconRedimensionado);
    
    
        // Adicionando elementos ao painel central
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painelCentral.add(labelNome, gbc);
    
        gbc.gridy = 1;
        painelCentral.add(campoNome, gbc);
        
        gbc.gridy = 2;
        painelCentral.add(labelEmail, gbc);
    
        gbc.gridy = 3;
        painelCentral.add(campoEmail, gbc);
        
        gbc.gridy = 4;
        painelCentral.add(botaoCadastra, gbc);
    
        gbc.gridy = 5;
        painelCentral.add(voltar, gbc);
    
        botaoCadastra.addActionListener(e -> 
        {
            if (!campoNome.getText().isEmpty() && !campoEmail.getText().isEmpty() && campoEmail.getText().contains("@") && campoEmail.getText().contains(".com")) 
            {
                boolean ok = cadastraCliente(campoNome.getText(), campoEmail.getText(), proximoCliente++);
                if (ok) 
                {
                    JOptionPane.showMessageDialog(mainFrame, "Cliente cadastrado com sucesso!");
                    campoNome.setText("");
                    campoEmail.setText("");
                } 
                else 
                {
                    JOptionPane.showMessageDialog(mainFrame, "Não foi possível concluir o cadastro\nCliente já existe.");
                }


            } 
            else 
            {
                if (campoNome.getText().isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo nome é obrigatório.");
                if (campoEmail.getText().isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo email é obrigatório.");
                if (!(campoEmail.getText().contains("@") && campoEmail.getText().contains(".com"))) JOptionPane.showMessageDialog(mainFrame, "Email inválido.");
            }
        });
    
    
        voltar.addActionListener(e -> menuInicial(darkMode));
        botaoDarkMode.addActionListener(e -> menuCadastraCliente(!darkMode));
    
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);
        footer.add(botaoDarkMode, BorderLayout.LINE_END);
    
        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);
    
        mainFrame.revalidate();
        mainFrame.repaint();
    }



    private static void menuCadastraRestaurante(boolean darkMode) 
    {
        mainFrame.getContentPane().removeAll();
    
        List<Item> cardapioAtual = new ArrayList<>();
    
        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;
    
        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
    
        Header header = new Header(barrasSuperioresInferiores);
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);
    
        JPanel painelPrincipal = new JPanel(new BorderLayout(20, 20));
        painelPrincipal.setBackground(fundoPrincipal);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Painel lado esquerdo - dados do restaurante
        JPanel painelDadosRestaurante = new JPanel(new GridBagLayout());
        painelDadosRestaurante.setBackground(fundoPrincipal);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 15, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        LabelMenu labelNomeJuridico = new LabelMenu("Nome jurídico do restaurante:", darkMode);
        LabelMenu labelNomeComercial = new LabelMenu("Nome comercial do restaurante:", darkMode);
        LabelMenu labelEmail = new LabelMenu("Email do restaurante:", darkMode);
    
        JTextField textFieldNomeJuridico = new JTextField(25);
        JTextField textFieldNomeComercial = new JTextField(25);
        JTextField textFieldEmail = new JTextField(25);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelDadosRestaurante.add(labelNomeJuridico, gbc);
        gbc.gridy++;
        painelDadosRestaurante.add(textFieldNomeJuridico, gbc);
        gbc.gridy++;
        painelDadosRestaurante.add(labelNomeComercial, gbc);
        gbc.gridy++;
        painelDadosRestaurante.add(textFieldNomeComercial, gbc);
        gbc.gridy++;
        painelDadosRestaurante.add(labelEmail, gbc);
        gbc.gridy++;
        painelDadosRestaurante.add(textFieldEmail, gbc);
    
        // Painel direito - cadastro de itens
        JPanel painelCadastroItens = new JPanel(new BorderLayout(15, 15));
        painelCadastroItens.setBackground(fundoPrincipal);
        painelCadastroItens.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    
        // Painel dos campos de novo item (em linha)
        JPanel painelLinhaItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelLinhaItem.setBackground(fundoPrincipal);
    
        LabelMenu labelNomeItem = new LabelMenu("Nome do item:", darkMode);
        JTextField textFieldNomeItem = new JTextField(15);
        LabelMenu labelValorItem = new LabelMenu("Valor:", darkMode);
        JTextField textFieldValorItem = new JTextField(6);
        BotaoMenu botaoAdicionaItem = new BotaoMenu("Adicionar", darkMode);
    
        painelLinhaItem.add(labelNomeItem);
        painelLinhaItem.add(textFieldNomeItem);
        painelLinhaItem.add(labelValorItem);
        painelLinhaItem.add(textFieldValorItem);
        painelLinhaItem.add(botaoAdicionaItem);
    
        // Painel para exibir itens
        JPanel painelItens = new JPanel();
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));
        painelItens.setBackground(fundoPrincipal);
    
        TitledBorder bordaCustomizada = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(barrasSuperioresInferiores, 2),
            "Itens do Cardápio"
        );
        bordaCustomizada.setTitleColor(textoPrincipal);
        bordaCustomizada.setTitleFont(fonte.deriveFont(Font.BOLD));
    
        JScrollPane scrollPaneItens = new JScrollPane(painelItens);
        scrollPaneItens.setPreferredSize(new Dimension(320, 200));
        scrollPaneItens.setBorder(bordaCustomizada);
        scrollPaneItens.setBackground(fundoPrincipal);
        scrollPaneItens.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    
        painelCadastroItens.add(painelLinhaItem, BorderLayout.NORTH);
        painelCadastroItens.add(scrollPaneItens, BorderLayout.CENTER);
    
        // Botões inferiores
        JPanel painelBotoes = new JPanel(new BorderLayout());
        painelBotoes.setBackground(fundoPrincipal);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    
        BotaoMenu botaoVoltar = new BotaoMenu("Voltar", darkMode);
        BotaoMenu botaoCadastra = new BotaoMenu("Cadastrar Restaurante", darkMode);
        JButton botaoDarkMode = new JButton(iconRedimensionado);
    
        JPanel painelBotoesCentrais = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoesCentrais.setBackground(fundoPrincipal);
        painelBotoesCentrais.add(botaoVoltar);
        painelBotoesCentrais.add(botaoCadastra);
    
        painelBotoes.add(painelBotoesCentrais, BorderLayout.CENTER);
        footer.add(botaoDarkMode, BorderLayout.LINE_END);
    
        // Listeners
        botaoDarkMode.addActionListener(e -> menuCadastraRestaurante(!darkMode));
        botaoAdicionaItem.addActionListener(e -> {
            String nome = textFieldNomeItem.getText();
            String valorStr = textFieldValorItem.getText();
    
            if (!nome.isEmpty() && !valorStr.isEmpty()) {
                try {
                    double valor = Double.parseDouble(valorStr);
                    Item item = new Item(nome, valor);
                    cardapioAtual.add(item);
    
                    JLabel novoItemLabel = new JLabel(item.getNome() + " - R$ " + String.format("%.2f", item.getValor()));
                    novoItemLabel.setForeground(textoPrincipal);
                    novoItemLabel.setFont(fonte);
                    painelItens.add(novoItemLabel);
                    painelItens.revalidate();
                    painelItens.repaint();
    
                    textFieldNomeItem.setText("");
                    textFieldValorItem.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Valor inválido.");
                }
            } else {
                if (nome.isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo nome do item é obrigatório.");
                if (valorStr.isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo valor do item é obrigatório.");
            }
        });
        botaoVoltar.addActionListener(e -> menuInicial(darkMode));
        botaoCadastra.addActionListener(e -> {
            String juridico = textFieldNomeJuridico.getText();
            String comercial = textFieldNomeComercial.getText();
            String email = textFieldEmail.getText();
    
            if (!juridico.isEmpty() && !comercial.isEmpty() && !email.isEmpty() && !cardapioAtual.isEmpty()) 
            {
                ArrayList<Item> cardapioCopia = new ArrayList<>();
                for (Item item : cardapioAtual) 
                {
                    cardapioCopia.add(new Item(item.getNome(), item.getValor()));
                }
    
                Restaurante restaurante = new Restaurante(juridico, proximoRestaurante++, email, comercial, cardapioCopia);
                if (!restaurantes.restauranteExiste(restaurante)) {
                    restaurantes.adicionaRestaurante(restaurante);
                    JOptionPane.showMessageDialog(mainFrame, "Restaurante cadastrado com sucesso!");
    
                    textFieldNomeJuridico.setText("");
                    textFieldNomeComercial.setText("");
                    textFieldEmail.setText("");
    
                    cardapioAtual.clear();
                    painelItens.removeAll();
                    painelItens.revalidate();
                    painelItens.repaint();
    
                } 
                else 
                {
                    JOptionPane.showMessageDialog(mainFrame, "Não foi possível concluir o cadastro.\nRestaurante já existe.");
                }
            }
            else 
            {
                if (juridico.isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo nome jurídico é obrigatório.");
                if (comercial.isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo nome comercial é obrigatório.");
                if (email.isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo email é obrigatório.");
                if (cardapioAtual.isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Adicione pelo menos um item ao cardápio.");
            }
        });

        textFieldValorItem.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) 
                {
                    e.consume();
                }
            }
        });
    
        // Conteúdo principal dividido
        JPanel painelConteudo = new JPanel(new GridLayout(1, 2, 20, 0));
        painelConteudo.setBackground(fundoPrincipal);
        painelConteudo.add(painelDadosRestaurante);
        painelConteudo.add(painelCadastroItens);
    
        painelPrincipal.add(painelConteudo, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
    
        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelPrincipal, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);
    
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    

    private static void menuCadastraEntregador(boolean darkMode)
    {
        mainFrame.getContentPane().removeAll();

        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        //Declaração painéis.
        Header header = new Header(barrasSuperioresInferiores);
        JPanel painelCentral = new JPanel(new GridBagLayout());
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);

        painelCentral.setBackground(fundoPrincipal);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));


        //Declaração labels
        LabelMenu labelNome = new LabelMenu("Insira o nome do entregador: ", darkMode);
        LabelMenu labelEmail = new LabelMenu("Insira o email do entregador: ", darkMode);
        LabelMenu labelTransporte = new LabelMenu("Insira o meio de transporte do entregador: ", darkMode);

        //Declaração TField

        JTextField textFieldNome = new JTextField(30);
        JTextField textFieldEmail = new JTextField(30);

        //Declaração botões
        JButton botaoDarkMode = new JButton(iconRedimensionado);
        BotaoMenu botaoCadastra = new BotaoMenu("Cadastrar Entregador", darkMode);
        BotaoMenu botaoVoltar = new BotaoMenu("Voltar para o menu principal", darkMode);

        //Declaração menu drop-down

        String[] opcoes = {"Moto", "Carro", "Bicicleta", "Avião"};
        JComboBox<String> dropdown = new JComboBox<>(opcoes);
        dropdown.setBounds(50, 50, 250, 50);

        //Funções botoes


        botaoCadastra.addActionListener(e->
        {
            if(!textFieldEmail.getText().isEmpty() && !textFieldNome.getText().isEmpty() && textFieldEmail.getText().contains("@") && textFieldEmail.getText().contains(".com"))
            {
                Entregador entregador = new Entregador(textFieldNome.getText(), proximoEntregador++, textFieldEmail.getText(), (String) dropdown.getSelectedItem());

                if(!entregadores.entregadorExiste(entregador))
                {
                    entregadores.adicionaEntregador(entregador);
                    JOptionPane.showMessageDialog(mainFrame, "Entregador cadastrado com sucesso!");

                    textFieldEmail.setText("");
                    textFieldNome.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(mainFrame, "Não foi possível concluir o cadastro.\nEntregador já existe.");
                }
            }
            else
            {
                if(textFieldNome.getText().isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo nome é obrigatório.");
                if(textFieldEmail.getText().isEmpty()) JOptionPane.showMessageDialog(mainFrame, "Campo email é obrigatório.");
                if(!textFieldEmail.getText().contains("@") || !textFieldEmail.getText().contains(".com")) JOptionPane.showMessageDialog(mainFrame, "Email inválido.");
            }
        });

        botaoDarkMode.addActionListener(e->
        {
            menuCadastraEntregador(!darkMode);
        });

        botaoVoltar.addActionListener(e->
        {
            menuInicial(darkMode);
        });


        //Adição elementos nos painéis.

        footer.add(botaoDarkMode, BorderLayout.LINE_END);

        gbc.gridx = 0;
        gbc.gridy = 0;

        painelCentral.add(labelNome, gbc);
        
        gbc.gridy = 1;
        painelCentral.add(textFieldNome, gbc);

        gbc.gridy = 2;
        painelCentral.add(labelEmail, gbc);

        gbc.gridy = 3;
        painelCentral.add(textFieldEmail,gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        painelCentral.add(labelTransporte, gbc);

        gbc.gridy = 1;
        painelCentral.add(dropdown, gbc);

        gbc.gridy = 6;
        painelCentral.add(botaoCadastra, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        painelCentral.add(botaoVoltar, gbc);


        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);
        mainFrame.revalidate();
        mainFrame.repaint();


    }

    private static void menuCriarPedido1(boolean darkMode) {
        mainFrame.getContentPane().removeAll();
    
        ArrayList<Cliente> listaClientes = clientes.getListaClientes();
        DefaultListModel<Cliente> listaClientesModel = new DefaultListModel<>();
    
        for (Cliente c : listaClientes) {
            listaClientesModel.addElement(c);
        }
    
        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
    
        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
    
        // Declaração de painéis
        Header header = new Header(barrasSuperioresInferiores);
        JPanel painelCentral = new JPanel(new GridBagLayout());
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 linha, 2 colunas, espaçamento de 10px
        
        painelBotoes.setBackground(fundoPrincipal);
        painelCentral.setBackground(fundoPrincipal);
    
        // Declaração da JList com JScrollPane
        JList<Cliente> jListClientes = new JList<>(listaClientesModel);
        jListClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListClientes.setVisibleRowCount(5);
        jListClientes.setBackground(barrasSuperioresInferiores);
        jListClientes.setFixedCellHeight(60); 
    
        if (!listaClientesModel.isEmpty()) 
        {
            jListClientes.setSelectedIndex(0);
        }
    
        jListClientes.setCellRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    
                Cliente cliente = (Cliente) value;
                JPanel panel = new JPanel(new GridLayout(3, 1, 0, 2)); 
                panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
                
                panel.setBackground(isSelected ? new Color(100, 149, 237) : list.getBackground());
    
                LabelMenu nomeLabel = new LabelMenu("Nome: " + cliente.getNome(), darkMode);
                LabelMenu emailLabel = new LabelMenu("Email: " + cliente.getEmail(), darkMode);
                LabelMenu idLabel = new LabelMenu("ID: " + cliente.getId(), darkMode);
    
                panel.add(nomeLabel);
                panel.add(emailLabel);
                panel.add(idLabel);
    
                return panel;
            }
        });
    
        JScrollPane scrollPane = new JScrollPane(jListClientes);
        scrollPane.setPreferredSize(new Dimension(350, 200)); // Define o tamanho do ScrollPane
    
        // Declaração Labels
        LabelMenu labelSelecionarCliente = new LabelMenu("Selecione o cliente que realizará o pedido: ", darkMode);
    
        // Declaração de Botões
        JButton botaoDarkMode = new JButton(iconRedimensionado);
        BotaoMenu botaoAvancar =  new BotaoMenu("Avançar", darkMode);
        BotaoMenu botaoVoltar = new BotaoMenu("Voltar para o menu principal", darkMode);
    
        // Funções dos botões
        botaoDarkMode.addActionListener(e -> menuCriarPedido1(!darkMode));
        botaoAvancar.addActionListener(e->
        {
            if(!listaClientes.isEmpty())
            {
                Cliente cliente = clientes.getClienteIndex(jListClientes.getSelectedIndex());
                menuCriarPedido2(darkMode, cliente);
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "Não há clientes cadastrados.\nNão é possível realizar pedidos.");
            }

        });
        botaoVoltar.addActionListener(e -> menuInicial(darkMode));
    
        // Adicionando componentes aos painéis
        footer.add(botaoDarkMode, BorderLayout.LINE_END);

        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoAvancar);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentral.add(labelSelecionarCliente, gbc);
    
        gbc.gridy = 1;
        painelCentral.add(scrollPane, gbc);
    
        gbc.gridy = 2;
        painelCentral.add(painelBotoes, gbc);
    
        // Adicionando os painéis à frame principal
        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);
    
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private static void menuCriarPedido2(boolean darkMode, Cliente cliente) {
        mainFrame.getContentPane().removeAll();
    
        ArrayList<Restaurante> listaRestaurantes = restaurantes.getListaRestaurantes();
        DefaultListModel<Restaurante> listaRestaurantesModel = new DefaultListModel<>();
    
        for (Restaurante r : listaRestaurantes) {
            listaRestaurantesModel.addElement(r);
        }
    
        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;

        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
    
        Header header = new Header(barrasSuperioresInferiores);
        JPanel painelCentral = new JPanel(new GridBagLayout());
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        JPanel painelCardapio = new JPanel();
        painelCardapio.setLayout(new BoxLayout(painelCardapio, BoxLayout.Y_AXIS));
        painelCardapio.setBackground(barrasSuperioresInferiores);

        painelCentral.setBackground(fundoPrincipal);
    
        JList<Restaurante> jListRestaurantes = new JList<>(listaRestaurantesModel);
        jListRestaurantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListRestaurantes.setBackground(barrasSuperioresInferiores);
        jListRestaurantes.setCellRenderer(new ListCellRenderer<Restaurante>() 
        {
            @Override
            public Component getListCellRendererComponent(JList<? extends Restaurante> list, Restaurante restaurante, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBackground(isSelected ? new Color(100, 149, 237) : barrasSuperioresInferiores);
                panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
                JLabel nomeLabel = new JLabel("Nome: " + restaurante.getNomeComercial());
                JLabel idLabel = new JLabel("ID: " + restaurante.getID());
                JLabel emailLabel = new JLabel("Email: " + restaurante.getEmail());
                JLabel precoLabel = new JLabel("Preço médio: R$ " + restaurante.getPrecoMedio());
        
                Color texto = isSelected ? textosSecundarios : textoPrincipal;
                nomeLabel.setForeground(texto);
                idLabel.setForeground(texto);
                emailLabel.setForeground(texto);
                precoLabel.setForeground(texto);
        
                nomeLabel.setFont(fonte.deriveFont(16f));
                idLabel.setFont(fonte.deriveFont(12f));
                emailLabel.setFont(fonte.deriveFont(12f));
                precoLabel.setFont(fonte.deriveFont(12f));
        
                panel.add(nomeLabel);
                panel.add(idLabel);
                panel.add(emailLabel);
                panel.add(precoLabel);
        
                return panel;
            }
        });
    
        JScrollPane scrollPaneRestaurantes = new JScrollPane(jListRestaurantes);
        scrollPaneRestaurantes.setPreferredSize(new Dimension(300, 400));
    
        JScrollPane scrollPaneCardapio = new JScrollPane(painelCardapio);
        scrollPaneCardapio.setPreferredSize(new Dimension(300, 400));
    
        Map<Item, Integer> itensSelecionados = new HashMap<>();
        JLabel labelTotal = new JLabel("Total: R$ 0.00");
        labelTotal.setForeground(textoPrincipal);
        labelTotal.setFont(fonte.deriveFont(Font.BOLD, 16f));
        labelTotal.setHorizontalAlignment(SwingConstants.LEFT);
    
        Runnable atualizarTotal = () -> {
            double total = 0;
            for (Map.Entry<Item, Integer> entry : itensSelecionados.entrySet()) {
                total += entry.getKey().getValor() * entry.getValue();
            }
            labelTotal.setText(String.format("Total: R$ %.2f", total));
        };
    
        jListRestaurantes.addListSelectionListener(e -> 
        {
            if (!e.getValueIsAdjusting()) {
                painelCardapio.removeAll();
                itensSelecionados.clear();
                Restaurante restauranteSelecionado = jListRestaurantes.getSelectedValue();
    
                if (restauranteSelecionado != null) 
                {
                    for (Item item : restauranteSelecionado.getCardapio()) 
                    {
                        JPanel itemPanel = new JPanel(new BorderLayout(5, 5));
                        itemPanel.setBackground(barrasSuperioresInferiores);
                        itemPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    
                        LabelMenu itemLabel = new LabelMenu(item.getNome() + " - R$ " + String.format("%.2f", item.getValor()), darkMode);
                        itemLabel.setFont(fonte.deriveFont(14f));
    
                        JSpinner spinnerQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
                        spinnerQuantidade.setPreferredSize(new Dimension(50, 25));
                        spinnerQuantidade.addChangeListener(event -> {
                            int quantidade = (int) spinnerQuantidade.getValue();
                            if (quantidade > 0) {
                                itensSelecionados.put(item, quantidade);
                            } else {
                                itensSelecionados.remove(item);
                            }
                            atualizarTotal.run();
                        });
    
                        JPanel painelDireita = new JPanel(new GridBagLayout());
                        painelDireita.setBackground(barrasSuperioresInferiores);
                        GridBagConstraints gbcSpinner = new GridBagConstraints();
                        gbcSpinner.anchor = GridBagConstraints.CENTER;
                        painelDireita.add(spinnerQuantidade, gbcSpinner);
    
                        itemPanel.add(itemLabel, BorderLayout.CENTER);
                        itemPanel.add(painelDireita, BorderLayout.EAST);
                        painelCardapio.add(itemPanel);
                    }
    
                    painelCardapio.revalidate();
                    painelCardapio.repaint();
                }
            }
        });
    
        if (!listaRestaurantes.isEmpty()) 
        {
            jListRestaurantes.setSelectedIndex(0);
        }
    
        LabelMenu labelSelecionarRestaurante = new LabelMenu("Selecione o restaurante para o pedido:", darkMode);
        LabelMenu labelCardapio = new LabelMenu("Cardápio do restaurante:", darkMode);
    
        BotaoMenu botaoRealizarPedido = new BotaoMenu("Realizar Pedido", darkMode);
        JButton botaoDarkMode = new JButton(iconRedimensionado);
        botaoDarkMode.addActionListener(e -> menuCriarPedido2(!darkMode, cliente));
        BotaoMenu botaoVoltar = new BotaoMenu("Voltar", darkMode);
    
        botaoRealizarPedido.addActionListener(e -> 
        {
            Restaurante restaurante = jListRestaurantes.getSelectedValue();
            if (restaurante != null) 
            {
                ArrayList<Item> itensPedido = new ArrayList<>();
                for (Map.Entry<Item, Integer> entry : itensSelecionados.entrySet()) 
                {
                    for (int i = 0; i < entry.getValue(); i++) 
                    {
                        itensPedido.add(entry.getKey());
                    }
                }
    
                if (!itensPedido.isEmpty()) 
                {
                    double valorTotal = 0;
                    for (Item item : itensPedido) 
                    {
                        valorTotal += item.getValor();
                    }

                    pedidos.adicionaPedido(new Pedido(proximoPedido++, cliente, restaurante, itensPedido, valorTotal));
                    JOptionPane.showMessageDialog(mainFrame, "Pedido realizado com sucesso!");
                    menuInicial(darkMode);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(mainFrame, "Adicione pelo menos um item ao pedido.");
                }
            } 
            else 
            {
                JOptionPane.showMessageDialog(mainFrame, "Selecione um restaurante antes de avançar.");
            }
        });
    
        botaoVoltar.addActionListener(e -> menuCriarPedido1(darkMode));
        
        footer.add(botaoDarkMode, BorderLayout.LINE_END);

        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoRealizarPedido);
        painelBotoes.setBackground(fundoPrincipal);
    
        JPanel painelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        painelSuperior.setBackground(fundoPrincipal);
    
        JPanel painelRestaurantes = new JPanel(new BorderLayout());
        painelRestaurantes.setBackground(fundoPrincipal);
        painelRestaurantes.add(labelSelecionarRestaurante, BorderLayout.NORTH);
        painelRestaurantes.add(scrollPaneRestaurantes, BorderLayout.CENTER);
    
        JPanel painelCardapioContainer = new JPanel(new BorderLayout());
        painelCardapioContainer.setBackground(fundoPrincipal);
        painelCardapioContainer.add(labelCardapio, BorderLayout.NORTH);
        painelCardapioContainer.add(scrollPaneCardapio, BorderLayout.CENTER);
    
        painelSuperior.add(painelRestaurantes);
        painelSuperior.add(painelCardapioContainer);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        painelCentral.add(painelSuperior, gbc);
    
        gbc.gridy = 1;
        gbc.weighty = 0;
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setBackground(fundoPrincipal);
    
        JPanel painelInfoInferior = new JPanel(new BorderLayout());
        painelInfoInferior.setBackground(fundoPrincipal);
        painelInfoInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        painelInfoInferior.add(labelTotal, BorderLayout.WEST);
        painelInfoInferior.add(painelBotoes, BorderLayout.EAST);
    
        painelInferior.add(painelInfoInferior, BorderLayout.CENTER);
        painelCentral.add(painelInferior, gbc);
    
        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);
    
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    

    private static void menuAtribuirEntregador(boolean darkMode)
    {
        mainFrame.getContentPane().removeAll();

        ArrayList<Pedido> listaPedidos = pedidos.getListaPedidos();
        DefaultListModel<Pedido> listaPedidosModel = new DefaultListModel<>();
        ArrayList<Entregador> listaEntregadores = entregadores.getListaEntregadores();
        
    
        for (Pedido p : listaPedidos) 
        {
            listaPedidosModel.addElement(p);
        }

        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;

        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        

        //Painéis
        Header header = new Header(barrasSuperioresInferiores);
        JPanel painelCentral = new JPanel(new GridBagLayout());
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);

        painelCentral.setBackground(fundoPrincipal);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));


        //Declaração JList com JScrollPane
        JList<Pedido> jListPedidos = new JList<>(listaPedidosModel);
        jListPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListPedidos.setBackground(barrasSuperioresInferiores);
        jListPedidos.setCellRenderer(new ListCellRenderer<Pedido>() 
        {
            @Override
            public Component getListCellRendererComponent(JList<? extends Pedido> list, Pedido pedido, int index,
                                                          boolean isSelected, boolean cellHasFocus) 
                {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBackground(isSelected ? new Color(100, 149, 237) : barrasSuperioresInferiores);
                panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    
                LabelMenu numeroPedidoLabel = new LabelMenu("Número Pedido: " + pedido.getNumeroPedido(), darkMode);
                LabelMenu clienteLabel = new LabelMenu("Cliente: ", darkMode);
                LabelMenu nomeClienteLabel = new LabelMenu("Nome cliente: " + pedido.getCliente().getNome(), darkMode);
                LabelMenu emailClienteLabel = new LabelMenu("Email cliente: " + pedido.getCliente().getEmail(), darkMode);
                LabelMenu idClienteLabel = new LabelMenu("ID cliente: " + pedido.getCliente().getId(), darkMode);
                LabelMenu restauranteLabel = new LabelMenu("Restaurante: ", darkMode);
                LabelMenu nomeRestauranteLabel = new LabelMenu("Nome restaurante: " + pedido.getRestaurante().getNomeComercial(), darkMode);
                LabelMenu emailRestauranteLabel = new LabelMenu("Email restaurante: " + pedido.getRestaurante().getEmail(), darkMode);
                LabelMenu idRestauranteLabel = new LabelMenu("ID restaurante: " + pedido.getRestaurante().getID(), darkMode);
                LabelMenu statusLabel = new LabelMenu("Status: " + pedido.getStatus(), darkMode);
                LabelMenu entregadorLabel = new LabelMenu("Entregador: " + (pedido.getEntregador() != null ? pedido.getEntregador().getNome() : "Nenhum"), darkMode);
                LabelMenu precoLabel = new LabelMenu("Preço total: R$ " + String.format("%.2f", pedido.getValorTotal()), darkMode);

        
                Color texto = isSelected ? textosSecundarios : textoPrincipal;
                    
                numeroPedidoLabel.setFont(fonte.deriveFont(17f));
                clienteLabel.setFont(fonte.deriveFont(16f));
                nomeClienteLabel.setFont(fonte.deriveFont(12f));
                emailClienteLabel.setFont(fonte.deriveFont(12f));
                idClienteLabel.setFont(fonte.deriveFont(12f));
                restauranteLabel.setFont(fonte.deriveFont(16f));
                nomeRestauranteLabel.setFont(fonte.deriveFont(12f));
                emailRestauranteLabel.setFont(fonte.deriveFont(12f));
                idRestauranteLabel.setFont(fonte.deriveFont(12f));
                statusLabel.setFont(fonte.deriveFont(12f));
                entregadorLabel.setFont(fonte.deriveFont(12f));
                precoLabel.setFont(fonte.deriveFont(14f));
                    
                panel.add(numeroPedidoLabel);
                panel.add(clienteLabel);
                panel.add(nomeClienteLabel);
                panel.add(emailClienteLabel);
                panel.add(idClienteLabel);
                panel.add(restauranteLabel);
                panel.add(nomeRestauranteLabel);
                panel.add(emailRestauranteLabel);
                panel.add(idRestauranteLabel);
                panel.add(statusLabel);
                panel.add(entregadorLabel);
                panel.add(precoLabel);
        
                return panel;
            }
        });

        if (!listaPedidosModel.isEmpty()) 
        {
            jListPedidos.setSelectedIndex(0);
        }
    
        JScrollPane scrollPanePedidos = new JScrollPane(jListPedidos);
        scrollPanePedidos.setPreferredSize(new Dimension(300, 300));
        scrollPanePedidos.setMinimumSize(new Dimension(300, 300));

        //Declaração Labels

        LabelMenu labelSelecionarPedido = new LabelMenu("Selecione o pedido para atribuir um entregador: ", darkMode);

        //Declaração botões

        JButton botaoDarkMode = new JButton(iconRedimensionado);
        BotaoMenu botaoVoltar = new BotaoMenu("Voltar para o menu principal", darkMode);
        BotaoMenu botaoAtribuir = new BotaoMenu("Atribuir entregador", darkMode);

        //Funções botões

        botaoDarkMode.addActionListener(e -> menuAtribuirEntregador(!darkMode));
        botaoVoltar.addActionListener(e -> menuInicial(darkMode));
        botaoAtribuir.addActionListener(e -> 
        {
            if(!listaPedidos.isEmpty())
            {
                Pedido pedido = pedidos.getPedidoIndex(jListPedidos.getSelectedIndex());
                if(pedido != null)
                {
                    if(pedido.getEntregador() != null)
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Esse pedido já possui um entregador atribuído.");
                        return;
                    }
                    for (Entregador entregador : listaEntregadores)
                    {
                        if (entregador.getStatusDisponivel())
                        {
                            entregador.setStatusDisponivel(); // Muda para indisponível
                            pedido.atribuirEntregador(entregador);
                    
                            JOptionPane.showMessageDialog(mainFrame,
                                "Entregador atribuído com sucesso!\n" +
                                "Nome do entregador: " + entregador.getNome() + "\n" +
                                "ID do entregador: " + entregador.getId() + "\n" +
                                "Email do entregador: " + entregador.getEmail() + "\n" +
                                "Meio de transporte: " + entregador.getMeioDeTransporte());
                    
                            return; 
                        }
                    }
                    
                    JOptionPane.showMessageDialog(mainFrame, "Não há entregadores disponíveis no momento.");
                    
                    return;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "Não há pedidos cadastrados.\nNão é possível atribuir entregadores.");
            }
        });



        //Inserção elementos painéis

        footer.add(botaoDarkMode, BorderLayout.LINE_END);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentral.add(labelSelecionarPedido, gbc);

        gbc.gridy++;
        painelCentral.add(scrollPanePedidos, gbc);

        gbc.gridy++;
        painelCentral.add(botaoAtribuir, gbc);

        gbc.gridy++;
        painelCentral.add(botaoVoltar, gbc);
        

        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);

        mainFrame.revalidate();
        mainFrame.repaint();


    }

    private static void menuAtualizarStatusPedido(boolean darkMode)
    {
        mainFrame.getContentPane().removeAll();

        ArrayList<Pedido> listaPedidos = pedidos.getListaPedidos();
        DefaultListModel<Pedido> listaPedidosModel = new DefaultListModel<>();

                
    
        for (Pedido p : listaPedidos) 
        {
            listaPedidosModel.addElement(p);
        }

        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;

        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        //Painéis
        Header header = new Header(barrasSuperioresInferiores);
        JPanel painelCentral = new JPanel(new GridBagLayout());
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);
        
        painelCentral.setBackground(fundoPrincipal);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        //Declaração JList com JScrollPane
        JList<Pedido> jListPedidos = new JList<>(listaPedidosModel);
        jListPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListPedidos.setBackground(barrasSuperioresInferiores);
        jListPedidos.setCellRenderer(new ListCellRenderer<Pedido>() 
        {
            @Override
            public Component getListCellRendererComponent(JList<? extends Pedido> list, Pedido pedido, int index,
                                                          boolean isSelected, boolean cellHasFocus) 
                {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBackground(isSelected ? new Color(100, 149, 237) : barrasSuperioresInferiores);
                panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    
                LabelMenu numeroPedidoLabel = new LabelMenu("Número Pedido: " + pedido.getNumeroPedido(), darkMode);
                LabelMenu clienteLabel = new LabelMenu("Cliente: ", darkMode);
                LabelMenu nomeClienteLabel = new LabelMenu("Nome cliente: " + pedido.getCliente().getNome(), darkMode);
                LabelMenu emailClienteLabel = new LabelMenu("Email cliente: " + pedido.getCliente().getEmail(), darkMode);
                LabelMenu idClienteLabel = new LabelMenu("ID cliente: " + pedido.getCliente().getId(), darkMode);
                LabelMenu restauranteLabel = new LabelMenu("Restaurante: ", darkMode);
                LabelMenu nomeRestauranteLabel = new LabelMenu("Nome restaurante: " + pedido.getRestaurante().getNomeComercial(), darkMode);
                LabelMenu emailRestauranteLabel = new LabelMenu("Email restaurante: " + pedido.getRestaurante().getEmail(), darkMode);
                LabelMenu idRestauranteLabel = new LabelMenu("ID restaurante: " + pedido.getRestaurante().getID(), darkMode);
                LabelMenu statusLabel = new LabelMenu("Status: " + pedido.getStatus(), darkMode);
                LabelMenu entregadorLabel = new LabelMenu("Entregador: " + (pedido.getEntregador() != null ? pedido.getEntregador().getNome() : "Nenhum"), darkMode);
                LabelMenu precoLabel = new LabelMenu("Preço total: R$ " + String.format("%.2f", pedido.getValorTotal()), darkMode);

        
                Color texto = isSelected ? textosSecundarios : textoPrincipal;
                    
                numeroPedidoLabel.setFont(fonte.deriveFont(17f));
                clienteLabel.setFont(fonte.deriveFont(16f));
                nomeClienteLabel.setFont(fonte.deriveFont(12f));
                emailClienteLabel.setFont(fonte.deriveFont(12f));
                idClienteLabel.setFont(fonte.deriveFont(12f));
                restauranteLabel.setFont(fonte.deriveFont(16f));
                nomeRestauranteLabel.setFont(fonte.deriveFont(12f));
                emailRestauranteLabel.setFont(fonte.deriveFont(12f));
                idRestauranteLabel.setFont(fonte.deriveFont(12f));
                statusLabel.setFont(fonte.deriveFont(12f));
                entregadorLabel.setFont(fonte.deriveFont(12f));
                precoLabel.setFont(fonte.deriveFont(14f));
                    
                panel.add(numeroPedidoLabel);
                panel.add(clienteLabel);
                panel.add(nomeClienteLabel);
                panel.add(emailClienteLabel);
                panel.add(idClienteLabel);
                panel.add(restauranteLabel);
                panel.add(nomeRestauranteLabel);
                panel.add(emailRestauranteLabel);
                panel.add(idRestauranteLabel);
                panel.add(statusLabel);
                panel.add(entregadorLabel);
                panel.add(precoLabel);
        
                return panel;
            }
        });

        if (!listaPedidosModel.isEmpty()) 
        {
            jListPedidos.setSelectedIndex(0);
        }
    
        JScrollPane scrollPanePedidos = new JScrollPane(jListPedidos);
        scrollPanePedidos.setPreferredSize(new Dimension(300, 300));
        scrollPanePedidos.setMinimumSize(new Dimension(300, 300));

        //Declaração Labels

        LabelMenu labelSelecionarPedido = new LabelMenu("Selecione o pedido para atualizar o status: ", darkMode);
        

        //Declaração Botões

        JButton botaoDarkMode = new JButton(iconRedimensionado);
        BotaoMenu botaoVoltar = new BotaoMenu("Voltar para o menu principal", darkMode);
        BotaoMenu botaoAtualizar = new BotaoMenu("Atualizar status", darkMode);
        //Funções Botões
        botaoDarkMode.addActionListener(e -> menuAtualizarStatusPedido(!darkMode));
        botaoVoltar.addActionListener(e -> menuInicial(darkMode));
        botaoAtualizar.addActionListener(e -> 
        {
            if(!listaPedidos.isEmpty())
            {
                int selectedIndex = jListPedidos.getSelectedIndex();
                Pedido pedido = pedidos.getPedidoIndex(selectedIndex);
                if(pedido != null)
                {
                    if (pedido.getStatus().equals("Preparado") && pedido.getEntregador() == null)
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Esse pedido não possui entregador atribuído.");
                        return;
                    }

                    boolean excluir = pedido.atualizarStatus();

                    if(excluir)
                    {
                        pedidos.getListaPedidos().remove(selectedIndex);
                        listaPedidosModel.removeElementAt(selectedIndex); 
                        
                        pedido.getEntregador().setStatusDisponivel();
                        JOptionPane.showMessageDialog(mainFrame, "Pedido finalizado com sucesso!");
        
                        if (!listaPedidosModel.isEmpty())
                        {
                            jListPedidos.setSelectedIndex(0); 
                        }
                    }

                    else
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Status atualizado com sucesso!\n" +
                        "Novo status: " + pedido.getStatus());
                    }

                }
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "Não há pedidos cadastrados.\nNão é possível atualizar o status.");
            }
        });

        //Inserção elementos painéis
        footer.add(botaoDarkMode, BorderLayout.LINE_END);

        gbc.gridx = 0;
        gbc.gridy = 0;

        painelCentral.add(labelSelecionarPedido, gbc);
        
        gbc.gridy++;
        painelCentral.add(scrollPanePedidos, gbc);
        
        gbc.gridy++;
        painelCentral.add(botaoAtualizar, gbc);

        gbc.gridy++;
        painelCentral.add(botaoVoltar, gbc);

        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);

        mainFrame.revalidate();
        mainFrame.repaint();

    }

    private static void menuListarPedidos(boolean darkMode)
    {
        mainFrame.getContentPane().removeAll();
    
        ArrayList<Pedido> listaPedidos = pedidos.getListaPedidos();
        DefaultListModel<Pedido> listaPedidosModel = new DefaultListModel<>();
    
        for (Pedido p : listaPedidos) 
        {
            listaPedidosModel.addElement(p);
        }
    
        Color fundoPrincipal = darkMode ? fundoPrincipalEscuro : fundoPrincipalClaro;
        Color barrasSuperioresInferiores = darkMode ? barraSuperioresInferioresEscuro : barrasSuperioresInferioresClaro;
        Color textoPrincipal = darkMode ? textosPrincipalEscuro : textosPrincipaisClaro;
        Color textosSecundarios = darkMode ? textosSecundariosEscuro : textosSecundariosClaro;
    
        ImageIcon imageBotaoDarkMode = darkMode ? escuroParaClaro : claroParaEscuro;
        ImageIcon iconRedimensionado = new ImageIcon(imageBotaoDarkMode.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
    
        Header header = new Header(barrasSuperioresInferiores);
        Footer footer = new Footer(barrasSuperioresInferiores, textoPrincipal, fonte);
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(fundoPrincipal);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
    
        // Lado esquerdo - lista de pedidos
        JList<Pedido> jListPedidos = new JList<>(listaPedidosModel);
        jListPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListPedidos.setBackground(barrasSuperioresInferiores);
        jListPedidos.setCellRenderer(new ListCellRenderer<Pedido>() 
        {
            @Override
            public Component getListCellRendererComponent(JList<? extends Pedido> list, Pedido pedido, int index, boolean isSelected, boolean cellHasFocus) 
            {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBackground(isSelected ? new Color(100, 149, 237) : barrasSuperioresInferiores);
                panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    
                Color texto = isSelected ? textosSecundarios : textoPrincipal;
    
                LabelMenu numeroPedidoLabel = new LabelMenu("Número Pedido: " + pedido.getNumeroPedido(), darkMode);
                LabelMenu clienteLabel = new LabelMenu("Cliente: ", darkMode);
                LabelMenu nomeClienteLabel = new LabelMenu("Nome cliente: " + pedido.getCliente().getNome(), darkMode);
                LabelMenu emailClienteLabel = new LabelMenu("Email cliente: " + pedido.getCliente().getEmail(), darkMode);
                LabelMenu idClienteLabel = new LabelMenu("ID cliente: " + pedido.getCliente().getId(), darkMode);
                LabelMenu restauranteLabel = new LabelMenu("Restaurante: ", darkMode);
                LabelMenu nomeRestauranteLabel = new LabelMenu("Nome restaurante: " + pedido.getRestaurante().getNomeComercial(), darkMode);
                LabelMenu emailRestauranteLabel = new LabelMenu("Email restaurante: " + pedido.getRestaurante().getEmail(), darkMode);
                LabelMenu idRestauranteLabel = new LabelMenu("ID restaurante: " + pedido.getRestaurante().getID(), darkMode);
                LabelMenu statusLabel = new LabelMenu("Status: " + pedido.getStatus(), darkMode);
                LabelMenu entregadorLabel = new LabelMenu("Entregador: " + (pedido.getEntregador() != null ? pedido.getEntregador().getNome() : "Nenhum"), darkMode);
    
                numeroPedidoLabel.setFont(fonte.deriveFont(17f));
                clienteLabel.setFont(fonte.deriveFont(16f));
                nomeClienteLabel.setFont(fonte.deriveFont(12f));
                emailClienteLabel.setFont(fonte.deriveFont(12f));
                idClienteLabel.setFont(fonte.deriveFont(12f));
                restauranteLabel.setFont(fonte.deriveFont(16f));
                nomeRestauranteLabel.setFont(fonte.deriveFont(12f));
                emailRestauranteLabel.setFont(fonte.deriveFont(12f));
                idRestauranteLabel.setFont(fonte.deriveFont(12f));
                statusLabel.setFont(fonte.deriveFont(12f));
                entregadorLabel.setFont(fonte.deriveFont(12f));
    
                panel.add(numeroPedidoLabel);
                panel.add(clienteLabel);
                panel.add(nomeClienteLabel);
                panel.add(emailClienteLabel);
                panel.add(idClienteLabel);
                panel.add(restauranteLabel);
                panel.add(nomeRestauranteLabel);
                panel.add(emailRestauranteLabel);
                panel.add(idRestauranteLabel);
                panel.add(statusLabel);
                panel.add(entregadorLabel);
    
                return panel;
            }
        });
    
        JScrollPane scrollLista = new JScrollPane(jListPedidos);
        scrollLista.setPreferredSize(new Dimension(400, 400));
    
        // Lado direito - itens do pedido selecionado
        JPanel painelItens = new JPanel();
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));
        painelItens.setBackground(fundoPrincipal);
        
        JScrollPane scrollItens = new JScrollPane(painelItens);
        scrollItens.setPreferredSize(new Dimension(400, 400));
    
        // Atualizar painelItens ao selecionar
        jListPedidos.addListSelectionListener(e -> 
        {
            if (!e.getValueIsAdjusting()) 
            {
                painelItens.removeAll();
        
                Pedido pedidoSelecionado = jListPedidos.getSelectedValue();
                if (pedidoSelecionado != null) 
                {
                    HashMap<String, Integer> contagemItens = new HashMap<>();
                    HashMap<String, Double> precosItens = new HashMap<>();
        
                    for (Item item : pedidoSelecionado.getItens()) 
                    {
                        String nome = item.getNome();
                        double preco = item.getValor(); 
        
                        contagemItens.put(nome, contagemItens.getOrDefault(nome, 0) + 1);
                        precosItens.put(nome, preco); 
                    }
        
                    double subtotal = 0.0;
        
                    for (Map.Entry<String, Integer> entry : contagemItens.entrySet()) 
                    {
                        String nomeItem = entry.getKey();
                        int quantidade = entry.getValue();
                        double precoUnitario = precosItens.get(nomeItem);
                        double totalItem = quantidade * precoUnitario;
        
                        subtotal += totalItem;
        
                        LabelMenu itemLabel = new LabelMenu(
                            quantidade + "x " + nomeItem + " = R$ " + String.format("%.2f", totalItem), 
                            darkMode
                        );
                        itemLabel.setFont(fonte.deriveFont(14f));
                        painelItens.add(itemLabel);
                    }
        
                    // Adiciona o subtotal no final
                    LabelMenu subtotalLabel = new LabelMenu(
                        "Subtotal: R$ " + String.format("%.2f", subtotal), 
                        darkMode
                    );
                    subtotalLabel.setFont(fonte.deriveFont(Font.BOLD, 15f));
                    subtotalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                    painelItens.add(Box.createVerticalStrut(10));
                    painelItens.add(subtotalLabel);
        
                    painelItens.revalidate();
                    painelItens.repaint();
                }
            }
        });
    
        // Painel central dividido horizontalmente
        JPanel painelListagem = new JPanel();
        painelListagem.setLayout(new GridLayout(1, 2, 20, 0));
        painelListagem.setBackground(fundoPrincipal);
        painelListagem.add(scrollLista);
        painelListagem.add(scrollItens);
    
        // Botões
        JButton botaoDarkMode = new JButton(iconRedimensionado);
        BotaoMenu botaoVoltar = new BotaoMenu("Voltar para o menu principal", darkMode);
    
        botaoDarkMode.addActionListener(e -> menuListarPedidos(!darkMode));
        botaoVoltar.addActionListener(e -> menuInicial(darkMode));
    
        footer.add(botaoDarkMode, BorderLayout.LINE_END);
    
        // Adicionando ao painel
        painelCentral.add(painelListagem, BorderLayout.CENTER);
    
        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(fundoPrincipal);
        painelBotao.add(botaoVoltar);
    
        painelCentral.add(painelBotao, BorderLayout.SOUTH);
    
        mainFrame.add(header, BorderLayout.NORTH);
        mainFrame.add(painelCentral, BorderLayout.CENTER);
        mainFrame.add(footer, BorderLayout.SOUTH);
    
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    

    private static void sair()
    {
        System.exit(0);
    }
}
