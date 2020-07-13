package com.abdelrehim.mostafa.telephonebook.service;

import com.abdelrehim.mostafa.telephonebook.Utils.TelephoneEntryFactory;
import com.abdelrehim.mostafa.telephonebook.model.TelephoneEntry;
import com.abdelrehim.mostafa.telephonebook.repository.TelephoneEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TelephoneEntryServiceTest {

    @Mock
    private TelephoneEntryRepository telephoneEntryRepository;

    @InjectMocks
    private TelephoneEntryService telephoneEntryService;

    @Test
    void findById() {
        TelephoneEntry telephoneEntry = TelephoneEntryFactory.create();

        doReturn(Optional.of(telephoneEntry)).when(telephoneEntryRepository).findById(telephoneEntry.getId());

        TelephoneEntry result = telephoneEntryService.findById(telephoneEntry.getId()).orElse(null);
        assertThat(result).isEqualTo(telephoneEntry);
    }

    @Test
    void findAll() {
        TelephoneEntry telephoneEntry1 = TelephoneEntryFactory.create();
        TelephoneEntry telephoneEntry2 = TelephoneEntryFactory.create();

        List<TelephoneEntry> entries = Arrays.asList(telephoneEntry1, telephoneEntry2);

        doReturn(entries).when(telephoneEntryRepository).findAll();

        List<TelephoneEntry> result = telephoneEntryService.findAll();

        assertThat(result).isEqualTo(entries);
    }

    @Test
    void update() {
        TelephoneEntry originalTelephoneEntry = TelephoneEntryFactory.create();

        TelephoneEntry newTelephoneEntry = TelephoneEntryFactory.createUnidentified("030 663 7000", "Ordina");

        doReturn(Optional.of(originalTelephoneEntry)).when(telephoneEntryRepository)
                .findById(originalTelephoneEntry.getId());
        doAnswer(i -> i.getArguments()[0]).when(telephoneEntryRepository).save(any(TelephoneEntry.class));

        TelephoneEntry result = telephoneEntryService.update(originalTelephoneEntry.getId(), newTelephoneEntry);

        assertThat(result.getId()).isEqualTo(originalTelephoneEntry.getId());
        assertThat(result.getPhoneNumber()).isEqualTo(newTelephoneEntry.getPhoneNumber());
        assertThat(result.getName()).isEqualTo(newTelephoneEntry.getName());
    }

    @Test
    void save() {
        TelephoneEntry telephoneEntry = TelephoneEntryFactory.create();

        TelephoneEntry result = telephoneEntryService.save(telephoneEntry);
        verify(telephoneEntryRepository, times(1)).save(telephoneEntry);
    }

    @Test
    void delete() {
        Long id = 23L;
        telephoneEntryService.delete(id);
        verify(telephoneEntryRepository, times(1)).deleteById(id);
    }
}