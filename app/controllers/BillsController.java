package controllers;

import models.Bill;
import models.Customer;
import models.Identity;
import play.libs.Json;
import play.mvc.Result;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static services.JsonConcat.concat;

public class BillsController extends BaseController {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    Jedis jedis;

    public Result show(long id) {

        Bill bill = Bill.find.byId(id);
        if (bill == null) {
            return notFound("Bill is not found");
        }
        Customer customer = Customer.find.byId((long) bill.customer_id);
        Identity identity = Identity.find.byId((long) bill.customer_id);

        return ok(concat(customer, identity, bill));
    }

    public Result update(long id) {
        Bill bill = Bill.find.byId(id);
        if (bill == null) {
            return notFound("Bill is not found");
        }
        bill.paid = true;
        bill.payment_date = new java.sql.Date(new java.util.Date().getTime());
        bill.save();

        int[] list = {0, 1, 3, 6, 12};
        Customer customer = Customer.find.byId((long) bill.customer_id);
        Identity identity = Identity.find.byId((long) bill.customer_id);
        jedis = jedisPool.getResource();
        for (int x = 0; x < 5; x++) {

            String debt = "/api/customers/" + bill.customer_id + "/debts/" + list[x];
            String payment = "/api/customers/" + bill.customer_id + "/payments/" + list[x];

            if (list[x] == 0) {
                List<Bill> debts = Bill.find
                        .where()
                        .eq("customer_id", bill.customer_id)
                        .eq("paid", false)
                        .findList();
                jedis.set(debt, concat(customer, identity, debts));

                List<Bill> payments = Bill.find
                        .where()
                        .eq("customer_id", bill.customer_id)
                        .eq("paid", true)
                        .findList();
                jedis.set(payment, concat(customer, identity, payments));

            } else {
                List<Bill> getLastBill = Bill
                        .find
                        .where()
                        .eq("customer_id", bill.customer_id)
                        .orderBy("id desc")
                        .findList();

                Date dateToday = getLastBill.get(0).billed_date;
                Date pastDate = null;
                c.setTime(dateToday);

                if (list[x] == 1) {
                    c.add(Calendar.MONTH, -0);
                } else if (list[x] == 3) {
                    c.add(Calendar.MONTH, -2);
                } else if (list[x] == 6) {
                    c.add(Calendar.MONTH, -5);
                } else if (list[x] == 12) {
                    c.add(Calendar.MONTH, -11);
                } else {
                    break;
                }
                pastDate = c.getTime();
                String today = dateFormat.format(dateToday);
                String past = dateFormat.format(pastDate);

                List<Bill> debts = Bill
                        .find
                        .where()
                        .eq("customer_id", bill.customer_id)
                        .eq("paid", false)
                        .between("billed_date", past, today)
                        .findList();

                jedis.set(debt, concat(customer, identity, debts));


                List<Bill> payments = Bill
                        .find
                        .where()
                        .eq("customer_id", bill.customer_id)
                        .eq("paid", true)
                        .between("billed_date", past, today)
                        .findList();

                jedis.set(payment, concat(customer, identity, payments));
            }
        }
        jedisPool.returnResource(jedis);
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