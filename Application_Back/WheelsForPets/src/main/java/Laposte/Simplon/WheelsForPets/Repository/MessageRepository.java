package Laposte.Simplon.WheelsForPets.Repository;

import Laposte.Simplon.WheelsForPets.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;




public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query(value = "SELECT * FROM messages WHERE (annonceId = ?1)", nativeQuery = true)
    Collection<Message>findByIdAnnonce(int identifiant);

}
