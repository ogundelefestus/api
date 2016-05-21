package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import services.JsonDateSerializer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="identities")
public class Identity extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column(name = "citizen_no")
    public Long citizen_no;

    @Column(name = "full_name")
    public String full_name;

    @Column (name = "birthdate")
    @JsonSerialize(using = JsonDateSerializer.class)
    public java.sql.Date birthdate;

    @Column(name = "place_of_birth")
    public Integer place_of_birth;

    @Column(name = "father_name")
    public String father_name;

    @Column(name = "mother_name")
    public String mother_name;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public Date created_at;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public Date updated_at;


    public static final Model.Finder<Long, Identity> find = new Model.Finder<>(
            Long.class, Identity.class);
}