// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.net.*;
import java.io.*;
import Server.*;

/**
 * This class is a program that uses the Server class defined in Chapter 9.
 * Server would load arbitrary "Service" classes to provide services.
 * This class is an alternative program to start up a Server in a similar 
 * way.  The difference is that this one uses a SecurityManager and a 
 * ClassLoader to prevent the Service classes from doing anything damaging
 * or malicious on the local system.  This allows us to safely run Service 
 * classes that come from untrusted sources.
 **/
public class SafeServer {
  public static void main(String[] args) {
    try {
      // Install the Security manager
      System.setSecurityManager(new ServiceSecurityManager());

      // Create the ClassLoader that we'll use to load Service classes.
      // The classes should be stored in (or beneath) the directory specified
      // as the first command-line argument
      LocalClassLoader loader = new LocalClassLoader(args[0]);

      // Create a Server object that does no logging and
      // has a limit of five concurrent connections at once.
      Server server = new Server(null, 5);

      // Parse the argument list, which should contain Service name/port pairs.
      // For each pair, load the named Service using the class loader, then
      // instantiate it with newInstance(), then tell the server to start
      // running it.
      int i = 1;
      while(i < args.length) {
        Class serviceClass = loader.loadClass(args[i++]);      // dynamic load
        Service service = (Service)serviceClass.newInstance(); // instantiate
        int port = Integer.parseInt(args[i++]);                // get port
        server.addService(service, port);                      // run service
      }
    }
    catch (Exception e) { // Display a message if anything goes wrong
      System.err.println(e);
      System.err.println("Usage: java SafeServer "  +
                         "<servicedir> <servicename> <port>\n" +
                         "\t\t[<servicename> <port> ... ]");
      System.exit(1);
    }
  }

  /**
   * This is a fairly uptight security manager subclass.  Classes loaded by
   * a ClassLoader are highly restricted in what they are allowed to do.
   * This is okay, because our Service classes have a very narrowly defined
   * task: to read from one stream and send output to another.  
   *
   * A SecurityManager consists of various methods that the system calls to
   * check whether certain sensitive operations should be allowed.  These
   * methods can throw a SecurityException to prevent the operation from
   * happening.  With this SecurityManager, we want to prevent untrusted
   * code that was loaded by a class loader from performing those sensitive
   * operations.  So we use inherited SecurityManager methods to check
   * whether the call is being made by an untrusted class.  If it is, we
   * throw an exception.  Otherwise, we simply return, allowing the
   * operation to proceed normally.
   **/
  public static class ServiceSecurityManager extends SecurityManager {
    /**
     * This is the basic method that tests whether there is a class loaded
     * by a ClassLoader anywhere on the stack.  If so, it means that that
     * untrusted code is trying to perform some kind of sensitive operation.
     * We prevent it from performing that operation by throwing an exception.
     * trusted() is called by most of the check...() methods below.
     **/
    protected void trusted() {
      if (inClassLoader()) throw new SecurityException();
    }

    /**
     * This is a variant on the trusted() method above.  There are a couple
     * of methods that loaded code should not be able to call directly, but
     * which system methods invoked by the loaded code still need to be
     * able to call.  So for these, we only throw an exception if a
     * loaded class is the third thing on the call stack.  I.e. right above
     * this method and the check...() method that invoked it
     **/
    protected void trustedOrIndirect() {
      if (classLoaderDepth() == 3) throw new SecurityException();
    }

    /**
     * Here's another variant.  It denies access if a loaded class attempts
     * the operation directly or through one level of indirection.  It is used
     * to prevent loaded code from calling Runtime.load(), or 
     * System.loadLibrary() (which calls Runtime.load()).
     **/
    protected void trustedOrIndirect2() {
      int depth = classLoaderDepth();
      if ((depth == 3) || (depth == 4)) throw new SecurityException();
    }

    /**
     * These are all the specific checks that a security manager can
     * perform.  They all just call one of the methods above and throw a
     * SecurityException if the operation is not allowed.  This 
     * SecurityManager subclass is perhaps a little too restrictive.  For
     * example, it doesn't allow loaded code to read *any* system properties,
     * even though some of them are quite harmless.
     **/
    public void checkCreateClassLoader() { trustedOrIndirect(); }
    public void checkAccess(Thread g) { trusted(); }
    public void checkAccess(ThreadGroup g) { trusted(); }
    public void checkExit(int status) { trusted(); }
    public void checkExec(String cmd) { trusted(); }
    public void checkLink(String lib) { trustedOrIndirect2(); }
    public void checkRead(FileDescriptor fd) { trusted(); }
    public void checkRead(String file) { trusted(); }
    public void checkRead(String file, Object context) { trusted(); }
    public void checkWrite(FileDescriptor fd) { trusted(); }
    public void checkWrite(String file) { trusted(); }
    public void checkDelete(String file) { trusted(); }
    public void checkConnect(String host, int port) { trusted(); }
    public void checkConnect(String host,int port,Object context) {trusted();}
    public void checkListen(int port) { trusted(); }
    public void checkAccept(String host, int port) { trusted(); }
    public void checkMulticast(InetAddress maddr) { trusted(); }
    public void checkMulticast(InetAddress maddr, byte ttl) { trusted(); }
    public void checkPropertiesAccess() { trustedOrIndirect(); }
    public void checkPropertyAccess(String key) { trustedOrIndirect(); }
    public void checkPrintJobAccess() { trusted(); }
    public void checkSystemClipboardAccess() { trusted(); }
    public void checkAwtEventQueueAccess() { trusted(); }
    public void checkSetFactory() { trusted(); }
    public void checkMemberAccess(Class clazz, int which) { trusted(); }
    public void checkSecurityAccess(String provider) { trusted(); }

    /** Loaded code can only load classes from java.* packages */
    public void checkPackageAccess(String pkg) { 
      if (inClassLoader() && !pkg.startsWith("java."))
        throw new SecurityException();
    }

    /** Loaded code can't define classes in java.* or sun.* packages */
    public void checkPackageDefinition(String pkg) { 
      if (inClassLoader() && (pkg.startsWith("java.")||pkg.startsWith("sun.")))
        throw new SecurityException();
    }

    /** 
     * This is the one SecurityManager method that is different from the
     * others.  It indicates whether a top-level window should display an
     * "untrusted" warning.  The window is always allowed to be created, so
     * this method is not normally meant to throw an exception.  It should
     * return true if the window does not need to display the warning, and
     * false if it does.  In this example, however, our text-based Service
     * classes should never need to create windows, so we will actually
     * throw an exception to prevent any windows from being opened.
     **/
    public boolean checkTopLevelWindow(Object window) { 
      trusted();
      return true; 
    }
  }

  /**
   * In order to impose tight security restrictions on untrusted classes but
   * not on trusted system classes, we have to be able to distinguish between
   * those types of classes.  This is done by keeping track of how the classes
   * are loaded into the system.  By definition, any class that the interpreter
   * loads directly from the CLASSPATH is trusted.  This means that we can't
   * load untrusted code in that way--we can't load it with Class.forName().
   * Instead, we create a ClassLoader subclass to load the untrusted code.
   * This one loads classes from a specified directory (which should not
   * be part of the CLASSPATH).
   **/
  public static class LocalClassLoader extends ClassLoader {
    /** This is the directory from which the classes will be loaded */
    String directory;

    /** The constructor.  Just initialize the directory */
    public LocalClassLoader(String dir) { directory = dir; }

    /** A convenience method that calls the 2-argument form of this method */
    public Class loadClass(String name) throws ClassNotFoundException { 
      return loadClass(name, true); 
    }

    /**
     * This is one abstract method of ClassLoader that all subclasses must
     * define.  Its job is to load an array of bytes from somewhere and to
     * pass them to defineClass().  If the resolve argument is true, it must
     * also call resolveClass(), which will do things like verify the presence
     * of the superclass.  Because of this second step, this method may be
     * called to load superclasses that are system classes, and it must take 
     * this into account.
     **/
    public Class loadClass(String classname, boolean resolve) 
      throws ClassNotFoundException {
      try {
        // Our ClassLoader superclass has a built-in cache of classes it has
        // already loaded.  So, first check the cache.
        Class c = findLoadedClass(classname);

        // After this method loads a class, it will be called again to
        // load the superclasses.  Since these may be system classes, we've
        // got to be able to load those too.  So try to load the class as
        // a system class (i.e. from the CLASSPATH) and ignore any errors
        if (c == null) {
          try { c = findSystemClass(classname); }
          catch (Exception e) {}
        }

        // If the class wasn't found by either of the above attempts, then
        // try to load it from a file in (or beneath) the directory
        // specified when this ClassLoader object was created.  Form the
        // filename by replacing all dots in the class name with
        // (platform-independent) file separators and by adding the
        // ".class" extension.
        if (c == null) {
          // Figure out the filename
          String filename = classname.replace('.',File.separatorChar)+".class";

          // Create a File object.  Interpret the filename relative to the
          // directory specified for this ClassLoader.
          File f = new File(directory, filename);

          // Get the length of the class file, allocate an array of bytes for
          // it, and read it in all at once.
          int length = (int) f.length();
          byte[] classbytes = new byte[length];
          DataInputStream in = new DataInputStream(new FileInputStream(f));
          in.readFully(classbytes);
          in.close();

          // Now call an inherited method to convert those bytes into a Class
          c = defineClass(classname, classbytes, 0, length);
        }

        // If the resolve argument is true, call the inherited resolveClass
        // method.
        if (resolve) resolveClass(c);

        // And we're done.  Return the Class object we've loaded.
        return c;
      }
      // If anything goes wrong, throw a ClassNotFoundException error
      catch (Exception e) { throw new ClassNotFoundException(e.toString()); }
    }
  }

  /**
   * This is a demonstration service.  It attempts to do things that the
   * ServiceSecurityManager doesn't allow, and sends the results of
   * its attempts to the client.
   **/
  public static class ProhibitedService implements Service {
    public void serve(InputStream i, OutputStream o) throws IOException {
      PrintWriter out = new PrintWriter(new OutputStreamWriter(o));
      out.print("Attempting to read a file...");
      try { new FileInputStream("testfile"); }
      catch (Exception e) { out.println("Failed: " + e); }
      out.print("Attempting to write a file...");
      try { new FileOutputStream("testfile"); }
      catch (Exception e) { out.println("Failed: " + e); }
      out.print("Attempting to read system property...");
      try { System.getProperty("java.version"); }
      catch (Exception e) { out.println("Failed: " + e); }
      out.print("Attempting to load a library...");
      try { Runtime.getRuntime().load("testlib"); }
      catch (Exception e) { out.println("Failed: " + e); }

      out.close();
      i.close();
    }
  }
}
