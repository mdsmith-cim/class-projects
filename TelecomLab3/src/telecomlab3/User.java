/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telecomlab3;

/**
 *
 * @author michael
 */
public class User {
    
    private String username, password;
    private boolean isLoggedIn;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
    }
    
    public User() {
        username = null;
        password = null;
        isLoggedIn = false;
    }
    
    public synchronized String getUsername() {
        return username;
    }
    
    public synchronized String getPassword() {
        return password;
    }
    
    public synchronized void setLogin(boolean state) {
        isLoggedIn = state;
    }
    
    public synchronized boolean getLoginState() {
        return isLoggedIn;
    }
    
    public synchronized void setUsername(String username) {
        this.username = username;
    }
    
    public synchronized void setPassword(String password) {
        this.password = password;
    }
}