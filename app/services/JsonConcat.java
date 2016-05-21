package services;

import com.google.gson.*;
import models.Bill;
import models.Customer;
import models.Identity;

import java.util.List;

public class JsonConcat {

    static Gson gson = new GsonBuilder().create();

    public static String concat (Customer customer, Identity identity) {

        JsonObject customerJson = new JsonObject();
        customerJson.addProperty("id", customer.id);
        customerJson.addProperty("identity_id", customer.identity_id);
        customerJson.addProperty("register_date", customer.register_date+"");
        customerJson.addProperty("status", customer.status);
        customerJson.addProperty("closing_date", customer.closing_date+"");
        customerJson.addProperty("created_at", customer.created_at+"");
        customerJson.addProperty("updated_at", customer.updated_at+"");

        JsonObject identityJson = new JsonObject();
        identityJson.addProperty("id", identity.id);
        identityJson.addProperty("citizen_no", identity.citizen_no);
        identityJson.addProperty("full_name", identity.full_name);
        identityJson.addProperty("birthdate", identity.birthdate+"");
        identityJson.addProperty("place_of_birth", identity.place_of_birth);
        identityJson.addProperty("father_name", identity.father_name);
        identityJson.addProperty("mother_name", identity.mother_name);
        identityJson.addProperty("created_at", identity.created_at+"");
        identityJson.addProperty("updated_at", identity.updated_at+"");

        customerJson.add("identity", identityJson);

        String result = gson.toJson(customerJson);
        return result;

    }

    public static String concat (Customer customer, Identity identity, List<Bill> bills) {

        JsonObject customerJson = new JsonObject();
        customerJson.addProperty("id", customer.id);
        customerJson.addProperty("identity_id", customer.identity_id);
        customerJson.addProperty("register_date", customer.register_date+"");
        customerJson.addProperty("status", customer.status);
        customerJson.addProperty("closing_date", customer.closing_date+"");
        customerJson.addProperty("created_at", customer.created_at+"");
        customerJson.addProperty("updated_at", customer.updated_at+"");

        JsonObject identityJson = new JsonObject();
        identityJson.addProperty("id", identity.id);
        identityJson.addProperty("citizen_no", identity.citizen_no);
        identityJson.addProperty("full_name", identity.full_name);
        identityJson.addProperty("birthdate", identity.birthdate+"");
        identityJson.addProperty("place_of_birth", identity.place_of_birth);
        identityJson.addProperty("father_name", identity.father_name);
        identityJson.addProperty("mother_name", identity.mother_name);
        identityJson.addProperty("created_at", identity.created_at+"");
        identityJson.addProperty("updated_at", identity.updated_at+"");

        customerJson.add("identity", identityJson);

        JsonArray billsJsonArray = new JsonArray();

        for (int i = 0; i<bills.size(); i++) {
            JsonObject itemObj = new JsonObject();
            itemObj.addProperty("id", bills.get(i).id);
            itemObj.addProperty("customer_id", bills.get(i).customer_id);
            itemObj.addProperty("billed_date", bills.get(i).billed_date+"");
            itemObj.addProperty("cost", bills.get(i).cost);
            itemObj.addProperty("taxes", bills.get(i).taxes);
            itemObj.addProperty("paid", bills.get(i).paid);
            itemObj.addProperty("payment_date", bills.get(i).payment_date+"");
            itemObj.addProperty("created_at", bills.get(i).created_at+"");
            itemObj.addProperty("updated_at", bills.get(i).updated_at+"");
            billsJsonArray.add(itemObj);
        }

        customerJson.add("bills", billsJsonArray);
        String result = gson.toJson(customerJson);
        return result;
    }

    public static String concat (Customer customer,Identity identity, Bill bills) {

        JsonObject customerJson = new JsonObject();
        customerJson.addProperty("id", customer.id);
        customerJson.addProperty("identity_id", customer.identity_id);
        customerJson.addProperty("register_date", customer.register_date+"");
        customerJson.addProperty("status", customer.status);
        customerJson.addProperty("closing_date", customer.closing_date+"");
        customerJson.addProperty("created_at", customer.created_at+"");
        customerJson.addProperty("updated_at", customer.updated_at+"");

        JsonObject identityJson = new JsonObject();
        identityJson.addProperty("id", identity.id);
        identityJson.addProperty("citizen_no", identity.citizen_no);
        identityJson.addProperty("full_name", identity.full_name);
        identityJson.addProperty("birthdate", identity.birthdate+"");
        identityJson.addProperty("place_of_birth", identity.place_of_birth);
        identityJson.addProperty("father_name", identity.father_name);
        identityJson.addProperty("mother_name", identity.mother_name);
        identityJson.addProperty("created_at", identity.created_at+"");
        identityJson.addProperty("updated_at", identity.updated_at+"");

        customerJson.add("identity", identityJson);

        JsonObject billJson = new JsonObject();
        billJson.addProperty("id", bills.id);
        billJson.addProperty("customer_id", bills.customer_id);
        billJson.addProperty("billed_date", bills.billed_date+"");
        billJson.addProperty("cost", bills.cost);
        billJson.addProperty("taxes", bills.taxes);
        billJson.addProperty("paid", bills.paid);
        billJson.addProperty("payment_date", bills.payment_date+"");
        billJson.addProperty("created_at", bills.created_at+"");
        billJson.addProperty("updated_at", bills.updated_at+"");

        billJson.add("customer", customerJson);
        String result = gson.toJson(billJson);
        return result;

    }

}
