package controllers;

import models.Bill;
import models.Customer;
import models.Identity;
import play.mvc.Result;

import static services.JsonConcat.concat;

public class BillsController extends BaseController {

    public Result show(long id) {

        Bill bill = Bill.find.byId(id);
        Customer customer = Customer.find.byId((long) bill.customer_id);
        Identity identity = Identity.find.byId((long) bill.customer_id);

        return ok(concat(customer,identity,bill));
    }

    public Result update (long id) {
        Bill bill = Bill.find.byId(id);
        bill.paid = true;
        bill.payment_date = new java.sql.Date (new java.util.Date().getTime());
        bill.save();
        return ok(buildJsonResponse("update", "Users updated successfully"));
    }
}


