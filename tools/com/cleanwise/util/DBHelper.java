import java.sql.*;

public class DBHelper {

    private static String kUsage = "Usage: -DdbUrl=v -DdbUser=v -DdbPwd=v ";
    public static Connection getDbConnection() throws Exception {
	Class.forName ("oracle.jdbc.driver.OracleDriver");
	    
	String  dbUrl = System.getProperty("dbUrl"),
	    dbUser = System.getProperty("dbUser"),
	    dbPwd = System.getProperty("dbPwd");
	if ( dbUrl == null || dbUser == null || dbPwd == null ) {
	    throw new Exception(kUsage);
	}
	return DriverManager.getConnection(dbUrl, dbUser, dbPwd);
    }

    public static String runSql(Connection pCon , String pSql)
    {
        String res = "--";
        try {
            System.out.println("run sql:" + pSql);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String [] args)
    {
        return;
    }
}    

    
