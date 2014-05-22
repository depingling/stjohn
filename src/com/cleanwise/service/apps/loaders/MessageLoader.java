/*
 * MessageLoader.java
 *
 * Created on June 22, 2006, 4:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.service.apps.loaders;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import java.io.*;
import java.util.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import org.apache.log4j.Logger;
/**
 *
 * @author Ykupershmidt
 */
public class MessageLoader {
  private static final Logger log = Logger.getLogger(MessageLoader.class);
  /** Creates a new instance of MessageLoader */
  public MessageLoader() {
  }
  public int uploadMessages(String fileName, String locale, int storeId)
  throws Exception {
    // Check for a properties file command option.
    String propFileName = System.getProperty("conf");
log.info("Properties: "+propFileName);
    Properties props = new Properties();
    props.load(new FileInputStream (propFileName) );
    InitialContext jndiContext = new InitialContext(props);

    Object ref  = jndiContext.lookup(JNDINames.KEYWORD_EJBHOME);
    KeywordHome kHome = (KeywordHome)
                    PortableRemoteObject.narrow (ref, KeywordHome.class);
     Keyword keywordBean = kHome.create();
     File messages = new File(fileName);
     if(!messages.exists() || !messages.isFile()) {
       log.info("Error. Can't find file: "+fileName);
       return 2;
     }
     LinkedList propLL = new LinkedList();
     FileInputStream fis = new FileInputStream(messages);
    // BufferedReader rdr = new BufferedReader(new InputStreamReader(fis,"ISO-8859-1"));
     BufferedReader rdr = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
     String line = rdr.readLine();
     while(line!=null) {
       propLL.add(line);
       line = rdr.readLine();
     }
     MessageResourceDataVector messageDV = new MessageResourceDataVector();
     ArrayList allKeys = new ArrayList();
     ArrayList duplicateKeys = new ArrayList();
     for(Iterator iter=propLL.iterator(); iter.hasNext();) {
       String str = (String) iter.next();
       str = str.trim();
       if(str.length()>0) {
         if(!str.startsWith("#")) {
           int ind = str.indexOf("=");
           if(ind>=0) {
             String key = str.substring(0,ind);
             key=key.trim();
             String value = "";
             if(str.length()>ind+1) {
               value = str.substring(ind+1).trim();
             }
             MessageResourceData mD = MessageResourceData.createValue();
             mD.setAddBy("MessageLoader");
             mD.setModBy("MessageLoader");
             mD.setBusEntityId(storeId);
             mD.setName(key);
			 List<String> excludes = Arrays.asList("shop.inputDateFormat","shop.inputTimeFormat");
             if("xx-piglatin".equals(locale)&& !excludes.contains(key)){
            	 value = encryptToPigLatin(value);
             }
             mD.setValue(value);
             mD.setLocale(locale);

             // check key for duplicates
             if (allKeys.contains(key))  {
                 log.info("----Error: duplicate key " + key);
                 duplicateKeys.add(key);
             } else {
                 allKeys.add(key);
                 messageDV.add(mD);
             }
             log.info("$$ "+key+" = "+value+"$");
           }
         }
       }
     }
     if(messageDV.size()>0) {
       keywordBean.loadMessages(messageDV);
     }
      if (duplicateKeys.size() > 0) {
         log.info("Warning! Duplicate key(s) found: ");
         Iterator ii = duplicateKeys.iterator();
         while (ii.hasNext()) {
             String key = (String)ii.next();
             log.info(" --   " + key);
         }
      }
     return 0;
  }


  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    // TODO code application logic here
    log.info("Cleanwise Message Loader. Parameters: ");
    log.info(" - fileName");
    log.info(" - locale (type null, if messages are not locale related)");
    log.info(" - store id (type 0, if messges are not store related)");
    if(args.length<3) {
      log.info("Error. Only "+args.length+" parameters found");
      return;
    }
    int storeId = 0;
    try {
      storeId = Integer.parseInt(args[2]);
    }catch(Exception exc) {
      log.info("Error. Wrong store id format: "+args[2]);
      return;
    }
    String locale = args[1];
    String fileName = args[0];

    MessageLoader ml = new MessageLoader();
    ml.uploadMessages(fileName,locale,storeId);
    return;
  }




  /** Encrypts text into Pig-latin.
   *  @param text The plain-text to be encrypted.
   *  @return The encrypted text.
   */

  public static String encryptToPigLatin ( String text )
  {
     if ( text.length() == 0 )
        return "";
     StringReader sr = new StringReader( text );
     StringWriter sw = new StringWriter();

     for ( ;; )
     {
        String word = getNextWord( sr, sw );
        if ( word.equals("") )
            break;  // End of text reached, all done.

        // Write the "pig latin"-ized word:
        sw.write( xlateWord(word) );
     }
     try
     {  sr.close();
        sw.close();
     } catch ( Exception ignored ) {}
     return sw.toString();
  }


//   getNextWord is a kind of String tokenizer, but it correctly
//   handles punctuation symbols:
  private static String getNextWord (StringReader sr, StringWriter sw)
  {
     char c = ' ';  // Initialize to avoid annoying javac warnings!
     int ch = 0;

     // Read the input, writting the characters read, until
     // the start of a word is found:

     try
     {
        while ( ( ch=sr.read() ) !=  -1 )
        {
           c = (char) ch;
           if ( Character.isLetter(c) ) break;
           sw.write( c );
        }
     } catch ( IOException ignored ) {}

     if ( ch == -1 )  return "";   // All done; return an empty string.

     // Now collect all the letters in the word, and return it:
     StringBuffer word = new StringBuffer();
     do
     {  try
        {  word.append( c );
           sr.mark(1);
           ch = sr.read();
           c = (char) ch;
        } catch (IOException ignored) {}
     } while ( (ch !=  -1) && ( c == '-' || c == '\'' || Character.isLetter(c) ) );

     if ( ch != -1 ) try {
        sr.reset();  // rewind to the last mark, i.e., back up one character.
     }  catch ( IOException ignored ) {}

     return word.toString();
  }


//   xlateWord implements the Pig Latin rules and translates its arg.
  private static String xlateWord ( String inWord )
  {
     StringBuffer word = new StringBuffer( inWord );
     char let = word.charAt( 0 );
     boolean isUpCase = Character.isUpperCase( let );
     boolean allCaps = inWord.equals( inWord.toUpperCase() );
     boolean containsHyphen = ( inWord.indexOf( '-' ) != -1 );
     let = Character.toLowerCase( let );

     // Words not begining with a consonant have "way" appended:

     if ( let == 'a' || let == 'e' || let == 'i' || let == 'o' || let == 'u'
          || ( ! Character.isLetter(let) )  )
     {
        if ( allCaps )
           word.append( "WAY" );
        else
           word.append( "way" );
        return word.toString();
     }

     // Words begining with a consonant have the leading consonants
     // (including a "u" preceeded by a "q") shifted to the end,
     // and the suffix "ay" is then added:

     int i;
     boolean qFlag = false;     // When true shows the previous letter was a 'q'.
     if ( ! allCaps )
        word.setCharAt( 0, let );  // Replace original letter with lower-case.
     StringBuffer leadingConsonants = new StringBuffer();

     loop:
     for ( i=0; i<word.length(); ++i )
     {
        let = word.charAt( i );
        switch ( Character.toLowerCase(let) )
        {
           case 'a':  case 'e':  case 'i':  case 'o':  case 'y':
              break loop;

           case '-':              // Turn "q-tip" into "tip-qay".
              word.append( '-' );
              ++i;
              break loop;

           case 'q':
              qFlag = true;
              leadingConsonants.append( let );
              break;

           case 'u':
              if ( qFlag )
              {  qFlag = false;
                 leadingConsonants.append( let );
                 break;
              } else
              {  break loop;
              }

           default:
              qFlag = false;
              leadingConsonants.append( let );
              break;
        }
     }

     if ( allCaps )
        leadingConsonants.append( "AY" );
     else
        leadingConsonants.append( "ay" );

     if ( i >= word.length() )  // Then the word has no vowels!
        return leadingConsonants.toString();

     if ( isUpCase )   // If original word was capitalized, so is result.
     {  let = word.charAt( i );
        word.setCharAt( i, Character.toUpperCase(let) );
     }

     StringBuffer outWord = new StringBuffer();
     outWord.append( word.toString().substring(i) );
     outWord.append( leadingConsonants.toString() );
     return outWord.toString();
  }
  
}
