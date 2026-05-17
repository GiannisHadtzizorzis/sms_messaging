package dev.giannishadjizorzis;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MessageRepository {

    @Inject
    EntityManager em;

    public void persist(Message message) {
        em.persist(message);
    }

    public Optional<Message> findById(UUID id) {
        return Optional.ofNullable(em.find(Message.class, id));
    }

    public List<Message> findAll() {
        return em.createQuery("SELECT m FROM Message m", Message.class)
                .getResultList();
    }

    public List<Message> findBySenderNumber(String senderNumber) {
        return em.createQuery("SELECT m FROM Message m WHERE m.senderNumber = :sender", Message.class)
                .setParameter("sender", senderNumber)
                .getResultList();
    }

    public List<Message> findByReceiverNumber(String receiverNumber) {
        return em.createQuery("SELECT m FROM Message m WHERE m.receiverNumber = :receiver", Message.class)
                .setParameter("receiver", receiverNumber)
                .getResultList();
    }
}