package com.company;

import java.util.Scanner;

public class Main {

    public long m, p ,q, n, e, v, decryptKey = 1;

    public static void main(String[] args) {

        //Eingabe von P und Q
        System.out.println("Bitte erste Primzahl p eingeben.");
        Scanner pscan = new Scanner(System.in);
        long p = pscan.nextLong();
        if (isPrim(p)){
            System.out.println("P ist eine Primzahl");
        } else {
            System.out.println("P ist keine Primzahl. Programm stoppt.");
            System.exit(1);
        }

        System.out.println("Bitte erste Primzahl q eingeben.");
        Scanner qscan = new Scanner(System.in);
        long q = qscan.nextLong();
        if (isPrim(q)){
            System.out.println("Q ist eine Primzahl");
        } else {
            System.out.println("Q ist keine Primzahl. Programm stoppt.");
            System.exit(1);
        }
        if (p == q) {
            System.out.println("P darf nicht (!=) Q sein! Bitte Programm neustarten.");
        }

        // n berechnen
        long n = p * q;
        System.out.println("Testausgabe: n = p * q = " + n);

        //phi berechnen
        System.out.println("Testausgabe: phi(n)=(p-1)*(q-1)" + phi(p, q));

        //Eingabe von der Nachricht M
        System.out.println("Bitte Nachricht m eingeben.");
        Scanner mscan = new Scanner(System.in);
        long m = mscan.nextLong();

        //Eingabe des Verschlüsselungsschlüssel
        System.out.println("Bitte Verschlüsselungsschlüssel c eingeben.");
        Scanner cscan = new Scanner(System.in);
        long c = cscan.nextLong();

        //Ist der Verschlüsselungsschlüssel C richtig gewählt?
        if (3 <= c && c < phi(p, q)) {
            if (isTeilerfremd(c, phi(p, q))) {
                System.out.println("C kann verwendet werden, da 3 <= C < PHI und C, PHI teilerfremd sind.");
            } else {
                System.out.println("C kann nicht verwendet werden, Programm neustarten.");
                System.exit(1);
            }
        }

        //Nachricht verschlüsseln und ausgeben.
        System.out.println("Verschlüsselte Nachricht:");
        long mCrypt = crypt(m, c, n);
        System.out.println(mCrypt);

        //Nachricht entschlüsseln
        //Zuerst Entschlüsselungsschlüssel d berechnen.
        System.out.println("Verschlüsselungsschlüssel d berechnen...");
        long decryptKey = 1;
        while ((c * decryptKey) % phi(p, q) == 1) {
            decryptKey++;
            System.out.print(decryptKey + "...");
        }

        System.out.println();

        System.out.println("Entschlüsselte Nachricht:");
        long mDecrypt = decrypt(m,decryptKey,n);
        System.out.println(mDecrypt);
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

        return a == 1;
    }

    private static long crypt(long m, long c, long n){
        long v = 1;
        for (int i=1; i<=c; i++){
            v = v * m % n; //Potenzieren nach der klassichen Art notwendig. Math.pow wird aufgrund der größe der Zahlen nicht funktionieren.
            //modulo sorgt für n-1 kleine zahlen...
        }
        return v;
    }
    private static long decrypt(long m, long decryptKey, long n){
        long encryptedMessage = crypt(m,decryptKey,n);
        for (int i = 1; i<=decryptKey; i++){
            encryptedMessage = encryptedMessage * m % n; //m=crypt(m) pow d mod n
        }
        return encryptedMessage;
    }

    private static boolean isPrim(final long value) {
        if (value <= 2) {
            return (value == 2);
        }
        for (long i = 2; i * i <= value; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }
}
