public class Polynomial {
    private double[] coefficients;
    public Polynomial() {
        this.coefficients = new double[]{0};
    }
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }
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
    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }

        return result;
    }
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
