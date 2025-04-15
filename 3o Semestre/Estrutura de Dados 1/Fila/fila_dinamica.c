#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


typedef struct reg *no;

struct reg
{
    int info;
    no prox, fim;
};

typedef struct
{
    no inicio;
    no fim;
}Fila;

void inicia_fila(Fila *f)
{
    f->inicio = NULL;
    f->fim = NULL;
}

void insere_fila(Fila *f, int info)
{
    no novo = (no)malloc(sizeof(struct reg));
    novo->info = info;
    novo->prox = NULL;

    if (f->inicio == NULL)
    {
        f->inicio = f->fim = novo;
        return;
    }

    f->fim->prox = novo;
    f->fim = novo;
    
}

void pop_fila(Fila *f)
{
    if (f->inicio == NULL) return;

    no temp = f->inicio;
    f->inicio = f->inicio->prox;

    if (f->inicio == NULL) f->fim = NULL;
    
    free(temp);
}

int front_fila(Fila *f)
{
    return f->inicio->info;
}

int tamanho_fila(Fila *f)
{
    int tam = 0;
    no p = f->inicio;
    while (p != NULL)
    {
        tam++;
        p = p->prox;
    }
    return tam;
}

int fila_vazia(Fila *f)
{
    return f->inicio == NULL;
}

int main()
{
    Fila fila;
    inicia_fila(&fila);

    insere_fila(&fila, 10);
    insere_fila(&fila, 20);
    insere_fila(&fila, 30);
    insere_fila(&fila, 40);
    insere_fila(&fila, 50);
    insere_fila(&fila, 60);
    insere_fila(&fila, 70);
    insere_fila(&fila, 80);

    printf("Tamanho fila: %d\n\nConteúdos fila:\n", tamanho_fila(&fila));

    while (!fila_vazia(&fila))
    {
        printf("%d\n", front_fila(&fila));
        pop_fila(&fila);
    }

    return 0;
}
