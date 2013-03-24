/*
 * Isbn.java
 */

package se.kb.libris.utils.isbn;

/**
 *
 * @author marma
 */
public class Isbn {
    public static final int ISBN10  = 0;
    public static final int ISBN13  = 1;
    public static final int UNKNOWN = 2;
    public static final int weights[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    public static final int weights13[] = { 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3 };

    String isbn = "", orgIsbn = "";
    String country="", publisher="", number="";
    int type = UNKNOWN;

    public Isbn(String country, String publisher, String number) {
        this.country = country;
        this.publisher = publisher;
        this.number = number;

        String s = toString();
        if (s.length() == 10) type = ISBN10;
        else if (s.length() == 13) type = ISBN13;
        else type = UNKNOWN;
    }
    
    public String getPublisher() {
        return publisher;
    }

    public String getCountry() {
        return country;
    }

    public String getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }
    
    public char checksum() {
        String isbn = country + publisher + number;
        int n = 0, weight = 1;
        
        // calculate sum
        if (type == ISBN10) {
            for (int i=0;i<isbn.length();i++)
                n += Character.getNumericValue(isbn.charAt(i))*weights[i];
            
            n %= 11;
        } else if (type == ISBN13) {
            for (int i=0;i<isbn.length();i++)
                n += Character.getNumericValue(isbn.charAt(i))*weights13[i];
            
            n = (10 - (n % 10)) % 10;
        }
                
        return (n==10)? 'X' : (char)(n + '0');
    }
    
    public Isbn convert(int to) throws ConvertException {
        if (to == getType()) return this;
        
        if (to == ISBN10 && getType() == ISBN13) {
            if (getCountry().startsWith("978")) {
                return new Isbn(getCountry().substring(3), getPublisher(), getNumber());
            } else {
                throw new ConvertException("could not convert from ISBN13 to ISBN10 for country " + getCountry());
            }
        }
        
        if (to == ISBN13 && getType() == ISBN10) {
            return new Isbn("978" + country, publisher, number);
        }

        throw new ConvertException("could not convert from " + getType() + " to " + to);
    }
    
    public String toString() {
        return country + publisher + number + checksum();
    }
    
    public String toString(boolean hyphens) {
        if (hyphens) {
            if (type == ISBN13 && (country.startsWith("978") || country.startsWith("979"))) {
                return country.substring(0,3) + '-' + country.substring(3) + '-' + publisher + '-' + number + '-' + checksum();
            } else {
                return country + '-' + publisher + '-' + number + '-' + checksum();
            }
        } else {
            return toString();
        }
    }
}
