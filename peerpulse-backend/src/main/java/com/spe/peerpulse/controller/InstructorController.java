package com.spe.peerpulse.controller;

import com.spe.peerpulse.entity.*;
import com.spe.peerpulse.entity.Class;
import com.spe.peerpulse.model.response.GenericResponse;
import com.spe.peerpulse.model.request.IdList;
import com.spe.peerpulse.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/class")
    public ResponseEntity<Class> addClass(@RequestBody Class newClass, @RequestParam Long teacherId) {
        instructorService.validateTeacherId(teacherId);

        Class addedClass = instructorService.addClass(newClass, teacherId);
        return new ResponseEntity<>(addedClass, HttpStatus.CREATED);
    }

    @GetMapping("/classes")
    public List<Class> getAllClasses() {
        return instructorService.findAllClasses();
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<Class> getClassById(@PathVariable Long id) {
        instructorService.validateClassId(id);

        Optional<Class> optionalEntity = instructorService.findClassById(id);
        return optionalEntity.map(aClass -> new ResponseEntity<>(aClass, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/class/{id}")
    public ResponseEntity<GenericResponse> deleteClass(@PathVariable Long id) {
        instructorService.validateClassId(id);

        Optional<Class> optionalEntity = instructorService.findClassById(id);
        if (optionalEntity.isPresent()) {
            instructorService.deleteClassById(id);
            return new ResponseEntity<>(new GenericResponse("Class with ID " + id + " deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse("Class with ID " + id + " not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/group")
    public ResponseEntity<ClassGroup> addGroup(@RequestBody ClassGroup newGroup, @RequestParam Long classId) {
        instructorService.validateClassId(classId);

        ClassGroup addedGroup = instructorService.addGroup(newGroup, classId);
        return new ResponseEntity<>(addedGroup, HttpStatus.CREATED);
    }

    @GetMapping("/groups")
    public List<ClassGroup> getAllGroups(@RequestParam Long classId) {
        instructorService.validateClassId(classId);

        return instructorService.findAllGroups(classId);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<ClassGroup> getGroupById(@PathVariable Long id) {
        instructorService.validateGroupId(id);

        Optional<ClassGroup> optionalEntity = instructorService.findGroupById(id);
        return optionalEntity.map(classGroup -> new ResponseEntity<>(classGroup, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<GenericResponse> deleteGroup(@PathVariable Long id) {
        instructorService.validateGroupId(id);

        Optional<ClassGroup> optionalEntity = instructorService.findGroupById(id);
        if (optionalEntity.isPresent()) {
            instructorService.deleteGroupById(id);
            return new ResponseEntity<>(new GenericResponse("Group with ID " + id + " deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse("Group with ID " + id + " not found"), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/topic")
    public ResponseEntity<Topic> addTopic(@RequestBody Topic newTopic, @RequestParam Long classId) {
        instructorService.validateClassId(classId);

        Topic addedTopic = instructorService.addTopic(newTopic, classId);
        return new ResponseEntity<>(addedTopic, HttpStatus.CREATED);
    }

    @GetMapping("/topics")
    public List<Topic> getAllTopics(@RequestParam Long classId) {
        instructorService.validateClassId(classId);
        return instructorService.findAllTopics(classId);
    }

    @GetMapping("/topic/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        instructorService.validateTopicId(id);

        Optional<Topic> optionalEntity = instructorService.findTopicById(id);
        return optionalEntity.map(topic -> new ResponseEntity<>(topic, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/topic/{id}")
    public ResponseEntity<GenericResponse> deleteTopic(@PathVariable Long id) {
        instructorService.validateTopicId(id);

        Optional<Topic> optionalEntity = instructorService.findTopicById(id);
        if (optionalEntity.isPresent()) {
            instructorService.deleteTopicById(id);
            return new ResponseEntity<>(new GenericResponse("Topic with ID " + id + " deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse("Topic with ID " + id + " not found"), HttpStatus.NOT_FOUND);
        }
    }





    @PostMapping("/group/students")
    public ResponseEntity<Void> addStudentsToGroup(@RequestBody IdList studentIds, @RequestParam Long groupId) {
        instructorService.validateGroupId(groupId);

        instructorService.addStudentsToGroup(studentIds.getIds(), groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/group/students")
    public ResponseEntity<List<User>> getStudentsInGroup(@RequestParam Long groupId) {
        instructorService.validateGroupId(groupId);

        return new ResponseEntity<List<User>>(instructorService.getStudentsInGroup(groupId), HttpStatus.OK);
    }
    @DeleteMapping("/group/student/{id}")
    public ResponseEntity<GenericResponse> deleteStudentFromGroup(@PathVariable Long id, @RequestParam Long groupId) {
        instructorService.validateGroupId(groupId);
        Long del = instructorService.deleteStudentFromGroup(id, groupId);
        if (del > 0) {
            return new ResponseEntity<>(new GenericResponse("Student with ID " + id + " deleted successfully from group."), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse("Student with ID " + id + " not found in this group."), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/not-group/students")
    public ResponseEntity<List<User>> getStudentsNotInGroup(@RequestParam Long classId) {
        return new ResponseEntity<List<User>>(instructorService.getStudentsNotInGroup(classId), HttpStatus.OK);
    }


    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllStudents() {
        return new ResponseEntity<List<User>>(instructorService.getAllStudents(), HttpStatus.OK);
    }



    @GetMapping("/scores")
    public ResponseEntity<List<Score>> getScoresInTopic(@RequestParam Long topicId) {
        System.out.println("topicId = " + topicId);
        return new ResponseEntity<List<Score>>(instructorService.getScoresInTopic(topicId), HttpStatus.OK);
    }

    @DeleteMapping("/score")
    public ResponseEntity<GenericResponse> deleteStudentFromGroup(@RequestParam Long studentId, @RequestParam Long scorerId, @RequestParam Long topicId) {

        Long del = instructorService.deleteScore(studentId, scorerId, topicId);
        if (del > 0) {
            return new ResponseEntity<>(new GenericResponse("Score deleted successfully."), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse("Score not found."), HttpStatus.NOT_FOUND);
        }
    }
}
