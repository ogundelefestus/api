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
    public int identityId;

    @Column(name = "register_date")
    @Formats.DateTime(pattern = "dd/MM/yy")
    public java.sql.Date registerDate;

    @Column(name = "status")
    public boolean status;

    @Column(name = "closing_date")
    @Formats.DateTime(pattern = "dd/MM/yy")
    public java.sql.Date closingDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "created_at")
    public Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "updated_at")
    public Date updatedAt;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="identity_id", referencedColumnName="id", unique=true)
    public Identity identity;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="customer")
    public List<Bill> bills;

    public static final Finder<Long, Customer> find = new Finder<>(
            Long.class, Customer.class);

}