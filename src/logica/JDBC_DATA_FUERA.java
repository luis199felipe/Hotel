package logica;

//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;

public class JDBC_DATA_FUERA {
	public Connection c = null;
	private ResultSet resultSet = null;
	private Statement stmt = null;
	

	public JDBC_DATA_FUERA() throws Exception {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:D:\\programacion\\Nevera\\src\\DB\\NeveraDB.sqlite");
			c.setAutoCommit(true);
		} catch (Exception e) {
            throw e;
        } finally {
            close();
        }
	}
	
	

	public Object[][] listaProd() {

		try {
			c = DriverManager.getConnection("jdbc:sqlite:D:\\programacion\\Nevera\\src\\DB\\NeveraDB.sqlite");
			String sql1 = "SELECT count(1) FROM Productos;";
			PreparedStatement stm1 = c.prepareStatement(sql1);
			ResultSet rs = stm1.executeQuery();
			int cantidad = Integer.parseInt(rs.getString(1));
			
			Object[][] productos = new Object[cantidad][4];
			sql1= "SELECT * FROM Productos;";
			stm1 = c.prepareStatement(sql1);
			ResultSet rs1 = stm1.executeQuery();
			for (int i = 0; rs1.next(); i++) {
				productos[i][0] = rs1.getString("nombre");
				productos[i][1] = rs1.getString("precio");
				productos[i][2] = rs1.getString("cantidad");
				productos[i][3] = rs1.getString("id");
			}
			
			return productos;
			
		} catch (Exception e) {
			System.out.println("Error obteniendo " + e);
			return new Object[1][1];       
        } finally {
            close();
        }
	}

	public void setCompra(int selectedIndex, int cantProdRestante) throws Exception {
		try {
			c = DriverManager.getConnection("jdbc:sqlite:D:\\programacion\\Nevera\\src\\DB\\NeveraDB.sqlite");
			
			String sql = "UPDATE `Productos` SET `cantidad`='" + cantProdRestante + "' _rowid_='"	+ selectedIndex + "';";
			PreparedStatement stm = c.prepareStatement(sql);
			int t= stm.executeUpdate();
			
			System.out.println(sql+ " t: "+ t);
			c.commit();
		}catch (Exception e) {
            throw e;
        } finally {
            close();
        }
	}
	
	public void setDeuda(String habitacion,String nombre,String precio,int cant) throws Exception {
		try {
			this.stmt = c.createStatement();
			
			System.out.println("INSERT INTO Deudas (habitacion,nombre,precio,cantidad) VALUES ('"+habitacion+"','"+nombre+"','"+precio+"','"+cant+"')");
			int t = stmt.executeUpdate("INSERT INTO Deudas (habitacion,nombre,precio,cantidad) VALUES ('"+habitacion+"','"+nombre+"','"+precio+"','"+cant+"')");
			System.out.println("t: "+ t);
		}catch (Exception e) {
            throw e;
        } finally {
            //close();
        }
	}
	
	// You need to close the resultSet
    private void close() {
        try {
            
			if (resultSet != null) {
                resultSet.close();
            }

            if (stmt != null) {
            	stmt.close();
            }

            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
        	
        }
    }
}