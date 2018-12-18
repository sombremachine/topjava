package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserFormValidator implements Validator {

    private UserRepository repository;

    @Autowired
    public UserFormValidator(UserRepository repository) {
        this.repository = repository;
    }

    public boolean checkEmail(String email, Integer id) {
        User user = repository.getByEmail(email);
        return ((user == null) || (user.getId().equals(id)));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz) || User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String email = null;
        Integer id = null;

        if (target instanceof User) {
            email = ((User) target).getEmail();
            id = ((User) target).getId();
        }

        if (target instanceof UserTo) {
            email = ((UserTo) target).getEmail();
            id = ((UserTo) target).getId();
        }

        if (!checkEmail(email, id)) {
            errors.rejectValue("email", "email", "User with this email already exists");
        }
    }
}
