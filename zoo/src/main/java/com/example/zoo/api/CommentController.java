package com.example.zoo.api;

import com.example.zoo.model.Animal;
import com.example.zoo.model.Comment;
import com.example.zoo.service.AnimalService;
import com.example.zoo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/animals")
public class CommentController {

    private final CommentService commentService;
    private final AnimalService animalService;

    @Autowired
    public CommentController(CommentService commentService, AnimalService animalService) {
        this.commentService = commentService;
        this.animalService = animalService;
    }

    @GetMapping("{id}/comments")
    public List<Comment> getCommentsByAnimalId(@PathVariable("id") UUID id) {
        return commentService.getCommentsByAnimalId(id);
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<byte[]> addComment(@PathVariable("id") UUID id, @RequestBody Comment comment) {
        Optional<Animal> animalMaybe = animalService.getAnimalById(id);
        if(animalMaybe.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        Animal animal = animalMaybe.get();
        //set the user with jwt

        comment.setAnimal(animal);
        if(commentService.addComment(comment) == 0)
            return ResponseEntity.status(NOT_ACCEPTABLE).body("Comment not added".getBytes());
        return ResponseEntity.status(CREATED).body("Comment added".getBytes());
    }

    @DeleteMapping("{id}/comments/{commentId}")
    public ResponseEntity<byte[]> deleteMyComment(@PathVariable("id") UUID id, @PathVariable("commentId") UUID commentId) {
        Optional<Animal> animalMaybe = animalService.getAnimalById(id);
        if(animalMaybe.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());

        if(commentService.deleteComment(commentId) == 0)
            return ResponseEntity.status(NOT_ACCEPTABLE).body("Comment not deleted".getBytes());
        return ResponseEntity.status(NO_CONTENT).body("Comment deleted".getBytes());
    }

}
