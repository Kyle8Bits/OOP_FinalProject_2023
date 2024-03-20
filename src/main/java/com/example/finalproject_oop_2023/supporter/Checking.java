package com.example.finalproject_oop_2023.supporter;

public class Checking {
    public boolean isValidPhoneNumber(String input) {
        if (input.length() != 10) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
