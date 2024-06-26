package org.university;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category productCategory;

    @Override
    public String toString() {
        return "Товар{" +
                "id=" + id +
                ", назва='" + name + '\'' +
                ", ціна=" + price +
                ", опис='" + description + '\'' +
                ", категорія='" + productCategory.getName() + '\'' +
                '}';
    }
}