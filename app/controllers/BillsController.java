package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Bill;
import models.Customer;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


import static services.JsonNodeMerge.merge;


public class BillsController extends Controller {


    public Result show(long id) {

        Bill bill = Bill.find.byId(id);

        Customer customer = Customer.find
                .fetch("identity")
                .where()
                .eq("id",bill.customerId)
                .findUnique();

        JsonNode billJson = Json.toJson(bill);
        JsonNode billCustomer = Json.toJson(customer);

        return ok(merge(billJson,billCustomer));
    }



}


