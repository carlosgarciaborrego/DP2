/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.ClinicRepository;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class VetService {

	private VetRepository		vetRepository;

	private SpecialtyRepository	specialtyRepository;

	private UserService			userService;

	private AuthoritiesService	authoritiesService;

	private ClinicRepository	clinicRepository;


	@Autowired
	public VetService(final VetRepository vetRepository, final SpecialtyRepository specialtyRepository, final UserService userService, final AuthoritiesService authoritiesService, final ClinicRepository clinicRepository) {
		super();
		this.vetRepository = vetRepository;
		this.specialtyRepository = specialtyRepository;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
		this.clinicRepository = clinicRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Vet> findVets() throws DataAccessException {
		return this.vetRepository.findAll();
	}

	@Transactional
	public void saveVet(final Vet owner) throws DataAccessException {
		//creating owner
		this.vetRepository.save(owner);
		//creating user
		this.userService.saveUser(owner.getUser());
		//creating authorities
		this.authoritiesService.saveAuthorities(owner.getUser().getUsername(), "veterinarian");
	}

	public void delete(final int vetId) {

		this.vetRepository.deleteById(vetId);

	}

	public Optional<Vet> findVetById(final int vetId) {
		Optional<Vet> optional = this.vetRepository.findById(vetId);
		return optional;
	}

	public List<Pet> findPetsByVetId(final Integer vetId) {
		List<Pet> optional = this.vetRepository.findPetsByVetId(vetId);
		return optional;
	}

	public List<Vet> findVetByUserId(final String username) {
		List<Vet> optional = this.vetRepository.findVetByUserId(username);
		return optional;
	}

	@Transactional
	public void deleteById(final int vetId) throws DataAccessException {
		this.vetRepository.deleteById(vetId);

	}

	@Transactional
	public Specialty saveSpecialty(final Specialty specialty) throws DataAccessException {

		return this.specialtyRepository.save(specialty);
	}

	@Transactional
	public void deleteSpecialty(final Specialty specialty) throws DataAccessException {
		this.specialtyRepository.delete(specialty);
	}

	@Transactional
	public Optional<Specialty> findSpecialtyById(final int specialtyId) {
		Optional<Specialty> optional = this.specialtyRepository.findById(specialtyId);
		return optional;
	}

	public List<Vet> findVetByClinicId(final Integer username) {
		List<Vet> optional = this.vetRepository.findVetByClinicId(username);
		return optional;
	}

	public Collection<Clinic> findAllClinic() {
		return this.clinicRepository.findAll();
	}
}
