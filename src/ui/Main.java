/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.*;

import javax.swing.JFrame;

/**
 * @author kuhail
 */
public class Main {


    public static void main(String[] args) {
        DBManager DB = new DBManager();
        try {

            DB.connect("root", "", "127.0.0.1", "3306", "advertise");

            JFrame LoginFrame = new LoginFrame(DB);
            LoginFrame.setVisible(true);
        } catch (Exception e) {

        }
    }
}
