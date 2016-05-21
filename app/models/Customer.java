package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.format.Formats;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="customers")
public class Customer extends Model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column(name = "identity_id")
    public int identity_id;

    @Column(name = "register_date")
    @Formats.DateTime(pattern = "dd/MM/yy")
    public java.sql.Date register_date;

    @Column(name = "status")
    public boolean status;

    @Column(name = "closing_date")
    @Formats.DateTime(pattern = "dd/MM/yy")
    public java.sql.Date closing_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "created_at")
    public Date created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "updated_at")
    public Date updated_at;

    public static final Finder<Long, Customer> find = new Finder<>(
            Long.class, Customer.class);

}