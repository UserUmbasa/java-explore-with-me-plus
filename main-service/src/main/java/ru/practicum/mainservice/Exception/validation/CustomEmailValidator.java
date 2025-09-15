package ru.practicum.mainservice.Exception.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Общая длина email не меньше 6 символов
 * Общая длина email не больше 254 символов
 * Длина локальной части (до @) не больше 64 символов
 * Каждая часть домена (между точками) не длиннее 63 символов
 * Наличие символа @
 * Корректное разделение на локальную часть и домен
 */
public class CustomEmailValidator implements ConstraintValidator<CustomEmail, String> {
    private static final int MIN_EMAIL_LENGTH = 6;
    private static final int MAX_EMAIL_LENGTH = 254;
    private static final int MAX_DOMAIN_PART_LENGTH = 63;
    private static final int MAX_LOCAL_PART_LENGTH = 64;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }
        if (email.length() < MIN_EMAIL_LENGTH || email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return false;
        }
        String localPart = email.substring(0, atIndex);
        if (localPart.length() > MAX_LOCAL_PART_LENGTH) {
            return false;
        }
        String domain = email.substring(atIndex + 1);
        String[] domainParts = domain.split("\\.");
        for (String part : domainParts) {
            if (part.length() > MAX_DOMAIN_PART_LENGTH) {
                return false;
            }
        }
        return true;
    }
}

