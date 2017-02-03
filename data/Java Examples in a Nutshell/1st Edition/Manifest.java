// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.security.*;
import java.io.*;
import java.util.*;

public class Manifest {
  /**
   * This program creates a manifest file for the specified files, or verifies
   * an existing manifest file.  By default the manifest file is named
   * MANIFEST, but the -m option can be used to override this.  The -v
   * option specifies that the manifest should be verified.  Verification is
   * also the default option if no files are specified.
   **/
  public static void main(String[] args) {
    try {
      // Set the default values of the command-line arguments
      boolean verify = false;              // Verify manifest or create one?
      String manifestfile = "MANIFEST";    // Manifest file name
      String digestAlgorithm = "MD5";      // Algorithm for message digests
      String signername = null;            // Signer.  No signature by default
      String signatureAlgorithm = "DSA";   // Algorithm for digital signatures
      Vector filelist = new Vector();      // The list of files to digest
      
      // Parse the command-line arguments, overriding the defaults above
      for(int i = 0; i < args.length; i++) {
        if (args[i].equals("-v")) verify = true;
        else if (args[i].equals("-m")) manifestfile = args[++i];
        else if (args[i].equals("-da") && !verify) digestAlgorithm = args[++i];
        else if (args[i].equals("-s") && !verify) signername = args[++i];
        else if (args[i].equals("-sa") && !verify) 
          signatureAlgorithm = args[++i];
        else if (!verify) filelist.addElement(args[i]);
        else throw new IllegalArgumentException(args[i]);
      }

      // If -v was specified or no file were given, verify a manifest
      // Otherwise, create a new manifest for the specified files
      if (verify || (filelist.size() == 0)) verify(manifestfile);
      else create(manifestfile, digestAlgorithm, 
                  signername, signatureAlgorithm, filelist);
    }
    // If anything goes wrong, display the exception, and print a usage message
    catch (Exception e) {
      System.err.println("\n" + e);
      System.err.println("Usage: java Manifest [-v] [-m <manifestfile>]\n" + 
                         "   or: java Manifest " +
                         "[-m <manifestfile>] [-da <digest algorithm>]\n" + 
                         "\t\t[-s <signer>] [-sa <signature algorithm>] " +
                         "files...");
    }
  }

  /**
   * This method creates a manifest file with the specified name, for
   * the specified vector of files, using the named message digest
   * algorithm.  If signername is non-null, it adds a digital signature
   * to the manifest, using the named signature algorithm.  This method can
   * throw a bunch of exceptions.
   **/
  public static void create(String manifestfile, String digestAlgorithm, 
                            String signername, String signatureAlgorithm,
                            Vector filelist)
       throws NoSuchAlgorithmException, InvalidKeyException, 
              SignatureException, IOException 
  {
    // For computing a signature, we have to process the files in a fixed,
    // repeatable order, so copy the filenames into an array and sort it.
    // Use the Sorter class from Chapter 2.
    String[] files = new String[filelist.size()];
    filelist.copyInto(files);
    Sorter.sortAscii(files);
    
    Properties manifest = new Properties(), metadata = new Properties();
    MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
    Signature signature = null;
    byte[] digest;
    
    // If a signer name has been specified, then prepare to sign the manifest
    if (signername != null) {
      // Look up the signer object
      Signer signer = 
        (Signer)IdentityScope.getSystemScope().getIdentity(signername);
      // Get a Signature object
      signature = Signature.getInstance(signatureAlgorithm);
      // And prepare to create a signature for the specified signer
      signature.initSign(signer.getPrivateKey());
    }
    
    // Now, loop through the files, in a well-known alphabetical order
    System.out.print("Computing message digests");
    for(int i = 0; i < files.length; i++) {
      // Compute the digest for each one, and skip files that don't exist.
      try { digest = getFileDigest(files[i], md); } 
      catch (IOException e) {
        System.err.println("\nSkipping " + files[i] + ": " + e);
        continue;
      }
      // If we're computing a signature, use the bytes of the filename and
      // of the digest as part of the data to sign.
      if (signature != null) {
        signature.update(files[i].getBytes());
        signature.update(digest);
      }
      // Store the filename and the encoded digest bytes in the manifest
      manifest.put(files[i], hexEncode(digest));
      System.out.print('.');
      System.out.flush();
    }
    
    // If a signer was specified, compute digital signature for the manifest
    byte[] signaturebytes = null;
    if (signature != null) {
      System.out.print("done\nComputing digital signature...");
      System.out.flush();
      
      // Compute the digital signature by encrypting a message digest of all
      // the bytes passed to the update() method using the private key of the
      // signer.  This is a time consuming operation.
      signaturebytes = signature.sign();
    }

    // Tell the user what comes next
    System.out.print("done\nWriting manifest...");
    System.out.flush();

    // Store some metadata about this manifest, including the name of the
    // message digest algorithm it uses
    metadata.put("__META.DIGESTALGORITHM", digestAlgorithm);
    // If we're signing the manifest, store some more metadata
    if (signername != null) {
      // Store the name of the signer
      metadata.put("__META.SIGNER", signername);
      // Store the name of the algorithm
      metadata.put("__META.SIGNATUREALGORITHM", signatureAlgorithm);
      // And generate the signature, encode it, and store it
      metadata.put("__META.SIGNATURE", hexEncode(signaturebytes));
    }

    // Now, save the manifest data and the metadata to the manifest file
    FileOutputStream f = new FileOutputStream(manifestfile);
    manifest.save(f, "Manifest message digests");
    metadata.save(f, "Manifest metadata");
    System.out.println("done");
  }

  /**
   * This method verifies the digital signature of the named manifest
   * file, if it has one, and if that verification succeeds, it verifies
   * the message digest of each file in filelist that is also named in the
   * manifest.  This method can throw a bunch of exceptions
   **/
  public static void verify(String manifestfile) 
       throws NoSuchAlgorithmException, SignatureException, 
              InvalidKeyException, IOException
  {
    Properties manifest = new Properties();
    manifest.load(new FileInputStream(manifestfile));
    String digestAlgorithm = manifest.getProperty("__META.DIGESTALGORITHM");
    String signername = manifest.getProperty("__META.SIGNER");
    String signatureAlgorithm = 
      manifest.getProperty("__META.SIGNATUREALGORITHM");
    String hexsignature = manifest.getProperty("__META.SIGNATURE");

    // Get a list of filenames in the manifest.  Use an Enumeration to
    // get them into a Vector, then allocate an array and copy them into that.
    Vector filelist = new Vector();
    Enumeration names = manifest.propertyNames();
    while(names.hasMoreElements()) {
      String s = (String)names.nextElement();
      if (!s.startsWith("__META")) filelist.addElement(s);
    }
    String[] files = new String[filelist.size()];
    filelist.copyInto(files);

    // If the manifest contained metadata about a digital signature, then
    // verify that signature first
    if (signername != null) {
      System.out.print("Verifying digital signature...");
      System.out.flush();

      // To verify the signature, we must process the files in exactly the
      // same order we did when we created the signature.  We guarantee
      // this order by sorting the filenames.
      Sorter.sortAscii(files);

      // Get the Signer identity, create a Signature object, and initialize
      // it for signature verification, using the signer's public key
      Identity signer = 
        (Identity)IdentityScope.getSystemScope().getIdentity(signername);
      Signature signature = Signature.getInstance(signatureAlgorithm);
      signature.initVerify(signer.getPublicKey());

      // Now loop through these files in their known sorted order
      // For each one, send the bytes of the filename and of the digest
      // to the signature object for use in computing the signature.
      // It is important that this be done in exactly the same order when
      // verifying the signature as it was done when creating the signature.
      for(int i = 0; i < files.length; i++) {
        signature.update(files[i].getBytes());
        signature.update(hexDecode(manifest.getProperty(files[i])));
      }

      // Now decode the signature read from the manifest file and pass it
      // to the verify() method of the signature object.  If the signature
      // is not verified, print an error message and exit.
      if (!signature.verify(hexDecode(hexsignature))) {
        System.out.println("\nManifest has an invalid digital signature");
        System.exit(0);
      }
      
      // Tell the user we're done with this lengthy computation
      System.out.println("verified.");
    }

    // Tell the user we're starting the next phase of verification
    System.out.print("Verifying file message digests");
    System.out.flush();

    // Get a MessageDigest object to compute digests
    MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
    // Loop through all files
    for(int i = 0; i < files.length; i++) {
      // Look up the encoded digest from the manifest file
      String hexdigest = manifest.getProperty(files[i]);
      // Compute the digest for the file.
      byte[] digest;
      try { digest = getFileDigest(files[i], md); } 
      catch (IOException e) {
        System.out.println("\nSkipping " + files[i] + ": " + e);
        continue;
      }

      // Encode the computed digest and compare it to the encoded digest
      // from the manifest.  If they are not equal, print an error message.
      if (!hexdigest.equals(hexEncode(digest)))
        System.out.println("\nFile '" + files[i] + "' failed verification.");

      // Send one dot of output for each file we process.  Since computing
      // message digests takes some time, this lets the user know that the
      // program is functioning and making progress
      System.out.print("."); 
      System.out.flush();
    }
    // And tell the user we're done with verification.
    System.out.println("done.");
  }

  /**
   * This convenience method is used by both create() and verify().  It
   * reads the contents of a named file and computes a message digest
   * for it, using the specified MessageDigest object.
   **/
  public static byte[] getFileDigest(String filename, MessageDigest md) 
       throws IOException {
    // Make sure there is nothing left behind in the MessageDigest
    md.reset();

    // Create a stream to read from the file and compute the digest
    DigestInputStream in = 
      new DigestInputStream(new FileInputStream(filename),md);

    // Read to the end of the file, discarding everything we read.
    // The DigestInputStream automatically passes all the bytes read to
    // the update() method of the MessageDigest
    while(in.read(buffer) != -1) /* do nothing */ ;

    // Finally, compute and return the digest value.
    return md.digest();
  }

  /** This static buffer is used by getFileDigest() above */
  public static byte[] buffer = new byte[4096];

  /** This array is used to convert from bytes to hexadecimal numbers */
  static final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7',
                                 '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  /**
   * A convenience method to convert an array of bytes to a String.  We do
   * this simply by converting each byte to two hexadecimal digits.  Something
   * like Base 64 encoding is more compact, but harder to encode.
   **/
  public static String hexEncode(byte[] bytes) {
    StringBuffer s = new StringBuffer(bytes.length * 2);
    for(int i = 0; i < bytes.length; i++) {
      byte b = bytes[i];
      s.append(digits[(b & 0xf0) >> 4]);
      s.append(digits[b & 0x0f]);
    }
    return s.toString();
  }

  /**
   * A convenience method to convert in the other direction, from a string
   * of hexadecimal digits to an array of bytes.
   **/
  public static byte[] hexDecode(String s) throws IllegalArgumentException {
    try {
      int len = s.length();
      byte[] r = new byte[len/2];
      for(int i = 0; i < r.length; i++) {
        int digit1 = s.charAt(i*2), digit2 = s.charAt(i*2 + 1);
        if ((digit1 >= '0') && (digit1 <= '9')) digit1 -= '0';
        else if ((digit1 >= 'a') && (digit1 <= 'f')) digit1 -= 'a' - 10;
        if ((digit2 >= '0') && (digit2 <= '9')) digit2 -= '0';
        else if ((digit2 >= 'a') && (digit2 <= 'f')) digit2 -= 'a' - 10;
        r[i] = (byte)((digit1 << 4) + digit2);
      }
      return r;
    }
    catch (Exception e) {
      throw new IllegalArgumentException("hexDecode(): invalid input");
    }
  }
}
