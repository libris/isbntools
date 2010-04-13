/*
 * IsbnTest.java
 *
 * Created on December 6, 2006, 10:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package se.kb.libris.utils.isbn;

/**
 *
 * @author marma
 */
public class IsbnTest {
    /** Creates a new instance of IsbnTest */
    public static void main(String args[]) throws Exception {
       System.out.println(IsbnParser.parse("9781412950763").toString(true));
    }
}
