package Conexion;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class Conexion
{
    private Connection conexion05;
    private Connection conexionCloud;
    private Connection conexionVPS;
    
    public Conexion() {
        this.conexion05 = null;
        this.conexionCloud = null;
        this.conexionVPS = null;
    }
    
    public Connection GetConnectionCloud() {
        final int e = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            final String url = "jdbc:sqlserver://138.219.43.212:1433;database= cm;user= ws;password= cmt_496;";
            this.conexionCloud = DriverManager.getConnection(url);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            this.conexionCloud = null;
        }
        catch (SQLException ex2) {
            ex2.printStackTrace();
            this.conexionCloud = null;
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
            this.conexionCloud = null;
        }
        finally {
            return this.conexionCloud;
        }
    }
    
    public Connection GetConnection5() {
        final int e = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            final String url = "jdbc:sqlserver://192.168.0.5;database= cm;user= sa;password= syncmaster;";
            this.conexion05 = DriverManager.getConnection(url);
        }
        catch (ClassNotFoundException ex) {
            this.conexion05 = null;
        }
        catch (SQLException ex2) {
            this.conexion05 = null;
        }
        catch (Exception ex3) {
            this.conexion05 = null;
        }
        finally {
            return this.conexion05;
        }
    }
    
    public Connection GetConnectionVPS() {
        final int e = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            final String url = "jdbc:sqlserver://vps-1264017-x.dattaweb.com;database= cm;user= sa;password= Syncmaster496*;";
            this.conexionVPS = DriverManager.getConnection(url);
        }
        catch (ClassNotFoundException ex) {
            this.conexionVPS = null;
        }
        catch (SQLException ex2) {
            this.conexionVPS = null;
        }
        catch (Exception ex3) {
            this.conexionVPS = null;
        }
        finally {
            return this.conexionVPS;
        }
    }
    
    public static void main(final String[] args) {
        System.out.println("Conectado");
    }
}
