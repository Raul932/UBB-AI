package Repository.Base;

import Domain.Identifiable;

import java.util.*;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    protected Map<ID, T> elements;

    public MemoryRepository() {
        elements = new HashMap<>();
    }

    @Override
    public void add(ID id, T entity) {
        elements.put(id, entity);
    }

    @Override
    public Optional<T> delete(ID id) {
        if(elements.containsKey(id)) {
            return Optional.ofNullable(elements.remove(id));
        }
        return Optional.empty();
    }

    @Override
    public void modify(ID id, T entity) {
        if (elements.containsKey(id))
            elements.put(id, entity);

        else
            throw new RuntimeException("Element not found");
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(elements.get(id));
    }

    @Override
    public List<T> getAll() {
        // List<Integer> valuesList = new ArrayList<>(elements.values());
        return new ArrayList<>(elements.values());
    }

    public synchronized String getNextId() {
        int result = 1;  // Start checking from ID 1

        // Using a sorted set to track used IDs if not already using one
        TreeSet<Integer> usedIds = new TreeSet<>();
        elements.keySet().forEach(key -> usedIds.add(Integer.parseInt((String) key)));

        // Find the first available ID not in the set
        while (usedIds.contains(result)) {
            result++;
        }

        return Integer.toString(result);
    }
}
