import java.sql.*;
import java.util.*;
import java.io.*;

public class CheckUserAssociation {

    private static String kUsage = "Usage: -DdbUrl=v -DdbUser=v "
        + "-DdbPwd=v -Dusername=v -Dacctsearch=v2";
    
    public static void main(String [] args ) {
	CheckUserAssociation o = new CheckUserAssociation();
	o.exec();
    }

    Connection dbc = null;
    Statement stmt = null;

    private class AcctInfo {
	public AcctInfo( int pUserId, int pAcctId, String pAcctName ) {
	    userId = pUserId;
	    acctId = pAcctId;
	    acctName = pAcctName;
	}
	public int userId;
	public int acctId;
	public String acctName;
	public String toString () {
	    return "[ acctId=" + acctId +
		" acctName=" + acctName + "]";
	}
    }

    private ArrayList getAllAccountsToRun
	(String pUname, String pSearchToken, String pStoreList )
        throws Exception 
    {
        String sql = "select distinct user_id from clw_user "
	    + " where user_name = '" + pUname + "'";

	int uid = 0;
	ResultSet rs = stmt.executeQuery(sql);
        while (rs.next())     { 
	    uid = rs.getInt(1);
	}
        rs.close();

	sql = "select distinct bus_entity_id, short_desc "
	    + " from clw_bus_entity "
	    + " where short_desc like '" + pSearchToken + "'"
	    + " and bus_entity_type_cd = 'ACCOUNT' "
	    + " and bus_entity_status_cd = 'ACTIVE'"
	    + " and bus_entity_id in ( "
	    + "   select bus_entity1_id  from clw_bus_entity_assoc "
	    + "     where BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' "
	    + "     and bus_entity2_id in (" + pStoreList + ") )";

	System.out.println( " sql=" + sql);


	rs = stmt.executeQuery(sql);
        ArrayList accts = new ArrayList();
        while (rs.next())     { 
	    accts.add(new AcctInfo(uid, rs.getInt(1),rs.getString(2))); 
	}
        rs.close();

        return accts;
    }

    private void checkUserAssoc(String uname, AcctInfo accti) 
    throws Exception {

	String q = " select count(*) "
	    + " from clw_user u, clw_user_assoc  ua"
	    + " where u.user_id = " + accti.userId
	    + " and ua.user_assoc_cd = 'ACCOUNT' "
	    + " and u.user_id = ua.user_id "
	    + " and ua.bus_entity_id = " + accti.acctId;

	    Statement s1 = dbc.createStatement();
	    ResultSet rs = s1.executeQuery(q);
	    if ( rs.next() ) {
		if ( rs.getInt(1) == 0 ) {
		    System.out.println( " add user " + uname +
					" to acccount " + accti );

		    String i = " insert into clw_user_assoc ( "
			+ " USER_ASSOC_ID	, "
			+ " USER_ID	, "
			+ " BUS_ENTITY_ID	, "
			+ " USER_ASSOC_CD	, "
			+ " ADD_DATE	, "
			+ " ADD_BY	, "
			+ " MOD_DATE	, "
			+ " MOD_BY	) values ( "
			+ " clw_user_assoc_seq.nextval, "
			+ accti.userId + " , "
			+ accti.acctId + " , "
			+ " 'ACCOUNT' , sysdate, 'system', sysdate, 'system' "
			+ " ) ";
		    System.out.println( "i=" + i);
		    s1.executeUpdate(i);
		}
	    }
	    rs.close();
	    s1.close();

    }

    private void exec() {
        try {
            String uname = System.getProperty("uname"),
                acctsearch = System.getProperty("acctsearch"),
		storeIdList = System.getProperty("storeIdList");
        
	    if ( null == storeIdList ) {
		storeIdList = "1";
	    }

            dbc = DBHelper.getDbConnection();
            stmt = dbc.createStatement();

            ArrayList accts = new ArrayList();
	    accts = getAllAccountsToRun(uname, acctsearch, storeIdList );


            for ( int acctIdx = 0; accts != null && 
		      acctIdx < accts.size(); acctIdx++ ){
		AcctInfo accti = (AcctInfo)accts.get(acctIdx);
		System.out.println
		    ( "  " + acctIdx + " -> " + accts.size() 
		     + " uname=" + uname 
		     + " accti=" + accti );
		checkUserAssoc(uname, accti);
	    }
            stmt.close();

            dbc.close();
        }
        catch (Exception e) {
            System.out.println( "DB error: " );
            e.printStackTrace();
        }
    }
    
}    

    
