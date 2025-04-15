#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


//Implementação de pilha utilizando listas encadeadas.

typedef struct reg *no;

struct reg
{
    int info;
    no prox;
};

void inicia_pilha(no *pilha){*pilha = NULL;}

void adiciona_pilha(no *pilha, int info)
{
    no novo = (no)malloc(sizeof(struct reg));
    novo->info = info;
    novo->prox = *pilha;

    *pilha = novo;
}

void pop(no *pilha)
{
    if(*pilha == NULL) return;

    no p = *pilha;
    *pilha = (*pilha)->prox;
    free(p);
}

int frente_pilha(no *pilha)
{
    return (*pilha)->info;
}

int tamanho_pilha(no pilha)
{
    if(pilha == NULL) return 0;

    int tamanho = 0;

    no p = pilha;
    while(p != NULL)
    {
        tamanho++;
        p = p->prox;
    }

    return tamanho;
}

int esta_vazia(no *pilha)
{
    return *pilha == NULL;
}


int main()
{
    no pilha;

    inicia_pilha(&pilha);

    adiciona_pilha(&pilha, 10);
    adiciona_pilha(&pilha, 20);
    adiciona_pilha(&pilha, 30);
    adiciona_pilha(&pilha, 40);
    adiciona_pilha(&pilha, 50);
    adiciona_pilha(&pilha, 60);

    printf("%d\n\n", lenght(pilha));

    while(pilha != NULL)
    {
        printf("%d\n", front(&pilha));
        pop(&pilha);
    }
}
