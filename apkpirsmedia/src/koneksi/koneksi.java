/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;



public class koneksi {
  
    Connection conn;
    Statement st;
    ResultSet rs;
    private static Connection koneksi;
    
    
    public static Connection getKoneksi(){
        try{
            String url = "jdbc:mysql://localhost:3306/apkpirsmedia";
            String user = "root";
            String pass = "";
            
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            koneksi = DriverManager.getConnection(url,user,pass);
            System.out.println("koneksi berhasil");
        
        }catch(Exception e){
            System.out.println(e);
            System.out.println("koneksi gagal");
        }
        return koneksi;
    }
}
   

