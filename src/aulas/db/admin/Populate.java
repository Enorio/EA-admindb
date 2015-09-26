/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aulas.db.admin;

import java.sql.Statement;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class Populate {

    static final int MAX = 10000;
    static final int MAXCLI = 1024;
    static final int MAXPROD = 2048;

    public static void main(String[] args) throws Exception {
        Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost/Perez_25");

        Statement s = c.createStatement();

        s.executeUpdate(
                "create table invoice (id int, prodid int, cliid int)");

        Random r = new Random();

        PreparedStatement ps = c.prepareStatement(
                "insert into invoice values (?,?,?)");

        for (int i = 0; i < MAX; i++) {
            int p = r.nextInt(MAXPROD) | r.nextInt(MAXPROD);
            int cl = r.nextInt(MAXCLI) | r.nextInt(MAXCLI);

            ps.setInt(1, i);
            ps.setInt(2, p);
            ps.setInt(3, cl);

            ps.executeUpdate();
        }

        // TODO: tabela produto
        s.executeUpdate("create table product (id int,des varchar)");

        ps = c.prepareStatement(
                "insert into product values (?,?)");

        String produto = "prod_";

        for (int i = 0; i < MAXPROD; i++) {
            ps.setInt(1, i);
            ps.setString(2, produto+ i);

            ps.execute();
        }

		// TODO: tabela cliente
        s.execute("create table client (id int,name varchar, address varchar)");
        ps = c.prepareStatement("insert into client values(?,?,?)");
        String client = "client_";
        String address = "address_";
        
        for (int i = 0; i < MAXCLI; i++) {
            ps.setInt(1, i);
            ps.setString(2, client+i);
            ps.setString(3,address+i);    
            ps.execute();
        }
        
        
        
        
        ps.close();
        s.close();

        c.close();

        System.out.println("Ok!");
    }
}
