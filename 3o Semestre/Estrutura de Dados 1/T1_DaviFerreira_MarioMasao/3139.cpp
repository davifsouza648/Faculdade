//Davi Ferreira de Souza e Mário Masao Mukai
//RA: 241024676 e 241022321
//16/04/2025

#include <bits/stdc++.h>

using namespace std;

#define ll long long

int main(){
    ll numeroSeguidores , minimoSeguidores;

    cin>> numeroSeguidores >> minimoSeguidores;
    
    queue<ll> seguidoresPorDia;
    ll soma = 0;
    for(int i = 0; i<30; i++){
        ll num;
        cin >> num;
        seguidoresPorDia.push(num);
        soma+= num;
    } 
    
    ll cont = 0;
    while(numeroSeguidores < minimoSeguidores){
        ll media = (ll)ceil(soma/30.0);

        numeroSeguidores += media;

        soma-=seguidoresPorDia.front();
        seguidoresPorDia.pop();
        seguidoresPorDia.push(media);

        soma+=media;
        cont++;
    }
    cout << cont << "\n";

    return 0;
}
