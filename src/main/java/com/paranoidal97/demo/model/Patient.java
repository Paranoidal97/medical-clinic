package com.paranoidal97.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
//Adnotacja Lombok @Data to
// najbardziej popularna adnotacja, łączy w sobie kilka innych:
// @Getter, @Setter, @EqualsAndHashCode, @RequiredArgsConstructor oraz @ToString,
// dlatego poniższe przykłady generują równoważny kod.
@AllArgsConstructor
//Podobnie jak poprzednio tyle, że tutaj generowany jest konstruktor z argumentami, które
// odpowiadają wszystkim polom w klasie. Oznacza to, że jeśli w klasie mamy na przykład pola id i name to w pliku class znajdzie się konstruktor:
//        Item(Long id, String name) {
//        this.id=id;
//        this.name=name;
//        }
@Builder
//W celu implementacji wzorca projektowego builder wystarczy adnotować
// główną klasę adnotacją @Builder, a kompilator wygeneruje statyczną
// zagnieżdżoną klasę buildera oraz niezbędny konstruktor w głównej klasie
public class Patient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;


}
