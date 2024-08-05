package com.restAPI.banking_app.entity;


import jakarta.persistence.*;
import lombok.*;
import java.security.SecureRandom;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cards")
@Entity
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long card_id;

    @PrePersist
    public void generateCardDetails() {
        generateCardNumber();
        generateCVV();
    }


    private void generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 12; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        this.cardNumber = sb.toString();
    }

    private void generateCVV() {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        this.cvv = sb.toString();
    }

    @Column(name = "Card_Number")
    private String cardNumber;
    @Column(name = "Expiration_Date")
    private String expirationDate;
    @Column(name = "CVV")
    private String cvv;
    @Column(name = "Card_Type")
    private String cardType;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

   /* private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            cardNumber.append(random.nextInt(9) + 1); // Generate digits from 1 to 9
        }
        return cardNumber.toString();
    }

    private String generateCVV() {
        Random random = new Random();
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(9) + 1); // Generate digits from 1 to 9
        }
        return cvv.toString();
    }*/
}
