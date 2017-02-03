
public class CharWrapper {

    public static void main(String args[]) throws Exception {
        int digitCount = 0, letterCount = 0, lcCount = 0, ucCount = 0,
                wsCount = 0;
        for (int i = 0; i < 0xFF; i++) {
            if (Character.isDigit(i)) {
                digitCount++;
            }
            if (Character.isLetter(i)) {
                letterCount++;
            }
            if (Character.isLowerCase(i)) {
                lcCount++;
            }
            if (Character.isUpperCase(i)) {
                ucCount++;
            }
            if (Character.isWhitespace(i)) {
                wsCount++;
            }
        }
        System.out.println("No of digits: " + digitCount);
        System.out.println("No of letters: " + letterCount);
        System.out.println("No of lower case letters: " + lcCount);
        System.out.println("No of upper case letters: " + ucCount);
        System.out.println("No of white space characters: " + wsCount);
    }
}