import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//Rename this class
public class Main {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //JavaFileProcessor processor = null;

        //while (processor == null) {
            System.out.print("Enter the file name: ");
            String fileName = scanner.nextLine();
            try {
                System.out.print("File found. Beginning processing...\n");
                JavaFileProcessor processor = new JavaFileProcessor(fileName);

                Character ch;
                while ((ch = processor.getNextCharacter()) != null) {
                    if (ch == '{' || ch == '(' || ch == '[') {
                        processor.delimiterStack.push(ch);
                    } else if (ch == '}' || ch == ')' || ch == ']') {
                        if (processor.delimiterStack.isEmpty()) {
                            System.out.println("Mismatched delimiter: " + ch + " at " + processor.getCurrentPosition());
                            break;
                        }
                        char lastDelimiter = processor.delimiterStack.pop();
                        if (!JavaFileProcessor.isMatchingPair(lastDelimiter, ch)) {
                            System.out.println("Mismatched delimiter: " + ch + " at " + processor.getCurrentPosition());
                            break;
                        }
                    }
                }
                //
                if (processor.delimiterStack.isEmpty()) {
                    System.out.println("Delimiters are matching");

                } else {
                    System.out.println("Mismatched delimiter: " + ch + " at " + processor.getCurrentPosition());
                }

                processor.closeFile();

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
