
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SenseRepository;
import domain.Chorbi;
import domain.Sense;

@Service
@Transactional
public class SenseService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SenseRepository	senseRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;


	// Constructors------------------------------------------------------------
	public SenseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Sense findOne(final int senseId) {
		Sense result;

		result = this.senseRepository.findOne(senseId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Sense> findAll() {
		Collection<Sense> result;

		result = this.senseRepository.findAll();

		return result;
	}

	public Sense create(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		Sense result;
		Chorbi principal;
		Calendar today;

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(chorbi.getId() != principal.getId());

		today = Calendar.getInstance();
		today.set(Calendar.MILLISECOND, -10);

		result = new Sense();
		result.setSender(principal);
		result.setRecipient(chorbi);
		result.setMoment(today.getTime());

		return result;
	}

	public Sense save(final Sense sense) {
		Assert.notNull(sense);
		Sense result;
		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(sense.getSender().getId() != principal.getId());

		result = this.senseRepository.save(sense);

		return result;
	}

	public void delete(final Sense sense) {
		Assert.notNull(sense);
		Assert.notNull(this.chorbiService.findByPrincipal());
		Assert.isTrue(sense.getId() != 0);

		this.senseRepository.delete(sense);
	}
}