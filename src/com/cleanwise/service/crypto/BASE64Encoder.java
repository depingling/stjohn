package com.cleanwise.service.crypto;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class BASE64Encoder {
    public String encode(byte[] raw) {
        return Base64.encode(raw);
    }

    public String encode(byte[] raw, int length) {
        return Base64.encode(raw,length);
    }
    /**
     * Copies the given file to Base64 encoded file. Each 48 bytes of the
     * input file is converted to 64 ASCII digits and written out with a CR-LF.
     * 
     * @param inputFilename
     *               The name of the file to convert.
     * @exception IOException
     *                   Thrown for any problem with the IO, e.g. file not found.
     */
    public void encode (String inputFilename, String outputFilename) 
    throws IOException {
        FileInputStream in = new FileInputStream(inputFilename);
        PrintWriter out = new PrintWriter(new FileOutputStream(outputFilename));
        byte [] buf = new byte [48];
        int bytesRead;
        while ((bytesRead = in.read(buf)) > 0) {
            out.println(encode(buf, bytesRead));
        }
        in.close();
        out.flush();
        out.close();
    }
}
