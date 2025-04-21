#include <iostream>
#include <string>
#include <stack>

using namespace std;

int main()
{
    int n;
    string expressao;
    stack<char> pilha;

    cin >> n;

    for(int i = 0; i < n; i++)
    {
        cin >> expressao;
        int ok = 1;
        for(char c:expressao)
        {
            if(c == '(' || c == '{' || c == '[')pilha.push(c);

            if((c == ')' || c == '}' || c == ']') && pilha.empty())
            {
                ok = 0;
                break;
            }

            if(c == ')')
            {
                if(pilha.top() != '(')
                {
                    ok = 0;
                    break;
                }
                else
                {
                    pilha.pop();
                }
            }
            else if(c == '}')
            {
                if(pilha.top() != '{')
                {
                    ok = 0;
                    break;
                }
                else
                {
                    pilha.pop();
                }
            }
            else if(c == ']')
            {
                if(pilha.top() != '[')
                {
                    ok = 0;
                    break;
                }
                else
                {
                    pilha.pop();
                }
            }
            


        }

        if(!pilha.empty()) ok = 0;


        while(!pilha.empty()) pilha.pop();
        ok == 1 ? cout << 'S' : cout << 'N';
        cout << endl;

        

    }
}