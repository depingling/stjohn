package com.cleanwise.service.apps.loaders;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.FileInputStream;
import com.cleanwise.service.api.value.CityPostalCodeData;
import com.cleanwise.service.api.value.CityPostalCodeDataVector;
//RMI Stuff
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import com.cleanwise.service.api.util.JNDINames;

import com.cleanwise.service.api.session.AddressValidator;
import com.cleanwise.service.api.session.AddressValidatorHome;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import org.apache.log4j.Logger;

/**
 *Loads data from the vertex dbf files into our database for address validation.  Does some
 *trickery to determine Canadian or US zips.  The documentation on TinySQL (the dbf jdbc driver used)
 *does not handle joining dbf files very well so all of the joining between the DBF files 
 *are done in memory using HashMaps.  Compound keys (into the county table) are joined using
 *the geo codes seperated by commas.  The vertex data files store zips as ranges, these are
 *translated out into individual records as this seems more normal, and it would cause problems
 *when going to other countrys that use letters in their postal codes, or the numbering
 *system is not sequential per geographic area.
 */
public class VertexDBFZipcodeLoader {
    private static final Logger log = Logger.getLogger(VertexDBFZipcodeLoader.class);
    //database column names
    private static final String GEO_STATE_CODE="GEOST";
    private static final String GEO_COUNTY_CODE="GEOCNTY";
    private static final String COUNTY_NAME="CNTYNAME";
    private static final String CITY_NAME="CITYNAME";
    private static final String ZIP_START="ZIPSTART";
    private static final String ZIP_END="ZIPEND";
    private static final String STATE_ABBREVIATION="STABB";
    private static final String STATE_NAME="STNAME";
    //database "table" names (represented as files by dbf*)
    private static final String COUNTY_TABLE="loccnty";
    private static final String STATE_TABLE="locstate";
    private static final String CITY_TABLE="loccity";
    
    private static final String COUNTRY_CODE_US="UNITED STATES";
    private static final String COUNTRY_CODE_CA="CANADA";
    
    private static Map stateMap = new HashMap();
    private static Map countyMap = new HashMap();
    
    private static AddressValidator mAddressValidatorRef;
    /**
     *Sets up the class as an EJB Client.
     */
    private static void setUp() throws Exception {
        // Check for a properties file command option.
        String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
        Properties props = new Properties();
        java.io.File f = new java.io.File(propFileName);
        props.load(new FileInputStream(propFileName) );
        InitialContext jndiContext = new InitialContext(props);
        Object ref;
        
        // Get a reference to the Address Validator Bean
        ref = jndiContext.lookup(JNDINames.ADDRESS_VALIDATOR_EJBHOME);
        AddressValidatorHome addressValidatorHome = (AddressValidatorHome)
        PortableRemoteObject.narrow(ref, AddressValidatorHome.class);
        mAddressValidatorRef = addressValidatorHome.create();
    }
    
    public static void main(String argv[]) {
        // Uncomment the next line to get noisy messages
        // java.sql.DriverManager.setLogStream(System.out);
        try {
            long startTime = System.currentTimeMillis();
            setUp();
            // load the driver
            Class.forName("ORG.as220.tinySQL.dbfFileDriver").newInstance();
            
            // the url to the tinySQL data source
            //    jdbc:dbfFile:<directory dbf files are located>
            String url = "jdbc:dbfFile:R:/db/";
            
            // get the connection
            Connection con = DriverManager.getConnection(url, "", "");
            
            log.info("*************************************");
            
            //fist cache the state reference map
            Statement stmt = con.createStatement();
            String str = "SELECT "+GEO_STATE_CODE+","+STATE_ABBREVIATION+","+STATE_NAME+" FROM "+STATE_TABLE;
            log.info("Executing SQL: " + str);
            ResultSet rs = stmt.executeQuery(str);
            VertexDBFZipcodeLoader.processStateResults(rs);
            
            //next cache the county reference map
            stmt = con.createStatement();
            str = "SELECT "+GEO_COUNTY_CODE+","+GEO_STATE_CODE+","+COUNTY_NAME+" FROM "+COUNTY_TABLE;
            log.info("Executing SQL: " + str);
            rs = stmt.executeQuery(str);
            VertexDBFZipcodeLoader.processCountyResults(rs);
            
            // create a statement and execute a query
            stmt = con.createStatement();
            str = "SELECT "+GEO_STATE_CODE+","+GEO_COUNTY_CODE+","+CITY_NAME+","+ZIP_START+","+ZIP_END+" FROM "+CITY_TABLE;
            log.info("Executing SQL: " + str);
            rs = stmt.executeQuery(str);
            int numProcessed = VertexDBFZipcodeLoader.processCityResults(rs);
            
            long endTime = System.currentTimeMillis();
            long diffTime = endTime - startTime;
            log.info("\n    -> Elapsed time: " + diffTime + " Number of records processed: " + numProcessed);
            
            stmt.close();
            con.close();
        } catch( Exception e ) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     *Creates an in memory reference for the state code to the state abbreviation.
     *This could be done as a join but I do not trust the jdbc driver that far when
     *working with dbf files.
     */
    static void processStateResults(ResultSet rs) throws java.sql.SQLException {
        if (rs == null) {
            System.err.println("ERROR in processStateResults(): No data in ResulSet");
            throw new NullPointerException("result set was null");
        }
        while (rs.next()) {   
            String geoState = rs.getString(GEO_STATE_CODE).trim();
            String stateCd = rs.getString(STATE_ABBREVIATION).trim();
            String stateNm = rs.getString(STATE_NAME).trim();
            stateMap.put(geoState,new StateStruct(stateNm,stateCd));
        }
    }
    
    /**
     *Creates an in memory reference for the county.
     *This could be done as a join but I do not trust the jdbc driver that far when
     *working with dbf files.
     */
    static void processCountyResults(ResultSet rs) throws java.sql.SQLException {
        if (rs == null) {
            System.err.println("ERROR in processStateResults(): No data in ResulSet");
            throw new NullPointerException("result set was null");
        }
        while (rs.next()) {
            String geoCnty = rs.getString(GEO_COUNTY_CODE).trim();
            String geoState = rs.getString(GEO_STATE_CODE).trim();
            String county = rs.getString(COUNTY_NAME).trim();
            //this is a bad way of doing a compound join.  Everything is numeric
            //so adding a ',' is a safe way of uniquly determining a record
            countyMap.put(geoState+","+geoCnty,county);
        }
    }
    
    /**
    *Processes the results of the query
    */
    static int processCityResults(ResultSet rs) throws java.sql.SQLException,java.rmi.RemoteException {
        if (rs == null) {
            System.err.println("ERROR in processCityResults(): No data in ResulSet");
            throw new NullPointerException("result set was null");
        }
        
        CityPostalCodeDataVector addresses = new CityPostalCodeDataVector();
        int numDbfProcessed = 0; //dbf record count
        try{
        // fetch each row
        while (rs.next()) {
            CityPostalCodeData address = CityPostalCodeData.createValue();
            //deal with each column
            String geoState = rs.getString(GEO_STATE_CODE).trim();
            String geoCounty = rs.getString(GEO_COUNTY_CODE).trim();
            String cityName = rs.getString(CITY_NAME).trim();
            String zipStart = rs.getString(ZIP_START).trim();
            String zipEnd = rs.getString(ZIP_END).trim();
            String currentZip = zipStart;
            try{
                int zipEndI = Integer.parseInt(zipEnd);
                int currentZipI = Integer.parseInt(zipStart);
                do{
                    //log.info("start range: " + zipStart);
                    //log.info("end range: " + zipEnd);
                    //log.info("currentZip="+currentZip);
                    address.setAddBy("addressValidationLoader");
                    address.setCity(cityName);
                    StateStruct stStr = (StateStruct) stateMap.get(geoState);
                    address.setStateProvinceCd(stStr.getStateCd());
                    address.setStateProvinceNam(stStr.getStateName());
                    address.setCountyCd((String) countyMap.get(geoState+","+geoCounty));
                    address.setPostalCode(currentZip);
                    address.setEntryType(RefCodeNames.CITY_POSTAL_ENTRY_TYPE_CD.LOADER);
                    address.setCountryCd(COUNTRY_CODE_US);
                    addresses.add(address);
                    address = CityPostalCodeData.createValue();
                    currentZipI = Integer.parseInt(currentZip);
                    currentZipI ++;
                    currentZip = Utility.padLeft(Integer.toString(currentZipI), '0', 5);
                    //log.info("NOW currentZip="+currentZip);
                }while(currentZipI<=zipEndI);
            }catch(NumberFormatException e){
                //dirty little trick if zip is not numeric it is a CANADIAN zipcode
                //with Vertex data if it is not numeric start and end are always the same
                address.setAddBy("addressValidationLoader");
                address.setCity(cityName);
                StateStruct stStr = (StateStruct) stateMap.get(geoState);
                address.setStateProvinceCd(stStr.getStateCd());
                address.setStateProvinceNam(stStr.getStateName());
                address.setCountyCd((String) countyMap.get(geoState+","+geoCounty));
                address.setPostalCode(zipStart);
                address.setEntryType(RefCodeNames.CITY_POSTAL_ENTRY_TYPE_CD.LOADER);
                address.setCountryCd(COUNTRY_CODE_CA);
                addresses.add(address);
            }
            
            numDbfProcessed++;
            if(numDbfProcessed % 1000 == 0){
                log.info("parsed: " + numDbfProcessed + " records to add count: " + addresses.size());
            }
        }
        }catch(RuntimeException e){
            log.info("Falied parsing Record: "+numDbfProcessed);
            log.info("No database changes made");
            log.info("Exiting....");
            throw e;
        }
        log.info("sending " + addresses.size() + " records to EJB (parsed "+numDbfProcessed+" from dbf files)");
        mAddressValidatorRef.repopulateCityPostalCodeData(addresses);
        return numDbfProcessed;
        
    }
    
    static class StateStruct {
        StateStruct(String pName, String pCode){
            stateName = pName;
            stateCd = pCode;
        }
        
        /** Holds value of property stateCd. */
        private String stateCd;
        
        /** Holds value of property stateName. */
        private String stateName;
        
        /** Getter for property stateCd.
         * @return Value of property stateCd.
         *
         */
        public String getStateCd() {
            return this.stateCd;
        }
        
        
        /** Getter for property stateName.
         * @return Value of property stateName.
         *
         */
        public String getStateName() {
            return this.stateName;
        }
        
    }
    
}


