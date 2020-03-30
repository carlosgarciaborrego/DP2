
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

	// ----------------------------- Hotels ------------------------------------------------

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

	//------------------------------------ Specialty ---------------------------------

	//Negative Cause

	@Test
	void nonValidateWithNameIsEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Specialty specialty = new Specialty();
		specialty.setName("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Specialty>> constraintViolations = validator.validate(specialty);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Specialty> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"AE", "Odontología especial y Cirugía Maxilofacial Veterinarias"
	})
	void nonValidateWithNameLower3AndHigher50Words(final String argumento) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Specialty specialty = new Specialty();
		specialty.setName(argumento);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Specialty>> constraintViolations = validator.validate(specialty);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Specialty> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
	}

	// Positive Cause

	@ParameterizedTest
	@ValueSource(strings = {
		"Ont", "Dentristy", "Odontologías y Cirugías Maxilofaciales Veterinaria"
	})
	void ValidateWithNameHigher3AndLower50Words(final String argumento) {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Specialty specialty = new Specialty();
		specialty.setName(argumento);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Specialty>> constraintViolations = validator.validate(specialty);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);

	}

	@Test
	void shouldNotValidateWhenAdressVetEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Vet v = new Vet();
		v.setFirstName("Estefan");
		v.setCity("Barcelona");
		v.setLastName("Gonzalez");
		v.setTelephone("666666666");
		v.setAddress("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Vet>> constraintViolations = validator.validate(v);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Vet> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("address");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenTelephoneVetEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Vet v = new Vet();
		v.setFirstName("Estefan");
		v.setCity("Barcelona");
		v.setLastName("Gonzalez");
		v.setTelephone("");
		v.setAddress("La botica");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Vet>> constraintViolations = validator.validate(v);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(2);
		ConstraintViolation<Vet> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
	}

	@Test
	void shouldNotValidateWhenTelephoneLongVetEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Vet v = new Vet();
		v.setFirstName("Estefan");
		v.setCity("Barcelona");
		v.setLastName("Gonzalez");
		v.setTelephone("666666666666");
		v.setAddress("La botica");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Vet>> constraintViolations = validator.validate(v);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Vet> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
		Assertions.assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");
	}

	@Test
	void shouldNotValidateWhenSummaryEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		PetHistory petHist = new PetHistory();
		petHist.setDate(LocalDate.now());
		petHist.setSummary("");
		petHist.setDetails("details");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PetHistory>> constraintViolations = validator.validate(petHist);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<PetHistory> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("summary");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenDetailsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		PetHistory petHist = new PetHistory();
		petHist.setDate(LocalDate.now());
		petHist.setSummary("summary");
		petHist.setDetails("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PetHistory>> constraintViolations = validator.validate(petHist);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<PetHistory> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("details");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

}
