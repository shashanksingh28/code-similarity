
public class UnitsConverter {

    private static double numberToConvert = 0;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(
                    "Usage: java UnitsConverter <weight in pounds>");
            System.exit(0);
        }
        numberToConvert = Double.parseDouble(args[0]);
        System.out.println("lbs " + args[0] + " equals:\n");
        for (Converter conv : Converter.values()) {
            System.out.printf("%s: %f%n",
                    conv, conv.performConversion(numberToConvert));
        }
    }
}

enum Converter {

    KG("KG") {

        double performConversion(double f) {
            return f *= 0.45359237;
        }
    },
    CARAT("carat") {

        double performConversion(double f) {
            return f *= 2267.96185;
        }
    },
    GMS("gms") {

        double performConversion(double f) {
            return f *= 453.59237;
        }
    },
    OUNCE("ounce") {

        double performConversion(double f) {
            return f *= 16;
        }
    },
    STONE("stone") {

        double performConversion(double f) {
            return f *= 0.071428571429;
        }
    };
    private final String symbol;

    Converter(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    abstract double performConversion(double f);
}