package Filter;

import Domain.BirthdayCake;

public class FilterCakeByFlavour implements AbstractFilter<BirthdayCake> {
    private final String flavour;

    public FilterCakeByFlavour(String flavour) {
        this.flavour = flavour;
    }

    @Override
    public boolean accept(BirthdayCake birthdayCake) {
        return flavour.equals(birthdayCake.getFlavour());
    }
}
