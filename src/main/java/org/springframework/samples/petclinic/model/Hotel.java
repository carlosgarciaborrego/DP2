
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
public class Hotel extends NamedEntity {

	@Column(name = "name")
	private String	name;

	@Column(name = "location")
	@NotEmpty
	private String	location;

	@Column(name = "count")
	@Range(min = 0)
	private Integer	count;

	@Column(name = "capacity")
	private Integer	capacity;

	//	private String		description;
	//
	//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	//	private LocalDate	start;
	//
	//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	//	private LocalDate	e;

}
