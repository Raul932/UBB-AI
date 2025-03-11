package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SerializationUtil {
    public static void serializeModel(Serializable model, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
            oos.writeObject(model);
        }
    }

    public static Object deserializeModel(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)))) {
            return ois.readObject();
        }
    }
}
