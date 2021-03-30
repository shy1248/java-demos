package me.shy.demo.base;

public class TestArgs {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java %n \"n1\"  \"op\"  \"n2\"");
            System.exit(-1);
        }
        try {
            double d1;
            double d2;
            double d = 0;
            d1 = Double.parseDouble(args[0]);
            d2 = Double.parseDouble(args[2]);
            if (args[1].equals("+")) {
                d = d1 + d2;
            } else if (args[1].equals("-")) {
                d = d1 - d2;
            } else if (args[1].equals("x")) {
                d = d1 * d2;
            } else if (args[1].equals("/")) {
                d = d1 * d2;
            } else {
                System.out.println("The \"op\" of your give is error,plese check!");
                System.exit(-1);
            }
            System.out.println(d);
        } catch (NumberFormatException ae) {
            System.out.println("The \"NUM\" your give is error,pliese Check!");
        }
    }
}
	
	
	
	