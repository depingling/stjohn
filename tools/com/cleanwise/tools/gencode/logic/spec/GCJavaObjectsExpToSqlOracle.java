/**
 * Title:        GCJavaObjectsExpToSqlOracle
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic.spec;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.GCJavaObjects;
import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCSequence;
import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToSql;


public class GCJavaObjectsExpToSqlOracle implements GCJavaObjectsExpToSql {

    public StringReader exportToSql(Properties settings, GCJavaObjects javaObjects) throws GCException {
        if (javaObjects == null) {
            return new StringReader("");
        }
        String schemaPattern = "";
        String tableSpace = "";
        if (settings != null) {
            schemaPattern = (String) settings.get(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN);
            tableSpace = (String) settings.get(GCCodeNames.Property.DATABASE_TABLESPACE);
        }

        System.out.println("#########################################################");
        System.out.println("# [GCJavaObjectsExpToSqlOracle] PROPERTIES");
        System.out.println("# " + GCCodeNames.Property.DATABASE_SCHEMA_PATTERN + ": " + schemaPattern);
        System.out.println("# " + GCCodeNames.Property.DATABASE_TABLESPACE + ": " + tableSpace);
        System.out.println("#########################################################");

        StringReader stringReader = null;
        try {
            StringWriter stringWriter = new StringWriter();

            /// Tables
            if (javaObjects.getTables() != null) {
                for (int i = 0; i < javaObjects.getTables().size(); ++i) {
                    GCTable table = (GCTable)javaObjects.getTables().get(i);
                    GCOracleUtils.WriteSqlToCreateTable(stringWriter, table, schemaPattern, tableSpace);
                }
            }

            /// Sequences
            if (javaObjects.getSequences() != null) {
                for (int i = 0; i < javaObjects.getSequences().size(); ++i) {
                    GCSequence sequence = (GCSequence)javaObjects.getSequences().get(i);
                    GCOracleUtils.WriteSqlToCreateSequence(stringWriter, sequence, schemaPattern);
                }
            }

            /// Indexes
            if (javaObjects.getIndexes() != null) {
                for (int i = 0; i < javaObjects.getIndexes().size(); ++i) {
                    GCIndex index = (GCIndex)javaObjects.getIndexes().get(i);
                    GCOracleUtils.WriteSqlToCreateIndex(stringWriter, index, schemaPattern);
                }
            }

            /// Foreign keys
            if (javaObjects.getForeignKeys() != null) {
                for (int i = 0; i < javaObjects.getForeignKeys().size(); ++i) {
                    GCForeignKey foreignKey = (GCForeignKey)javaObjects.getForeignKeys().get(i);
                    GCOracleUtils.WriteSqlToCreateForeignKey(stringWriter, foreignKey, schemaPattern);
                }
            }

            stringWriter.flush();
            stringReader = new StringReader(stringWriter.getBuffer().toString());
            stringWriter.close();
        }
        catch (GCException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new GCException("Exception occurred. " + 
                ex.getMessage(), "GCJavaObjectsExpToSqlOracle.exportToSql");
        }
        return stringReader;
    }

}
