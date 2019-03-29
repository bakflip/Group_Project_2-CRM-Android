package com.example.groupproject;

import org.junit.Test;

import Model.AllUsers;
import Model.Customer;
import Model.Owner;
import Model.RetObject;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//Test for sign up feature methods
public class SignUpTest {

    SignUp signUp;

    public SignUpTest() {
        signUp = new SignUp();
        signUp.allUsersInIt(); // Initialize all user
    }

    //Test Owner SignUp method
    @Test
    public void OwnerSignUpTest() {
        RetObject ret = new RetObject();
        AllUsers allUsers = signUp.getAllUsers();
        assertEquals(false, ret.getBool()); // Test if initial value is false
        assertEquals(0, allUsers.getOwnerSize()); // Test if the list has no owner account
        ret = signUp.signUpAsOwner("Owner", "ownerID", "ownerPassword");
        assertEquals(true, ret.getBool()); //Test if sign up is successfully executed
        assertEquals(1, allUsers.getOwnerSize()); //Test if new account is added to list
        Owner owner = allUsers.getOwnerBasedOnName("Owner");
        assertEquals("ownerID", owner.getID()); // Test if the ID is correct
        assertEquals("ownerPassword", owner.getPassword()); // Test if the password is correct
    }

    //Test Customer SignUp method
    @Test
    public void CustomerSignUpTest() {
        RetObject ret = new RetObject();
        AllUsers allUsers = signUp.getAllUsers();
        allUsers.addOwner(new Owner("owner", "owner","Owner",0));
        assertEquals(false, ret.getBool()); // Test if initial value is false
        assertEquals(0, allUsers.getCustomerSize()); // Test if the list
                                                             // has no customer account
        ret = signUp.signUpAsCustomer("Customer", "custID",
                "custPassword", "Owner");
        assertEquals(true, ret.getBool()); //Test if sign up is successfully executed
        assertEquals(1, allUsers.getCustomerSize()); //Test if new account is added to list
        Customer cust = allUsers.getCustomerBasedOnID("custID");
        assertEquals("Customer", cust.getCustomerName()); // Test if the name is correct
        assertEquals("custPassword", cust.getPassword()); // Test if the password is correct
    }
}