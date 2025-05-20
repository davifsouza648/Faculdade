import numpy as np
import math
import threading
import tabulate
import os
import time
import copy
import matplotlib.pyplot as plt

def criterio_menores_principais(A): #Critério das menores principais para os métodos da decomposição LU
    n = len(A)
    menores_principais = []

    A = np.array(A) #Transforma a matriz A num array do numpy

    for k in range(1, n+1): 
        Ak = A[0:k, 0:k] #Cria as submatrizes
        det = np.linalg.det(Ak) #Utiliza a biblioteca numpy para calcular o determinante
        menores_principais.append(det) #Guarda o determinante no vetor menores_principais

    for i in range(len(menores_principais)):
        if(menores_principais[i] == 0): #Checa se algum menor principal é menor que zero.
            return False

    return True

def criterio_raio_espectral_jacobi(A): #Calcula o critério do raio espectral para o método de jacobi_richardson
    A = np.array(A, dtype=float)
    D = np.diag(np.diag(A))
    L = np.tril(A, -1)
    U = np.triu(A, 1)
    
    D_inv = np.linalg.inv(D)
    T = -D_inv @ (L + U)
    
    autovalores = np.linalg.eigvals(T)
    raio_espectral = max(abs(autovalores))
    
    return raio_espectral < 1

def criterio_raio_espectral_gauss_seidel(A): #Calcula o critério do raio espectral para o método de gauss-seidel
    A = np.array(A, dtype=float)
    D = np.diag(np.diag(A))
    L = np.tril(A, -1)
    U = np.triu(A, 1)
    
    DL_inv = np.linalg.inv(D + L)
    T = -DL_inv @ U
    
    autovalores = np.linalg.eigvals(T)
    raio_espectral = max(abs(autovalores))
    
    return raio_espectral < 1

def zerar_valores_pequenos(M, eps=1e-10): #Função para eliminar erros de arredondamento nos métodos.
    for i in range(len(M)):
        for j in range(len(M[0])):
            if(abs(M[i][j]) < eps):
                M[i][j] = 0

def gauss_compacto(Ax,b):

    #Criar matriz aumentada Ax|b
    n = len(Ax)
    for i in range(n):
        Ax[i].append(b[i])

    for k in range(n):
        pivo = Ax[k][k] #Pega o pivo (elemento da diagonal)
        if pivo == 0: #Checa se ele é 0 (não converge)
            return "N/A"

        for i in range(k+1,n):
            fator = Ax[i][k] / pivo #Determina o fator que a linha deve ser multiplicada para zerar a coluna

            for j in range(k,n+1):
                Ax[i][j]-=fator*Ax[k][j] #Subtrai cada elemento da linha
    
    x = [] #Inicializa o vetor x

    for i in range(n):
        x.append(0) #Enche de 0s

    for i in range(n-1,-1,-1): #Faz as substituições no vetor
        soma = 0
        for j in range(i + 1, n):
            soma+=Ax[i][j] * x[j] #Realiza a soma dos outros elementos da linha ja conhecidos
        
        x[i] = (Ax[i][n] - soma) / Ax[i][i] #Determina x[í] subtraindo o resultado da linha pela soma e dividindo pelo seu escalar

    return x #retorna X

def eliminacao_gauss_jordan(Ax,b):
    
    n = len(Ax)
    for i in range(n):
        Ax[i].append(b[i]) #Cria a matriz aumentada Ax|b

    for k in range(n):
        pivo = Ax[k][k] #Pega o pivô da linha
        
        if(pivo == 0): #Checa se ele é zero (Não aplicável)
            return "N/A"

        if(pivo != 1): #Se o pivô não é 1 deve ser normalizado
            multiplicador = 1/pivo #Determina o multiplicador para normalizar
            for j in range(n+1):
                Ax[k][j] *= multiplicador #Normaliza a linha inteira do pivo
        
        for i in range(n): #Percorre a linha
            if i != k:
                fator = Ax[i][k]
                for j in range(n+1):
                    Ax[i][j] -= fator * Ax[k][j]

    x = [] #Inicializa o vetor x
    for i in range(n):
        x.append(Ax[i][n]) #Guarda os resultados (vetor b depois das alterações)


    return x #Retorna o vetor x

def decomposicao_lu(Ax,b):
    n = len(Ax)

    if(criterio_menores_principais(Ax) == False): #Checa o critério das menores principais para aplicar o método
        return "N/A"

    U = np.zeros((n,n)) #Cria a matriz U como uma matriz de zeros de NxN elementos
    L = np.identity(n) #Cria a matriz identidade de tamanho n na matriz L

   
    for k in range(n): #Realiza os cálculos das fórmulas da decomposição LU
        for j in range(n):
            soma = 0
            for s in range(k):
                soma+=L[k][s] * U[s][j]
            U[k][j] = Ax[k][j] - soma
        
        for i in range(k+1,n):
            soma = 0
            for s in range(k):
                soma+=L[i][s] * U[s][k]
            if(U[k][k] == 0):
                return "N/A"
            L[i][k] = (1/U[k][k]) * (Ax[i][k] - soma)

    zerar_valores_pequenos(L) #Zera os valores muito próximos de zero nas duas matrizes
    zerar_valores_pequenos(U)

    y = np.zeros(n) #Cria um vetor de zeros y
    for i in range(n): #Resolve o sistema Ly = b
        soma = 0
        for j in range(i):
            soma += L[i][j] * y[j]
        y[i] = b[i] - soma 

    x = np.zeros(n)
    for i in range(n-1, -1, -1): #Resolve o sistema Ux = y
        soma = 0
        for j in range(i+1, n):
            soma += U[i][j] * x[j]
        if U[i][i] == 0:
            return "N/A"
        x[i] = (y[i] - soma) / U[i][i]

    return x.tolist() #Retorna x como uma lista novamente

def fatoracao_cholesky(Ax,b):
    
    A = np.array(Ax)
    n = len(Ax)
    autovalores = np.linalg.eigvals(A) #Realiza o cálculo dos autovalores

    if not np.allclose(A, A.T, atol=1e-10): #Checa se a matriz é simétrica
        return "N/A"
    
    if np.any(autovalores <= 0): #Checa se algum autovalor é <= 0
        return "N/A"
    
    L = np.zeros((n,n)) #Cria a matriz L como uma matriz de zeros de NxN

    for i in range(n): #Realiza os cálculos para determinar a matriz L
        for j in range(i + 1):
            soma = 0
            for k in range(j):
                soma+=L[i][k] * L[j][k]
            
            if(i == j):
                L[i][j] = math.sqrt(A[i][j] - soma)
            else:
                L[i][j] = (A[i][j] - soma) / L[j][j]

    y = np.zeros(n) #Cria os vetores y e x como vetores de 0
    x = np.zeros(n)

    for i in range(n):
        soma = sum(L[i][k] * y[k] for k in range(i))
        y[i] = (b[i] - soma) / L[i][i] #Resolve o sistema Ly = b

    for i in reversed(range(n)):
        soma = sum(L[k][i] * x[k] for k in range(i + 1, n))
        x[i] = (y[i] - soma) / L[i][i] #Resolve o sistema Ltx = y

    return x.tolist() #Retorna x como uma lista novamente

def calcula_xi(Ax, b, x_n_1, i, xn): #Função para calcular cada coeficiente do vetor x da iteração atual no método jacobi_richardson usando threads
    
    xi = b[i]
    
    for j in range(len(Ax)):
        if j != i:
            xi -= Ax[i][j] * x_n_1[j]
    xi /= Ax[i][i]
    
    xn[i] = xi



def jacobi_richardson(Ax,b, x0, tolerancia = 1e-5):
    
    erro = 1 + tolerancia #Inicializa as variáveis necessárias
    n = len(Ax)
    x_n_1 = x0
    iteracoes = 0

    if not criterio_raio_espectral_jacobi(Ax): #Checa o critério do raio espectral específico para o método
        return "N/A"

    while(abs(erro) > tolerancia): #Continua iterando enquanto o critério não está satisfeito
        threads = [] #Inicializa o vetor de threads
        iteracoes+=1
        xn = [0 for _ in range(n)]
        for i in range(n):
            t = threading.Thread(target=calcula_xi, args=(Ax, b, x_n_1, i, xn)) #Cria a thread para calcular o coeficiente i do vetor xn
            threads.append(t)

        for t in threads:
            t.start() #Inicia todos os threads
        
        for t in threads:
            t.join() #Espera todos os threads terminarem seus cálculos
        
        erro = np.linalg.norm(np.array(xn) - np.array(x_n_1), ord=np.inf) #Calcula o erro usando a norma infinita usando numpy

        x_n_1 = xn[:] #X_n_1 se torna xn para  proxima iteração

    return x_n_1, erro, iteracoes #Retorna x_n_1 (copia do xn), o erro e o número de iterações.

def gauss_seidel(Ax, b, x0, tolerancia=1e-5):
    erro = 1 + tolerancia #Inicializa as variáveis
    iteracoes = 0
    n = len(Ax)
    A = np.array(Ax, dtype=float)
    b = np.array(b, dtype=float)
    x = np.array(x0, dtype=float)

    if not criterio_raio_espectral_gauss_seidel(A): #Checa o critério de convergência do raio espectral
        return "N/A"

    while abs(erro) > tolerancia: #Continua iterando enquanto o critério não é satisfeito
        iteracoes+=1 #Incrementa o número de iterações
        x_anterior = x.copy() #Copia o vetor x para o x_anterior

        for i in range(n):
            soma = 0
            for j in range(n):
                if j != i:
                    soma += A[i][j] * x[j]
            x[i] = (b[i] - soma) / A[i][i] #Calcula todos os coeficientes do vetor x

        erro = np.linalg.norm(x - x_anterior, ord=np.inf) #Calcula o erro

    return x.tolist(), erro, iteracoes #Retorna o vetor x como lista, o erro e o número de iterações



def main():

    Ax = [[10,2,-1], #Declaração da matriz do problema
           [-3,-6,2],
           [1,1,5]]

    b = [27, -61.5, -21.5] #Vetor b do problema

    os.system("chcp 65001") #Para usar caracteres especiais
    os.system("cls" if os.name == "nt" else "clear") #Limpa tela

    rodando = True #Flag para continuar o loop do menu

    ax_gauss_compacto = copy.deepcopy(Ax) #Cria uma cópia de Ax para cada método para evitar conflitos entre os métodos
    ax_gauss_jordan = copy.deepcopy(Ax)
    ax_lu = copy.deepcopy(Ax)
    ax_cholesky = copy.deepcopy(Ax)

    tempo_gauss_compacto = time.time() #Inicia o tempo
    x_gauss_compacto = gauss_compacto(ax_gauss_compacto,b) #Calcula o vetor x
    tempo_gauss_compacto = time.time() - tempo_gauss_compacto #Calcula o tempo

    tempo_gauss_jordan = time.time() #Mesmo do de cima para todos os demais métodos
    x_gauss_jordan = eliminacao_gauss_jordan(ax_gauss_jordan,b)
    tempo_gauss_jordan = time.time() - tempo_gauss_jordan

    tempo_decomposicao_lu = time.time()
    x_decomposicao_lu = decomposicao_lu(ax_lu,b)
    tempo_decomposicao_lu = time.time() - tempo_decomposicao_lu

    tempo_cholesky = time.time()
    x_cholesky = fatoracao_cholesky(ax_cholesky,b)
    tempo_cholesky = time.time() - tempo_cholesky



    while(rodando):

        print("Problema analisado:\n")

        print("10x1 + 2x2 − x3 = 27\n−3x1 − 6x2 + 2x3 = −61.5\nx1 + x2 + 5x3 = −21.5\n") #Apresenta o problema
        print("x1, x2, x3 são as forças internas nas barras (em kN).\n\n")


        print("1 - Aplicar um método para resolução do problema") #Opções do menu
        print("2 - Comparar resultados")
        print("3 - Gerar gráficos comparativos")
        print("4 - Sair do programa\n")

        escolha = int(input("Insira sua escolha: ")) #Leitura da escolha

        match escolha:
            case 1:
                os.system("cls" if os.name == "nt" else "clear") 
                print("Escolha um método para resolver o problema:\n")

                print("1 - Eliminação de Gauss-Compacto") #Apresentação dos métodos
                print("2 - Eliminação de Gauss-Jordan")
                print("3 - Decomposição LU")
                print("4 - Fatoração de Cholesky")
                print("5 - Método Iterativo de Jacobi-Richardson")
                print("6 - Método Iterativo de Gauss-Seidel")

                escolha2 = int(input("Insira sua escolha: "))

                match escolha2:
                    case 1: #Para cada um limpa a tela e mostra as informações obtidas pelo método
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Método de eliminação de Gauss-Compacto:\n")

                        print("X = " + str(x_gauss_compacto))
                        print("Tempo de processamento: " + str(tempo_gauss_compacto) + " segundos.")

                        input("\nPressione enter para continuar: ")
                        
                        continue
                    case 2:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Método da Eliminação de Gauss-Jordan:\n")
                        
                        print("X = " + str(x_gauss_jordan))
                        print("Tempo de processamento: " + str(tempo_gauss_jordan) + " segundos")

                        input("\nPressione enter para continuar: ")
                        
                        continue
                    case 3:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Método da Decomposição LU:\n")

                        print("X = " + str(x_decomposicao_lu))
                        print("Tempo de processamento: " + str(tempo_decomposicao_lu) + " segundos")

                        input("\nPressione enter para continuar: ")

                        continue
                        
                    case 4:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Método da Fatoração de Cholesky:\n")

                        print("X = " + str(x_cholesky))
                        print("Tempo de processamento = " + str(tempo_cholesky) + " segundos")

                        input("\nPressione enter para continuar: ")

                        continue
                    
                    case 5:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Método Iterativo de Jacobi-Richardson:\n")

                        print("Insira o vetor x0 para o método: ")
                        x0 = []

                        for i in range(3):
                            x0.append(int(input("X["+str((i+1))+"] = ")))
                        
                        tempo_jacobi_richardson = time.time()
                        x, erro, iteracoes = jacobi_richardson(Ax,b,x0)
                        tempo_jacobi_richardson = time.time() - tempo_jacobi_richardson

                        print("X = " + str(x))
                        print("Tempo de processamento: " + str(tempo_jacobi_richardson) + " segundos")
                        print("Erro final: " + str(erro))
                        print("Número de iterações: " + str(iteracoes))

                        input("\nPressione enter para continuar: ")
                        
                        continue

                    case 6:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Método Iterativo de Gauss-Seidel\n")

                        print("Insira o vetor x0 para o método: ")
                        x0 = []

                        for i in range(3):
                            x0.append(int(input("X["+str((i+1))+"] = ")))

                        tempo_gauss_seidel = time.time()
                        x, erro, iteracoes = gauss_seidel(Ax,b,x0)
                        tempo_gauss_seidel = time.time() - tempo_gauss_seidel

                        print("X = " + str(x))
                        print("Tempo de processamento: " + str(tempo_gauss_seidel) + " segundos")
                        print("Erro final: " + str(erro))
                        print("Número de iterações: " + str(iteracoes))

                        input("\nPressione enter para continuar: ")

                        continue
                    case _:
                        print("Opção inválida.")
                        input("\nPressione enter para continuar: ")
                        continue
                
                continue
            
            case 2:

                os.system("cls" if os.name == "nt" else "clear")
                print("Comparando resultados:\n")

                print("Insira o vetor x0 para os Métodos Jacobi-Richardson e Gauss-Seidel:") #Pede a inserção do vetor x0 para calcular pelos métodos jacobi-richardson e gauss-seidel(iguais para comparação ser justa)
                x0 = []
                for i in range(3):
                    x0.append(int(input("X["+str((i+1))+"] = ")))

 
                tempo_jacobi_richardson = time.time() #Calcula o tempo dos métodos iterativos
                x_jacobi_richardson, erro_jacobi_richardson, iteracoes_jacobi_richardson = jacobi_richardson(Ax,b,x0) #Calcula o x, erro e o número de iterações de cada método
                tempo_jacobi_richardson = time.time() - tempo_jacobi_richardson

                tempo_gauss_seidel = time.time()
                x_gauss_seidel, erro_gauss_seidel, iteracoes_gauss_seidel = gauss_seidel(Ax,b,x0)
                tempo_gauss_seidel = time.time() - tempo_gauss_seidel


                table = [["Gauss Compacto",x_gauss_compacto, tempo_gauss_compacto, "Não iterativo", 0],
                         ["Gauss Jordan",x_gauss_jordan, tempo_gauss_jordan, "Não iterativo", 0],
                         ["Decomposição LU",x_decomposicao_lu, tempo_decomposicao_lu, "Não iterativo", 0],
                         ["Fatoração de Cholesky",x_cholesky, tempo_cholesky, "Não iterativo", 0],
                         ["Jacobi-Richardson",x_jacobi_richardson, tempo_jacobi_richardson, iteracoes_jacobi_richardson, erro_jacobi_richardson],
                         ["Gauss-Seidel",x_gauss_seidel, tempo_gauss_seidel, iteracoes_gauss_seidel, erro_gauss_seidel]] #Cria uma tabela para apresentar os resultados
                
                print(tabulate.tabulate(table, headers = ["Método", "X", "Tempo de processamento (segundos)", "Iterações", "Erro final"], tablefmt = "heavy_outline")) #Imprime a tabela com os respectivos headers utilizando a biblioteca tabulate

                input("\nPressione enter para continuar: ")

                continue
            case 3:
                os.system("cls" if os.name == "nt" else "clear")
                print("Gerando gráficos comparativos:\n")
                
                print("Insira o vetor x0 para os Métodos Jacobi-Richardson e Gauss-Seidel:") #Pede a inserção do vetor x0 para calcular pelos métodos jacobi-richardson e gauss-seidel(iguais para comparação ser justa)
                x0 = []
                for i in range(3):
                    x0.append(float(input(f"X[{i+1}] = ")))

                tempo_jacobi_richardson = time.time()  #Calcula o tempo dos métodos iterativos
                x_jacobi, erro_jacobi, iteracoes_jacobi = jacobi_richardson(Ax,b,x0)#Calcula o x, erro e o número de iterações de cada método
                tempo_jacobi_richardson = time.time() - tempo_jacobi_richardson

                tempo_gauss_seidel = time.time()
                x_seidel, erro_seidel, iteracoes_seidel = gauss_seidel(Ax,b,x0)
                tempo_gauss_seidel = time.time() - tempo_gauss_seidel

                metodos = ['Gauss-Compacto', 'Gauss-Jordan', 'Decomposição LU', 'Cholesky', 'Jacobi', 'Gauss-Seidel'] #Inicializa os vetores para plot de gráficco
                tempos = [tempo_gauss_compacto, tempo_gauss_jordan, tempo_decomposicao_lu, 
                         tempo_cholesky, tempo_jacobi_richardson, tempo_gauss_seidel]
                erros = [0, 0, 0, 0, erro_jacobi, erro_seidel]  #Métodos não iterativos tem erro 0 e 0 iterações
                iteracoes = [0, 0, 0, 0, iteracoes_jacobi, iteracoes_seidel]  

                #Plota os gráficos
                plt.figure(figsize=(20, 10))
                plt.bar(metodos, tempos, color='blue')
                plt.title('Comparação de Tempo de Processamento')
                plt.ylabel('Tempo (segundos)')
                plt.xticks(rotation=45)
                plt.tight_layout()
                plt.savefig('graficos/comparacao_tempos.png') #Salva os gráficos dentro da pasta graficos

                plt.figure(figsize=(20, 10))
                plt.bar(metodos, erros, color='red')
                plt.title('Comparação de Erro Final (métodos iterativos)')
                plt.ylabel('Erro')
                plt.xticks(rotation=45)
                plt.tight_layout()
                plt.savefig('graficos/comparacao_erros.png')

                plt.figure(figsize=(20, 10))
                plt.bar(metodos, iteracoes, color='green')
                plt.title('Comparação de Número de Iterações')
                plt.ylabel('Iterações')
                plt.xticks(rotation=45)
                plt.savefig('graficos/comparacao_iteracoes.png')
                plt.show()

                print("Gráficos salvos com sucesso na pasta 'graficos':") #Mostra a mensagem de todos os gráficos salvos
                print("- comparacao_tempos.png")
                print("- comparacao_erros.png")
                print("- comparacao_iteracoes.png")

                input("\nPressione enter para continuar: ")
                continue
            case 4:
                rodando = False
                continue

            case _:
                print("Opção inválida.")
                input("\nPressione enter para continuar: ")




if __name__ == "__main__": #Inicializa a main quando executa o código
    main()