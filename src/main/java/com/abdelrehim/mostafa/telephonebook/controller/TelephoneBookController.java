package com.abdelrehim.mostafa.telephonebook.controller;

import com.abdelrehim.mostafa.telephonebook.controller.exception.EntryNotFoundException;
import com.abdelrehim.mostafa.telephonebook.model.TelephoneEntry;
import com.abdelrehim.mostafa.telephonebook.service.TelephoneEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/phoneNumber")
public class TelephoneBookController {

    private final TelephoneEntryService telephoneEntryService;

    @Autowired
    public TelephoneBookController(TelephoneEntryService telephoneEntryService) {
        this.telephoneEntryService = telephoneEntryService;
    }


    /**
     * Creates a new telephone entry.
     *
     * @param   telephoneEntry The entry to create
     * @return  The created entry
     *          BAD_REQUEST if the given body is invalid
     */
    @PostMapping
    public ResponseEntity<TelephoneEntry> createEntry(@Valid @RequestBody TelephoneEntry telephoneEntry) {
        TelephoneEntry returnedTelephoneEntry = telephoneEntryService.save(telephoneEntry);
        try {
            return ResponseEntity
                    .created(new URI("/phoneNumber/" + returnedTelephoneEntry.getId()))
                    .body(returnedTelephoneEntry);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Updates the fields in the specified entry with the specified ID
     *
     * @param telephoneEntry    The telephone entry field values to update
     * @param id                The ID of the telephone entry to update
     * @return  the updated telephone entry
     * @throws  EntryNotFoundException  when a entry is not found with the specified ID
     */
    @PutMapping("/{id}")
    public TelephoneEntry updateEntry(@PathVariable Long id, @Valid @RequestBody TelephoneEntry telephoneEntry) {
        telephoneEntryService.findById(id).orElseThrow(() -> new EntryNotFoundException(id));
        return telephoneEntryService.update(id, telephoneEntry);
    }

    /**
     * Deletes the entry with the specified ID
     *
     * @param   id  The ID of the entry to delete
     * @return  OK  if the delete was successful
     * @throws  EntryNotFoundException  when a entry is not found with the specified ID
     */
    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable Long id) {
        telephoneEntryService.findById(id).orElseThrow(() -> new EntryNotFoundException(id));
        telephoneEntryService.delete(id);
    }

    /**
     * Returns the entry with the specified ID.
     *
     * @param   id  The ID of the entry to retrieve.
     * @return  The entry with the specified ID
     * @throws  EntryNotFoundException  when a entry is not found with the specified ID
     */
    @GetMapping("/{id}")
    public TelephoneEntry getEntry(@PathVariable Long id) {
        return telephoneEntryService.findById(id).orElseThrow(() -> new EntryNotFoundException(id));
    }

    /**
     * Returns all entries in the database.
     *
     * @return All entries in the database.
     */
    @GetMapping
    public ResponseEntity<Iterable<TelephoneEntry>> getVacancies() {

        List<TelephoneEntry> entries = telephoneEntryService.findAll();
        if(entries.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(entries);
        }
    }


}
