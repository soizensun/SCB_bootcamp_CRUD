package service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import com.digitalacademy.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import support.CustomerSupportTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomerServiceTest.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }


    @DisplayName("Test get all customer should return list")
    @Test
    void testGetAllCustomer(){
        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getCustomerList());
        List<Customer> resp = customerService.getCustomerList();

        assertEquals(1, resp.get(0).getId().intValue());
        assertEquals("kitpavin", resp.get(0).getFirstName());
        assertEquals("chaiwong", resp.get(0).getLastName());
        assertEquals("aaa@gmail.com", resp.get(0).getEmail());
        assertEquals("12321321", resp.get(0).getPhoneNumber());
        assertEquals(22, resp.get(0).getAge().intValue());

        assertEquals(2, resp.get(1).getId().intValue());
        assertEquals("kitpavin1", resp.get(1).getFirstName());
        assertEquals("chaiwong1", resp.get(1).getLastName());
        assertEquals("aaa1@gmail.com", resp.get(1).getEmail());
        assertEquals("12321321", resp.get(1).getPhoneNumber());
        assertEquals(23, resp.get(1).getAge().intValue());
    }

    @DisplayName("Test get all customer by ID should return list")
    @Test
    void testGetAllCustomerByID(){
        Long resParam = 1L;
        when(customerRepository.findAllById(resParam)).thenReturn(CustomerSupportTest.getCustomerList().get(0));
        Customer resp = customerService.getCustomerId(resParam);

        assertEquals(1, resp.getId().intValue());
        assertEquals("kitpavin", resp.getFirstName());
        assertEquals("chaiwong", resp.getLastName());
        assertEquals("aaa@gmail.com", resp.getEmail());
        assertEquals("12321321", resp.getPhoneNumber());
        assertEquals(22, resp.getAge().intValue());
    }


    @DisplayName("Test get all customer by name should return list")
    @Test
    void testGetAllCustomerByName(){
        String name = "kitpavin";
        when(customerRepository.findByFirstName(name)).thenReturn(CustomerSupportTest.getCustomerListSameName());
        List<Customer> resp = customerService.getCustomerName(name);

        assertEquals(1, resp.get(0).getId().intValue());
        assertEquals("kitpavin", resp.get(0).getFirstName());
        assertEquals("chaiwong", resp.get(0).getLastName());
        assertEquals("aaa@gmail.com", resp.get(0).getEmail());
        assertEquals("12321321", resp.get(0).getPhoneNumber());
        assertEquals(22, resp.get(0).getAge().intValue());
    }

//    create customer
    @DisplayName("Test create customer should return new customer")
    @Test
    void testCreateCustomer(){
        Customer reqCustomer = new Customer();

        reqCustomer.setFirstName("Noon");
        reqCustomer.setLastName("Bow");
        reqCustomer.setEmail("noon@gmail.com");
        reqCustomer.setPhoneNumber("1234566");
        reqCustomer.setAge(18);

        when(customerRepository.save(reqCustomer)).thenReturn(CustomerSupportTest.getNewCustomer());
        Customer resp = customerService.createCustomer(reqCustomer);

        assertEquals(1, resp.getId().intValue());
        assertEquals("Noon", resp.getFirstName());
        assertEquals("Bow", resp.getLastName());
        assertEquals("noon@gmail.com", resp.getEmail());
        assertEquals("1234566", resp.getPhoneNumber());
        assertEquals(18, resp.getAge().intValue());
    }

//    update cutomer
    @DisplayName("Test update customer should return SUCCESS")
    @Test
    void testUpdateCustomer(){
        Long reqId = 1L;
        Customer reqCustomer = new Customer();
        reqCustomer.setId(1L);
        reqCustomer.setFirstName("Noon");
        reqCustomer.setLastName("Bow");
        reqCustomer.setEmail("noon@gmail.com");
        reqCustomer.setPhoneNumber("1234566");
        reqCustomer.setAge(18);

        when(customerRepository.findAllById(reqId)).thenReturn(CustomerSupportTest.getNewCustomer());
        when(customerRepository.save(reqCustomer)).thenReturn(CustomerSupportTest.getNewCustomer());
        Customer resp =  customerService.updateCustomer(reqId, reqCustomer);

        assertEquals(1, resp.getId().intValue());
        assertEquals("Noon", resp.getFirstName());
        assertEquals("Bow", resp.getLastName());
        assertEquals("noon@gmail.com", resp.getEmail());
        assertEquals("1234566", resp.getPhoneNumber());
        assertEquals(18, resp.getAge().intValue());
    }

    @DisplayName("Test update customer should return FAIL")
    @Test
    void testUpdateCustomerFail(){
        Long reqId = 4L;
        Customer reqCustomer = new Customer();
        reqCustomer.setId(1L);
        reqCustomer.setFirstName("Noon");
        reqCustomer.setLastName("Bow");
        reqCustomer.setEmail("noon@gmail.com");
        reqCustomer.setPhoneNumber("1234566");
        reqCustomer.setAge(18);

        when(customerRepository.findAllById(reqId)).thenReturn(null);
        Customer resp =  customerService.updateCustomer(reqId, reqCustomer);
        assertEquals(null, resp);
    }


//    delete customer
    @DisplayName("Test delete customer should return true")
    @Test
    void testDeleteCustomer(){
        Long resqId = 1L;
        doNothing().when(customerRepository).deleteById(resqId);
        boolean resp = customerService.deleteCustomer(resqId);
        assertTrue(resp);
    }

    @DisplayName("Test delete customer should return false")
    @Test
    void testDeleteCustomerFalse(){
        Long reqId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(customerRepository).deleteById(reqId);

        boolean resp = customerService.deleteCustomer(reqId);
        assertFalse(resp);
    }

}
