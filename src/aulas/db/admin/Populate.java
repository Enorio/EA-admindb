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
import java.sql.SQLException;

public class Populate extends SQLException {

    private final Connection c;

    private final int max;
    private final int maxprod;
    private final int maxcli;

    public int getMax() {
        return max;
    }

    public int getMaxprod() {
        return maxprod;
    }

    public int getMaxcli() {
        return maxcli;
    }
    
    public Populate(int max, int maxcli, int maxprod, String connection) throws SQLException {
        this.max = max;
        this.maxcli = maxcli;
        this.maxprod = maxprod;
        this.c = DriverManager.getConnection(connection);
    }
    
    public void createTableInvoice() throws SQLException {
        try (Statement s = this.c.createStatement()) {
            s.executeUpdate("create table invoice (id int, prodid int, cliid int)");
            s.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void createTableClient() throws SQLException {
        try (Statement s = this.c.createStatement()) {
            s.executeUpdate("create table client (id int,name varchar, address varchar)");
            s.close();
        } catch (SQLException e) {
            System.err.println(e);
        }

    }
    
    public void createTableProduct() {
        try (Statement s = this.c.createStatement()) {
            s.executeUpdate("create table product (id int,des varchar)");
            s.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void insertClient(int id, String cl, String address) {
        try (PreparedStatement ps = c.prepareStatement(
                "insert into client values (?,?,?)")) {
            ps.setInt(1, id);
            ps.setString(2, cl);
            ps.setString(3, address);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void insertProduct(int id, String des) {
        try (PreparedStatement ps = c.prepareStatement(
                "insert into product values (?,?)")) {
            ps.setInt(1, id);
            ps.setString(2, des);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    public void populateTables() {
        Random r = new Random();
        for (int i = 0; i < this.max; i++) {
            int p = r.nextInt(this.maxprod) | r.nextInt(this.maxprod);
            int cl = r.nextInt(this.maxcli) | r.nextInt(this.maxcli);
            insertInvoice(i, p, cl);
        }
        for (int i = 0; i < this.maxprod; i++) {
            insertProduct(i, "prod_" + i);
        }
        for (int i = 0; i < this.maxcli; i++) {
            insertClient(i, "client_" + i, "address_" + i);
        }
    }

    public void insertInvoice(int id, int prodid, int clid) {
        try (PreparedStatement ps = c.prepareStatement(
                "insert into invoice values (?,?,?)")) {
            ps.setInt(1, id);
            ps.setInt(2, prodid);
            ps.setInt(3, clid);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void closeConnection() throws SQLException {
        this.c.close();
    }

    public void deleteTables() throws SQLException {
        try (Statement s = this.c.createStatement()) {
            s.execute("DROP TABLE client");
            s.execute("DROP TABLE product");
            s.execute("DROP TABLE invoice");
        }
        catch(Exception e){
          e.printStackTrace();
        }
    }
}
