

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.value.OrderAddressData;

public class TempLogShipToAddr {
    private static String kUsage = "Usage: -DdbUrl=<db url> -DdbUser=<db user> " + 
    "-DdbPwd=<db password> -Ddist=<dist erp num> -DstartOrd=<order> -DendOrd=<order> -DfileName "+
    "-DaddressLogFile=<name of file to write out to>";
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        go();
    }

    private static void go() {
        try {
            Connection dbc = DBHelper.getDbConnection();
            Statement stmt = dbc.createStatement();
            String sqlStr = "select order_id from clw_order where order_num = "+System.getProperty("startOrd");
            String sqlEnd = "select order_id from clw_order where order_num = "+System.getProperty("endOrd");
            
            ResultSet rs = stmt.executeQuery(sqlStr);
            int startId, endId;
            if(!rs.next()){
                System.out.println("ERROR no order found: "+System.getProperty("startOrd"));
                return;
            }
            startId = rs.getInt("order_id");
            rs.close();
            
            rs = stmt.executeQuery(sqlEnd);
            if(!rs.next()){
                System.out.println("ERROR no order found: "+System.getProperty("endOrd"));
                return;
            }
            endId = rs.getInt("order_id");
            rs.close();
            
            String sql = 
                "select o.site_id, o.user_first_name, o.user_last_name,o.order_site_name, "+
                    "oa.address1, oa.address2, oa.address3, oa.address4, "+
                    "oa.city, oa.state_province_cd, oa.postal_code, oi.erp_po_num "+
                     "from clw_order_address oa, clw_order o, clw_order_item oi where "+
                    "oa.order_id = o.order_id and oa.address_type_cd = 'SHIPPING' and " +
                    "oi.order_id = o.order_id and oi.dist_erp_num = '"+System.getProperty("dist")+"' "+
                    "and o.order_id between "+startId+" and "+endId;
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                try{
                    System.out.println("Writing out order: "+rs.getString("erp_po_num"));
                    com.cleanwise.service.apps.dataexchange.CompressedAddress ca = new com.cleanwise.service.apps.dataexchange.CompressedAddress(
                        rs.getString("order_site_name"),rs.getString("address1"),rs.getString("address2"),rs.getString("address3"),rs.getString("address4"),
                        rs.getString("city"),rs.getString("state_province_cd"),rs.getString("postal_code"),null,4);
                
                    logShipToAddress(ca,rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getInt("site_id"),rs.getString("erp_po_num"));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            stmt.close();
            dbc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void logShipToAddress(com.cleanwise.service.apps.dataexchange.CompressedAddress pShipTo,
            String fn, String ln, int siteId, String po) {
        System.out.println("     = ST = \n" + pShipTo);
        String logstr = siteId + "\t";

        String t = fn;
        if (null == t)
            t = ".";
        logstr += t + "\t";

        t = ln;
        if (null == t)
            t = "..";
        logstr += t + "\t";

        t = pShipTo.getAddress1();
        if (t.length() == 0) {
            t += " ";
        }
        t += "\t";
        logstr += t;

        t = "";
        if (pShipTo.getAddress2() != null) {
            t += pShipTo.getAddress2();
        }
        if (pShipTo.getAddress3() != null) {
            if (t.length() > 0)
                t += "/";
            t += pShipTo.getAddress3();
        }
        if (pShipTo.getAddress4() != null) {
            if (t.length() > 0)
                t += "/";
            t += pShipTo.getAddress4();
        }
        if (t.length() == 0) {
            t += " ";
        }
        t += "\t";

        logstr += t;
        logstr += pShipTo.getCity();
        logstr += "\t";
        logstr += pShipTo.getStateProvinceCode();
        logstr += "\t";
        logstr += pShipTo.getPostalCode();
        logstr += "\t";
        logstr += po + "\t";
        logstr += "\t";
        logstr += "EOR" + "\n";
        System.out.println("     = logstr = \n" + logstr);

        try {
            t = java.lang.System.getProperty("addressLogFile");
            if (t != null && t.length() > 0) {
                FileOutputStream addrFileOut = new FileOutputStream(new File(t), true);
                addrFileOut.write(logstr.getBytes());
                addrFileOut.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
