package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.*;


public class AuthController extends BaseController {

    public Result signup() {
        Form<User> userForm = Form.form(User.class);

        if (userForm.hasErrors()) {
            return badRequest(userForm.errorsAsJson());
        }

        User newUser = userForm.bindFromRequest().get();
        newUser.created_at =  new java.sql.Timestamp ( new java.util.Date().getTime());
        newUser.updated_at = new java.sql.Timestamp ( new java.util.Date().getTime());
        newUser.save();
        session("email", newUser.email);

        return ok(buildJsonResponse("success", "Users created successfully"));
    }

    public Result update(long id) {

        DynamicForm form = Form.form().bindFromRequest();
        User user = User.find.byId(id);
        user.setEmail(form.get("email"));
        user.full_name = form.get("full_name");
        if (form.get("password") != null){
            user.setPassword(form.get("password"));
        }
        user.updated_at = new java.sql.Timestamp ( new java.util.Date().getTime());
        user.save();

        return ok(buildJsonResponse("success", "Users updated successfully"));
    }

    public Result login() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }
        Login loggingInUser = loginForm.get();
        User user = User.findByEmailAndPassword(loggingInUser.email, loggingInUser.password);
        if(user == null) {
            return badRequest(buildJsonResponse("error", "Incorrect email or password"));
        } else {
            session().clear();
            session("email", loggingInUser.email);

            ObjectNode wrapper = Json.newObject();
            ObjectNode msg = Json.newObject();
            msg.put("message", "Logged in successfully");
            msg.put("user", loggingInUser.email);
            msg.put("id", user.id);
            msg.put("full_name", user.full_name);
            msg.put("admin", user.admin);
            wrapper.put("success", msg);
            return ok(wrapper);
        }
    }

    public Result logout() {
        session().clear();
        return ok(buildJsonResponse("success", "Logged out successfully"));
    }

    public Result isAuthenticated() {
        if(session().get("email") == null) {
            return unauthorized();
        } else {
            return ok(buildJsonResponse("Users is logged in already", session().get("email")));
        }
    }

    public static class Login {
        @Constraints.Required
        public String password;

        @Constraints.Required
        @Constraints.Email
        public String email;
    }
}