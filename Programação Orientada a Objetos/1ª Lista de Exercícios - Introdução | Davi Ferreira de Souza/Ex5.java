import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import java.text.SimpleDateFormat;

class Aluno
{
    String nome;
    Calendar dataNascimento;
    String RG;
    String endereco;
    String telefone;
    int RA;
    double CR;


    public void setNome(String nome){this.nome = nome;}
    public void setDataNascimento(Calendar data){this.dataNascimento = data;}
    public void setRG(String RG){this.RG = RG;}
    public void setEndereco(String endereco){this.endereco = endereco;}
    public void setTelefone(String telefone){this.telefone = telefone;}
    public void setRA(int RA){this.RA = RA;}
    public void setCR(double CR){this.CR = CR;}

    public String getNome(){return this.nome;}
    public Calendar getDataNascimento(){return this.dataNascimento;}
    public String getRG(){return this.RG;}
    public String getEndereco(){return this.endereco;}
    public String getTelefone(){return this.telefone;}
    public int getRA(){return this.RA;}
    public double getCR(){return this.CR;}

    public void imprimeAluno()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("-----------------------------------------------------");
        System.out.println("ALUNO: " + this.nome);
        System.out.println("Data de Nascimento: " + sdf.format(this.dataNascimento.getTime()));
        System.out.println("RG: " + this.getRG());
        System.out.println("Endereco: " + this.getEndereco());
        System.out.println("Telefone: " + this.getTelefone());
        System.out.println("RA: " + this.getRA());
        System.out.println("CR: " + this.getCR());
        System.out.println("-----------------------------------------------------");

    }

}



public class Ex5
{

    public static void ordenaVetor(Aluno[] alunos,int numAlunos, int escolha)
    {
        boolean ordenado = false;
        Aluno temp;
        if(escolha == 1)
        {
            while(!ordenado)
            {
                ordenado = true;
                for(int i = 0; i < numAlunos - 1; i++)
                {
                    if(alunos[i].getRA() > alunos[i+1].getRA())
                    {
                        temp = alunos[i];
                        alunos[i] = alunos[i+1];
                        alunos[i+1] = temp;
                        ordenado = false;
                    }
                }
            }
        }
        else if(escolha == 2)
        {
            while(!ordenado)
            {
                ordenado = true;
                for(int i = 0; i < numAlunos - 1; i++)
                {
                    if(alunos[i].getNome().compareTo(alunos[i+1].getNome()) > 0)
                    {
                        temp = alunos[i];
                        alunos[i] = alunos[i+1];
                        alunos[i+1] = temp;
                        ordenado = false;
                    }
                }
            }
        }
        else
        {
            while(!ordenado)
            {
                ordenado = true;
                for(int i = 0; i < numAlunos - 1; i++)
                {
                    if(alunos[i].getCR() < alunos[i+1].getCR())
                    {
                        temp = alunos[i];
                        alunos[i] = alunos[i+1];
                        alunos[i+1] = temp;
                        ordenado = false;
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Aluno[] alunos = new Aluno[100];

        int numAlunos = 0;
        boolean rodando = true;
        int escolha;

        while(rodando)
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("1 - Cadastrar novo aluno.");
            System.out.println("2 - Ler o valor de um RA e imprimir os dados do aluno com este RA.");
            System.out.println("3 - Imprimir todos os cadastros.");
            System.out.println("4 - Sair do programa.\n");
            System.out.println("-----------------------------------------------------");

            System.out.println("Insira sua escolha: ");
            escolha = scanner.nextInt();

            switch(escolha)
            {
                case 1:
                    Aluno alunoNovo = new Aluno();

                    scanner.nextLine();
                    System.out.print("Digite o nome do aluno: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite o dia de nascimento: ");
                    int dia = scanner.nextInt();
                    System.out.print("Digite o mês de nascimento (1-12): ");
                    int mes = scanner.nextInt();
                    System.out.print("Digite o ano de nascimento: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();

                    Calendar dataNascimento = Calendar.getInstance();
                    dataNascimento.set(ano, mes - 1, dia);

                    System.out.print("Insira o RG do aluno: ");
                    String RG = scanner.nextLine();

                    System.out.print("Digite o endereço do aluno: ");
                    String endereco = scanner.nextLine();

                    System.out.print("Insira o telefone do aluno: ");
                    String telefone = scanner.nextLine();

                    System.out.print("Insira o RA do aluno: ");
                    int RA = scanner.nextInt();

                    System.out.print("Insira o CR do aluno: ");
                    double CR = scanner.nextDouble();

                    alunoNovo.setNome(nome);
                    alunoNovo.setDataNascimento(dataNascimento);
                    alunoNovo.setRG(RG);
                    alunoNovo.setEndereco(endereco);
                    alunoNovo.setTelefone(telefone);
                    alunoNovo.setRA(RA);
                    alunoNovo.setCR(CR);

                    alunos[numAlunos] = alunoNovo;
                    numAlunos++;

                    break;
                case 2:
                    int RAbusca;


                    System.out.println("Insira o RA do aluno a ser buscado: ");

                    RAbusca = scanner.nextInt();

                    for(int i = 0; i < numAlunos; i++)
                    {
                        if(alunos[i].getRA() == RAbusca)
                        {
                            alunos[i].imprimeAluno();
                        }

                        System.out.println("Aperte enter para continuar:");
                        scanner.nextLine();

                    }

                    break;
                case 3:

                    System.out.println("-----------------------------------------------------");
                    System.out.println("1 - Imprimir alunos ordenados por RA");
                    System.out.println("2 - Imprimir alunos ordenados por Nome");
                    System.out.println("3 - Imprimir alunos ordenados por CR");
                    System.out.println("-----------------------------------------------------");

                    System.out.println("Insira sua escolha: ");
                    escolha = scanner.nextInt();

                    ordenaVetor(alunos, numAlunos, escolha);

                    for(int i = 0; i < numAlunos; i++)
                    {
                        alunos[i].imprimeAluno();
                    }


                    break;
                case 4:
                    rodando = false;
                    break;
                default:
                    System.out.println("Escolha inválida.\nTente novamente.");
            }
        }





    }
}
