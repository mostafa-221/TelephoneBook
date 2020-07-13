package com.abdelrehim.mostafa.telephonebook.service;

import com.abdelrehim.mostafa.telephonebook.model.TelephoneEntry;

import java.util.List;
import java.util.Optional;

public interface TelephoneEntryService {
    Optional<TelephoneEntry> findById(Long id);

    List<TelephoneEntry> findAll();

    TelephoneEntry update(Long id, TelephoneEntry newTelephoneEntry);

    TelephoneEntry save(TelephoneEntry telephoneEntry);

    void delete(Long id);
}
