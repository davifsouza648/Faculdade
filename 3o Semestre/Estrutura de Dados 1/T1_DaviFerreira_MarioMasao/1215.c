//Davi Ferreira de Souza e Mário Masao Mukai
//RA: 241024676 e 241022321
//16/04/2025

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct reg *arvore;

struct reg {
    char palavra[100];
    arvore esquerda, direita;
};


void inicia_arvore(arvore *arv) 
{
    *arv = NULL;
}

void insere_arvore(arvore *arv, char *str) 
{
    if (*arv == NULL) 
    {
        arvore novo = (arvore)malloc(sizeof(struct reg));
        strcpy(novo->palavra, str);
        novo->esquerda = NULL;
        novo->direita = NULL;
        *arv = novo;
        return;
    }

    int cmp = strcmp(str, (*arv)->palavra);
    if (cmp == 0) return; 
    if (cmp < 0) insere_arvore(&(*arv)->esquerda, str);
    else insere_arvore(&(*arv)->direita, str);
}

void imprime_ordenado(arvore arv) 
{
    if (arv == NULL) return;
    imprime_ordenado(arv->esquerda);
    printf("%s\n", arv->palavra);
    imprime_ordenado(arv->direita);
}

void toMinusculo(char *str) 
{
    for (int i = 0; str[i]; i++) 
    {
        str[i] = tolower((unsigned char)str[i]);
    }
}

void limpaPontuacao(char *str) 
{
    int j = 0;
    char temp[100];
    for (int i = 0; str[i] != '\0'; i++)
    {
        if (isalpha((unsigned char)str[i])) 
        {
            temp[j++] = str[i];
        }
    }
    temp[j] = '\0';
    strcpy(str, temp);
}

int main() {
    arvore arv;
    inicia_arvore(&arv);

    char linha[1000];
    char palavra[100];
    int i, j;

    while (fgets(linha, sizeof(linha), stdin)) {
        i = 0;
        while (linha[i] != '\0') {
            j = 0;

            while (isalpha(linha[i])) 
            {
                palavra[j++] = tolower(linha[i]);
                i++;
            }

            palavra[j] = '\0';

            if (j > 0) 
            {
                insere_arvore(&arv, palavra);
            }

            while (linha[i] != '\0' && !isalpha(linha[i])) 
            {
                i++;
            }
        }
    }

    imprime_ordenado(arv);
    return 0;
}
