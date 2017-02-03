// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.io.*;
import java.security.*;
import java.rmi.*;          // Used for the test programs only
import java.rmi.server.*;

/**
 * A SignedObject is exactly that--an object that bears a digital signature.
 * Subclass this class to add a digital signature to any class you want.
 * Note, however, that the signature is computed using serialization, so only
 * the serializable, non-transient fields of a subclass are included in
 * the signature computation.
 **/
public class SignedObject implements Serializable {
  protected String signername;               // Who is doing the signing
  protected String algorithm;                // What algorithm to use.
  private byte[] signature;                  // The bytes of the signature
  transient private boolean signing = false; // A flag used below.

  /**
   * This method computes a digital signature for the current state of the
   * object, excluding the signature-related state of this class.
   * That is, the signature is based only on the state of the subclasses.
   * The arguments specify who is signing the object, and what digital 
   * signature algorithm to use.
   * 
   * Note that no other threads should be modifying the object while
   * this computation is being performed.  If a subclass will be used in a 
   * multi-threaded environment, this means that methods of the subclass
   * that modify its state should be synchronized like this one is.
   **/
  public synchronized void sign(String signername, String algorithm) 
       throws IOException, InvalidKeyException, 
         SignatureException, NoSuchAlgorithmException 
  {
    // Save the arguments for use by verify()
    this.signername = signername;
    this.algorithm = algorithm;

    // Get a Signature object to compute the signature with
    Signature s = Signature.getInstance(algorithm);

    // Get a Signer object representing the signer
    Signer signer = 
      (Signer)IdentityScope.getSystemScope().getIdentity(signername);

    // Initialize the Signature object using the PrivateKey of the Signer
    s.initSign(signer.getPrivateKey());

    // Create an ObjectOutputStream that writes its output to a 
    // ByteArrayStream.  This is how we capture the state of the object
    // so that it can be signed.
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bout);

    // Now serialize the object, capturing its state.  We have to set a flag
    // before we do this, so that the signer name, algorithm, and signature
    // itself are not included in this serialized state.  See writeObject()
    // below to see how this works.
    signing = true;
    out.writeObject(this);
    signing = false;

    // Now tell the Signature object about the bytes of serialized state
    // that were stored by the ByteArrayOutputStream
    s.update(bout.toByteArray());

    // And finally, compute the signature
    this.signature = s.sign();
  }

  /** A simpler version of sign(), that defaults to using the DSA algorithm */
  public synchronized void sign(String signername) 
       throws IOException, InvalidKeyException, 
         SignatureException, NoSuchAlgorithmException {
    sign(signername, "DSA");
  }

  /**
   * This method verifies the signature of any SignedObject subclass.
   * It works much like the sign() method, and is also synchronized.
   **/
  public synchronized boolean verify() 
       throws IOException, InvalidKeyException,
         SignatureException, NoSuchAlgorithmException
  {
    // Make sure the object is actually signed.
    if (signature == null) 
      throw new SignatureException("Object is not signed");

    // Get the signature, signer and public key, and initialize, like above,
    // except that this time use a PublicKey instead of a PrivateKey
    Signature s = Signature.getInstance(algorithm);
    Identity signer = 
      (Identity)IdentityScope.getSystemScope().getIdentity(signername);
    s.initVerify(signer.getPublicKey());

    // Create streams and capture the current state of the object 
    // (excluding the signature bytes themselves) in a byte array
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bout);
    signing = true;
    out.writeObject(this);
    signing = false;

    // Pass state of the object to the Signature, and verify the stored
    // signature bytes against that state.  Return the result of verification.
    s.update(bout.toByteArray());
    return s.verify(this.signature);
  }

  /**
   * When the contents of the object change, the signature becomes invalid.
   * When this happens, the signature should be erased, because validation
   * is guaranteed to fail.
   **/
  public void removeSignature() { 
    signature = null; signername = null; algorithm = null; 
  }

  /**
   * This method is invoked to allow custom serialization.
   * We only write out our signature-related state when we are not computing
   * or verifying a signature.  When we are computing or verifying, only our
   * subclass state gets written out.  If we don't do this, verification will
   * fail because the signature[] array will obviously be different on
   * verification than it is when the signature is generated.
   **/
  private void writeObject(ObjectOutputStream out) throws IOException {
    if (!signing) out.defaultWriteObject();
  }
}

/**
 * This class is a simple SignedObject subclass.
 * This and the following interface and classes are used to test SignedObject.
 **/
class SignedString extends SignedObject {
  public String s;
  public SignedString(String s) { this.s = s; }
}

/**
 * This interface extends Remote.  It is part of an RMI example using our
 * SignedString class above.
 **/
interface RemoteSignedString extends Remote {
  public SignedString getString() throws RemoteException;
}

/**
 * This is a simple RMI server class and a program to start and register the
 * server.  The server creates a SignedString object, signs it, and then 
 * exports it to clients through the RemoteSignedString getString() method.
 **/
class SignedStringServer extends UnicastRemoteObject 
                         implements RemoteSignedString 
{
  SignedString s;  // The state of the server object

  /** The constructor.  Initialize the SignedString */
  public SignedStringServer(SignedString s) throws RemoteException {this.s=s;}

  /** This is the remote method exported by the server */
  public SignedString getString() throws RemoteException { return s; }
  
  /** The main program that creates and registers the server object */
  public static void main(String[] args) 
       throws NoSuchAlgorithmException, InvalidKeyException, 
         IOException, SignatureException
  {
    SignedString ss = new SignedString(args[0]);         // create object
    ss.sign(args[1]);                                    // sign it
    SignedStringServer sss = new SignedStringServer(ss); // start server
    Naming.rebind("SignedStringServer", sss);            // register server
    System.out.println("Ready for clients");             // up and running!
  }
}

/**
 * This class is a client that connects to a RemoteSignedString server and
 * calls the getString() method to obtain a SignedString.  It then verifies
 * the signature of the object that it has just downloaded over a network
 * from an entirely different Java VM.  The client takes an optional hostname
 * argument on the command line.
 **/
class SignedStringClient {
  public static void main(String[] args)
       throws NoSuchAlgorithmException, InvalidKeyException, 
         IOException, SignatureException, NotBoundException
  {
    // Look up the server
    RemoteSignedString rss = 
      (RemoteSignedString) Naming.lookup("rmi://" + 
                                         ((args.length>0)?args[0]:"") +
                                         "/SignedStringServer"); 

    // Invoke a remote method of the server to get a SignedString
    SignedString ss = rss.getString();

    // Now verify the signature on that SignedString.
    if (ss.verify()) System.out.println("Verified SignedString: " + ss.s);
    else System.out.println("SignedString failed verification");
  }
}
