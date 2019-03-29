package Controller;

import Model.AllUsers;
import Model.Customer;
import Model.Owner;

public class ChangePassword {
    private static AllUsers allUser;
    private static String userName,passWord, npassWord;
    private static Owner owners = null;
    private static Customer customer = null;

    public static boolean ChangePassword(String _userName, String _passWord, String _npassWord, AllUsers _allUser){
        userName = _userName;
        passWord = _passWord;
        npassWord = _npassWord;
        allUser = _allUser;

        owners = allUser.getOwnerBasedOnID(_userName);
        customer = allUser.getCustomerBasedOnID(_userName);


        // if both of them are null, sign in fail
        // both id haven't sign up yet
        // otherwise, there is only one null
        // check the password belong with the userID in both owner and customer list

        // check the owner list first
        if(owners != null && customer == null){
            if (owners.getPassword().equals(_passWord)){
                owners.setPassword(npassWord);
                return true;
            }
            else {
                // wrong password;
                return false;
            }
        }

        // then check the customer list
        if(owners == null && customer != null){
            if(customer.getPassword().equals(_passWord)){
                return true;
            }
            else {
                // wrong password
                return false;
            }
        }

        // return false if both of them are null
        return false;

    }
}
