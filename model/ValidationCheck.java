package model;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class ValidationCheck implements Validate {

    private boolean validate(String input, String regex) {
        if (input == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean validateName(String name) {
        String regex = "^[A-Za-z][A-Za-z ]{2,29}$";
        return validate(name, regex);
    }

    public boolean validatePassword(String password) {
        String regex = "^[A-Za-z][A-Za-z0-9!@#$%^&*]{7}$";
        return validate(password, regex);
    }

    public boolean validateNumber(long number) {
        String numberStr = Long.toString(number);
        String regex = "^[6-9]\\d{9}$";
        return validate(numberStr, regex);
    }

    public boolean validateAadhar(long aadharNumber) {
        String aadharStr = Long.toString(aadharNumber);
        String regex = "^[1-9]\\d{11}$";
        return validate(aadharStr, regex);
    }
    
   public boolean validateVoterId(String voterID){
       String regex = "^[A-Z]{3}\\d{7}$";
       return validate(voterID,regex);
   } 
   
   public boolean validateGender(String gender){
        return gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("TransGender");
   }       
}

