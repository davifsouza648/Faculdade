#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include <math.h>

#define MAX 100

typedef struct
{
    int elementos[MAX];
    int tamanho;
}heap;

void inicia_heap(heap *hp)
{
    memset(hp->elementos, 0, sizeof(hp->elementos));
    hp->tamanho = 0;
}

void troca(int *a, int *b)
{
    int temp = *a;
    *a = *b;
    *b = temp;
}

void heapify_up(heap *hp, int index)
{
    if(index == 0) return;

    int pai = (index - 1) / 2;
    if(hp->elementos[index] > hp->elementos[pai])
    {
        troca(&hp->elementos[index], &hp->elementos[pai]);
        heapify_up(hp, pai);
    }


}


void insert(heap *hp, int info)
{
    if(hp->tamanho >= MAX){return;}
    hp->elementos[hp->tamanho] = info;
    heapify_up(hp, hp->tamanho);

    hp->tamanho++;
}

void heapify_down(heap *hp, int index)
{
    int esquerda = 2 * index + 1;
    int direita = 2 * index + 2;
    int maior = index;

    if(esquerda < hp->tamanho && hp->elementos[esquerda] > hp->elementos[maior])
        maior = esquerda;
    if(direita < hp->tamanho && hp->elementos[direita] > hp->elementos[maior])
        maior = direita;
    

    if(maior != index)
    {
        troca(&hp->elementos[index],&hp->elementos[maior]);
        heapify_down(hp, maior);
    }

}

int top(heap *hp)
{
    return hp->elementos[hp->tamanho - 1];
}

void remove_heap(heap *hp)
{
    if(hp->tamanho == 0) return;

    hp->elementos[0] = hp->elementos[hp->tamanho - 1];
    hp->tamanho--;
    
    heapify_down(hp, 0);

}

void imprime_heap(heap *hp)
{
    for(int i = 0; i < hp->tamanho; i++)
    {
        printf("%d ", hp->elementos[i]);
    }
    printf("\n");
}

int main()
{
    heap hp;
    inicia_heap(&hp);

    insert(&hp, 30);
    insert(&hp, 40);
    insert(&hp, 10);
    insert(&hp, 20);
    insert(&hp, 70);
    insert(&hp, 50);
    insert(&hp, 90);

    remove_heap(&hp);
    remove_heap(&hp);

    imprime_heap(&hp);



}
