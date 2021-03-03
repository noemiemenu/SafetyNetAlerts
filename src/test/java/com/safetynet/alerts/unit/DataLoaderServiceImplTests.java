package com.safetynet.alerts.unit;

import com.safetynet.alerts.interfaces.IDataLoaderService;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.PersonRepository;
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
    private PersonRepository personRepository;


    private IDataLoaderService dataLoaderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dataLoaderService = PowerMockito.spy(new DataLoaderServiceImpl(personRepository));
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
        verify(personRepository, atLeastOnce()).save(any(Person.class));
    }

}
