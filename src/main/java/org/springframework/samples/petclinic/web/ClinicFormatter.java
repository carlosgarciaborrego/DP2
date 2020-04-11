
package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Component;

@Component
public class ClinicFormatter implements Formatter<Clinic> {

	private ClinicService clinicService;


	@Autowired
	public ClinicFormatter(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@Override
	public String print(final Clinic object, final Locale locale) {
		// TODO Auto-generated method stub
		return object.getName();
	}

	@Override
	public Clinic parse(final String text, final Locale locale) throws ParseException {
		Collection<Clinic> findclinics = this.clinicService.findAll();
		for (Clinic type : findclinics) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
