package project.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config<E> {
    public static Scanner scanner() {
        return new Scanner(System.in);
    }
    public List<E> readFromFile(String path) {
        List<E> list = new ArrayList<>();
        File file = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            file = new File(path);
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                list = (List<E>) objectInputStream.readObject();
            }
        } catch (FileNotFoundException f) {
            System.err.println("File Not Found!");
        } catch (IOException i) {
            System.err.println("IOException!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException i) {
                System.err.println("IOException!");
            }
        }
        return list;
    }
    public void writeToFile(List<E> list, String path) {
        File file = null;
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
        } catch (IOException i) {
            System.err.println("IOException!");
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException i) {
                System.err.println("IOException!");
            }
        }
    }
}
