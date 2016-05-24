package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Sql;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import services.JsonDateSerializer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="bills")
public class Bill extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column (name = "customer_id")
    public int customer_id;

    @Column (name = "billed_date")
    @JsonSerialize(using = JsonDateSerializer.class)
    public java.sql.Date billed_date;

    @Column(name = "cost")
    public Double cost;

    @Column (name = "taxes")
    public Double taxes;

    @Column (name = "paid")
    public boolean paid;

    @Column (name = "payment_date")
    @JsonSerialize(using = JsonDateSerializer.class)
    public java.sql.Date payment_date;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public Date created_at;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public Date updated_at;

    public static final Model.Finder<Long, Bill> find = new Model.Finder<>(
            Long.class, Bill.class);

}