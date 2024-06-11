package org.example;

import org.example.entities.Transaction;
import org.assertj.core.api.SoftAssertions;
import org.example.transaction_tools.processors.TransactionProcessor;
import org.example.transaction_tools.readers.LocalCSVTransactionReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TransactionReaderTest {

    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Створення тимчасового CSV-файлу
        tempFile = Files.createTempFile("transactions", ".csv");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("07-12-2023,-1380,Бензин\n");
            writer.write("09-12-2023,-1000,Аптека\n");
            writer.write("10-12-2023,80000,Зарплата\n");
            writer.write("15-12-2023,15000,Премія\n");
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Видалення тимчасового файлу
        Files.delete(tempFile);
    }

    @Test
    public void testReadTransactions() {
        // Читання даних із тимчасового CSV-файлу
        LocalCSVTransactionReader reader = new LocalCSVTransactionReader();
        String transactionsString = reader.readTransactions(tempFile.toString());

        // Використання SoftAssertions
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(transactionsString).isNotNull();
        softly.assertThat(transactionsString).isEqualTo("07-12-2023,-1380,Бензин\n09-12-2023,-1000,Аптека\n10-12-2023,80000,Зарплата\n15-12-2023,15000,Премія\n");

        softly.assertAll();
    }

    @Test
    public void testProcessTransactions() {
        // Читання даних із тимчасового CSV-файлу
        LocalCSVTransactionReader reader = new LocalCSVTransactionReader();
        String transactionsString = reader.readTransactions(tempFile.toString());

        // Обробка зчитаних даних
        TransactionProcessor processor = new TransactionProcessor();
        List<Transaction> transactions = processor.processTransactions(transactionsString);

        // Використання SoftAssertions
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(transactions).isNotNull();
        softly.assertThat(transactions).hasSize(4);

        softly.assertAll();
    }

    @Test
    public void testIndividualTransaction() {
        // Читання даних із тимчасового CSV-файлу
        LocalCSVTransactionReader reader = new LocalCSVTransactionReader();
        String transactionsString = reader.readTransactions(tempFile.toString());

        // Обробка зчитаних даних
        TransactionProcessor processor = new TransactionProcessor();
        List<Transaction> transactions = processor.processTransactions(transactionsString);

        // Використання SoftAssertions
        SoftAssertions softly = new SoftAssertions();

        Transaction t1 = transactions.get(0);
        softly.assertThat(t1.getDate()).isEqualTo("07-12-2023");
        softly.assertThat(t1.getAmount()).isEqualTo(-1380);
        softly.assertThat(t1.getDescription()).isEqualTo("Бензин");

        Transaction t2 = transactions.get(1);
        softly.assertThat(t2.getDate()).isEqualTo("09-12-2023");
        softly.assertThat(t2.getAmount()).isEqualTo(-1000);
        softly.assertThat(t2.getDescription()).isEqualTo("Аптека");

        Transaction t3 = transactions.get(2);
        softly.assertThat(t3.getDate()).isEqualTo("10-12-2023");
        softly.assertThat(t3.getAmount()).isEqualTo(80000);
        softly.assertThat(t3.getDescription()).isEqualTo("Зарплата");

        Transaction t4 = transactions.get(3);
        softly.assertThat(t4.getDate()).isEqualTo("14-12-2023");
        softly.assertThat(t4.getAmount()).isEqualTo(15000);
        softly.assertThat(t4.getDescription()).isEqualTo("Премія");

        softly.assertAll();
    }
}
