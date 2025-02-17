package Repository.FileRepository;

import Domain.BirthdayCake;

import java.io.*;
import java.util.HashMap;

public class CakeRepositoryBinaryFile extends FileRepository<String, BirthdayCake> {
    public CakeRepositoryBinaryFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            elements = (HashMap<String, BirthdayCake>) ois.readObject();
        } catch(EOFException e) {
            this.elements = new HashMap<String, BirthdayCake>();
            writeToFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(elements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
