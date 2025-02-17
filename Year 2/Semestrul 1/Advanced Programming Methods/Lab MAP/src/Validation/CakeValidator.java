package Validation;

import Domain.BirthdayCake;

import javax.xml.validation.Validator;

public class CakeValidator implements ValidatorInterface<BirthdayCake> {
    @Override
    public void validate(BirthdayCake cake) throws ValidatorException {
        if (!isValidId(cake))
            throw new ValidatorException("Invalid ID.\n");

        if (!isValidSize(cake))
            throw new ValidatorException("Invalid size.\n");

        if (!isValidFlavour(cake))
            throw new ValidatorException("Invalid flavour.\n");

        if (!isValidCandles(cake))
            throw new ValidatorException("Invalid candle number.\n");

        if (!isValidPrice(cake))
            throw new ValidatorException("Invalid price.\n");
    }

    private boolean isValidId(BirthdayCake cake) {
        return cake.getId() != null && Integer.parseInt(cake.getId()) > 0;
    }

    private boolean isValidSize(BirthdayCake cake) {
        return cake.getSize() > 0 && cake.getSize() <= 100;
    }

    private boolean isValidFlavour(BirthdayCake cake) {
        // Valid flavours: chocolate, vanilla, caramel, strawberry, coconut.
        String flavour = cake.getFlavour();

        if (flavour == null)
            return false;

        return flavour.equals("chocolate") ||
                flavour.equals("vanilla") ||
                flavour.equals("caramel") ||
                flavour.equals("strawberry") ||
                flavour.equals("coconut");
    }

    private boolean isValidCandles(BirthdayCake cake) {
        return cake.getCandles() > 0 && cake.getCandles() <= 122;
    }

    private boolean isValidPrice(BirthdayCake cake) {
        return cake.getPrice() > 0 && cake.getPrice() <= 10000;
    }
}
