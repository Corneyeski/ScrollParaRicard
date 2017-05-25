package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Scroll.
 */
@Entity
@Table(name = "scroll")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Scroll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prueba", nullable = false)
    private String prueba;

    @Column(name = "prueba_2")
    private Integer prueba2;

    @Column(name = "prueba_3")
    private String prueba3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrueba() {
        return prueba;
    }

    public Scroll prueba(String prueba) {
        this.prueba = prueba;
        return this;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public Integer getPrueba2() {
        return prueba2;
    }

    public Scroll prueba2(Integer prueba2) {
        this.prueba2 = prueba2;
        return this;
    }

    public void setPrueba2(Integer prueba2) {
        this.prueba2 = prueba2;
    }

    public String getPrueba3() {
        return prueba3;
    }

    public Scroll prueba3(String prueba3) {
        this.prueba3 = prueba3;
        return this;
    }

    public void setPrueba3(String prueba3) {
        this.prueba3 = prueba3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scroll scroll = (Scroll) o;
        if (scroll.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, scroll.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Scroll{" +
            "id=" + id +
            ", prueba='" + prueba + "'" +
            ", prueba2='" + prueba2 + "'" +
            ", prueba3='" + prueba3 + "'" +
            '}';
    }
}
