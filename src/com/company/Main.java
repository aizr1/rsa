package com.company;

import java.math.BigInteger;
import java.util.Scanner;

class Main {
    //Variablen deklarieren.
    private BigInteger msg, p, q, n, e, decryptKey;

    public static void main(String[] args) {
        //Eingabe von P und Q
        System.out.println("Bitte erste Primzahl p eingeben.");
        Scanner pscan = new Scanner(System.in);
        BigInteger p = pscan.nextBigInteger();
        if (isPrim(p)){
            System.out.println("P ist eine Primzahl");
        } else {
            System.out.println("P ist keine Primzahl. Programm stoppt.");
            System.exit(1); //Programm beenden
        }

        System.out.println("Bitte erste Primzahl q eingeben.");
        Scanner qscan = new Scanner(System.in);
        BigInteger q = qscan.nextBigInteger();
        if (isPrim(q)){
            System.out.println("Q ist eine Primzahl");
        } else {
            System.out.println("Q ist keine Primzahl. Programm stoppt.");
            System.exit(1); //Programm beenden
        }
        if (p.equals(q)) {
            System.out.println("P darf nicht (!=) Q sein! Bitte Programm neustarten.");
        }

        // n berechnen
        BigInteger n = p.intValue() * q.intValue();
        System.out.println("Testausgabe: n = p * q = " + n);

        //phi berechnen
        System.out.println("Testausgabe: phi(n)=(p-1)*(q-1)" + phi(p, q));

        //Eingabe von der Nachricht M
        System.out.println("Bitte Nachricht m eingeben.");
        Scanner mscan = new Scanner(System.in);
        BigInteger msg = mscan.nextBigInteger();

        //Eingabe des Verschlüsselungsschlüssel
        System.out.println("Bitte Verschlüsselungsschlüssel c eingeben.");
        Scanner cscan = new Scanner(System.in);
        BigInteger c = cscan.nextBigInteger();

        //Ist der Verschlüsselungsschlüssel C richtig gewählt?
        if (3 <= c && c < phi(p, q)) {
            if (isTeilerfremd(c, phi(p, q))) {
                System.out.println("Verschlüsselungsschlüssel kann verwendet werden, da 3 <= C < PHI und C, PHI teilerfremd sind.");
            } else {
                System.out.println("C: " + c + " ...kann nicht verwendet werden, Programm neustarten.");
                System.exit(1);
            }
        }

        //Nachricht verschlüsseln und ausgeben.
        System.out.println("Verschlüsselte Nachricht:");
        BigInteger mCrypt = crypt(msg, c, n); //Ausgabe der Methode crypt wird an mCrypt (Verschlüsselte Nachricht) übergeben.
        System.out.println(mCrypt);

        //Nachricht entschlüsseln
        //Zuerst Entschlüsselungsschlüssel d berechnen.
        System.out.println("Verschlüsselungsschlüssel d berechnen...");
        BigInteger decryptKey = 1;

        for (;true; decryptKey++){
            System.out.println(decryptKey);
            if ((c*decryptKey) % phi(p,q) == 1) {
                break;
            }
        }

        System.out.println();

        System.out.println("Entschlüsselte Nachricht:");
        BigInteger mDecrypt = decrypt(msg,decryptKey,n);
        System.out.println(mDecrypt);
    }

    private static BigInteger phi(BigInteger p, BigInteger q){
        //phi(n)=(p-1)*(q-1)
        BigInteger z;
        z = (q - 1) * (p - 1);
        return z;
    }

    private static boolean isTeilerfremd(BigInteger a, BigInteger b){
        
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


    private static BigInteger crypt(BigInteger msg, BigInteger c, BigInteger n){
        BigInteger modulo = new BigInteger(""+n);
        BigInteger temp = null;
        temp = Main.potenz(msg,c);
        return temp.remainder(modulo); //encryptedMessage
        /*
        v(m) = m^c mod n
        v = m^c mod n
         */
    }
    private static BigInteger decrypt(BigInteger m, BigInteger decryptKey, BigInteger n){
        BigInteger encryptedMessage = new BigInteger(""+crypt(m,decryptKey,n));
        BigInteger modulo = new BigInteger(""+n);
        BigInteger temp = null;
        temp = BigInteger.valueOf(Main.potenz(encryptedMessage.longValue(),decryptKey));
        return temp.remainder(modulo);
    }

    private static boolean isPrim(final BigInteger value) {
        if (value <= 2) {
            return (value == 2);
        }
        for (BigInteger i = 2; i * i <= value; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }

    private BigInteger getRandomC() { //C berechnen.
        BigInteger cryptKey =  3;
        while (isTeilerfremd(cryptKey,phi(p,q))){
            cryptKey++;
        }
        return cryptKey;
    }

    private static BigInteger potenz(BigInteger basis, BigInteger exponent) {
        BigInteger x = basis;
        for (int j = 1; j < exponent.intValue(); j++) {
            x = basis;
        }
        return x;
    }

}
