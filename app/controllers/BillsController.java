package controllers;

import models.Bill;
import models.Customer;
import models.Identity;
import play.mvc.Controller;
import play.mvc.Result;

import static services.JsonConcat.concat;

public class BillsController extends Controller {

    public Result show(long id) {

        Bill bill = Bill.find.byId(id);
        Customer customer = Customer.find.byId((long) bill.customer_id);
        Identity identity = Identity.find.byId((long) bill.customer_id);

        return ok(concat(customer,identity,bill));
    }
}


