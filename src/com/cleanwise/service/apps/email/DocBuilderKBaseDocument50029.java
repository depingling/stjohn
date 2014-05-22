package com.cleanwise.service.apps.email;

import com.cleanwise.service.api.value.KBaseDocument50029;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.process.operations.FileGenerator;
import com.cleanwise.service.apps.dataexchange.OutboundDocSender;

import java.io.*;
import java.util.Iterator;
import java.util.Date;
import java.text.NumberFormat;


public class DocBuilderKBaseDocument50029 extends com.cleanwise.service.api.process.operations.DocBuilder implements FileGenerator {

    private static String className="DocBuilderKBaseDocument50029";


    private void writeKBaseDoc50029ToFile(KBaseDocument50029 doc) throws IOException {
        StringBuffer sb = new StringBuffer();
        writeHeader(sb,doc);
        nextLine(sb);
        nextLine(sb);
        writeBody(sb,doc);
        nextLine(sb);
        nextLine(sb);
        writeOptions(sb,doc);
        String resultString=sb.toString();
        resultString=setPageNumberCount(resultString);
        //writeFooter(sb,doc);
        os.write(resultString.getBytes());
    }

    private void writeOptions(StringBuffer sb, KBaseDocument50029 doc) {
        line.append("Options2 : ");
        nextLine(sb);
        line.append("S1CPItemCodes_Start="+KBaseDocument50029.S1CPItemCodes_Start);
        nextLine(sb);
        PairViewVector pairsSkuQty = doc.getPairsSkuQty();
        if(pairsSkuQty!=null&&pairsSkuQty.size()>0)
        {
          
           Iterator it= pairsSkuQty.iterator();
            while(it.hasNext())
            {
             PairView pair= (PairView) it.next();
             line.append(pair.getObject1()+"="+pair.getObject2());
             nextLine(sb);

           }
        }

        line.append("S1CPItemCodes_End="+KBaseDocument50029.S1CPItemCodes_End);
        nextLine(sb);
    }

    private void writeBody(StringBuffer sb, KBaseDocument50029 doc) {
        line.append("S1DocFormat="+KBaseDocument50029.S1DocFormat);
        nextLine(sb);
        line.append("S1DocType="+KBaseDocument50029.S1DocType);
        nextLine(sb);
        line.append("S1DocSource="+KBaseDocument50029.S1DocSource);
        nextLine(sb);
        line.append("S1CustAcct="+doc.getS1CustAcct());
        nextLine(sb);
        line.append("S1CustName="+doc.getS1CustName());
        nextLine(sb);
        line.append("S1CustShipTo="+KBaseDocument50029.S1CustShipTOCode);
        nextLine(sb);
        line.append("S1CustShipToCode="+KBaseDocument50029.S1CustShipTOCode);
        nextLine(sb);
        line.append("S1PONum="+doc.getS1PONum());
        nextLine(sb);
        nextLine(sb);
        Iterator it = doc.getS1Instruct().iterator();
        NumberFormat nf=NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(2);
        int i=0;
        while (it.hasNext()) {
            line.append("S1Instruct" +nf.format(++i)+"="+it.next());
            nextLine(sb);

        }
        
    }

    private void writeHeader(StringBuffer sb, KBaseDocument50029 doc) {

        line.append(align("Summary : "+doc.getSummary(),CENTER));
        nextLine(sb);
        nextLine(sb);
        line.append("Primary Keyword : "+doc.getPrimaryKeyword());
        line.append(align("Public KBDoc ? "+(doc.getPublicKBDoc()?"Y":"N"),RIGTH));
        nextLine(sb);
        line.append("Other  Keyword : "+doc.getOtherKeyword());
        nextLine(sb);
        nextLine(sb);
        line.append(getCharLine(' ',2)+"Owner : ");
        line.append(align("Module : "+doc.getModule(),RIGTH));
        nextLine(sb);
        line.append(getCharLine(' ',4)+"Entered : "+doc.getEnteredData());
        line.append(align("Step  "+(doc.getStep())+" Version  "+(doc.getVersion()),RIGTH));
        nextLine(sb);
        line.append(getCharLine(' ',4)+"Entered By : "+doc.getEnteredBy());
        nextLine(sb);
        line.append(getCharLine(' ',4)+"Last Modify : "+doc.getLastModifyData());
        nextLine(sb);
        line.append(getCharLine(' ',4)+"Modify By : "+doc.getLastModifyBy());
        nextLine(sb);

   }

    public void writeTitle(StringBuffer sb) {
       line.append(new Date());
       line.append(getCharLine(' ',7));
       line.append(KBaseDocument50029.Title);
       line.append(align("Page "+pageNumber +" of "+"$pnc$",RIGTH));
       nextLine(sb);
       line.append(getCharLine('_',DOC_LENGTH));
       nextLine(sb);
       nextLine(sb);

    }

    public String generate(Object data, String fileName) throws Exception {
    	return generate(data, new File(fileName));
    }
    
    public String generate(Object data, File file) throws Exception {
        KBaseDocument50029 doc = new KBaseDocument50029((OutboundDocSender.BuilderRequest) data);
        doc.createTestValue();
        setUpOutputStream(file);
        writeKBaseDoc50029ToFile(doc);
        closeOutputStream();
        return file.getAbsolutePath();
    }
}
