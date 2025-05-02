#Davi Ferreira de Souza
#Programa para analisar 5 problemas de diferentes áreas e buscar zeros de funções reais.
#13/04/2025


import matplotlib.pyplot as mpl
import os
import time
import math

#Inicialização de variáveis globais para contagem de iterações e para o problema 4 (função de multiplas variáveis)
iteracoes_bicessao = 0
iteracoes_falsa_posicao = 0
iteracoes_newton = 0

epsilon = 0
sigma = 0

#Declaração das funções de todos os problemas
def funcao_problema_1(x):
    return 5 * x - 100

def funcao_problema_2(x):
    return 1e-12 * (math.exp(x / 0.025) - 1) - 1e-2

def funcao_problema_3(x):
    if(0.1-x) < 1e-12:
        return float('inf')
    return (x**2)/(0.1-x) - 1e-5

def funcao_problema_4(x):
    return 24 * epsilon * (2 * (sigma/x)**13 - (sigma/x)**7)

def funcao_problema_5(x):
    return x - 1 - math.exp(-x)

#Função para plotar os gráficos
def plot_graficos(valores_fx, valores_tempo, valores_iteracoes, numproblema):
    
    #Define a xLabel com o nome dos métodos
    metodos = ["Bisseção", "Falsa posição", "Newton"]

    #1 - Plot do módulo da função nas raizes encontradas
    mpl.figure(figsize = (10,6)) #Define o tamanho da figura
    mpl.bar(metodos, valores_fx, color = "skyblue") #Plota com os valores de x sendo os nomes dos métodos e os valores de y sendo |f(raizmetodo)| com a cor skyblue
    mpl.ylabel("|f(x)| na raiz encontrada") #Define a label y
    mpl.title("Precisão dos métodos (quanto menor, melhor [se -1 não convergiu]):") #Define o titulo do grafico
    mpl.grid(True, axis = 'y') #Habilita a grid
    mpl.savefig(f"grafico_erro_problema{numproblema}.png") #Salva o gráfico com o número do problema correspondente no nome
    mpl.close()

    #2 - Plot do tempo de execução dos métodos
    mpl.figure(figsize = (10,6)) #Repete para o primeiro grafico
    mpl.bar(metodos, valores_tempo, color = "skyblue")
    mpl.ylabel("Tempo de execução do método em segundos")
    mpl.title("Tempo de execução dos métodos (quanto menor, melhor)")
    mpl.grid(True, axis = 'y')
    mpl.savefig(f"grafico_tempo_problema{numproblema}.png")
    mpl.close()

    #3 - Plot do número de iterações
    mpl.figure(figsize = (10,6))
    mpl.bar(metodos, valores_iteracoes, color = "skyblue")
    mpl.ylabel("Número de iterações")
    mpl.title("Número de iterações dos métodos (quanto menor, melhor)")
    mpl.grid(True, axis = 'y')
    mpl.savefig(f"grafico_iteracoes_problema{numproblema}.png")
    mpl.close()


def derivada(func,x):
    h = 1e-10

    return (func(x+h)-func(x-h)) / (2*h) #Uma iteração de derivada finita central, é suficiente visto que h é bem pequeno.

def metodo_bissecao(f, a, b, erro = 1e-6, max_iter = 1000):
    global iteracoes_bicessao
    iteracoes = 1 #Começa a contagem utilizando a variável global

    if(f(a) * f(b)>0): #Checa se o método pode convergir ou não
        return None
    
    xi = (a + b)/2 #Define o primeiro xi

    while ((abs(f(xi)) > erro or abs(b-a) > erro) and iteracoes < max_iter): #Checa os erros da função e também a quantidade maxima de iterações
        iteracoes += 1 #Incrementa a quantidade de iterações
        xi = (a + b) / 2 #Calcula o novo xi

        if(f(a) * f(xi) == 0): #Checa se achou raiz
            return xi
        elif(f(a) * f(xi) < 0): #Se f(a) * f(xi) < 0, então o b se torna xi
            b = xi
        else: #Se não, a se torna xi
            a = xi

    iteracoes_bicessao = iteracoes #Guarda a quantidade de iterações na variável global
    return xi if iteracoes < max_iter else None #Checa se o método convergiu, se convergiu retorna a raiz, se não, retorna None

def metodo_falsa_posicao(f, a, b, erro = 1e-6, max_iter = 1000):
    global iteracoes_falsa_posicao
    iteracoes = 1 #Inicializa novamente a variável global

    if(f(a) * f(b)>0): #Checa critério de convergência do método
        return None

    denominador = f(b)-f(a) 
    if(denominador < 1e-12): #Checa se na primeira iteração terá divisão por zero.Se sim, retorna None
        return None
    xi = (a * f(b) - b * f(a)) / (denominador) #Calculo do primeiro xi

    while (abs(f(xi)) > erro and abs(b-a) > erro and iteracoes < max_iter):
        iteracoes += 1 #Incrementa iterações
        denominador = f(b)-f(a) #Novamente checa por divisões por zero
        if(denominador < 1e-12):
            return None
        xi = (a * f(b) - b * f(a)) / (denominador)

        if(f(a) * f(xi) == 0): #Checa se achou raiz
            return xi
        elif(f(a) * f(xi) < 0): #B anda
            b = xi
        else: #A anda
            a = xi

    iteracoes_falsa_posicao = iteracoes #Atualiza o valor da variável global
    return xi if iteracoes < max_iter else None  #Checa se o método convergiu, se não convergiu retorna None

def metodo_newton(f, x0, erro = 1e-6, max_iter = 1000):
    iteracoes = 1
    global iteracoes_newton
    df = derivada(f, x0) #Calcula a derivada de f(x) no ponto
    if abs(df) < 1e-12: #Checa se a derivada da zero para retornar None
        return None

    xi = x0 - (f(x0) / derivada(f, x0)) #Calcula o primeiro xi

    while (abs(f(xi)) > erro and iteracoes < max_iter): #Checa os critérios de parada
        iteracoes += 1 #Incrementa iterações
        xiantes = xi #Guarda o xi anterior numa variável
        df = derivada(f, xi) #Realiza o calculo da derivada
    
        if abs(df) < 1e-12: #Checa se a derivada da 0 novamente
            iteracoes_newton = iteracoes 
            return None
        
        xi = xi - (f(xi) / df) #Calcula o novo xi

        if (abs(xi - xiantes) / max(xi, 1) < erro): #Checa critérios de parada
            iteracoes_newton = iteracoes 
            return xi

    iteracoes_newton = iteracoes
    return xi if iteracoes < max_iter else None  #Retorna raiz se convergiu, ou None.


def analise_problema(funcao_problema, a0,b0,x0, erro, numproblema):

    global iteracoes_bicessao #Chama as variáveis globais
    global iteracoes_falsa_posicao
    global iteracoes_newton

    print(f"Erro = {erro}\n\n") #Imprime o erro a ser analisado

    print("Resultados obtidos:\n") #Apresentação dos resultados
    print("----------Raizes----------")

    tempo_bissecao = time.time(); #Uso da biblioteca time para checar o tempo de execução.
    raiz_metodo_bissecao = metodo_bissecao(funcao_problema,a0,b0,erro) #Calcula a raiz utilizando método da bisseção
    tempo_bissecao = time.time() - tempo_bissecao #Termina o calculo de tempo
    print(f"Método da bisseção (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_bissecao == None else raiz_metodo_bissecao}") #Imprime a raiz somente se o método convergiu
    

    tempo_metodo_falsa_posicao = time.time() #Novamente calculo do tempo de execução
    raiz_metodo_falsa_posicao = metodo_falsa_posicao(funcao_problema,a0,b0,erro) #Calcula a raiz utilizando o método da falsa posição
    tempo_metodo_falsa_posicao = time.time() - tempo_metodo_falsa_posicao
    print(f"Método da falsa posição (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_falsa_posicao == None else raiz_metodo_falsa_posicao}")
    

    tempo_metodo_newton = time.time()
    raiz_metodo_newton = metodo_newton(funcao_problema, x0, erro) #Calcula a raiz utilizando o método de newton
    tempo_metodo_newton = time.time() - tempo_metodo_newton
    print(f"Método de Newton (x0 = {x0}): {"Não convergiu" if raiz_metodo_newton == None else raiz_metodo_newton}")
    

    print("\n----------Tempos de execução----------"); #Apresenta os demais resultados
    print(f"Método da bisseção: {tempo_bissecao} segundos")
    print(f"Método da falsa posição: {tempo_metodo_falsa_posicao} segundos")
    print(f"Método de Newton: {tempo_metodo_newton} segundos")

    print("\n----------Número de iterações----------")
    print(f"Método da bisseção: {iteracoes_bicessao} iterações")
    print(f"Método da falsa posição: {iteracoes_falsa_posicao} iterações")
    print(f"Método de Newton: {iteracoes_newton} iterações")

    print("\n----------Erros finais----------") #Mostra os erros finais e também checa se o método chegou a convergir
    print(f"Método da bisseção: {abs(funcao_problema(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else "Não convergiu"}")
    print(f"Método da falsa posição: {abs(funcao_problema(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else "Não convergiu"}")
    print(f"Método de Newton: {abs(funcao_problema(raiz_metodo_newton)) if raiz_metodo_newton is not None else "Não convergiu"}")

    #Plotar gráficos
    valores_fx = [abs(funcao_problema(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else -1,
                  abs(funcao_problema(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else -1,
                  abs(funcao_problema(raiz_metodo_newton)) if raiz_metodo_newton is not None else -1] #Coloca -1 se o método não convergiu para que seja possível visualizar no gráfico. Também guarda os erros num vetor para serem plotados na função plot_graficos
    valores_tempo = [tempo_bissecao, tempo_metodo_falsa_posicao, tempo_metodo_newton] #Guarda os tempos de execução
    valores_iteracoes = [iteracoes_bicessao, iteracoes_falsa_posicao, iteracoes_newton] #Guarda a quantidade de iterações

    plot_graficos(valores_fx, valores_tempo, valores_iteracoes, numproblema) #Chama a função de plotar gráficos

def problema_1():

    os.system("clear");
    print("Problema 1 – Economia: Equilíbrio de Oferta e Demanda\n\n") #Apresenta o problema
    print("Função analisada: f(p) = 5p - 100") #Apresenta a função
    
    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: ")) #Input do usuário para análise
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0: #Se erro for muito pequeno, valor default
        erro = 1e-6

    analise_problema(funcao_problema_1, a0, b0, x0, erro, 1) #Chama a função de análise
    

    continuar = input("\n\nAperte enter para continuar: ") #Enter para continuar
    main()

   
def problema_2():
    os.system("clear");

    print("Problema 2 – Engenharia: Equação Não-Linear de um Diodo") #Apresenta o problema
    print("Função analisada: f(V) = 1e-12 * (e^(V / 0.025) - 1) - 1e-2") #Apresenta a função
    
    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: ")) #Input do usuário para análise
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0: #Se erro for muito pequeno, valor default
        erro = 1e-6
    
    analise_problema(funcao_problema_2, a0, b0, x0, erro, 2) #Chama a função de análise
    
    continuar = input("\n\nAperte enter para continuar: ") #Enter para continuar
    main()

def problema_3():
    os.system("clear");

    print("Problema 3 – Química: Equilíbrio de Dissociação de um Ácido Fraco") #Mesmos comentários função problema_1 e função problema_2.
    print("Função analisada: f(x) = (x^2)/(0.1-x) - 1e-5")
    
    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6
    
    analise_problema(funcao_problema_3, a0, b0, x0, erro, 3)

    continuar = input("\n\nAperte enter para continuar: ")
    main()

def problema_4():
    global epsilon
    global sigma

    print("Problema 4 – Física: Ponto de Equilíbrio no Potencial de Lennard-Jones") #Mesmos comentários função problema_1 e função problema_2.
    print("Função analisada: f(x) = 24ε [(2(σ/x)¹³) - (σ/x)⁷]")

    epsilon = float(input("Informe o valor de ε (profundidade do poço de potencial): "))
    sigma = float(input("Informe o valor de σ (distância na qual o potencial é zero): "))

    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6
    
    analise_problema(funcao_problema_4, a0, b0, x0, erro, 4)

    continuar = input("\n\nAperte enter para continuar: ")
    main()


def problema_5():
    os.system("clear")
    print("Problema 5 – Engenharia (Controle): Ganho Crítico para Estabilidade de um Sistema de Realimentação")#Mesmos comentários função problema_1 e função problema_2.
    print("Função analisada: f(x) = x − 1 − e^x")


    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6


    analise_problema(funcao_problema_5, a0, b0, x0, erro, 5)

    continuar = input("\n\nAperte enter para continuar: ")
    main()



def main():
    os.system("chcp 65001") #Decodificação UTF-8
    os.system("clear")    #Limpa Console
     
    print("Selecione o problema para analisar: ") #Menu
    print("1 - Problema 1 – Economia: Equilíbrio de Oferta e Demanda")
    print("2 - Problema 2 – Engenharia: Equação Não-Linear de um Diodo")
    print("3 - Problema 3 – Química: Equilíbrio de Dissociação de um Ácido Fraco")
    print("4 - Problema 4 – Física: Ponto de Equilíbrio no Potencial de Lennard-Jones")
    print("5 - Problema 5 – Engenharia (Controle): Ganho Crítico para Estabilidade de um Sistema de Realimentação")
    print("6 - Sair\n")

    escolha = int(input("Insira sua escolha: ")) 

    while escolha < 1 or escolha > 6: #Checa se a escolha é valida
        print("Escolha inválida.")
        escolha = int(input("Insira a escolha novamente: "))

    match escolha: #Mudança de menus baseada na escolha
        case 1:
            problema_1()
        case 2:
            problema_2()
        case 3:
            problema_3()
        case 4:
            problema_4()
        case 5:
            problema_5()
        case 6:
            quit()
        

if __name__ == "__main__": #Roda a main se o programa estiver sendo executado diretamente
    main()
