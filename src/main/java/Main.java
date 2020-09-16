import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class Main {

    @Option(name = "-c")
    private String encodeKey;

    @Option(name = "-d")
    private String decodeKey;

    @Argument(required = true)
    private String input;

    @Option(name = "-o")
    private String output;

    public Main(String... args) throws IllegalArgumentException {
        start(args);
    }

    public Main() {
    }

    public static void main(String[] args) {
        new Main().start(args);
    }

    private void start(String[] args) throws IllegalArgumentException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            throw new IllegalArgumentException(e);
        }
        File outputFile;
        if (input == null || input.equals("")) throw new IllegalArgumentException("enter a file!");
        File inputFile = new File(input);
        if (!inputFile.isFile() || !inputFile.canRead()) throw new IllegalArgumentException("input file is wrong!");
        if (output == null) {
            outputFile = new File(input);
        } else {
            outputFile = new File(output);
        }
        if (!outputFile.isFile() || !outputFile.canWrite())
            throw new IllegalArgumentException("path for execute is wrong!");
        if (encodeKey != null && decodeKey == null) {
            main.java.XorCipher.encode(inputFile, outputFile, encodeKey);
        } else if (encodeKey == null && decodeKey != null) {
            main.java.XorCipher.decode(inputFile, outputFile, decodeKey);
        } else throw new IllegalArgumentException("enter -c for encode or -d for decode!");
    }
}