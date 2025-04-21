#include <stdio.h>
#include <stdlib.h>
#include <math.h>


typedef struct reg *arvore;
typedef struct reg2 *no;

struct reg
{
    int info;
    int altura;
    arvore esquerda, direita;
};

struct reg2
{
    arvore info;
    no prox;
};

typedef struct
{
    no primeiro,ultimo;
}Pilha;

int max(int a, int b)
{
    return (a > b) ? a : b;
}

void inicia_pilha(Pilha *pilha)
{
    pilha->primeiro = NULL;
    pilha->ultimo = NULL;
}

void insere_pilha(Pilha *pilha, arvore arv)
{
    no novo = (no)malloc(sizeof(struct reg2));
    novo->info = arv;
    novo->prox = NULL;
    
    if(pilha->primeiro == NULL)
    {
        pilha->primeiro = novo;
        pilha->ultimo = novo;
    }
    else
    {
        pilha->ultimo->prox = novo;
        pilha->ultimo = novo;
    }
}

void pop(Pilha *pilha)
{
    if(pilha->primeiro == NULL) return;

    if(pilha->primeiro == pilha->ultimo)
    {
        no temp = pilha->primeiro;
        pilha->primeiro = NULL;
        pilha->ultimo = NULL;

        free(temp);
        return;
    }

    no temp = pilha->primeiro;
    pilha->primeiro = temp->prox;

    free(temp);

}

arvore front(Pilha pilha)
{
    if(pilha.primeiro != NULL)
        return pilha.primeiro->info;
    return NULL;
}

int is_empty(Pilha pilha)
{
    return pilha.primeiro == NULL;
}

int lenght(Pilha pilha)
{
    int tam = 0;
    no p = pilha.primeiro;

    while(p != NULL)
    {
        p = p->prox;
        tam++;
    }

    return tam;
}

void inicia_arvore(arvore *arv)
{
    *arv = NULL;
}

int calcula_altura(arvore arv)
{
    if(arv == NULL) return 0;

    return arv->altura;
}

void atualiza_altura(arvore *arv)
{
    int alt_esq = (*arv)->esquerda ? (*arv)->esquerda->altura : 0;
    int alt_dir = (*arv)->direita ? (*arv)->direita->altura : 0;
    (*arv)->altura = max(alt_esq, alt_dir) + 1;
}

int fator_balanceamento(arvore arv)
{
    if (arv == NULL) return 0;
    return calcula_altura(arv->esquerda) - calcula_altura(arv->direita);
}

void rotacao_direita(arvore *arv)
{
    arvore x = (*arv)->esquerda;
    arvore t2 = x->direita;

    x->direita = (*arv);
    (*arv)->esquerda = t2;

    atualiza_altura(&(*arv));
    atualiza_altura(&x);

    (*arv) = x;
}

void rotacao_esquerda(arvore *arv)
{
    arvore y = (*arv)->direita;
    arvore t2 = y->esquerda;

    y->esquerda = (*arv);
    (*arv)->direita = t2;

    atualiza_altura(&(*arv));
    atualiza_altura(&y);

    (*arv) = y;
}

void insere_avl(arvore *arv, int info)
{
    if(*arv == NULL)
    {
        (*arv) = (arvore)malloc(sizeof(struct reg));
        (*arv)->info = info;
        (*arv)->esquerda = NULL;
        (*arv)->direita = NULL;
        (*arv)->altura = 1;
        return;
    }

    if(info < (*arv)->info){insere_avl(&(*arv)->esquerda, info);}
    else if(info > (*arv)->info){insere_avl(&(*arv)->direita, info);}
    else return;

    atualiza_altura(arv);
    int fb = fator_balanceamento(*arv);

    if(fb > 1 && info < (*arv)->esquerda->info)
    {
        rotacao_direita(arv);
    }

    else if(fb < -1 && info > (*arv)->direita->info)
    {
        rotacao_esquerda(arv);
    }

    else if(fb > 1 && info > (*arv)->esquerda->info)
    {
        rotacao_esquerda(&(*arv)->esquerda);
        rotacao_direita(arv);
    }
    else if(fb < -1 && info < (*arv)->direita->info)
    {
        rotacao_direita(&(*arv)->direita);
        rotacao_esquerda(arv);
    }



}

void impressao_nivel(arvore arv)
{
    if (arv == NULL) return;

    Pilha fila;
    inicia_pilha(&fila);
    insere_pilha(&fila, arv);

    while (!is_empty(fila))
    {
        int nivel_tam = lenght(fila);
        for (int i = 0; i < nivel_tam; i++)
        {
            arvore atual = front(fila);
            pop(&fila);

            printf("%d ", atual->info);

            if (atual->esquerda) insere_pilha(&fila, atual->esquerda);
            if (atual->direita) insere_pilha(&fila, atual->direita);
        }
        printf("\n");
    }
}

int main()
{
    arvore arv;

    inicia_arvore(&arv);

    insere_avl(&arv, 10);
    insere_avl(&arv, 20);
    insere_avl(&arv, 30);
    insere_avl(&arv, 40);
    insere_avl(&arv, 50);
    insere_avl(&arv, 60);
    insere_avl(&arv, 70);
    insere_avl(&arv, 80);
    insere_avl(&arv, 90);
    insere_avl(&arv, 100);
    insere_avl(&arv, 110);
    insere_avl(&arv, 120);
    insere_avl(&arv, 130);
    insere_avl(&arv, 140);
    insere_avl(&arv, 150);
    insere_avl(&arv, 160);
    insere_avl(&arv, 170);
    insere_avl(&arv, 180);
    insere_avl(&arv, 190);

    impressao_nivel(arv);

}

