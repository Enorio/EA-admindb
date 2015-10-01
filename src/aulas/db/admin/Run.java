/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aulas.db.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class Run implements Runnable {

    static int n = 0;
    static boolean partida = false;
    static double tot = 0;

    private static synchronized void inc(long delta) {
        if (partida) {
            n++;
            tot += delta;
        }
    }

    private static synchronized void fim(long delta) {
        System.out.println("débito: " + (n / ((delta) / 1000d)));

        System.out.println("latencia: " + (tot / n));
    }

    private static Random r = new Random();

    public void run() {
        try {

            Connection c = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/Perez_25");
            Populate p = new Populate(10000, 1024, 2048, "jdbc:postgresql://localhost/Perez_25");
            Statement s = c.createStatement();

            while (true) {

                long antes = System.currentTimeMillis();

                switch (0) {

                    case 0: //sell
                        int cl0 = r.nextInt(p.getMaxcli()) | r.nextInt(p.getMaxcli());
                        int pl0 = r.nextInt(p.getMaxprod()) | r.nextInt(p.getMaxprod());
                        p.insertInvoice(p.getMax()+1, pl0, cl0);
                        
                        
                        break;
                    case 1: // current account
                        int cl = r.nextInt(p.getMaxcli()) | r.nextInt(p.getMaxcli());
                        ResultSet rs = s.executeQuery("select count(*) from invoice where cliid = " + cl);
                        while (rs.next()){
                            
                        }
                           
                        rs.close();
                        break;
                    case 2: // top 10
                        ResultSet rs2 = s.executeQuery("select prodid,count(prodid) AS sells from invoice GROUP BY prodid ORDER BY sells DESC LIMIT 10");
                        
                        while (rs2.next()) {
                           
                        }
                        rs2.close();
                        break;
                }
                long depois = System.currentTimeMillis();

                inc(depois - antes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void main(String[] args) throws Exception {
        for (int i = 0; i < 8; i++) {
            new Thread(new Run()).start();
        }

        Thread.sleep(5000);

        System.out.println("inicio");

        partida = true;
        long antes = System.currentTimeMillis();

        Thread.sleep(5000);

        long depois = System.currentTimeMillis();

        fim(depois - antes);

        System.exit(0);
    }

}
