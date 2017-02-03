// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.io.*;

/**
 * This Pipes class serves to group a number of pipe classes and interfaces.
 * It has no fields or methods of its own; its only members are static
 * classes and interfaces.  
 **/
public class Pipes {
  /**
   * This class contains a test program for the pipe classes below.
   * It also demonstrates how you typically use these pipes classes.
   * It is basically another implementation of a Unix-like grep command.
   * Note that it frivolously passes the output of the grep filter through
   * two rot13 filters (which, combined, leave the output unchanged).
   * Then it converts non-ASCII characters to their \U Unicode encodings.
   *
   * With the pipe infrastructure defined below, it is easy to define
   * new filters and create pipes to perform many useful operations.
   * Other filter possibilities include sorting lines, removing
   * duplicate lines, and doing search-and-replace.
   **/
  public static class Test {
    /** This is the test program for our pipes infrastructure */
    public static void main(String[] args) throws IOException {
      // Check the command-line arguments
      if (args.length != 2) {
        System.out.println("Usage: java Pipe$Test <pattern> <filename>");
        System.exit(0);
      }
      
      // Create a Reader to read data from, and a Writer to send data to.
      Reader in = new BufferedReader(new FileReader(args[1]));
      Writer out = new BufferedWriter(new OutputStreamWriter(System.out));

      // Now build up the pipe, starting with the sink, and working 
      // backwards, through various filters, until we reach the source
      WriterPipeSink sink = new WriterPipeSink(out);
      PipeFilter filter4 = new UnicodeToASCIIFilter(sink);
      PipeFilter filter3 = new Rot13Filter(filter4);
      PipeFilter filter2 = new Rot13Filter(filter3);
      PipeFilter filter1 = new GrepFilter(filter2, args[0]);
      ReaderPipeSource source = new ReaderPipeSource(filter1, in);
      
      // Start the pipe -- start each of the threads in the pipe running.
      // This call returns quickly, since the each component of the pipe
      // is its own thread
      System.out.println("Starting pipe...");
      source.startPipe();
      
      // Wait for the pipe to complete
      try { source.joinPipe(); } catch (InterruptedException e) {}
      System.out.println("Done.");
    }
  }

  /**
   * A Pipe is a kind of thread that is connected to another (possibly null)
   * thread, known as its "sink".  If it has a sink, it creates a PipedWriter
   * stream through which it can write characters to that sink.  It connects
   * its PipedWriter stream to a corresponding PipedReader stream in the sink.
   * It asks the sink to create and return such a PipedReader stream by calling
   * the getReader() method of the sink.
   *
   * In once sense, a Pipe is just a linked list of threads, and the Pipe
   * class defines operations that operate on the whole chain of threads, 
   * rather than a single thread.
   **/
  public static abstract class Pipe extends Thread {
    protected Pipe sink = null;
    protected PipedWriter out = null;
    protected PipedReader in = null;

    /**
     * Create a Pipe and connect it to the specified Pipe 
     **/
    public Pipe(Pipe sink) throws IOException { 
      this.sink = sink; 
      out = new PipedWriter();
      out.connect(sink.getReader());
    }
    
    /**
     * This constructor is for creating terminal Pipe threads--i.e. those
     * sinks that are at the end of the pipe, and are not connected to any
     * other threads.
     **/
    public Pipe() { super(); }

    /**
     * This protected method requests a Pipe threads to create and return
     * a PipedReader thread so that another Pipe thread can connect to it.
     **/
    protected PipedReader getReader() {
      if (in == null) in = new PipedReader();
      return in;
    }

    /**
     * This and the following methods provide versions of basic Thread methods
     * that operate on the entire pipe of threads.  
     * This one calls start() on all threads in sink-to-source order.
     **/
    public void startPipe() { 
      if (sink != null) sink.startPipe(); 
      this.start(); 
    }
    
    /** Call resume() on all threads in the pipe, in sink-to-source order */
    public void resumePipe() { 
      if (sink != null)  sink.resumePipe(); 
      this.resume(); 
    }

    /** Call stop() on all threads in the pipe, in source-to-sink order */
    public void stopPipe() { 
      this.stop(); 
      if (sink != null) sink.stopPipe(); 
    }

    /** Call suspend() on all threads in the pipe, in source-to-sink order */
    public void suspendPipe() { 
      this.suspend(); 
      if (sink != null) sink.suspendPipe();
    }

    /** Wait for all threads in the pipe to terminate */
    public void joinPipe() throws InterruptedException { 
      if (sink != null) sink.joinPipe(); 
      this.join(); 
    }
  }
  
  /**
   * This class is a source of data for a pipe of threads.  It connects to
   * a sink, but cannot serve as a sink for any other Pipe.  That is, it must
   * always be at the beginning, or "source" of the pipe.  For this class,
   * the source of data is the specified Reader object (such as a FileReader).
   **/
  public static class ReaderPipeSource extends Pipe {
    protected Reader in;  // The Reader we take data from

    /** 
     * To create a ReaderPipeSource, specify the Reader that data comes from
     * and the Pipe sink that it should be sent to.
     **/
    public ReaderPipeSource(Pipe sink, Reader in) 
         throws IOException { 
      super(sink);
      this.in = in; 
    }

    /** 
     * This is the thread body.  When the pipe is started, this method copies 
     * characters from the Reader into the pipe
     **/
    public void run() {
      try { 
        char[] buffer = new char[1024];
        int chars_read;
        while((chars_read = in.read(buffer)) != -1) 
          out.write(buffer, 0, chars_read);
      } 
      catch (IOException e) {}
      // When done with the data, close the Reader and the pipe
      finally { try { in.close(); out.close(); } catch (IOException e) {} }
    }

    /**
     * This method overrides the getReader() method of Pipe.  Because this
     * is a source thread, this method should never be called.  To make sure
     * that it is never called, we throw an Error if it is.
     **/
    protected PipedReader getReader() {
      throw new Error("Can't connect to a ReaderPipeSource!");
    }
  }

  /**
   * This class is a sink for data from a pipe of threads.  It can be connected
   * to by other Pipe, but its constructor is not passed a Pipe sink for it
   * to connect to.  That is, it must always be at the end or "sink" of a 
   * pipe.  It writes the characters into a specified Writer (such as a 
   * FileWriter).
   **/
  public static class WriterPipeSink extends Pipe {
    protected Writer out;  // The stream to write data to

    /**
     * To create a WriterPipeSink, just specify what Writer characters 
     * from the pipe should be written to
     **/
    public WriterPipeSink(Writer out) throws IOException { 
      super();  // Create a terminal Pipe with no sink attached.
      this.out = out; 
    }

    /** 
     * This is the thread body for this sink.  When the pipe is started, it
     * copies characters from the pipe into the specified Writer.
     **/
    public void run() {
      try { 
        char[] buffer = new char[1024];
        int chars_read;
        while((chars_read = in.read(buffer)) != -1)
          out.write(buffer, 0, chars_read);
      } 
      catch (IOException e) {}
      // When done with the data, close the pipe and flush the Writer
      finally { try {in.close(); out.flush(); } catch (IOException e) {} }
    }
  }

  /**
   * This abstract class simplifies (somewhat) the task of writing a 
   * filter pipe--i.e. one that reads data from one Pipe thread, filters
   * it somehow, and writes the results to some other Pipe.
   **/
  public static abstract class PipeFilter extends Pipe {
    public PipeFilter(Pipe sink) throws IOException { super(sink); }
    
    public void run() {
      try { filter(in, out); } 
      catch (IOException e) {}
      finally { try { in.close(); out.close(); } catch (IOException e) {} }
    }

    /** The method that subclasses must implement to do the filtering */
    abstract public void filter(Reader in, Writer out) throws IOException;
  }

  /**
   * This is concrete implementation of the PipeFilter interface.
   * It uses the GrepReader we defined elsewhere to do the filtering.
   **/
  public static class GrepFilter extends PipeFilter {
    protected String pattern;  // The string to grep for.

    /** 
     * Create a GrepFilter, will search its input for the specified pattern
     * and send the results to the specified Pipe.
     **/
    public GrepFilter(Pipe sink, String pattern) 
         throws IOException { 
      super(sink);
      this.pattern = pattern; 
    }

    /** 
     * Do the filtering, using a GrepReader to filter lines read from
     * the Reader, and using a PrintWriter to send those lines back out
     * to the Writer.
     **/
    public void filter(Reader in, Writer out) throws IOException {
      GrepReader gr = new GrepReader(new BufferedReader(in), pattern);
      PrintWriter pw = new PrintWriter(out);
      String line;
      while((line = gr.readLine()) != null) pw.println(line);
    }
  }
  
  /**
   * This is another implementation of Filter.  It implements the
   * trivial rot13 cipher on the letters A-Z and a-z.  Rot-13 "rotates" 
   * ASCII letters 13 characters through the alphabet.
   **/
  public static class Rot13Filter extends PipeFilter {
    /** Constructor just calls superclass */
    public Rot13Filter(Pipe sink) throws IOException { super(sink); }

    /** Filter characters from in to out */
    public void filter(Reader in, Writer out) throws IOException {
      char[] buffer = new char[1024];
      int chars_read;
      
      while((chars_read = in.read(buffer)) != -1) { // read a batch of chars
        // Apply rot-13 to each character, one at a time
        for(int i = 0; i < chars_read; i++) {
          if ((buffer[i] >= 'a') && (buffer[i] <= 'z')) {
            buffer[i] = (char) ('a' + ((buffer[i]-'a') + 13) % 26);
          }
          if ((buffer[i] >= 'A') && (buffer[i] <= 'Z')) {
            buffer[i] = (char) ('A' + ((buffer[i]-'A') + 13) % 26);
          }
        }
        out.write(buffer, 0, chars_read);           // write the batch of chars
      }
    }
  }

  /** 
   * This class is a Filter that accepts arbitrary Unicode characters as input
   * and outputs non-ASCII characters with their \U Unicode encodings
   **/
  public static class UnicodeToASCIIFilter extends PipeFilter {
    /** Constructor just calls superclass */
    public UnicodeToASCIIFilter(Pipe sink) throws IOException { 
      super(sink); 
    }

    /**
     * Read characters from the reader, one at a time (using a BufferedReader
     * for efficiency).  Output printable ASCII characters unfiltered.  For
     * other characters, output the \U Unicode encoding.
     **/
    public void filter(Reader r, Writer w) throws IOException {
      BufferedReader in = new BufferedReader(r);
      PrintWriter out = new PrintWriter(new BufferedWriter(w));
      int c;
      while((c = in.read()) != -1) {
        // Just output ASCII characters
        if (((c >= ' ') && (c <= '~')) || (c=='\t') || (c=='\n') || (c=='\r'))
          out.write(c);
        // And encode the others
        else {
          String hex = Integer.toHexString(c);
          switch (hex.length()) { 
            case 1:  out.print("\\u000" + hex); break;
            case 2:  out.print("\\u00" + hex); break;
            case 3:  out.print("\\u0" + hex); break;
            default: out.print("\\u" + hex); break;
          }
        }
      }
      out.flush();  // flush the output buffer we create
    }
  }
}
