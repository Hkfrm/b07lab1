public class Polynomial {
    private double[] coefficients;

    // No-argument constructor that sets the polynomial to zero
    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    // Constructor that takes an array of double as an argument
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] resultCoefficients = new double[maxLength];

        for (int i = 0; i < this.coefficients.length; i++) {
            resultCoefficients[i] += this.coefficients[i];
        }

        for (int i = 0; i < other.coefficients.length; i++) {
            resultCoefficients[i] += other.coefficients[i];
        }

        return new Polynomial(resultCoefficients);
    }

    // Method to evaluate the polynomial for a given value of x
    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}