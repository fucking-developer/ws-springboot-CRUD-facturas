package com.jonhdevelop.springbootapifacturas.entity;

import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción es requerida")
    private String descripcion;
    private String observacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<ItemFactura> items;

    public void addItemFactura(ItemFactura item){
        this.items.add(item);
    }

    public Factura(){
        this.items = new ArrayList<>();
    }

    public Double getTotal(){
        Double total = 0.0;
        for (ItemFactura item : items) {
            total += item.calcularImporte();
        }
        return total;
    }


    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    @XmlTransient
    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }
}
