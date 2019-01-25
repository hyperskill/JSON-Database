package org.hyperskill.database.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Util {
    public static Request read(String filename) throws IOException, IllegalArgumentException {
        Path file = Paths.get(filename);
        String command = null;
        String key = null;
        StringBuilder value = new StringBuilder();
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line = null;
            int lineProcessing = 0;
            while ((line = reader.readLine()) != null) {
                switch (lineProcessing) {
                    case 0:
                        command = line;
                        lineProcessing++;
                        break;
                    case 1:
                        key = line;
                        lineProcessing++;
                        break;
                    case 2:
                        value.append(line);
                        lineProcessing++;
                        break;
                    default:
                        value.append(" ");
                        value.append(line);
                        lineProcessing++;
                        break;
                }
            }
        }
        return new Request(command, key, value.toString());
    }

    public static void write(Response response, String outFileName) {
        Objects.requireNonNull(response, "Response can't be null in Util.write");
        Objects.requireNonNull(outFileName, "outFileName can't be null in Util.write");
        try (FileWriter fw = new FileWriter(outFileName, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(response.getType());
            out.println(response.getValue());

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
