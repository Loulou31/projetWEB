/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author nicolasl
 */
public abstract class AbstractDatabaseDAO {

    protected final DataSource dataSource;
    
    protected AbstractDatabaseDAO(DataSource ds) {
        this.dataSource = ds;
    }

    protected Connection getConn() throws SQLException {
        return dataSource.getConnection();
    }
}
