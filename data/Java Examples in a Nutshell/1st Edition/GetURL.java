// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.io.*;
import java.net.*;

/**
 * This simple program uses the URL class and its openStream() method to
 * download the contents of a URL and copy them to a file or to the console.
 **/
public class GetURL {
  public static void main(String[] args) {
    InputStream in = null;   
    OutputStream out = null;
    try {
      // Check the arguments
      if ((args.length != 1) && (args.length != 2)) 
        throw new IllegalArgumentException("Wrong number of arguments");

      // Set up the streams
      URL url = new URL(args[0]);   // Create the URL
      in = url.openStream();        // Open a stream to it
      if (args.length == 2)         // Get an appropriate output stream
        out = new FileOutputStream(args[1]);
      else out = System.out;

      // Now copy bytes from the URL to the output stream
      byte[] buffer = new byte[4096];
      int bytes_read;
      while((bytes_read = in.read(buffer)) != -1)
        out.write(buffer, 0, bytes_read);
    }
    // On exceptions, print error message and usage message.
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java GetURL <URL> [<filename>]");
    }
    finally {  // Always close the streams, no matter what.
      try { in.close();  out.close(); } catch (Exception e) {}
    }
  }
}
