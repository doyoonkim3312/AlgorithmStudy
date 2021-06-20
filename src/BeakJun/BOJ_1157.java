package BeakJun;

// BOJ 1157

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_1157 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] target = br.readLine().toUpperCase().split("");
        Arrays.sort(target);

        String most = "";
        int frequent = 0;
        String current = "";
        int count = 0;
        boolean sameFrequent = false;

        for (int i = 0; i < target.length; i++) {
            if (i == 0) {
                current = target[i];
                most = current;
                count++;
            } else {
                if (!current.equals(target[i])) {
                    if (frequent == 0) {
                        frequent = count;
                    } else {
                        if (count > frequent) {
                            most = current;
                            frequent = count;
                            sameFrequent = false;
                        } else if (count == frequent) sameFrequent = true;
                    }
                    current = target[i];
                    count = 1;
                } else {
                    count++;
                }
            }
        }
        if ( count > frequent) {
            most = current;
            sameFrequent = false;
        }
        else if (count == frequent) sameFrequent = true;

        if (sameFrequent) System.out.println("?");
        else System.out.println(most);
        long end = System.currentTimeMillis();
        System.out.println("TASK COMPLETED in " + (end - start) / 1000.0 + "s");
    }
}