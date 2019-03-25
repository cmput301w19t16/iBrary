package ca.rededaniskal.BusinessLogic;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ca.rededaniskal.EntityClasses.User;

public class Login_Manager_BL implements Serializable {
    private String username = "";
    private String password = "";
    static private Login_Manager_BL instance;


    private Login_Manager_BL(){
    }

    public static Login_Manager_BL getLoginManager(){
        if (instance == null){
            instance = new Login_Manager_BL();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
