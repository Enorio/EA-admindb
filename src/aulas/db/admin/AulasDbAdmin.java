/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aulas.db.admin;


import java.sql.SQLException;


/**
 *
 * @author Perez_25
 */
public class AulasDbAdmin {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException, Exception {
       Populate pop = new Populate(10000, 1024, 2048, "jdbc:postgresql://localhost/Perez_25");
       pop.deleteTables();
       pop.createTableClient();
       pop.createTableProduct();
       pop.createTableInvoice();
       pop.populateTables();
       Run run = new Run();
       run.main(args);
       
    }
    
    
}
