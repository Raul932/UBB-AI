package Repository.FileRepository;

import Domain.BirthdayCake;

import java.io.*;

public class CakeRepositoryTextFile extends FileRepository<String, BirthdayCake> {
    public CakeRepositoryTextFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(", ");

                if (tokens.length != 5)
                    throw new IOException("File not valid.\n");

                else {
                    BirthdayCake cake = new BirthdayCake(
                            tokens[0],
                            Integer.parseInt(tokens[1]),
                            tokens[2],
                            Integer.parseInt(tokens[3]),
                            Double.parseDouble(tokens[4])
                    );

                    elements.put(cake.getId(), cake);
                }
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Iterable<BirthdayCake> iter = getAll();
            String separator = ", ";

            for (BirthdayCake cake : iter) {
                bw.write(
                        cake.getId() + separator +
                                cake.getSize() + separator +
                                cake.getFlavour() + separator +
                                cake.getCandles() + separator +
                                cake.getPrice() + "\n"
                );
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
