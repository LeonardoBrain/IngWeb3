package ar.edu.iua.ingweb3.model.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.ingweb3.model.Archivo;


@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Integer> {

}
