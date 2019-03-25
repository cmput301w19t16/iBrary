package ca.rededaniskal.BusinessLogic;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Login_Manager_Helper_BL {
    private String FILE_SAV = "file.sav";
    private Context ctx;
    transient private ObjectOutputStream oos;
    transient private FileOutputStream fos;

    public Login_Manager_Helper_BL(Context ctx){
        this.ctx = ctx;
    }

    public void readFromFile(Login_Manager_BL lmbl){
        try{
            FileInputStream fis = ctx.openFileInput("file.sav");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object o = ois.readObject();


            if (o instanceof Login_Manager_BL){
                Login_Manager_BL op = (Login_Manager_BL) o;
                lmbl.setUsername(op.getUsername());
                lmbl.setPassword(op.getPassword());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveInFile(Login_Manager_BL lmbl){

        try {
            fos = ctx.openFileOutput("file.sav", 0);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(lmbl);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearInfo(Login_Manager_BL lmbl){
        lmbl.setPassword("");
        lmbl.setUsername("");
        saveInFile(lmbl);
    }
}
