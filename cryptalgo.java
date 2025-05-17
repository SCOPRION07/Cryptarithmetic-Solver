import java.util.*;

public class cryptalgo {
    static HashMap<Character, Integer> letterToDigit = new HashMap<>();

    static HashSet<Integer> usedDigits = new HashSet<>();

    static String operand1, operand2, result;

    static Set<Character> leadingLetters = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first operand: ");
        operand1 = scanner.next().toUpperCase();

        System.out.print("Enter second operand: ");
        operand2 = scanner.next().toUpperCase();

        System.out.print("Enter the result: ");
        result = scanner.next().toUpperCase();

        if (operand1.length() > 1) leadingLetters.add(operand1.charAt(0));
        if (operand2.length() > 1) leadingLetters.add(operand2.charAt(0));
        if (result.length() > 1) leadingLetters.add(result.charAt(0));

        Set<Character> uniqueLetters = new HashSet<>();
        for (char c : (operand1 + operand2 + result).toCharArray()) {
            uniqueLetters.add(c);
        }
        if (uniqueLetters.size() > 10) {
            System.out.println("No solution exists: too many distinct letters.");
            scanner.close();
            return;
        }

        List<Character> letters = new ArrayList<>(uniqueLetters);

        if (solve(letters, 0)) {
            System.out.println("\nSolution Found!\n");
            printMatrix(letters);
            printAddition();
        } else {
            System.out.println("\nNo solution exists.");
        }
        scanner.close();
    }

    public static boolean solve(List<Character> letters, int index) {

        if (index == letters.size()) {
            return checkEquation();
        }

        char letter = letters.get(index);

        for (int digit = 0; digit <= 9; digit++) {

            if (usedDigits.contains(digit))
                continue;

            if (digit == 0 && leadingLetters.contains(letter))
                continue;

            letterToDigit.put(letter, digit);
            usedDigits.add(digit);

            if (solve(letters, index + 1)) {
                return true;
            }

            letterToDigit.remove(letter);
            usedDigits.remove(digit);
        }
        return false;
    }

    public static boolean checkEquation() {
        long num1 = convertWordToNumber(operand1);
        long num2 = convertWordToNumber(operand2);
        long sum = convertWordToNumber(result);
        return (num1 + num2 == sum);
    }

    public static long convertWordToNumber(String word) {
        long number = 0;
        for (char c : word.toCharArray()) {
            number = number * 10 + letterToDigit.get(c);
        }
        return number;
    }

    public static void printMatrix(List<Character> letters) {
        System.out.println("Letter to Digit Mapping:");
        for (char letter : letters) {
            System.out.println(letter + " -> " + letterToDigit.get(letter));
        }
    }

    public static void printAddition() {
        long num1 = convertWordToNumber(operand1);
        long num2 = convertWordToNumber(operand2);
        long sum = convertWordToNumber(result);
        
        System.out.println("\nAddition Operation:");
        System.out.println(" " + num1);
        System.out.println("+" + num2);
        System.out.print(" ");
        for (int i = 0; i < result.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println(sum);
    }
}