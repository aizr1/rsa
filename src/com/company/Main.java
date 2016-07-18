package com.company;

/**
 * Created by theo on 16.07.16.
 */

import java.util.Scanner;

import static java.lang.System.out;

public class Main {

    //GLOBALE VARIABLEN
    private static long
            p, //PRIMZAHL 1
            q, //PRIMZAHL 2
            c, //VERSCHLÜSSELUNGSSCHLÜSSEL
            m, //GANZZAHLINGE NACHRICHT
            vm, //VERSSCHLÜSSELTE NACHRICHT
            d, //ENTSCHLÜSSELUNGSSCHLÜSSEL
            phi, //PHI
            n; //


    public static void main(String[] args) {

        //Eingabe von P und Q sowie Überprüfung ob gultige Primzahl
        out.println("Bitte erste Primzahl p eingeben.");
        Scanner pscan = new Scanner(System.in);
        p = pscan.nextLong();

        if (isPrim(p)) {
            out.println("P ist eine Primzahl");
        } else {
            out.println("P ist keine Primzahl. Programm stoppt.");
            System.exit(1); //Programm beenden
        }

        out.println("Bitte erste Primzahl q eingeben.");
        Scanner qscan = new Scanner(System.in);
        q = qscan.nextLong();
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
        n = p * q;
        System.out.println("Testausgabe: n = p * q = " + n);

        //phi berechnen
        phi = phi(p,q);
        System.out.println("Testausgabe: phi(n)=(p-1)*(q-1) = " + phi);

        //Eingabe von der Nachricht M
        System.out.println("Bitte Nachricht m eingeben.");
        Scanner mscan = new Scanner(System.in);
        m = mscan.nextLong();

        //Eingabe des Verschlüsselungsschlüssel
        System.out.println("Bitte Verschlüsselungsschlüssel c eingeben.");
        Scanner cscan = new Scanner(System.in);
        c = cscan.nextLong();

        if (3 <= c && c < phi) {
            if (isTeilerfremd(c, phi)) {

                System.out.println("Verschlüsselungsschlüssel kann verwendet werden, da 3 <= C < PHI und C, PHI teilerfremd sind.");

            } else {

                System.out.println("C: " + c + " ...kann nicht verwendet werden, Programm neustarten.");
                System.exit(1);
            }
        }

        //Verschlüsselung
        System.out.println("Verschlüsselte Nachricht:");
        System.out.println(crypt(m,c,n));
        vm = crypt(m,c,n);
        System.out.println("vm = " + vm);

        //Entschlüsselung
        System.out.println("Entschlüsselte Nachricht");
        System.out.println(decrypt(vm,n));
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

    private static long crypt(long m, long c, long n){
        /*
        v(m) = m^c mod n
        v = m^c mod n
         */
        long v = 1;
        long x = m % n;
        for (int i = 1; i <= c; i++){ //MOD besonders oft auf v anweden
           v = v*x;
        }
        v = v%n;
        return v;
    }

    private static long decrypt(long vm, long n){

        while (c * d % phi != 1)    //Entschlüsselungsschlüssel
        {
            d=d+1;
            System.out.print(d+", ");
        }
        System.out.println();
        System.out.println("Testoutput: vm = " + vm + " d = " + d + " n = " + n + " m = " + m);

        long m = 1;
        for (long i = 1; i < (d+1); i++){
            m = (m * vm) % n;
            System.out.print(m+", ");
        }
        return m;
    }
}
