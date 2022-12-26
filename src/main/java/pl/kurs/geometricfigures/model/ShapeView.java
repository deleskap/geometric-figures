package pl.kurs.geometricfigures.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Immutable
@Getter
@Setter
@Subselect("SELECT * FROM shape_view")
public class ShapeView {
    @Column(name = "type")
    private String type;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "radius")
    private Double radius;

    @Column(name = "area")
    private Double area;

    @Column(name = "perimeter")
    private Double perimeter;

    @Column(name = "version")
    private int version;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @Column(name = "last_modified_by_id")
    private Long lastModifiedById;

    public ShapeView() {
    }

}