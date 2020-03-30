package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = PetController.class, includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CauseControllerTests {

	private static final int	TEST_CAUSE_ID	= 1;
	
	@Autowired
	private CauseController		causeController;

	@MockBean
	private CauseService			causeService;
	
	@Autowired
	private MockMvc				mockMvc;
	
	private final Cause				cause = new Cause();;
	
	@BeforeEach
	void setup() {

//		this.cause = new Cause();
		this.cause.setId(CauseControllerTests.TEST_CAUSE_ID);
		this.cause.setName("TestCauseName");
		this.cause.setDescription("TestCauseDescription");
		this.cause.setOrganisation("TestCauseOrganisation");
		this.cause.setBudgetTarget(1000.0);
		
		Cause res = this.causeService.findCauseById(CauseControllerTests.TEST_CAUSE_ID).get();
		BDDMockito.given(res).willReturn(this.cause);

	}

	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/new")).andExpect(status().isOk()).andExpect(model().attributeExists("cause"))
			.andExpect(view().name("causes/editCause"));
	}
	
	
	
	
	
	
	
}
