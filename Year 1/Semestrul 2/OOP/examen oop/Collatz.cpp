#include "Collatz.h"
#include <iostream>

using namespace std;


Nod::~Nod(){
    delete next;
}
Collatz::Collatz():head(nullptr){}
Collatz::~Collatz(){
    delete head;
}
void Collatz::add(int x){
        if(visited.find(x)!=visited.end())
            return;
        
        Nod* newNode=new Nod;
        newNode->info=x;
        newNode->next=nullptr;
        if(head==nullptr)
        {
            head=newNode;
        } 
        else 
        {
            Nod* current=head;
            while(current->next != nullptr){
                current=current->next;
            }
            current->next=newNode;
            newNode->previous1=current;
            if(current->info==x/2&&current->previous1!=nullptr){
                newNode->previous2=current->previous1;
            } else {
                newNode->previous2=current->previous2;
            }
        }
        visited[x]++;
        CollatzMate(newNode);
    }
void Collatz::CollatzMate(Nod* node){
        int x=node->info;
        while(x!=1){
            if (x%2==0)
                x/=2;
            else
                x=3*x+1;
            add(x);
        }
    }

void Collatz::printSequence(){
        Nod* current=head;
        while(current!=nullptr){
            cout << current->info << " ";
            current=current->next;
        }
        cout << endl;
    }