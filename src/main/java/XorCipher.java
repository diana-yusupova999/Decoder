package main.java;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class XorCipher {

    //шифрование
    public static void encode(File input, File output, String key) {
        StringBuilder encrypHexa = new StringBuilder();
        try (Scanner x = new Scanner(read(input))) {
            int keyItr = 0;
            while (x.hasNext()) {
                String a = x.nextLine();

                if (x.hasNext()) {
                    a += System.lineSeparator();
                }

                for (int i = 0; i < a.length(); i++) {
                    // XOR
                    int temp = a.charAt(i) ^ key.charAt(keyItr);

                    encrypHexa.append(String.format("%02x", (byte) temp));
                    keyItr++;
                    if (keyItr == key.length()) {
                        keyItr = 0;
                    }
                }
            }
        }
        write(output, encrypHexa.toString());
    }

    //расшифровка
    public static void decode(File input, File output, String key) {
        StringBuilder hexiToDeci = new StringBuilder();
        StringBuilder decrypText = new StringBuilder();
        try (Scanner x = new Scanner(read(input))) {
            while (x.hasNext()) {

                String a = x.nextLine();

                for (int i = 0; i < a.length() - 1; i += 2) {

                    String out = a.substring(i, i + 2);

                    int decimal = Integer.parseInt(out, 16);

                    hexiToDeci.append((char) decimal);

                }
                // рашифровка XOR
                int keyItr = 0;
                for (int i = 0; i < hexiToDeci.length(); i++) {
                    // XOR
                    int temp = hexiToDeci.charAt(i) ^ key.charAt(keyItr);
                    decrypText.append((char) temp);
                    keyItr++;
                    if (keyItr == key.length()) {
                        keyItr = 0;
                    }
                }
            }
        }
        write(output, decrypText.toString());
    }

    //чтение содержимого файла
    private static String read(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            Files.readAllLines(file.toPath()).forEach(line -> sb.append(line).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //запись текста в файл
    private static void write(File file, String text) {
        try {
            Files.writeString(file.toPath(), text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}