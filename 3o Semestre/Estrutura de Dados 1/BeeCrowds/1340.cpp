#include <queue>
#include <stack>
#include <iostream>


using namespace std;

int main()
{
    int p,f,fp;
    stack<int> pilha;
    queue<int> fila;
    priority_queue<int> fila_prioridade;


    int num_testes;

    

    while(cin >> num_testes)
    {
        p = f = fp = 1;
        while(!pilha.empty()){pilha.pop();}
        while(!fila.empty()){fila.pop();}
        while(!fila_prioridade.empty()){fila_prioridade.pop();}
        
        for(int i = 0; i < num_testes; i++)
        {
            int op, num;

            cin >> op >> num;

            if(op == 1)
            {
                if(p){pilha.push(num);}
                if(f){fila.push(num);}
                if(fp){fila_prioridade.push(num);}
            }
            else
            {
                if(p)
                {
                    if(pilha.empty() || pilha.top() != num)
                    {
                        p = false;
                    }
                    else
                    {
                        pilha.pop();
                    }
                }
                if(f)
                {
                    if(fila.empty() || fila.front() != num)
                    {
                        f = false;
                    }
                    else
                    {
                        fila.pop();
                    }
                }
                if(fp)
                {
                    if(fila_prioridade.empty() || fila_prioridade.top() != num)
                    {
                        fp = false;
                    }
                    else
                    {
                        fila_prioridade.pop();
                    }
                }
            }
        }

        if (p && !f && !fp)
            cout << "stack" << endl;
        else if (!p && f && !fp)
            cout << "queue" << endl;
        else if (!p && !f && fp)
            cout << "priority queue" << endl;
        else if (!p && !f && !fp)
            cout << "impossible" << endl;
        else
            cout << "not sure" << endl;
    }


}