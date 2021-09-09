package com.financial.domain.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import static com.financial.domain.model.Budget.*;

@Table(name = TABLE_NAME)
@Entity
public class Budget implements Serializable {

    public static final String TABLE_NAME = "Budget";

    @Id
    @Getter
    @Setter
    @NotNull
    private Integer year = null;

    @Getter
    @Setter
    private Long amount = null;

    public Budget() {
    }

    public Budget(Integer year, Long amount) {
        this.year = year;
        this.amount = amount;
    }

}
