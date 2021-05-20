package Java.Algs4LibraryPractice;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.io.*;
import java.util.Scanner;

public class MultipleStreamExample {
    public static void main(String[] args) {

        System.out.println("Using Plain Java Convention");
        plainJava(args);

        /*
        System.out.println("Using Algs4 Library");
        algs4Library(args);
         */
    }

    public static void algs4Library(String[] args) {
        Out out = new Out(args[args.length - 1]);
        for (int i = 0; i < args.length; i++) {
            In input = new In(args[i]); // Calling edu.princeton.cs.algs4.In.In(String name);
            String resultString = input.readAll();
            out.println(resultString);
            input.close();
        }
        out.close();
    }

    public static void plainJava(String[] args) {
        for (int i = 0; i < args.length; i++) {
            File targetFile = new File(args[i]);
            if (targetFile.exists()) {
                try {
                    Scanner input = new Scanner(new BufferedInputStream(new FileInputStream(targetFile)));
                    while (input.hasNext()) {
                        System.out.println(input.nextLine());
                    }
                } catch (FileNotFoundException fnf) {
                    fnf.getStackTrace();
                }
            }
        }
    }
}
