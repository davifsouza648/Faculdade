import matplotlib.pyplot as mpl
import os
from tabulate import tabulate

def diferenciacao_regressiva(x, y): #Função diferenciação regressiva
    derivadas = [None] #Coloca None no primeiro índice já que não há derivada nesse ponto usando esse método

    for i in range(1, len(x)): #Itera do índice 1 ao tamanho do vetor x
        dx = x[i] - x[i-1] #Calcula a diferença em x
        dy = y[i] - y[i-1] #Calcula a diferença em y

        derivadas.append(dy / dx) #Adiciona no vetor de derivadas
 
    return derivadas #Retorna o vetor de derivadas calculadas

def diferenciacao_central(x, y): #Função diferenciação central
    derivadas = [None]; #Coloca None no primeiro índice já que não há derivada nesse ponto usando esse método

    for i in range(1, len(x) - 1): #Itera do índice 1 a tamanho do vetor x - 1
        dx = x[i+1] - x[i-1] #Calcula diferença em x
        dy = y[i+1] - y[i-1] #Calcula diferença em y

        derivadas.append(dy / dx) #Adiciona no vetor de derivadas

    derivadas.append(None) #Adiciona no último índice None visto que não há derivada no último ponto também

    return derivadas #Retorna o vetor de derivadas calculadas
    

def diferenciacao_progressiva(x, y): #Função diferenciação progressiva
    derivadas = [] #Inicia o vetor com nenhum elemento (existe derivada no primeiro elemento)

    for i in range(0, len(x) - 1): #Percorre de 0 a tamanho vetor x - 1 pois não há derivada no último ponto
        dx = x[i+1] - x[i] #Calcula a diferença em x
        dy = y[i+1] - y[i] #Calcula a diferença em y

        derivadas.append(dy / dx) #Adiciona no vetor de derivadas

    derivadas.append(None) #Adiciona None no último elemento pois não há derivada no último ponto

    return derivadas #Retorna o vetor de derivadas calculadas.

def gerar_graficos_comparacao(valores_x, derivadas_regressivas, derivadas_centrais, derivadas_progressivas, derivadas_aproximadas_regressao): #Função de gerar gráficos de comparação de resultados, recebe os valores obtidos pelas derivadas regressivas, centrais e progressivas, além dos valores obtidos usando a função de regressão
    # Prepara listas vazias
    xs_reg, ys_reg = [], [] #Inicia os vetores de pontos
    xs_cent, ys_cent = [], []
    xs_prog, ys_prog = [], []
    xs_regressao, ys_regressao = [],[]

    # Regressiva
    for i in range(len(valores_x)):
        
        if derivadas_regressivas[i] != None: #Checa se há derivada no ponto antes de adicionar nos vetores respectivos para análise
            xs_reg.append(valores_x[i])
            ys_reg.append(derivadas_regressivas[i])
        
        if derivadas_centrais[i] != None:
            xs_cent.append(valores_x[i])
            ys_cent.append(derivadas_centrais[i])
        
        if derivadas_progressivas[i] != None:
            xs_prog.append(valores_x[i])
            ys_prog.append(derivadas_progressivas[i])
        
        xs_regressao.append(valores_x[i]) #Adiciona todos os valores visto que na função regressiva há derivada em todos os pontos
        ys_regressao.append(derivadas_aproximadas_regressao[i])



    # Plot do gráfico
    mpl.figure(figsize=(8, 5)) #Inicia a figura e define o tamanho
    mpl.plot(xs_prog, ys_prog, marker='o', linestyle='-', label='Progressiva') #Define cada subplot do gráfico
    mpl.plot(xs_reg, ys_reg,  marker='s', linestyle='--', label='Regressiva')
    mpl.plot(xs_cent, ys_cent, marker='^', linestyle='-.', label='Central')
    mpl.plot(xs_regressao, ys_regressao, marker='8', linestyle = ':', label = 'Diferenciação usando função regressão')

    mpl.title("Comparação de Métodos de Diferenciação Numérica") #Título do gráfico
    mpl.xlabel("Tempo (s)") #Label x do gráfico
    mpl.ylabel("Velocidade (m/s)") #Label y do gráfico
    mpl.legend() #Habilita a legenda do gráfico
    mpl.tight_layout() #Ajusta o padding entre os subplots
    mpl.savefig("grafico_comparacao.png") #Salva a imagem do gráfico


def gerar_graficos_erro(valores_x, erros_derivadas_regressivas, erros_derivadas_centrais, erros_derivadas_progressivas):
    xs_reg, ys_reg = [], [] #Inicia os vetores de pontos
    xs_cent, ys_cent = [], []
    xs_prog, ys_prog = [], []

    for i in range(len(valores_x)):
        if(erros_derivadas_regressivas[i] != None): #Checa se há a derivada nesse ponto para adicionar os erros e os valores de x nos vetores
            xs_reg.append(valores_x[i])
            ys_reg.append(erros_derivadas_regressivas[i])
        
        if(erros_derivadas_centrais[i] != None):
            xs_cent.append(valores_x[i])
            ys_cent.append(erros_derivadas_centrais[i])
        
        if(erros_derivadas_progressivas[i] != None):
            xs_prog.append(valores_x[i])
            ys_prog.append(erros_derivadas_progressivas[i])

    mpl.figure(figsize=(8, 5)) #Inicia a figura e define o tamanho
    mpl.plot(xs_prog, ys_prog, marker='o', linestyle='-', label='Progressiva') #Define cada subplot do gráfico
    mpl.plot(xs_reg, ys_reg,  marker='s', linestyle='--', label='Regressiva')
    mpl.plot(xs_cent, ys_cent, marker='^', linestyle='-.', label='Central')

    mpl.title("Erros em relação à regressão quadrática") #Título do gráfico
    mpl.xlabel("Tempo (s)") #Label x do gráfico
    mpl.ylabel("|Aproximação - Derivada Função Regressiva|") #Label y do gráfico
    mpl.legend() #Habilita a legenda do gráfico
    mpl.tight_layout() #Ajusta o padding entre os subplots
    mpl.savefig("grafico_erro.png") #Salva a imagem do gráfico



def main():
    #Inicialização das variáveis
    valores_x = [0,3,5,8,10,13]
    valores_y = [0, 225, 383, 623, 742, 993]
    
    derivadas_obtidas_regressao = [76.3073788546256, 76.1573788546256, 76.0573788546256, 75.9073788546256, 75.8073788546256, 75.6573788546256] #Derivadas obtidas usando o polinômio de regressão obtido pelo geogebra: f(x) = -0.025x^2 + 76.3073788546256x - 0.1354625559661
    
    derivadas_regressivas = diferenciacao_regressiva(valores_x,valores_y) #Calcula as derivadas utilizando os respectivos métodos
    derivadas_centrais = diferenciacao_central(valores_x, valores_y)
    derivadas_progressivas = diferenciacao_progressiva(valores_x,valores_y)

    rodando = True
    
    while(rodando): #Loop principal
        os.system("chcp 65001") #Permite encoding UTF-8
        os.system("cls" if os.name == "nt" else "clear") #Limpa console
        
        print("--- Diferenciação Numérica ---")
        print("\nPontos analisados:")
        print(tabulate(zip(valores_x, valores_y), headers=["x", "y"], tablefmt="rounded_grid")) #Mostra a tabela dos pontos analisados

        print("\nMenu:")
        print("1 - Resultados das derivadas")
        print("2 - Gráficos de comparação")
        print("3 - Erros de aproximação")
        print("4 - Gráficos de erro")
        print("5 - Sair\n")
        
        try: #Try e catch para lidar com entradas inválidas
            escolha = int(input("Insira sua escolha: "))
        except ValueError:
            print("Entrada inválida! Digite um número entre 1 e 5.")
            input("Pressione Enter para continuar...")
            continue
        
        if escolha < 1 or escolha > 5:
            print("Opção inválida! Digite um número entre 1 e 5.")
            input("Pressione Enter para continuar...")
            continue
        
        match escolha: #Switch case para cada escolha
            case 1:
                os.system("cls" if os.name == "nt" else "clear") 
                dados_tabela = [] #Inicia a matriz que guarda os dados da tabela
                for i in range(len(valores_x)):
                    linha = [
                        valores_x[i],
                        f"{derivadas_regressivas[i]:.6f}" if derivadas_regressivas[i] is not None else "N/A", #Guarda o valor de x e a derivada de cada método ou N/A se não for possível a derivada no ponto
                        f"{derivadas_centrais[i]:.6f}" if derivadas_centrais[i] is not None else "N/A",
                        f"{derivadas_progressivas[i]:.6f}" if derivadas_progressivas[i] is not None else "N/A",
                        f"{derivadas_obtidas_regressao[i]:.6f}"
                    ]
                    dados_tabela.append(linha) #Guarda a linha na matriz
                
                # Exibir tabela formatada
                headers = ["Tempo (s)", "Regressiva (m/s)", "Central (m/s)", "Progressiva (m/s)", "Função Regressão (m/s)"] #Define os headers da tabela
                
                print("\nRESULTADOS DAS DERIVADAS NUMÉRICAS") #Título da tabela
                print(tabulate( #Uso da biblioteca tabulate para gerar a tabela
                    dados_tabela, 
                    headers=headers, 
                    tablefmt="rounded_grid",
                    floatfmt=".6f",
                    stralign="center",
                    numalign="center"
                ))
                
                print("\nLegenda:") #Legenda da tabela
                print("N/A = Método não aplicável neste ponto")
                print("Valores mostrados com 6 casas decimais")
                
                input("Aperte enter para continuar: ") #Continuar depois de ler o enter


            case 2:

                gerar_graficos_comparacao(valores_x, derivadas_regressivas, derivadas_centrais, derivadas_progressivas, derivadas_obtidas_regressao) #Chama a função gerar_graficos_comparacao com os resultados obtidos

                print("Gráfico gerado com sucesso!")
                input("Aperte enter para continuar: ") #Continuar depois de ler o enter

            case 3:
                dados_tabela = [] #Inicia a matriz da tabela
                for i in range(len(valores_x)):
                    linha = [
                        valores_x[i],
                        f"{abs(derivadas_regressivas[i] - derivadas_obtidas_regressao[i]):.6f}" #Guarda o valor de x e o erro de cada método usando como base a derivada obtida pela regressão ou N/A se não há derivada.
                            if derivadas_regressivas[i] is not None else "N/A",
                        f"{abs(derivadas_centrais[i] - derivadas_obtidas_regressao[i]):.6f}" 
                            if derivadas_centrais[i] is not None else "N/A",
                        f"{abs(derivadas_progressivas[i] - derivadas_obtidas_regressao[i]):.6f}" 
                            if derivadas_progressivas[i] is not None else "N/A"
                    ]
                    dados_tabela.append(linha) #Adiciona a linha na tabela
                
                # Exibir tabela de erros
                headers = ["Tempo (s)", "Erro Regressiva", "Erro Central", "Erro Progressiva"] #Define os headers
                
                print("\nERROS DE APROXIMAÇÃO EM RELAÇÃO À REGRESSÃO") #Título da tabela
                print(tabulate(
                    dados_tabela, 
                    headers=headers, 
                    tablefmt="rounded_grid",
                    floatfmt=".6f",
                    stralign="center",
                    numalign="center"
                ))
                
                input("Aperte enter para continuar: ") #Continuar depois de ler o enter
                    
            case 4:
                erros_regressiva = [] #Inicia os vetores de erro
                erros_central = []
                erros_progressiva = []

                for i in range(0,len(valores_x)): #Calcula os erros para todos os métodos usando as derivadas obtidas pela função de regressão do geogebra
                    erros_regressiva.append(abs(derivadas_regressivas[i] - derivadas_obtidas_regressao[i]) if derivadas_regressivas[i] is not None else None)
                    erros_central.append(abs(derivadas_centrais[i] - derivadas_obtidas_regressao[i]) if derivadas_centrais[i] is not None else None)
                    erros_progressiva.append(abs(derivadas_progressivas[i] - derivadas_obtidas_regressao[i]) if derivadas_progressivas[i] is not None else None)
                

                gerar_graficos_erro(valores_x, erros_regressiva, erros_central, erros_progressiva) #Chama a função para gerar os gráficos

                print("Gráfico gerado com sucesso!")
                input("Aperte enter para continuar: ") #Continuar depois de ler o enter
                

            case 5:
                rodando = False

if __name__ == "__main__":
    main()
