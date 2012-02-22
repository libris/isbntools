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
	if (args.length == 1) {
		System.out.println(IsbnParser.parse(args[0]).toString(false));
		System.out.println(IsbnParser.parse(args[0]).toString(true));
		System.out.println(IsbnParser.parse(args[0]).convert(Isbn.ISBN10).toString(false));
		System.out.println(IsbnParser.parse(args[0]).convert(Isbn.ISBN10).toString(true));
		System.out.println(IsbnParser.parse(args[0]).convert(Isbn.ISBN13).toString(false));
		System.out.println(IsbnParser.parse(args[0]).convert(Isbn.ISBN13).toString(true));
	} else {
		System.out.println("usage: java se.kb.libris.utils.isbn.IsbnTest <isbn>");
		System.exit(1);
	}
    }
}
