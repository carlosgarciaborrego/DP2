
package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.HotelService;

public class HotelFormatter implements Formatter<Hotel> {

	private HotelService hotelService;


	@Autowired
	public HotelFormatter(final HotelService hotelService) {
		this.hotelService = hotelService;
	}

	@Override
	public String print(final Hotel object, final Locale locale) {
		// TODO Auto-generated method stub
		return object.getName() + " (" + object.getLocation() + ")";
	}

	@Override
	public Hotel parse(final String text, final Locale locale) throws ParseException {
		Collection<Hotel> findhotels = (Collection<Hotel>) this.hotelService.findAll();
		for (Hotel type : findhotels) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
