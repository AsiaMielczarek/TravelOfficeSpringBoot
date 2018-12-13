package traveloffice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import traveloffice.repository.CustomerRepository;
import traveloffice.repository.TripRepository;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TripRepository tripRepository;

    @Before
    public void deleteAllBeforeTests() throws Exception {
        customerRepository.deleteAll();
        tripRepository.deleteAll();
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        mockMvc.perform(post("/customers")
                .content("{\"firstName\": \"Mc\", \"lastName\":\"Mock\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("customers/")));
    }

    @Test
    public void shouldRetriveCustomers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/customers")
                .content("{\"firstName\": \"Mc\", \"lastName\":\"Mock\"}"))
                .andExpect(status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mc"))
                .andExpect(jsonPath("$.lastName").value("Mock"));
    }

    @Test
    public void shouldRemoveCustomer() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/customers")
                .content("{\"firstName\": \"Mc\", \"lastName\":\"Mock\"}"))
                .andExpect(status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateTrip() throws Exception {
        mockMvc.perform(post("/trips")
                .content("{\"startDate\": \"2.4.2019\", \"endDate\":\"14.4.2019\", \"destination\": \"Bali\", \"price\":\"2499\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("trips/")));
    }

    @Test
    public void shouldRetriveTrips() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/trips")
                .content("{\"startDate\": \"2.4.2019\", \"endDate\":\"14.4.2019\", \"destination\": \"Bali\", \"price\":\"2499.0\"}"))
                .andExpect(status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate").value("2.4.2019"))
                .andExpect(jsonPath("$.endDate").value("14.4.2019"))
                .andExpect(jsonPath("$.destination").value("Bali"))
                .andExpect(jsonPath("$.price").value("2499.0"));
    }

    @Test
    public void shouldRemoveTrip() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/trips")
                .content("{\"startDate\": \"2.4.2019\", \"endDate\":\"14.4.2019\", \"destination\": \"Bali\", \"price\":\"2499\"}"))
                .andExpect(status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }


}