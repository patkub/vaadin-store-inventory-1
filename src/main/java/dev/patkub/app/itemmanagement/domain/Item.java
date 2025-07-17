package dev.patkub.app.itemmanagement.domain;

import dev.patkub.app.base.domain.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

@Entity
@Table(name = "item")
public class Item extends AbstractEntity<Long> {

    public static final int NAME_MAX_LENGTH = 80;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "name", nullable = false, length = NAME_MAX_LENGTH, unique = true)
    @Size(max = NAME_MAX_LENGTH)
    private String name;

    @Column(name = "listed_date")
    private LocalDate listedDate;

    @Column(name = "price")
    private Double price;

    @Override
    public @Nullable Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Nullable LocalDate getListedDate() {
        return listedDate;
    }

    public void setListedDate(@Nullable LocalDate listedDate) {
        this.listedDate = listedDate;
    }

    public @Nullable Double getPrice() {
        return price;
    }

    public void setPrice(@Nullable Double price) {
        this.price = price;
    }
}
