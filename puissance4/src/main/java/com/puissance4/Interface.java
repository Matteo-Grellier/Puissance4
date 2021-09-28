package com.puissance4;


public class Interface {
    
    public static void main(String[] args) {
        Test();
    }

    static void Test(){

        for (int i = 0; i < App.nbrLines; i++) {
            for (int j = 0; j < App.nbrColumns; j++) {
                System.out.print("|");
                if (App.arr().get(j) == null) {
                    System.out.print(" ");
                }else {
                    System.out.print(App.arr().get(j));
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

