package com.devbooks.sharlan.controller;

import com.devbooks.sharlan.dto.CommentDto;
import com.devbooks.sharlan.entities.Comment;
import com.devbooks.sharlan.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/catalog/offers")
@Api(value = "/api/v1/catalog/offers", description = "Manage offers comments")
public class CommentController {

    private ModelMapper modelMapper;
    private CommentService service;

    @ApiOperation(value = "Return set of a comments by offer id")
    @RequestMapping(value = "/{offerId}/comments", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<CommentDto> getCommentsByOfferId(@PathVariable long offerId) {
        log.info("Searching for comments by offer id: " + offerId);
        Set<Comment> foundComments = service.findByOfferId(offerId);
        log.info("Found comments: " + foundComments);
        return foundComments.stream()
                .map(comment -> convertToDto(comment))
                .collect(Collectors.toSet());
    }

    @ApiOperation(value = "Return single comment by id")
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentById(@PathVariable long id) {
        log.info("Searching for comment by id: " + id);
        Comment foundComment = service.findById(id);
        log.info("Found comment: " + foundComment);
        return convertToDto(foundComment);
    }

    @ApiOperation(
            value = "Create Comment",
            notes = "Required comment instance"
    )
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(
            @ApiParam(value = "Comment instance", required = true)
            @Valid @RequestBody CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        log.info("Saving comment: " + comment);
        Comment savedComment = service.save(comment);
        log.info("Saved comment id: " + comment.getId());
        return convertToDto(savedComment);
    }

    @ApiOperation(
            value = "Delete comment",
            notes = "Required comment id"
    )
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(
            @ApiParam(value = "Comment id", required = true)
            @PathVariable long id) {
        log.info("Deleting comment with id: " + id);
        service.delete(id);
    }

    @ApiOperation(
            value = "Update comment",
            notes = "Required comment instance"
    )
    @RequestMapping(value = "/comments", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(
            @ApiParam(value = "Comment instance", required = true)
            @Valid @RequestBody CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        log.info("Updating comment: " + comment);
        Comment updatedComment = service.update(comment);
        log.info("Updated comment: " + updatedComment);
        return convertToDto(updatedComment);
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Comment convertToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

}
