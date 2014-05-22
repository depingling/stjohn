package com.cleanwise.tools;

import java.io.*;
//import org.apache.xalan.xslt.*;
//import org.apache.xalan.transformer.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import java.util.LinkedList;

/* 
 * Tried to make this an Ant task, however it seemed there was a
 * conflict the XML parser used internally by Ant and that required by
 * the Xerces/Xalan libraries for this processor.  So made this a
 * standalone app.
 */
public class XMLTableProcessor {
  private String input;
  private String style;
  private String outdir;
  private String prefix;
  private String suffix;
  private String singleOutFileName;
  private boolean verbose = true;
  private String tagName = "table";
  private String javaName = null;
  
  public void setTagName(String tagName){
      this.tagName = tagName;
  }
  
  public void setInput(String input) {
    this.input = input;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public void setOutdir(String outdir) {
    this.outdir = outdir;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public void setSingleOutFileName(String singleOutFileName) {
    this.singleOutFileName = singleOutFileName;
  }
  
  public void setJavaName(String javaName) {
    this.javaName = javaName;
  }
  public String getJavaName() {
    return this.javaName;
  }
  
  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  private void parseArgs(String args[]) {
    int i = 0;
    while (i < args.length) {
      String s = args[i];
      if (s.startsWith("-input")) {
	setInput(s.substring(6).trim());
	i++;
      } else if (s.startsWith("-style")) {
	setStyle(s.substring(6).trim());
	i++;
      } else if (s.startsWith("-outdir")) {
	setOutdir(s.substring(7).trim());
	i++;
      } else if (s.startsWith("-prefix")) {
	setPrefix(s.substring(7).trim());
	i++;
      } else if (s.startsWith("-suffix")) {
	setSuffix(s.substring(7).trim());
	i++;
      } else if (s.equalsIgnoreCase("-verbose")) {
	setVerbose(true);
	i++;
      } else if (s.equalsIgnoreCase("-noverbose")) {
	setVerbose(false);
	i++;
      } else if (s.startsWith("-tagname")) {
          setTagName(s.substring(8).trim());
	i++;
     } else if (s.startsWith("-singleOutFileName")) {
          setSingleOutFileName(s.substring(18).trim());
	i++;
     } else if (s.startsWith("-javaname")) {
	   String jn = s.substring("-javaname".length()).trim();
       if("all".equalsIgnoreCase(jn)) jn = null;
       setJavaName(jn);
	i++;
      } else {
	System.err.println(args[i]);
	printUsage();
      }
    }
  }

  public static void main(String args[]) {
    XMLTableProcessor process = new XMLTableProcessor(); 
    process.parseArgs(args);
    process.execute();
  }

  public void log(String msg) {
    if (verbose) {
      System.out.println(msg);
    }
  }

  public void execute() {
    if(javaName!=null && javaName.startsWith("${")) {
      String mess = "Parameter javaname is missing. Use -Djavaname=all to jenerate all classes";
      System.out.println(mess);
      return;
    }
    if (input == null || input.length() == 0 || 
	    style == null || style.length() == 0) {
      System.err.println("No input xml or style sheet specified.");
      printUsage();
    }
    if (outdir == null)
      outdir = ".";
      File file1 = new File(outdir);
      if (!file1.exists()) {
      log(" does not exist. " + "attempting to create.");
      boolean flag = file1.mkdirs();
      if (!flag) {
	      System.err.println(file1.getAbsolutePath() + " does not " + "exist, and cannot be created.");
      	System.exit(1);
      }
    }

    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      log(input + "'");
      File file2 = new File(input);
	  
      log(new StringBuffer("Processing from ").append(input).toString());
      log(new StringBuffer("With XSL: ").append(style).toString());
      FileInputStream fileInputStream1 = new FileInputStream(file2);
      Document document = documentBuilder.parse(fileInputStream1);
      NodeList nodeList = document.getElementsByTagName(tagName);
      LinkedList nodeListLL = new LinkedList();
      for (int i = 0; i < nodeList.getLength(); i++) {        
	      Node node1 = nodeList.item(i);
	      NamedNodeMap namedNodeMap = node1.getAttributes();
          String javaNameAttr = "";
          try {
             Node nnn = namedNodeMap.getNamedItem("javaname");          
             javaNameAttr = nnn.getNodeValue();          
          } catch (Exception exc) {
System.out.println("javaname tag doesn't exist");                
          }
        if(javaName==null || javaName.equalsIgnoreCase(javaNameAttr)) {
          nodeListLL.add(node1);
        }
      }
      log(new StringBuffer("Table node count: ").append(nodeListLL.size()).toString());
      //nodeList = null;
      for (int i = 0; i < nodeListLL.size(); i++) {        
          Node node1 = (Node) nodeListLL.get(i);
          NamedNodeMap namedNodeMap = node1.getAttributes();
          String javaNameAttr = "";
          try {
             Node nnn = namedNodeMap.getNamedItem("javaname");          
             javaNameAttr = nnn.getNodeValue();          
          } catch (Exception exc) {
System.out.println("javaname tag doesn't exist1");                
          }
                           
          if(javaName!=null && !javaName.equalsIgnoreCase(javaNameAttr)) {
              continue;
          }
          String outname;
 System.out.println("singleOutFileName: "+singleOutFileName);                
        if(singleOutFileName != null){
           outname = singleOutFileName;
            if(nodeList.getLength() > 1){
                throw new IllegalStateException("single output file specified with a xml file with more then 1 node named: "+tagName);
            }
        }else{
            Node node2 = namedNodeMap.getNamedItem("javaname");
            outname = node2.getNodeValue();
            if (prefix != null)
              outname = new StringBuffer(String.valueOf(prefix)).append(outname).toString();
            if (suffix != null)
              outname = new StringBuffer(String.valueOf(outname)).append(suffix).toString();
        }
 System.out.println("outname: "+outname);                
        
		  
        //StreamResult output = new StreamResult(file3);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		StreamResult output = new StreamResult(bout);
        TransformerFactory factory = TransformerFactory.newInstance();
        DOMSource xmlSource = new DOMSource(node1);
	      FileInputStream fileInputStream2 = new FileInputStream(style);
        StreamSource streamSource = new StreamSource(fileInputStream2);
        Transformer trans = factory.newTransformer(streamSource);  
        trans.transform(xmlSource, output);
		
		log(outname);
	    File file3 = new File(file1, outname);
		if(file3.exists()){
			file3.delete();
		}
		file3.createNewFile();
		FileOutputStream fout = new FileOutputStream(file3);
		bout.writeTo(fout);
		fout.flush();
		fout.close();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      log("XMLTableProcessor done.");
    }
  }

  static void printUsage()
  {
    String string = "XMLTableProcessor 	-input <inputfile> 	" +
            "-styles <stylelist>\n    Options: \n    " +
            "-input <inputfile>: a db.xml file output from DBtoXML\n    	" +
            "-style <stylelist>: the style sheet to process\n    " +
            "-outdir <dir>: the directory in which to dump output. \n    	" +
            "-prefix <filenameprefix>: The filename prefix, prepended to tablenamd\n    " +
            "-suffix <filenamesuffix>: The filename suffix, apppended to tablename   " +
            "-tagname <tagname>: the tag name in the xml file (optional)" +
            "-table <table>: table name or \"all\" if all tables requested (mandatory)" +
            "\n";
    System.err.println(string);
    System.exit(1);
  }
}
