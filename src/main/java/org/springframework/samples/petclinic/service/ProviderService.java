
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderService {

	private ProviderRepository providerRepository;


	@Autowired
	public ProviderService(final ProviderRepository providerRepository) {
		this.providerRepository = providerRepository;
	}

	@Transactional
	public int providerCount() {
		return (int) this.providerRepository.count();
	}

	@Transactional
	public Iterable<Provider> findAll() {
		return this.providerRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Provider findProviderById(final int id) {
		return this.providerRepository.findProviderById(id);
	}

	@Transactional
	public void save(final Provider provider) {
		this.providerRepository.save(provider);
	}

	@Transactional
	public void delete(final Provider provider) {
		this.providerRepository.delete(provider);
	}

	@Transactional
	public Clinic findClinicById(final int id) {
		return this.providerRepository.findClinicByClinicId(id);
	}

}
