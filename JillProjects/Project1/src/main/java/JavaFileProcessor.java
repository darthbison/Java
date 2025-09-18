import java.io.*;
import java.util.*;

public class JavaFileProcessor {
    private BufferedReader reader;
    private int currentLine;
    private int currentChar;
    Stack<Character> delimiterStack;

    public JavaFileProcessor(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        currentLine = 1;
        currentChar = 0;
        delimiterStack = new Stack<>();
    }

    public Character getNextCharacter() throws IOException {
        int ch;
        boolean inString = false;
        boolean inChar = false;
        boolean inSingleLineComment = false;
        boolean inMultiLineComment = false;

        while ((ch = reader.read()) != -1) {
            currentChar++;
            char character = (char) ch;

            if (inSingleLineComment) {
                if (character == '\n') {
                    inSingleLineComment = false;
                    currentLine++;
                    currentChar = 0;
                }
                continue;
            }

            if (inMultiLineComment) {
                if (character == '*' && reader.read() == '/') {
                    inMultiLineComment = false;
                    currentChar++;
                }
                continue;
            }

            if (inString) {
                if (character == '"' && reader.read() != '\\') {
                    inString = false;
                }
                continue;
            }

            if (inChar) {
                if (character == '\'' && reader.read() != '\\') {
                    inChar = false;
                }
                continue;
            }

            if (character == '/') {
                int nextChar = reader.read();
                if (nextChar == '/') {
                    inSingleLineComment = true;
                    continue;
                } else if (nextChar == '*') {
                    inMultiLineComment = true;
                    continue;
                } else {
                   // reader.unread(nextChar);
                	return (char)nextChar;
                }
            }

            if (character == '"') {
                inString = true;
                continue;
            }

            if (character == '\'') {
                inChar = true;
                continue;
            }

            if (!Character.isWhitespace(character)) {
                return character;
            }
        }
        return null;
    }

    public String getCurrentPosition() {
        return "Line: " + currentLine + ", Char: " + currentChar;
    }

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        JavaFileProcessor processor = null;
//
//        while (processor == null) {
//            System.out.print("Enter the file name: ");
//            String fileName = scanner.nextLine();
//            try {
//                processor = new JavaFileProcessor(fileName);
//            } catch (FileNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        try {
//            Character ch;
//            while ((ch = processor.getNextCharacter()) != null) {
//                if (ch == '{' || ch == '(' || ch == '[') {
//                    processor.delimiterStack.push(ch);
//                } else if (ch == '}' || ch == ')' || ch == ']') {
//                    if (processor.delimiterStack.isEmpty()) {
//                        System.out.println("Mismatched delimiter: " + ch + " at " + processor.getCurrentPosition());
//                        break;
//                    }
//                    char lastDelimiter = processor.delimiterStack.pop();
//                    if (!isMatchingPair(lastDelimiter, ch)) {
//                        System.out.println("Mismatched delimiter: " + ch + " at " + processor.getCurrentPosition());
//                        break;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    static boolean isMatchingPair(char left, char right) {
        return (left == '{' && right == '}') || (left == '(' && right == ')') || (left == '[' && right == ']');
    }

    public void closeFile() throws IOException {
        this.reader.close();
    }
}