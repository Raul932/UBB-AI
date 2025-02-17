package Repository.FileRepository;

import Domain.Identifiable;
import Repository.Base.MemoryRepository;

import java.util.Optional;

public abstract class FileRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T> {
    protected String filename;

    public FileRepository(String filename) {
        this.filename = filename;
        readFromFile();
    }

    protected abstract void readFromFile();

    protected abstract void writeToFile();

    @Override
    public void add(ID id, T entity) {
        super.add(id, entity);
        writeToFile();
    }

    @Override
    public Optional<T> delete(ID id) {
        super.delete(id);
        writeToFile();
        return Optional.empty();
    }

    @Override
    public void modify(ID id, T entity) {
        super.modify(id, entity);
        writeToFile();
    }
}
