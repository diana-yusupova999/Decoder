import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainTests {
    private static final String resources = new File("").getAbsolutePath() + "/src/main/resources/";

    @Test
    public void testXorCipher() {
        Path input = new File(resources + "input_1_.txt").toPath();
        Path output = new File(resources + "output_1_.txt").toPath();
        cleanFile(output);
        String inputLines = read(input);
        String outputLines = read(output);
        new Main("-c", "615d5049405d405b", input.toString(), "-o", output.toString());
        Assertions.assertEquals(inputLines, read(input));
        Assertions.assertNotEquals(outputLines, read(output));
        Assertions.assertNotEquals(outputLines, read(input));
        new Main("-d", "615d5049405d405b", output.toString());
        Assertions.assertNotEquals(outputLines, read(input));
        Assertions.assertEquals(inputLines, read(output));
    }

    @Test
    public void testArgs() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Main("-c"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Main("-c", "key", "wrongPath.txt"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Main("-c", "key", "input.txt", "output_without_o_flag.txt"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Main("-c", "input.txt", "-o", "output.txt"));
    }

    private static void cleanFile(Path path) {
        try {
            new FileOutputStream(path.toFile()).write("".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String read(Path path) {
        try {
            StringBuilder res = new StringBuilder();
            for (String line : Files.readAllLines(path)) {
                res.append(line).append("\n");
            }
            return res.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}