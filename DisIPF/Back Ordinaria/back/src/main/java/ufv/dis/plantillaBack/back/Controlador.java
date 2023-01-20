package ufv.dis.plantillaBack.back;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@RestController
public class Controlador {
    public ArrayList<IP> ips = new ArrayList<>();

    public Error error = new Error();

    @GetMapping(path = "/IP")
    public ResponseEntity ip() throws FileNotFoundException {
        LectorJSON Leer = new LectorJSON();
        ips = Leer.LectorJSON();
        for(int i = 0; i < ips.size(); i++){
            ips.get(i).setId(i + 1);
        }
        return new ResponseEntity<>(this.ips, HttpStatus.OK);
    }

    @GetMapping("/IP/{ID}")
    public ResponseEntity GetCovid(@PathVariable int ID){
        IP data;
        for (int i = 0; i < ips.size(); i++) {
            if (ips.get(i).getId() == ID) {
                data = ips.get(i);
                return new ResponseEntity<>(data, HttpStatus.OK);
            }
        }
        error.setId(ID);
        error.setMensaje_error("IP no encontrada");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/IP",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevoIP(@RequestBody IP nuevoIP) {
        this.ips.add(nuevoIP);
        int tamanoArrayMenos1 = ips.size()-1;
        int tamanoArrayMenos2 = this.ips.get(tamanoArrayMenos1 - 1).getId();
        this.ips.get(tamanoArrayMenos1).setId(tamanoArrayMenos2 + 1);
        return new ResponseEntity<>(this.ips, HttpStatus.CREATED);
    }

    @PutMapping("/IP/{ID}")
    public ResponseEntity PutIP(@RequestBody IP nuevoIP, @PathVariable int ID){
        for (int i = 0; i < ips.size(); i++){
            if(ips.get(i).getId() == ID){
                ips.set(i, nuevoIP);
                ips.get(i).setId(ID);
                return new ResponseEntity<>(this.ips, HttpStatus.OK);
            }
        }
        error.setId(ID);
        error.setMensaje_error("IP no encontrada");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/IP/{ID}")
    public ResponseEntity DeleteIP(@PathVariable int ID) {
        for (int i = 0; i < ips.size(); i++){
            if(ips.get(i).getId() == ID){
                this.ips.remove(ips.get(i));
                return new ResponseEntity<>(this.ips, HttpStatus.OK);
            }
        }
        error.setId(ID);
        error.setMensaje_error("IP no encontrada");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
