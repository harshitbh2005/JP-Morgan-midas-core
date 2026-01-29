package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionProcessor {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionProcessor(UserRepository userRepository,
                                TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @KafkaListener(topics = "${general.kafka-topic}")
    @Transactional
    public void handle(Transaction tx) {

        UserRecord sender = userRepository.findById(tx.getSenderId());
        UserRecord recipient = userRepository.findById(tx.getRecipientId());

        if (sender == null || recipient == null) {
            return;
        }

        if (sender.getBalance() < tx.getAmount()) {
            return;
        }

        sender.setBalance(sender.getBalance() - tx.getAmount());
        recipient.setBalance(recipient.getBalance() + tx.getAmount());

        transactionRepository.save(
                new TransactionRecord(sender, recipient, tx.getAmount())
        );

        userRepository.save(sender);
        userRepository.save(recipient);
    }
}
