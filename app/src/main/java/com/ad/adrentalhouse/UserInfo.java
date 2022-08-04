package com.ad.adrentalhouse;

public class UserInfo {
    String FullName;
    String UserEmail;
    public UserInfo(){}
    public UserInfo(String fullName, String userEmail) {
        FullName = fullName;
        UserEmail = userEmail;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}
