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
    public Long citizenNo;

    @Column(name = "full_name")
    public String fullName;

    @Column (name = "birthdate")
    @JsonSerialize(using = JsonDateSerializer.class)
    public java.sql.Date birthdate;

    @Column(name = "place_of_birth")
    public Integer placeOfBirth;

    @Column(name = "father_name")
    public String fatherName;

    @Column(name = "mother_name")
    public String motherName;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public Date updatedAt;


    public static final Model.Finder<Long, Identity> find = new Model.Finder<>(
            Long.class, Identity.class);
}