
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	// ------------------------- persona ------------------------
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

	//------------------------------------ Vet ---------------------------------

	//Positive Vet
	@Test
	void shouldPositiveVet() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Vet v = new Vet();
		v.setFirstName("Estefan");
		v.setCity("Barcelona");
		v.setLastName("Gonzalez");
		v.setTelephone("666666666");
		v.setAddress("La Botica");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Vet>> constraintViolations = validator.validate(v);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	// Negative Vet

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

	// ----------------------------- Cause ------------------------------------------------

	//------------------------------- Donation ---------------------------------------------

	// ----------------------------- Hotels ------------------------------------------------

	//Negative Causes
	@Test
	void shouldNotValidateWhenCapacityIsNegative() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Hotel hotel = new Hotel();
		hotel.setName("Calle Betis");
		hotel.setLocation("Sevilla");
		hotel.setCapacity(-1);

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
		hotel.setName("Calle Andalucia");
		hotel.setCapacity(10);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Hotel> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("location");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenNameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Hotel hotel = new Hotel();
		hotel.setLocation("Sevilla");
		hotel.setName("");
		hotel.setCapacity(10);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Hotel> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	//No se puede dar que el atributo Count sea negativo, por tanto no se hace test

	//Positive Causes
	@Test
	void shouldWorkProperly() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Hotel hotel = new Hotel();
		hotel.setLocation("Sevilla");
		hotel.setName("Calle Garcia");
		hotel.setCapacity(10);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Hotel>> constraintViolations = validator.validate(hotel);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	//------------------------------------ Reservation ---------------------------------

	// Negative Cause
	@Test
	void shouldNotValidateWhenTelephoneIsGreaterThan10Character() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Reservation res = new Reservation();
		res.setTelephone("66677788891111");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2020";
		LocalDate localDate = LocalDate.parse(date, formatter);
		res.setReservationDate(localDate);
		res.setStatus("pending");
		res.setResponseClient("");
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		res.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		res.setClinic(cli);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(res);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Reservation> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
		Assertions.assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");
	}

	@Test
	void shouldNotValidateWhenTelephoneHasNotDigit() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Reservation res = new Reservation();
		res.setTelephone("SSAAAS");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2020";
		LocalDate localDate = LocalDate.parse(date, formatter);
		res.setReservationDate(localDate);
		res.setStatus("pending");
		res.setResponseClient("");
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		res.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		res.setClinic(cli);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(res);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Reservation> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
		Assertions.assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");
	}

	@Test
	void shouldNotValidateWhenTelephoneHasDigitAndLetter() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Reservation res = new Reservation();
		res.setTelephone("12345asd6");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2020";
		LocalDate localDate = LocalDate.parse(date, formatter);
		res.setReservationDate(localDate);
		res.setStatus("pending");
		res.setResponseClient("");
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		res.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		res.setClinic(cli);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(res);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Reservation> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
		Assertions.assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");
	}

	@Test
	void shouldNotValidateWhenDateIsLowerThanTomorrow() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Reservation res = new Reservation();
		res.setTelephone("666777444");
		LocalDate fechaAnterior = LocalDate.now();
		res.setReservationDate(fechaAnterior);
		res.setStatus("pending");
		res.setResponseClient("");
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		res.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		res.setClinic(cli);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(res);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Reservation> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("reservationDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must be a future date");
	}

	@Test
	void shouldNotValidateWhenDateIsLowerThanTomorrow2() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Reservation res = new Reservation();
		res.setTelephone("666777444");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2016";
		LocalDate localDate = LocalDate.parse(date, formatter);
		res.setReservationDate(localDate);
		res.setStatus("pending");
		res.setResponseClient("");
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		res.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		res.setClinic(cli);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(res);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Reservation> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("reservationDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must be a future date");
	}

	@Test
	void shouldNotValidateWhenClinicIsNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Reservation res = new Reservation();
		res.setTelephone("123456789");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2020";
		LocalDate localDate = LocalDate.parse(date, formatter);
		res.setReservationDate(localDate);
		res.setStatus("pending");
		res.setResponseClient("");
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		res.setOwner(owner);
		Clinic cli = null;
		res.setClinic(cli);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(res);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Reservation> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("clinic");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void ValidateReservation() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Reservation res = new Reservation();
		res.setTelephone("123456789");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2020";
		LocalDate localDate = LocalDate.parse(date, formatter);
		res.setReservationDate(localDate);
		res.setStatus("pending");
		res.setResponseClient("");
		Owner owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		res.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		res.setClinic(cli);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(res);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	//------------------------------------ PetHistory ---------------------------------

	// Negative Cause

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

	// Positive Cause

	@Test
	void shouldPositivePethistory() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		PetHistory petHist = new PetHistory();
		petHist.setDate(LocalDate.now());
		petHist.setSummary("summary");
		petHist.setDetails("details");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PetHistory>> constraintViolations = validator.validate(petHist);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);

	}

	//------------------------------------ Visit (Registration in HTML) ---------------------------------

	// Negative Cause

	//No se puede dar, ya que la fecha es la actual, la descripcion es opcional y tienes que seleccionar un hotel para poder añadir un visita.

	// Positive Cause
	@Test
	void ValidateVisit() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Visit visit = new Visit();
		LocalDate fechaAnterior = LocalDate.now();
		visit.setDate(fechaAnterior);
		visit.setDescription("Problemas respiratorios");
		Hotel hotel = new Hotel();
		hotel.setLocation("Sevilla");
		hotel.setName("Calle Garcia");
		hotel.setCapacity(10);
		visit.setHotel(hotel);
		Pet p = new Pet();
		p.setName("Curro");
		LocalDate fechaCumple = LocalDate.now();
		p.setBirthDate(fechaCumple);
		PetType tipo = new PetType();
		tipo.setName("perro");
		p.setType(tipo);
		Vet v = new Vet();
		v.setFirstName("Estefan");
		v.setCity("Barcelona");
		v.setLastName("Gonzalez");
		v.setTelephone("666777888");
		v.setAddress("La botica");
		p.setVet(v);
		visit.setPet(p);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Visit>> constraintViolations = validator.validate(visit);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	//------------------------------------ Clinic ---------------------------------

	@Test
	void ValidateClinic() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Clinic c = new Clinic();
		c.setCapacity(3);
		c.setEmail("kfjds@email.com");
		c.setEmergency("66666666");
		c.setLocation("Sevilla");
		c.setName("San Bernardo Clinic");
		c.setTelephone("999999999");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Clinic>> constraintViolations = validator.validate(c);
		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

	//Negative

	@Test
	void nonValidateTelephoneClinic() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Clinic c = new Clinic();
		c.setCapacity(3);
		c.setEmail("kfjds@email.com");
		c.setEmergency("66666666");
		c.setLocation("Sevilla");
		c.setName("San Bernardo Clinic");
		c.setTelephone("");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Clinic>> constraintViolations = validator.validate(c);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Clinic> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must be not empty");
	}

	@Test
	void nonValidateCapacityClinic() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Clinic c = new Clinic();
		c.setCapacity(-3);
		c.setEmail("kfjds@email.com");
		c.setEmergency("66666666");
		c.setLocation("Sevilla");
		c.setName("San Bernardo Clinic");
		c.setTelephone("999999999");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Clinic>> constraintViolations = validator.validate(c);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Clinic> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("capacity");
	}

	@Test
	void nonValidateEmergencyClinic() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Clinic c = new Clinic();
		c.setCapacity(3);
		c.setEmail("kfjds@email.com");
		c.setEmergency("66666666666");
		c.setLocation("Sevilla");
		c.setName("San Bernardo Clinic");
		c.setTelephone("999999999");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Clinic>> constraintViolations = validator.validate(c);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Clinic> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("emergency");
	}

}
