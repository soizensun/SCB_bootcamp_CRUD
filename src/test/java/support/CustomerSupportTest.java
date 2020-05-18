package support;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {
    public static List<Customer> getCustomerList(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kitpavin");
        customer.setLastName("chaiwong");
        customer.setEmail("aaa@gmail.com");
        customer.setPhoneNumber("12321321");
        customer.setAge(22);

        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("kitpavin1");
        customer.setLastName("chaiwong1");
        customer.setEmail("aaa1@gmail.com");
        customer.setPhoneNumber("12321321");
        customer.setAge(23);

        customerList.add(customer);

        return customerList;
    }

    public static List<Customer> getCustomerListSameName(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kitpavin");
        customer.setLastName("chaiwong");
        customer.setEmail("aaa@gmail.com");
        customer.setPhoneNumber("12321321");
        customer.setAge(22);

        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("kitpavin");
        customer.setLastName("chaiwong1");
        customer.setEmail("aaa1@gmail.com");
        customer.setPhoneNumber("12321321");
        customer.setAge(23);

        customerList.add(customer);

        return customerList;
    }

    public static Customer getNewCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Noon");
        customer.setLastName("Bow");
        customer.setEmail("noon@gmail.com");
        customer.setPhoneNumber("1234566");
        customer.setAge(18);

        return customer;
    }

    public static Customer createNewCustomerNoID(){
        Customer customer = new Customer();
        customer.setFirstName("Noon1");
        customer.setLastName("Bow");
        customer.setEmail("noon@gmail.com");
        customer.setPhoneNumber("1234566");
        customer.setAge(18);

        return customer;
    }

    public static Customer getUpdateCustomer(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Noon2");
        customer.setLastName("Bow");
        customer.setEmail("noon@gmail.com");
        customer.setPhoneNumber("1234566");
        customer.setAge(18);

        return customer;
    }
}
