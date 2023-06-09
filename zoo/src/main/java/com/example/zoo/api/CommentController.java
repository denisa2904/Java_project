package com.example.zoo.api;

import com.example.zoo.model.Animal;
import com.example.zoo.model.Comment;
import com.example.zoo.model.CommentHelper;
import com.example.zoo.model.User;
import com.example.zoo.service.AnimalService;
import com.example.zoo.service.CommentService;
import com.example.zoo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/animals")
public class CommentController {

    private final CommentService commentService;
    private final AnimalService animalService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, AnimalService animalService, UserService userService) {
        this.commentService = commentService;
        this.animalService = animalService;
        this.userService = userService;
    }

    @GetMapping("{id}/comments")
    public List<Comment> getCommentsByAnimalId(@PathVariable("id") UUID id) {
        return commentService.getCommentsByAnimalId(id);
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable("id") UUID id, @RequestBody CommentHelper commentHelper,
                                             @NonNull HttpServletRequest request){
        Optional<Animal> animalMaybe = animalService.getAnimalById(id);
        if(animalMaybe.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found");
        Animal animal = animalMaybe.get();
        String username = userService.getUsernameFromJwt(request);
        User user = userService.getUserByUsername(username);

        Comment comment = new Comment();
        comment.setContent(commentHelper.getContent());
        comment.setUser(user);
        comment.setAnimal(animal);
        comment.setCreatedAt(LocalDateTime.now());
        if(commentService.addComment(comment) == 0)
            return ResponseEntity.status(NOT_ACCEPTABLE).body("Comment not added");
        return ResponseEntity.status(CREATED).body("Comment added");
    }

    @DeleteMapping("{id}/comments/{commentId}")
    public ResponseEntity<byte[]> deleteMyComment(@PathVariable("id") UUID id,
                                                  @PathVariable("commentId") UUID commentId,
                                                  @NonNull HttpServletRequest request) {
        Optional<Animal> animalMaybe = animalService.getAnimalById(id);
        if(animalMaybe.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        Animal animal = animalMaybe.get();
        String username = userService.getUsernameFromJwt(request);
        User user = userService.getUserByUsername(username);
        Optional<Comment> commentMaybe = commentService.getCommentById(commentId);
        Comment comment;
        if(commentMaybe.isPresent())
            comment = commentMaybe.get();
        else return ResponseEntity.status(NOT_FOUND).body("Comment not found".getBytes());
        comment.setUser(user);
        comment.setAnimal(animal);
        if(commentService.deleteComment(commentId) == 0)
            return ResponseEntity.status(NOT_ACCEPTABLE).body("Comment not deleted".getBytes());
        return ResponseEntity.status(NO_CONTENT).body("Comment deleted".getBytes());
    }

}
