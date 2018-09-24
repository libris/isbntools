/*
 * IsbnHyphenator.java
 *
 * Created on December 5, 2006, 3:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package se.kb.libris.utils.isbn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author marma
 */
public class IsbnParser {
    static HashMap countries = null;
    
    public static void init() throws IOException {
        init(IsbnParser.class.getResourceAsStream("/isbn-ranges.txt"));
    }
    
    public static void init(File file) throws IOException {
        init(new FileInputStream(file));
    }
    
    public static void init(InputStream stream) throws IOException {
        countries = new HashMap();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        int n=0;
        
        String line = "";
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            n++;
            
            try {
                if (line.trim().equals("") || line.trim().startsWith("#")) continue;
                
                //if (!java.util.regex.Pattern.matches("\\d*.\\d*-\\d*", line)) {
                //    continue;
                //}
                
                String country = line.substring(0, line.indexOf(' ')), start = line.substring(line.indexOf(' ')+1, line.indexOf('-')), stop = line.substring(line.indexOf('-')+1);

                Vector v = (Vector)countries.get(country);

                if (v == null) {
                    v = new Vector();
                    countries.put(country, v);
                }

                v.add(start);
                v.add(stop);
            } catch (Exception e) {
                System.err.println("warning: error on line " + n + " ('" + line + "', " + e.getMessage() + ")");
            }
        }
    }
    
    public static Isbn parse (String isbn) throws IsbnException {
        String country = null;
	boolean isbn10 = false;
        
        try {
            if (countries == null) init();

            isbn = scrub(isbn, "-");
            
            if ( isbn.length() == 10 ) {
			isbn = "978" + isbn;
			isbn10 = true;
            }

            // 1) find country
            for (int i=4;i<isbn.length();i++) {
                if (countries.get(isbn.substring(0,i)) != null) {
                    country = isbn.substring(0,i);
                }
            }

            if (country == null) {
                return null;
            }
            
            // 2) find publishers number
            String pprefix = "", num = "", tmp = isbn.substring(country.length(), isbn.length()-1);

            Vector v = (Vector)countries.get(country);
            for (int i=0;i<v.size();i += 2) {
                int length = ((String)v.get(i)).length(), start = Integer.parseInt((String)v.get(i)), stop = Integer.parseInt((String)v.get(i+1)), n = Integer.parseInt(tmp.substring(0,length));

                if (n >= start && n <= stop) {
                    pprefix = tmp.substring(0, length);
                    break;
                }
            }

            num = tmp.substring(pprefix.length(), tmp.length());

            if (isbn10) country = country.substring(3);
            
            return new Isbn(country, pprefix, num);
        } catch (Exception e) {
            throw new IsbnException(e);
        }    
    }
    
    public static String scrub(String data, String scrub) {
        StringBuffer sb = new StringBuffer();
        
        for (int i=0;i<data.length();i++) {
            char c = data.charAt(i);
            
            if (scrub.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}
