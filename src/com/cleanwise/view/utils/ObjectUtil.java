package com.cleanwise.view.utils;

import com.cleanwise.service.api.process.variables.InboundContent;
import oracle.jdbc.driver.OracleTypes;
import oracle.sql.BLOB;

import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ObjectUtil {
    public final static Class BYTE_ARRAY_CLASS = new byte[0].getClass();
    public final static Class INBOUND_CONTENT = InboundContent.class;

    private final static Set primitives = new HashSet();
    static {
        primitives.add(Boolean.TYPE);
        primitives.add(Boolean.class);
        primitives.add(Byte.TYPE);
        primitives.add(Byte.class);
        primitives.add(Short.TYPE);
        primitives.add(Short.class);
        primitives.add(Character.TYPE);
        primitives.add(Character.class);
        primitives.add(Integer.TYPE);
        primitives.add(Integer.class);
        primitives.add(Long.TYPE);
        primitives.add(Long.class);
        primitives.add(Float.TYPE);
        primitives.add(Float.class);
        primitives.add(Double.TYPE);
        primitives.add(Double.class);
        primitives.add(BigInteger.class);
        primitives.add(BigDecimal.class);
        primitives.add(String.class);
    }

    public final static boolean isPrimitive(Class clazz) {
        return primitives.contains(clazz);
    }

    public final static Blob bytesToBlob(Connection conn, byte[] data)
            throws Exception {
        Blob blob = createTemporaryBlob(conn);
        OutputStream out = blob.setBinaryStream(0);
        out.write(data);
        out.flush();
        out.close();
        return blob;
    }

    public static Blob createTemporaryBlob(Connection conn) throws SQLException {
        CallableStatement cst = null;
        try {
            cst = conn
                    .prepareCall("{call dbms_lob.createTemporary(?, false, dbms_lob.SESSION)}");
            cst.registerOutParameter(1, OracleTypes.BLOB);
            cst.execute();
            return cst.getBlob(1);
        } finally {
            if (cst != null) {
                cst.close();
            }
        }
    }

    public final static void bytesToBlob(BLOB blob, byte[] data)
            throws Exception {
        OutputStream out = blob.setBinaryStream(0);
        out.write(data);
        out.flush();
        out.close();
    }

    public final static long length(Blob blob) throws SQLException {
        return (blob == null) ? 0 : blob.length();
    }

    public final static byte[] blobToBytes(Blob blob) throws SQLException {
        if (blob == null) {
            return null;
        }
        long len = blob.length();
        return blob.getBytes(1L, (int) len);
    }

    public final static byte[] objectToBytes(Object pObj)
            throws java.io.IOException {
        java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
        os.writeObject(pObj);
        os.flush();
        os.close();
        return oStream.toByteArray();
    }

    public final static Object bytesToObject(byte[] pBytes) {
        Object obj = null;
        java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(
                pBytes);
        try {
            java.io.ObjectInputStream is = new java.io.ObjectInputStream(
                    iStream);
            obj = is.readObject();
            is.close();
            iStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return obj;
    }

    public final static String clobToString(Clob clob) {
        try {
            if (clob != null && clob.length() > 0) {
                StringBuffer sb = new StringBuffer();
                Reader reader = clob.getCharacterStream();
                char buffer[] = new char[1024];
                do {
                    int count = reader.read(buffer);
                    if (count > 0) {
                        sb.append(buffer, 0, count);
                    } else {
                        break;
                    }
                } while (true);
                reader.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
