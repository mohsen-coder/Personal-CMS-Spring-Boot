package mohsen.coder.personalcmsblogspring.adapter.web.request;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FieldsValueMatch.List.class)
@Documented
@Constraint(validatedBy = FieldsValueMatchValidator.class)
public @interface FieldsValueMatch {
    String message() default "Fields values don't match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field() default "";

    String fieldMatch() default "";

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FieldsValueMatch[] value();
    }
}