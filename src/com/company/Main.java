package com.company;

import java.util.Scanner;

import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {

        //Eingabe von P und Q
        out.println("Bitte erste Primzahl p eingeben.");
        Scanner pscan = new Scanner(in);
        long p = pscan.nextLong();

        out.println("Bitte erste Primzahl q eingeben.");
        Scanner qscan = new Scanner(in);
        long q = qscan.nextLong();

        if (p == q){
            System.out.println("P darf != Q sein! Bitte Programm neustarten.");
        }

        // n berechnen
        long n = p*q;
        out.println("n = p * q = " + n);

        //phi berechnen
        System.out.println("Testausgabe: phi(n)=(p-1)*(q-1)");
        System.out.println(phi(p,q));

        //Eingabe von der Nachricht M
        out.println("Bitte Nachricht m eingeben.");
        Scanner mscan = new Scanner(in);
        long m = mscan.nextLong();

        //Eingabe des Verschlüsselungsschlüssel
        out.println("Bitte Verschlüsselungsschlüssel c eingeben.");
        Scanner cscan = new Scanner(in);
        long c = cscan.nextLong();
        
        //Ist der Verschlüsselungsschlüssel C richtig gewählt?
        if(3<=c && c<phi(p,q)){
            if (isTeilerfremd(c, phi(p, q))){
                System.out.println("C kann verwendet werden, da 3 <= C < PHI und C, PHI teilerfremd sind.");
            } else System.out.println("C kann nicht verwendet werden, Programm neustarten.");
        }

        //Nachricht verschlüsseln und ausgeben.
        System.out.println("Verschlüsselte Nachricht:");
        System.out.println(crypt(m,c,n));

        //Nachricht entschlüsseln
        //Zuerst Entschlüsselungsschlüssel d berechnen.
        long d = 1;
        while (c * d != 1 % phi(p,q)){
            d++;
        }
    }

    private static long phi(long p, long q){
        //phi(n)=(p-1)*(q-1)
        long z;
        z = (q - 1) * (p - 1);
        return z;
    }

    private static boolean isTeilerfremd(long a, long b){
        
        //ggt berechnen & so überprüfen ob das ganze teilerfremd ist.
        while (b != 0){
            if(a > b){
                a = a - b;
            } else {
                b = b - a;
            }
        }
        
        if (a == 1) return true;
        else return false;
    }

    private static long crypt(long m, long c, long n){
        long v = 1;
        for (int i=1; i<=c; i++){
            v = v * m % n; //Potenzieren nach der klassichen Art notwendig. Math.pow wird aufgrund der größe der Zahlen nicht funktionieren.
            //modulo sorgt für n-1 kleine zahlen...
        }
        return v;
    }

    private static long decrypt(){
        //m=v(m) pow d mod n

    }

}
