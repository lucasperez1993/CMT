package Controller;

import Login.Login;

public class VistaLogin {
    public static void vistaLogin(){
        Login login = new Login();
        login.setVisible(true);
    }
    
    public static void main(String[] args) {
        vistaLogin();
    }
}