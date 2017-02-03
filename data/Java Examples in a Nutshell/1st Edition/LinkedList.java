// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

/**
 * This class implements a linked list that can contain any type of object
 * that implements the nested Linkable interface.  Note that the methods are
 * all synchronized, so that it can safely be used by multiple threads at
 * the same time.
 **/
public class LinkedList {
  /** 
   * This interface defines the methods required by any object that can be
   * linked into a linked list.
   **/
  public interface Linkable {
    public Linkable getNext();          // Returns the next element in the list
    public void setNext(Linkable node); // Sets the next element in the list
  }

  // This class has a default constructor: public LinkedList() {}

  /** This is the only field of the class.  It holds the head of the list */
  Linkable head;

  /** Return the first node in the list */
  public synchronized Linkable getHead() { return head; }

  /** Insert a node at the beginning of the list */
  public synchronized void insertAtHead(Linkable node) {
    node.setNext(head);
    head = node;
  }

  /** Insert a node at the end of the list */
  public synchronized void insertAtTail(Linkable node) {
    if (head == null) head = node;
    else {
      Linkable p, q;
      for(p = head; (q = p.getNext()) != null; p = q);
      p.setNext(node);
    }
  }

  /** Remove and return the node at the head of the list */
  public synchronized Linkable removeFromHead() {
    Linkable node = head;
    if (node != null) {
      head = node.getNext();
      node.setNext(null);
    }
    return node;
  }

  /** Remove and return the node at the end of the list */
  public synchronized Linkable removeFromTail() {
    if (head == null) return null;
    Linkable p = head, q = null, next = head.getNext();
    if (next == null) {
      head = null;
      return p;
    }
    while((next = p.getNext()) != null) { 
      q = p; 
      p = next;
    }
    q.setNext(null);
    return p;
  }

  /** 
   * Remove a node matching the specified node from the list.  
   * Use equals() instead of == to test for a matched node.
   **/
  public synchronized void remove(Linkable node) {
    if (head == null) return;
    if (node.equals(head)) { 
      head = head.getNext(); 
      return;
    }
    Linkable p = head, q = null;
    while((q = p.getNext()) != null) {
      if (node.equals(q)) {
        p.setNext(q.getNext());
        return;
      }
      p = q;
    }
  }

  /**
   * This nested class defines a main() method that tests the LinkedList class
   **/
  public static class Test {
    /**
     * This is a test class that implements the Linkable interface
     **/
    static class LinkableInteger implements Linkable {
      int i;          // The data contained in the node
      Linkable next;  // A reference to the next node in the list
      public LinkableInteger(int i) { this.i = i; }       // Constructor method
      public Linkable getNext() { return next; }          // Part of Linkable
      public void setNext(Linkable node) { next = node; } // Part of Linkable
      public String toString() { return i + ""; }         // For easy printing
      public boolean equals(Object o) {                   // For comparison
        if (this == o) return true;
        if (!(o instanceof LinkableInteger)) return false;
        if (((LinkableInteger)o).i == this.i) return true;
        return false;
      }
    }

    /**
     * The test program.  Insert some nodes, remove some nodes, then
     * print out all elements in the list.  It should print out the
     * numbers 4, 6, 3, 1, and 5
     **/
    public static void main(String[] args) {
      LinkedList ll = new LinkedList();         // Create a list
      ll.insertAtHead(new LinkableInteger(1));  // Insert some stuff
      ll.insertAtHead(new LinkableInteger(2));
      ll.insertAtHead(new LinkableInteger(3));
      ll.insertAtHead(new LinkableInteger(4));
      ll.insertAtTail(new LinkableInteger(5));  // Insert some more stuff
      ll.insertAtTail(new LinkableInteger(6));
      System.out.println(ll.removeFromHead());  // Remove and print a node
      System.out.println(ll.removeFromTail());  // Remove and print another
      ll.remove(new LinkableInteger(2));        // Remove another one

      // Now print out the contents of the list.
      for(Linkable l = ll.getHead(); l != null; l = l.getNext())
        System.out.println(l);
    }
  }
}
