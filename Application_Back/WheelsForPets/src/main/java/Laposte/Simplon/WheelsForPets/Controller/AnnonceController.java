package Laposte.Simplon.WheelsForPets.Controller;

import Laposte.Simplon.WheelsForPets.Model.Annonce;
import Laposte.Simplon.WheelsForPets.Repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AnnonceController {



    @Autowired
    private AnnonceRepository annonceRepository;





    /*
     * Methode Get All
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/annonces", produces = "application/json")
    public ResponseEntity<Collection<Annonce>> getAllAnnonces(){
        return new ResponseEntity<Collection<Annonce>>(annonceRepository.findAll(), HttpStatus.OK);
    }


    /*
     * Methode Get Last3
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/annonces/dernieres", produces = "application/json")
    public ResponseEntity<Collection<Annonce>> getLast3(){
        return new ResponseEntity<Collection<Annonce>>(annonceRepository.getLast3Annonces(), HttpStatus.OK);
    }


    /*
     * Methode get par ID
     */
    @RequestMapping(value = "/annonces/{annonceId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAnnonceById(@PathVariable int annonceId){
        Optional<Annonce> annonce;

        try {
            annonce = annonceRepository.findById(annonceId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        if(annonce == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(annonce);
    }



    /*
     * Methode POST
     */
    @RequestMapping(value = "/annonces", method = RequestMethod.POST, produces= "application/json", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> addAnnonce(@RequestBody Annonce annonce){
        Annonce resultAnnonce = null;
        annonce.setDateRedaction(new Date());
        try {
          resultAnnonce = annonceRepository.saveAndFlush(annonce);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resultAnnonce);
    }

    /*
     * Methode PUT
     */

    @PutMapping(value = "/annonces/{annonceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> modifyAnnonce(@RequestBody Annonce annonce) {
        Annonce annonceAmodifier = null;

        try {
            annonceAmodifier = annonceRepository.saveAndFlush(annonce);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(annonceAmodifier);
    }

    /*
     * Methode DELETE
     */
    @RequestMapping(value = "/annonces/{annonceId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAnnonce(@PathVariable Integer annonceId){
        Annonce annonceAsupprimer = null;
        try {
            annonceAsupprimer = annonceRepository.findById(annonceId).get();
            annonceRepository.deleteById(annonceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(annonceAsupprimer);
    }
}