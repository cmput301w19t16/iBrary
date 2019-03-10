/* TYPE:
 * Logic
 *
 * PURPOSE:
 * Handles business logic for Login_Activity
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;


/*
Created by Delaney Lothian 09/03/2019

 */

public class Log_In_Logic {
    private String email;
    private String password;
    private boolean valid;

    public Log_In_Logic(String email, String password) {
        valid = true;
        this.email = email;
        this.password = password;


    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String validatePassword(){
        String errorMessage = "";
        if (password.isEmpty()){
            valid = false;
            errorMessage = "Please enter password";
        }
        return errorMessage;
    }

    public String validateEmail(){
        String errorMessage = "";
        if (email.isEmpty()){
            valid = false;
            errorMessage = "Please enter email";
        }
        return errorMessage;
    }
}
