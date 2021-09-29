package com.puissance4;


public class Interface {

    static void Test(){

        for (int i = 0; i <= App.nbrLines-1; i++) {
            for (int j = 0; j < App.nbrColumns; j++) {
                System.out.print("|");
                if (App.arr().get(i).get(i) == " ") {
                    System.out.print(" ");
                }else {
                    System.out.print(App.arr().get(j).get(i));
                }
            }
            System.out.print("|");
            System.out.println("");
        }
        for (int i = 0; i < App.nbrColumns*2; i++) {
            System.out.print("#");
        }
        System.out.println("");
        System.out.print("|");
        for (int i = 1; i <= App.nbrColumns; i++) {
            
            System.out.print(i);
            System.out.print("|");
        }
    }
    
}

