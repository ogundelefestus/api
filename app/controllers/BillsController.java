package controllers;

import models.Bill;
import models.Customer;
import models.Identity;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

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
        return ok(buildJsonResponse("success", "Payment made successfully"));
    }

    public Result all() {

        String start = request().getQueryString("start");
        String end = request().getQueryString("end");

        List<Bill> bill = Bill
                .find
                .where()
                .between("billed_date", end, start)
                .findList();

        return ok(Json.toJson(bill));
    }
}