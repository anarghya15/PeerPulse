package com.spe.peerpulse;

import com.spe.peerpulse.entity.ClassGroup;
import com.spe.peerpulse.exceptions.ResourceNotFoundException;
import com.spe.peerpulse.repository.ClassGroupRepository;
import com.spe.peerpulse.repository.ClassRepository;
import com.spe.peerpulse.entity.Class;
import com.spe.peerpulse.service.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupsTest {

    @Mock
    private ClassRepository classRepository;

    @Mock
    private ClassGroupRepository groupRepository;

    @InjectMocks
    private InstructorService teacherService;

    @BeforeEach
    public void setUp() {
        Class testClass = new Class();
        testClass.setClassId(1L);

        ClassGroup testGroup = new ClassGroup();
        testGroup.setClassGroupId(1L);
        testGroup.setName("Test Group");
        testGroup.setDescription("This is a test group.");
        testGroup.setClassObj(testClass);
    }

    @Test
    public void testAddGroupWithNonExistingClass() {
        when(classRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Class not found"));

        ClassGroup newGroup = new ClassGroup();
        newGroup.setName("New Group");
        newGroup.setDescription("This is a new group.");

        assertThrows(ResourceNotFoundException.class, () -> {
            teacherService.addGroup(newGroup, 1L);
        });
    }
}

