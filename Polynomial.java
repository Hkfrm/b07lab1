import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        coefficients = null;
        exponents = null;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        String line = reader.readLine();
        if (line.equals("")) {
            coefficients = null;
            exponents = null;
        } else {
            line = line.replace("-", "~-");
            line = line.replace("+", "~+");
            String[] terms = line.split("~");
            int i = 0;
            if (terms[0].equals("")) {
                i = 1;
            }

            Polynomial result = new Polynomial(new double[]{1}, new int[]{0});
            for (; i < terms.length; i++) {
                String currentTerm = terms[i];
                int indexOfX = currentTerm.indexOf("x");
                int xExponent;
                double coefficient;
                if (indexOfX == -1) {
                    xExponent = 0;
                } else if (currentTerm.substring(indexOfX + 1).equals("")) {
                    xExponent = 1;
                } else {
                    xExponent = Integer.parseInt(currentTerm.substring(indexOfX + 1));
                }
                if (indexOfX == -1) {
                    coefficient = Double.parseDouble(currentTerm);
                } else {
                    coefficient = Double.parseDouble(currentTerm.substring(0, indexOfX));
                }

                result = result.add(new Polynomial(new double[]{coefficient}, new int[]{xExponent}));
            }
            result = result.add(new Polynomial(new double[]{-1}, new int[]{0}));
            coefficients = result.coefficients;
            exponents = result.exponents;
        }
        reader.close();
    }

    public Polynomial add(Polynomial other) {
        if (coefficients == null || other.coefficients == null) {
            System.out.println("Error, polynomial is not initialized (add)");
            return new Polynomial();
        }
        int size1 = exponents.length;
        int size2 = other.exponents.length;

        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size1 - 1 - i; j++) {
                if (exponents[j] > exponents[j + 1]) {
                    int tempExponent = exponents[j];
                    double tempCoefficient = coefficients[j];
                    exponents[j] = exponents[j + 1];
                    coefficients[j] = coefficients[j + 1];
                    exponents[j + 1] = tempExponent;
                    coefficients[j + 1] = tempCoefficient;
                }
            }
        }
        for (int i = 0; i < size2; i++) {
            for (int j = 0; j < size2 - 1 - i; j++) {
                if (other.exponents[j] > other.exponents[j + 1]) {
                    int tempExponent = other.exponents[j];
                    double tempCoefficient = other.coefficients[j];
                    other.exponents[j] = other.exponents[j + 1];
                    other.coefficients[j] = other.coefficients[j + 1];
                    other.exponents[j + 1] = tempExponent;
                    other.coefficients[j + 1] = tempCoefficient;
                }
            }
        }

        int firstSize = coefficients.length;
        int secondSize = other.coefficients.length;
        int i = 0;
        int j = 0;
        int resultSize = 0;
        while (i < firstSize && j < secondSize) {
            if (exponents[i] == other.exponents[j]) {
                if (coefficients[i] + other.coefficients[j] != 0) {
                    resultSize++;
                }
                i++;
                j++;
            } else if (exponents[i] < other.exponents[j]) {
                resultSize++;
                i++;
            } else {
                resultSize++;
                j++;
            }
        }
        resultSize = resultSize + firstSize + secondSize - i - j;
        double[] resultCoefficients = new double[resultSize];
        int[] resultExponents = new int[resultSize];

        i = 0;
        j = 0;
        int resultIndex = 0;
        while (i < firstSize && j < secondSize) {
            if (exponents[i] == other.exponents[j]) {
                if (coefficients[i] + other.coefficients[j] != 0) {
                    resultExponents[resultIndex] = exponents[i];
                    resultCoefficients[resultIndex] = coefficients[i] + other.coefficients[j];
                    resultIndex++;
                }
                i++;
                j++;
            } else if (exponents[i] < other.exponents[j]) {
                resultExponents[resultIndex] = exponents[i];
                resultCoefficients[resultIndex] = coefficients[i];
                i++;
                resultIndex++;
            } else {
                resultExponents[resultIndex] = other.exponents[j];
                resultCoefficients[resultIndex] = other.coefficients[j];
                j++;
                resultIndex++;
            }
        }

        if (resultIndex < resultSize && firstSize == i) {
            for (; resultIndex < resultSize; resultIndex++, j++) {
                resultExponents[resultIndex] = other.exponents[j];
                resultCoefficients[resultIndex] = other.coefficients[j];
            }
        } else if (resultIndex < resultSize && secondSize == j) {
            for (; resultIndex < resultSize; resultIndex++, i++) {
                resultExponents[resultIndex] = exponents[i];
                resultCoefficients[resultIndex] = coefficients[i];
            }
        }

        return new Polynomial(resultCoefficients, resultExponents);
    }

    public double evaluate(double x) {
        if (coefficients == null) {
            System.out.println("Error, polynomial is not initialized (evaluate)");
            return -10000000;
        }
        int size = coefficients.length;
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        if (coefficients == null) {
            System.out.println("Error, polynomial is not initialized (hasRoot)");
            return false;
        }
        return (evaluate(x) == 0);
    }

    public Polynomial multiply(Polynomial other) {
        if (coefficients == null || other.coefficients == null) {
            System.out.println("Error, either polynomial is not initialized (multiply)");
            return new Polynomial();
        }
        Polynomial result = new Polynomial(new double[]{1}, new int[]{0});

        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double[] termCoefficients = new double[]{coefficients[i] * other.coefficients[j]};
                int[] termExponents = new int[]{exponents[i] + other.exponents[j]};
                result = result.add(new Polynomial(termCoefficients, termExponents));
            }
        }
        result = result.add(new Polynomial(new double[]{-1}, new int[]{0}));
        return result;
    }

    public void saveToFile(String fileName) throws IOException {
        File outputFile = new File(fileName);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        FileWriter writer = new FileWriter(fileName);
        int i = 0;

        writer.write(Double.toString(coefficients[i]));
        if (exponents[i] != 0) {
            writer.write("x");
            writer.write(Integer.toString(exponents[i]));
        }
        for (i = 1; i < coefficients.length; i++) {
            if (coefficients[i] > 0) {
                writer.write("+");
            }
            writer.write(Double.toString(coefficients[i]));

            if (exponents[i] != 0) {
                writer.write("x");
                writer.write(Integer.toString(exponents[i]));
            }
        }
        writer.close();
    }

    public void printCoefficients() {
        if (coefficients == null) return;
        for (int i = 0; i < coefficients.length; i++) {
            System.out.print(coefficients[i] + " ");
        }
    }

    public void printExponents() {
        if (exponents == null) return;
        for (int i = 0; i < exponents.length; i++) {
            System.out.print(exponents[i] + " ");
        }
    }
}
