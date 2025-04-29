package ru.kolomoets.cryptanalyzer.application;


import ru.kolomoets.cryptanalyzer.core.CaesarCipher;


public class Application {

    public static void main( String[] args ) {

        CaesarCipher cs = new CaesarCipher();
        System.out.println("ая az 19 !`");
        String encrypt = cs.encrypt("ая az 19 !`", 1);
        String encrypt1 = cs.encrypt("☺" , 10);
        System.out.println(encrypt);
        System.out.println(encrypt1);

        System.out.println("********************");

        String decrypt = cs.decrypt("ба!ba!20!@", 1);
        System.out.println(decrypt);
    }
    }

