package br.com.fiap.microservices;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoPrimitivesRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class PojoTest {

    private static final String[] PACKAGES_TO_BE_TESTED = {
    	"br.com.fiap.microservices.entities"
    };

    @Test
    public void validateGettersSettersToString() {
        Validator validator = ValidatorBuilder.create()
                .with(new NoPublicFieldsExceptStaticFinalRule())
                .with(new NoStaticExceptFinalRule())
                .with(new NoPrimitivesRule())
                .with(new SetterTester())
                .with(new GetterTester())
        .build();
        Arrays.stream(PACKAGES_TO_BE_TESTED)
        	.forEach(pack -> validator.validate(pack, new FilterPackageInfo()));
    }
}

