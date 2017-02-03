
public class StringComparator {

    public static void main(String[] args) {
        String str1 = "This is a test string";
        String str2 = new String(str1);
        String str3 = "This is a test string";
        System.out.println("str1.equals(str2) returns " + str1.equals(str2));
        System.out.println("str1==str2 returns " + (str1 == str2));
        System.out.println("str1.equals(str3) returns " + str1.equals(str3));
        System.out.println("str1==str3 returns " + (str1 == str3));
    }
}