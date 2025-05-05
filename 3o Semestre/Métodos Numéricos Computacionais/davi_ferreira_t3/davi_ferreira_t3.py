import matplotlib.pyplot as mpl
import os

def gauss_compacto(A, b):

    n = len(A) #Determina o tamanho da matriz A
    for i in range(n): #Cria a matriz aumentada A
        A[i].append(b[i])

    #Eliminação
    for i in range(n): # Para cada linha i da matriz

        if A[i][i] == 0: #Checa se um pivô é zero, nesse caso não há como realizar o método
            return "Sistema sem solução ou infinitas soluções (pivô zero)."
        
        for j in range(i+1,n): #Percorre as linhas abaixo da linha i
            div = A[j][i]/A[i][i] #Determina o fator a ser multiplicado dividindo o elemento A[j][i] pelo pivô (A[i][i])
            for k in range(i, n+1): # Atualiza da coluna i até a última da matriz aumentada
                A[j][k] -= div * A[i][k] #Atualiza o valor de A[j][k] multiplicando o fator pelo elemento A[i][k] da matriz (elemento acima)

    x = [0 for _ in range(n)] #Inicia o vetor x com zeros

    for i in range(n-1,-1,-1): #Percorre a matriz de baixo para cima
        soma = 0 #Inicia a variável soma
        for j in range(i+1,n): #Percorre as colunas a partir de i + 1 da matriz
            soma = soma + A[i][j] * x[j] #Atualiza a soma
        
        x[i] = (A[i][n] - soma) / A[i][i] #Determina o valor de x[i]

    return x #Retorna o vetor de resolução

def gauss_jordan(A,b):
    return 0

def decomposicao_lu(A,b):
    return 0

def fatoracao_cholesky(A,b):
    return 0

def jacobi_richardson(A,b, x0, max_iteracoes = 50, erro):
    return 0

def gauss_seidel(A,b,x0, max_iteracoes = 50, erro):
    
    xn = [0 for _ in range(len(A))]

    return 0
    

    


def main():
    matriz = [
        [10,2,-1],
        [-3,-6,2], 
        [1,1,5]
               ]

    b = [27, -61.5, -21.5]

    erro = 1e-5
    max_iteracoes = 50

    print(gauss_compacto(matriz,b))


if __name__ == "__main__":
    main()
