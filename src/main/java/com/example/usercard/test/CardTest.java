package com.example.usercard.test;

public class CardTest {
    public static boolean isNumber(String number){
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i)<48 && number.charAt(i)>57){
                return false;
            }
        }
        return true;
    }
}
