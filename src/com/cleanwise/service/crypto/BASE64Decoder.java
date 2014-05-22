package com.cleanwise.service.crypto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class BASE64Decoder {
    public byte[] decodeBuffer(String base64) {
        return Base64.decode(base64);
    }
    /**
     * Copies the given file assumed to be Base64 encoded,
     * to a unencoded file. The input file is assumed to consist of records with
     * a length which is a multiple of 4. If not, the record is not decoded
     * but is written to the output file in its raw form.
     * <P>
     * It is further assumed that the file contains only ASCII characters which
     * should be the case if it is Base64 encoded. 
     * 
     * @param inputFilename
     *               The name of the file to convert.
     * @param outputFilename
     *               Name of the output file.
     * @exception IOException
     *                   Thrown for any problem with the IO, e.g. file not found.
     */
    public void decode (String inputFilename, String outputFilename) 
    throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(inputFilename));
        FileOutputStream out = new FileOutputStream(outputFilename);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if ((inputLine.length() % 4) == 0) {
                out.write(decodeBuffer(inputLine));
            } else {
                out.write(inputLine.getBytes());
            }
        }
        in.close();
        out.flush();
        out.close();
    }

}
