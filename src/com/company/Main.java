package com.company;

/**
 * Created by theo on 16.07.16.
 */

import java.math.BigInteger;
import java.util.Scanner;

import static java.lang.System.out;

public class Main {

    private static long p,q,c,message;

    public static void main(String[] args) {


        //Eingabe von P und Q
        out.println("Bitte erste Primzahl p eingeben.");
        Scanner pscan = new Scanner(System.in);
        long p = pscan.nextLong();

        if (isPrim(p)) {
            out.println("P ist eine Primzahl");
        } else {
            out.println("P ist keine Primzahl. Programm stoppt.");
            System.exit(1); //Programm beenden
        }

        out.println("Bitte erste Primzahl q eingeben.");
        Scanner qscan = new Scanner(System.in);
        long q = qscan.nextLong();
        if (isPrim(q)) {
            out.println("Q ist eine Primzahl");
        } else {
            out.println("Q ist keine Primzahl. Programm stoppt.");
            System.exit(1); //Programm beenden
        }
        if (p == q) {
            out.println("P darf nicht (!=) Q sein! Bitte Programm neustarten.");
            System.exit(1);
        }

        // n berechnen
        long n = p * q;
        System.out.println("Testausgabe: n = p * q = " + n);

        //phi berechnen
        System.out.println("Testausgabe: phi(n)=(p-1)*(q-1) = " + phi(p, q));

        //Eingabe von der Nachricht M
        System.out.println("Bitte Nachricht m eingeben.");
        Scanner mscan = new Scanner(System.in);
        long message = mscan.nextLong();

        //Eingabe des Verschlüsselungsschlüssel
        System.out.println("Bitte Verschlüsselungsschlüssel c eingeben.");
        Scanner cscan = new Scanner(System.in);
        long c = cscan.nextLong();

        if (3 <= c && c < phi(p, q)) {
            if (isTeilerfremd(c, phi(p, q))) {

                System.out.println("Verschlüsselungsschlüssel kann verwendet werden, da 3 <= C < PHI und C, PHI teilerfremd sind.");

            } else {

                System.out.println("C: " + c + " ...kann nicht verwendet werden, Programm neustarten.");
                System.exit(1);
            }
        }

        //Verschlüsselung
        System.out.println("Verschlüsselte Nachricht:");
        System.out.println(crypt(message,c,n));

        //Entschlüsselung
        System.out.println("Entschlüsselte Nachricht");
        System.out.println(decrypt(message,getDecryptKey(),n));
    }

    private static boolean isPrim(final long value) {
        if (value <= 2) {
            return (value == 2);
        }
        for (int i = 2; i * i <= value; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static long potenz(long basis, long exponent) {
        long x = basis;
        for (int j = 1; j < exponent; j++) {
            x = basis;
        }
        return x;
    }

    private static boolean isTeilerfremd(long a, long b){
        //ggt berechnen & so überprüfen ob das ganze teilerfremd ist.
        while (b != 0){
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        return a == 1;
    }

    private static long phi(long p, long q){
        //phi(n)=(p-1)*(q-1)
        return (q - 1) * (p - 1);
    }

    private static long crypt(long message, long cryptKey, long n){
        /*
        v(m) = m^c mod n
        v = m^c mod n
         */
        long v = 0;
        for (int i = 1; i <= cryptKey; i++){ //MOD besonders oft auf v anweden
           v = potenz(message, cryptKey) % n;
        }
        return v;
    }

    private static long decrypt(long message, long decryptKey, long n){
        long e = 0;
        for (int i = 1; i <= decryptKey; i++){
            e = crypt(message,decryptKey,n) * decryptKey  % n;
        }
        return e;
    }

    private static long getDecryptKey(){
        long d = 1;
        /*
        Was läuft hier falsch?

        for (;true;d++){
            System.out.println(d);
            System.out.println("Entschlüsselungsschlüssel generieren...");
            if ((c*d)%phi(p,q) != 1) {
                break;
            }
        }
        */

        /*
        while ((c*d)%phi(p,q) != 1){
            System.out.println("Generieren... " + d);
            d++;
        }
        */
        for (;true;d++) {
            System.out.println("Generieren... " + d);
            if ((c*d)%phi(p,q) != 1) {
                break;
            }
        }
        return d;
    }
}
