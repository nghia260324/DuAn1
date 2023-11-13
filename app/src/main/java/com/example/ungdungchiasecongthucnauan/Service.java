package com.example.ungdungchiasecongthucnauan;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Service {
    public void writeFile(Context context, String path, ArrayList<String> lstSearchHistory){
        try {
            FileOutputStream fos = context.openFileOutput(path,Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lstSearchHistory);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> readFile(Context context,String path){
        ArrayList<String> lstSearchHistory = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            lstSearchHistory = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstSearchHistory;
    }
}
