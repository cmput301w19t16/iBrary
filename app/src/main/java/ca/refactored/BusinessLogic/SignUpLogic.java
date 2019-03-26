/* TYPE:
 * Logic
 *
 * PURPOSE:
 * Handles the business logic of Signup_Activity
 *
 * ISSUES:
 *
 *
 * Created by Delaney on 07/03/2019
 *
 * */

package ca.refactored.BusinessLogic;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class SignUpLogic {

    private boolean valid;
    private String username;
    private String password;
    private String confirm;
    private String email;
    private String phone;

    public SignUpLogic(String username, String password, String confirm, String email, String phone) {
        valid = true;
        this.username = username;
        this.password = password;
        this.confirm = confirm;
        this.email = email;
        this.phone = phone;
    }


    public boolean isValid() {
        return valid;
    }


    public void setValid(boolean valid) {
        this.valid = valid;
    }


    //Validate the Phoene Field
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public String validdatePhone(){
        // check if phone is all numbers
        // check if phone is length 10
        String errorMessage = "";
        if (phone.isEmpty()) {
            errorMessage = "Please enter a phone number";
            valid = false;
        }else if (phone.length() != 10) {
            errorMessage = "That is not a valid phone number";
            valid = false;
        }

        return errorMessage;
    }


    //Validate the Username Field
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public String validateUsername() {
        // TODO: Part 5: Implement "this username is not available"
        String errorMessage = "";
        if (username.isEmpty()) {
            errorMessage = "Please enter username";
            valid = false;
        }
        return errorMessage;
    }


    //Validate the password Field
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public String validatePassword() {

        String errorMessage = "";
        if (password.isEmpty()) {
            errorMessage = "Please enter a password";
            valid = false;
        }
        return errorMessage;
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public String validateConfirm(){

        String errorMessage = "";
        if (confirm.isEmpty()) {
            errorMessage = "Please confirm the password";
            valid = false;
        } else if (!password.equals(confirm)) {
            errorMessage = "Must be the same as password";
            valid = false;
        }

        return errorMessage;
    }


    //Validate the email Field
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String validateEmail() {

        String errorMessage = "";
        if (email.isEmpty()) {
            errorMessage = "Please enter an email";
            valid = false;
        }else if(!isEmailValid(email)){
            errorMessage = "Invalid email";
            valid = false;
        }
        return errorMessage;
    }


    //helper function for validating email
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
