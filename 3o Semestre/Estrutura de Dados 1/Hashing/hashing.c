#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include <math.h>

#define TAM 50
#define VAZIO -1

typedef struct no
{
    int chave;
    int info;
    struct no *prox;
}no;

typedef struct
{
    int tamanho;
    no **elementos;
}tabela;


void inicializa_tabela(tabela *t, int tamanho)
{
    t->tamanho = tamanho;
    t->elementos = malloc(tamanho * sizeof(no*));

    if(t->elementos == NULL)
    {
        exit(0);
    }

    for(int i = 0; i < tamanho; i++)
    {
        t->elementos[i] = NULL;
    }


}

int funcao_hash(int chave, int tamanho)
{
    
    double A = 0.6180339887; 
    double frac = (chave * A) - floor(chave * A);
    return (int)(tamanho * frac);
}

void insere_tabela(tabela *t, int chave, int info)
{
    int pos = funcao_hash(chave, t->tamanho);

    no *novo = malloc(sizeof(no));
    novo->chave = chave;
    novo->info = info;
    novo->prox = NULL;

    no *p = t->elementos[pos];
    
    if(p == NULL)
    {
        t->elementos[pos] = novo;
    }
    else
    {
        while(p -> prox != NULL){p = p->prox;}
        p->prox = novo;
    }

}

int busca(tabela *t, int chave)
{
    int pos = funcao_hash(chave, t->tamanho);

    no *p = t->elementos[pos];
    while(p != NULL && p->chave != chave)
    {
        p=p->prox;
    }

    if(p != NULL)
    {
        printf("%d achado na posição %d\n", chave, pos);
    }
    return p != NULL;

}

void remove_tabela(tabela *t, int chave)
{
    int pos = funcao_hash(chave, t->tamanho);
    
    no *anterior = NULL;
    no *p = t->elementos[pos];

    while(p != NULL && p->chave != chave)
    {
        anterior = p;
        p = p->prox;
    }

    if(p == NULL) return; 

    if(anterior == NULL)
    {
        t->elementos[pos] = p->prox;
    }
    else
    {
        anterior->prox = p->prox;
    }

    printf("Removido: %d %d\n", p->chave, p->info);
    free(p);
}

void imprime_tabela(tabela *t)
{
    for(int i = 0; i < t->tamanho; i++)
    {
        no *p = t->elementos[i];

        if(p == NULL) continue;
        printf("%d -> (%d,%d)", i, p->chave, p->info);
        p = p->prox;
        while(p != NULL)
        {
            printf("-> (%d,%d)", p->chave, p->info);
            p = p->prox;
        }

        printf("\n");
    }
}

int main()
{
    tabela t;
    inicializa_tabela(&t, 15);

    insere_tabela(&t, 15, 10);
    insere_tabela(&t, 7, 2);
    insere_tabela(&t, 12, 5);
    insere_tabela(&t, 11, 2);
    insere_tabela(&t, 102, 77);
    insere_tabela(&t, 98, 43);
    insere_tabela(&t, 55, 22);
    insere_tabela(&t, 43, 5);
    insere_tabela(&t, 27, 5);

    imprime_tabela(&t);


}
