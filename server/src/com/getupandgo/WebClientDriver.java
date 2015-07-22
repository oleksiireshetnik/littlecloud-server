package com.getupandgo;

/**
 * Created by getupandgo on 7/19/15.
 */
public class WebClientDriver {
    void onLogin(UserInfo user){
        if(user.email.contains("@")){
            //check email and pass in DB
        }
        //else
            //send error to web app

    }

    void onRegistration(UserInfo user){
        if(user.email.contains("@")){
            //check email and pass in DB
        }
        //else
        //send error to web app
    }

    void displayFiles(){
        //send img and name of files to web app
    }
}
