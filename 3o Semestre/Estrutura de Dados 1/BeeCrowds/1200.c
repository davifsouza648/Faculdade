#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct reg *arvore;

struct reg
{
    char info;
    arvore esquerda, direita;
};


void inicia_arvore(arvore *arv)
{
    *arv = NULL;
}

void insere_arvore(arvore *arv, char info)
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

int busca_arvore(arvore arv, char c)
{
    if(arv == NULL) return 0;

    if(arv->info == c) return 1;

    if(arv->info > c) return busca_arvore(arv->esquerda, c);
    else return busca_arvore(arv->direita, c);
    
}

void pre_order(arvore arv, int *primeiro)
{
    if (arv == NULL) return;
    if (*primeiro)
        *primeiro = 0;
    else
        printf(" ");
    printf("%c", arv->info);
    pre_order(arv->esquerda, primeiro);
    pre_order(arv->direita, primeiro);
}

void in_order(arvore arv, int *primeiro)
{
    if (arv == NULL) return;
    in_order(arv->esquerda, primeiro);
    if (*primeiro)
        *primeiro = 0;
    else
        printf(" ");
    printf("%c", arv->info);
    in_order(arv->direita, primeiro);
}

void pos_order(arvore arv, int *primeiro)
{
    if (arv == NULL) return;
    pos_order(arv->esquerda, primeiro);
    pos_order(arv->direita, primeiro);
    if (*primeiro)
        *primeiro = 0;
    else
        printf(" ");
    printf("%c", arv->info);
}


int main()
{
    char entrada[100];
    arvore arv;

    inicia_arvore(&arv);

    while(fgets(entrada, sizeof(entrada), stdin) != NULL)
    {
        entrada[strcspn(entrada, "\n")] = '\0';
        if (strlen(entrada) == 0) continue;

        if (strcmp(entrada, "INFIXA") == 0)
        {
            int primeiro = 1;
            in_order(arv, &primeiro);
            printf("\n");
        }
        else if (strcmp(entrada, "PREFIXA") == 0)
        {
            int primeiro = 1;
            pre_order(arv, &primeiro);
            printf("\n");
        }
        else if (strcmp(entrada, "POSFIXA") == 0)
        {
            int primeiro = 1;
            pos_order(arv, &primeiro);
            printf("\n");
        }

        else if(entrada[0] == 'I')
        {
            insere_arvore(&arv, entrada[2]);
        }

        else
        {
            busca_arvore(arv, entrada[2]) ? printf("%c existe\n", entrada[2]) : printf("%c nao existe\n", entrada[2]);
        }


    }
}