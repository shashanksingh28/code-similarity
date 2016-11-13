/**
   This is a simplified implementation of an array list.
*/
public class ArrayList
{
   private Object[] elements;
   private int currentSize;

   /**
      Constructs an empty array list.
   */
   public ArrayList() 
   {
      final int INITIAL_SIZE = 10;
      elements = new Object[INITIAL_SIZE];
      currentSize = 0;
   }

   /**
      Gets the size of this array list.
      @return the size
   */
   public int size() { return currentSize; }

   /**
      Throws an IndexOutOfBoundsException if the checked index is out of bounds
      @param n the index to check
   */
   private void checkBounds(int n)
   {
      if (n < 0 || n >= currentSize) 
      {
         throw new IndexOutOfBoundsException();
      }
   }

   /**
      Gets the element at a given position.
      @param pos the position
      @return the element at pos
   */
   public Object get(int pos)
   {
      checkBounds(pos);
      return elements[pos];
   }

   /**
      Sets the element at a given position.
      @param pos the position
      @param element the new value
   */
   public void set(int pos, Object element)
   {
      checkBounds(pos);
      elements[pos] = element;
   }

   /**
      Removes the element at a given position.
      @param pos the position
      @return the removed element
   */
   public Object remove(int pos)
   {      
      checkBounds(pos);

      Object removed = elements[pos];

      for (int i = pos + 1; i < currentSize; i++)
      {
         elements[i - 1] = elements[i];
      }

      currentSize--; 

      return removed;
   }

   /**
      Adds an element after a given position.
      @param pos the position
      @param newElement the element to add
   */
   public boolean add(int pos, Object newElement)
   {
      growIfNecessary();
      currentSize++;

      checkBounds(pos);

      for (int i = currentSize - 1; i > pos; i--)
      {
         elements[i] = elements[i - 1];
      }

      elements[pos] = newElement;
      return true;
   }

   /**
      Adds an element after the end of the array list
      @param newElement the element to add
   */
   public boolean addLast(Object newElement)
   {
      growIfNecessary();
      currentSize++;
      
      elements[currentSize - 1] = newElement;
      return true;
   }

   /**
      Grows the elements array if the current size equals the capacity.
   */
   private void growIfNecessary()
   {
      if (currentSize == elements.length)
      {
         Object[] newElements = new Object[2 * elements.length];
         for (int i = 0; i < elements.length; i++) 
         { 
            newElements[i] = elements[i]; 
         }      
         elements = newElements;
      }
   }
}
