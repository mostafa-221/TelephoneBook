package com.abdelrehim.mostafa.telephonebook.repository;

import com.abdelrehim.mostafa.telephonebook.model.TelephoneEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelephoneEntryRepository extends JpaRepository<TelephoneEntry, Long> {
}
