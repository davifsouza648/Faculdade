#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct reg *arvore;
typedef struct reg2 *no;

struct reg
{
    int info;
    arvore esquerda, direita;
};

struct reg2
{
    arvore info;
    no prox;
};

typedef struct
{
    no inicio;
    no fim;
}Fila;

//Código Fila dinâmica
void inicia_fila(Fila *f)
{
    f->inicio = NULL;
    f->fim = NULL;
}

void insere_fila(Fila *f, arvore info)
{
    no novo = (no)malloc(sizeof(struct reg2));
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

arvore front_fila(Fila *f)
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

//Código de árvore
void inicia_arvore(arvore *arv)
{
    *arv = NULL;
}

void insere_arvore(arvore *arv, int info)
{
    if(*arv == NULL)
    {
        arvore novo = (arvore)malloc(sizeof(struct reg));
        novo->info = info;
        novo->esquerda = NULL;
        novo->direita = NULL;
        *arv = novo;
        return;
    }

    if(info < (*arv)->info) insere_arvore(&(*arv)->esquerda, info);
    else if(info > (*arv)->info) insere_arvore(&(*arv)->direita, info);
    else{return;}
}

void pre_order(arvore arv) //Pai, esquerda, direita
{
    if(arv == NULL) return;
   
    printf("%d ", arv->info);
    pre_order(arv->esquerda);
    pre_order(arv->direita);

}

void in_order(arvore arv) //Esquerda,pai,direita
{
    if(arv == NULL) return;

    in_order(arv->esquerda);
    printf("%d ", arv->info);
    in_order(arv->direita);
}

void pos_order(arvore arv) //Esquerda, direita, pai
{
    if(arv == NULL) return;
    pos_order(arv->esquerda);
    pos_order(arv->direita);
    printf("%d ", arv->info);
}

void remove_arvore(arvore *arv, int num)
{

}

void impressão_nivel(arvore arv)
{
    if(arv == NULL) return;

    Fila fila;
    inicia_fila(&fila);

    insere_fila(&fila, arv);

    while(!fila_vazia(&fila))
    {
        int tam = tamanho_fila(&fila);
        for(int i = 0; i < tam; i++)
        {
            printf("%d ", front_fila(&fila)->info);

            if(front_fila(&fila)->esquerda != NULL) insere_fila(&fila, front_fila(&fila)->esquerda);
            if(front_fila(&fila)->direita != NULL) insere_fila(&fila, front_fila(&fila)->direita);

            pop_fila(&fila);
        }

        printf("\n");
    }

}

int main()
{
    arvore arv;

    inicia_arvore(&arv);

    insere_arvore(&arv, 15);
    insere_arvore(&arv, 7);
    insere_arvore(&arv, 4);
    insere_arvore(&arv, 17);
    insere_arvore(&arv, 19);
    insere_arvore(&arv, 16);
    insere_arvore(&arv, 8);
    insere_arvore(&arv, 5);
    // insere_arvore(&arv, 19);
    // insere_arvore(&arv, 19);


    printf("Pre ordem: ");
    pre_order(arv);

    printf("\nIn ordem: ");
    in_order(arv);

    printf("\nPos ordem: ");
    pos_order(arv);

    printf("\nImpressão nivel:\n");
    impressão_nivel(arv);
}
