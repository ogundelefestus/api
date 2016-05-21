package controllers;

import models.*;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static services.JsonConcat.concat;


public class CustomersController extends BaseController {

    public Result show(long id) {

        /*String user = "user:" + id;
        String result = jedisPool.getResource().get(user);
        if (result != null) {
            return ok(result);
        }*/
        Customer customer = Customer.find.byId(id);
        Identity identity = Identity.find.byId(id);

        return ok(concat(customer,identity));
    }

    public Result all(Long start, Long end) {

        List<Bill> bill = Bill
                .findDate
                .where()
                .between("billed_date", start, end)
                .findList();

        return ok(Json.toJson(bill));
    }

    public Result payments(Long id) {

        Customer customer = Customer.find.byId(id);
        Identity identity = Identity.find.byId(id);
        List<Bill> bills = Bill.find
                .where()
                .eq("customer_id", id)
                .eq("paid", true)
                .findList();

        return ok(concat(customer,identity,bills));
    }

    public Result debts(Long id) {

        Customer customer = Customer.find.byId(id);
        Identity identity = Identity.find.byId(id);
        List<Bill> bills = Bill.find
                .where()
                .eq("customer_id", id)
                .eq("paid", false)
                .findList();

        return ok(concat(customer,identity,bills));
    }
}