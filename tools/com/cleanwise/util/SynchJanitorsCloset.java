import java.sql.*;
import java.util.*;
import java.io.*;

public class SynchJanitorsCloset {

    private static String kUsage = "Usage: -DdbUrl=v -DdbUser=v "
        + "-DdbPwd=v -Ddaysback=v";
    
    public static void main(String [] args ) {
		SynchJanitorsCloset o = new SynchJanitorsCloset();
		o.exec();
	}

	Connection dbc = null;
	Statement stmt = null;
    private void log(String v) {
        System.out.println(v);
    }
    
	private void exec() {
        try {
            String v = System.getProperty("daysback");
        
            if ( v == null || v.length() == 0 ) {
                System.out.println( "Usage: " + kUsage);
                return;
            }

            dbc = DBHelper.getDbConnection();
            stmt = dbc.createStatement();
            synchJC(v);
        
            stmt.close();
            dbc.close();
        }
        catch (Exception e) {
            System.out.println( "DB error: " );
            e.printStackTrace();
        }
    }
    
    private void synchJC(String pDaysBack)
        throws Exception 
    {
        log( "synchJC " );

        String sql = "select distinct o.site_id, " 
            + " 0, oi.item_id, o.order_id,"
            + " oi.add_by, to_char(oi.add_date, 'yyyy-mm-dd'), "
            + " oi.mod_by, to_char(oi.mod_date, 'yyyy-mm-dd') "
            + " from clw_order o, clw_order_item oi "
            + " where o.add_date > sysdate - " + pDaysBack
            + " and o.add_date < sysdate - 1 "
            + " and o.order_status_cd in  "
            +  "('Ordered', 'ERP Released','Invoiced') "
			+ " and oi.order_id = o.order_id "
			+ " and (select count(*) from clw_janitor_closet jc "
            + " where jc.order_id = o.order_id "
            + " and jc.item_id = oi.item_id) = 0 ";
        
        log(sql);
		ResultSet rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()) 
        {
            String s = " insert into clw_janitor_closet ("
                + " JANITOR_CLOSET_ID, "
                + " BUS_ENTITY_ID,	"
                + " USER_ID, "
                + " ITEM_ID, "
                + " ORDER_ID, "
                + " ADD_BY, "
                + " ADD_DATE, "
                + " MOD_BY, "
                + " MOD_DATE  ) values ( "
                + " clw_janitor_closet_seq.nextval, "
                + rs.getString(1) + " , "
                + rs.getString(2) + " , "
                + rs.getString(3) + " , "
                + rs.getString(4) + " , "
                + "'jc_synch' , "
                + "to_date('" + rs.getString(6) + "', 'yyyy-mm-dd') , "
                + "'" + rs.getString(7) + "' , "
                + "to_date('" + rs.getString(8) + "', 'yyyy-mm-dd') "
                + " ) ";
            
            // log(s);
            Statement stmt2 = dbc.createStatement();
            try {
            stmt2.executeUpdate(s);
            }
            catch (Exception e) {
                log( " -- " + s + "failed, msg " + e.getMessage() );
            }
            stmt2.close();
            i++;
        }
        log("-- " + pDaysBack + " - " + i);
        
    }
    
}    

    
