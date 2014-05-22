
package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import java.io.*;
import org.apache.tools.ant.taskdefs.*;

/**
 * Executes a given command if the os platform is appropriate.
 *
 * @author duncan@x180.com
 * @author rubys@us.ibm.com
 * @author thomas.haas@softwired-inc.com
 * @author <a href="mailto:stefan.bodewig@megabit.net">Stefan Bodewig</a>
 * @author <a href="mailto:mariusz@rakiura.org">Mariusz Nowostawski</a>
 */
public class DBImport extends Task {

    private String os;
    private File out;
    private File dir;
    protected boolean failOnError = false;
    private Integer timeout = null;
    private Environment env = new Environment();


   //Commented bt YK    protected Commandline cmdl = new Commandline();
    private FileOutputStream fos = null;

    // Timeout in milliseconds after which the process will be killed.
    public void setTimeout(Integer value) {
        timeout = value;
    }
    //YK Begin
    private String program=null;  //on all our computers it is imp, but in production it is  impO
    private String  fromuser=null;
    private String  touser=null;
    private String  tables=null;
    private String  connection=null;
    private String  data=null;

    public void setProgram(String program) {this.program=program;}
    public String getProgram() {return this.program;}
    public void setFromuser(String fromuser) {this.fromuser=fromuser;}
    public void setTouser(String touser) {this.touser=touser;}
    public void setTables(String tables) {this.tables=tables;}
    public void setConnection(String connection) {this.connection=connection;}
    public void setData(String data) {this.data=data;}
    //YK End



    /* Commented by YK
    // The command to execute.
    public void setExecutable(String value) {
       cmdl.setExecutable(value);
    }
    */


    // The working directory of the process
    public void setDir(File d) {
        this.dir = d;
    }

    /**
     * Only execute the process if <code>os.name</code> includes this string.
     */
    public void setOs(String os) {
        this.os = os;
    }

   /* Commented bt YK
   // The full commandline to execute, executable + arguments.
    public void setCommand(Commandline cmdl) {
        log("The command attribute is deprecated. " +
            "Please use the executable attribute and nested arg elements.",
            Project.MSG_WARN);
   //        this.cmdl = cmdl;
    }
   */

    // File the output of the process is redirected to.
    public void setOutput(File out) {
        this.out = out;
    }

    // Throw a BuildException if process returns non 0.
    public void setFailonerror(boolean fail) {
        failOnError = fail;
    }

    // Add a nested env element - an environment variable.
    public void addEnv(Environment.Variable var) {
        env.addVariable(var);
    }

    /* Commented by YK
    // Add a nested arg element - a command line argument.
    public Commandline.Argument createArg() {
        return cmdl.createArgument();
    }
    */

    //***************************************************************************
    // Do the work.
    public void execute() throws BuildException {
        //All parameters presnt?
        checkParameters();
        //Commented by YK checkConfiguration();
        if (isValidOs()) {
            runExec(prepareExec());
        }
    }

    //***************************************************************************
    private void checkParameters() throws BuildException {
      if (program==null) {
        String message="Parameter program needed for cw_import task";
        throw new BuildException(message);
      }
      if (fromuser==null) {
        String message="Parameter fromuser needed for cw_import task";
        throw new BuildException(message);
      }
      if (touser==null) {
        String message="Parameter touser needed for cw_import task";
        throw new BuildException(message);
      }
      if (tables==null) {
        String message="Parameter tables needed for cw_import task";
        throw new BuildException(message);
      }
      if (connection==null) {
        String message="Parameter connection needed for cw_import task";
        throw new BuildException(message);
      }
      if (data==null) {
        String message="Parameter data needed for cw_import task";
        throw new BuildException(message);
      }

    }
    //***************************************************************************

    /* Commented by YK
    // Has the user set all necessary attributes?
    protected void checkConfiguration() throws BuildException {
        if (cmdl.getExecutable() == null) {
            throw new BuildException("no executable specified", location);
        }
    }
    */

    // Is this the OS the user wanted?
    protected boolean isValidOs() {
        // test if os match
        String myos = System.getProperty("os.name");
        log("Myos = " + myos, Project.MSG_VERBOSE);
        if ((os != null) && (os.indexOf(myos) < 0)){
            // this command will be executed only on the specified OS
            log("Not found in " + os, Project.MSG_VERBOSE);
            return false;
        }
        return true;
    }

    /**
     * Create an Execute instance with the correct working directory set.
     */
    protected Execute prepareExec() throws BuildException {
        // default directory to the project's base directory
        if (dir == null) dir = project.getBaseDir();
        // show the command

//YK        log(cmdl.toString(), Project.MSG_VERBOSE);

        Execute exe = new Execute(createHandler(), createWatchdog());
        exe.setAntRun(project);
        exe.setWorkingDirectory(dir);
        String[] environment = env.getVariables();
        if (environment != null) {
            for (int i=0; i<environment.length; i++) {
                log("Setting environment variable: "+environment[i],
                    Project.MSG_VERBOSE);
            }
        }
        exe.setEnvironment(environment);
        return exe;
    }

    //***************************************************************************
    // Run the command using the given Execute instance.
    protected void runExec(Execute exe) throws BuildException {
        String[] startImport=new String[10];

        //fill up command parameters

        startImport[0]=program;
        startImport[1]=connection;
        startImport[2]="IGNORE=Y";
        startImport[3]="GRANTS=N";
        startImport[4]="INDEXES=N";
        startImport[5]="CONSTRAINTS=N";
        startImport[6]="file="+data;
        startImport[7]="fromuser="+fromuser;
        startImport[8]="touser="+touser;
        startImport[9]="";

        FileInputStream fis;
        String tablesNames;
        int fileLen;
        try {
          fis = new FileInputStream(tables);
          fileLen = fis.available();
          byte[] templ = new byte[fileLen];
          fis.read(templ);
          fis.close();
          tablesNames=new String(templ);
        } catch (FileNotFoundException exc){
          String message=exc.getMessage();
          throw new BuildException (message);
        } catch (IOException exc) {
          String message=exc.getMessage();
          throw new BuildException (message);
        }

        StringReader tableReader = new StringReader(tablesNames);
        BufferedReader tableList = new BufferedReader(tableReader);
        String line;
        String tbls="";
        //open tables names file
        int ii=0;
        try{
            while ((line=tableList.readLine()) != null){
              //System.out.println("LLLLLLLLLLLLL line "+line);
              if(line.trim().length()==0) continue;
              if(line.trim().startsWith("#")) continue;
              if(ii!=0) tbls+=", ";
              tbls+=line;
              ii++;
              if(ii==150) {
                 startImport[9]="tables="+tbls;
                 runImport(exe, startImport);
                 ii=0;
                 tbls="";
              }
            }
        } catch (IOException exc) {
          String message= exc.getMessage();
          throw new BuildException(message);
        }
        if(ii>0) {
           startImport[9]="tables="+tbls;
           runImport(exe, startImport);
        }

    }
    //***************************************************************************
    // Run import command
    protected void runImport(Execute exe, String[] startImport ) throws BuildException {
        int err = -1; // assume the worst
        try {
            exe.setCommandline(startImport);
            err = exe.execute();
            if (err != 0) {
                if (failOnError) {
                    throw new BuildException("Exec returned: "+err, location);
                } else {
                    log("Result: " + err, Project.MSG_ERR);
                }
            }
        } catch (IOException e) {
            throw new BuildException("Execute failed: " + e, e, location);
        } finally {
            // close the output file if required
            logFlush();
        }
    }

    /**
     * Create the StreamHandler to use with our Execute instance.
     */
    protected ExecuteStreamHandler createHandler() throws BuildException {
        if(out!=null)  {
            try {
                fos = new FileOutputStream(out);
                log("Output redirected to " + out, Project.MSG_VERBOSE);
                return new PumpStreamHandler(fos);
            } catch (FileNotFoundException fne) {
                throw new BuildException("Cannot write to "+out, fne, location);
            } catch (IOException ioe) {
                throw new BuildException("Cannot write to "+out, ioe, location);
            }
        } else {
            return new LogStreamHandler(this,
                                        Project.MSG_INFO, Project.MSG_WARN);
        }
    }

    /**
     * Create the Watchdog to kill a runaway process.
     */
    protected ExecuteWatchdog createWatchdog() throws BuildException {
        if (timeout == null) return null;
        return new ExecuteWatchdog(timeout.intValue());
    }

    /**
     * Flush the output stream - if there is one.
     */
    protected void logFlush() {
        try {
            if (fos != null) fos.close();
        } catch (IOException io) {}
    }

}
