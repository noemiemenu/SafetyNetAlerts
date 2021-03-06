package com.safetynet.alerts.unit;

import com.safetynet.alerts.interfaces.IDataLoaderService;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.FirestationsRepository;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import com.safetynet.alerts.service.DataLoaderServiceImpl;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.internal.WhiteboxImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@PrepareForTest(DataLoaderServiceImpl.class)
public class DataLoaderServiceImplTests {

    @Mock
    private PersonsRepository personsRepository;

    @Mock
    private FirestationsRepository firestationsRepository;

    @Mock
    private MedicalRecordsRepository medicalRecordsRepository;



    private IDataLoaderService dataLoaderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dataLoaderService = PowerMockito.spy(new DataLoaderServiceImpl(personsRepository, firestationsRepository, medicalRecordsRepository ));

    }

    @Test
    public void testReadJsonFile_And_Returns_The_Json() throws Exception {
        JSONObject jsonObject = WhiteboxImpl.invokeMethod(dataLoaderService,"readJsonFile");
        assertNotNull(jsonObject);
        assertNotNull(jsonObject.get("persons"));
        assertNotNull(jsonObject.get("firestations"));
        assertNotNull(jsonObject.get("medicalrecords"));
    }


    @Test
    public void testLoadDataJsonFileOnStartup_Assert_That_PersonRepository_Save_Method_Is_Called() {
        assertDoesNotThrow(() -> dataLoaderService.loadDataJsonFileOnStartup(null));
        verify(personsRepository, atLeastOnce()).save(any(Person.class));
        verify(firestationsRepository, atLeastOnce()).save(any(Firestation.class));
        verify(medicalRecordsRepository, atLeastOnce()).save(any(MedicalRecord.class));
    }

}
