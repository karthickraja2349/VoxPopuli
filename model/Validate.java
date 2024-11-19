package model;
public interface Validate{
     boolean validateName(String name);
     boolean validatePassword(String password);
     boolean validateNumber(long number);
     boolean validateAadhar(long aadharNumber);
     boolean validateVoterId(String voterId);
     boolean validateGender(String gender);
     
} 
