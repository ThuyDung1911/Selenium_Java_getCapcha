package com.thuydung.pages;

public class CommonPage {
    public RegisterPage registerPage;

    public RegisterPage getRegisterPage() {
        if (registerPage == null) {
            registerPage = new RegisterPage();
        }
        return registerPage;
    }

}
