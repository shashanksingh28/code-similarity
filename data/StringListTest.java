// Name: John Watts
// PIN: F33
// ASUID: 1207237937
// Class: CSE 360
// Assignment: 2

/**
 * StringListTest
 * 
 * Class to test the methods of StringList and StringListExt.
 */

package cse360assign2;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class StringListTest {
	
	//method that tests whether search will return an empty string if the list is empty.
	@Test
	public void testEmpty(){
		System.out.println("Testing empty list");
		StringList list = new StringList();
		assertEquals(-1, list.search("0"));
		assertTrue("".equals(list.toString()));
		System.out.println("testEmpty Passed");
	}
	
	//method that tests the insert method to make sure it works
	@Test
	public void testInsert(){
		System.out.println("Testing insert method");
		StringList list = new StringList();
		list.insert("woohoo");
		System.out.println("List: " + list.toString());
		assertEquals(0, list.search("woohoo"));
		assertEquals(-1, list.search("0"));
		System.out.println("testInsert Passed");
	}
	
	//method that makes sure duplicates aren't added to the list
	@Test
	public void testDuplicate(){
		System.out.println("Testing insert method for duplicates");
		StringList list = new StringList();
		list.insert("a");
		list.insert("d");
		list.insert("b");
		list.insert("c");
		list.insert("e");
		list.insert("e");
		list.insert("e");
		list.insert("e");
		assertTrue("abcde".equals(list.toString().replaceAll("\\s+","")) || ("\""+"abcde"+"\"").equals(list.toString().replaceAll("\\s+","")));
		System.out.println("testDuplicate Passed");
	}
	
	//method that tests the get method to make sure it returns the correct string
	@Test
	public void testGet()
	{
		System.out.println("Testing get method");
		StringListExt list = new StringListExt();
		assertEquals("", list.get(0));
		list.insert("yay");
		assertEquals("yay", list.get(0));
		System.out.println("testGet Passed");
	}
	
	//method that tests the add method to make sure it adds the strings at the end of the list.
	@Test
	public void testAdd()
	{
		System.out.println("Testing add method");
		StringListExt list = new StringListExt();
		list.add("orange");
		assertEquals("orange", list.get(0));
		list.add("grape");
		assertEquals("grape", list.get(1));
		System.out.println("testAdd Passed");
	}
	
	//method that tests the length method to make sure it returns the correct length
	@Test
	public void testLength()
	{
		System.out.println("Testing length method");
		StringListExt list = new StringListExt();
		assertEquals(0, list.length());
		list.add("apple");
		list.add("apple");
		list.add("apple");
		list.add("apple");
		list.add("apple");
		list.add("apple");
		assertEquals(6, list.length());
		System.out.println("testLength Passed");
	}
	
	//method that tests the getLast method to make sure it returns the last string. 
	@Test
	public void testGetLast()
	{
		System.out.println("Testing getLast method");
		StringListExt list = new StringListExt();
		assertEquals("", list.getLast());
		list.add("x");
		list.add("x");
		list.add("x");
		list.add("x");
		list.add("x");
		list.add("x");
		list.add("y");
		assertEquals("y",list.getLast());
		list.add("z");
		list.add("a");
		assertEquals("a",list.getLast());
		System.out.println("getLast Passed");
	}
	
	//method that tests the remove method to make sure it removes the correct string. 
	@Test
	public void testRemove()
	{
		System.out.println("Testing remove method");
		StringListExt list = new StringListExt();
		list.add("apple");
		list.add("orange");
		list.add("grape");
		list.add("banana");
		list.remove("orange");
		assertEquals("grape",list.get(1));
		System.out.println("remove Passed");
	}
}
