package com.example.myapplication;

public class Route {
    //route for login
    final String login = "/auth";
    //route for create account
    final String accountCreate = "/accounts/create";
    //this route is authentication for login
    final String loginAuth = "/auth/user";
    //route for update account
    final String accountUpdate = "/accounts/update";
    //route for delete account
    final String accountDelete = "/accounts/delete";

    //ROUTES FOR ACCOUNT PROFILES
    //create information
    final String informationCreate = "/informations/create";
    //retrieve account info
    final String informationRetrieve = "/informations/:account_id";         //WARNING: THIS IS A GET ROUTE
    //update account info
    final String informationUpdate = "/informations/update";
    //delete account info
    final String informationDelete = "/informations/delete/:account_id";    //WARNING: THIS IS A GET ROUTE

    //ROUTES FOR ACCOUNT STATUS
    //create status
    final String statusCreate = "/status/create";
    //retrieve status
    final String statusRetrieve = "/status/:account_id";                    //WARNING: THIS IS A GET ROUTE
    //update status
    final String statusUpdate = "/status/update";
    //delete status
    final String statusDelete = "/status/delete/:account_id";               //WARNING: THIS IS A GET ROUTE

    final String image = "/images";
}
