package com.abdelrehim.mostafa.telephonebook.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryNotFoundException extends RuntimeException{
    public EntryNotFoundException() {
        super("Entry not found");
    }

    public EntryNotFoundException(Long id) {
        super("Could not find entry with id: " + id);
    }
}
