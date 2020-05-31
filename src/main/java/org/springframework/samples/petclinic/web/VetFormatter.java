
package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Component;

@Component
public class VetFormatter implements Formatter<Vet> {

	private final VetService vetService;


	@Autowired
	public VetFormatter(final VetService vetService) {
		this.vetService = vetService;
	}

	@Override
	public String print(final Vet object, final Locale locale) {
		return object.getFirstName() + " " + object.getLastName();
	}

	@Override
	public Vet parse(final String text, final Locale locale) throws ParseException {
		Collection<Vet> findhotels = this.vetService.findVets();
		for (Vet type : findhotels) {
			String nombre = type.getFirstName() + " " + type.getLastName();
			if (nombre.equals(text)) {
				return type;
			}
		}
		throw new ParseException("Vet not found: " + text, 0);
	}

}
