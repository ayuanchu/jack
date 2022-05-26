import lombok.SneakyThrows;

/**
 * <p>
 * JavaProcessTests
 * <p>
 *
 * @author: kancy
 * @date: 2020/7/6 11:11
 **/

public class JavaProcessTests {
    @SneakyThrows
    public static void main(String[] args) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("Mybatisplus Code Generator.exe");
        Process process = processBuilder.start();
        System.exit(0);

    }
}
