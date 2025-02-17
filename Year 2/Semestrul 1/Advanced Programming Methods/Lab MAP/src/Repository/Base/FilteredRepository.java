package Repository.Base;

import Domain.Identifiable;
import Filter.AbstractFilter;

import java.util.ArrayList;
import java.util.List;

public class FilteredRepository<T extends Identifiable<String>> extends MemoryRepository<String, T> {
    public AbstractFilter<T> filter;

    public FilteredRepository(AbstractFilter<T> filter) {
        this.filter = filter;
    }

    @Override
    public List<T> getAll() {
        List<T> filteredElements = new ArrayList<>();

        for (T element : super.getAll()) {
            if (filter.accept(element)) {
                filteredElements.add(element);
            }
        }
        return filteredElements;
    }
}
