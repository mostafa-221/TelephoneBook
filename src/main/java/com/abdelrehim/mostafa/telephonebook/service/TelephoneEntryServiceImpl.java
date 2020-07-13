package com.abdelrehim.mostafa.telephonebook.service;

import com.abdelrehim.mostafa.telephonebook.controller.exception.EntryNotFoundException;
import com.abdelrehim.mostafa.telephonebook.model.TelephoneEntry;
import com.abdelrehim.mostafa.telephonebook.repository.TelephoneEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelephoneEntryServiceImpl implements TelephoneEntryService {
    private final TelephoneEntryRepository telephoneEntryRepository;

    @Autowired
    public TelephoneEntryServiceImpl(TelephoneEntryRepository telephoneEntryRepository) {
        this.telephoneEntryRepository = telephoneEntryRepository;
    }


    /**
     * Returns the entry with the specified id
     *
     * @param id ID of the entry to retrieve
     * @return An optional of the requested entry if found, and an empty optional otherwise
     */
    @Override
    public Optional<TelephoneEntry> findById(Long id) {
        return telephoneEntryRepository.findById(id);
    }

    /**
     * Returns all entries in the database
     *
     * @return All entries in the database
     */
    @Override
    public List<TelephoneEntry> findAll() {
        return telephoneEntryRepository.findAll();
    }

    /**
     * Updates an entry, identified by its id
     *
     * @param   newTelephoneEntry   The entry with the values to be updated
     * @return  the body of the updated entry
     */
    @Override
    public TelephoneEntry update(Long id, TelephoneEntry newTelephoneEntry) {

        return telephoneEntryRepository.findById(id)
                .map(oldTelephoneEntry -> {
                    if(newTelephoneEntry.getName() != null) oldTelephoneEntry.setName(newTelephoneEntry.getName());
                    if(newTelephoneEntry.getPhoneNumber() != null)
                        oldTelephoneEntry.setPhoneNumber(newTelephoneEntry.getPhoneNumber());

                    return telephoneEntryRepository.save(oldTelephoneEntry);
                }).orElseThrow(() -> new EntryNotFoundException(id));
    }

    /**
     * Saves the specified entry to the database
     *
     * @param   telephoneEntry The entry to save to the database
     * @return  The saved entry
     */
    @Override
    public TelephoneEntry save(TelephoneEntry telephoneEntry) {
        return telephoneEntryRepository.save(telephoneEntry);
    }

    /**
     * Deletes the entry with the specified id
     *
     * @param   id  The id of the entry to delete
     */
    @Override
    public void delete(Long id) {
        telephoneEntryRepository.deleteById(id);
    }

}
