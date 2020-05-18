package controller;

import com.digitalacademy.customer.controller.CustomerController;
import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import support.CustomerSupportTest;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomerControllerTest.class)
public class CustomerControllerTest {

    private MockMvc mvc;
    private static final String urlCustomerList = "/customer/list/";
    private static final String urlCustomer = "/customer/";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test get customer should return customer list")
    @Test
    void testGetCustomerList() throws Exception{
        when(customerService.getCustomerList()).thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("1", jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("kitpavin", jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("chaiwong", jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("12321321", jsonArray.getJSONObject(0).get("phoneNumber"));
        assertEquals("aaa@gmail.com", jsonArray.getJSONObject(0).get("email"));
        assertEquals(22, jsonArray.getJSONObject(0).get("age"));

        assertEquals("2", jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("kitpavin1", jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("chaiwong1", jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("12321321", jsonArray.getJSONObject(1).get("phoneNumber"));
        assertEquals("aaa1@gmail.com", jsonArray.getJSONObject(1).get("email"));
        assertEquals(23, jsonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by ID should return customer")
    @Test
    void testGetCustomerByID() throws Exception{
        Long reqId = 1L;
        when(customerService.getCustomerId(reqId)).thenReturn(CustomerSupportTest.getNewCustomer());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "/" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals("1", json.get("id").toString());
        assertEquals("Noon", json.get("firstName"));
        assertEquals("Bow", json.get("lastName"));
        assertEquals("1234566", json.get("phoneNumber"));
        assertEquals("noon@gmail.com", json.get("email"));
        assertEquals(18, json.get("age"));
    }

    @DisplayName("Test get customer by ID should return customer not founr")
    @Test
    void testGetCustomerByIdNotFound() throws Exception{
        Long reqId = 5L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer + "/" + reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("test get customer by id name return customer ")
    @Test
    void testGetCustomerByName() throws Exception {
        String reqName = "kitpavin";

        when(customerService.getCustomerName(reqName)).thenReturn(CustomerSupportTest.getCustomerListSameName());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "?name=" + reqName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("kitpavin",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("chaiwong",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("aaa@gmail.com",jsonArray.getJSONObject(0).get("email"));
        assertEquals("12321321",jsonArray.getJSONObject(0).get("phoneNumber"));
        assertEquals("22",jsonArray.getJSONObject(0).get("age").toString());

        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("kitpavin",jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("chaiwong1",jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("aaa1@gmail.com",jsonArray.getJSONObject(1).get("email"));
        assertEquals("12321321",jsonArray.getJSONObject(1).get("phoneNumber"));
        assertEquals("23",jsonArray.getJSONObject(1).get("age").toString());

    }

    @DisplayName("Test get customer by ID should return customer")
    @Test
    void testGetCustomerByNameNotFound() throws Exception{
        String reqName = "kitpavin";
        mvc.perform(get(urlCustomer + "?name=" + reqName))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test get customer is empty")
    @Test
    void testGetCustomerIsEmpty() throws Exception{
        String reqName = "dss";
        when(customerService.getCustomerName(reqName)).thenReturn(null);

        MvcResult mvcResult = mvc.perform(get(urlCustomer +"?name="+ reqName))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test create customer should return SUCCESS")
    @Test
    void testCreateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.createNewCustomerNoID();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer)).thenReturn(CustomerSupportTest.getNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals("1", json.get("id").toString());
        assertEquals("Noon", json.get("firstName"));
        assertEquals("Bow", json.get("lastName"));
        assertEquals("1234566", json.get("phoneNumber"));
        assertEquals("noon@gmail.com", json.get("email"));
        assertEquals(18, json.get("age"));

        verify(customerService, times(1)).createCustomer(reqCustomer);
    }

    @DisplayName("Test create customer with first name is empty should")
    @Test
    void testCreateCustomerWithNameEmpty() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getNewCustomer();
        reqCustomer.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer)).thenReturn(CustomerSupportTest.getNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitalacademy.customer.controller.CustomerController.createCustomer(com.digitalacademy.customer.model.Customer): [Field error in object 'customer' on field 'firstName': rejected value []; codes [Size.customer.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstName,firstName]; arguments []; default message [firstName],100,1]; default message [Please type your first name size between 1-100]] ", mvcResult.getResolvedException().getMessage());
    }

    @DisplayName("Test update customer should return SUCCESS")
    @Test
    void testUpdateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getNewCustomer();
        Long reqId = 1L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer)).thenReturn(CustomerSupportTest.getUpdateCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals("1", json.get("id").toString());
        assertEquals("Noon2", json.get("firstName"));
        assertEquals("Bow", json.get("lastName"));
        assertEquals("1234566", json.get("phoneNumber"));
        assertEquals("noon@gmail.com", json.get("email"));
        assertEquals(18, json.get("age"));

        verify(customerService, times(1)).updateCustomer(reqId, reqCustomer);
    }

    @DisplayName("Test create customer should return ID not found")
    @Test
    void testUpdateCustomerIdNotFound() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getNewCustomer();
        Long reqId = 1L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer)).thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(customerService, times(1)).updateCustomer(reqId, reqCustomer);
    }

    @DisplayName("Test create customer with path id in not sent")
    @Test
    void testUpdateCustomerWithPathIsNotSent() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getNewCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = ow.writeValueAsString(reqCustomer);

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @DisplayName("Test delete customer should SUCCESS")
    @Test
    void testDeleteCustomer() throws Exception{
        Long reqId = 4L;

        when(customerService.deleteCustomer(reqId)).thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(customerService, times(1)).deleteCustomer(reqId);
    }

    @DisplayName("Test delete customer are not found")
    @Test
    void testDeleteIsNotFound() throws Exception{
        Long reqId = 4L;

        when(customerService.deleteCustomer(reqId)).thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        verify(customerService, times(1)).deleteCustomer(reqId);
    }

}
