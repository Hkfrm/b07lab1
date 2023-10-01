import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {

        // Create polynomials
        double[] coefficients1 = {3, -2, 1}; // 3x^2 - 2x + 1
        int[] exponents1 = {2, 1, 0};
        Polynomial poly1 = new Polynomial(coefficients1, exponents1);

        double[] coefficients2 = {-1, 4}; // -x + 4
        int[] exponents2 = {1, 0};
        Polynomial poly2 = new Polynomial(coefficients2, exponents2);

        // Add polynomials
        Polynomial sum = poly1.add(poly2);
        System.out.println("Sum of polynomials: "); // Expected: 3x^2 - 3x + 5
        sum.printCoefficients();
        System.out.println();
        sum.printExponents();
        System.out.println();

        // Evaluate polynomial at x = 2
        double x = 2;
        double result = poly1.evaluate(x);
        System.out.println("poly1(2) = " + result); // Expected: 3

        // Check if polynomial has a root at x = 1
        if (poly1.hasRoot(1)) {
            System.out.println("1 is a root of poly1");
        } else {
            System.out.println("1 is not a root of poly1");
        }

        // Multiply polynomials
        Polynomial product = poly1.multiply(poly2);
        System.out.println("Product of polynomials: "); // Expected: -3x^3 + 14x^2 - 9x + 4
        product.printCoefficients();
        System.out.println();
        product.printExponents();
        System.out.println();

        // Save polynomial to file
        poly1.saveToFile("polynomial.txt");
        System.out.println("Polynomial saved to file.");

        // Create polynomial from file
        File file = new File("polynomial.txt");
        Polynomial polyFromFile = new Polynomial(file);
        System.out.println("Polynomial read from file: "); // Expected: 3x^2 - 2x + 1
        polyFromFile.printCoefficients();
        System.out.println();
        polyFromFile.printExponents();
        System.out.println();
    }
}
