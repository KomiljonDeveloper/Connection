package com.example.usercard.test;

import com.example.usercard.model.Com;


public class UserTest {
    public static boolean checkPassword(String password){
        int a = 0,b=0,c=0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i)>=48 && password.charAt(i)<=57)
                a++;
            else if (password.charAt(i)>=65 && password.charAt(i)<=90)
                b++;
            else if (password.charAt(i)>=97 && password.charAt(i)<=122)
                c++;
        }
        return a >= 2 && b >= 2 && c >= 2;
    }

    public static boolean checkEmail(String email){
        String[] split = email.split("@");
        String[] split1 = split[1].split("\\.");
        if (split.length==2){
            return (split1[0].equals(Com.gmail.getTitle())||split1[0].equals(Com.mail.getTitle())||split1[0].equals(Com.yahoo.getTitle())) && split1[1].equals("com");
        }
       return false;

    }

}
