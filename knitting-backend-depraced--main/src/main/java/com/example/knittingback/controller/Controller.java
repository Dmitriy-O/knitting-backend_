package com.example.knittingback.controller;

import com.example.knittingback.model.*;
import com.example.knittingback.services.Service;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class Controller {

    //The @Autowired annotation tells Spring Boot to inject the Service dependency into the Controller class. The Service class is responsible for interacting with the database.
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }



    @PostMapping("/items")
    public com.example.knittingback.entity.ItemEntity createItem(@RequestPart MultipartFile image, @RequestPart String description, @RequestPart String price, @RequestPart String name) throws IOException {

        return service.createItem(image, description,price,name);
    }

    @PostMapping("/order")
    public Order createOrder() {
        Order order = new Order().builder().date(LocalDateTime.now()).build();
        return service.createOrder(order);
    }

    @PostMapping("/client")
    public Client createClient(@RequestBody Client client) {
        return service.createClient(client);
    }

    @GetMapping(
            value = "/images/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody ByteArrayResource getImageWithMediaType(@PathVariable("id") long id) throws IOException {
        FileEntity imageData = service.downloadImageService(id);
//    Path path = Paths.get(getClass().getResource(imageData.getFilePath()).toURI());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Path.of(imageData.getFilePath())));

        return resource;
    }



    @GetMapping("/items")
    public List<ItemEntity> getAllItems() {
        return service.get_All_Items();
    }

    @GetMapping("/images")
    public List<FileEntity> downloadAllimages() {
        return service.getAllImages();

    }


    @DeleteMapping("/images/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteModels(@PathVariable("id") long id) {
        boolean isDeleted = false;
        isDeleted = service.deleteModel(id);
        return null;
    }
}
