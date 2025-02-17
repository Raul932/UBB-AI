package Validation;

import Domain.Order;
import Service.BirthdayCakeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderValidator implements ValidatorInterface<Order> {
    private final BirthdayCakeService birthdayCakeService;

    public OrderValidator(BirthdayCakeService birthdayCakeService) {
        this.birthdayCakeService = birthdayCakeService;
    }

    @Override
    public void validate(Order order) throws ValidatorException {
        if (!isValidId(order))
            throw new ValidatorException("Invalid order ID.\n");

        if (!isValidCustomerName(order))
            throw new ValidatorException("Invalid customer name.\n");

        if (!isValidCustomerContact(order))
            throw new ValidatorException("Invalid customer contact.\n");

        if (!isValidOrderDate(order))
            throw new ValidatorException("Invalid order date.\n");

        if (!isValidCakeIds(order))
            throw new ValidatorException("Invalid cake ID's.\n");
    }

    private boolean isValidId(Order order) {
        return order.getId() != null && Integer.parseInt(order.getId()) > 0;
    }

    private boolean isValidCustomerName(Order order) {
        // Valid name examples: Parau, Parau Raul;
        // Not valid name examples: raul, ra^ul, ra&ul.
        if (order.getCustomerName() == null)
            return false;

        Pattern pattern = Pattern.compile("^[A-Z][a-z]+(-[A-Z][a-z]+)?$");
        Matcher matcher = pattern.matcher(order.getCustomerName());

        return matcher.matches();
    }

    private boolean isValidCustomerContact(Order order) {
        // Valid types of contact: phone, email, fax.
        String contact = order.getCustomerContact();
        if (contact == null)
            return false;

        return contact.equals("phone") || contact.equals("email") || contact.equals("fax");
    }

    private boolean isValidOrderDate(Order order) {
        // Valid types of date: dd/MM/yyyy.
        if (order.getOrderDate() == null)
            return false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate.parse(order.getOrderDate(), formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidCakeIds(Order order) {
        // The id's matching birthday-cakes must be found in the service's repository.
        for (int id : order.getCakeIds()) {
            if (birthdayCakeService.getBirthdayCakeById(id) == null)
                return false;
        }

        return true;
    }
}
