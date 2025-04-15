#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

//Implementação de pilha com vetor

void adiciona_pilha(int *pilha, int *final, int conteudo)
{
    pilha[(*final)] = conteudo;
    (*final)++;
}

void pop(int *pilha, int *final)
{
    (*final)--;
}

int frente_pilha(int *pilha, int final)
{
    return pilha[final - 1];
}

int pilha_vazia(int *pilha, int final)
{
    return final == 0;
}

int lenght_pilha(int *pilha, int final)
{
    return final;
}

int main()
{
    int *pilha = (int*)malloc(sizeof(int) * 50);
    int final = 0;

    adiciona_pilha(pilha, &final, 10);
    adiciona_pilha(pilha, &final, 20);
    adiciona_pilha(pilha, &final, 30);
    adiciona_pilha(pilha, &final, 40);
    adiciona_pilha(pilha, &final, 50);
    adiciona_pilha(pilha, &final, 60);

    while(!pilha_vazia(pilha, final))
    {
        printf("%d\n", frente_pilha(pilha, final));
        pop(pilha, &final);
    }

    


}
