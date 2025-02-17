package Filter;

import Domain.BirthdayCake;

public class FilterCakeByPrice implements AbstractFilter<BirthdayCake>{
    private final double price;

    public FilterCakeByPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean accept(BirthdayCake birthdayCake) {
        return birthdayCake.getPrice() == price;
    }
}
