
package org.springframework.samples.petclinic.model;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 *         when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Person person = new Person();
		person.setFirstName("");
		person.setLastName("smith");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	//Hotels

	//Negative Causes
	@Test
	void shouldNotValidateWhenCapacityIsNegative() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Hotel hotel = new Hotel();
		hotel.setLocation("Sevilla");
		hotel.setCapacity(-1);
		hotel.setCount(0);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Hotel> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("capacity");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must be between 0 and 9223372036854775807");
	}

	@Test
	void shouldNotValidateWhenLocationEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Hotel hotel = new Hotel();
		hotel.setLocation("");
		hotel.setCapacity(10);
		hotel.setCount(5);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Hotel> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("location");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenIsNegative() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Hotel hotel = new Hotel();
		hotel.setLocation("Sevilla");
		hotel.setCapacity(-1);
		hotel.setCount(0);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Hotel> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("capacity");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must be between 0 and 9223372036854775807");
	}

	//	@Test
	//	void shouldNotValidateWhenCountIsGreaterThanCapacity() {
	//		LocaleContextHolder.setLocale(Locale.ENGLISH);
	//		Hotel hotel = new Hotel();
	//		hotel.setLocation("Sevilla");
	//		hotel.setCapacity(10);
	//		hotel.setCount(15);
	//
	//		Validator validator = this.createValidator();
	//		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
	//		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
	//		ConstraintViolation<Hotel> violation = constraintViolations.iterator().next();
	//		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("count");
	//		Assertions.assertThat(violation.getMessage()).isEqualTo("count must not be greater than capacity");
	//	}

	//Positive Causes
	@Test
	void shouldWorkProperly() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Hotel hotel = new Hotel();
		hotel.setLocation("Sevilla");
		hotel.setCapacity(10);
		hotel.setCount(3);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

}
