package hr.fer.ruazosa.pointofinterest.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="places", uniqueConstraints =
        { @UniqueConstraint(name = "UniqueNameAndUser", columnNames = {"name", "user_id"})})
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "place_id")
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "location_latitude")
    private Double locationLatitude;

    @Column(name = "location_longitude")
    private Double locationLongitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) { this.user = user; }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) { this.description = description; }

    public void setLatitude(Double lat) {
        this.locationLatitude = lat;
    }

    public void setLongitude(Double lng) {
        this.locationLongitude = lng;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() { return description; }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }
}
