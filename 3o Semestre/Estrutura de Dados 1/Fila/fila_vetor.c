#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX 6 //Tamanho máximo da fila = MAX - 1

int cheia_fila(int inicio, int fim)
{
    return ((fim + 1) % MAX) == inicio;
}

int tamanho_fila(int inicio, int fim)
{
    return (fim - inicio + MAX) % MAX;
}

void insere_fila(int *fila, int *fim, int info)
{
    fila[*fim] = info;
    *fim = (*fim + 1) % MAX; //Incrementa fim e se chega no máximo volta para 0
}

void pop_fila(int *fila, int *inicio)
{
    *inicio = (*inicio + 1) % MAX; //Incrementa o inicio e se chega no máximo volta para 0
}

int front_fila(int *fila, int inicio)
{
    return fila[inicio]; 
}

int vazia_fila(int *fila, int inicio, int fim)
{
    return inicio == fim;
}


int main()
{
    int fila[MAX];
    int inicio = 0;
    int fim = 0;

    insere_fila(fila, &fim, 10);
    insere_fila(fila, &fim, 20);
    insere_fila(fila, &fim, 30);
    insere_fila(fila, &fim, 40);
    insere_fila(fila, &fim, 50);

    pop_fila(fila, &inicio);

    insere_fila(fila, &fim, 35);
    
    pop_fila(fila, &inicio);

    insere_fila(fila, &fim, 42);

    while(!vazia_fila(fila, inicio, fim))
    {
        printf("%d\n", front_fila(fila, inicio));
        pop_fila(fila, &inicio);
    }




}
